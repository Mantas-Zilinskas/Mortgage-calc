package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class RealMain extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("First.fxml"));
			Scene scene = new Scene(root,475,400);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Calc");
			primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void mains(String[] args) {
		launch(args);
	}
}
