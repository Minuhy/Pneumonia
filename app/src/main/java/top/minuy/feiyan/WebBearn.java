package top.minuy.feiyan;

public class WebBearn {
    String title;
    String url;

    public WebBearn(){}

    public WebBearn(String title,String url){
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
