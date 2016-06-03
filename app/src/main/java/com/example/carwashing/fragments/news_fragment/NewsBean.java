package com.example.carwashing.fragments.news_fragment;

/**
 * Created by Wakesy on 2016/5/29.
 */
public class NewsBean {

/*
    public String NewsIconUrl;
    public String NewsTitle;
    public String NewsContent;*/
    /*
    * {
    "showapi_res_code": 0,
    "showapi_res_error": "",
    "showapi_res_body": {
        "pagebean": {
            "allNum": 54188,
            "allPages": 2710,
            "contentlist": [
                {
                    "channelId": "5572a109b3cdc86cf39001db",
                    "channelName": "国内最新",
                    "content": "中新网成都4月28日电(记者 张浪)四川省纪委28日向社会公开曝光5起“两个责任”问责追究典型案例，持续强化警示和震",
                    "desc": "四川省纪委28日向社会公开曝光5起“两个责任”问责追究典型案例，持续强化警示和震慑。",
                    "html": "<p>中新“九阵系统”违规向住院患者收取费用，内儿科主任夏某、外妇科副主任彭某分别受到党内严重警告和党内警告处分。",
                    "imageurls": [],
                    "link": "http://m.cankaoxiaoxi.com/china/20160428/1143639.shtml",
                    "nid": "6168178246936511522",
                    "pubDate": "2016-04-28 15:30:09",
                    "sentiment_display": 0,
                    "source": "手机参考消息网",
                    "title": "四川纪委曝光5起典型案例 有人挪用百万公款网络赌博"
                },
                {
                    "channelId": "5572a109b3cdc86cf39001db",
                    "channelName": "国内最新",
                    "content": "近年来，拥有“华尔街野蛮人”之称的老牌私募股份投资机构KKR(科尔伯格?克拉维斯)开始将投资目光瞄向了中国市场，",
                    "desc": "KKR又投资了国内生鲜电商巨头易果生鲜。",

                    "imageurls": [
                        {
                            "height": 250,
                            "url": "http://image20.it168.com/201604_800x800/2502/f4afc28a07a8a151.jpg",
                            "width": 546
                        }
                    ],
                    "link": "http://elec.it168.com/a2016/0428/2619/000002619879.shtml",
                    "nid": "10826203206257278541",
                    "pubDate": "2016-04-28 15:29:18",
                    "sentiment_display": 0,
                    "sentiment_tag": {
                        "count": "14547",
                        "dim": "0",
                        "id": "934",
                        "isbooked": 0,
                        "ishot": "0",
                        "name": "Haier",
                        "type": "senti"
                    },
                    "source": "IT168",
                    "title": "KKR做红娘，促成海尔与易果战略合作"
                }
            ],
            "currentPage": 1,
            "maxResult": 20
        },
        "ret_code": 0
    }
}
    *
    * */

    private String NewsIconUrl;//汽车资讯图片
    private String NewsTitle;//资讯标题
    private String NewsTime;//资讯时间
    private String NewsSource;//资讯来源
    private String NewsLink;//资讯链接

    public String getNewsLink() {
        return NewsLink;
    }

    public void setNewsLink(String newsLink) {
        NewsLink = newsLink;
    }

    public String getNewsIconUrl() {
        return NewsIconUrl;
    }

    public void setNewsIconUrl(String newsIconUrl) {
        NewsIconUrl = newsIconUrl;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsTime() {
        return NewsTime;
    }

    public void setNewsTime(String newsTime) {
        NewsTime = newsTime;
    }

    public String getNewsSource() {
        return NewsSource;
    }

    public void setNewsSource(String newsSource) {
        NewsSource = newsSource;
    }
}

