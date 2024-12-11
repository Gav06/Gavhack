package me.gavin.gavhack.config

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import me.gavin.gavhack.Gavhack
import me.gavin.gavhack.module.ModuleManager
import net.minecraft.client.Minecraft
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object ConfigManager {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val parser = JsonParser()
    private val folder = File(Minecraft.getMinecraft().mcDataDir, "gavhack")

    private val configs = HashSet<IConfigurable>()
    /* Detected to be true if the gavhack data folder doesn't already exist, used for default settings */
    var firstTimeRunningGavhack = false

    private fun setupConfigFolder() {
        if (!folder.exists()) {
            folder.mkdir()
            this.firstTimeRunningGavhack = true
            // default configs
            ModuleManager.mods.forEach { it.firstTimeSetup() }
        }
    }

    private fun getConfigFile(config: IConfigurable): File {
        return File(folder, "${config.getConfigFileName()}.json")
    }

    private fun saveConfig(config: IConfigurable) {
        val jsonObj = config.toJsonObj()
        val configFile = getConfigFile(config)

        if (!configFile.exists())
            configFile.createNewFile()

        val writer = FileWriter(configFile)
        gson.toJson(jsonObj, writer)
        writer.close()
        println("Saved ${Gavhack.NAME} config for ${config.getConfigFileName()}")
    }

    private fun loadConfig(config: IConfigurable) {
        val configFile = getConfigFile(config)

        if (!configFile.exists())
            return

        val reader = FileReader(configFile)
        val jsonElement = parser.parse(reader) ?: return

        try {
            val jsonObj = jsonElement.asJsonObject
            config.fromJsonObj(jsonObj)
        } catch (e: Exception) {
            Gavhack.log.error("Unable to load config for: ${config.getConfigFileName()}")
            e.printStackTrace()
        }
    }

    fun loadConfigs() {
        configs.forEach { loadConfig(it) }
    }

    fun saveConfigs() {
        configs.forEach { saveConfig(it) }
    }

    fun addConfig(config: IConfigurable) {
        this.configs.add(config)
    }

    init {
        this.setupConfigFolder()
        Runtime.getRuntime().addShutdownHook(object: Thread() {
            override fun run() {
                println("Attempting to save configs")
                saveConfigs()
            }
        })
    }
}