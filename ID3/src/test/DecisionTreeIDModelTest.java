package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import model.DecisionTreeID;
import model.TaggedTree;

public class DecisionTreeIDModelTest {

	@Test
	public void testFarmacoTreeCorrect() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		TaggedTree<String> idealTree = buildTreeFarmaco();
		t.readCSV("csv-files/farmaco.csv");
		assertEquals(idealTree,t.getModel());
		
	}
	
	private TaggedTree<String> buildTreeFarmaco() {
		TaggedTree<String> aux1,aux2,aux3;
		String node1 = "No",node2 = "Sí";
		Map<String,TaggedTree<String>> sons = null;
		aux1 = new TaggedTree<>(node1);
		aux2 = new TaggedTree<>(node2);
		String alerg = "Otras alergias",alto="alto",bajo="bajo";
		sons = new HashMap<>();
		sons.put(node2, aux1);
		sons.put(node1, aux2);
		aux1 = new TaggedTree<>(alerg,sons);
		aux2 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put(node2, aux1);
		sons.put(node1, aux2);
		aux1 = new TaggedTree<>("Alergia a antibióticos",sons);
		aux2 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put(alto, aux1);
		sons.put(bajo, aux2);
		aux1 = new TaggedTree<>("Azúcar en sangre",sons);
		aux2 = new TaggedTree<>(node1);
		aux3 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put(alto, aux2);
		sons.put(bajo, aux3);
		aux2 = new TaggedTree<>("Índice de colesterol",sons);
		aux3 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put("Alta", aux1);
		sons.put("Media", aux2);
		sons.put("Baja", aux3);
		return new TaggedTree<>("Presión alterial",sons);
	}
}
