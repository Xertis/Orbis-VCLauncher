package org.orbis.orbis

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class Orbis : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(Orbis::class.java.getResource("main.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(Orbis::class.java)
}