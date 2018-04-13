package com.xxg.jcatch.mapper;

import com.xxg.jcatch.bean.ExceptionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by wucao on 17/5/9.
 */
public interface ExceptionMapper {

    @Update("update t_exception set last_submit_time=now(),occurrence_number=occurrence_number+1 where id=#{id}")
    void submitExistentException(int id);

    Integer getExistentExceptionId(ExceptionInfo exceptionInfo);

    @Insert("insert into t_exception (app_id,remote_addr,exception_name,message,stack_trace,class_name,method_name,file_name,line_number) " +
            "values (#{appId},#{remoteAddr},#{exceptionName},#{message},#{stackTrace},#{className},#{methodName},#{fileName},#{lineNumber})")
    void insert(ExceptionInfo exceptionInfo);

    @Select("select id,app_id as appId,remote_addr as remoteAddr,exception_name as exceptionName,message,stack_trace as stackTrace,class_name as className,method_name as methodName,file_name as fileName,line_number as lineNumber")
    ExceptionInfo selectByPrimaryKey(int id);
}
