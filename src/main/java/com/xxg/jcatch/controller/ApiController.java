package com.xxg.jcatch.controller;

import com.xxg.jcatch.api.ApiResult;
import com.xxg.jcatch.mbg.bean.TException;
import com.xxg.jcatch.mbg.mapper.TAppMapper;
import com.xxg.jcatch.service.ExceptionSubmitService;
import org.json.JSONObject;
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
    private TAppMapper appMapper;

    @Autowired
    private ExceptionSubmitService exceptionSubmitService;

    @ResponseBody
    @RequestMapping(value = "/submitExceptionJson", method = RequestMethod.POST)
    public ApiResult submitExceptionJson(@RequestBody String jsonString, HttpServletRequest request, String appId) throws IOException, ClassNotFoundException {

        if(!StringUtils.hasText(appId)) {
            return ApiResult.fail("AppId is required");
        }
        if(appMapper.selectByPrimaryKey(appId) == null) {
            return ApiResult.fail("Unknown appId: " + appId);
        }

        JSONObject jsonObject = new JSONObject(jsonString);

        TException tException = new TException();
        tException.setAppId(appId);
        tException.setRemoteAddr(request.getRemoteAddr());
        if(jsonObject.has("stackTrace")) {
            tException.setStackTrace(jsonObject.getString("stackTrace"));
        }
        if(jsonObject.has("exceptionName")) {
            tException.setExceptionName(jsonObject.getString("exceptionName"));
        }
        if(jsonObject.has("message")) {
            tException.setMessage(jsonObject.getString("message"));
        }
        if(jsonObject.has("className")) {
            tException.setClassName(jsonObject.getString("className"));
        }
        if(jsonObject.has("fileName")) {
            tException.setFileName(jsonObject.getString("fileName"));
        }
        if(jsonObject.has("methodName")) {
            tException.setMethodName(jsonObject.getString("methodName"));
        }
        if(jsonObject.has("lineNumber")) {
            tException.setLineNumber(jsonObject.getInt("lineNumber"));
        }
        exceptionSubmitService.submit(tException);

        return ApiResult.success();
    }
}
