package me.gavin.gavhack.config

import com.google.gson.JsonObject

interface IConfigurable {

    fun getConfigFileName(): String

    fun toJsonObj(): JsonObject

    fun fromJsonObj(jsonObj: JsonObject)
}