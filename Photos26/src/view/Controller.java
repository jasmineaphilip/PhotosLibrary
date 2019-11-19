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
	public User user;
	
	public void login(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		boolean userExists = false;
		if(username.equals("stock")) {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("StockUser.fxml"));
			Parent parent = (Parent) loader.load();
			
			StockUserController ctrl = loader.getController();
			Scene scene = new Scene(parent);
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			ctrl.start(window);
			
			window.setScene(scene);
			window.show();
		}
		
		else if(username.equals("admin")) {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("AdminUser.fxml"));
			Parent parent = (Parent) loader.load();
			
			AdminUserController ctrl = loader.getController();
			Scene scene = new Scene(parent);
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			
			ctrl.start(window);
			
			window.setScene(scene);
			window.show();
		}
		
		else {
			for(User u: allUsers) {
				if(u.getUsername().equals(username)) {
					user = u;
					userExists = true;
					break;
				}
			}
			if(userExists) {
				FXMLLoader loader = new FXMLLoader (getClass().getResource("NonAdminUser.fxml"));
				Parent parent = (Parent) loader.load();
				
				NonAdminUserController ctrl = loader.getController();
				Scene scene = new Scene(parent);
				
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				
				ctrl.start(window, user);
				
				window.setScene(scene);
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
		deserializeUsers();
	}
	
	public static ArrayList<User> allUsers = new ArrayList<User>();
	/**
	 * Stores the location of the file in which the user data is stored in.
	 */
	public static String filename = "data.dat";
	
	public static void serializeUsers() {
		try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			
			out.writeObject(allUsers);
			file.close();
		}
		catch(IOException ex) {
			System.out.println("IO Exception Caught");
		}
	}
	/**
	 * Deserializes all the users from the user.dat file into the users ArrayList. It is secure I swear.
	 */
	@SuppressWarnings("unchecked")
	public static void deserializeUsers() {
		try {
			  FileInputStream file = new FileInputStream(filename); 
	          ObjectInputStream in = new ObjectInputStream(file);
	          allUsers = (ArrayList<User>)in.readObject();
	          in.close();
	          file.close();
		}
		catch(IOException ex){
			System.out.println("IO Exception Caught");
		}
		catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
	}
}