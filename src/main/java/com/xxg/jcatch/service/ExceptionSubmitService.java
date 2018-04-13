package com.xxg.jcatch.service;

import com.xxg.jcatch.bean.ExceptionInfo;
import com.xxg.jcatch.mapper.ExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wucao on 17/5/9.
 */
@Service
public class ExceptionSubmitService {

    @Autowired
    private ExceptionMapper exceptionMapper;

    public void submit(ExceptionInfo exceptionInfo) {

        Integer id = exceptionMapper.getExistentExceptionId(exceptionInfo);

        if(id != null) {
            exceptionMapper.submitExistentException(id);
        } else {
            exceptionMapper.insert(exceptionInfo);
        }
    }

}
