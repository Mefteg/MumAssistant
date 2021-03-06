package com.ladybugriders.mumassistant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class FeedingData {
    private long m_date;

    public Date getDate() {
        return new Date(m_date);
    }

    public void setDate(Date date) {
        this.m_date = date.getTime();
    }

    public JSONObject serialize() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("date", m_date);

        return object;
    }

    public void deserialize(JSONObject json) throws JSONException {
        m_date = json.getLong("date");
    }
}
