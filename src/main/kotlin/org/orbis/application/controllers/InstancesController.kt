package org.orbis.application.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.geometry.Pos
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class InstancesController {
    @FXML lateinit var boxInstances: FlowPane
    @FXML lateinit var paneInstances: AnchorPane

    private val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    @FXML
    fun initialize() {
        val paneContextMenu = createPaneContextMenu()

        paneInstances.setOnContextMenuRequested { event ->
            if (!event.isConsumed) {
                paneContextMenu.show(boxInstances.scene.window, event.screenX, event.screenY)
                event.consume()
            }
        }
        //refreshVersions()

        scheduler.scheduleAtFixedRate({
            Platform.runLater {
                //refreshVersions()
            }
        }, 0, 1, TimeUnit.SECONDS)
    }

    private fun createPaneContextMenu(): ContextMenu {
        val contextMenu = ContextMenu()
        val addItem = MenuItem("Создать группу")
        addItem.setOnAction {
            println("Открываем диалог создания группы...")
            //showAddVersionDialog()
        }
        contextMenu.items.add(addItem)
        return contextMenu
    }
}