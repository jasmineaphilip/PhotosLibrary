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

public class Photos extends Application implements Serializable {
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/Photos.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		Controller controller = loader.getController();
		controller.start(primaryStage);
		
		//Image image = new Image(new FileInputStream("/Desktop/testImage.jpg"));
//		ImageView imageView = new ImageView(image);
//		imageView.setX(50);;
//		imageView.setY(25);
//		imageView.setFitHeight(455); 
//	    imageView.setFitWidth(500); 
//	    imageView.setPreserveRatio(true);

		Scene scene = new Scene(root, 600, 600); //width, length
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photos Library");
		//primaryStage.setResizable(false);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() {
		Controller.serializeUsers();
	}

}