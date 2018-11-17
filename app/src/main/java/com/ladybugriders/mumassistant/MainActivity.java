package com.ladybugriders.mumassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Main m_main = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_main = new Main();
        m_main.readFromFile(getBaseContext());

        setContentView(R.layout.activity_main);

        Button startFeedingButton = findViewById(R.id.start_feeding_button);
        startFeedingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedingData feeding = new FeedingData();
                feeding.setDate(new Date());

                m_main.getAssistantData().getFeedings().add(feeding);

                m_main.writeToFile(getBaseContext());

                updateViews();
            }
        });

        ListView feedingsListView = findViewById(R.id.feedings_listview);

        updateViews();
    }

    protected void updateViews() {
        List<FeedingData> feedings = m_main.getAssistantData().getFeedings();
        if (feedings.isEmpty() == true)
        {
            return;
        }

        FeedingData lastFeeding = feedings.get(feedings.size() - 1);
        TextView lastStartFeedingValueLabel = findViewById(R.id.last_feeding_value_label);
        lastStartFeedingValueLabel.setText(lastFeeding.getDate().toString());
    }
}
