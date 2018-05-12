package com.wondersgroup.healthcloud.api.http.controllers.admin.administer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wondersgroup.healthcloud.api.helper.PropertiesUtil;
import com.wondersgroup.healthcloud.api.utils.Pager;
import com.wondersgroup.healthcloud.common.http.annotations.Admin;
import com.wondersgroup.healthcloud.common.http.dto.JsonResponseEntity;
import com.wondersgroup.healthcloud.common.utils.IdGen;
import com.wondersgroup.healthcloud.jpa.entity.permission.Role;
import com.wondersgroup.healthcloud.jpa.entity.permission.RoleEntity;
import com.wondersgroup.healthcloud.jpa.entity.permission.User;
import com.wondersgroup.healthcloud.jpa.entity.permission.UserRole;
import com.wondersgroup.healthcloud.jpa.repository.permission.RoleRepository;
import com.wondersgroup.healthcloud.jpa.repository.permission.UserRepository;
import com.wondersgroup.healthcloud.jpa.repository.permission.UserRoleRepository;
import com.wondersgroup.healthcloud.services.permission.BasicInfoService;
import com.wondersgroup.healthcloud.services.permission.MenuService;
import com.wondersgroup.healthcloud.services.permission.dto.MenuDTO;
import com.wondersgroup.healthcloud.services.user.exception.ErrorAccountNotExitException;
import com.wondersgroup.healthcloud.utils.MenuUtils;
import com.wondersgroup.healthcloud.utils.security.RSA;
import com.wondersgroup.healthcloud.utils.security.RSAKey;
import okio.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuchunliu on 2015/11/12.
 * <p/>
 * 基础资料的维护：人员，角色，分组，人员角色，医院分组信息
 */
@Controller
@RequestMapping(value = "/api/basicInfo")
@Admin
public class BasicInfoController {
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserRoleRepository userRoleRepo;
    @Autowired
    private BasicInfoService basicInfoService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private PropertiesUtil propertiesUtil;

