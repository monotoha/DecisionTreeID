package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;

import com.mxgraph.swing.mxGraphComponent;

import model.DecisionTreeID;
import view.ID3View;
import view.ID3Window;

public class ID3Controller implements ActionListener {
	
	private ID3View window;
	private DecisionTreeID decisionTree;
	
	public ID3Controller(ID3View _window, DecisionTreeID _decisionTree) {
		window = _window;
		decisionTree = _decisionTree;
	}
	
	public Map<String, Set<String>> buildSelectionMenu() {
		Map<String, Set<String>> selectionMenu = new HashMap<>();
		List<String> keys = decisionTree.getTable().get(0);
		
		for (int col = 0; col < decisionTree.getTable().get(0).size() - 1; col++) {
			Set<String> values = new HashSet<>();
			for (int row = 1; row < decisionTree.getTable().size(); row++) {
				values.add(decisionTree.getTable().get(row).get(col));
				selectionMenu.put(keys.get(col), values);
			}
		}
		
		return selectionMenu;
	}

	public void actionPerformed(ActionEvent e) {
		// Handle open button action
		if (e.getActionCommand().equals(ID3View.LOAD)) {
			int returnVal = window.getFileChooser().showOpenDialog(null);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File data = window.getFileChooser().getSelectedFile();
				window.getDirectoryField().setText(data.getPath());
				window.getMessage().setText("Load succesful.");
				try {
					decisionTree = new DecisionTreeID();
					decisionTree.learnID3(data.getPath());
					window.drawTree(decisionTree.getModel());
					window.showSelectionMap(buildSelectionMenu());
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} 
		
		if (e.getActionCommand().equals(ID3View.PREDICT)) {
			window.printPredectionresult((String) decisionTree.prediction(window.getPredictionInput()));
		}
	}	
}
