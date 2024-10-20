package me.gavin.gavhack.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

object ConfigManager {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val parser = JsonParser()

    fun loadConfigs() {

    }

    fun saveConfigs() {

    }
}