module org.example.crazyjoesworld {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens org.example.crazyjoesworld to javafx.fxml;
    exports org.example.crazyjoesworld;
}