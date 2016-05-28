/**
 * Created by Kapmat on 2016-03-27.
 **/

package Lab4.FX;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutController {

	@FXML private Label aboutLabel;

	public Label getAboutLabel() {
		return aboutLabel;
	}

	public void setAboutLabel(Label aboutLabel) {
		this.aboutLabel = aboutLabel;
	}
}
