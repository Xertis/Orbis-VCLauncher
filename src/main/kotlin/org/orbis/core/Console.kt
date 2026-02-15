package org.orbis.core

import java.io.BufferedReader
import java.io.InputStreamReader
import org.orbis.core.Globals

object Console {

    fun run(command: Array<String>): String {
        println(command.joinToString())
        return try {
            val process = ProcessBuilder(*command)
                .redirectErrorStream(true)
                .start()

            val output = BufferedReader(InputStreamReader(process.inputStream)).use { it.readText() }
            process.waitFor()
            output.trim()
        } catch (e: Exception) {
            "Ошибка выполнения команды: ${e.message}"
        }
    }

    fun prepareCommand(
        command: String,
        variables: HashMap<String, String>? = null
    ): Array<String> {
        var processedCommand = command
        variables?.forEach { (key, value) ->
            processedCommand = processedCommand.replace("{$key}", value)
        }

        return when (Globals.osName) {
            "windows" -> {
                arrayOf("cmd", "/c", processedCommand)
            }
            "linux" -> {
                arrayOf("bash", "-c", processedCommand)
            }
            else -> {
                arrayOf("sh", "-c", processedCommand)
            }
        }
    }
}