package com.wondersgroup.healthcloud.jpa.repository.permission;

import com.wondersgroup.healthcloud.jpa.entity.permission.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Created by zhuchunliu on 2015/11/11.
 */

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor {

    @Query("select u from User u  where loginname = ?1 and delFlag = '0'")
    User findByLoginName(String loginname);

    @Query("select u from User u  where userId <> ?1 and loginname = ?2")
    User findByLoginName(String userId, String loginname);

    //@Transactional
    @Modifying
    @Query("update User  set delFlag = '1', updateDate = ?2 where userId in (?1)")
    void deteleUserInfo(String[] userIds, Date updateDate);

    @Query("select u from User u where loginname = ?1 and password = ?2 and delFlag = '0'")
    User findOne(String loginname, String password);
}
