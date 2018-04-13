package com.xxg.jcatch.controller;

import com.xxg.jcatch.bean.AppInfo;
import com.xxg.jcatch.mapper.AppMapper;
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
    private AppMapper appMapper;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {

        List<AppInfo> list = appMapper.selectAll();

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
    public String createOrEdit(AppInfo app) {

        if(StringUtils.hasText(app.getId())) {
            app.setSecretKey(null);
            appMapper.update(app);
        } else {
            String id = UUID.randomUUID().toString().replace("-", "");
            String secretKey = UUID.randomUUID().toString().replace("-", "");
            app.setId(id);
            app.setSecretKey(secretKey);
            appMapper.insert(app);
        }

        return "redirect:/app/index";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String id) {

        AppInfo app = appMapper.selectByPrimaryKey(id);

        ModelAndView mv = new ModelAndView("appCreateOrEdit");
        mv.addObject("app", app);

        return mv;
    }
}
