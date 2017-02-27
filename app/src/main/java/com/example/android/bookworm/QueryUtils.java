package com.example.android.bookworm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by phil.walker on 2/27/17.
 */

class QueryUtils {
    private static final String LOG_TAG = BookActivity.class.getSimpleName();

    @NonNull
    static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    static ArrayList<Book> parseBookResponse(String jsonResponse) {
        JSONObject baseJsonResponse;
        ArrayList<Book> list = new ArrayList<>();
        try {
            baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < bookArray.length(); i++) {
                String title = "";
                String description = "";
                ArrayList<String> authors = new ArrayList<>();

                JSONObject itemDetails = bookArray.getJSONObject(i);
                JSONObject volumeInfo = itemDetails.getJSONObject("volumeInfo");
                title = volumeInfo.getString("title");

                if (volumeInfo.has("description")) {
                    description = volumeInfo.getString("description");
                } else {
                    description = "None";
                }

                if (volumeInfo.has("authors")) {
                    JSONArray authorArray = volumeInfo.getJSONArray("authors");
                    for (int x = 0; x < authorArray.length(); x++) {
                        authors.add(authorArray.getString(x));
                    }
                } else {
                    authors.add("None");
                }

                list.add(new Book(title, authors, description));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    static String constructUrlString(String queryString, String maxResults) {
        String finalString;
        String firstSection = "https://www.googleapis.com/books/v1/volumes?q=";
        String secondSection = "&maxResults=";

        finalString = firstSection + queryString + secondSection + maxResults;
        return finalString;
    }

    static boolean checkConnectivity(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
