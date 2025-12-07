package org.orbis.application

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.text.Font
import javafx.scene.image.Image

class Orbis : Application() {
    override fun start(stage: Stage) {
        Font.loadFont(Orbis::class.java.getResourceAsStream("/org/orbis/application/fonts/ithaca.ttf"), 18.0)

        val fxmlLoader = FXMLLoader(Orbis::class.java.getResource("main.fxml"))
        val scene = Scene(fxmlLoader.load(), 700.0, 400.0)
        try {
            val icon = Image(Orbis::class.java.getResourceAsStream("/org/orbis/application/icon.png"))

            stage.icons.add(icon)
        } catch (e: Exception) {
            System.err.println("Ошибка загрузки иконки: ${e.message}")
        }

        stage.title = "Orbis"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(Orbis::class.java)
}
