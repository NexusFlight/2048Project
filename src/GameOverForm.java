import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class GameOverForm implements ActionListener {
	
	private JFrame mainFrame;
	private JFrame gOFrame;
	private JEditorPane gameOverText;
	private JButton btnBack;
	
	public GameOverForm(JFrame gOFrame,JFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.gOFrame = gOFrame;
		createPanel();
	}
	
	private void createPanel() {
		JPanel gameOver = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
		
		gameOverText = new JEditorPane();
		gameOverText.setContentType("text/html");
		gameOverText.setEditable(false);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.3;
		constraints.weighty = 0.5;
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 0;
		gameOver.add(gameOverText,constraints);
		
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(this);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.gridx = 1;
		constraints.gridy = 1;
		gameOver.add(btnBack,constraints);

		
		gOFrame.getContentPane().add(gameOver);
		gOFrame.setResizable(false);
		gOFrame.setSize(605, 400);
		gOFrame.setLocationRelativeTo(null);
		gOFrame.setVisible(false);
		gOFrame.setDefaultCloseOperation(gOFrame.DO_NOTHING_ON_CLOSE);

	}
	
	public void setHighestTile(int highestTile) {
		gameOverText.setText("<center><h1>GAME OVER</h1> <br><br><br><br><br> <h2>Highest Tile: " +highestTile+"</h2></center>");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnBack) {
			gOFrame.setVisible(false);
			mainFrame.setVisible(true);
		}
		
	}
}
