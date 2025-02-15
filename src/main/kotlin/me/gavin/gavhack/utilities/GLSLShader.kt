package me.gavin.gavhack.utilities

import org.lwjgl.opengl.GL20
import java.io.InputStream
import java.io.InputStreamReader

open class GLSLShader(fragStream: InputStream?, vertStream: InputStream?) {

    private val fragSrc: String = readFromStream(fragStream)
    private val vertSrc: String =  readFromStream(vertStream)
    private val uniforms = HashMap<String, Int>()

    val shaderProgram = setupShader()

    constructor(fragPath: String, vertPath: String): this(getResource(fragPath), getResource(vertPath))

    private fun setupShader(): Int {
        val vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER)
        GL20.glShaderSource(vs, vertSrc)
        GL20.glCompileShader(vs)
        checkShaderError(vs, "Vertex")

        val fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER)
        GL20.glShaderSource(fs, fragSrc)
        GL20.glCompileShader(fs)
        checkShaderError(fs, "Fragment")

        val prog = GL20.glCreateProgram()
        GL20.glAttachShader(prog, vs)
        GL20.glAttachShader(prog, fs)
        GL20.glLinkProgram(prog)
        checkProgramError(prog)

        GL20.glDeleteShader(vs)
        GL20.glDeleteShader(fs)


        return prog
    }

    private fun checkShaderError(shader: Int, type: String) {
        val compiled = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS)
        if (compiled == 0) {
            val error = GL20.glGetShaderInfoLog(shader, 1024)
            throw RuntimeException("$type shader compilation failed:\n$error")
        }
    }

    private fun checkProgramError(program: Int) {
        val linked = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS)
        if (linked == 0) {
            val error = GL20.glGetProgramInfoLog(program, 1024)
            throw RuntimeException("Shader program linking failed:\n$error")
        }
    }


    // returns if successful
    protected fun setupUniform(name: String): Boolean {
        val uniformLoc = GL20.glGetUniformLocation(shaderProgram, name)

        if (uniformLoc == -1) {
            println("Warning: Uniform '$name' not found in shader program")
            return false
        }

        uniforms[name] = uniformLoc
        return true
    }

    public fun getUniform(name: String): Int {
        return if (uniforms.containsKey(name)) {
            uniforms[name]!!
        } else {
            -1
        }
    }

    companion object {
        fun readFromStream(streamIn: InputStream?): String {
            if (streamIn == null) {
                throw RuntimeException("Shader input stream is null")
            }

            val reader = InputStreamReader(streamIn)
            val src = reader.readLines().joinToString("\n") + "\n"
            reader.close()
            return src
        }

        fun getResource(path: String): InputStream {
            return Companion::class.java.getResourceAsStream(path)
                ?: throw RuntimeException("Could not find shader resource: $path")
        }
    }

    open fun updateUniforms() { }
}