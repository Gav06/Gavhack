package me.gavin.gavhack.module.impl

import me.gavin.gavhack.module.ModCategory
import me.gavin.gavhack.module.Module
import me.gavin.gavhack.module.setting.EnumSetting
import me.gavin.gavhack.module.setting.FloatSetting
import me.gavin.gavhack.module.setting.IntSetting
import me.gavin.gavhack.module.setting.BooleanSetting

class TestModule(name: String) : Module(name, "Empty module for testing", ModCategory.MISC) {

    enum class TestOption {
        FOO,
        BAR,
        FIZZ,
        BUZZ
    }

    val testToggle = addSetting(BooleanSetting("Test bool", true))
    val testFloat = addSetting(FloatSetting("Test float", 15f, 3f, 25f))
    val testInt = addSetting(IntSetting("Test int", 5, 1, 10))
    val testEnum = addSetting(EnumSetting("Test enum", TestOption.BAR))
}