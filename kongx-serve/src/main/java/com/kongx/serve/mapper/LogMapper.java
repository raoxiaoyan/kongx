package com.kongx.serve.mapper;

import com.kongx.serve.entity.system.OperationLog;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogMapper {
    @Insert({"insert into kongx_operation_log(userId,operation_type,content,target,creator,create_at,remark,profile,ip) ",
            "values(#{log.userId}, #{log.operation},",
            "#{log.content,typeHandler=com.kongx.common.handler.JSONHandler},#{log.target},",
            "#{log.creator}, #{log.create_at, jdbcType=TIMESTAMP},",
            "#{log.remark},#{log.profile},#{log.ip})"})
    int add(@Param("log") OperationLog log);

    @Select("SELECT t.* FROM kongx_operation_log t where TO_DAYS(NOW())-#{days}<TO_DAYS(t.create_at) order by t.create_at desc")
    List<OperationLog> findAllByBeforeDays(int days);

    @Select({"<script>", "SELECT t.* FROM kongx_operation_log t where TO_DAYS(NOW())-#{days}=TO_DAYS(t.create_at)",
            "<when test='keyword!=null'>",
            " and t.remark like CONCAT('%',#{keyword},'%')",
            "</when>",
            " order by t.create_at desc",
            "</script>"})
    @Results({
            @Result(property = "operation", column = "operation_type", javaType = String.class)
    })
    List<OperationLog> findAllByDays(@Param("days") int days, @Param("keyword") String keyword);

    @Select("select date_add(date_format(now(),'%Y%m%d'), interval -#{days} day) dateStr from dual")
    Map getDateStr(int days, String type);
}
