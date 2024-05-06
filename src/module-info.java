module Skaicioukle2 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires org.apache.poi.ooxml;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
