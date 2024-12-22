package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.NumberSetting
import me.gavin.gavhack.module.setting.ToggleSetting

class TestModule(name: String) : Module(name, "Empty module for testing", ModCategory.MISC) {

    enum class TestOption {
        FOO,
        BAR,
        FIZZ,
        BUZZ
    }

    val testToggle = addSetting(ToggleSetting("Test bool", true))
    val testFloat = addSetting(NumberSetting("Test num", 15f, 3f, 25f))
    val testEnum = addSetting(EnumSetting("Test enum", TestOption.BAR))
}