package me.gavin.gavhack.module

import com.google.gson.JsonObject
import me.gavin.gavhack.config.IConfigurable
import me.gavin.gavhack.module.setting.ModSetting
import net.minecraft.client.Minecraft

abstract class Module(val name: String, val desc: String?, val category: ModCategory): IConfigurable {

    val settings = ArrayList<ModSetting>()

    protected val mc: Minecraft = Minecraft.getMinecraft()

    private var enabled = false

    protected open fun onEnable() { }

    protected open fun onDisable() { }

    fun enable() {
        enabled = true
        onEnable()
    }

    fun disable() {
        enabled = false
        onDisable()
    }

    fun toggle() {
        if (enabled) disable() else enable()
    }

    fun isEnabled() : Boolean {
        return enabled
    }

    override fun toJsonObj(): JsonObject {
        TODO("Not yet implemented")
    }

    override fun fromJsonObj(json: JsonObject) {
        TODO("Not yet implemented")
    }
}