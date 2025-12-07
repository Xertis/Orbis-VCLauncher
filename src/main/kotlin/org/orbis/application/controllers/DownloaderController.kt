package org.orbis.application.controllers

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.ProgressBar
import javafx.stage.Stage
import org.orbis.application.Globals
import org.orbis.application.libs.network.Github
import org.orbis.application.libs.network.Release

object DataCache {
    val githubReleases: List<Release> by lazy {
        Github.getReleases()
    }
}

class DownloaderController {
    @FXML lateinit var progressBar: ProgressBar
    @FXML lateinit var btnDownload: Button
    @FXML lateinit var versions: ChoiceBox<Any>
    @FXML private lateinit var dialogStage: Stage

    @FXML
    fun initialize() {
        val releases = DataCache.githubReleases

        for (release in releases) {
            val hasNeededVersion = release.assets.any { asset ->
                val assetName = asset.name.lowercase()

                assetName.endsWith(Globals.versionsExt)
            }

            if (hasNeededVersion) {
                versions.items.add(release.name)
            }

        }

        versions.value = releases[0].name

    }

    fun setDialogStage(dialogStage: Stage) {
        this.dialogStage = dialogStage
    }

    private fun downloadVersion(release: Release) {
        val assets = release.assets
        val indx = assets.indexOfFirst { asset ->
            val assetName = asset.name.lowercase()

            assetName.endsWith(Globals.versionsExt)
        }

        if (indx == -1) {
            println("релиз не найден")
            println(assets)
            return
        }

        progressBar.isVisible = true

        val fileName = "${release.name}.${release.assets[indx].name.substringAfterLast(".")}"

        println(Globals.versionsDir + fileName)
        Github.downloadFileWithProgress(
            fileData = assets[indx],
            destinationPath = Globals.versionsDir + fileName,
            onProgressUpdate = { progress ->
                progressBar.progress = progress / 100.0
            },
            onEnd = { status ->
                println("статус скачивания: $status")
                progressBar.isVisible = false
                progressBar.progress = 0.0
            }

        )
    }

    @FXML
    private fun downloadVersionCallback() {
        val releases = DataCache.githubReleases
        for (release in releases) {
            if (release.name == versions.value) {
                downloadVersion(release)
                break
            }
        }
    }
}