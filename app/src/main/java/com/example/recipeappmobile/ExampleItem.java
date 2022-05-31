package com.example.recipeappmobile;

public class ExampleItem {
    private String mImageUrl;
    private String mTitle;
    private int mDuration;

    public ExampleItem(String imageUrl, String title, int duration){
        mImageUrl = imageUrl;
        mDuration = duration;
        mTitle = title;
    }

    public String getImageUrl(){
        return mImageUrl;
    }

    public String getTitle(){
        return mTitle;
    }

    public int getDuration(){
        return mDuration;
    }




}
