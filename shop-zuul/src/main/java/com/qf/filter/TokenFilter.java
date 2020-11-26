package com.qf.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.qf.entity.ResultEntity;
import com.qf.service.ZuulManagerServiceImpl;
import com.qf.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @auth qk
 * @date 2020-09-14
 */
@Component
@Slf4j
public class TokenFilter extends ZuulFilter {

    @Autowired
    private ZuulManagerServiceImpl zuulManagerService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //用户访问的服务
        StringBuffer requestURL = request.getRequestURL();
        log.info("requestURL："+requestURL);

        // 获取用户的token
        String token = request.getHeader("Authorization");
        log.info("token:"+token);
        if(StringUtils.isEmpty(token)){
            token=request.getParameter("token");
        }

        //这个服务是否需要验证，从数据库或redis中查询
        if (zuulManagerService.isRequre(requestURL.toString())){

            // 校验
            ResultEntity resultEntity = JWTUtils.require(token);
            log.info("resultEntity:"+resultEntity);
            if(!ResultEntity.SUCEESS.equals(resultEntity.getStatus())){

                // token验证失败，服务不能往下转发
                resultEntity.setMsg("你访问的服务必须要认证才可以。");
                requestContext.setSendZuulResponse(false); // 不能往下走了
                requestContext.getResponse().setContentType("application/json;charset=utf-8"); // 设置响应的数据类型
                requestContext.setResponseBody(JSON.toJSONString(resultEntity));

            }
        }
        return null;
    }
}
