package com.xxg.jcatch.message;

import com.xxg.jcatch.mbg.bean.TApp;
import com.xxg.jcatch.mbg.bean.TException;

import java.util.Date;
import java.util.List;

/**
 * Created by wucao on 2017/11/16.
 */
public interface MessageService {

    void sendMessage(TApp app, List<TException> exceptionList, Date start, Date end) throws Exception;

}
