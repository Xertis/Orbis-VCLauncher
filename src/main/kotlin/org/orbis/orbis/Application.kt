package org.orbis.orbis

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.scene.text.Font

class Orbis : Application() {
    override fun start(stage: Stage) {

        Font.loadFont(Orbis::class.java.getResourceAsStream("/org/orbis/orbis/fonts/ithaca.ttf"), 18.0)

        val fxmlLoader = FXMLLoader(Orbis::class.java.getResource("main.fxml"))
        val scene = Scene(fxmlLoader.load(), 700.0, 400.0)

        stage.title = "Orbis"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(Orbis::class.java)
}
