package com.ladybugriders.mumassistant;

import org.json.JSONException;
import org.json.JSONObject;

public class FeedingData {
    private int m_date;

    public int getDate() {
        return m_date;
    }

    public void setDate(int m_date) {
        this.m_date = m_date;
    }

    public JSONObject serialize() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("date", m_date);

        return object;
    }
}
