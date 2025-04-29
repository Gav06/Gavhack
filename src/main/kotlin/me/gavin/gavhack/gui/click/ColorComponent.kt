package me.gavin.gavhack.gui.click

import me.gavin.gavhack.module.setting.ColorSetting

class ColorComponent(override val setting: ColorSetting, w: Int, h: Int): SettingComponent(setting, 0, 0, w, h, ) {

    override fun onClick(mouseX: Int, mouseY: Int, button: Int) {
        TODO("Not yet implemented")
    }

    override fun onRelease(mouseX: Int, mouseY: Int, button: Int) {
        TODO("Not yet implemented")
    }

    override fun onRender(mouseX: Int, mouseY: Int, partialTicks: Float) {
        TODO("Not yet implemented")
    }

    override fun keyTyped(keyChar: Char, keyCode: Int) {
        TODO("Not yet implemented")
    }


}