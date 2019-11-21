package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.imageio.ImageIO;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

/**
 * Controller to handle all functionality for a non admin user, including deleting and adding photos, adding and editing photo tags, moving or copying the photo to other albums
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class NonAdminAlbumController {
	@FXML ListView<HBox> photoListView;
	@FXML ListView<Tag> tagsListView;
	@FXML MenuItem quitButton;
	@FXML MenuItem logOutButton;
	@FXML Parent root;
	@FXML Text albumNameText;
	@FXML TextField displayCaptionText;
	@FXML TextField displayTimeText;
	@FXML Button browseButton;
	@FXML TextField photoPathText;
	@FXML Button addPhotoButton;
	@FXML Button cancelAddButton;
	@FXML Button deletePhotoButton;
	@FXML TextField editTagNameText;
	@FXML Button editTagNameButton;
	@FXML TextField editTagValueText;
	@FXML Button editTagValueButton;
	@FXML TextField addTagValueText;
	@FXML TextField addTagNameText;
	@FXML TextField editCaptionText;
	@FXML Button editCaptionButton;
	@FXML TextField destinationAlbumText;
	@FXML Button movePhotoButton;
	@FXML Button copyPhotoButton;
	@FXML ImageView enlargeDisplay;
	
	ObservableList<HBox> photosObs = FXCollections.observableArrayList();
	Album currentAlbum;
	User currentUser;
	ObservableList<Tag> tagsObs = FXCollections.observableArrayList();

	/**
	 * Start method for NonAdminAlbum Controller 
	 * @param mainStage sent in from NonAdminUserController's openAlbum method populates the imageview which displays all the photos in the selected album
	 * @param album the album to be opened
	 * @param user the current session's user
	 */
	public void start(Stage mainStage, Album album, User user) {
		currentAlbum = album;
		currentUser = user;
		albumNameText.setText(album.getName());
		for(Photo p:currentAlbum.getPhotos()) {
			String imagePath = p.getPath();
			
			Image image = new Image(new File(imagePath).toURI().toString());
		    ImageView imageView = new ImageView(image);
		    imageView.setFitHeight(50.0);
		    imageView.setFitWidth(50.0);
		    HBox hbox = new HBox(3);
		    Label l = new Label(p.getCaption());
		    Label path = new Label(p.getPath());
		    hbox.getChildren().addAll(imageView, l, path);
		    ((Label)(hbox.getChildren().get(2))).setVisible(false);
//		    ((Label)(hbox.getChildren().get(2))).setPrefHeight(0);
//		    ((Label)(hbox.getChildren().get(2))).setPrefWidth(0);
		    photosObs.add(hbox);
		    photoListView.setItems(photosObs);
		}
		
	}
	
	/**
	 * Serializes the users before quitting the application, so user data is saved across sessions
	 * @param event pressing the MenuItem "Quit"
	 */
	public void quitApp(ActionEvent event){
		Controller.serializeUsers();
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Logs out the current user by bringing them back to the first login scene
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
	 * Deletes a photo from the current album, also removing it from the album listview. If the photo is not duplicated in other albums, it will be deleted from the arraylist allPhotos as well
	 */
	public void deletePhoto() {
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		photosObs.remove(photo);
		Photo toBeDeleted = null;
		for(Photo p: currentAlbum.getPhotos()) {
			if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
				toBeDeleted = p;
			}
		}
		currentAlbum.getPhotos().remove(toBeDeleted);
		if(!photosObs.isEmpty()) {
			displayInfo();
		}
		boolean repeat = false;
		for(Album a:currentUser.getAlbums()) {
			for(Photo p:a.getPhotos()) {
				if (p.getPath().equals(toBeDeleted.getPath())){
					repeat = true;
//					Alert alert = new Alert(AlertType.CONFIRMATION, "Would you like to delete all instances of this photo?", ButtonType.YES, ButtonType.NO);
//					alert.showAndWait();
//					
//					if (alert.getResult() == ButtonType.YES) {
//						deleteAllInstances(toBeDeleted.getPath());
//						currentUser.getAllPhotos().remove(toBeDeleted);
//					}
					break;
				}
			}
			if(repeat) {break;}
		}
		if(!repeat) {
			currentUser.getAllPhotos().remove(toBeDeleted);
		}
		if(photosObs.isEmpty()) {
			clearInfo();
		}
	}
	
	/**
	 * Deletes all instances of a photo, across all albums
	 * @param path file path of the photo to be deleted, used to compare it with other photos to find all instances of that photo
	 */
	public void deleteAllInstances(String path) {
		for(Album a:currentUser.getAlbums()) {
			for(Photo p:a.getPhotos()) {
				if (p.getPath().equals(path)){
					a.getPhotos().remove(p);
				}
				continue;
			}
		}
	}
	
	/**
	 * Allows user to browse their local file directory and pick an image to add to the album (as long as it is an image)
	 */
	public void browse() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		
		if (selectedFile != null && isImage(selectedFile)) {
			photoPathText.setText(selectedFile.getAbsolutePath());
		} else {
			Alert alert = new Alert(AlertType.ERROR, "This file is not an image. Please select an image file.", ButtonType.OK);
			photoPathText.setText("");
			alert.show();
			return;
		}
		 
	}
	
	/**
	 * If an add is cancelled, clears the file path in the Add Photo field to
	 */
	public void cancelAdd() {
		photoPathText.setText("");
	}
	
	/**
	 * Allows user to do a manual slide show of photos in the album, by selecting the previous photo
	 */
	public void previousPhoto() {
		HBox currentPhoto = photoListView.getSelectionModel().getSelectedItem();
		if(currentPhoto == null) {
			return;
		}
		int index = photosObs.indexOf(currentPhoto);
		if(index == 0) {
			return;
		}
		photoListView.getSelectionModel().select(index-1);
		displayInfo();
	}
	
	/**
	 * Allows user to do a manual slide show of photos in the album, by selecting the next photo
	 */
	public void nextPhoto() {
		HBox currentPhoto = photoListView.getSelectionModel().getSelectedItem();
		if(currentPhoto == null) {
			return;
		}
		int index = photosObs.indexOf(currentPhoto);
		if(index == photosObs.size()-1){
			index = -1;
		}
		photoListView.getSelectionModel().select(index+1);
		displayInfo();
	}
	
	/**
	 * Allows user to add a photo to their album libary, after it was selected using the browse method. The photo will also be added to the allPhotos arraylist and the album's listview.
	 */
	public void addPhoto() {
		if (photoPathText.getText().contentEquals("")) {
			Alert alert = new Alert(AlertType.ERROR, "No image is selected. Please select an image file.", ButtonType.OK);
			alert.show();
			return;
		}
		
		String imagePath = photoPathText.getText();
		for(Photo p: currentAlbum.getPhotos()) {
			if(p.getPath().equals(imagePath)) {
				Alert alert = new Alert(AlertType.ERROR, "This photo already exists in the album.", ButtonType.OK);
				photoPathText.setText("");
				alert.show();
				return;
			}
		}
		Photo photo = new Photo();
		photo.setPath(imagePath);
		currentAlbum.getPhotos().add(photo);
		currentAlbum.setOldestPhoto(currentAlbum.getOldestPhoto());
		currentAlbum.setNewestPhoto(currentAlbum.getNewestPhoto());
		Image image = new Image(new File(imagePath).toURI().toString());

	    ImageView imageView = new ImageView(image);
	    imageView.setFitHeight(50.0);
	    imageView.setFitWidth(50.0);
	    HBox hbox = new HBox(5);
	    Label l = new Label(photo.getCaption());
	    Label path = new Label(imagePath);
	    hbox.getChildren().addAll(imageView, l, path);
	    ((Label)(hbox.getChildren().get(2))).setVisible(false);
//	    ((Label)(hbox.getChildren().get(2))).setPrefHeight(0);
//	    ((Label)(hbox.getChildren().get(2))).setPrefWidth(0);
	    photosObs.add(hbox);
	    
	    photoListView.setItems(photosObs);
	    photoPathText.setText("");
	    currentUser.getAllPhotos().add(photo);

	}
	
	/**
	 * Deletes a tag (name value/pair) of a photo
	 */
	public void deleteTag() {
		Tag selectedTag = tagsListView.getSelectionModel().getSelectedItem();
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		if (selectedTag == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select a tag to delete.", ButtonType.OK);
			editTagNameText.setText("");
			editTagValueText.setText("");
			alert.show();
			return;
		}
		Photo tagToBeDeleted = null;
		if(!photosObs.isEmpty()) {
			for(Photo p: currentAlbum.getPhotos()) {
				if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
					tagToBeDeleted = p;
				}
			}
		}
		for(Tag r: tagToBeDeleted.getTags()) {
			if(r.equals(selectedTag)){
				tagsObs.remove(selectedTag);
			}
		}
		tagToBeDeleted.getTags().remove(selectedTag);
		
	}
	
	/**
	 * Adds a tag (name value/pair) to a photo
	 */
	public void addTag() {
		String name = addTagNameText.getText();
		String value = addTagValueText.getText();
		Tag t = new Tag(name, value);
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		if(photo == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please select a photo.", ButtonType.OK);
			addTagNameText.setText("");
			addTagValueText.setText("");
			alert.show();
			return;
		}
		if(name.equals("") || value.equals("")) {
			Alert alert = new Alert(AlertType.ERROR, "Please complete tag information.", ButtonType.OK);
			alert.show();
			return;
		}
		Photo tagToBeAdded = null;
		if(!photosObs.isEmpty()) {
			for(Photo p: currentAlbum.getPhotos()) {
				if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
					tagToBeAdded = p;
				}
			}
		}
		for(Tag r: tagToBeAdded.getTags()) {
			if(r.getName().contentEquals(t.getName())
					&& r.getValue().equals(t.getValue())){
				Alert alert = new Alert(AlertType.ERROR, "This is a duplicate tag.", ButtonType.OK);
				addTagNameText.setText("");
				addTagValueText.setText("");
				alert.show();
				return;
					}
		}
		
		tagToBeAdded.getTags().add(t);
		tagsObs.add(t);
		tagsListView.setItems(tagsObs);
		displayInfo();
		addTagNameText.setText("");
		addTagValueText.setText("");
	}
	
	/**
	 * Edits a selected photo's tag
	 */
	public void editTag() {
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		Photo tagToBeChanged = null;
		if(photo == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please select a photo.", ButtonType.OK);
			addTagNameText.setText("");
			addTagValueText.setText("");
			alert.show();
			return;
		}
		if(!photosObs.isEmpty()) {
			for(Photo p: currentAlbum.getPhotos()) {
				if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
					tagToBeChanged = p;
				}
			}
		}
		Tag selectedTag = tagsListView.getSelectionModel().getSelectedItem();
		if (selectedTag == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select a tag to edit.", ButtonType.OK);
			editTagNameText.setText("");
			editTagValueText.setText("");
			alert.show();
			return;
		}
		if(editTagNameText.getText().equals("") || editTagValueText.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR, "Please complete tag information.", ButtonType.OK);
			alert.show();
			return;
		}
		for(Tag r: tagToBeChanged.getTags()) {
			if(r.getName().equals(editTagNameText.getText())
					&& r.getValue().equals(editTagValueText.getText())){
				Alert alert = new Alert(AlertType.ERROR, "This is a duplicate tag.", ButtonType.OK);
				editTagNameText.setText("");
				editTagValueText.setText("");
				alert.show();
				return;
			}
		}
		for(Tag r: tagToBeChanged.getTags()) {
			if(r.getName().equals(selectedTag.getName())
					&& r.getValue().equals(selectedTag.getValue())){
				r.setName(editTagNameText.getText());
				r.setValue(editTagValueText.getText());
				editTagNameText.setText("");;
				editTagValueText.setText("");
			}
		}
		
		tagsListView.setItems(tagsObs);
		displayInfo();
		
		
	}
	
	/**
	 * Edits a selected photo's caption
	 */
	public void editCaption() {
		//replace child in Hbox. hbox.getchildren.set(i,label)
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		Photo captionToBeChanged = null;
		if(photo == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please select a photo.", ButtonType.OK);
			addTagNameText.setText("");
			addTagValueText.setText("");
			alert.show();
			return;
		}
		for(Photo p: currentAlbum.getPhotos()) {
			if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
				captionToBeChanged = p;
			}
		}
		Label label = new Label(editCaptionText.getText());
		photo.getChildren().set(1, label);
		captionToBeChanged.setCaption(editCaptionText.getText());
		displayCaptionText.setText(editCaptionText.getText());
		editCaptionText.setText("");
	}
	public void movePhoto() {
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		Photo photoToBeMoved = null;
		if(photo == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please select a photo.", ButtonType.OK);
			addTagNameText.setText("");
			addTagValueText.setText("");
			alert.show();
			return;
		}
		for(Photo p: currentAlbum.getPhotos()) {
			if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
				photoToBeMoved = p;
			}
		}
		
		Album albumToBeMovedIn = null;
		String destinationAlbum = destinationAlbumText.getText();
		for(Album a: currentUser.getAlbums()) {
			if(a.getName().equals(destinationAlbum)) {
				albumToBeMovedIn = a;
			}
		}
		if(albumToBeMovedIn == null) {
			Alert alert = new Alert(AlertType.ERROR, "Destination album does not exist.", ButtonType.OK);
			destinationAlbumText.setText("");
			alert.show();
			return;
		}
		int i = currentUser.getAlbums().indexOf(albumToBeMovedIn);
		currentUser.getAlbums().get(i).getPhotos().add(photoToBeMoved);
		deletePhoto();
