package com.kongx.serve.mapper;

import com.kongx.common.handler.JSONHandler;
import com.kongx.serve.entity.flow.ServicePipeline;
import com.kongx.serve.entity.system.SystemProfile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ServicePipelineMapper {
    @Select({"<script>", "SELECT * FROM kongx_service_pipeline  where 1=1 and profile=#{profile.profile}",
            "<when test='job.name!=null'>",
            " and (name like CONCAT('%',#{job.name},'%'))",
            "</when>",
            "order by create_at desc", "</script>"})
    @Results({
            @Result(property = "linkList", column = "link_list", typeHandler = JSONHandler.class),
            @Result(property = "nodeList", column = "node_list", typeHandler = JSONHandler.class),
            @Result(property = "origin", column = "origin", typeHandler = JSONHandler.class),
    })
    List<ServicePipeline> findAll(@Param("profile") SystemProfile systemProfile, @Param("job") ServicePipeline project);

    @Insert({"insert into kongx_service_pipeline(name,link_list,node_list,origin,remark,creator,create_at,profile) values (",
            "#{name},#{linkList,typeHandler=com.kongx.common.handler.JSONHandler},",
            "#{nodeList,typeHandler=com.kongx.common.handler.JSONHandler},",
            "#{origin,typeHandler=com.kongx.common.handler.JSONHandler},",
            "#{remark},",
            "#{creator}, #{create_at, jdbcType=TIMESTAMP},#{profile}",
            ")"})
    int insert(ServicePipeline project);

    @Update({"update kongx_service_pipeline set " +
            "name=#{name},",
            "link_list=#{linkList,typeHandler=com.kongx.common.handler.JSONHandler},",
            "node_list=#{nodeList,typeHandler=com.kongx.common.handler.JSONHandler},",
            "origin=#{origin,typeHandler=com.kongx.common.handler.JSONHandler},",
            "modifier=#{modifier},modify_at=#{modify_at, jdbcType=TIMESTAMP},",
            "remark=#{remark} where id=#{id} "})
    int update(ServicePipeline project);

    @Select({"select * from kongx_service_pipeline  where id = #{id} "})
    @Results({
            @Result(property = "linkList", column = "link_list", typeHandler = JSONHandler.class),
            @Result(property = "nodeList", column = "node_list", typeHandler = JSONHandler.class),
            @Result(property = "origin", column = "origin", typeHandler = JSONHandler.class),
    })
    ServicePipeline findById(int id);

    @Delete("delete from kongx_service_pipeline where id=#{id}")
    int deleteById(int id);

}
