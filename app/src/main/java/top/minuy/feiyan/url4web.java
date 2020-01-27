package top.minuy.feiyan;

import java.util.HashMap;
import java.util.Map;

public class url4web {
    private url4web (){}

    private void newUrl() {
        url = new HashMap<String, WebBearn>();
        url.put("1",new WebBearn("腾讯","https://news.qq.com/zt2020/page/feiyan.htm"));
        url.put("2",new WebBearn("丁香","https://3g.dxy.cn/newh5/view/pneumonia"));
        url.put("3",new WebBearn("网易","http://news.163.com/special/epidemic"));
        url.put("4",new WebBearn("夸克","https://broccoli.uc.cn/apps/pneumonia/routes/index"));
        url.put("5",new WebBearn("百度","https://voice.baidu.com/act/newpneumonia/newpneumonia"));
      }

    static private url4web u4;
    static public url4web getU4(){
        if(u4 == null){
            u4 = new url4web();
        }
        return u4;
    }

    Map<String,WebBearn> url;

    public Map<String,WebBearn> getUrl() {
        if(url == null){
            newUrl();
        }
        return url;
    }

    public void setUrl(Map<String, WebBearn> url) {
        this.url = url;
    }

    public static void setU4(url4web u4) {
        if(u4!=null) {
            url4web.u4 = u4;
        }
    }
}
