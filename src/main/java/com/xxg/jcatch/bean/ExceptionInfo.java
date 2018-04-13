package com.xxg.jcatch.bean;

import java.util.Date;

/**
 * Created by wucao on 2018/4/13.
 */
public class ExceptionInfo {

    private int id;
    private String appId;
    private String remoteAddr;
    private String exceptionName;
    private String message;
    private String stackTrace;
    private String className;
    private String methodName;
    private String fileName;
    private int lineNumber;
    private int occurrenceNumber;
    private Date createTime;
    private Date lastSubmitTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getOccurrenceNumber() {
        return occurrenceNumber;
    }

    public void setOccurrenceNumber(int occurrenceNumber) {
        this.occurrenceNumber = occurrenceNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastSubmitTime() {
        return lastSubmitTime;
    }

    public void setLastSubmitTime(Date lastSubmitTime) {
        this.lastSubmitTime = lastSubmitTime;
    }
}
