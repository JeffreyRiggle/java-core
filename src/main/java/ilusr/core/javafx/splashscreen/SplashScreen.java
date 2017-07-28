package ilusr.core.javafx.splashscreen;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class SplashScreen extends AnchorPane implements Initializable{

	private SplashScreenModel model;
	
	@FXML
	private Label applicationName;
	@FXML
	private Label state;
	@FXML
	private Label version;
	@FXML
	private GridPane grid;
	
	/**
	 * 
	 * @param model The @see SplashScreenModel to bind to this view.
	 */
	public SplashScreen(SplashScreenModel model) {
		this.model = model;
		FXMLLoader splashLoader = new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
		splashLoader.setRoot(this);
		splashLoader.setController(this);
		
		try {
			splashLoader.load();
		} catch (Exception exception) {
			//TODO
			exception.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		state.textProperty().bind(model.currentState());
		applicationName.textProperty().bind(model.applicationName());
		version.textProperty().bind(model.version());
		if (model.getBackgroundImage() != null) {
			BackgroundImage backImage = new BackgroundImage(model.getBackgroundImage(), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
			          BackgroundSize.DEFAULT);
			grid.setBackground(new Background(backImage));
		} else {
			grid.styleProperty().bind(model.backgroundStyle());
		}
		
		state.fontProperty().setValue(new Font(model.fontFamily().getValue(), state.fontProperty().getValue().getSize()));
		applicationName.fontProperty().setValue(new Font(model.fontFamily().getValue(), applicationName.fontProperty().getValue().getSize()));
		version.fontProperty().setValue(new Font(model.fontFamily().getValue(), version.fontProperty().getValue().getSize()));
		
		model.fontFamily().addListener((listener) -> {
			state.setFont(new Font(model.fontFamily().getValue(), state.fontProperty().getValue().getSize()));
			applicationName.setFont(new Font(model.fontFamily().getValue(), applicationName.fontProperty().getValue().getSize()));
			version.setFont(new Font(model.fontFamily().getValue(), version.fontProperty().getValue().getSize()));
		});
	}
}
