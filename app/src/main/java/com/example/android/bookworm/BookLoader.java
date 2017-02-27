package com.example.android.bookworm;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by phil.walker on 2/27/17.
 */

class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {
    private URL queryURL;

    BookLoader(Context context, URL url) {
        super(context);
        queryURL = url;
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        String jsonResponse = QueryUtils.makeHttpRequest(queryURL);

        return QueryUtils.parseBookResponse(jsonResponse);
    }
}
