package com.kongx.serve.mapper;

import com.github.pagehelper.Page;
import com.kongx.common.handler.JSONHandler;
import com.kongx.serve.entity.gateway.SyncEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SyncConfigMapper<T> {
    @Insert({"insert into kongx_sync_config(services,src_client, dest_clients, comment, creator, create_at,sync_no,status,data_type,policy,log_type) " +
            "values(#{services,typeHandler=com.kongx.common.handler.JSONHandler}, " +
            "#{src_client,typeHandler=com.kongx.common.handler.JSONHandler}," +
            "#{clients,typeHandler=com.kongx.common.handler.JSONHandler}, #{comment}, #{creator}, #{create_at, jdbcType=TIMESTAMP},#{syncNo},#{status}" +
            ",#{dataType},#{policy},#{logType})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(SyncEntity<T> config);

    @Update("update kongx_sync_config set comment=#{config.comment},status=#{config.status} where id=#{config.id}")
    int update(@Param("config") SyncEntity config);

    @Select("SELECT * FROM kongx_sync_config where log_type=#{logType} order by create_at desc")
    @Results({
            @Result(property = "dataType", column = "data_type", javaType = String.class),
            @Result(property = "syncNo", column = "sync_no", javaType = String.class),
            @Result(property = "services", column = "services", typeHandler = JSONHandler.class),
            @Result(property = "clients", column = "dest_clients", typeHandler = JSONHandler.class),
            @Result(property = "src_client", column = "src_client", typeHandler = JSONHandler.class)
    })
    Page<SyncEntity<T>> findAll(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize, @Param("logType") int logType);

    @Select("SELECT * FROM kongx_sync_config where sync_no=#{syncNo}")
    @Results({
            @Result(property = "dataType", column = "data_type", javaType = String.class),
            @Result(property = "syncNo", column = "sync_no", javaType = String.class),
            @Result(property = "services", column = "services", typeHandler = JSONHandler.class),
            @Result(property = "clients", column = "dest_clients", typeHandler = JSONHandler.class),
            @Result(property = "src_client", column = "src_client", typeHandler = JSONHandler.class)
    })
    SyncEntity<T> findBySyncNo(String syncNo);
}
