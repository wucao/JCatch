package com.xxg.jcatch.mapper;

import org.apache.ibatis.annotations.Update;

/**
 * Created by wucao on 17/5/9.
 */
public interface ExceptionMapper {

    @Update("update t_exception set last_submit_time=now(),occurrence_number=occurrence_number+1 where id=#{id}")
    void submitExistentException(int id);

}
