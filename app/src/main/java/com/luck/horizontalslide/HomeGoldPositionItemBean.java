package com.luck.horizontalslide;


//首页金刚位，每个item的数据
public class HomeGoldPositionItemBean {
    public String image;
    public String text;
    public String scheme;

    public HomeGoldPositionItemBean(String image, String text) {
        this.image = image;
        this.text = text;
    }

    public HomeGoldPositionItemBean() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
