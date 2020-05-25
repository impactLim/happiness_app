package com.example.happiness;

public class HAPPINESSIMAGEFEED_DATA {


    // 우리가 원하는 image와 text 데이터를 보관하기 위한 데이터 저장소 개념
    public String imageurl;
    public String imagetitle;

    public HAPPINESSIMAGEFEED_DATA(String IMAGEurl, String IMAGEtitle) {
        this.imageurl = IMAGEurl;
        this.imagetitle = IMAGEtitle;

    }

    public String getImageurl() {
        return imageurl;
    }

    public String getImagetitle() {
        return imagetitle;
    }
}
//    // 우리가 원하는 image와 text 데이터를 보관하기 위한 데이터 저장소 개념
//        public String text;
//        public int image;
//
//        public HAPPINESSIMAGEFEED_DATA(String Text, int Image) {
//            this.text = Text;
//            this.image = Image;
//        }


