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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
/**
 * 
 * Provides implementation of functionality seen on the Non-Admin user scene, including editing/adding/deleting albums, and searching for photos based on certain filters
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
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
	@FXML Button searchButton;
	@FXML DatePicker fromDate;
	@FXML DatePicker toDate;
	@FXML TextField tagName1Text;
	@FXML TextField tagValue1Text;
	@FXML TextField tagName2Text;
	@FXML TextField tagValue2Text;
	@FXML RadioButton tagAnd;
	@FXML RadioButton tagOr;
	
	ObservableList<Album> albums = FXCollections.observableArrayList();
	ArrayList<Photo> searchResults = new ArrayList<Photo>();
	
	private User user;
	ToggleGroup group = new ToggleGroup();
	
	/**
	 * Populates the listview with the specific user's albums
	 * @param mainStage
	 * @param user
	 */
	public void start(Stage mainStage, User user) {
		welcomeText.setText("Welcome, "  + user.getUsername() + "!");
		this.user = user;
		for(int i=0;i<user.getAlbums().size();i++) {
			albums.add(user.getAlbums().get(i));
		}
		tagAnd.setToggleGroup(group);
		tagOr.setToggleGroup(group);
		listView.setItems(albums);
	}
	
	/**
	 * Autofills the Edit Album field the selected album's name
	 */
	public void displayEditInfo() {
		if(!(listView.getSelectionModel().getSelectedIndex()==-1)) {
			if(albums.isEmpty()) {emptyAddAndRenameInfo(); return;}
			Album album = listView.getSelectionModel().getSelectedItem();
			editAlbumText.setText(album.getName());
		}
	}
	
	/**
	 * Clears the add album and edit album fields 
	 */
	public void emptyAddAndRenameInfo() {
		addAlbumText.setText("");
		editAlbumText.setText("");
	}
	
	/**
	 * Exits the program while saving the user's updates, due to serialization
	 * @param event
	 * @throws IOException
	 */
	public void quitApp(ActionEvent event) throws IOException {
		Controller.serializeUsers();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Logsout of the current user's home page, bringing user back to the main sign-in page
	 * @throws IOException
	 */
	public void logOut() throws IOException {
		Stage stage = (Stage) root.getScene().getWindow();
		Parent loginParent = FXMLLoader.load(getClass().getResource("Photos.fxml"));
		Scene home = new Scene(loginParent);
		stage.setScene(home);
		stage.show();
	}
	
	/**
	 * Adds an album to the current user's list of albums and observeable list
	 * @throws IOException
	 */
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
			listView.setItems(albums);
			listView.getSelectionModel().select(albums.indexOf(newAlbum));
			displayEditInfo();
		} else {
			return;
		}
	}
	
	/**
	 * Renames an album base don user input and updates user's list of albums and observeable list with new name
	 * @throws IOException
	 */
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
	
	/**
	 * Deletes an album from the current user's list of albums and observeable list
	 * @throws IOException
	 */
	public void deleteAlbum() throws IOException {
		while (albums.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR, "Library is empty. Add albums to proceed.", ButtonType.OK);
			a.show();	
			return;
		}
		Album album = listView.getSelectionModel().getSelectedItem();
		if(album == null) {
			Alert a = new Alert(AlertType.ERROR, "Please select an album.", ButtonType.OK);
			a.show();	
			return;
		}
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
	
	/**
	 * Opens the selected album to show the composite photos
	 * @param event button press that triggers this method
	 * @throws IOException
	 */
	public void openAlbum(ActionEvent event) throws IOException {

		Album album = listView.getSelectionModel().getSelectedItem();
		if(album == null) {
			Alert a = new Alert(AlertType.ERROR, "Please select an album.", ButtonType.OK);
			a.show();	
			return;
		}
		FXMLLoader loader = new FXMLLoader (getClass().getResource("NonAdminAlbum.fxml"));
		Parent parent = (Parent) loader.load();
		
		NonAdminAlbumController ctrl = loader.getController();
		Scene scene = new Scene(parent);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		ctrl.start(window, album, user);
		
		window.setScene(scene);
		window.show();
	}
	
