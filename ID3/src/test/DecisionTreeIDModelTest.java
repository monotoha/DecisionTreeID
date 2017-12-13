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
		t.learnID3("csv-files/farmaco.csv");
		assertEquals(idealTree,t.getModel());
		
	}
	
	@Test
	public void testTennisTreeCorrect() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		TaggedTree<String> idealTree = buildTreeTennis();
		t.learnID3("csv-files/Tennis.csv");
		assertEquals(idealTree,t.getModel());
	}
	
	@Test
	public void testSimpsonsTreeCorrect() throws FileNotFoundException {
		DecisionTreeID t = new DecisionTreeID();
		TaggedTree<String> idealTree = buildTreeSimpsons();
		t.learnID3("csv-files/simpsons.csv");
		assertEquals(idealTree,t.getModel());
	}
	
	private TaggedTree<String> buildTreeSimpsons() {
		TaggedTree<String> aux1,aux2,aux3;
		Map<String, TaggedTree<String>> sons = null;
		String nodeH="H",nodeM="M";
		aux1 = new TaggedTree<>(nodeH);
		aux2 = new TaggedTree<>(nodeM);
		sons = new HashMap<>();
		sons.put("Menor 5", aux1);
		sons.put("Mayor 5", aux2);
		aux2 = new TaggedTree<>("Longitud Pelo",sons);
		aux1 = new TaggedTree<>(nodeH);
		sons = new HashMap<>();
		sons.put("Mayor 160 lbs", aux1);
		sons.put("Menor 160 lbs", aux2);
		return new TaggedTree<>("Peso",sons);
	}

	private TaggedTree<String> buildTreeTennis() {
		TaggedTree<String> aux1,aux2,aux3;
		String leafNo = "No", leafYes = "Yes";
		Map<String, TaggedTree<String>> sons = null;
		aux1 = new TaggedTree<>(leafNo);
		aux2 = new TaggedTree<>(leafYes);
		sons = new HashMap<>();
		sons.put("High", aux1);
		sons.put("Normal", aux2);
		aux1 = new TaggedTree<>("Humidity",sons);
		aux2 = new TaggedTree<>(leafYes);
		aux3 = new TaggedTree<>(leafNo);
		sons = new HashMap<>();
		sons.put("Weak", aux2);
		sons.put("Strong", aux3);
		aux3 = new TaggedTree<>("Wind",sons);
		aux2 = new TaggedTree<>(leafYes);
		sons = new HashMap<>();
		sons.put("Sunny", aux1);
		sons.put("Overcast", aux2);
		sons.put("Rain", aux3);
		return new TaggedTree<>("Outlook",sons);
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
