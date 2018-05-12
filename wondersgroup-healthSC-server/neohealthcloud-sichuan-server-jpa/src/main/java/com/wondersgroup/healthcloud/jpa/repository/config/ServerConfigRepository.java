package com.wondersgroup.healthcloud.jpa.repository.config;

import com.wondersgroup.healthcloud.jpa.entity.config.AppConfig;
import com.wondersgroup.healthcloud.jpa.entity.config.ServerConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by dukuanxinon 2017/2/24.
 */
public interface ServerConfigRepository extends JpaRepository<ServerConfig, Integer> {

    @Query(nativeQuery = true, value = "select * from tb_server_config where area_code=?1 order by area_code")
    List<ServerConfig> queryServerConfig(String areaCode);

    @Query(nativeQuery = true, value = "select * from tb_server_config group by area_code")
    List<ServerConfig> queryServerConfig();

    @Query(nativeQuery = true, value = "select * from tb_server_config where area_code=?1 and type=?2")
    ServerConfig queryApiUrl(String areaCode, String type);
}
