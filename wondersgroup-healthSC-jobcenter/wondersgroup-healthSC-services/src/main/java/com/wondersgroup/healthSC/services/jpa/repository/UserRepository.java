package com.wondersgroup.healthSC.services.jpa.repository;

import com.wondersgroup.healthSC.services.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nick on 2016/11/7.
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query(nativeQuery = true, value = "select * from tb_user_account a where a.verification_level = ?1 limit ?2, ?3")
    List<User> getUserByVerificationLevel(int verificationLevel, int start, int end);

    @Query(nativeQuery = true, value = "select count(*) from tb_user_account a where a.verification_level = ?1")
    int countByVerificationLevel(int verificationLevel);
}
