package demo;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.ID3Controller;
import model.DecisionTreeID;
import view.ID3View;
import view.ID3Window;

public class DecisionTreeDemo {

	public static void main(String[] args) {
		DecisionTreeID decisionTree = new DecisionTreeID();
		ID3View window = new ID3Window();
		ActionListener ctr = new ID3Controller(window, decisionTree);
		window.addController(ctr);
		
		JFrame frame = new JFrame("Decision Tree ID3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane((JPanel) window);
		frame.pack();
		frame.setVisible(true);
	}
}
