package com.kongx.serve.mapper;

import com.kongx.serve.entity.system.SystemProfile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SystemProfileMapper {
    @Select("SELECT * FROM kongx_system_profile order by create_at desc")
    @Results(value = {
            @Result(property = "deployType", column = "deploy_type", javaType = String.class),
            @Result(property = "profileCode", column = "profile_code", javaType = String.class),
    })
    List<SystemProfile> findAll();

    @Insert({"insert into kongx_system_profile(profile_code,name,env,deploy_type,ab,url, consul_url,config_url,extensions,profile, creator, create_at) " +
            "values(#{profileCode},#{name},#{env},#{deployType},#{ab}, #{url},#{consul_url},#{config_url},#{extensions}, #{profile}, #{creator}, #{create_at, jdbcType=TIMESTAMP})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(SystemProfile systemProfile);

    @Update({"update kongx_system_profile set ",
            "profile_code=#{client.profileCode},name=#{client.name},ab=#{client.ab},url=#{client.url},",
            "env=#{client.env},deploy_type=#{client.deployType},",
            "consul_url=#{client.consul_url},config_url=#{client.config_url},",
            "extensions=#{client.extensions},profile=#{client.profile} where id=#{client.id}"})
    int update(@Param("client") SystemProfile client);

    @Delete("delete from kongx_system_profile where id=#{id}")
    int remove(@Param("id") int id);

    @Select("SELECT * FROM kongx_system_profile where profile_code=#{profile} ")
    @Results(value = {
            @Result(property = "deployType", column = "deploy_type", javaType = String.class),
            @Result(property = "profileCode", column = "profile_code", javaType = String.class),
    })
    SystemProfile findByProfile(String profile);

}
