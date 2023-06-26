package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Model;
import model.User;

public class SignupController implements Initializable {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button createUser;
	@FXML
	private Button close;
	@FXML
	private Label status;
	@FXML
	private Hyperlink Selectimage;
	InputStream fileInputStream = null;
	@FXML
	private ImageView image;
	private Stage stage;
	private Stage stage2;
	private Stage parentStage;
	private Model model;
	private byte[] buf;
	private String imagePath;

	public SignupController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
		createUser.setOnAction(event -> {
			if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
				User user;
				try {
					// creating the new user and hashing the password to store in the database
					user = model.getUserDao().createUser(username.getText(), doHashing(password.getText()),
							firstName.getText(), lastName.getText(), imagePath);
					System.out.println(user);
					if (user != null) {
						status.setText("Created " + user.getUsername());
						status.setTextFill(Color.GREEN);
					} else {
						status.setText("Cannot create user");
						status.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					status.setText(e.getMessage());
					status.setTextFill(Color.RED);
				}

			} else {
				status.setText("Empty fields like: username or password");
				status.setTextFill(Color.RED);
			}
		});

	}

	// selecting the profile picture for the profile
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
					fileInputStream = new FileInputStream(selectedFile);
					image.setImage(new Image(fileInputStream));

					int w = (int) image.getImage().getWidth();
					int h = (int) image.getImage().getHeight();
					String path = System.getProperty("user.dir");

					imagePath = (new File(path).toURI().relativize(selectedFile.toURI()).getPath());

				} catch (IOException e) {
					System.out.println("File not found");
				}
			}
		});
	}

	public String doHashing(String password) {// to hash the user's password
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes());
			byte[] resultByteArray = messageDigest.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : resultByteArray) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return "";
	}

	// for shwoing the signup window on button click
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 600);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Sign up");
		stage.show();
	}

	// setting a default image on the signup pane
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		image.setImage(new Image("file:" + "src/main/Images/ProfileDefault.jpeg"));
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});

	}
}
