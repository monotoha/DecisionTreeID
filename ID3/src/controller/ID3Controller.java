package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;

import com.mxgraph.swing.mxGraphComponent;

import model.DecisionTreeID;
import view.ID3View;

public class ID3Controller implements ActionListener {
	
	private ID3View window;
	private DecisionTreeID decisionTree;
	
	public ID3Controller(ID3View _window, DecisionTreeID _decisionTree) {
		window = _window;
		decisionTree = _decisionTree;
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
					decisionTree.learnDT(data.getPath());
					window.addTree(new mxGraphComponent(decisionTree.drawDecisionTree()));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}	
	}	
}
