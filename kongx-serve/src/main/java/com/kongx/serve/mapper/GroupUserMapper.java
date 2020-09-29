package com.kongx.serve.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GroupUserMapper {
    @Insert({"<script>",
            "replace into kongx_user_group_user(group_id,user_id) values",
            " (#{groupId},#{userId} )"
            , "</script>"})
    int insert(int groupId, String userId);


    @Delete({"<script>", "replace into kongx_user_group_user(group_id,user_id) values",
            "<foreach collection='userIds' item='userId' index='index' separator=','>",
            " (#{groupId},#{userId} )",
            "</foreach>"
            , "</script>"})
    int batchInsert(@Param("groupId") int groupId, @Param("userIds") List<String> mapList);

    @Delete({"delete from kongx_user_group_user where group_id=#{groupId} and user_id=#{userId}"})
    int remove(int groupId, String userId);

    @Delete({"<script>", "delete from kongx_user_group_user where",
            "<foreach collection='userIds' item='userId' index='index' separator=','>",
            " (group_id=#{groupId} and user_id=#{userId})",
            "</foreach>"
            , "</script>"})
    int batchRemove(@Param("groupId") int groupId, @Param("userIds") List<String> mapList);
}
