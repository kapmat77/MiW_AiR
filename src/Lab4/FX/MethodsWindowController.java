/**
 * Created by Kapmat on 2016-03-26.
 **/

package Lab4.FX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MethodsWindowController {

	//################## METHODS WINDOW ####################
	@FXML private Label lMethod;
	@FXML private Button okButton;
	@FXML private Button cancelButton;

	//########## SIMILARITY #############
	//IRIS
	@FXML private TextField txLlSim;
	@FXML private TextField txLwSim;
	@FXML private TextField txPlSim;
	@FXML private TextField txPwSim;
	@FXML private TextField txIrisSim;
	//WINE
	@FXML private TextField txAlcoholSim;
	@FXML private TextField txMalicAcidSim;
	@FXML private TextField txAshSim;
	@FXML private TextField txAlcalinityOfAsheSim;
	@FXML private TextField txMagnesiumSim;
	@FXML private TextField txTotalPhenolsSim;
	@FXML private TextField txFlavanoidsSim;
	@FXML private TextField txNonFlavanoidsSim;
	@FXML private TextField txProanthocyanisSim;
	@FXML private TextField txColorIntSim;
	@FXML private TextField txHueSim;
	@FXML private TextField txOD280Sim;
	@FXML private TextField txProlineSim;
	@FXML private TextField txWineSim;

	public Label getLabelMethod() {
		return lMethod;
	}

	public void setLabelMethod(String text) {
		this.lMethod.setText(text);
	}

	public TextField getTxLlSim() {
		return txLlSim;
	}

	public void setTxLlSim(double val) {
		this.txLlSim.setText(String.valueOf(val));
	}

	public TextField getTxLwSim() {
		return txLwSim;
	}

	public void setTxLwSim(double val) {
		this.txLwSim.setText(String.valueOf(val));
	}

	public TextField getTxPlSim() {
		return txPlSim;
	}

	public void setTxPlSim(double val) {
		this.txPlSim.setText(String.valueOf(val));
	}

	public TextField getTxPwSim() {
		return txPwSim;
	}

	public void setTxPwSim(double val) {
		this.txPwSim.setText(String.valueOf(val));
	}

	public TextField getTxIrisSimilarity() {
		return txIrisSim;
	}

	public void setTxIrisSimilarity(double val) {
		this.txIrisSim.setText(String.valueOf(val));
	}

//	@FXML
//	public void cancelButtonAction(ActionEvent event) {
//		((Stage) cancelButton.getScene().getWindow()).close();
//	}

	@FXML
	public void okButtonAction(ActionEvent event) {
//		if (lMethod.getText().contains("SIMILARITY")) {
//
//		} else if (lMethod.getText().contains("FILTER")) {
//
//		} else if (lMethod.getText().contains("CORRELACTION")) {
//
//		} else if (lMethod.getText().contains("MIN&MAX")) {
//
//		}
		((Stage) okButton.getScene().getWindow()).close();
	}

	public void setVisibility() {
		if (lMethod.getText().contains("SIMILARITY")) {
			setVisibilitySim(true);
		} else if (lMethod.getText().contains("FILTER")) {
			setVisibilitySim(false);
		} else if (lMethod.getText().contains("CORRELACTION")) {
			setVisibilitySim(false);
		} else if (lMethod.getText().contains("MIN&MAX")) {
			setVisibilitySim(false);
		}
	}

	private void setVisibilitySim(boolean visible) {
		txLlSim.setVisible(visible);
		txLwSim.setVisible(visible);
		txPlSim.setVisible(visible);
		txPwSim.setVisible(visible);
		txIrisSim.setVisible(visible);
		txAlcoholSim.setVisible(visible);
		txMalicAcidSim.setVisible(visible);
		txAshSim.setVisible(visible);
		txAlcalinityOfAsheSim.setVisible(visible);
		txMagnesiumSim.setVisible(visible);
		txTotalPhenolsSim.setVisible(visible);
		txFlavanoidsSim.setVisible(visible);
		txNonFlavanoidsSim.setVisible(visible);
		txProanthocyanisSim.setVisible(visible);
		txColorIntSim.setVisible(visible);
		txHueSim.setVisible(visible);
		txOD280Sim.setVisible(visible);
		txProlineSim.setVisible(visible);
		txWineSim.setVisible(visible);
	}
}
