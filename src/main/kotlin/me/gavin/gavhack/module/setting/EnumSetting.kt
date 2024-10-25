package me.gavin.gavhack.module.setting

class EnumSetting<T : Enum<T>>(
    name: String,
    private val defaultValue: T
): ModSetting(name) {
    val values: Array<T> = defaultValue.javaClass.getEnumConstants()

    var value: T = defaultValue

    fun increment(): T {
        value = if (value.ordinal == values.size - 1) {
            values[0]
        } else {
            values[value.ordinal + 1]
        }

        return value
    }

    fun decrement(): T {
        value = if (value.ordinal == 0) {
            values[values.size - 1]
        } else {
            values[value.ordinal - 1]
        }
        return value
    }

    override fun reset() {
        this.value = this.defaultValue
    }

    fun indexOf(t: T): Int {
        for (i in values.indices) {
            if (values[i] === t) return i
        }
        return -1
    }
}