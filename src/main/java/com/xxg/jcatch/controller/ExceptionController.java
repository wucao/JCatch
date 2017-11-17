package com.xxg.jcatch.controller;

import com.xxg.jcatch.exception.ResourceNotFoundException;
import com.xxg.jcatch.mbg.bean.TException;
import com.xxg.jcatch.mbg.bean.TExceptionExample;
import com.xxg.jcatch.mbg.mapper.TAppMapper;
import com.xxg.jcatch.mbg.mapper.TExceptionMapper;
import com.xxg.jcatch.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wucao on 17/3/15.
 */
@Controller
@RequestMapping("/exception")
public class ExceptionController {

    private static final long PAGE_SIZE = 15;

    @Autowired
    private TExceptionMapper exceptionMapper;

    @Autowired
    private TAppMapper appMapper;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(TException exception,
                              @RequestParam(value = "startDate", required = false) String startDate,
                              @RequestParam(value = "endDate", required = false) String endDate,
                              @RequestParam(value = "p", required = false, defaultValue = "1") long p) throws ParseException {

        String appId = exception.getAppId();
        if(!StringUtils.hasText(appId)) {
            throw new ResourceNotFoundException();
        }
        if(appMapper.selectByPrimaryKey(appId) == null) {
            throw new ResourceNotFoundException();
        }

        TExceptionExample example = new TExceptionExample();
        TExceptionExample.Criteria criteria = example.createCriteria();
        criteria.andAppIdEqualTo(appId);
        if(StringUtils.hasText(exception.getRemoteAddr())) {
            criteria.andRemoteAddrEqualTo(exception.getRemoteAddr());
        }
        if(StringUtils.hasText(exception.getExceptionName())) {
            criteria.andExceptionNameLike('%' + exception.getExceptionName() + '%');
        }
        if(StringUtils.hasText(exception.getMessage())) {
            criteria.andMessageLike('%' + exception.getMessage() + '%');
        }
        if(StringUtils.hasText(exception.getClassName())) {
            criteria.andClassNameLike('%' + exception.getClassName() + '%');
        }
        if(StringUtils.hasText(exception.getMethodName())) {
            criteria.andMethodNameLike('%' + exception.getMethodName() + '%');
        }
        if(StringUtils.hasText(exception.getFileName())) {
            criteria.andFileNameLike('%' + exception.getFileName() + '%');
        }
        if(exception.getLineNumber() != null) {
            criteria.andLineNumberEqualTo(exception.getLineNumber());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.hasText(startDate)) {
            criteria.andCreateTimeGreaterThanOrEqualTo(simpleDateFormat.parse(startDate));
        }
        if(StringUtils.hasText(endDate)) {
            criteria.andCreateTimeLessThanOrEqualTo(simpleDateFormat.parse(endDate));
        }

        Long count = exceptionMapper.countByExample(example);
        long pageCount = PageUtil.getPageCount(count, PAGE_SIZE);
        if(p < 1) {
            p = 1;
        }
        if(p > pageCount) {
            p = pageCount;
        }
        List<Long> pageList = PageUtil.generatePageList(p, pageCount);

        example.setOrderByClause("last_submit_time desc");
        example.setLimit((int) PAGE_SIZE);
        example.setOffset(((int) p - 1) * (int) PAGE_SIZE);

        List<TException> list = exceptionMapper.selectByExample(example);

        ModelAndView mv = new ModelAndView("exceptionList");
        mv.addObject("page", p);
        mv.addObject("pageCount", pageCount);
        mv.addObject("pageList", pageList);
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView detail(int id) {
        TException exception = exceptionMapper.selectByPrimaryKey(id);
        if(exception == null) {
            throw new ResourceNotFoundException();
        }

        ModelAndView mv = new ModelAndView("exceptionDetail");
        mv.addObject("detail", exception);
        return mv;
    }

}
