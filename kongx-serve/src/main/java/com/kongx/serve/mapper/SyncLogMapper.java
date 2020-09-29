package com.kongx.serve.mapper;


import com.kongx.serve.entity.gateway.SyncLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SyncLogMapper {
    @Insert({"insert into kongx_sync_log(src_client, dest_client,status, content,comment, creator, create_at,sync_no,service) " +
            "values(#{src_client},#{dest_client},#{status}, " +
            "#{content,typeHandler=com.kongx.common.handler.JSONHandler}, #{comment},#{creator}, #{create_at, jdbcType=TIMESTAMP},#{syncNo},#{service})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(SyncLog log);

    @Select("SELECT * FROM kongx_sync_log where sync_no=#{syncNo} order by create_at asc")
    @Results({
            @Result(property = "syncNo", column = "sync_no", javaType = String.class)
    })
    List<SyncLog> findBySyncNo(String syncNo);

    @Select("SELECT * FROM kongx_sync_log where sync_no=#{syncNo} and service=#{service} and dest_client=#{dest_client} order by create_at asc")
    @Results({
            @Result(property = "syncNo", column = "sync_no", javaType = String.class)
    })
    List<SyncLog> findBySyncNoAndService(String syncNo, String service, String dest_client);
}
