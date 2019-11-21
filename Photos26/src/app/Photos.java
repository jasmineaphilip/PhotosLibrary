// Created by Radhe Bangad and Jasmine Philip

package app;

import java.io.*;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;

/**
 * This main Photos class calls the initial controller to begin the program.
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class Photos extends Application implements Serializable {
	
	private static final long serialVersionUID = -7201791085130963483L;

	/**
	 * Begins the program by loading the controller and first scene
	 * @param primaryStage first stage of the photo library
	 */
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/Photos.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Controller controller = loader.getController();
		controller.start(primaryStage);
		Scene scene = new Scene(root, 600, 600); //width, length
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photos Library");
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}

	/**
	 * The main method calls the start method of the controller, starting the program
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Allows user to quit program with saved updates using built in closing functionality, rather than just through the app
	 */
	@Override
	public void stop() {
		Controller.serializeUsers();
	}

}