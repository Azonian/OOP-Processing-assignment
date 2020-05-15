package ie.tudublin;

public class Main
{

    public void gameControler()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new GameControler());
    }
    
    public static void main(String[] arg)
    {
        Main main = new Main();
		main.gameControler();        
    }
}