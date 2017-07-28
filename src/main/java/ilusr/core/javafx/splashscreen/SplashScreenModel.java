package ilusr.core.javafx.splashscreen;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class SplashScreenModel {
	private final String COLOR_PREFIX = "-fx-background-color:";
	private final String COLOR_SUFFIX = ";";
	
	private SimpleStringProperty applicationName;
	private SimpleStringProperty version;
	private SimpleStringProperty currentState;
	private SimpleStringProperty fontFamily;
	private SimpleStringProperty backgroundStyle;
	private Image backgroundImage;
	
	/**
	 * Creates a new splash screen model.
	 */
	public SplashScreenModel() {
		applicationName = new SimpleStringProperty();
		version = new SimpleStringProperty();
		currentState = new SimpleStringProperty();
		fontFamily = new SimpleStringProperty();
		backgroundStyle = new SimpleStringProperty();
		backgroundImage = null;
	}
	
	/**
	 * 
	 * @return The name of the application.
	 */
	public SimpleStringProperty applicationName() {
		return applicationName;
	}
	
	/**
	 * 
	 * @return The version of the application.
	 */
	public SimpleStringProperty version() {
		return version;
	}
	
	/**
	 * 
	 * @return The state to display on the splash screen.
	 */
	public SimpleStringProperty currentState() {
		return currentState;
	}
	
	/**
	 * 
	 * @param state The new state to display on the splash screen.
	 */
	public void setCurrentState(String state) {
		Platform.runLater(() -> {
			currentState.setValue(state);
		});
	}
	
	/**
	 * 
	 * @return The font to use for the splash screen.
	 */
	public SimpleStringProperty fontFamily() {
		return fontFamily;
	}
	
	/**
	 * 
	 * @return The style to apply to the splash screen.
	 */
	public SimpleStringProperty backgroundStyle() {
		return backgroundStyle;
	}
	
	/**
	 * 
	 * @return The background image to use for the splash screen.
	 */
	public Image getBackgroundImage() {
		return backgroundImage;
	}
	
	/**
	 * 
	 * @param image The new image to use for the background.
	 */
	public void setBackgroundImage(Image image) {
		backgroundImage = image;
	}
	
	/**
	 * 
	 * @param color The background color to use of the splash screen.
	 */
	public void setBackgroundColor(String color) {
		backgroundStyle.setValue(COLOR_PREFIX + color + COLOR_SUFFIX);
	}
}