//		currentAlbum.getPhotos().remove(photoToBeMoved);
//		photoListView.setItems(photosObs);
	}
	
	/**
	 * Copies a photo from the current album to another. Doesn't create another photo object, rather assigns the selected photo to another album as well as the current one
	 */
	public void copyPhoto() {
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		Photo photoToBeMoved = null;
		if(photo == null) {
			Alert alert = new Alert(AlertType.ERROR, "Please select a photo.", ButtonType.OK);
			addTagNameText.setText("");
			addTagValueText.setText("");
			alert.show();
			return;
		}
		for(Photo p: currentAlbum.getPhotos()) {
			if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
				photoToBeMoved = p;
			}
		}
		
		Album albumToBeMovedIn = null;
		String destinationAlbum = destinationAlbumText.getText();
		for(Album a: currentUser.getAlbums()) {
			if(a.getName().equals(destinationAlbum)) {
				albumToBeMovedIn = a;
			}
		}
		if(albumToBeMovedIn == null) {
			Alert alert = new Alert(AlertType.ERROR, "Destination album does not exist.", ButtonType.OK);
			destinationAlbumText.setText("");
			alert.show();
			return;
		}
		int i = currentUser.getAlbums().indexOf(albumToBeMovedIn);
		currentUser.getAlbums().get(i).getPhotos().add(photoToBeMoved);
		Alert alert = new Alert(AlertType.INFORMATION, "Copy Complete.", ButtonType.OK);
		alert.show();
		return;
	}
	
	/**
	 * Checks if the file chosen by the user in the browse method is an image, as only images are allowed in the photo library
	 * @param file file chosed by user in browse method
	 * @return true if the file is an image
	 */
	public boolean isImage(File file) {
		try {
		    BufferedImage image = ImageIO.read(file);
		    if (image == null) {
		        return false;
		    }
		} catch(IOException ex) {
		    return false;
		}
		return true;
	}
	
	/**
	 * Displays the photo's details, including caption, tag, and time of capture/upload
	 */
	public void displayInfo() {
		//if(photosObs.isEmpty()) {emptyAddEditAndInitialInfo(); return;}
		HBox photo = photoListView.getSelectionModel().getSelectedItem();
		Photo toBeDisplayed = null;
		if(!photosObs.isEmpty()) {	
			for(Photo p: currentAlbum.getPhotos()) {
				if(p.getPath().equals((((Label) photo.getChildren().get(2)).getText()))) {
					toBeDisplayed = p;
				}
			}
		}
		displayCaptionText.setText(toBeDisplayed.getCaption());
		tagsObs.clear();
		for(Tag t:toBeDisplayed.getTags()) {
			tagsObs.add(t);
		}
		tagsListView.setItems(tagsObs);
		
		String imagePath = (((Label) photo.getChildren().get(2)).getText());
		Image image = new Image(new File(imagePath).toURI().toString());
		enlargeDisplay.setImage(image);
		String time = toBeDisplayed.getCalendar().getTime().toString();
		displayTimeText.setText(time);
		
		//displayTimeText.setText(getPicTime());
		//displayTagsText.setText(photo.getTags());
	}
	
	/**
	 * Clears the fields that populate with the photo's details in order to edit
	 */
	public void clearInfo() {
		editTagNameText.setText("");
		editTagValueText.setText("");
		editCaptionText.setText("");
		displayCaptionText.setText("");
		displayTimeText.setText("");
		tagsObs.clear();
		enlargeDisplay.setImage(null);
	}
	
	/**
	 * Allows the user to go to the previous scene (NonAdminUserController) so they can select other albums
	 * @param event button press of "Back" button
	 * @throws IOException throws exception
	 */
	public void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader (getClass().getResource("NonAdminUser.fxml"));
		Parent parent = (Parent) loader.load();
		
		NonAdminUserController ctrl = loader.getController();
		Scene scene = new Scene(parent);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		ctrl.start(window, currentUser);
		
		window.setScene(scene);
		window.show();
	}
}

