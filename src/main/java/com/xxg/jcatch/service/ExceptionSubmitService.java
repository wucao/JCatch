package com.xxg.jcatch.service;

import com.xxg.jcatch.mapper.ExceptionMapper;
import com.xxg.jcatch.mbg.bean.TException;
import com.xxg.jcatch.mbg.bean.TExceptionExample;
import com.xxg.jcatch.mbg.mapper.TExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wucao on 17/5/9.
 */
@Service
public class ExceptionSubmitService {

    @Autowired
    private TExceptionMapper mbgExceptionMapper;

    @Autowired
    private ExceptionMapper exceptionMapper;

    public void submit(TException exception) {

        TExceptionExample example = new TExceptionExample();
        TExceptionExample.Criteria criteria = example.or().andAppIdEqualTo(exception.getAppId());
        if(exception.getClassName() != null) {
            criteria.andClassNameEqualTo(exception.getClassName());
        } else {
            criteria.andClassNameIsNull();
        }
        if(exception.getExceptionName() != null) {
            criteria.andExceptionNameEqualTo(exception.getExceptionName());
        } else {
            criteria.andExceptionNameIsNull();
        }
        if(exception.getFileName() != null) {
            criteria.andFileNameEqualTo(exception.getFileName());
        } else {
            criteria.andFileNameIsNull();
        }
        if(exception.getLineNumber() != null) {
            criteria.andLineNumberEqualTo(exception.getLineNumber());
        } else {
            criteria.andLineNumberIsNull();
        }
        if(exception.getRemoteAddr() != null) {
            criteria.andRemoteAddrEqualTo(exception.getRemoteAddr());
        } else {
            criteria.andRemoteAddrIsNull();
        }
        if(exception.getMessage() != null) {
            criteria.andMessageEqualTo(exception.getMessage());
        } else {
            criteria.andMessageIsNull();
        }
        if(exception.getStackTrace() != null) {
            criteria.andStackTraceEqualTo(exception.getStackTrace());
        } else {
            criteria.andStackTraceIsNull();
        }
        if(exception.getMethodName() != null) {
            criteria.andMethodNameEqualTo(exception.getMethodName());
        } else {
            criteria.andMethodNameIsNull();
        }
        example.setLimit(1);

        List<TException> list = mbgExceptionMapper.selectByExample(example);

        if(list.size() > 0) {
            int id = list.get(0).getId();
            exceptionMapper.submitExistentException(id);
        } else {
            mbgExceptionMapper.insertSelective(exception);
        }
    }

}
