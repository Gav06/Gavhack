package me.gavin.gavhack.config

import com.google.gson.JsonObject

interface IConfigurable {

    fun getConfigName(): String

    fun getConfigGroup(): String

    fun toJsonObj(): JsonObject

    fun fromJsonObj(jsonObj: JsonObject)
}