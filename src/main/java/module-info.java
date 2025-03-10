module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;

    opens application to javafx.fxml;
    exports application;
}
