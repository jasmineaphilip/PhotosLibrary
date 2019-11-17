package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

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
import model.Album;
import model.User;

public class NonAdminUserController {
	@FXML ListView<Album> listView;
	@FXML MenuItem quitButton;
	@FXML MenuItem logOutButton;
	@FXML Parent root;
	@FXML TextField addAlbumText;
	@FXML Button addAlbumButton;
	@FXML TextField editAlbumText;
	@FXML Button renameAlbumButton;
	@FXML Button deleteAlbumButton;
	
	static ObservableList<Album> albums = FXCollections.observableArrayList();

	public void start(Stage mainStage) {
		listView.setItems(FXCollections.observableList(albums));
	}
	
	public void displayAlbums(ActionEvent event) {
		listView.setItems(FXCollections.observableList(albums));
	}
	
	public void displayEditInfo() {
		if(albums.isEmpty()) {emptyCreateAndDeleteInfo(); return;}
		Album album = listView.getSelectionModel().getSelectedItem();
		editAlbumText.setText(album.getName());
	}
	
	public void emptyCreateAndDeleteInfo() {
		addAlbumText.setText("");
		editAlbumText.setText("");
	}
	
	public void quitApp(ActionEvent event) throws IOException {
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
	
	public void addAlbum() throws IOException {
		String albumName = addAlbumText.getText();
		for(Album a: albums) {
			if(a.getName().equals(albumName)) {
				Alert alert = new Alert(AlertType.ERROR, "This album already exists. Please enter new album name.", ButtonType.OK);
				alert.show();
				return;
			}
		}
		Album album = new Album(albumName);
		albums.add(album);
		listView.setItems(albums);
	}
	
	public void renameAlbum() throws IOException {
		
	}
	
	public void deleteAlbum() throws IOException {
		
	}
	
	
	
	
	

}
