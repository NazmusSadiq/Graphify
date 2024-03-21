module org.example.graphify {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;
    requires java.logging;
    requires org.controlsfx.controls;

    opens org.example.graphify to javafx.fxml;
    exports org.example.graphify;
}