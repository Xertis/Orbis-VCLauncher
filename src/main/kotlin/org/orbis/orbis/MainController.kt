package org.orbis.orbis

import javafx.fxml.FXML
import javafx.scene.control.Button

class MainController {
    @FXML lateinit var btnConsole: Button
    @FXML lateinit var btnInstances: Button
    @FXML lateinit var btnVersions: Button
    @FXML lateinit var btnSettings: Button

    @FXML fun initialize() {
        btnInstances.isDisable = true
    }

    private fun changePage(page: String) {
        println(page)
        val buttons = arrayOf(
            btnConsole, btnInstances, btnVersions, btnSettings
        )

        for (button in buttons) {
            button.isDisable = button.id == page
        }
    }

    @FXML private fun showInstances() {
        changePage(btnInstances.id)
    }

    @FXML private fun showConsole() {
        changePage(btnConsole.id)
    }

    @FXML private fun showVersions() {
        changePage(btnVersions.id)
    }

    @FXML private fun showSettings() {
        changePage(btnSettings.id)
    }
}