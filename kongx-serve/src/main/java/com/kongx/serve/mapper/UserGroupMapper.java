package com.kongx.serve.mapper;

import com.kongx.common.core.entity.UserInfo;
import com.github.pagehelper.Page;
import com.kongx.serve.entity.system.ProfileRole;
import com.kongx.serve.entity.system.SystemRole;
import com.kongx.serve.entity.system.UserGroup;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserGroupMapper {
    @Select("SELECT * FROM kongx_user_group")
    @Results({
            @Result(property = "useYn", column = "use_yn")
    })
    List<UserGroup> findAllGroup();

    @Select({"<script>", "SELECT t.*,t.id group_user_id ,t.id group_role_id FROM kongx_user_group t where 1=1 ",
            "<when test='job.name!=null'>",
            " and t.name like CONCAT('%',#{job.name},'%')",
            "</when>",
            "order by create_at desc", "</script>"})
    @Results({
            @Result(property = "useYn", column = "use_yn"),
            @Result(column = "group_user_id", property = "userList",
                    many = @Many(select = "com.kongx.serve.mapper.UserGroupMapper.findUserByGroupId", fetchType = FetchType.LAZY)),
            @Result(column = "group_role_id", property = "profileRoles",
                    many = @Many(select = "com.kongx.serve.mapper.UserGroupMapper.findRoleByGroupId", fetchType = FetchType.LAZY))
    })
    Page<UserGroup> findByPage(@Param("pageNum") int pageNum,
                               @Param("pageSize") int pageSize, @Param("job") UserGroup project);


    @Select("SELECT profile,group_id FROM kongx_user_group_role where group_id=#{groupId}")
    @Results({
            @Result(column = "{groupId = group_id,profile = profile}", property = "roleList",
                    many = @Many(select = "com.kongx.serve.mapper.UserGroupMapper.findRoleByProfile", fetchType = FetchType.LAZY))
    })
    List<ProfileRole> findRoleByGroupId(@Param("groupId") int groupId);

    @Select("select * from kongx_system_role a where exists (select t.role_id from kongx_user_group_role t where t.role_id=a.id and t.group_id=#{groupId} and t.profile=#{profile})")
    List<SystemRole> findRoleByProfile(@Param("groupId") int groupId, @Param("profile") String profile);

    @Select({"select * from kongx_user_info a where exists (select t.user_id from kongx_user_group_user t where t.user_id=a.user_id and t.group_id=#{groupId})"})
    @Results({
            @Result(property = "userId", column = "user_id"),
    })
    List<UserInfo> findUserByGroupId(@Param("groupId") int groupId);

    @Insert({"insert into kongx_user_group(name,use_yn,remark,creator,create_at) values (",
            "#{name},",
            "#{useYn},",
            "#{remark},",
            "#{creator}, #{create_at, jdbcType=TIMESTAMP}",
            ")"})
    int insert(UserGroup userGroup);

    @Update({"update kongx_user_group set ",
            "name=#{name},",
            "use_yn=#{useYn},",
            "modifier=#{modifier},modify_at=#{modify_at, jdbcType=TIMESTAMP},",
            "remark=#{remark} where id=#{id} "})
    int update(UserGroup userGroup);

    @Select({"select * from kongx_user_group  where id = #{id} "})
    @Results({
            @Result(property = "useYn", column = "use_yn")
    })
    UserGroup findById(int id);
}
