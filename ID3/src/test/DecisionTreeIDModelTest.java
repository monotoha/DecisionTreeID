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
		System.out.println(idealTree);
		System.out.println(t.getModel());
		System.out.println(idealTree.equals(t.getModel()));
		assertEquals(idealTree,t.getModel());
	}
	
	private TaggedTree<String> buildTreeSimpsons() {
		TaggedTree<String> aux1,aux2,aux3;
		Map<String, TaggedTree<String>> sons = null;
		String nodeH="h",nodeM="m";
		aux1 = new TaggedTree<>(nodeH);
		aux2 = new TaggedTree<>(nodeM);
		sons = new HashMap<>();
		sons.put("menor 5", aux1);
		sons.put("mayor 5", aux2);
		aux2 = new TaggedTree<>("longitud pelo",sons);
		aux1 = new TaggedTree<>(nodeH);
		sons = new HashMap<>();
		sons.put("mayor 160 lbs", aux1);
		sons.put("menor 160 lbs", aux2);
		return new TaggedTree<>("peso",sons);
	}

	private TaggedTree<String> buildTreeTennis() {
		TaggedTree<String> aux1,aux2,aux3;
		String leafNo = "no", leafYes = "yes";
		Map<String, TaggedTree<String>> sons = null;
		aux1 = new TaggedTree<>(leafNo);
		aux2 = new TaggedTree<>(leafYes);
		sons = new HashMap<>();
		sons.put("high", aux1);
		sons.put("normal", aux2);
		aux1 = new TaggedTree<>("humidity",sons);
		aux2 = new TaggedTree<>(leafYes);
		aux3 = new TaggedTree<>(leafNo);
		sons = new HashMap<>();
		sons.put("weak", aux2);
		sons.put("strong", aux3);
		aux3 = new TaggedTree<>("wind",sons);
		aux2 = new TaggedTree<>(leafYes);
		sons = new HashMap<>();
		sons.put("sunny", aux1);
		sons.put("overcast", aux2);
		sons.put("rain", aux3);
		return new TaggedTree<>("outlook",sons);
	}

	private TaggedTree<String> buildTreeFarmaco() {
		TaggedTree<String> aux1,aux2,aux3;
		String node1 = "no",node2 = "si";
		Map<String,TaggedTree<String>> sons = null;
		aux1 = new TaggedTree<>(node1);
		aux2 = new TaggedTree<>(node2);
		String alerg = "otras alergias",alto="alto",bajo="bajo";
		sons = new HashMap<>();
		sons.put(node2, aux1);
		sons.put(node1, aux2);
		aux1 = new TaggedTree<>(alerg,sons);
		aux2 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put(node2, aux1);
		sons.put(node1, aux2);
		aux1 = new TaggedTree<>("alergia a antibioticos",sons);
		aux2 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put(alto, aux1);
		sons.put(bajo, aux2);
		aux1 = new TaggedTree<>("azucar en sangre",sons);
		aux2 = new TaggedTree<>(node1);
		aux3 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put(alto, aux2);
		sons.put(bajo, aux3);
		aux2 = new TaggedTree<>("indice de colesterol",sons);
		aux3 = new TaggedTree<>(node2);
		sons = new HashMap<>();
		sons.put("alta", aux1);
		sons.put("media", aux2);
		sons.put("baja", aux3);
		return new TaggedTree<>("presion alterial",sons);
	}
}
