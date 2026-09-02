module com.example.smartorganizer {
    requires javafx.controls;
    requires javafx.media;
    opens com.example.smartorganizer to javafx.graphics;

    exports com.example.smartorganizer;
}
