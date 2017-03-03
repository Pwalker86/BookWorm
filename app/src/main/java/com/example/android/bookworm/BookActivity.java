package com.example.android.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by phil.walker on 2/25/17.
 */

public class BookActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<Book>> {
    private BookAdapter bookAdapter;
    URL queryURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        if (QueryUtils.checkConnectivity(this)) {
            String query = getIntent().getStringExtra("query");
            String maxResults = getIntent().getStringExtra("maxResults");
            String finalUrlString = QueryUtils.constructUrlString(query, maxResults);
            queryURL = QueryUtils.createUrl(finalUrlString);

            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            Intent intent = getParentActivityIntent();
            startActivity(intent);
        }
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, queryURL);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        ProgressBar loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        TextView emptyView = (TextView) findViewById(R.id.empty_view);

        if (data.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
            bookAdapter = new BookAdapter(BookActivity.this, data);
            ListView listView = (ListView) findViewById(R.id.list_container);
            listView.setAdapter(bookAdapter);
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Book>> loader) {
        if (bookAdapter != null) {
            bookAdapter.clear();
        }
    }
}
