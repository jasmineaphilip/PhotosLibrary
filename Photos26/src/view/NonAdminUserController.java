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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
	@FXML Button openAlbumButton;
	@FXML Text welcomeText;
	
	ObservableList<Album> albums = FXCollections.observableArrayList();
	private User user;
	

	public void start(Stage mainStage, User user) {
		welcomeText.setText("Welcome, "  + user.getUsername() + "!");
		this.user = user;
		//System.out.println(user.getAlbums());
		for(int i=0;i<user.getAlbums().size();i++) {
			albums.add(user.getAlbums().get(i));
		}
		listView.setItems(albums);
	}
	
	public void displayEditInfo() {
		if(albums.isEmpty()) {emptyAddAndRenameInfo(); return;}
		Album album = listView.getSelectionModel().getSelectedItem();
		editAlbumText.setText(album.getName());
	}
	
	public void emptyAddAndRenameInfo() {
		addAlbumText.setText("");
		editAlbumText.setText("");
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
			emptyAddAndRenameInfo();
			Album newAlbum = new Album(albumName);
			user.getAlbums().add(newAlbum);
			albums.add(newAlbum);
			//if (user.getAlbum(); == null) System.out.println("why null");
			//int index = Controller.allUsers.indexOf(user);
			//System.out.println(index);
			//Controller.allUsers.get(index).albums.add(newAlbum);
			listView.setItems(albums);
			listView.getSelectionModel().select(albums.indexOf(newAlbum));
			
			
			
			displayEditInfo();
		} else {
			return;
		}
	}
	
	public void renameAlbum() throws IOException {
		while (user.getAlbums().isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Library is empty. Add albums to proceed.", ButtonType.CANCEL);
			a.show();	
			return;
		}
		int index = user.getAlbums().indexOf(listView.getSelectionModel().getSelectedItem());
		Album album = listView.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Rename " + album.getName()  +"?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			if (editAlbumText.getText().equals("")) {
				Alert a = new Alert(AlertType.ERROR, "Enter Album Name", ButtonType.OK);
				a.show();
				displayEditInfo();
				return;
			} 
			for(Album a: user.getAlbums()) {
				if(a.getName().equals(editAlbumText.getText())) {
					Alert alert1 = new Alert(AlertType.ERROR, "This album already exists. Please enter new album name.", ButtonType.OK);
					alert1.show();
					displayEditInfo();
					return;
				}
			}
			album.setName(editAlbumText.getText());
			int index1 = user.getAlbums().indexOf(album);
			user.getAlbums().get(index1).setName(editAlbumText.getText());
			albums.set(index, album);
			listView.setItems(albums);
			listView.getSelectionModel().select(albums.indexOf(album));
			displayEditInfo();
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
			user.getAlbums().remove(index);
			listView.getSelectionModel().select(index);
			displayEditInfo();
		} else {
			return;
		}
	}
	
	public void openAlbum(ActionEvent event) throws IOException {
		Stage stage = (Stage)openAlbumButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/NonAdminAlbum.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		//Album selectedAlbum = obsAlbums.get(albumList.getSelectionModel().getSelectedIndex());
//		AlbumDisplayController controller = loader.getController();
//		controller.start(stage);

		Scene scene = new Scene(root, 719, 651);
		stage.setScene(scene);
		stage.show();
		
//		FXMLLoader loader = new FXMLLoader (getClass().getResource("NonAdminAlbum.fxml"));
//		Parent parent = (Parent) loader.load();
//		
//		NonAdminAlbumController ctrl = loader.getController();
//		Scene scene = new Scene(parent);
//		
//		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//		
//		ctrl.start(window, user);
//		
//		window.setScene(scene);
//		window.show();
	}
	
	
	
	
	

}
