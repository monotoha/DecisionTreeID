package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.mxgraph.view.mxGraph;

public class DecisionTreeID {
	private final List<List<String>> table = new ArrayList<>();

	CSVParser parser;

	public void learnDT(String ficheroCSV) throws FileNotFoundException {

		Reader reader = new BufferedReader(new FileReader(ficheroCSV));

		try {
			parser = new CSVParser(reader, CSVFormat.DEFAULT);
			List<CSVRecord> records = parser.getRecords();

			for (CSVRecord r : records) {
				List<String> row = new ArrayList<>();
				r.forEach(x -> {
					row.add(x.isEmpty() ? "UNKNOWN" : x);
				});
				table.add(row);
			}

			System.out.println(table);

		} catch (IOException e) {
			System.out.println("IOException");
		}
	}
	
	public List<List<String>> getTable() {
		return table;
	}

	public mxGraph drawDecisionTree() {
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();
		try {
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
			Object v3 = graph.insertVertex(parent, null, "This", 500, 150, 80, 30);
			graph.insertEdge(parent, null, "Edge", v1, v2);
			graph.insertEdge(parent, null, "Edge", v1, v3);

		} finally {
			graph.getModel().endUpdate();
		}

		return graph;
	}

	public Object prediction(String[] registroCVS) {
		return null;
	}
    private int ganancia(boolean[] row,boolean[] col,int res)
    {
        int[] ganancias = new int[col.length];
        int[] classAmount;
        List<String> valuesRes = values(res,row);
        int entropiaRes = entropiaRes(res,row,valuesRes);
        List<String> values;
        for(int i=0;i<col.length;i++)
        {
            values = values(i,row);
            int total=0;
            ganancias[i] = entropiaRes;
            if(i!=res)
            {
                
                
                classAmount = new int[values.size()+1];
                
                for(int j=0;j<row.length;j++)
                    if(!row[j])
                    {
                        classAmount[values.indexOf(table.get(j).get(i))]++;
                        total++;
                    }       
                int[] entropia = entropia(res,i,row,valuesRes);
                for(int j =0;j<entropia.length;j++)
                {
                    ganancias[i]-=(classAmount[j]/total)*entropia[j];
                }
                
            }
        }
        int index =0;
        int max=0;
        for(int i=0;i<ganancias.length;i++)
        {
            if(ganancias[i]>max)
            {
                max=ganancias[i];
                index = i;
            }
        }
        
        return index;
    }
    
    private List<String> values(int col,boolean[] row)
    {
        List<String> values =new ArrayList<>();
        for(int j=0;j<row.length;j++)
            if(!values.contains(table.get(j).get(col)) && !row[j])
            {
                values.add(table.get(j).get(col));
            }
        return values;
    }
    
    
    
    private int entropiaRes(int col,boolean[] row,List<String> values)
    {
        int entropia =0;
        int total = 0;
        int[] classAmount;
        
        classAmount = new int[values.size()+1];
        
        for(int j=0;j<row.length;j++)
            if(!row[j])
            {
                classAmount[values.indexOf(table.get(j).get(col))]++;
                total++;
            }
        for(int j=0;j<classAmount.length;j++)
        {
            try
            {
                entropia-=(classAmount[j]/total)*Math.log(classAmount[j]/total);
            }
            catch(Exception e)
            {
                entropia -= 0;
            }
        }
        return entropia;
    }
    
    private int[] entropia(int colRes,int col,boolean[] row,List<String> valuesRes)
    {
        List<String> values = values(col,row);
        int[] entropia=new int[values.size()+1];
        int[] total =new int[values.size()+1];
        int[][] classAmount = new int[values.size()+1][valuesRes.size()+1];
        for(int j=0;j<row.length;j++)
        {
            if(!row[j])
            {
                classAmount[values.indexOf(table.get(j).get(col))][valuesRes.indexOf(table.get(j).get(colRes))]++;
                total[values.indexOf(table.get(j).get(col))]++;
            }
        }
        
        for(int i=0; i<classAmount.length;i++)
        {
            for(int j=0;j<classAmount[0].length;j++)
            {   
                try
                {
                    entropia[i]-=(classAmount[i][j]/total[i])*Math.log(classAmount[i][j]/total[i]);
                }
                catch(Exception e)
                {
                    entropia[i]-=0;
                }
            }
        }
        
        
        return entropia;
    }
}
