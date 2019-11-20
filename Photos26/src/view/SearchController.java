package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class SearchController {
	
	@FXML ListView<VBox> searchResultListView;
	@FXML TextField searchAlbumNameText;
	@FXML Button backButton;
	@FXML Button createSearchAlbumButton;

	private User user;
	
	public void start(Stage mainStage, User user) {
		this.user = user;
		for (int i = 0; i < user.getAllPhotos().size(); i++) {
			
		}
	}
	public void createSearchAlbum() {
		
	}
	public void back() {
		
	}
}
