package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Model;
import model.User;

public class EditController {
	// for editting the profile information
	private Stage stage;
	private Stage parentStage;
	private Model model;
	private Pane parentroot;
	@FXML
	private ImageView logo;
	@FXML
	private Text username;
	@FXML
	private Hyperlink Selectimage;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button edit;
	private Image Profilelogo;
	private String password;
	private String imagePath;
	private Stage stage2;
//	private User updateduser;
	private User user;
	boolean clicked = false;
	private ButtonType button;

	// constructor to set the eidtController
	public EditController(Stage parentStage, Model model, Pane parentRoot) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
		this.parentroot = parentRoot;

	}

	// to show the edit controller pane
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 500);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Edit Profile");
		// waiting for the user interaction on the edit pane
		stage.showAndWait();

	}

	// to set the info on the edit pane retrieving from the profile
	public void setEditPane(Image image, String text, User user) {
		this.user = user;
		Profilelogo = new Image("file:" + user.getlogo());
		this.logo.setImage(Profilelogo);
		this.username.setText(text);
		firstName.setText(user.getFirstName());
		lastName.setText(user.getLastName());
		this.password = user.getPassword();
		imagePath = user.getlogo();

	}

	// setting new image on clikch by the user
	public void selectImage() {
		Selectimage.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg");

			fileChooser.getExtensionFilters().add(extFilter);

			// Show a file open dialog
			File selectedFile = fileChooser.showOpenDialog(stage2);

			if (selectedFile != null) {
				try {
					FileInputStream fileInputStream = new FileInputStream(selectedFile);
					logo.setImage(new Image(fileInputStream));

					String path = System.getProperty("user.dir");
					imagePath = (new File(path).toURI().relativize(selectedFile.toURI()).getPath());

				} catch (IOException e) {
					System.out.println("File not found");
				}
			}
		});
	}

	// updating the user on the databse based on editted info
	public void UpdatedUser() {
		edit.setOnAction(event -> {
			System.out.println("WORKING");
			if (!firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {

				try {
					user = model.getUserDao().getUser(user.getUsername(), password);

					user = model.getUserDao().updateUser(user.getUsername(), password, firstName.getText(),
							lastName.getText(), imagePath);

					model.UpdateCurrentUser(user);

					clicked = true;
					this.stage.close();

					parentStage.show();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("not working");
					e.printStackTrace();
				}

			}

		});

	}

	public boolean clicked() {
		return clicked;

	}

	public User editedUser() {
		return this.user;
	}
}
