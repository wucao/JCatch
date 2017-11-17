package com.xxg.jcatch.controller;

import com.xxg.jcatch.mbg.bean.TApp;
import com.xxg.jcatch.mbg.bean.TAppExample;
import com.xxg.jcatch.mbg.mapper.TAppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

/**
 * Created by wucao on 17/3/15.
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private TAppMapper appMapper;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {

        List<TApp> list = appMapper.selectByExample(new TAppExample());

        ModelAndView mv = new ModelAndView("appList");
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView mv = new ModelAndView("appCreateOrEdit");
        return mv;
    }

    @RequestMapping(value = "/createOrEdit", method = RequestMethod.POST)
    public String createOrEdit(TApp app) {

        if(StringUtils.hasText(app.getId())) {
            app.setSecretKey(null);
            appMapper.updateByPrimaryKeySelective(app);
        } else {
            String id = UUID.randomUUID().toString().replace("-", "");
            String secretKey = UUID.randomUUID().toString().replace("-", "");
            app.setId(id);
            app.setSecretKey(secretKey);
            appMapper.insertSelective(app);
        }

        return "redirect:/app/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {

        TApp app = appMapper.selectByPrimaryKey(id);

        ModelAndView mv = new ModelAndView("appCreateOrEdit");
        mv.addObject("app", app);

        return mv;
    }
}
