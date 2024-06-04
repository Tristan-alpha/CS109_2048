module org.example.cs109project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens org.example.cs109project to javafx.fxml;
    exports org.example.cs109project;

    //dividing
//    requires javafx.controls;
//    requires javafx.fxml;
//    requires javafx.web;
//
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;
//
    exports Controller to javafx.fxml;
    opens Controller to javafx.fxml;
}