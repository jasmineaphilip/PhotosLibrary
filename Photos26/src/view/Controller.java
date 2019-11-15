package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
	
	public void login(ActionEvent event) throws IOException {
		Parent loginParent = FXMLLoader.load(getClass().getResource("another.fxml"));
		Scene stock = new Scene(loginParent);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		window.setScene(stock);
		window.show();
	}

	public void start(Stage mainStage) {
		// TODO Auto-generated method stub
		
	}

}
