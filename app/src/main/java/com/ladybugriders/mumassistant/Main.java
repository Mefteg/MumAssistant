package com.ladybugriders.mumassistant;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    private AssistantData m_assistantData = null;

    public Main() {
        m_assistantData = new AssistantData();
    }

    public AssistantData getAssistantData() {
        return m_assistantData;
    }

    public void setAssistantData(AssistantData m_assistantData) {
        this.m_assistantData = m_assistantData;
    }

    public void readFromFile(Context context) {
        try {
            String ret = "";
            InputStream inputStream = context.openFileInput("assistant_data.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                JSONObject json = new JSONObject(ret);
                m_assistantData.deserialize(json);
            }
        }
        catch (JSONException e) {
            Log.e("Exception", "JSON deserialization failed: " + e.toString());
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        }
        catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void writeToFile(Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("assistant_data.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(m_assistantData.serialize().toString());
            outputStreamWriter.close();
        }
        catch (JSONException e) {
            Log.e("Exception", "JSON serialization failed: " + e.toString());
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
