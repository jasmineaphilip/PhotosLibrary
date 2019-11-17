package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
		if (albumName.equals("")) {
			Alert a = new Alert(AlertType.ERROR, "Enter Album Name", ButtonType.CANCEL);
			a.show();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION, "Add " + albumName +"?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.YES) {
			addAlbumText.setText("");
			Album newAlbum = new Album(albumName);
			albums.add(newAlbum);
			
			listView.getSelectionModel().select(albums.indexOf(newAlbum));
			//displayEditInfo();
			listView.setItems(albums);
		} else {
			return;
		}
	}
	
	public void renameAlbum() throws IOException {
		while (albums.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Library is empty. Add albums to proceed.", ButtonType.CANCEL);
			a.show();	
			return;
		}
		Album album = listView.getSelectionModel().getSelectedItem();
		Album album1 = new Album (editAlbumText.getText());
		//String newAlbumName = editAlbumText.getText();
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Edit " + album.getName()  +"?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			if ((album1.getName()).equals("")) {
				Alert a = new Alert(AlertType.ERROR, "Enter Album Name", ButtonType.CANCEL);
				a.show();	
				return;
			} 
			for(Album a: albums) {
				if(a.getName().equals(album1.getName())) {
					Alert alert1 = new Alert(AlertType.ERROR, "This album already exists. Please enter new album name.", ButtonType.OK);
					alert1.show();
					return;
				}
			}
			//deleteAlbumInfoEdit(album1);
			int index = albums.indexOf(album);
			albums.remove(index);
			albums.add(album1);
			listView.getSelectionModel().select(albums.indexOf(album1));
			listView.setItems(albums);
		}
	}
	
	
	
	public void deleteAlbum() throws IOException {
		while (albums.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Library is empty. Add albums to proceed.", ButtonType.CANCEL);
			a.show();	
			return;
		}
		Album album = listView.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + album.getName() + "?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			//if(albums.isEmpty()) {emptySongInfo(); return;}
			int index = albums.indexOf(album);
			albums.remove(index);
			listView.getSelectionModel().select(index);
			displayEditInfo();
		} else {
			return;
		}
	}
	
	
	
	
	

}
