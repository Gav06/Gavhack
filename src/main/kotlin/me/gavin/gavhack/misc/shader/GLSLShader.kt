package me.gavin.gavhack.misc.shader

import java.io.InputStream
import java.io.InputStreamReader

class GLSLShader(val fragShader: String, val vertShader: String) {

    constructor(fragStream: InputStream, vertStream: InputStream) : this(readTextFromStream(fragStream), readTextFromStream(vertStream))
    
    fun compileAndLink() {

    }

    companion object {
        private fun readTextFromStream(streamIn: InputStream): String {
            val reader = InputStreamReader(streamIn)
            var sb = StringBuilder()
            sb.append(reader.readLines())
            reader.close()
            return sb.toString()
        }
    }
}