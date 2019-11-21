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
import model.SerializablePhoto;
import model.Tag;
import model.User;

public class NonAdminAlbumController {
	@FXML ListView<HBox> photoListView;
	@FXML ListView<Tag> tagsListView;
	@FXML ListView<String> existingTagsListView;
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
	ObservableList<String> existingTagsObs = FXCollections.observableArrayList();

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
		if(!currentUser.getUsername().equals("stock")) {
			for(String t:currentUser.getExistingTags()) {
				existingTagsObs.add(t);
				existingTagsListView.setItems(existingTagsObs);
			}
		}
		
	}
	
	public void quitApp(ActionEvent event){
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
	
	public void cancelAdd() {
		photoPathText.setText("");
	}
	
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
		if(t.getName().equalsIgnoreCase("location")) {
			for(Tag r: tagToBeAdded.getTags()) {
				if(r.getName().equalsIgnoreCase("location")){
					Alert alert = new Alert(AlertType.ERROR, "Location tag already exists.", ButtonType.OK);
					addTagNameText.setText("");
					addTagValueText.setText("");
					alert.show();
					return;
				}
			}
		}
		for(Tag r: tagToBeAdded.getTags()) {
			if(r.getName().equals(t.getName())
					&& r.getValue().equals(t.getValue())){
				Alert alert = new Alert(AlertType.ERROR, "This is a duplicate tag.", ButtonType.OK);
				addTagNameText.setText("");
				addTagValueText.setText("");
				alert.show();
				return;
					}
		}
		boolean exists = false;
		for(String s: currentUser.getExistingTags()) {
			if(s.equals(t.getName())) {
				exists = true;
				break;
			}
		}
		if(!exists) {
			currentUser.getExistingTags().add(t.getName());
			existingTagsObs.add(t.getName());
		}
		existingTagsListView.setItems(existingTagsObs);
		tagToBeAdded.getTags().add(t);
		tagsObs.add(t);
		tagsListView.setItems(tagsObs);
		displayInfo();
		addTagNameText.setText("");
		addTagValueText.setText("");
	}
	
	public void populateTagNameField() {
		String tagName = existingTagsListView.getSelectionModel().getSelectedItem();
		addTagNameText.setText(tagName);
	}
	
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
	
	public void displayInfo() {
		//if(photosObs.isEmpty()) {emptyAddEditAndInitialInfo(); return;}
		if(photoListView.getSelectionModel().getSelectedIndex()==-1) {
			return;
		}
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
	public void clearInfo() {
		editTagNameText.setText("");
		editTagValueText.setText("");
		editCaptionText.setText("");
		displayCaptionText.setText("");
		displayTimeText.setText("");
		tagsObs.clear();
		enlargeDisplay.setImage(null);
	}

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

