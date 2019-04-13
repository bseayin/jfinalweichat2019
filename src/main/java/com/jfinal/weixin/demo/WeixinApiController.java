package com.jfinal.weixin.demo;

import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.CallbackIpApi;
import com.jfinal.weixin.sdk.api.CustomServiceApi;
import com.jfinal.weixin.sdk.api.MenuApi;
import com.jfinal.weixin.sdk.api.QrcodeApi;
import com.jfinal.weixin.sdk.api.ShorturlApi;
import com.jfinal.weixin.sdk.api.TemplateMsgApi;
import com.jfinal.weixin.sdk.api.UserApi;
import com.jfinal.weixin.sdk.jfinal.ApiController;

public class WeixinApiController extends ApiController {

    /**
     * 为WeixinConfig onLineTokenUrl处提供AccessToken
     * 
     * 此处是为了开发测试和生产环境同时使用一套appId时为开发测试环境提供AccessToken
     * 
     * 设计初衷：https://www.oschina.net/question/2702126_2237352
     */
    public void getToken() {
        String key = getPara("key");
        String json = ApiConfigKit.getAccessTokenCache().get(key);
        renderText(json);
    }
    
    /**
     * 获取公众号菜单
     */
    public void getMenu() {
        ApiResult apiResult = MenuApi.getMenu();
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 创建菜单
     */
    public void createMenu()
    {
//        String str = "{\n" +
//                "    \"button\": [\n" +
//                "        {\n" +
//                "            \"name\": \"扫码\",\n" +
//                "            \"url\": \"http://m.bajie8.com/bajie/enter\",\n" +
//                "            \"type\": \"view\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "            \"name\": \"安全保障\",\n" +
//                "            \"key\": \"112\",\n" +
//                "\t    \"type\": \"click\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "\t    \"name\": \"使用帮助\",\n" +
//                "\t    \"url\": \"http://m.bajie8.com/footer/cjwt\",\n" +
//                "\t    \"type\": \"view\"\n" +
//                "        }\n" +
//                "    ]\n" +
//                "}";
        
        
        String str = "{                                                    "+
        		"    \"button\": [                                    "+
        		"        {                                            "+
        		"            \"name\": \"扫码\",                      "+
        		"            \"sub_button\": [                        "+
        		"                {                                    "+
        		"                    \"type\": \"scancode_waitmsg\",  "+
        		"                    \"name\": \"扫码带提示\",        "+
        		"                    \"key\": \"rselfmenu_0_0\",      "+
        		"                    \"sub_button\": [ ]              "+
        		"                },                                   "+
        		"                {                                    "+
        		"                    \"type\": \"scancode_push\",     "+
        		"                    \"name\": \"扫码推事件\",        "+
        		"                    \"key\": \"rselfmenu_0_1\",      "+
        		"                    \"sub_button\": [ ]              "+
        		"                }                                    "+
        		"            ]                                        "+
        		"        },                                           "+
        		"        {                                            "+
        		"            \"name\": \"发图\",                      "+
        		"            \"sub_button\": [                        "+
        		"                {                                    "+
        		"                    \"type\": \"pic_sysphoto\",      "+
        		"                    \"name\": \"系统拍照发图\",      "+
        		"                    \"key\": \"rselfmenu_1_0\",      "+
        		"                   \"sub_button\": [ ]               "+
        		"                 },                                  "+
        		"                {                                    "+
        		"                    \"type\": \"pic_photo_or_album\","+ 
        		"                    \"name\": \"拍照或者相册发图\",  "+
        		"                    \"key\": \"rselfmenu_1_1\",      "+
        		"                    \"sub_button\": [ ]              "+
        		"                },                                   "+
        		"                {                                    "+
        		"                    \"type\": \"pic_weixin\",        "+
        		"                    \"name\": \"微信相册发图\",      "+
        		"                    \"key\": \"rselfmenu_1_2\",      "+
        		"                    \"sub_button\": [ ]              "+
        		"                }                                    "+
        		"            ]                                        "+
        		"        },                                           "+
        		"        {                                            "+
        		"            \"name\": \"发送位置\",                  "+
        		"            \"type\": \"location_select\",           "+
        		"            \"key\": \"rselfmenu_2_0\"               "+
        		"        }                                         "+
        		"    ]                                                "+
        		"}                                                    ";
        ApiResult apiResult = MenuApi.createMenu(str);
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 获取公众号关注用户
     */
    public void getFollowers()
    {
        ApiResult apiResult = UserApi.getFollows();
        renderText(apiResult.getJson());
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo()
    {
        ApiResult apiResult = UserApi.getUserInfo("oJPy1wDTBhEcY7vZv95WEeV_fZ6c");
        renderText(apiResult.getJson());
    }

    /**
     * 发送模板消息
     */
    public void sendMsg()
    {
        String str = " {\n" +
                "           \"touser\":\"oJPy1wClqxJ_nm40WjcAjSaT0y2o\",\n" +
                "           \"template_id\":\"WQDUD2cAd9Ztgi0Yz_wk1Q2GXjibkQIFw9SzzmTzp-E\",\n" +
                "           \"url\":\"http://www.sina.com\",\n" +
                "           \"topcolor\":\"#FF0000\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"去哪儿网发的酒店红包（1个）\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\"1元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        ApiResult apiResult = TemplateMsgApi.send(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取参数二维码
     */
    public void getQrcode()
    {
        String str = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
        ApiResult apiResult = QrcodeApi.create(str);
        renderText(apiResult.getJson());

//        String str = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"123\"}}}";
//        ApiResult apiResult = QrcodeApi.create(str);
//        renderText(apiResult.getJson());
    }

    /**
     * 长链接转成短链接
     */
    public void getShorturl()
    {
        String str = "{\"action\":\"long2short\"," +
                "\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}";
        ApiResult apiResult = ShorturlApi.getShorturl(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取客服聊天记录
     */
    public void getRecord()
    {
        String str = "{\n" +
                "    \"endtime\" : 987654321,\n" +
                "    \"pageindex\" : 1,\n" +
                "    \"pagesize\" : 10,\n" +
                "    \"starttime\" : 123456789\n" +
                " }";
        ApiResult apiResult = CustomServiceApi.getRecord(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取微信服务器IP地址
     */
    public void getCallbackIp()
    {
        ApiResult apiResult = CallbackIpApi.getCallbackIp();
        renderText(apiResult.getJson());
    }
}

