module com.example.sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;

    opens com.example.sms to javafx.fxml;
    exports com.example.sms;
    exports com.example.sms.f4f;
    opens com.example.sms.f4f to javafx.fxml;
}