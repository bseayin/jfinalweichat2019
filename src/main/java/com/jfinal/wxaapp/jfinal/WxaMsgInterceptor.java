/**
 * Copyright (c) 2011-2014, L.cm 卢春梦 (qq596392912@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.wxaapp.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.jfinal.MsgInterceptor;
import com.jfinal.weixin.sdk.kit.SignatureCheckKit;
import com.jfinal.wxaapp.WxaConfig;
import com.jfinal.wxaapp.WxaConfigKit;

/**
 * 小程序消息拦截器
 * @author L.cm
 *
 */
public class WxaMsgInterceptor implements Interceptor {
    private static final Log log = Log.getLog(MsgInterceptor.class);

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        if (!(controller instanceof MsgController)) {
            throw new RuntimeException("控制器需要继承 MsgController");
        }
        // 获取配置
        WxaConfig wxaConfig = WxaConfigKit.getWxaConfig();
        String token = wxaConfig.getToken();
        // 如果是服务器配置请求，则配置服务器并返回
        if (isConfigServerRequest(controller)) {
            configServer(controller, token);
            return;
        }

        // 对开发测试更加友好
        if (ApiConfigKit.isDevMode()) {
            inv.invoke();
        } else {
            // 签名检测
            if (checkSignature(controller, token)) {
                inv.invoke();
            } else {
                controller.renderText("签名验证失败，请确定是微信服务器在发送消息过来");
            }
        }
    }

    /**
     * 检测签名
     */
    private boolean checkSignature(Controller controller, String token) {
        String signature = controller.getPara("signature");
        String timestamp = controller.getPara("timestamp");
        String nonce = controller.getPara("nonce");
        if (StrKit.isBlank(signature) || StrKit.isBlank(timestamp) || StrKit.isBlank(nonce)) {
            controller.renderText("check signature failure");
            return false;
        }

        if (SignatureCheckKit.me.checkSignature(signature, token, timestamp, nonce)) {
            return true;
        } else {
            log.error("check signature failure: " +
                " signature = " + controller.getPara("signature") +
                " timestamp = " + controller.getPara("timestamp") +
                " nonce = " + controller.getPara("nonce"));

            return false;
        }
    }

    /**
     * 是否为开发者中心保存服务器配置的请求
     */
    private boolean isConfigServerRequest(Controller controller) {
        return StrKit.notBlank(controller.getPara("echostr"));
    }

    /**
     * 配置开发者中心微信服务器所需的 url 与 token
     *
     * @param c 控制器
     */
    public void configServer(Controller c, String token) {
        // 通过 echostr 判断请求是否为配置微信服务器回调所需的 url 与 token
        String echostr = c.getPara("echostr");
        String signature = c.getPara("signature");
        String timestamp = c.getPara("timestamp");
        String nonce = c.getPara("nonce");
        boolean isOk = SignatureCheckKit.me.checkSignature(signature, token, timestamp, nonce);
        if (isOk) {
            c.renderText(echostr);
        } else {
            log.error("验证失败：configServer");
        }
    }

}
