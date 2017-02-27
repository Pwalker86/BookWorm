package com.example.android.bookworm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView noConnectionView = (TextView) findViewById(R.id.no_connection_view);
        if (!QueryUtils.checkConnectivity(this)) {
            noConnectionView.setVisibility(View.VISIBLE);
        } else {
            noConnectionView.setVisibility(View.GONE);
        }

        final String[] maxResultsSelection = new String[1];
        final EditText searchBox = (EditText) findViewById(R.id.search_box);
        final Button button = (Button) findViewById(R.id.search_button);
        Spinner spinner = (Spinner) findViewById(R.id.max_results_box);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.max_results_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (Objects.equals(adapterView.getSelectedItem().toString(), "Max Results…")) {
                    button.setEnabled(false);
                } else {
                    maxResultsSelection[0] = adapterView.getSelectedItem().toString();
                    button.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = String.valueOf(searchBox.getText());
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                intent.putExtra("query", searchQuery);
                intent.putExtra("maxResults", maxResultsSelection[0]);
                startActivity(intent);
            }
        });
    }
}
