module link.pihda.billofmaterial {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires jakarta.persistence;
    requires atlantafx.base;
    requires org.apache.poi.ooxml;
    requires kotlin.stdlib;

    exports link.pihda.billofmaterial.enums;

    exports link.pihda.billofmaterial to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports link.pihda.billofmaterial.entity to eclipselink, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports link.pihda.billofmaterial.ui to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports link.pihda.billofmaterial.views to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;

    opens link.pihda.billofmaterial to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens link.pihda.billofmaterial.views to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens link.pihda.billofmaterial.ui to javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens link.pihda.billofmaterial.entity to eclipselink, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
}
