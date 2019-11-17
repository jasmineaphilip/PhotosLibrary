package view;

import java.io.*;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

public class Controller {
	
	@FXML Button loginButton;
	@FXML TextField usernameField;
	@FXML Button quitButton;
	@FXML Button logOutButton;
	@FXML Parent root;
	
	public void login(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		boolean userExists = false;
		if(username.equals("stock")) {
			Parent loginParent = FXMLLoader.load(getClass().getResource("StockUser.fxml"));
			Scene stock = new Scene(loginParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(stock);
			window.show();
		}
		
		else if(username.equals("admin")) {
			Parent loginParent = FXMLLoader.load(getClass().getResource("AdminUser.fxml"));
			Scene admin = new Scene(loginParent);
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			window.setScene(admin);
			window.show();
		}
		
		else {
			for(User u: AdminUserController.users) {
				if(u.getUsername().equals(username)) {
					userExists = true;
					break;
				}
			}
			if(userExists) {
				Parent loginParent = FXMLLoader.load(getClass().getResource("NonAdminUser.fxml"));
				Scene nonadmin = new Scene(loginParent);
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				
				window.setScene(nonadmin);
				window.show();
			}
			else {
				Alert a = new Alert(AlertType.ERROR, "This user does not exist. Please enter valid user", ButtonType.OK);
				a.show();
			}
		}
	}
	
	public void quitApp(ActionEvent event) throws IOException {
		Stage stage = (Stage) quitButton.getScene().getWindow();
		stage.close();
	}

	public void start(Stage mainStage) {
		// TODO Auto-generated method stub
		
	}

}