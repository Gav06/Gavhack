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
    private var firstTimeRunningGavhack = false

    private fun setupFolder(name: String): File {
        val file = File(folder, name)
        if (!file.exists()) {
            file.mkdir()
        }

        return file;
    }

    private fun setupConfigFolder() {
        if (!folder.exists()) {
            folder.mkdir()
            this.firstTimeRunningGavhack = true
            ModuleManager.mods.forEach { it.firstTimeSetup() }
        }
    }

    private fun getConfigFile(config: IConfigurable): File {
        val configFolder = setupFolder(config.getConfigGroup());
        return File(configFolder, "${config.getConfigName()}.json")
    }

    private fun saveConfig(config: IConfigurable) {
        val jsonObj = config.toJsonObj()
        val configFile = getConfigFile(config)

        if (!configFile.exists())
            configFile.createNewFile()

        val writer = FileWriter(configFile)
        gson.toJson(jsonObj, writer)
        writer.close()
        println("[${Gavhack.NAME}] Saved config: ${configFile.canonicalPath}")
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
            Gavhack.log.error("Unable to load config for: ${config.getConfigName()}")
            e.printStackTrace()
        }
    }

    fun isFirstTimeRunningGavhack(): Boolean {
        return this.firstTimeRunningGavhack
    }

    fun loadConfigs() {
        configs.forEach { loadConfig(it) }
    }

    fun saveConfigs() {
        // setup config folders if necessary

        // save individual jsons
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