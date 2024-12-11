package me.gavin.gavhack.module.setting

class EnumSetting<T : Enum<T>>(
    name: String,
    private val defaultValue: T
): Setting(name) {
    val values: Array<T> = defaultValue.javaClass.getEnumConstants()

    var value = indexOf(defaultValue)

    fun increment(): T {
        value = indexOf( if (value == values.size - 1) {
            values[0]
        } else {
            values[value + 1]
        })

        return values[value]
    }

    fun decrement(): T {
        value = indexOf(if (value == 0) {
            values[values.size - 1]
        } else {
            values[value - 1]
        })

        return values[value]
    }

    override fun reset() {
        this.value = indexOf(this.defaultValue)
    }

    fun indexOf(t: T): Int {
        for (i in values.indices) {
            if (values[i] == t) return i
        }
        return -1
    }

    fun indexOf(s: String): Int {
        for (i in values.indices) {
            if (values[i].name == s) return i
        }
        return -1
    }
}