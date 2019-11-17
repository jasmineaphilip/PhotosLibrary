package view;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

public class AdminUserController {
	
	@FXML ListView<User> listView;
	@FXML TextField createUsernameField;
	@FXML TextField deleteUsernameField;
	@FXML Button createUserButton;
	@FXML Button deleteUserButton;
	@FXML Parent root;
	@FXML MenuItem logOutButton;
	@FXML MenuItem quitButton;
	
	static ObservableList<User> users = FXCollections.observableArrayList();
	
	public void start(Stage mainStage) throws IOException{
		listView.setItems(FXCollections.observableList(users));
	}
	
	public void displayUsers(ActionEvent event) {
		listView.setItems(FXCollections.observableList(users));
	}
	
	public void displayDeleteInfo() {
		if(users.isEmpty()) {emptyCreateAndDeleteInfo(); return;}
		User user = listView.getSelectionModel().getSelectedItem();
		deleteUsernameField.setText(user.getUsername());
	}
	
	public void emptyCreateAndDeleteInfo() {
		createUsernameField.setText("");
		deleteUsernameField.setText("");
	}
	
	public void createUser(ActionEvent event) {
		String username = createUsernameField.getText();
		
		if (username.equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Please fill out both fields", ButtonType.OK);
			a.show();	
			return;
		}
		
		User user = new User(username);
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
	
	public void logOut() throws IOException {
		//Stage stage = (Stage) logOutButton.getScene().getWindow();
		Stage stage = (Stage) root.getScene().getWindow();
		Parent loginParent = FXMLLoader.load(getClass().getResource("Photos.fxml"));
		Scene home = new Scene(loginParent);
		//Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(home);
		stage.show();
	}
	
	public void quitApp(ActionEvent event) throws IOException {
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

}
