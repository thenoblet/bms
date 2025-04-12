module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;

    // opens ui to javafx.fxml;
    opens ui.controller to javafx.fxml;
    opens gtpbms.bms.model to com.google.gson;

   // exports ui;
    exports ui.controller;

    exports gtpbms.bms.model;
}
