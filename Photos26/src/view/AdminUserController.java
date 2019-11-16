package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class AdminUserController {
	
	@FXML TextField createUsernameField;
	@FXML TextField createFullnameField;
	@FXML TextField deleteUsernameField;
	@FXML TextField deleteFullnameField;
	@FXML Button createUserButton;
	@FXML Button deleteUserButton;
	
	private ObservableList<User> users;
	
	public void start(Stage mainStage) {
		users = FXCollections.observableArrayList();
		
	}

}
