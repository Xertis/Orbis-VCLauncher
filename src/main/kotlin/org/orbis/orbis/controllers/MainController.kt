package org.orbis.orbis.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.layout.AnchorPane

class MainController {
    @FXML lateinit var paneSettings: AnchorPane
    @FXML lateinit var paneVersions: AnchorPane
    @FXML lateinit var paneConsole: AnchorPane
    @FXML lateinit var paneInstances: AnchorPane

    @FXML lateinit var btnConsole: Button
    @FXML lateinit var btnInstances: Button
    @FXML lateinit var btnVersions: Button
    @FXML lateinit var btnSettings: Button
    @FXML lateinit var panels: Array<Pair<Button, AnchorPane>>

    @FXML
    fun initialize() {
        panels = arrayOf(
            Pair(btnInstances, paneInstances),
            Pair(btnConsole, paneConsole),
            Pair(btnVersions, paneVersions),
            Pair(btnSettings, paneSettings)
        )
        changePage(getPage(btnInstances))
    }

    private fun getPage(button: Button): AnchorPane {
        for (pair in panels) {
            if (pair.first == button) return pair.second
        }

        return paneInstances
    }

    private fun changePage(pane: AnchorPane) {

        for (pair in panels) {
            val button = pair.first
            val panel = pair.second

            val isDisable = panel.id == pane.id

            button.isDisable = isDisable
            panel.isVisible = isDisable
        }
    }

    @FXML
    private fun showInstances() {
        changePage(getPage(btnInstances))
    }

    @FXML
    private fun showConsole() {
        changePage(getPage(btnConsole))
    }

    @FXML
    private fun showVersions() {
        changePage(getPage(btnVersions))
    }

    @FXML
    private fun showSettings() {
        changePage(getPage(btnSettings))
    }
}