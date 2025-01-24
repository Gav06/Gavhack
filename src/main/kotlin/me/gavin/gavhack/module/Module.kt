package me.gavin.gavhack.module

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import me.gavin.gavhack.config.IConfigurable
import me.gavin.gavhack.event.Event
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.NumberSetting
import me.gavin.gavhack.module.setting.Setting
import me.gavin.gavhack.module.setting.ToggleSetting
import net.minecraft.client.Minecraft
import kotlin.reflect.KClass

abstract class Module(
    val name: String,
    val desc: String?,
    val category: ModCategory,
    var bind: Int = 0
): IConfigurable {

    val settings = ArrayList<Setting>()

    protected val mc: Minecraft = Minecraft.getMinecraft()

    private val listeners = HashMap<Listener<Event>, KClass<out Event>>()
    private var enabled = false

    protected open fun onEnable() { }

    protected open fun onDisable() { }

    open fun firstTimeSetup() { }

    fun enable() {
        enabled = true
        for ((listener, clazz) in listeners) {
            EventBus.addListener(listener, clazz)
        }

        onEnable()
    }

    fun disable() {
        enabled = false
        for ((listener, clazz) in listeners) {
            EventBus.removeListener(listener, clazz)
        }

        onDisable()
    }

    fun toggle() {
        if (enabled) disable() else enable()
    }

    fun isEnabled() : Boolean {
        return enabled
    }

    override fun toJsonObj(): JsonObject {
        val jsonObj = JsonObject()
        jsonObj.addProperty("name", this.name)
        jsonObj.addProperty("enabled", this.isEnabled())
        jsonObj.addProperty("bind", this.bind)

        val settingObj = JsonObject()

        for (setting in settings) {
            when (setting) {
                is ToggleSetting -> settingObj.addProperty(setting.name, setting.value)
                is NumberSetting ->  settingObj.addProperty(setting.name, setting.getValue())
                is EnumSetting<*> -> settingObj.addProperty(setting.name, setting.value)
            }
        }

        jsonObj.add("settings", settingObj)
        return jsonObj
    }

    override fun fromJsonObj(jsonObj: JsonObject) {
        if (!(jsonObj.has("name") && this.name == jsonObj["name"].asString))
            return

        if (jsonObj.has("enabled") && jsonObj["enabled"].asBoolean)
            this.enable()

        if (jsonObj.has("bind"))
            this.bind = jsonObj["bind"].asInt

        if (!jsonObj.has("settings"))
            return

        val settingsObj = jsonObj.get("settings").asJsonObject

        for (setting in settings) {
            when (setting) {
                is ToggleSetting ->
                    if (settingsObj.has(setting.name)) setting.value = settingsObj[setting.name].asBoolean
                is NumberSetting ->
                    if (settingsObj.has(setting.name)) setting.setValue(settingsObj[setting.name].asFloat)
                is EnumSetting<*> ->
                    if (settingsObj.has(setting.name)) {
                        val index = setting.indexOf(settingsObj[setting.name].asString)
                        setting.value = index
                    }
            }
        }
    }

    override fun getConfigFileName(): String {
        return this.name
    }

    protected fun <T : Setting> addSetting(setting: T) : T{
        this.settings.add(setting)
        return setting
    }

    protected fun <T : Event> addEventListener(listener: Listener<T>, clazz: KClass<out T>) {
        this.listeners[listener] = clazz
    }
}