package org.orbis.application.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ProgressBar
import javafx.stage.Stage
import org.orbis.core.Globals
import org.orbis.core.Vcim


class DownloaderController {
    @FXML lateinit var progressBar: ProgressBar
    @FXML lateinit var btnDownload: Button
    @FXML lateinit var versions: ChoiceBox<Any>
    @FXML private lateinit var dialogStage: Stage

    @FXML
    fun initialize() {
        initializeReleases()
    }

    private fun initializeReleases() {
        val releases = Vcim.getVersions()

        releases.forEach {
            versions.items.add(it)
        }

        versions.value = versions.items[0]
    }

    fun setDialogStage(dialogStage: Stage) {
        this.dialogStage = dialogStage
    }

    private fun downloadVersion(version: String) {
        Vcim.createInstant("mda", version)
    }

    @FXML
    private fun downloadVersionCallback() {
        for (release in Vcim.getVersions()) {
            if (release == versions.value) {
                downloadVersion(release)
                break
            }
        }
    }
}