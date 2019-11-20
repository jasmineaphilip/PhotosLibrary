package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.SerializablePhoto;
import model.User;

public class NonAdminAlbumController {
	@FXML ListView<Photo> photoListView;
	@FXML MenuItem quitButton;
	@FXML MenuItem logOutButton;
	@FXML Parent root;
	@FXML Text albumNameText;
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
	
	ObservableList<Photo> photos = FXCollections.observableArrayList();
	private Album album;

	public void start(Stage mainStage, Album album) {
		albumNameText.setText(album.getName());
		
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
		Photo photo = photoListView.getSelectionModel().getSelectedItem();
		photos.remove(photo);
		album.removePhoto(photo);
		//photoListView.getSelectionModel().select(index);
		displayInfo();
	}
	public void browse() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		
		if (selectedFile != null && isImage(selectedFile)) {
			photoPathText.setText(selectedFile.getAbsolutePath());
		} else {
			Alert alert = new Alert(AlertType.ERROR, "This file is not an image. Please select an image file.", ButtonType.OK);
			alert.show();
			return;
		}
		
		
		
		
//		BufferedImage bufferedImage = ImageIO.read(selectedFile);
//		Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//		SerializablePhoto tempImage = new SerializablePhoto();
//		tempImage.setPhoto(image);
//		for (Photo p: album.getPhotos()) {
//        	if (tempImage.equals(p.getSerializableImage())) {
//        		Alert alert = new Alert(AlertType.ERROR, "Photo already exists in album.", ButtonType.OK);
//    			alert.show();
//    			return;
//        	}
//        }
//		Photo tempPhoto = null;
//		boolean photoFound = false;
		
		 
	}
	
	public void cancelAdd() {
		photoPathText.setText("");
	}
	public void addPhoto() {
		if (photoPathText.getText().contentEquals("")) {
			Alert alert = new Alert(AlertType.ERROR, "No image is selected. Please select an image file.", ButtonType.OK);
			alert.show();
			return;
		}
		Stage stage = (Stage) root.getScene().getWindow();
		
		String imagePath = photoPathText.getText();
		//System.out.println(imagePath);
		Image image = new Image(new File(imagePath).toURI().toString());
	    //Image image = new Image(imagePath);

		photoListView.setCellFactory(param -> new ListCell<Photo>());
	    ImageView imageView = new ImageView(image);
	    VBox bro = new VBox(imageView);
	    //photos.add(bro);
	    photoListView.setItems(photos);
	    imageView.setFitHeight(30.0);
	    imageView.setFitWidth(30.0);
	    
//	    photos.add(bro);
	    SerializablePhoto tempImage = new SerializablePhoto();
	    tempImage.setPhoto(image);
//	    for (Photo p: album.getPhotos()) {
//        	if (tempImage.equals(p.getSerializableImage())) {
//        		Alert alert = new Alert(AlertType.ERROR, "Photo already exists in album.", ButtonType.OK);
//    			alert.show();
//    			return;
//        	}
//        }
//	    photos.add(tempImage);
	    
	    VBox root = new VBox(10, imageView, addPhotoButton, browseButton);
	    //photos.add(root);
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.setTitle("");
	    stage.show();
 		
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
	public boolean isImage(File file) {
		try {
		    BufferedImage image = ImageIO.read(file);
		    if (image == null) {
		        return false;
		        //System.out.println("The file"+file+"could not be opened, it is not an image");
		    }
		} catch(IOException ex) {
		    //System.out.println("The file"+file+"could not be opened , an error occurred.");
		    return false;
		}
		return true;
	}
	
	public void displayInfo() {
		if(photos.isEmpty()) {emptyAddEditAndInitialInfo(); return;}
		Photo photo = photoListView.getSelectionModel().getSelectedItem();
		displayCaptionText.setText(photo.getCaption());
		editCaptionText.setText(photo.getCaption());
		editTagNameText.setText(photo.getTagName());
		editTagValueText.setText(photo.getTagValue());
		//displayTimeText.setText(getPicTime());
		//displayTagsText.setText(photo.getTags());
	}
	public void emptyAddEditAndInitialInfo() {
		editTagNameText.setText("");
		editTagValueText.setText("");
		editCaptionText.setText("");
		displayCaptionText.setText("");
		displayTimeText.setText("");
		displayTagsText.setText("");
	}

	
}

