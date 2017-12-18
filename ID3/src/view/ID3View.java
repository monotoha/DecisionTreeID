package view;

import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.TaggedTree;

public interface ID3View {
	public String getCsvFileLocation();
	public Map<String, String> getPredictionInput();
	public void printPredectionresult(String result);
	public void addController(ActionListener ctr);
	public void drawTree(TaggedTree<String> tree);
	public void showSelectionMap(Map<String, Set<String>> attributes);
	public JFileChooser getFileChooser();
	public JTextField getDirectoryField();
	public JLabel getMessage();
	public final String LOAD = "L";
	public final String PREDICT = "P";
	public void repaint();
}
