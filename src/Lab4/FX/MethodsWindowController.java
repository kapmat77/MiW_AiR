/**
 * Created by Kapmat on 2016-03-26.
 **/

package Lab4.FX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MethodsWindowController {

	//################## METHODS WINDOW ####################
	@FXML private Label lMethod;
	//Similarity - Iris
	@FXML private TextField lLlSimilarity;
	@FXML private TextField lLwSimilarity;
	@FXML private TextField lPlSimilarity;
	@FXML private TextField lPwSimilarity;
	@FXML private TextField lSimSimilarity;


	public void setLabelMethod(String text) {
		this.lMethod.setText(text);
	}

	@FXML
	public void cancelButtonAction(ActionEvent event) {

	}
}
