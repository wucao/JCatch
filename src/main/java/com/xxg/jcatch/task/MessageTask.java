package com.xxg.jcatch.task;

import com.xxg.jcatch.mbg.bean.TApp;
import com.xxg.jcatch.mbg.bean.TAppExample;
import com.xxg.jcatch.mbg.bean.TException;
import com.xxg.jcatch.mbg.bean.TExceptionExample;
import com.xxg.jcatch.mbg.mapper.TAppMapper;
import com.xxg.jcatch.mbg.mapper.TExceptionMapper;
import com.xxg.jcatch.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wucao on 2017/11/16.
 */
public class MessageTask {

    @Autowired
    private TAppMapper mbgAppMapper;

    @Autowired
    private TExceptionMapper mbgExceptionMapper;

    private MessageService messageService;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Scheduled(cron = "0 5 * * * ?")
    public void service() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date end = calendar.getTime();
        calendar.add(Calendar.HOUR, -1);
        Date start = calendar.getTime();

        TAppExample appExample = new TAppExample();
        appExample.or().andSubscriberIsNotNull();
        List<TApp> appList = mbgAppMapper.selectByExample(appExample);

        for(TApp app : appList) {
            if(StringUtils.hasText(app.getSubscriber())) {

                TExceptionExample exceptionExample = new TExceptionExample();
                exceptionExample.or().andAppIdEqualTo(app.getId()).andLastSubmitTimeBetween(start, end);
                List<TException> exceptionList = mbgExceptionMapper.selectByExample(exceptionExample);

                if(exceptionList.size() > 0) {
                    messageService.sendMessage(app, exceptionList, start, end);
                }
            }
        }

    }

}
