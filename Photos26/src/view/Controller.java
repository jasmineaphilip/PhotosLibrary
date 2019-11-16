package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
	
	@FXML Button loginButton;
	@FXML TextField usernameField;
	
	public void login(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		if(username.equals("stock")) {
			Parent loginParent = FXMLLoader.load(getClass().getResource("another.fxml"));
			Scene stock = new Scene(loginParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(stock);
			window.show();
		}
	}

	public void start(Stage mainStage) {
		// TODO Auto-generated method stub
		
	}

}
