module org.example.oop_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires static lombok;

    opens org.example.oop_project.controller to javafx.fxml;
    exports org.example.oop_project;
}