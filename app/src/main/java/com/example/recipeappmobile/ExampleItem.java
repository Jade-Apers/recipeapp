package com.example.recipeappmobile;

public class ExampleItem {
    private String mImageUrl;
    private String mTitle;
    private int mDuration;
    private String mIngredients;
    private String mUri;

    public ExampleItem(String imageUrl, String title, int duration, String ingredients, String uri) {
        mImageUrl = imageUrl;
        mDuration = duration;
        mTitle = title;
        mIngredients = ingredients;
        mUri = uri;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getDuration() {
        return mDuration;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public String getUri() {
        return mUri;
    }
}
