module org.orbis.orbis {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens org.orbis.orbis to javafx.fxml;
    exports org.orbis.orbis;
}