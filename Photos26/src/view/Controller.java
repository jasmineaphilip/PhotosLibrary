package view;

import java.io.*;
import java.util.*;

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
	@FXML Button quitButton;
	@FXML Button logOutButton;
	
	public void login(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		if(username.equals("stock")) {
			Parent loginParent = FXMLLoader.load(getClass().getResource("StockUser.fxml"));
			Scene stock = new Scene(loginParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(stock);
			window.show();
		}
		
		if(username.equals("admin")) {
			Parent loginParent = FXMLLoader.load(getClass().getResource("AdminUser.fxml"));
			Scene stock = new Scene(loginParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(stock);
			window.show();
		}
	}
	
	public void quitApp(ActionEvent event) throws IOException {
		Stage stage = (Stage) quitButton.getScene().getWindow();
		stage.close();
	}
	
	public void logOut() throws IOException {
		Stage stage = (Stage) logOutButton.getScene().getWindow();
		//do the loggin out
	}

	public void start(Stage mainStage) {
		// TODO Auto-generated method stub
		
	}

}