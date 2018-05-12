package com.wondersgroup.healthcloud.jpa.repository.permission;

import com.wondersgroup.healthcloud.jpa.entity.permission.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by zhuchunliu on 2015/11/12.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, String>, JpaSpecificationExecutor {

    @Transactional
    @Modifying
    @Query("delete from UserRole where roleId = ?1 and userId = ?2 ")
    void deteleUserRoleInfo(String roleId, String userId);

    @Transactional
    @Modifying
    @Query("delete from UserRole where roleId in (?1) ")
    void deteleUserRoleInfo(String[] roleIds);


    @Query("select ur from UserRole ur where ur.userId = ?1 and ur.delFlag = '0'")
    List<UserRole> findAllInfoByUser(String userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserRole where userId = ?1")
    void deleteByUserId(String userId);
}
