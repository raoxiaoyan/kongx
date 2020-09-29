package com.kongx.serve.mapper;

import com.kongx.common.core.entity.UserInfo;
import com.github.pagehelper.Page;
import com.kongx.serve.entity.system.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Select({"select user_id,name,mobile,status from kongx_user_info"})
    @Results({
            @Result(property = "userId", column = "user_id"),
    })
    List<UserInfo> findAllUser();

    @Select({"<script>", "select t.user_id,t.name,t.mobile,t.status,t.email,t.creator,t.create_at,t.user_id uid from kongx_user_info t where 1=1 ",
            "<when test='job.name!=null'>",
            " and (name like CONCAT('%',#{job.name},'%') or user_id=#{job.name})",
            "</when>",
            "</script>"})
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userGroupList", column = "uid", many = @Many(select = "com.kongx.serve.mapper.UserInfoMapper.findGroupByUserId", fetchType = FetchType.LAZY))
    })
    Page<UserInfoVO> findByPage(@Param("pageNum") int pageNum,
                                @Param("pageSize") int pageSize, @Param("job") UserInfo userInfo);

    @Select("select * from kongx_user_group t where exists(select * from kongx_user_group_user b where  t.id=b.group_id and user_id=#{userId}) ")
    List<UserGroup> findGroupByUserId(String userId);

    @Update({"insert into kongx_user_info(user_id,password,name,email,mobile,status,creator,create_at) ",
            "values(#{userId} ,#{password},#{name} ,#{email} ,#{mobile},#{status},#{creator}, #{create_at, jdbcType=TIMESTAMP} )"})
    int insertUser(UserInfo userInfo);

    @Update({"update kongx_user_info set name=#{name} ,email=#{email} ,mobile=#{mobile} where user_id=#{userId} "})
    int updateUser(UserInfo userInfo);

    @Update({"update kongx_user_info set password=#{pwd}  where user_id=#{userId}"})
    int resetpwd(@Param("pwd") String pwd, @Param("userId") String userId);

    @Update({"update kongx_user_info set status=#{status}  where user_id=#{userId}"})
    int status(@Param("status") String status, @Param("userId") String userId);

    @Select({"select * from kongx_user_info where user_id=#{userId}"})
    @Results({
            @Result(property = "userId", column = "user_id"),
    })
    UserInfoVO findById(String userId);


    @Delete("delete from kongx_system_user_role where user_id=#{userId}")
    int deleteUserRole(String userId);

    @Insert({"<script>",
            "insert into kongx_system_user_role(role_id,user_id) values",
            "<foreach collection='roles' item='param' index='index' separator=','>",
            " (#{param.roleId}, #{param.userId} )",
            "</foreach>"
            , "</script>"})
    int batchInsertRole(@Param("roles") List<SystemUserRole> roles);

    @Select({"<script>", "SELECT DISTINCT",
            "b.id,b.parent_id parentId,",
            "  b.NAME label,",
            "b.menu_icon icon,",
            "b.visit_view component,",
            "b.code code,",
            "b.visit_path path",
            " FROM",
            " kongx_system_role_function a,",
            " kongx_system_function b",
            " WHERE",
            " b.menu_type=#{menuType} and a.function_id = b.id ",
            " and exists (" +
                    "select role_id from kongx_user_group_role ur where a.role_id = ur.role_id and  ur.`profile`=#{profile.profile} and  exists(" +
                    "select t.id from kongx_user_group_user t where ur.group_id=t.group_id and t.user_id=#{userId})",
            ") ORDER BY b.sort_order", "</script>"})
    List<Menu> findMenuByUserId(@Param("userId") String userId, @Param("menuType") String menuType, @Param("profile") SystemProfile systemProfile);

    @Select("select count(role_id) from kongx_user_group_role ur where  ur.`profile`=#{profile.profile} and  exists(select t.id from kongx_user_group_user t where ur.group_id=t.group_id and t.user_id=#{userId})")
    int countAuthorityRole(String userId, @Param("profile") SystemProfile systemProfile);

    @Select({"<script>", "select c.id id, c.code code, #{menuType} menuType,c.parent_id parentId,c.name label,c.menu_icon icon,c.visit_view component,c.visit_path path ",
            "from kongx_system_function c,kongx_system_role_function a,kongx_system_role b where a.function_id=c.id and  a.role_id=b.id and b.code=#{roleCode}",
            "and c.menu_type=#{menuType}", " ORDER BY c.sort_order", "</script>"}
    )
    List<Menu> findMenuByRoleCode(@Param("roleCode") String code, @Param("menuType") String menuType);
}
