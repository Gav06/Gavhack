package me.gavin.gavhack.config

import com.google.gson.JsonObject

interface IConfigurable {

    fun toJsonObj(): JsonObject

    fun fromJsonObj(json: JsonObject)
}