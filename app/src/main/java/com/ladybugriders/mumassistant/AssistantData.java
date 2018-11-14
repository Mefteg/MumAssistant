package com.ladybugriders.mumassistant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssistantData {
    private List<FeedingData> m_feedings;

    public AssistantData() {
        m_feedings = new ArrayList<>();
    }

    public List<FeedingData> getFeedings() {
        return m_feedings;
    }

    public void setFeedings(List<FeedingData> m_feedings) {
        this.m_feedings = m_feedings;
    }

    public JSONObject serialize() throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray feedings = new JSONArray();
        for (FeedingData feeding : getFeedings())
        {
            feedings.put(feeding.serialize());
        }

        json.put("feedings", feedings);

        return json;
    }

    public void deserialize(JSONObject json) throws JSONException {
        JSONArray feedings = json.getJSONArray("feedings");
        int feedingCount = feedings.length();
        m_feedings = new ArrayList<>(feedingCount);
        for (int i = 0; i < feedingCount; ++i)
        {
            FeedingData feeding = new FeedingData();
            feeding.deserialize(feedings.getJSONObject(i));
            m_feedings.add(feeding);
        }
    }
}
