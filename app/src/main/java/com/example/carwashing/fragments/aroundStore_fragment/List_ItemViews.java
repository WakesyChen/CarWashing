package com.example.carwashing.fragments.aroundStore_fragment;

/**
 * Created by Wakesy on 2016/5/22.
 */

/*用于存放ListView的Item中各个控件
* */
public class List_ItemViews {
    String ItemStore;//门店名
    int ItemImg;//门店图片
    String ItemAddress;//门店地址
    float ItemStars;//获得星数
    String ItemScore;//获得分数
    String ItemComments;//评论次数
    String ItemDistance;//距离
    String ItemType;//洗车类型

    public List_ItemViews(){

    }
    public String getItemStore() {
        return ItemStore;
    }

    public void setItemStore(String itemStore) {
        ItemStore = itemStore;
    }

    public int getItemImg() {
        return ItemImg;
    }

    public void setItemImg(int itemImg) {
        ItemImg = itemImg;
    }

    public String getItemAddress() {
        return ItemAddress;
    }

    public void setItemAddress(String itemAddress) {
        ItemAddress = itemAddress;
    }

    public float getItemStars() {
        return ItemStars;
    }

    public void setItemStars(float itemStars) {
        ItemStars = itemStars;
    }

    public String getItemScore() {
        return ItemScore;
    }

    public void setItemScore(String itemScore) {
        ItemScore = itemScore;
    }

    public String getItemComments() {
        return ItemComments;
    }

    public void setItemComments(String itemComments) {
        ItemComments = itemComments;
    }

    public String getItemDistance() {
        return ItemDistance;
    }

    public void setItemDistance(String itemDistance) {
        ItemDistance = itemDistance;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    String ItemPrice;//洗车价格

    public List_ItemViews(String itemStore, int itemImg, String itemAddress, float itemStars, String itemScore,
                          String itemComments, String itemDistance, String itemType, String itemPrice) {
        ItemStore = itemStore;
        ItemImg = itemImg;
        ItemAddress = itemAddress;
        ItemStars = itemStars;
        ItemScore = itemScore;
        ItemComments = itemComments;
        ItemDistance = itemDistance;
        ItemType = itemType;
        ItemPrice = itemPrice;
    }

}
