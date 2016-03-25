/**
 * Created by Kapmat on 2016-03-23.
 **/

package Lab4.FX;

import DataClass.Iris;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAGDS implements Initializable {

	@FXML private AnchorPane resultAnchorPane;

	@FXML private Tab tabFilter;
	@FXML private Tab tabSimilarity;

	@FXML private Button bExit;
	@FXML private Button bStart;

	@FXML private TextField similarityLeafLengthInput;
	@FXML private TextField similarityLeafWidthInput;
	@FXML private TextField similarityPetalLengthInput;
	@FXML private TextField similarityPetalWidthInput;
	@FXML private TextField similarityTextField;

	@FXML private TextField filterLeafLengthMIN;
	@FXML private TextField filterLeafLengthMAX;
	@FXML private TextField filterLeafWidthMIN;
	@FXML private TextField filterLeafWidthMAX;
	@FXML private TextField filterPetalLengthMIN;
	@FXML private TextField filterPetalLengthMAX;
	@FXML private TextField filterPetalWidthMIN;
	@FXML private TextField filterPetalWidthMAX;

	@FXML private RadioButton rbTypeSetosa;
	@FXML private RadioButton rbTypeVersicolor;
	@FXML private RadioButton rbTypeVirginica;
	@FXML private RadioButton rbGraph;
	@FXML private RadioButton rbTable;

	@FXML private TableView resultTable;

	@FXML private TableColumn colIndex;
	@FXML private TableColumn colLeafLength;
	@FXML private TableColumn colLeafWidth;
	@FXML private TableColumn colPetalLength;
	@FXML private TableColumn colPetalWidth;
	@FXML private TableColumn colType;
	@FXML private TableColumn colSimilarity;

	//Similarity methods variables
	private double leafL, leafW, petalL, petalW, similarityThreshold;

	//Filter methods variables
	private double lowestLL, highestLL, lowestLW, highestLW;
	private double lowestPL, highestPL, lowestPW, highestPW;
	private Iris.IrisType type = Iris.IrisType.NONE;

	private ModelAGDS modelAGDS = new ModelAGDS();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Initialize model
		modelAGDS.setDataType(1);
		modelAGDS.buildGraphAndTable();

		//Similarity tab
		similarityLeafLengthInput.setText("5.1");
		similarityLeafWidthInput.setText("3.5");
		similarityPetalLengthInput.setText("1.4");
		similarityPetalWidthInput.setText("0.2");
		similarityTextField.setText("50");

		//Filter tab
		filterLeafLengthMIN.setText("1");
		filterLeafLengthMAX.setText("6");
		filterLeafWidthMIN.setText("1");
		filterLeafWidthMAX.setText("6");
		filterPetalLengthMIN.setText("1");
		filterPetalLengthMAX.setText("6");
		filterPetalWidthMIN.setText("1");
		filterPetalWidthMAX.setText("6");

		//Radio button type
		rbTypeSetosa.setSelected(true);
		rbTypeVersicolor.setSelected(false);
		rbTypeVirginica.setSelected(false);

		//Radio button main
		rbGraph.setSelected(true);
		rbTable.setSelected(false);
	}

	@FXML
	public void startButtonAction(ActionEvent event) {
		updateParameters(event);
		if (tabSimilarity.isSelected()) {
			if (rbGraph.isSelected()) {
				modelAGDS.findPatternsInGraphSimilarity();
			} else if (rbTable.isSelected()) {
				modelAGDS.findPatternsInTableSimilarity();
			}
		} else if (tabSimilarity.isSelected()) {
			if (rbGraph.isSelected()) {
				modelAGDS.findPatternsInGraphFilter();
			} else if (rbTable.isSelected()) {
				modelAGDS.findPatternsInTableWithFilter();
			}
		}
	}

	private void updateParameters(ActionEvent event) {
		//Similarity methods variables
		this.leafL = Double.valueOf(similarityLeafLengthInput.getText());
		this.leafW = Double.valueOf(similarityLeafWidthInput.getText());
		this.petalL = Double.valueOf(similarityPetalLengthInput.getText());
		this.petalW = Double.valueOf(similarityPetalWidthInput.getText());
		this.similarityThreshold = Double.valueOf(similarityTextField.getText());

		//Filter methods variables
		this.lowestLL = Double.valueOf(filterLeafLengthMIN.getText());
		this.highestLL = Double.valueOf(filterLeafLengthMAX.getText());
		this.lowestLW = Double.valueOf(filterLeafWidthMIN.getText());
		this.highestLW = Double.valueOf(filterLeafWidthMAX.getText());
		this.lowestPL = Double.valueOf(filterPetalLengthMIN.getText());
		this.highestPL = Double.valueOf(filterPetalLengthMAX.getText());
		this.lowestPW = Double.valueOf(filterPetalWidthMIN.getText());
		this.highestPW = Double.valueOf(filterPetalWidthMAX.getText());
		//TODO wybranie typu jednego lub kilku
		this.type = Iris.IrisType.SETOSA;
	}

	@FXML
	public void radioButtonTypesAction(ActionEvent event) {
		if (rbTypeSetosa.isFocused() && !rbTypeVersicolor.isSelected() && !rbTypeVirginica.isSelected()) {
			rbTypeSetosa.setSelected(true);
		} else if (rbTypeVersicolor.isFocused() && !rbTypeSetosa.isSelected() && !rbTypeVirginica.isSelected()) {
			rbTypeVersicolor.setSelected(true);
		} else if (rbTypeVirginica.isFocused() && !rbTypeVersicolor.isSelected() && !rbTypeSetosa.isSelected()) {
			rbTypeVirginica.setSelected(true);
		}
	}

	@FXML
	public void radioButtonMainAction() {
		if (rbTable.isFocused()) {
			rbTable.setSelected(true);
			rbGraph.setSelected(false);
		} else if (rbGraph.isFocused()){
			rbTable.setSelected(false);
			rbGraph.setSelected(true);
		}
	}

	@FXML
	public void exitButtonAction(ActionEvent event) {
		Stage stage = (Stage) bExit.getScene().getWindow();
		stage.close();
	}
}
