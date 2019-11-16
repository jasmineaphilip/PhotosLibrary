package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

public class AdminUserController {
	
	@FXML         
	ListView<User> listView;
	@FXML TextField createUsernameField;
	@FXML TextField createFullnameField;
	@FXML TextField deleteUsernameField;
	@FXML TextField deleteFullnameField;
	@FXML Button createUserButton;
	@FXML Button deleteUserButton;
	
	ObservableList<User> users = FXCollections.observableArrayList();
	
	public void start(Stage mainStage) throws IOException{
		
		
	}
	
	public void displayDeleteInfo() {
		if(users.isEmpty()) {emptyCreateAndDeleteInfo(); return;}
		User user = listView.getSelectionModel().getSelectedItem();
		deleteFullnameField.setText(user.getFullname());
		deleteUsernameField.setText(user.getUsername());
	}
	
	public void emptyCreateAndDeleteInfo() {
		createFullnameField.setText("");
		createUsernameField.setText("");
		deleteFullnameField.setText("");
		deleteUsernameField.setText("");
	}
	
	public void createUser(ActionEvent event) {
		String fullname = createFullnameField.getText();
		String username = createUsernameField.getText();
		
		if (fullname.equals("") || username.equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Please fill out both fields", ButtonType.OK);
			a.show();	
			return;
		}
		
		User user = new User(fullname, username);
		for(User u: users) {
			if(u.getUsername().equals(user.getUsername())) {
				Alert a = new Alert(AlertType.ERROR, "This user already exists. Please enter new user information.", ButtonType.OK);
				a.show();
				return;
			}
		}
		
		users.add(user);
		emptyCreateAndDeleteInfo();
		listView.setItems(users);
	}
	
	public void deleteUser(ActionEvent event) {
		if (users.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "There are no users on file. Add users to proceed.", ButtonType.OK);
			a.show();	
			return;
		}
		User user = listView.getSelectionModel().getSelectedItem();
		users.remove(users.indexOf(user));
		emptyCreateAndDeleteInfo();
		listView.setItems(users);
		
	}

}
