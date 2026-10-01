module com.example.cardgame24 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cardgame24 to javafx.fxml;
    exports com.example.cardgame24;
}