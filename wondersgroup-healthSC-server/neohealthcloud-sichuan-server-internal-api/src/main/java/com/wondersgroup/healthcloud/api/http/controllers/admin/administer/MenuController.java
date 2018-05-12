package com.wondersgroup.healthcloud.api.http.controllers.admin.administer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.permission.Menu;
import com.wondersgroup.healthcloud.jpa.repository.permission.MenuRepository;
import com.wondersgroup.healthcloud.jpa.repository.permission.RoleMenuRepository;
import com.wondersgroup.healthcloud.services.permission.MenuService;
import com.wondersgroup.healthcloud.services.permission.dto.MenuDTO;
import com.wondersgroup.healthcloud.utils.MenuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuchunliu on 2015/11/12.
 * <p>
 * 基础资料的维护：菜单，角色绑定菜单，医院组绑定菜单
 */
@Controller
@RequestMapping(value = "/api/menu")
@Admin
public class MenuController {
    @Autowired
    private MenuRepository menuRepo;
    @Autowired
    private RoleMenuRepository roleMenuRepo;
    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     *
     * @param rootMenuId 根菜单ID 1-用户端,2-医生端
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @Admin
    public JsonResponseEntity getMenuInfoList(@RequestParam(required = true) String rootMenuId) {
        JsonResponseEntity result = new JsonResponseEntity();
        List<Menu> resourceList = menuRepo.findAllMenu();

        List<MenuDTO> list = Lists.newArrayList();
        for (Menu menu : resourceList) {
            list.add(new MenuDTO(menu));
        }

        MenuUtils.addMenuChildrenToParent(list);
        MenuDTO menuTree = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null && rootMenuId.equals(list.get(i).getMenuId())) {
                menuTree = list.get(i);
                break;
            }
        }
        result.setData(menuTree);
        return result;
    }

    /**
     * 添加菜单信息
     *
     * @param menuId 菜单主键
     * @return
     */
    @RequestMapping(value = "/menuAdd", method = RequestMethod.GET)
    @ResponseBody
    @Admin
    public JsonResponseEntity menuAdd(@RequestParam(value = "menuId", required = false) String menuId,
                                      @RequestParam(value = "next", required = false) String next) {

        JsonResponseEntity result = new JsonResponseEntity();
        Map<String, Object> map = Maps.newHashMap();
        if (!StringUtils.isEmpty(menuId)) {
            Menu menu = menuRepo.findOne(menuId);
            if (!StringUtils.isEmpty(menu.getParentId())) {
                Menu parent = menuRepo.findOne(menu.getParentId());
                menu.setParentName(null == parent ? null : parent.getName());
            }
            map.put("info", menu);

            if (!StringUtils.isEmpty(next)) { //用于添加下一级菜单
                Menu nmenu = new Menu();
                nmenu.setParentId(menu.getMenuId());
                nmenu.setParentName(menu.getName());
                nmenu.setSort(menuService.getNextSort(menu.getMenuId()).toString());
                map.put("info", nmenu);
            }
        } else {
            Menu menu = new Menu();
            menu.setSort(menuService.getNextSort("1").toString());
            menu.setParentId("1");
            menu.setParentName(menuRepo.findOne("1").getName());
            map.put("info", menu);
        }

        List<Map<String, Object>> resourceList = menuService.findAllMenu();
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Map<String, Object> child : resourceList) {
            Map<String, Object> tmap = Maps.newHashMap();
            if (child.get("menu_id").equals(menuId)) {
                continue;//菜单不可以选择自己为上级菜单
            }
            tmap.put("id", child.get("menu_id"));
            tmap.put("name", child.get("name"));
            tmap.put("pId", child.get("parent_id"));
            Integer next_sort = Integer.parseInt(child.get("next_sort").toString());
            Integer sort = Integer.parseInt(child.get("sort").toString());
            if (0 == next_sort) {
                int multiple = 1;
                while (sort % 10 == 0) {
                    sort = sort / 10;
                    multiple = multiple * 10;
                }
                tmap.put("sort", multiple * 10);
            } else {
                int multiple = 1;
                while (next_sort % 10 == 0) {
                    next_sort = next_sort / 10;
                    multiple = multiple * 10;
                }
                tmap.put("sort", (next_sort + 1) * multiple);
            }
            list.add(tmap);
        }
        map.put("menuTree", new Gson().toJson(list));
        result.setData(map);
        return result;
    }

    /**
     * 获取菜单树列表
     *
     * @return id name pId
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    @Admin
    public JsonResponseEntity<List<Map<String, Object>>> getMenuTreeInfo() {
        JsonResponseEntity<List<Map<String, Object>>> response = new JsonResponseEntity();
        List<Menu> resourceList = menuRepo.findAllMenu();
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Menu menu : resourceList) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", menu.getMenuId());
            map.put("name", menu.getName());
            map.put("pId", menu.getParentId());
            list.add(map);
        }
        response.setData(list);
        return response;
    }

    /**
     * 更新菜单信息信息
     * Updated upstream
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Admin
    public JsonResponseEntity updateMenuInfo(@RequestBody Menu menu) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            if (StringUtils.isEmpty(menu.getMenuId())) {
                menu.setMenuId(IdGen.uuid());
                menu.setCreateDate(new Date());
            }
            menuRepo.save(menu);
            response.setMsg("保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(1000);
            response.setMsg("保存失败");
        }
        return response;
    }

    @RequestMapping(value = "/sort/update", method = RequestMethod.POST)
    @ResponseBody
    @Admin
    public JsonResponseEntity updateMenuInfo(@RequestBody Menu[] menus) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            for (Menu menu : menus) {
                if (StringUtils.isEmpty(menu.getSort())) {
                    menu.setSort("0");
                }
                menuRepo.updateMenuSort(menu.getMenuId(), menu.getSort());
            }
            response.setMsg("保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(1000);
            response.setMsg("保存失败");
        }
        return response;
    }

    /**
     * 删除菜单信息
     *
     * @param menuIds 菜单主键集合，多个用户id，用,间隔
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Admin
    public JsonResponseEntity deleteUerInfo(
            @RequestParam(value = "menuIds", required = true) String menuIds) {
        List<String> list = Lists.newArrayList();
        for (String menuId : menuIds.split(",")) {
            list.add(menuId);
            this.getMenus(list, menuRepo.getChildMenuId(menuId));

        }
        menuRepo.deleteMenuInfo(list);
        roleMenuRepo.deleteRoleMenu(list);
        JsonResponseEntity response = new JsonResponseEntity();
        response.setMsg("删除成功");
        return response;
    }

    public void getMenus(List<String> list, List<String> sourceList) {
        for (String menuId : sourceList) {
            list.add(menuId);
            this.getMenus(list, menuRepo.getChildMenuId(menuId));
        }
    }

    private void sortMenu(List<Menu> list, List<Menu> resourceList, String parentId) {
        for (Menu menu : resourceList) {
            List<Menu> newList = new ArrayList<>(resourceList);
            if (!StringUtils.isEmpty(menu.getParentId()) && menu.getParentId().equals(parentId)) {
                list.add(menu);
                newList.remove(menu);
                this.sortMenu(list, newList, menu.getMenuId());
            }
        }
    }
}
