package org.orbis.application.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.Pos
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import org.orbis.application.Globals
import java.io.File

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import javafx.application.Platform
import javafx.scene.layout.FlowPane

class VersionsController {
    @FXML
    lateinit var paneVersions: AnchorPane
    @FXML
    lateinit var boxVersions: FlowPane

    private val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    @FXML
    fun initialize() {
        val paneContextMenu = createPaneContextMenu()

        paneVersions.setOnContextMenuRequested { event ->
            if (!event.isConsumed) {
                paneContextMenu.show(boxVersions.scene.window, event.screenX, event.screenY)
                event.consume()
            }
        }
        refreshVersions()

        scheduler.scheduleAtFixedRate({
            Platform.runLater {
                refreshVersions()
            }
        }, 0, 1, TimeUnit.SECONDS)
    }

    private fun refreshVersions() {
        //println("обновляем список ${System.currentTimeMillis()}")
        boxVersions.children.clear()

        val directory = File(Globals.versionsDir)
        val files: Array<File>? = directory.listFiles()

        if (files == null) return

        for (file in files) {
            if (file.isFile() && file.extension.lowercase() == Globals.versionsExt) {
                addVersionTile(file.name.substringBeforeLast('.'))
            }
        }
    }

    fun shutdownScheduler() {
        scheduler.shutdownNow()
        println("VersionsController scheduler shut down.")
    }

    private fun addVersionTile(name: String, iconPath: String="/org/orbis/application/images/voxelcore.png") {
        val versionIcon = ImageView()
        val label = Label(name)
        label.styleClass.add("pixel-label")


        try {
            val image = Image(javaClass.getResourceAsStream(iconPath))
            versionIcon.image = image

            versionIcon.fitWidth = 43.0
            versionIcon.fitHeight = 49.0
        } catch (e: Exception) {
            println(e)
        }

        val versionVBox = VBox(versionIcon, label)
        val contextMenu = createVersionContextMenu(name)

        versionVBox.setOnContextMenuRequested { event ->
            contextMenu.show(versionVBox.scene.window, event.screenX, event.screenY)
            event.consume()
        }

        versionVBox.alignment = Pos.CENTER
        versionVBox.spacing = 5.0
        versionVBox.prefWidth = 64.0
        versionVBox.styleClass.add("version-tile")
        boxVersions.children.add(versionVBox)
    }

    private fun showAddVersionDialog() {
        try {

            val fxmlLoader = FXMLLoader(javaClass.getResource("/org/orbis/application/stages/download_version.fxml"))

            val root: Parent = fxmlLoader.load()
            val dialogStage = Stage()
            dialogStage.title = "Добавить Версию"
            dialogStage.initModality(Modality.WINDOW_MODAL)
            dialogStage.initOwner(boxVersions.scene.window)

            val controller: DownloaderController = fxmlLoader.getController()
            controller.setDialogStage(dialogStage)
            val scene = Scene(root)
            dialogStage.scene = scene
            dialogStage.showAndWait()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createPaneContextMenu(): ContextMenu {
        val contextMenu = ContextMenu()
        val addItem = MenuItem("Добавить версию")
        addItem.setOnAction {
            println("Открываем диалог добавления версии...")
            showAddVersionDialog()
        }
        contextMenu.items.add(addItem)
        return contextMenu
    }

    private fun createVersionContextMenu(tileName: String): ContextMenu {
        val contextMenu = ContextMenu()

        val deleteItem = MenuItem("Удалить")

        deleteItem.setOnAction {
            println("Удаление версии $tileName...")
            try {
                val versionPath = "${Globals.versionsDir}$tileName.${Globals.versionsExt}"
                val versionFile = File(versionPath)

                if (!versionFile.exists()) return@setOnAction

                println(versionFile.delete())
            } catch (e: Exception) {
                println("Ошибка при удалении версии: $e")
            }
        }

        contextMenu.items.addAll(deleteItem)

        return contextMenu
    }
}