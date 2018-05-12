package com.wondersgroup.healthcloud.services.permission.impl;

import com.wondersgroup.healthcloud.jpa.entity.permission.Menu;
import com.wondersgroup.healthcloud.services.permission.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/17.
 */
@Service("menuServiceImpl")
public class MenuServiceImpl implements MenuService {

    @Autowired
    private JdbcTemplate jt;

    @Override
    public List<Map<String, Object>> getMenuByRole(String roleId) {
        String sql = " select menu.menu_id,menu.name,menu.parent_id, href, type," +
                "   case when (select count(1) from tb_permission_role_menu where role_id = '" + roleId + "' and menu_id = menu.menu_id and del_flag='0') <>0 then 1 else 0 end checked" +
                "   from tb_permission_menu menu where del_flag = '0'";

        return jt.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> getMenuByGroup(String groupId) {
        String sql = " select menu.menu_id ,menu.name,menu.parent_id, " +
                "   case when (select count(1) from tb_permission_group_menu where group_id = '" + groupId + "' and menu_id = menu.menu_id and del_flag='0') <>0 then 1 else 0 end checked" +
                "   from tb_permission_menu menu where del_flag = '0'";
        return jt.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> findAllMenu() {
        String sql = "select menu_id, name, parent_id,sort," +
                "   IFNULL((select max(sort) from tb_permission_menu where parent_id = menu.menu_id),0) as next_sort" +
                "   from tb_permission_menu menu  where del_flag = '0' order by parent_id asc , sort asc";
        return jt.queryForList(sql);
    }

    /**
     * 根据当前menuId,获取下一级的菜单的sort值
     *
     * @param menuId
     * @return
     */
    @Override
    public Integer getNextSort(String menuId) {
        String sql = "select IFNULL(max(sort),0) sort from tb_permission_menu where parent_id = '" + menuId + "'";
        Integer sort = Integer.parseInt(jt.queryForMap(sql).get("sort").toString());
        if (sort != 0) {
            int multiple = 1;
            while (sort % 10 == 0) {
                sort = sort / 10;
                multiple = multiple * 10;
            }
            return (sort + 1) * multiple;
        } else {
            sql = "select IFNULL(max(sort),0) as sort from tb_permission_menu where menu_id = '" + menuId + "'";
            sort = Integer.parseInt(jt.queryForMap(sql).get("sort").toString());
            int multiple = 1;
            while (sort % 10 == 0) {
                sort = sort / 10;
                multiple = multiple * 10;
            }
            return multiple * 10;
        }
    }

    @Override
    public List<Menu> getMenuExpectButtonByParentId(String menuId, String userId) {
        String sql = "select DISTINCT menu.* from tb_permission_menu menu \n" +
                "   join tb_permission_role_menu role_menu on menu.menu_id = role_menu.menu_id\n" +
                "   join tb_permission_role role on role_menu.role_id = role.role_id\n" +
                "   join tb_permission_user_role user_role on role.role_id = user_role.role_id" +
                "   where  type = '1' and is_show = '1' and role.useable = '1' and menu.del_flag = '0'" +
                "   and menu.parent_id = '" + menuId + "' and user_role.user_id = '" + userId + "'" +
                "   order by sort asc";
        return jt.query(sql, new RowMapper<Menu>() {
            @Override
            public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
                Menu menu = new Menu();
                menu.setMenuId(resultSet.getString("menu_id"));
                menu.setName(resultSet.getString("name"));
                menu.setHref(resultSet.getString("href"));
                menu.setIcon(resultSet.getString("icon"));
                menu.setParentId(resultSet.getString("parent_id"));
                return menu;
            }
        });

    }
}
