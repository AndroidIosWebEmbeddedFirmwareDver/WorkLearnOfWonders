package com.wondersgroup.healthcloud.jpa.repository.permission;

import com.wondersgroup.healthcloud.jpa.entity.permission.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by zhuchunliu on 2015/11/12.
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu, String>, JpaSpecificationExecutor {
    @Transactional
    @Modifying
    @Query("delete from RoleMenu where roleId = ?1")
    void deleteRoleMenu(String roleId);

    @Transactional
    @Modifying
    @Query("delete from RoleMenu where menuId in (?1) ")
    void deleteRoleMenu(Collection menuIds);
}
