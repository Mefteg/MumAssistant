package com.ladybugriders.mumassistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class FeedingDataAdapter extends ArrayAdapter<FeedingData> {
    private Main m_main;
    private SimpleDateFormat m_timeFormat;
    private SimpleDateFormat m_dateFormat;

    public FeedingDataAdapter(Context context, Main main)
    {
        super(context, 0, main.getAssistantData().getFeedings());

        m_main = main;

        m_timeFormat = new SimpleDateFormat("HH:mm");
        m_dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // If the view isn't loaded yet, do it.
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_feeding_data, parent,  false);
        }

        FeedingDataViewHolder viewHolder = (FeedingDataViewHolder) convertView.getTag();
        // Create the view holder if it has been done yet.
        if(viewHolder == null)
        {
            viewHolder = new FeedingDataViewHolder();
            viewHolder.timeText = convertView.findViewById(R.id.time_text);
            viewHolder.dateText = convertView.findViewById(R.id.date_text);
            viewHolder.removeButton = convertView.findViewById(R.id.remove_button);
            convertView.setTag(viewHolder);
        }

        // Get feeding regarding given position.
        final FeedingData feeding = getItem(position);

        // Fill the view.
        viewHolder.timeText.setText(m_timeFormat.format(feeding.getDate()));
        viewHolder.dateText.setText(m_dateFormat.format(feeding.getDate()));
        viewHolder.removeButton.setOnClickListener(CreateRemoveButtonListener(feeding));

        return convertView;
    }

    private View.OnClickListener CreateRemoveButtonListener(final FeedingData feeding) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage(R.string.remove_entry_message_feeding_alert_dialog);
                alertDialogBuilder.setPositiveButton(R.string.ok_alert_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        remove(feeding);

                        // Save the new data to the file.
                        m_main.writeToFile(getContext());

                        // Display a toast to inform that everything is ok.
                        Toast.makeText(getContext(), R.string.removed_successfuly, Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.setNegativeButton(R.string.cancel_alert_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDialogBuilder.create();
                alertDialogBuilder.show();
            }

        };
    }
}
