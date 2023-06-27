module com.example.coc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.coc to javafx.fxml;
    exports com.example.coc;
}