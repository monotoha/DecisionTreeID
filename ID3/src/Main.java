import java.io.FileNotFoundException;

public class Main
{

	public static void main(String[] args)
	{
		DecisionTreeID tree = new DecisionTreeID();
		try
		{
			tree.learnDT("/home/jose/archivoCVS");
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
