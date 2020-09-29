package com.kongx.serve.mapper;

import com.kongx.serve.entity.system.SystemFunction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FunctionMapper {
    @Select("SELECT *,id as fid FROM kongx_system_function   order by sort_order")
    @Results({
            @Result(property = "menuType", column = "menu_type"),
            @Result(property = "menuIcon", column = "menu_icon"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "visitView", column = "visit_view"),
            @Result(property = "visitPath", column = "visit_path"),
            @Result(property = "sortOrder", column = "sort_order"),
            @Result(property = "useYn", column = "use_yn"),
    })
    List<SystemFunction> findFunctionByTree(@Param("parentId") int parentId);

    @Select("SELECT * FROM kongx_system_function where parent_id=#{parentId} order by sort_order")
    @Results({
            @Result(property = "menuType", column = "menu_type"),
            @Result(property = "menuIcon", column = "menu_icon"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "visitView", column = "visit_view"),
            @Result(property = "visitPath", column = "visit_path"),
            @Result(property = "sortOrder", column = "sort_order"),
            @Result(property = "useYn", column = "use_yn"),
    })
    List<SystemFunction> findAllPoint(int parentId);

    @Insert({"insert into kongx_system_function(parent_id,name,code,sort_order,use_yn,menu_icon,visit_view,visit_path,menu_type) values (",
            "#{parentId},#{name},#{code},",
            "#{sortOrder},#{useYn},#{menuIcon},",
            "#{visitView}, #{visitPath}, #{menuType}",
            ")"})
    int insert(SystemFunction project);

    @Update({"update kongx_system_function set " +
            "name=#{name},menu_type=#{menuType},code=#{code},sort_order=#{sortOrder},use_yn=#{useYn},",
            "menu_icon=#{menuIcon},visit_view=#{visitView},",
            "visit_path=#{visitPath} where id=#{id} "})
    int update(SystemFunction project);

    @Select({"select * from kongx_system_function  where id = #{id} "})
    @Results({
            @Result(property = "menuType", column = "menu_type"),
            @Result(property = "menuIcon", column = "menu_icon"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "visitView", column = "visit_view"),
            @Result(property = "visitPath", column = "visit_path"),
            @Result(property = "sortOrder", column = "sort_order"),
            @Result(property = "useYn", column = "use_yn"),
    })
    SystemFunction findById(int id);
}