    /**
     * 添加用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/userAdd", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseEntity userAdd(@RequestParam(value = "userId", required = false) String userId) {
        JsonResponseEntity result = new JsonResponseEntity();
        User user = new User();
        if (!StringUtils.isEmpty(userId)) {
            user = userRepo.findOne(userId);
        }
//        设置角色选中状态
        List<Map<String, Object>> list = basicInfoService.findAllRole(userId);
        List<RoleEntity> roleList = Lists.newArrayList();
        for (Map<String, Object> role : list) {// 仅查询用户拥有的角色
            if ((null == role.get("check_role_id")) ? false : true) {
                RoleEntity child = new RoleEntity();
                child.setRoleId(role.get("role_id").toString());
                child.setName(role.get("name").toString());
                child.setChecked((null == role.get("check_role_id")) ? false : true);
                roleList.add(child);
            }
        }

        user.setRoleList(roleList);

        result.setData(user);
        return result;
    }

    /**
     * 添加角色信息
     *
     * @param roleId 角色主键
     * @return
     */
    @RequestMapping(value = "/roleAdd", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseEntity roleAdd(@RequestParam(value = "roleId", required = false) String roleId,
                                      @RequestParam(required = true) String rootMenuId) {
        JsonResponseEntity result = new JsonResponseEntity();
        Map<String, Object> map = Maps.newHashMap();

        List<Map<String, Object>> list = menuService.getMenuByRole(roleId);//获取选中的菜单信息
        List<MenuDTO> treeMenu = Lists.newArrayList();
        for (Map<String, Object> child : list) {
            MenuDTO menuDTO = new MenuDTO();
            menuDTO.setMenuId(child.get("menu_id").toString());
            menuDTO.setMenuName(StringUtils.isEmpty(child.get("name")) ? null : child.get("name").toString());
            menuDTO.setParentId(StringUtils.isEmpty(child.get("parent_id")) ? null : child.get("parent_id").toString());
            menuDTO.setHref(StringUtils.isEmpty(child.get("href")) ? null : child.get("href").toString());
            menuDTO.setType(StringUtils.isEmpty(child.get("type")) ? null : child.get("type").toString());
            if (StringUtils.isEmpty(child.get("checked")) || child.get("checked").toString().equals("0")) {
                menuDTO.setChecked(false);
            } else {
                menuDTO.setChecked(true);
            }
            treeMenu.add(menuDTO);
        }
        List<MenuDTO> listMenu = Lists.newArrayList();
        Collections.addAll(listMenu, new MenuDTO[treeMenu.size()]);
        Collections.copy(listMenu, treeMenu);
        MenuUtils.addMenuChildrenToParent(treeMenu);
        MenuDTO menuTree = null;
        for (int i = 0; i < treeMenu.size(); i++) {
            if (treeMenu.get(i) != null && rootMenuId.equals(treeMenu.get(i).getMenuId())) {
                menuTree = treeMenu.get(i);
                break;
            }
        }
        if (menuTree != null && list.size() > 0) {
            map.put("listMenu", listMenu);
            map.put("treeMenu", menuTree);
            result.setData(map);
        } else {
            result.setMsg("未查询到相关数据！");
        }
        return result;
    }


    /**
     * 获取用户列表
     * 传参数 hospitalId 医院id，loginname 登录名，username用户名
     *
     * @return
     */
    @PostMapping(value = "/user/list")
    @ResponseBody
    public JsonResponseEntity<Pager> getUserInfoList(@RequestBody Pager pager) {

        JsonResponseEntity<Pager> response = new JsonResponseEntity<Pager>();
        if (null != pager) {
            List<User> list = basicInfoService.findAllUser(pager.getParameter(), propertiesUtil.getAccount(), pager.getNumber(), pager.getSize());
            int total = basicInfoService.findAllUserTotal(pager.getParameter(), propertiesUtil.getAccount());
            pager.setTotalPages((total + pager.getSize() - 1) / pager.getSize());// 页数
            pager.setTotalElements(total);// 记录数
            pager.setData(list);// 结果集
        }
        response.setData(pager);
        return response;
    }

    /**
     * 更新用户信息
     * Updated upstream
     *
     * @return
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity updateUser(User user, @RequestParam(value = "roleIds") String roleIds) {
        JsonResponseEntity response = new JsonResponseEntity();
        user.setMainArea("51");
        if (StringUtils.isEmpty(user.getUserId())) {
            user.setUserId(IdGen.uuid());
            user.setCreateDate(new Date());
            user.setLocked("0");
            if (!StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(ByteString.of(RSA.decryptByPrivateKey(user.getPassword(), RSAKey.adminPrivateKey).getBytes()).md5().hex());
            }
        } else {
            User original = userRepo.findOne(user.getUserId());
            if (original == null) {
                throw new ErrorAccountNotExitException();
            }
            user.setPassword(original.getPassword());
            user.setLocked(null == original ? "0" : original.getLocked());
            user.setCreateDate(original.getCreateDate());
            user.setUpdateDate(new Date());
        }
        basicInfoService.updateUserInfo(user, roleIds);
        response.setMsg("保存成功");

        return response;
    }

    /**
     * 更新用户信息【基本信息】
     * Updated upstream
     *
     * @return
     */
    @RequestMapping(value = "/user/update/info", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity updateUserInfo(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "username", required = true) String username) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            User user = userRepo.findOne(userId);
            if (user == null) {
                throw new ErrorAccountNotExitException();
            }
            user.setUsername(username);
            userRepo.save(user);
            response.setMsg("保存成功");
        } catch (Exception e) {
            response.setCode(1000);
            response.setMsg("保存失败");
        }
        return response;
    }

    /**
     * 更新用户信息
     * Updated upstream
     *
     * @return
     */
    @RequestMapping(value = "/user/update/password", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity updateUserPassword(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "oldPassword", required = true) String oldPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            User user = userRepo.findOne(userId);
            if (!ByteString.of(RSA.decryptByPrivateKey(oldPassword, RSAKey.adminPrivateKey).getBytes()).md5().hex().equals(user.getPassword())) {
                response.setCode(1000);
                response.setData("旧密码输入错误");
            } else {
                user.setPassword(ByteString.of(RSA.decryptByPrivateKey(newPassword, RSAKey.adminPrivateKey).getBytes()).md5().hex());
                userRepo.save(user);
                response.setMsg("保存成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(1000);
            response.setMsg("保存失败");
        }
        return response;
    }

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity resetPassword(@RequestParam(value = "userId", required = true) String userId) {
        JsonResponseEntity result = new JsonResponseEntity();
        try {
            User user = userRepo.findOne(userId);
            user.setPassword(ByteString.of("WondersSc2016".getBytes()).md5().hex());
            userRepo.save(user);
            result.setMsg("密码已重置为:WondersSc2016");
        } catch (Exception ex) {
            result.setCode(1000);
            result.setMsg("保存失败");
        }
        return result;
    }

    /**
     * 锁定用户信息
     * Updated upstream
     *
     * @return
     */
    @RequestMapping(value = "/user/locked", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity lockedUser(
            @RequestParam(value = "userid", required = true) String userid) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            User user = userRepo.findOne(userid);
            user.setUpdateDate(new Date());
            user.setLocked(user.getLocked() == null ? "1" : String.valueOf(1 - Integer.parseInt(user.getLocked())));
            userRepo.save(user);
            response.setMsg("操作成功");

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(1000);
            response.setMsg("操作失败");
        }
        return response;
    }

    /**
     * 删除用户信息
     *
     * @param userIds 用户主键集合，多个用户id，用,间隔
     * @return
     */
    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity deleteUerInfo(@RequestParam(value = "userIds", required = true) String userIds) {
        userRepo.deteleUserInfo(userIds.split(","), new Date());

        JsonResponseEntity response = new JsonResponseEntity();
        return response;
    }

    /**
     * 判断登录名是否已经存在
     *
     * @return 1：表示已经存在，0：表示不存在
     */
    @RequestMapping(value = "/user/nameExist", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseEntity getNameExist(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "loginname", required = true) String loginname) {

        User user = null;
        if (StringUtils.isEmpty(userId)) {
            user = userRepo.findByLoginName(loginname);
        } else {
            user = userRepo.findByLoginName(userId, loginname);
        }
        JsonResponseEntity response = new JsonResponseEntity();
        if (null == user) {
            response.setCode(0);
        } else {
            response.setCode(1000);
            response.setMsg("该登录名已存在");
        }
        return response;
    }


    /**
     * 获取角色列表
     *
     * @return
     */
    @RequestMapping(value = "/role/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity<List<Role>> getRoleInfoList(@RequestBody(required = false) Role role) {

        JsonResponseEntity<List<Role>> response = new JsonResponseEntity<>();
        String name = "";
        if (role != null) {
            name = null == role.getName() ? "" : role.getName().toString().trim();
        }
        List<Role> list = roleRepo.findAllRole(name, new PageRequest(0, 9999));
        if (list != null && list.size() > 0) {
            response.setData(list);
        } else {
            response.setCode(1000);
            response.setMsg("查询无角色信息");
        }
        return response;
    }

    /**
     * 更新角色信息
     *
     * @param role    角色信息
     * @param menuIds 选中的菜单信息
     * @return
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity updateRoleInfo(Role role, @RequestParam(value = "menuIds") String menuIds) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            basicInfoService.updateRoleInfo(role, menuIds);
            response.setMsg("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(1000);
            response.setMsg("保存失败");
        }
        return response;
    }

    /**
     * 删除角色信息
     */
    @RequestMapping(value = "/role/enOrDisable", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity deleteRoleInfo(@RequestParam(value = "roleIds", required = true) String roleIds,
                                             @RequestParam(value = "delFlag", required = true) String delFlag) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            if ("0".equals(delFlag)) {// 启用角色
                roleRepo.enableRoleInfo(roleIds.split(","));
            } else {// 禁用角色
                roleRepo.deteleRoleInfo(roleIds.split(","));
                userRoleRepo.deteleUserRoleInfo(roleIds.split(","));
            }
            response.setMsg("操作成功！");
        } catch (Exception ex) {
            response.setCode(1000);
            response.setMsg("操作失败！");
        }
        return response;
    }

    /**
     * 获取指定角色下用户列表信息
     *
     * @return userId ,loginname, username, hospitalName
     */
    @RequestMapping(value = "/role/user/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResponseEntity<List<User>> getRoleUserInfoList(@RequestParam(value = "roleId", required = true) String roleId) {
        JsonResponseEntity<List<User>> response = new JsonResponseEntity<>();
        List<User> list = basicInfoService.getRoleUserInfo(roleId);
        response.setData(list);
        return response;
    }

    /**
     * 保存用户角色
     *
     * @return
     */
    @RequestMapping(value = "/role/user/update", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity<List<Map<String, Object>>> updateRoleUserInfo(UserRole userRole) {
        JsonResponseEntity response = new JsonResponseEntity();
        try {
            if (StringUtils.isEmpty(userRole.getId())) {
                userRole.setId(IdGen.uuid());
                userRole.setCreateDate(new Date());
            }
            userRoleRepo.save(userRole);
            response.setMsg("保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(1000);
            response.setMsg("保存失败");
        }
        return response;
    }

    /**
     * 删除用户角色
     *
     * @param roleId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/role/user/delete", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponseEntity<List<Map<String, Object>>> deleteRoleUserInfo(
            @RequestParam(value = "roleId", required = true) String roleId,
            @RequestParam(value = "userId", required = true) String userId) {

        userRoleRepo.deteleUserRoleInfo(roleId, userId);
        JsonResponseEntity response = new JsonResponseEntity();
        return response;
    }
}
