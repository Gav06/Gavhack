package dev.gavhack.config;

import com.google.gson.JsonObject;

public interface Configurable {

    void writeToConfig(JsonObject jsonObject);

    void readFromConfig(JsonObject jsonObject);
}