//	public void openAllPhotosAlbum(ActionEvent event) throws IOException {
//
//		Album album = user.getAllPhotos();
//		
//		FXMLLoader loader = new FXMLLoader (getClass().getResource("NonAdminAlbum.fxml"));
//		Parent parent = (Parent) loader.load();
//		
//		NonAdminAlbumController ctrl = loader.getController();
//		Scene scene = new Scene(parent);
//		
//		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//		ctrl.start(window, album);
//		
//		window.setScene(scene);
//		window.show();
//	}
	
	/**
	 * Searches for photos in the user's specific library based on the inputted tag names/value pairs and range of dates
	 * @param event 
	 * @throws IOException
	 */
	public void search(ActionEvent event) throws IOException {
		int toDateYear = toDate.getValue().getYear();
		int toDateDay = toDate.getValue().getDayOfYear();
		int fromDateYear = fromDate.getValue().getYear();
		int fromDateDay = fromDate.getValue().getDayOfYear();
		
		String tagName1 = tagName1Text.getText();
		String tagValue1 = tagValue1Text.getText();
		String tagName2 = tagName2Text.getText();
		String tagValue2 = tagValue2Text.getText();
		
		if (fromDate.getValue() == null && toDate.getValue() != null) {
			Alert a = new Alert(AlertType.ERROR, "Please enter a From Date.", ButtonType.OK);
			a.show();	
			return;
		}
		if (toDate.getValue() == null && fromDate.getValue() != null) {
			Alert a = new Alert(AlertType.ERROR, "Please enter a To Date.", ButtonType.OK);
			a.show();	
			return;
		}
		if (toDate.getValue().getYear() < fromDate.getValue().getYear()) {
			Alert a = new Alert(AlertType.ERROR, "Please enter a valid date range.", ButtonType.OK);
			a.show();	
			return;
		}
		if (toDate.getValue().getYear() == fromDate.getValue().getYear() && toDate.getValue().getDayOfYear() < fromDate.getValue().getDayOfYear()) {
			Alert a = new Alert(AlertType.ERROR, "Please enter a valid date range.", ButtonType.OK);
			a.show();	
			return;
		}
		
		if (fromDate.getValue() == null && toDate.getValue() == null) {
			//just search on tags
			
			//if tagname1 but not tagval1, or tagnam2 but not tagval2, ERROR
			if (!tagName1.equals("") && tagValue1.equals("")) {
				Alert a = new Alert(AlertType.ERROR, "Please fill Tag Value 1 field.", ButtonType.OK);
				a.show();	
				return;
			}
			if (!tagName2.equals("") && tagValue2.equals("")) {
				Alert a = new Alert(AlertType.ERROR, "Please fill Tag Value 2 field.", ButtonType.OK);
				a.show();	
				return;
			}

			//if tagval1 but not tagname1 or tagval2 but not tag name 2, ERROR
			if (!tagValue1.equals("") && tagName1.equals("")) {
				Alert a = new Alert(AlertType.ERROR, "Please fill Tag Name 1 field.", ButtonType.OK);
				a.show();	
				return;
			}
			if (!tagValue2.equals("") && tagName2.equals("")) {
				Alert a = new Alert(AlertType.ERROR, "Please fill Tag Name 2 field.", ButtonType.OK);
				a.show();	
				return;
			}
			
			if (group.getSelectedToggle() != tagAnd && group.getSelectedToggle() != tagOr) { //neither toggle
				//if tag1 && tag2, ERROR 
				if (!tagName1.equals("") && !tagName2.equals("")) {
					Alert a = new Alert(AlertType.ERROR, "Please select 'And' or 'Or' Tag toggle.", ButtonType.OK);
					a.show();	
					return;
				}
				
				//if neither tag1 nor tag2, ERROR
				if (tagName1.equals("") && tagName2.equals("")) {
					Alert a = new Alert(AlertType.ERROR, "Please enter Tag field(s) or select a date range for search.", ButtonType.OK);
					a.show();	
					return;
				}
				
				//if just tag1, do search on tag1
				if (!tagName1.equals("") && tagName2.equals("")) {
					for (int i = 0; i < user.getAllPhotos().size(); i++) {
						for (int j = 0; j < user.getAllPhotos().get(i).getTags().size(); j++) {
							if (user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName1)) {
								if (user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue1)) {
									searchResults.add(user.getAllPhotos().get(i));
								}
							}
						}
					}
				}
				
				//if just tag2, do search on tag2
				if (tagName1.equals("") && !tagName2.equals("")) {
					for (int i = 0; i < user.getAllPhotos().size(); i++) {
						for (int j = 0; j < user.getAllPhotos().get(i).getTags().size(); j++) {
							if (user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName2)) {
								if (user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue2)) {
									searchResults.add(user.getAllPhotos().get(i));
								}
							}
						}
					}
				}
				
			} else if (group.getSelectedToggle() == tagAnd) {
				//if tag1 but no tag2, ERROR
				if (!tagName1.equals("") && tagName2.equals("")) {
					Alert a = new Alert(AlertType.ERROR, "Please enter Tag 2 fields or select 'Or' toggle.", ButtonType.OK);
					a.show();	
					return;
				}
				
				//if tag2 but no tag1, ERROR
				if (tagName1.equals("") && !tagName2.equals("")) {
					Alert a = new Alert(AlertType.ERROR, "Please enter Tag 1 fields or select 'Or' toggle.", ButtonType.OK);
					a.show();	
					return;
				}
				
				//if tag1 and tag2, search on them
				if (!tagName1.equals("") && !tagName2.equals("")) {
					for (int i = 0; i < user.getAllPhotos().size(); i++) {
						for (int j = 0; j < user.getAllPhotos().get(i).getTags().size(); j++) {
							if (user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagName1)) {
								if (user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagValue1)) {
									if (user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName2)) {
										if (user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue2)) {
											searchResults.add(user.getAllPhotos().get(i));
										}
									}
								}
							}
						}
					}
				}
			} else { //tagOr
				for (int i = 0; i < user.getAllPhotos().size(); i++) {
					for (int j = 0; j < user.getAllPhotos().get(i).getTags().size(); j++) {
						if ((user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName1) && 
							user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue1)) || 
							(user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName2) && 
							user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue2))	) {
								searchResults.add(user.getAllPhotos().get(i));	
						}
					}
				}
			}
		}
		
		if (fromDate.getValue() != null && toDate.getValue() != null) {
			//search on tags and date
			
			//if tagname1 but not tagval1, or tagnam2 but not tagval2, ERROR
			if (!tagName1.equals("") && tagValue1.equals("")) {
				Alert a = new Alert(AlertType.ERROR, "Please fill Tag Value 1 field.", ButtonType.OK);
				a.show();	
				return;
			}
			
			//if tagval1 but not tagname1 or tagval2 but not tag name 2, ERROR
			
			if (group.getSelectedToggle() != tagAnd && group.getSelectedToggle() != tagOr) {
				//if tag1 && tag2, ERROR
				//if neither tag1 nor tag2, ERROR
				//if just tag1, do search on tag1 + date
				//if just tag2, do search on tag2 + date
			} else if (group.getSelectedToggle() == tagAnd) {
				
			} else { //tagOr
				for (int i = 0; i < user.getAllPhotos().size(); i++) {
					for (int j = 0; j < user.getAllPhotos().get(i).getTags().size(); j++) {
						if ((user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName1) && 
							user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue1)) || 
							(user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName2) && 
							user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue2))	) {
								searchResults.add(user.getAllPhotos().get(i));
						}
					}
				}
			}
		}
		
		
		if (group.getSelectedToggle() == tagAnd) {
			for (int i = 0; i < user.getAllPhotos().size(); i++) {
				for (int j = 0; j < user.getAllPhotos().get(i).getTags().size(); j++) {
					if (user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName1)) {
						if (user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue1)) {
							if (user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName2)) {
								if (user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue2)) {
									if (fromDateYear == -1 && toDateYear == -1) {
										searchResults.add(user.getAllPhotos().get(i));
									}
									
									int year = user.getAllPhotos().get(i).getYear(); 
									int day = user.getAllPhotos().get(i).getDay();
									if (year >= fromDateYear && year<= toDateYear) {
										if (day >= fromDateDay && day<= toDateDay) {
											searchResults.add(user.getAllPhotos().get(i));
										}
									}
								}
							}
						}
					}
				}
			}
		} else if (group.getSelectedToggle() == tagOr) {
			for (int i = 0; i < user.getAllPhotos().size(); i++) {
				for (int j = 0; j < user.getAllPhotos().get(i).getTags().size(); j++) {
					if ((user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName1) && 
						user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue1)) || 
						(user.getAllPhotos().get(i).getTags().get(j).getName().equals(tagName2) && 
						user.getAllPhotos().get(i).getTags().get(j).getValue().equals(tagValue2))	) {
							int year = user.getAllPhotos().get(i).getYear(); 
							int day = user.getAllPhotos().get(i).getDay();
							if (year >= fromDateYear && year<= toDateYear) {
								if (day >= fromDateDay && day<= toDateDay) {
									searchResults.add(user.getAllPhotos().get(i));
								}
							}	
					}
				}
			}
		}
		
		FXMLLoader loader = new FXMLLoader (getClass().getResource("SearchResults.fxml"));
		Parent parent = (Parent) loader.load();
		
		SearchController ctrl = loader.getController();
		Scene scene = new Scene(parent);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		
		ctrl.start(window, user, searchResults);
		
		window.setScene(scene);
		window.show();
	}
}
