package com.ladybugriders.mumassistant;

import java.util.List;

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
}
