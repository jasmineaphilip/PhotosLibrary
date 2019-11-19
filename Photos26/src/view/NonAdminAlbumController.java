package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class NonAdminAlbumController {
	@FXML MenuItem quitButton;
	@FXML MenuItem logOutButton;
	@FXML Parent root;
	@FXML TextField displayCaptionText;
	@FXML TextField displayTimeText;
	@FXML TextField displayTagsText;
	@FXML Button browseButton;
	@FXML TextField photoPathText;
	@FXML Button addPhotoButton;
	@FXML Button cancelAddButton;
	@FXML Button deletePhotoButton;
	@FXML TextField editTagNameText;
	@FXML Button editTagNameButton;
	@FXML TextField editTagValueText;
	@FXML Button editTagValueButton;
	@FXML TextField editCaptionText;
	@FXML Button editCaptionButton;
	@FXML TextField destinationAlbumText;
	@FXML Button movePhotoButton;
	@FXML Button copyPhotoButton;


	public void start(Stage mainStage, User user) {
		
		
	}
	
	public void quitApp(ActionEvent event) throws IOException {
		Controller.serializeUsers();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
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
	
	public void deletePhoto() {
		
	}
	public void browse() {
		
	}
	public void addPhoto() {
		
	}
	public void cancelAdd() {
		
	}
	public void editTagName() {
		
	}
	public void editTagValue() {
		
	}
	public void editCaption() {
		
	}
	public void movePhoto() {
		
	}
	public void copyPhoto() {
		
	}
	
}

