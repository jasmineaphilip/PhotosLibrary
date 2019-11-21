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
import model.Album;
import model.Photo;
import model.User;

/**
 * Main controller for the first scene, involving signing in to a certain account.
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class Controller {
	
	@FXML Button loginButton;
	@FXML TextField usernameField;
	@FXML Button quitButton;
	@FXML Button logOutButton;
	@FXML Parent root;
	public User user;
	
	/**
	 * Based on inputted username, logs a user into their specific photo library.
	 * @param event button press on the login button
	 * @throws IOException
	 */
	public void login(ActionEvent event) throws IOException {
		String username = usernameField.getText();
		boolean userExists = false;
//		if(username.equals("stock")) {
//			FXMLLoader loader = new FXMLLoader (getClass().getResource("StockUser.fxml"));
//			Parent parent = (Parent) loader.load();
//			
//			StockUserController ctrl = loader.getController();
//			Scene scene = new Scene(parent);
//			
//			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//			
//			ctrl.start(window);
//			
//			window.setScene(scene);
//			window.show();
//		}
		
		 if(username.equals("admin")) {
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
	
	/**
	 * Exits the application.
	 * @param event button press on the quit button
	 * @throws IOException
	 */
	public void quitApp(ActionEvent event) throws IOException {
		Stage stage = (Stage) quitButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * On start, deserializers the users. 
	 * @param mainStage
	 */
	public void start(Stage mainStage) {
		deserializeUsers();
		ArrayList<Album> stockAlbums = new ArrayList<Album>();
		ArrayList<Photo> stockPhotos = new ArrayList<Photo>();
		Album stockAlbum = new Album("stock");
		for(int i=1;i<=5;i++) {
			File file = new File("Stock"+i+".jpg");
			Photo photo = new Photo();
			photo.setPath(file.getAbsolutePath());
			System.out.println(file.getAbsolutePath());
			photo.setCaption("Stock Photo #"+i);
			stockAlbum.addPhoto(photo);
			stockPhotos.add(photo);
		}
		stockAlbums.add(stockAlbum);
		allUsers.get(allUsers.indexOf(new User("stock"))).setAlbums(stockAlbums);
		
	}
	
	/**
	 * 
	 */
	public static ArrayList<User> allUsers = new ArrayList<User>();
	/**
	 * Stores the location of the file in which the user data is stored in.
	 */
	public static String filename = "data.dat";
	
	/**
	 * Serializes all users to the data.dat file in order to preserve user data between sessions.
	 */
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
	 * Deserializes all the users from the data.dat file into the users ArrayList.
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