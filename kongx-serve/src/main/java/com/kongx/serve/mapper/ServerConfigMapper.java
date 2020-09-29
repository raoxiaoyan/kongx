package com.kongx.serve.mapper;

import com.kongx.serve.entity.system.ServerConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServerConfigMapper {
    @Select("SELECT * FROM kongx_server_config order by create_at desc")
    @Results({
            @Result(property = "configKey", column = "config_key", javaType = String.class),
            @Result(property = "configValue", column = "config_value", javaType = String.class),
            @Result(property = "configType", column = "config_type", javaType = String.class)
    })
    List<ServerConfig> findAll();

    @Insert({"insert into kongx_server_config(config_key, config_value,config_type, comment, creator, create_at) values(#{configKey}, #{configValue}, #{configType},#{comment}, #{creator}, #{create_at, jdbcType=TIMESTAMP})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(ServerConfig config);

    @Update("update kongx_server_config set config_value=#{config.configValue},config_type=#{config.configType},comment=#{config.comment},modifier=#{config.modifier},modify_at=#{config.modify_at, jdbcType=TIMESTAMP} where id=#{config.id}")
    int update(@Param("config") ServerConfig config);

    @Select("SELECT * FROM kongx_server_config WHERE config_key=#{key}")
    @Results({
            @Result(property = "configKey", column = "config_key", javaType = String.class),
            @Result(property = "configValue", column = "config_value", javaType = String.class),
            @Result(property = "configType", column = "config_type", javaType = String.class)
    })
    ServerConfig findByKey(String key);
}
