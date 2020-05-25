package com.example.happiness;

public class MYHAPPINESSCOMMUNITY_DATA {

    public String myhappinesscommunityTitleStr;
    public String myhappinesscommunityContentsStr;
    public String myhappinesscommunityImageUrlStr;

//    public byte[] myhappinesscommunityImageUrlStr;
//    public Bitmap  myhappinesscommunityImage;

    public MYHAPPINESSCOMMUNITY_DATA(String title, String contents, String imageurl){ // , ,// byte[] imageurl , Bitmap image
        myhappinesscommunityTitleStr = title;
        myhappinesscommunityContentsStr = contents;
        myhappinesscommunityImageUrlStr = imageurl;
    }

    public String getMyhappinesscommunityTitle(){

        return this.myhappinesscommunityTitleStr;

    }

    public String getMyhappinesscommunityContents(){

        return myhappinesscommunityContentsStr;

    }

    public String getMyhappinesscommunityImageUrlStr() {
        return myhappinesscommunityImageUrlStr;
    }

    //    public Bitmap getMyhappinesscommunityImage() {
//        return myhappinesscommunityImage;
//    }

//    public byte[] getMyhappinesscommunityImageUrlStr() {
//
//
//        return myhappinesscommunityImageUrlStr;
//    }

    //    public String getMyhappinesscommunityImageUrlStr() {
//        return myhappinesscommunityImageUrlStr;
//    }

}
