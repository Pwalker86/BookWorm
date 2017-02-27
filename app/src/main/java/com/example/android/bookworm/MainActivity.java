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

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] maxResultsSelection = new String[1];

        Spinner spinner = (Spinner) findViewById(R.id.max_results_box);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.max_results_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maxResultsSelection[0] = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final EditText searchBox = (EditText) findViewById(R.id.search_box);
        Button button = (Button) findViewById(R.id.search_button);

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
