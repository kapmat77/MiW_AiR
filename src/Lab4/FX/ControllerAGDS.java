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
	private double leafL, leafW, petalL, petalW, similarityThreshold = 0;

	//Filter methods variables
	private double lowestLL, highestLL, lowestLW, highestLW = 0;
	private double lowestPL, highestPL, lowestPW, highestPW = 0;
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

//	public void actionEventAGDS(ActionEvent event) {
//		//Similarity methods variables
//		double leafL, leafW, petalL, petalW, similarityThreshold = 0;
//
//		//Filter methods variables
//		double lowestLL, highestLL, lowestLW, highestLW = 0;
//		double lowestPL, highestPL, lowestPW, highestPW = 0;
//		Iris.IrisType type = Iris.IrisType.NONE;
//
//		//Initialize
////		modelAGDS.
//		}

	@FXML
	public void startButtonAction(ActionEvent event) {
		if (tabSimilarity.isSelected()) {
			if (rbGraph.isSelected()) {

			} else if (rbTable.isSelected()) {

			}
		} else if (tabSimilarity.isSelected()) {
			if (rbGraph.isSelected()) {

			} else if (rbTable.isSelected()) {

			}
		}
	}

	@FXML
	public void radioButtonTypesAction(ActionEvent event) {

		if (rbTypeSetosa.isFocused() && !rbTypeVersicolor.isSelected() && !rbTypeVirginica.isSelected()) {
			rbTypeSetosa.setSelected(true);
		}

		if (rbTypeVersicolor.isFocused() && !rbTypeSetosa.isSelected() && !rbTypeVirginica.isSelected()) {
			rbTypeVersicolor.setSelected(true);
		}

		if (rbTypeVirginica.isFocused() && !rbTypeVersicolor.isSelected() && !rbTypeSetosa.isSelected()) {
			rbTypeVirginica.setSelected(true);
		}
	}

	@FXML
	public void exitButtonAction(ActionEvent event) {
		Stage stage = (Stage) bExit.getScene().getWindow();
		stage.close();
	}
}
