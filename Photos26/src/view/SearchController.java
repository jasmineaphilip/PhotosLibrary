package view;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	
	@FXML ListView<HBox> searchResultListView;
	@FXML TextField searchAlbumNameText;
	@FXML Button backButton;
	@FXML Button createSearchAlbumButton;

	ObservableList<HBox> photosBox = FXCollections.observableArrayList();
	private static User user1;
	private static Album searchAlbum;
	
	/**
	 * Displays the photo search results in a Listview
	 * @param mainStage the mainStage of the controller
	 * @param user current user who has performed the photo search
	 * @param searchResults list of photos that match the search filters
	 */
	public void start(Stage mainStage, User user, ArrayList<Photo> searchResults) {
		user1 = user;
		for (int i = 0; i < searchResults.size(); i++) {
			Photo photo = searchResults.get(i);
			Image image = photo.getImage();
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(50.0);
		    imageView.setFitWidth(50.0);
		    HBox hbox = new HBox(5);
		    Label l = new Label("gibber");
		    String imagePath = photo.getPath();
		    Label path = new Label(imagePath);
		    hbox.getChildren().addAll(imageView, l, path);
		    photosBox.add(hbox);
		    searchResultListView.setItems(photosBox);
		    searchAlbum.addPhoto(searchResults.get(i));
		}
	}
	
	/**
	 * Creates an album in the user's library from the search results
	 */
	public void createSearchAlbum() {
		searchAlbum.setName(searchAlbumNameText.getText());
		searchAlbum.setOldestPhoto(searchAlbum.getOldestPhoto());
		searchAlbum.setNewestPhoto(searchAlbum.getNewestPhoto());
		user1.getAlbums().add(searchAlbum);
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
