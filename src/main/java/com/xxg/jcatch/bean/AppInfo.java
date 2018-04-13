package com.xxg.jcatch.bean;

/**
 * Created by wucao on 2018/4/13.
 */
public class AppInfo {

    private String id;
    private String name;
    private String description;
    private String secretKey;
    private String subscriber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }
}
