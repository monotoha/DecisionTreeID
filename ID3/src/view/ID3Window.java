package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ID3Window extends JPanel implements ID3View {

	private static final long serialVersionUID = 1L;
	
	/* 
	 */
	
	private JTextField fileDirectory;
	private JButton openFileButton;
	private JLabel message;
	private JFileChooser fc;
	

	
	public ID3Window() {
		fileDirectory = new JTextField(50);
		openFileButton = new JButton("Open");
		message = new JLabel("");
		fc = new JFileChooser();
		
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		openFileButton.setSize(10, 10);
			
		JPanel loadPanel = new JPanel();
		loadPanel.setLayout(new GridLayout(1, 3));
		loadPanel.add(fileDirectory);
		loadPanel.add(openFileButton);
				
		setLayout(new BorderLayout());
		add(loadPanel, BorderLayout.NORTH);
		add(message, BorderLayout.SOUTH);
	}
	
	public JFileChooser getFileChooser() {
		return fc;
	}
	
	public JLabel getMessage() {
		return message;
	}
	
	public JTextField getDirectoryField() {
		return fileDirectory;
	}

	public String getCsvFileLocation() {
		return fileDirectory.getText();
	}

	public String getPredictionInput() {
		// TODO Auto-generated method stub
		return null;
	}

	public String printPredectionresult() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addController(ActionListener ctr) {
		openFileButton.addActionListener(ctr);
		openFileButton.setActionCommand(LOAD);
		
	}

	public void drawTree() {
		// TODO Auto-generated method stub
		
	}

	public void drawTableFromCSV() {
		// TODO Auto-generated method stub
		
	}

}
