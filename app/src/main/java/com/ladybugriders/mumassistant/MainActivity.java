package com.ladybugriders.mumassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Main m_main = null;
    private ArrayAdapter<String> m_adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_main = new Main();
        m_main.readFromFile(getBaseContext());

        m_adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1);

        setContentView(R.layout.activity_main);

        Button startFeedingButton = findViewById(R.id.start_feeding_button);
        startFeedingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new feeding data and the current date.
                FeedingData feeding = new FeedingData();
                feeding.setDate(new Date());

                // Add the new feeding data.
                m_main.getAssistantData().getFeedings().add(feeding);

                // Save the new data to the file.
                m_main.writeToFile(getBaseContext());

                // Add the corresponding entry in the list.
                m_adapter.add(feeding.getDate().toString());

                // Update all the views.
                updateViews();

                // Display a toast to inform that everything is ok.
                Toast.makeText(MainActivity.this, R.string.saved_successfuly, Toast.LENGTH_SHORT).show();
            }
        });

        ListView feedingsListView = findViewById(R.id.feedings_listview);
        feedingsListView.setAdapter(m_adapter);

        updateViews();
    }

    protected void updateViews() {
        List<FeedingData> feedings = m_main.getAssistantData().getFeedings();
        if (feedings.isEmpty() == true)
        {
            return;
        }

        // Sort feedings anti-chronologically.
        Collections.sort(feedings, new Comparator<FeedingData>() {
                    @Override
                    public int compare(FeedingData feedingData, FeedingData t1) {
                        // Reverse order.
                        return feedingData.getDate().compareTo(t1.getDate()) * -1;
                    }
                }
        );
        ArrayList<String> dates = new ArrayList<>(feedings.size());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm - dd/MM/yyyy");
        for (FeedingData feeding : feedings) {
            Date date = feeding.getDate();
            dates.add(dateFormat.format(date));
        }

        m_adapter.clear();
        m_adapter.addAll(dates);
    }
}
