package com.example.android.bookworm;

import java.util.ArrayList;

/**
 * Created by phil.walker on 2/23/17.
 */

public class Book {
    private String mTitle;
    private ArrayList<String> mAuthors;
    private String mDescription;

    public Book(String title, ArrayList<String> authors, String description) {
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthors() {
        String finalString= "By: \n";
        for (int i = 0; i < mAuthors.size(); i ++){
            finalString += mAuthors.get(i);
            finalString += "\n";
        }
        return finalString;
    }

    public String getmDescription() {
        return "Description: \n" + mDescription;
    }
}
