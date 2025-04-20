module edu.ijse.therapycenter.therapycenter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;

    requires java.naming;
    requires modelmapper;
    requires bcrypt;
    requires java.desktop;

    opens edu.ijse.therapycenter.controller to javafx.fxml;

    opens edu.ijse.therapycenter.entity to org.hibernate.orm.core;
    opens edu.ijse.therapycenter.config to jakarta.persistence;



    exports edu.ijse.therapycenter;
    exports edu.ijse.therapycenter.controller;
}