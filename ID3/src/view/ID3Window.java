package view;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import model.TaggedTree;

public class ID3Window extends JPanel implements ID3View {

	private static final long serialVersionUID = 1L;

	/* 
	 */

	private JTextField fileDirectory;
	private JButton openFileButton;
	private JLabel message;
	private JFileChooser fc;

	private JPanel selectionMap;
	private JLabel predictionResult;
	private JButton predictButton;
	private Map<String, JComboBox> predictionMenu;

	private JPanel treePanel;

	public ID3Window() {
		fileDirectory = new JTextField(50);
		openFileButton = new JButton("Open");
		message = new JLabel("");
		fc = new JFileChooser();

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		openFileButton.setSize(10, 10);

		predictionResult = new JLabel("");
		predictButton = new JButton("Predict");

		JPanel loadPanel = new JPanel();
		loadPanel.setLayout(new GridLayout(1, 3));
		loadPanel.add(fileDirectory);
		loadPanel.add(openFileButton);

		selectionMap = new JPanel();
		treePanel = new JPanel();

		setLayout(new BorderLayout());
		add(loadPanel, BorderLayout.NORTH);
		add(treePanel, BorderLayout.CENTER);
		add(selectionMap, BorderLayout.SOUTH);
		// add(message, BorderLayout.SOUTH);

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

	public void showSelectionMap(Map<String, Set<String>> attributes) {
		int numOfDropLists = attributes.keySet().size();
		predictionMenu = new HashMap<>();
		
		selectionMap.removeAll();
		selectionMap.setLayout(new GridLayout(2, numOfDropLists + 2));
		int boxCounter = 0;
		for (String title : attributes.keySet()) {
			String[] values = new String[attributes.get(title).size()];
			int index = 0;

			for (String value : attributes.get(title)) {
				values[index++] = value;
			}

			JComboBox box = new JComboBox(values);
			predictionMenu.put(title, box);
			selectionMap.add(new JLabel(title));
			selectionMap.add(box);
		}
		
		
		selectionMap.add(predictionResult);
		selectionMap.add(predictButton);
		selectionMap.revalidate();
		selectionMap.repaint();

	}

	public Map<String, String> getPredictionInput() {
		// TODO Auto-generated method stub
		Map<String, String> input = new HashMap<>();
		for (String key : predictionMenu.keySet()) {
			input.put(key, (String) predictionMenu.get(key).getSelectedItem());
		}
		return input;
	}

	public void printPredectionresult(String result) {
		// TODO Auto-generated method stub
		predictionResult.setHorizontalTextPosition(SwingConstants.CENTER);
		predictionResult.setText(result);

	}

	public void addController(ActionListener ctr) {
		openFileButton.addActionListener(ctr);
		openFileButton.setActionCommand(LOAD);
		predictButton.addActionListener(ctr);
		predictButton.setActionCommand(PREDICT);
	}

	public void drawTree(TaggedTree<String> tree) {
		List<TaggedTree<String>> nodes = tree.getNodeList();
		List<Object> vertices = new ArrayList<>();

		mxGraph tGraph = new mxGraph();
		int i = 0;
		tGraph.getModel().beginUpdate();
		try {
			for (TaggedTree<String> vertex : nodes) {
				vertices.add(tGraph.insertVertex(tGraph.getDefaultParent(), null, vertex.getNodeValue(), 100 * (i++),
						100 * vertex.getNodeDepth(), 80, 30));
			}

			for (TaggedTree<String> vertex : nodes) {
				if (!vertex.isLeafNode()) {
					Map<String, TaggedTree<String>> children = vertex.getEdgesAssociatedToSubTrees();
					for (String edge : children.keySet()) {
						tGraph.insertEdge(tGraph.getDefaultParent(), null, edge, vertices.get(nodes.indexOf(vertex)),
								vertices.get(nodes.indexOf(children.get(edge))));
					}
				}
			}
		} finally {
			tGraph.getModel().endUpdate();
		}

		treePanel.removeAll();
		treePanel.add(new mxGraphComponent(tGraph), BorderLayout.CENTER);
		treePanel.revalidate();
		treePanel.repaint();
	}
}
