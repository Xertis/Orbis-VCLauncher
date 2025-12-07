module org.orbis.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires org.json;


    opens org.orbis.application to javafx.fxml;
    opens org.orbis.application.controllers to javafx.fxml;
    exports org.orbis.application;
}