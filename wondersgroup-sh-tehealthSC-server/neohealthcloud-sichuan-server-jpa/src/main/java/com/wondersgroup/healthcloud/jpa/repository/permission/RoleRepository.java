package com.wondersgroup.healthcloud.jpa.repository.permission;

import com.wondersgroup.healthcloud.jpa.entity.permission.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by zhuchunliu on 2015/11/12.
 */
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor {

    @Query("select r from Role r where r.name like %?1% order by r.name asc")
    List<Role> findAllRole(String name, Pageable pageable);

    @Query("select count(r) from Role r where r.name like %?1% and r.delFlag = '0'")
    Integer findAllRoleTotal(String name);

    @Transactional
    @Modifying
    @Query("update Role set delFlag = '1' where roleId in (?1)")
    void deteleRoleInfo(String[] roleIds);

    @Transactional
    @Modifying
    @Query("update Role set delFlag = '0' where roleId in (?1)")
    void enableRoleInfo(String[] roleIds);
}
