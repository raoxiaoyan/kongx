package com.kongx.serve.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GroupRoleMapper {
    @Insert({"<script>",
            "insert into kongx_user_group_role(group_id,role_id,profile) values",
            "<foreach collection='maps' item='param' index='index' separator=','>",
            " (#{param.groupId}, #{param.roleId},#{param.profile} )",
            "</foreach>"
            , "</script>"})
    int insert(@Param("maps") List<Map> maps);

    @Delete({"<script>", "delete from kongx_user_group_role where",
            "<foreach collection='maps' item='param' index='index' separator=','>",
            " (group_id=#{param.groupId} and role_id=#{param.roleId} and profile=#{param.profile})",
            "</foreach>"
            , "</script>"})
    int remove(@Param("maps") List<Map> maps);
}
