module org.orbis.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires org.json;

    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;

    opens org.orbis.application to javafx.fxml;
    opens org.orbis.application.controllers to javafx.fxml;
    opens org.orbis.core to kotlinx.serialization.core, kotlinx.serialization.json;

    exports org.orbis.application;
}