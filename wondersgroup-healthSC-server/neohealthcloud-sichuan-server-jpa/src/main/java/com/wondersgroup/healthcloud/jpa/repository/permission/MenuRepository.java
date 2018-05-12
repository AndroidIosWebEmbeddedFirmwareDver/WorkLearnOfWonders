package com.wondersgroup.healthcloud.jpa.repository.permission;

import com.wondersgroup.healthcloud.jpa.entity.permission.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by zhuchunliu on 2015/11/12.
 */
public interface MenuRepository extends JpaRepository<Menu, String>, JpaSpecificationExecutor {
    @Query("select m from Menu m where delFlag = '0' order by parentId asc, sort asc")
    List<Menu> findAllMenu();

    @Transactional
    @Modifying
    @Query("delete from Menu where menuId in (?1) ")
    void deleteMenuInfo(Collection menuIds);

    @Query("select m from Menu m where parentId = ?1  and type = '1' and isShow = '1' and delFlag = '0'  order by sort asc")
    List<Menu> getMenuExpectButtonByParentId(String parentId);

    @Query("select m from Menu m where href = ?1 and delFlag = '0'")
    Menu getMenuByUrl(String url);

    @Query("select m.menuId from Menu m where parentId = ?1 ")
    List<String> getChildMenuId(String parentId);

    @Transactional
    @Modifying
    @Query("update Menu set sort = ?2  where menuId = ?1 ")
    void updateMenuSort(String menuId, String sort);
}
