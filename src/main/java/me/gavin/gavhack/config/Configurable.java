package me.gavin.gavhack.config;

import com.google.gson.JsonObject;

public interface Configurable {

    JsonObject read();

    void write(JsonObject object);

    String getName();

    String getGroup();
}
