package com.example.android.bookworm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phil.walker on 2/23/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final Book currentBook = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.title_view);
        titleView.setText(currentBook.getmTitle());

        TextView descriptionView = (TextView) listItemView.findViewById(R.id.description_view);
        descriptionView.setText(currentBook.getmDescription());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author_view);
        authorView.setText(currentBook.getmAuthors());

        return listItemView;
    }
}
