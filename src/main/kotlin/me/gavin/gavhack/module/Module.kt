package me.gavin.gavhack.module

import com.google.gson.JsonObject
import me.gavin.gavhack.config.IConfigurable
import me.gavin.gavhack.event.Event
import me.gavin.gavhack.event.EventBus
import me.gavin.gavhack.event.Listener
import me.gavin.gavhack.module.setting.ModSetting
import net.minecraft.client.Minecraft
import kotlin.reflect.KClass

abstract class Module(
    val name: String,
    val desc: String?,
    val category: ModCategory,
    var bind: Int = 0
): IConfigurable {

    val settings = ArrayList<ModSetting>()

    protected val mc: Minecraft = Minecraft.getMinecraft()

    private val listeners = HashMap<Listener<Event>, KClass<out Event>>()
    private var enabled = false

    protected open fun onEnable() { }

    protected open fun onDisable() { }

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
        TODO("Not yet implemented")
    }

    override fun fromJsonObj(json: JsonObject) {
        TODO("Not yet implemented")
    }

    protected fun <T : ModSetting> addSetting(setting: T) : T{
        this.settings.add(setting)
        return setting
    }

    protected fun <T : Event> addEventListener(listener: Listener<T>, clazz: KClass<out T>) {
        this.listeners[listener] = clazz
    }
}