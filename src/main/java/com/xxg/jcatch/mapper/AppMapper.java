package com.xxg.jcatch.mapper;

import com.xxg.jcatch.bean.AppInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by wucao on 2018/4/13.
 */
public interface AppMapper {

    @Select("select id,name,description,secret_key as secretKey,subscriber from t_app where id=#{id}")
    AppInfo selectByPrimaryKey(String id);

    @Select("select id,name,description,secret_key as secretKey,subscriber from t_app")
    List<AppInfo> selectAll();

    @Update("update t_app set name=#{name},description=#{description},subscriber=#{subscriber} where id=#{id}")
    void update(AppInfo app);

    @Insert("insert into t_app (id,name,description,subscriber,secret_key) values (#{id},#{name},#{description},#{subscriber},#{secretKey})")
    void insert(AppInfo app);
}
