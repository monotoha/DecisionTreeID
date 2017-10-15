package view;

import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public interface ID3View {
	public String getCsvFileLocation();
	public String getPredictionInput();
	public String printPredectionresult();
	public void addController(ActionListener ctr);
	public void drawTree();
	public void drawTableFromCSV();
	public JFileChooser getFileChooser();
	public JTextField getDirectoryField();
	public JLabel getMessage();
	public final String LOAD = "L";
}
