package com.xxg.jcatch.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxg.jcatch.api.ApiResult;
import com.xxg.jcatch.bean.ExceptionInfo;
import com.xxg.jcatch.mapper.AppMapper;
import com.xxg.jcatch.service.ExceptionSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * Created by wucao on 17/3/14.
 */
@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ExceptionSubmitService exceptionSubmitService;

    @Autowired
    private AppMapper appMapper;

    @ResponseBody
    @RequestMapping(value = "/submitExceptionJson", method = RequestMethod.POST)
    public ApiResult submitExceptionJson(@RequestBody String jsonString, HttpServletRequest request, String appId) throws IOException, ClassNotFoundException {

        if(!StringUtils.hasText(appId)) {
            return ApiResult.fail("AppId is required");
        }
        if(appMapper.selectByPrimaryKey(appId) == null) {
            return ApiResult.fail("Unknown appId: " + appId);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ExceptionInfo exceptionInfo = mapper.readValue(jsonString, ExceptionInfo.class);
        exceptionInfo.setAppId(appId);
        exceptionInfo.setRemoteAddr(request.getRemoteAddr());
        exceptionSubmitService.submit(exceptionInfo);

        return ApiResult.success();
    }
}
