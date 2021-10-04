package me.gavin.gavhack.config;

import com.google.gson.JsonObject;

import com.google.gson.JsonObject;

public interface Configurable {

    String getName();

    String getConfigGroup();

    void saveProperties(JsonObject jsonObject);

    void loadProperties(JsonObject jsonObject);
}