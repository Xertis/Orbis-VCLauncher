package org.orbis.core

object Globals {
    val tempOs = System.getProperty("os.name").lowercase().replace(" ", "-")
    val osName =
        if ("windows" in tempOs) "windows"
        else if ("linux" in tempOs) "linux"
        else "macos"

    val userDir = System.getProperty("user.dir") + "\\"
    val versionsDir = userDir + "versions\\"
    val versionsExt = when(osName) {
        "windows" -> "zip"
        "linux" -> "appimage"
        else -> "dmg"
    }

    val vcimFile = when(osName) {
        "windows" -> "vcim.exe"
        else -> "vcim"
    }
}