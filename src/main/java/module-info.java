module com.example.labwithbase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;


    opens com.example.labwithbase to javafx.fxml;
    exports com.example.labwithbase;
}