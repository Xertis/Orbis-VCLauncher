package org.orbis.core

import javafx.scene.Group
import kotlinx.serialization.json.Json


private fun run(commands: Array<String>): String {
    return Console.run(arrayOf(Globals.vcimFile) + commands)
}

private fun run(command: String): String {
    return Console.run(arrayOf(Globals.vcimFile, command))
}

object Vcim {
    fun init() {
        run("init")
        run("sync")
        getCachedVersions()
    }

    fun getVersions(): List<String> {
        val out = run(arrayOf("repo", "versions", "--asjson"))
        val json: List<String> = Json.decodeFromString(out)
        return json
    }

    fun getInstancesInfo(): List<InstantShort> {
        val out = run(arrayOf("instances", "ilist", "--asjson"))
        val json: List<InstantShort> = Json.decodeFromString(out)
        return json
    }

    fun getInstantInfo(name: String): Instant {
        val out = run(arrayOf("instances", "info", name, "--asjson"))
        val json: Instant = Json.decodeFromString(out)
        return json
    }

    fun createInstant(name: String, version: String, group: String?=null): String {
        val out = when(group) {
            null -> run(arrayOf("instances", "install", name, version))
            else -> run(arrayOf("instances", "install", name, version, "--group", group))
        }
        return out
    }

    fun getCachedVersions(): List<String> {
        val out = run(arrayOf("cache", "list", "--asjson"))
        val json: List<String> = Json.decodeFromString(out)
        return json
    }

    fun deleteCachedVersion(version: String): String {
        val out = run(arrayOf("cache", "remove", version))
        return out
    }
}