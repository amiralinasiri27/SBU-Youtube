module com.example.youtube {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.base;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires com.google.gson;
    requires java.desktop;
    opens com.example.youtube.Model to com.google.gson;
    opens com.example.youtube to javafx.fxml;
    exports com.example.youtube;
    exports com.example.youtube.Controller;
    opens com.example.youtube.Controller to javafx.fxml;

}