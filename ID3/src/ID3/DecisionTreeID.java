package ID3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecisionTreeID
{
	private final List<List<String>> table = new ArrayList<>();

	public void learnDT(String ficheroCVS) throws FileNotFoundException
	{		
		Scanner s = new Scanner(new File(ficheroCVS));
		//Assuming that we aren't going to use the " character
		s.useDelimiter(",\\n");
		while(s.hasNextLine())
		{
			List<String> innerList = new ArrayList<>();
			
			Scanner scannerLine = new Scanner(s.nextLine());
			while(scannerLine.hasNext())
			{
				innerList.add(scannerLine.next());
			}
			table.add(innerList);
		}
		
		System.out.println(table);
	}
	
	
	public List<List<String>> getTable() {
		return table;
	}


	public void drawDecisionTree()
	{

	}

	public double entropy() {
		return 0;
	}
	public Object prediction(String[] registroCVS)
	{
		return null;
	}
	
}
