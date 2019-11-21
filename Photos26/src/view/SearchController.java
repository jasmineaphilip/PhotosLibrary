package view;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

/**
 * Controller for the scene that displays search results
 * @author Jasmine Philip
 * @author Radhe Bangad
 *
 */
public class SearchController {
	
	@FXML ListView<HBox> searchResultsListView;
	@FXML TextField searchAlbumNameText;
	@FXML Button backButton;
	@FXML Button createSearchAlbumButton;

	ObservableList<HBox> photosBox = FXCollections.observableArrayList();
	User user1;
	Album searchAlbum;
	
	/**
	 * Displays the photo search results in a Listview
	 * @param mainStage the mainStage of the controller
	 * @param user current user who has performed the photo search
	 * @param searchResults list of photos that match the search filters
	 */
	public void start(Stage mainStage, User user, ArrayList<Photo> searchResults) {
		searchAlbum = new Album("albumName");
		user1 = user;
		for (int i = 0; i < searchResults.size(); i++) {
			Photo photo = searchResults.get(i);
			String imagePath = photo.getPath();
			
			Image image = new Image(new File(imagePath).toURI().toString());
		    ImageView imageView = new ImageView(image);
		    imageView.setFitHeight(50.0);
		    imageView.setFitWidth(50.0);
		    HBox hbox = new HBox(3);
		    Label l = new Label(photo.getCaption());
		    Label path = new Label(photo.getPath());
		    hbox.getChildren().addAll(imageView, l, path);
		    ((Label)(hbox.getChildren().get(2))).setVisible(false);
			photosBox.add(hbox);
			searchResultsListView.setItems(photosBox);
			searchAlbum.addPhoto(searchResults.get(i));
		}
		
	}
	
	/**
	 * Creates an album in the user's library from the search results
	 */
	public void createSearchAlbum() {
		searchAlbum.setName(searchAlbumNameText.getText());
		if (searchAlbum.getName().equals("")) {
			Alert alert = new Alert(AlertType.ERROR, "Enter an album name.", ButtonType.OK);
			alert.show();
			return;
		}
		searchAlbum.setOldestPhoto(searchAlbum.getOldestPhoto());
		searchAlbum.setNewestPhoto(searchAlbum.getNewestPhoto());
		for(Album a:user1.getAlbums()) {
			if(a.getName().equals(searchAlbumNameText.getText())) {
				Alert alert = new Alert(AlertType.ERROR, "Album name already exists.", ButtonType.OK);
				alert.show();
				return;
			}
		}
		user1.getAlbums().add(searchAlbum);
		Alert alert = new Alert(AlertType.INFORMATION, "Album Created.", ButtonType.OK);
		alert.show();
		return;
	}
	
	/**
	 * Returns from the search results scene to the user's Non-Admin home page
	 * @param event
	 * @throws IOException
	 */
	public void back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader (getClass().getResource("NonAdminUser.fxml"));
		Parent parent = (Parent) loader.load();
		
		NonAdminUserController ctrl = loader.getController();
		Scene scene = new Scene(parent);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		ctrl.start(window, user1);
		
		window.setScene(scene);
		window.show();
	}
}
