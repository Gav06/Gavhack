package me.gavin.gavhack

import com.google.gson.JsonParser
import org.apache.commons.io.IOUtils
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets

object Utils {
    fun resolveCape(player: String): String {
        // fetch json file from cape api
        val url = try {
            URL("https://api.capes.dev/load/$player/minecraft")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return "URL error"
        }

        val jsonObj = JsonParser().parse(IOUtils.toString(url, StandardCharsets.UTF_8)).asJsonObject

        // pull message from json
        if (!jsonObj.has("msg") or !jsonObj.has("imageUrl"))
            return "Cape API JSON missing fields"

        val msg = jsonObj["msg"].asString
        val errMsg = "($player) $msg"

        if (msg == "Cape not found")
            return errMsg

        if (jsonObj["msg"].asString == "Cape found")
            return jsonObj["imageUrl"].asString

        return errMsg
    }
}