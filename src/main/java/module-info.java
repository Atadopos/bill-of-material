module link.pihda.billofmaterial {
    requires java.base;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires eclipselink;
    requires jakarta.persistence;
    requires atlantafx.base;
    requires java.naming;
    requires static lombok;
    requires org.apache.poi.ooxml;
    requires kotlin.stdlib;

    exports link.pihda.billofmaterial.enums;

    exports link.pihda.billofmaterial to lombok, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports link.pihda.billofmaterial.entity to lombok, eclipselink, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports link.pihda.billofmaterial.ui to lombok, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    exports link.pihda.billofmaterial.views to lombok, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;

    opens link.pihda.billofmaterial to lombok, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens link.pihda.billofmaterial.views to lombok, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens link.pihda.billofmaterial.ui to lombok, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;
    opens link.pihda.billofmaterial.entity to lombok, eclipselink, javafx.fxml, javafx.base, javafx.graphics, javafx.controls;


}
