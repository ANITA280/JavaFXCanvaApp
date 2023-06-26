package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Model;
import model.User;

//for controlling the login of the users 
public class LoginController {
	@FXML
	private TextField name;
	@FXML
	private PasswordField password;
	@FXML
	private Label message;
	@FXML
	private Button login;
	@FXML
	private Button profile;
	@FXML
	private Button signup;

	private Model model;
	private Stage stage;

	public LoginController(Stage stage, Model model) {
		this.stage = stage;
		this.model = model;
	}

	@FXML
	public void initialize() {
		login.setOnAction(event -> {
			if (!name.getText().isEmpty() && !password.getText().isEmpty()) {
				User user = null;
				try {
					user = model.getUserDao().getUser(name.getText(), doHashing(password.getText()));
					if (user != null) {
						model.setCurrentUser(user);
						//opeing the user profile on successful login 
						profile(user);

					} else {
						message.setText("Wrong username or password");
						message.setTextFill(Color.RED);
					}
				} catch (SQLException e) {
					message.setText(e.getMessage());
					message.setTextFill(Color.RED);
				}

			} else {
				message.setText("Empty username or password");
				message.setTextFill(Color.RED);
			}
			name.clear();
			password.clear();
		});

		signup.setOnAction(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignupView.fxml"));

				// Customize controller instance
				Callback<Class<?>, Object> controllerFactory = param -> {
					return new SignupController(stage, model);
				};

				loader.setControllerFactory(controllerFactory);
				VBox root = loader.load();

				SignupController signupController = loader.getController();
				signupController.showStage(root);

				message.setText("");
				name.clear();
				password.clear();

				stage.close();
			} catch (IOException e) {
				message.setText(e.getMessage());
			}
		});
	}
		public void profile(User user) {

			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Profile.fxml"));
				
				
				// Customize controller instance
				Callback<Class<?>, Object> controllerFactory = param -> {
					return new ProfileController(stage, model);
				};

				loader.setControllerFactory(controllerFactory);
				AnchorPane root = loader.load();
				 

				ProfileController profileController = loader.getController();
				profileController.setProfile(user);
				profileController.setRoot(root);
				profileController.showStage(root);
			    
			

				message.setText("");
				name.clear();
				password.clear();

//			message.setText("");
//			name.clear();
//			password.clear();

				stage.close();
			} catch (IOException e) {
				message.setText(e.getMessage());
			}
//		});
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
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Welcome");
		stage.show();
	}
}
