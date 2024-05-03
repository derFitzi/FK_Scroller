module org.example.crazyjoesworld {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.crazyjoesworld to javafx.fxml;
    exports org.example.crazyjoesworld;
}