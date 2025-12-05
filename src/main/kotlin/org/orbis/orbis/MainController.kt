package org.orbis.orbis

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label

class MainController {
    @FXML
    lateinit var btnConsole: Button
    @FXML
    lateinit var btnVersions: Button

    @FXML
    fun initialize() {
        btnVersions.isDisable = true
    }

    private fun changePage(page: String) {
        println(page)
        val buttons = arrayOf(
            btnConsole, btnVersions
        )

        for (button in buttons) {
            button.isDisable = button.id == page
        }
    }

    @FXML
    private fun showVersions() {
        changePage(btnVersions.id)
    }

    @FXML
    private fun showConsole() {
        changePage(btnConsole.id)
    }
}