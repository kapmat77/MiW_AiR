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
	@FXML private Button okIrisButton;
	@FXML private Button okWineButton;
	@FXML private Button cancelButton;
	@FXML public Tab tabIris;
	@FXML public Tab tabWine;

	//########## SIMILARITY #############
	//IRIS
	@FXML private TextField txLlSim;
	@FXML private TextField txLwSim;
	@FXML private TextField txPlSim;
	@FXML private TextField txPwSim;
	@FXML private TextField txIrisSim;
	@FXML private Label labelIrisSimilarity;
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

	//############# FILTER ################
	//IRIS
	@FXML private TextField txLlMinFil;
	@FXML private TextField txLwMinFil;
	@FXML private TextField txPlMinFil;
	@FXML private TextField txPwMinFil;
	@FXML private TextField txLlMaxFil;
	@FXML private TextField txLwMaxFil;
	@FXML private TextField txPlMaxFil;
	@FXML private TextField txPwMaxFil;
	@FXML private Label labelIrisMin;
	@FXML private Label labelIrisMax;

	public TextField getTxLlMinFil() {
		return txLlMinFil;
	}

	public TextField getTxPlMinFil() {
		return txPlMinFil;
	}

	public TextField getTxLwMinFil() {
		return txLwMinFil;
	}

	public TextField getTxPwMinFil() {
		return txPwMinFil;
	}

	public TextField getTxLlMaxFil() {
		return txLlMaxFil;
	}

	public TextField getTxLwMaxFil() {
		return txLwMaxFil;
	}

	public TextField getTxPlMaxFil() {
		return txPlMaxFil;
	}

	public TextField getTxPwMaxFil() {
		return txPwMaxFil;
	}

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

		if (tabIris.isSelected()) {
			((Stage) okIrisButton.getScene().getWindow()).close();
		} else if (tabWine.isSelected()) {
			((Stage) okIrisButton.getScene().getWindow()).close();
		}
	}

	public void setVisibility() {
		if (lMethod.getText().contains("SIMILARITY")) {
			setVisibilitySim(true);
			setVisibilityFilter(false);
		} else if (lMethod.getText().contains("FILTER")) {
			setVisibilitySim(false);
			setVisibilityFilter(true);
		} else if (lMethod.getText().contains("CORRELACTION")) {
			setVisibilitySim(false);
			setVisibilityFilter(false);
		} else if (lMethod.getText().contains("MIN&MAX")) {
			setVisibilitySim(false);
			setVisibilityFilter(false);
		}
	}

	private void setVisibilitySim(boolean visible) {
		txLlSim.setVisible(visible);
		txLwSim.setVisible(visible);
		txPlSim.setVisible(visible);
		txPwSim.setVisible(visible);
		txIrisSim.setVisible(visible);
		labelIrisSimilarity.setVisible(visible);
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

	private void setVisibilityFilter(boolean visible) {
		txLlMinFil.setVisible(visible);
		txLwMinFil.setVisible(visible);
		txPlMinFil.setVisible(visible);
		txPwMinFil.setVisible(visible);
		txLlMaxFil.setVisible(visible);
		txLwMaxFil.setVisible(visible);
		txPlMaxFil.setVisible(visible);
		txPwMaxFil.setVisible(visible);
		labelIrisMin.setVisible(visible);
		labelIrisMax.setVisible(visible);
	}
}
