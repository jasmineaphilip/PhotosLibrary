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

/**
 * AdminUserContoller class handles all functionality on the scene when the username "admin" is inputted at login, most importantly, the ability to add and delete users, as well as see all created users
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class AdminUserController {
	
	@FXML ListView<User> listView;
	@FXML TextField createUsernameField;
	@FXML TextField deleteUsernameField;
	@FXML Button createUserButton;
	@FXML Button deleteUserButton;
	@FXML Parent root;
	@FXML MenuItem logOutButton;
	@FXML MenuItem quitButton;
	
	ObservableList<User> users = FXCollections.observableArrayList();
	
	/**
	 * AdminUserController's start sets the listview to display all the created users
	 * @param mainStage Stage that shows the main scene for admin
	 * @throws IOException Exceptions
	 */
	public void start(Stage mainStage) throws IOException{
		for (User u: Controller.allUsers) {
			users.add(u);
		}
		listView.setItems(FXCollections.observableList(users));
	}
	
	/**
	 * Sets the text field for deleting user's to the username of the user selected from the listview
	 */
	public void displayDeleteInfo() {
		if(users.isEmpty()) {emptyCreateAndDeleteInfo(); return;}
		User user = listView.getSelectionModel().getSelectedItem();
		deleteUsernameField.setText(user.getUsername());
	}
	
	/**
	 * Clears the fields for creating and deleting users
	 */
	public void emptyCreateAndDeleteInfo() {
		createUsernameField.setText("");
		deleteUsernameField.setText("");
	}
	
	/**
	 * Creates a user by adding a new user object to the list of all users
	 * @param event button press of "Create User" button
	 */
	public void createUser(ActionEvent event) {
		String username = createUsernameField.getText();
		
		if (username.equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Please enter new username.", ButtonType.OK);
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
		Controller.allUsers.add(user);
		emptyCreateAndDeleteInfo();
		listView.setItems(users);
	}
	
	/**
	 * Deletes a user by removing them from the list of all users
	 * @param event button press of "Delete User" button
	 */
	public void deleteUser(ActionEvent event) {
		if (users.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "There are no users on file. Add users to proceed.", ButtonType.OK);
			a.show();	
			return;
		}
		User user = listView.getSelectionModel().getSelectedItem();
		if (user.getUsername().equals("stock")) {
			Alert a = new Alert(AlertType.ERROR, "Cannot delete stock user.", ButtonType.OK);
			a.show();	
			return;
		}
		else {
			users.remove(users.indexOf(user));
			Controller.allUsers.remove(user);
			emptyCreateAndDeleteInfo();
			listView.setItems(users);
		}
	}
	
	/**
	 * Logs out admin from the Admin User scene, bringing them back to the login scene
	 * @throws IOException throws exception
	 */
	public void logOut() throws IOException {
		//Stage stage = (Stage) logOutButton.getScene().getWindow();
		Stage stage = (Stage) root.getScene().getWindow();
		Parent loginParent = FXMLLoader.load(getClass().getResource("Photos.fxml"));
		Scene home = new Scene(loginParent);
		//Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(home);
		stage.show();
	}
	
	/**
	 * Quits the application and serializes the users before doing so, in order to save the session information
	 * @param event button press of "Quit" button
	 * @throws IOException throws exception
	 */
	public void quitApp(ActionEvent event) throws IOException {
		Controller.serializeUsers();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

}
