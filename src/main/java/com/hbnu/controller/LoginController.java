package com.hbnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.hbnu.entity.User;
import com.hbnu.service.UserService;
import com.hbnu.utils.MD5Util;
import com.hbnu.utils.Result;
import com.hbnu.utils.ResultGenerator;
import com.hbnu.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@RestController
@RequestMapping("/adminApi/common")
public class LoginController {
    @Autowired
    private DefaultKaptcha captchaProducer;

    @Autowired
    private UserService userService;

    /*
        登录
     */
    @PostMapping("/login")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult("用户名或密码不能为空");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", MD5Util.MD5Encode(password, "utf-8"));
        queryWrapper.allEq(params);
        User user = userService.getOne(queryWrapper);

        if (ObjectUtils.isNotNull(user)) {
            HashMap<String, Object> obj = new HashMap<>();
            obj.put("userInfo",user);
            obj.put("token", TokenUtil.getAdminToken(user));
            return ResultGenerator.genSuccessResult(obj);
        } else {
            return ResultGenerator.genFailResult("用户不存在");
        }
    }

    //outLogin，登出
    @GetMapping("/outLogin")
    @ResponseBody
    public String outLogin() {
        return "";
    }

    //getUserInfo，获取信息
    @GetMapping("/getUserInfo")
    public Object getUserInfo(HttpServletRequest request) {
        User user = userService.getById(TokenUtil.getAdminUserId(request.getHeader("Authorization")));
        if (user == null) {
            return ResultGenerator.genFailResult("账号不存在,请检查账号是否正确或联系管理员");
        } else {
            HashMap<String, Object> obj = new HashMap<>();
            obj.put("userInfo", user);
            obj.put("token", TokenUtil.getAdminToken(user));
            return ResultGenerator.genSuccessResult(obj);
        }
    }

    //registerPatients，注册病人，给参数
    @PostMapping("/registerPatients")
    @ResponseBody
    public String registerPatients() {
        return "";
    }

    //registerDoctor，注册医生,给参数
    @PostMapping("/registerDoctor")
    @ResponseBody
    public String registerDoctor() {
        return "";
    }

    //forgotPassword，忘记密码，给参数
    @PutMapping("/forgotPassword")
    @ResponseBody
    public String forgotPassword() {
        return "";
    }


    //生成验证码
    /*@GetMapping("/login/kaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        byte[] captchaOutputStream = null;
        ByteArrayOutputStream imgOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String verifyCode = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("verifyCode", verifyCode);
            BufferedImage challenge = captchaProducer.createImage(verifyCode);
            ImageIO.write(challenge, "jpg", imgOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        captchaOutputStream = imgOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }*/
}
