module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // opens ui to javafx.fxml;
    opens ui.controller to javafx.fxml;

   // exports ui;
    exports ui.controller;

    exports gtpbms.bms.model;
}
