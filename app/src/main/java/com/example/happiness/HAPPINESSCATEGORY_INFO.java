package com.example.happiness;

public class HAPPINESSCATEGORY_INFO {

    public String category_title ;


    public HAPPINESSCATEGORY_INFO(String title_category){

        this.category_title = title_category;

    }

    public String getCategoryName(){

        return category_title;

    }

    public void setCategoryName(String category_title){

        this.category_title = category_title;

    }


}
