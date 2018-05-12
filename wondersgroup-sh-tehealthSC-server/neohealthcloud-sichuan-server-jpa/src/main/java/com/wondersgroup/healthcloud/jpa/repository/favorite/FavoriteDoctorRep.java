package com.wondersgroup.healthcloud.jpa.repository.favorite;

import com.wondersgroup.healthcloud.jpa.entity.favorite.FavoriteDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by dukuanxin on 2016/11/10.
 */
public interface FavoriteDoctorRep extends JpaRepository<FavoriteDoctor, Integer> {

    @Query(nativeQuery = true,
            value = "select * from app_tb_favorite_doctor where doc_id=?1 and user_id=?2 order by id desc limit 1")
    FavoriteDoctor queryFavor(int docId, String userId);
}
