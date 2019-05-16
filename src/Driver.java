import javax.swing.JFrame;

public class Driver {

	public static void main(String[] args) {
		//declare and initilize the main jframes
		JFrame mainFrame = new JFrame("2048");
		JFrame gOFrame = new JFrame("Game Over");
		
		//create the frame contents
		GameOverForm gOGUI = new GameOverForm(gOFrame,mainFrame);
		MainForm mainGUI = new MainForm(mainFrame,gOFrame,gOGUI);
	}

}
