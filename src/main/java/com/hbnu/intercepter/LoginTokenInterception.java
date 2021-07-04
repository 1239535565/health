package com.hbnu.intercepter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hbnu.annotation.LoginToken;
import com.hbnu.annotation.PassToken;
import com.hbnu.utils.TokenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/*
    这个拦截器是拦截没有登录的用户，当用户每次请求时会带上token，
    如果你没有这个token就说明你没登录，如果token格式不对也不能登录，
    如果请求的控制器方法上标注了@PassToken则表明该请求无需验证登录，直接通过。
 */
public class LoginTokenInterception implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中取出token
        String token = request.getHeader("Authorization");

        //如果不是请求的控制器方法，则直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //转换成handlerMethod，控制器对象，我们写的控制器类都继承handlerMethod
        //获取被请求的控制器方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //如果控制器方法上标注了@PassToken，则放行
        if (method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()){
                return true;
            }
        }

        //检查有没有标注@LoginToken注解，有则进行验证
        if (method.isAnnotationPresent(LoginToken.class)){
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if (loginToken.required()){
                if (token==null){
                    throw new TokenException(403,"用户不存在");
                }
                //验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TokenUtil.SECRET)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new TokenException(403,"token不合法");
                }
            }
        }
        return true;
    }
}
