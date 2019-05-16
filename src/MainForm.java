import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainForm implements KeyListener, ChangeListener, ActionListener {
	//create variable references
	private JFrame mainFrame;
	private JFrame gOFrame;
	private JLabel[][] gridContents;
	private int gridSizeVal;
	private int freeCells;
	private JLabel prevNew;
	private JSpinner gridSize;
	private JPanel mainWind;
	private JButton btnStart;
	private JButton btnReset;
	private boolean gameStarted;
	private GameOverForm gameOverForm;
	//declare and initilize randomizer
	private Random rand = new Random();
	//create a int array with 2 and 4 stored
	private int[] newTileVal = {2,4};

	//MainForm constructor
	public MainForm(JFrame mainFrame,JFrame gOFrame,GameOverForm gameOverForm) {
		//set fields to passed in values
		this.mainFrame = mainFrame;
		this.gOFrame = gOFrame;
		this.gameOverForm = gameOverForm;
		//call create panel
		createPanel();
	}

	//createPanel Method
	private void createPanel() {

		//set the size of the components
		Dimension compSize = new Dimension(100,50);
		//set the initial location of the first component
		Point compPos = new Point(50,650);
		//declare and initilize Jlabel
		JLabel gridSizeLbl = new JLabel("Grid Size 3-10");

		//declare a SpinnerModel for the JSpinner to declare initial value, minimum, maximum, increment value
		SpinnerModel model = new SpinnerNumberModel(3, 3, 10, 1);
		//set the reference of gridsize to a new Jspinner using the created model
		gridSize = new JSpinner(model);
		//attach a change listner so when this is changed the grid can change
		gridSize.addChangeListener(this);
		//set gridSizeVal to the initial value of the spinner
		gridSizeVal = (int) gridSize.getValue();

		
		btnStart = new JButton("Start");
		btnStart.setLocation(compPos);
		btnStart.setSize(compSize);
		btnStart.addActionListener(this);

		compPos.move(compPos.x+200, compPos.y);
		gridSize.setLocation(compPos);
		gridSize.setSize(compSize);

		compPos.move(compPos.x+200, compPos.y);
		btnReset = new JButton("Reset");
		btnReset.setLocation(compPos);
		btnReset.setSize(compSize);
		btnReset.addActionListener(this);

		compPos.move(compPos.x-190, compPos.y-50);
		gridSizeLbl.setLocation(compPos);
		gridSizeLbl.setSize(compSize);

		mainWind = createPlayArea(gridSizeVal);


		mainFrame.addKeyListener(this);
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);
		mainFrame.add(mainWind);
		mainFrame.add(gridSize);
		mainFrame.add(btnStart);
		mainFrame.add(btnReset);
		mainFrame.add(gridSizeLbl);
		mainFrame.setSize(605, 800);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

		gridSize.setFocusable(false);
		mainFrame.setAutoRequestFocus(true);
		while(!mainFrame.isFocused()) {
			mainFrame.requestFocus();
		}
	}

	private JPanel createPlayArea(int gridSize) {
		gridContents = null;
		freeCells = (gridSize*gridSize)-1;
		JPanel p1 = new JPanel(new GridLayout(gridSize,gridSize));
		p1.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		p1.setSize(600, 600);
		Dimension d = new Dimension(p1.getWidth()/gridSize, p1.getHeight()/gridSize);
		gridContents = new JLabel[gridSize][gridSize];

		int startCellX = rand.nextInt(gridSize);
		int startCellY = rand.nextInt(gridSize);
		int newTile = rand.nextInt(newTileVal.length);

		for(int y = 0; y < gridSize; y++) {
			for(int x = 0; x < gridSize; x++) {
				JLabel label = null;

				if(x == startCellX && y == startCellY) 
					label = new JLabel(""+newTileVal[newTile]);
				else 
					label = new JLabel();


				label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				label.setSize(d);
				label.setHorizontalAlignment(JLabel.CENTER);

				gridContents[x][y] = label;

				p1.add(label);	
			}	
		}
		return p1;
	}


	private void moveCellsLeft() {
		int combinations = 2;
		for (int i = 0; i < combinations; i++) {
			for (int y = 0; y < gridSizeVal; y++) {
				int freeSpace = 0;
				for (int x = 1; x < gridSizeVal; x++) {
					if(!gridContents[x][y].getText().equals("")) {
						if(compareContents(gridContents[x-1][y],gridContents[x][y])) {
							combinations++;
						}
					}
				}
				for (int x = 0; x < gridSizeVal; x++) {

					if(gridContents[x][y].getText().equals("")) {
						freeSpace = x;
						break;
					}else {
						freeSpace = gridSizeVal;
					}
				}
				for (int x = 0; x < gridSizeVal; x++) {
					if(!gridContents[x][y].getText().equals("") && freeSpace < gridSizeVal && freeSpace < x) {
						gridContents[freeSpace][y].setText(gridContents[x][y].getText());
						gridContents[x][y].setText("");
						freeSpace++;
					}else {
						continue;
					}
				}
			}
		}
	}

	private void moveCellsRight() {
		int combinations = 2;

		for (int i = 0; i < combinations; i++) {
			for (int y = 0; y < gridSizeVal; y++) {
				int freeSpace = gridSizeVal-1;
				for (int x = gridSizeVal - 2; x >= 0 ; x--) {
					if(!gridContents[x][y].getText().equals("")) {
						if(compareContents(gridContents[x+1][y],gridContents[x][y])) {
							combinations++;
						}
					}
				}
				for (int x = gridSizeVal-1; x >= 0; x--) {

					if(gridContents[x][y].getText().equals("")) {
						freeSpace = x;
						break;
					}else {
						freeSpace = -1;
					}
				}
				for (int x = gridSizeVal-1; x >= 0; x--) {
					if(!gridContents[x][y].getText().equals("") && freeSpace > -1 && freeSpace > x) {
						gridContents[freeSpace][y].setText(gridContents[x][y].getText());
						gridContents[x][y].setText("");
						freeSpace--;
					}else {
						continue;
					}
				}
			}
		}
	}

	private void moveCellsUp() {
		int combinations = 2;

		for (int i = 0; i < combinations; i++) {
			for (int x = 0; x < gridSizeVal; x++) {
				int freeSpace = 0;
				for (int y = 1; y < gridSizeVal; y++) {
					if(!gridContents[x][y].getText().equals("")) {
						if(compareContents(gridContents[x][y-1],gridContents[x][y])) {
							combinations++;
						}
					}
				}
				for (int y = 0; y < gridSizeVal; y++) {

					if(gridContents[x][y].getText().equals("")) {
						freeSpace = y;
						break;
					}else {
						freeSpace = gridSizeVal;
					}
				}
				for (int y = 0; y < gridSizeVal; y++) {

					if(!gridContents[x][y].getText().equals("") && freeSpace < gridSizeVal && freeSpace < y) {
						gridContents[x][freeSpace].setText(gridContents[x][y].getText());
						gridContents[x][y].setText("");
						freeSpace++;
					}else {
						continue;
					}
				}
			}
		}

	}

	private void moveCellsDown() {
		int combinations = 2;

		for (int i = 0; i < combinations; i++) {
			for (int x = 0; x < gridSizeVal; x++) {
				int freeSpace = gridSizeVal-1;
				for (int y = gridSizeVal - 2; y >= 0 ; y--) {
					if(!gridContents[x][y].getText().equals("")) {
						if(compareContents(gridContents[x][y+1],gridContents[x][y])) {
							combinations++;
						}
					}
				}
				for (int y = gridSizeVal-1; y >= 0; y--) {
					if(gridContents[x][y].getText().equals("")) {
						freeSpace = y;
						break;
					}else {
						freeSpace = -1;
					}
				}
				for (int y = gridSizeVal-1; y >= 0; y--) {
					if(!gridContents[x][y].getText().equals("") && freeSpace > -1 && freeSpace > y) {
						gridContents[x][freeSpace].setText(gridContents[x][y].getText());
						gridContents[x][y].setText("");
						freeSpace--;
					}else {
						continue;
					}
				}
			}
		}
	}

	private boolean compareContents(JLabel cell1, JLabel cell2) {
		String cell1Text = cell1.getText();		
		String cell2Text = cell2.getText();
		if(cell1Text.equals(cell2Text)) {
			mergeContents(cell1, cell2);
			return true;
		}
		return false;
	}

	private void mergeContents(JLabel cell1, JLabel cell2) {
		cell1.setText(""+Integer.parseInt(cell1.getText())*2);
		cell2.setText("");
		freeCells++;
	}	

	private int[][] getFreeCells(){
		int[][] freeSpaces = new int[freeCells][2];
		int counter = 0;
		for (int y = 0; y < gridSizeVal; y++) {
			for (int x = 0; x < gridSizeVal; x++) {
				if(gridContents[x][y].getText().equals("") && counter < freeCells) {
					freeSpaces[counter][0] = x;
					freeSpaces[counter][1] = y;
					counter++;
				}
			}
		}
		return freeSpaces;
	}


	private void insertNewTile() {
		if(prevNew != null)
			prevNew.setForeground(Color.BLACK);
		if(freeCells > 0) {
			int[][] freeSpaces = getFreeCells();
			int newCellX = 0;
			int newCellY = 0;
			int newTileVal = rand.nextInt(this.newTileVal.length);
			int newTileLoc = rand.nextInt(freeSpaces.length);
			newCellX = freeSpaces[newTileLoc][0];
			newCellY = freeSpaces[newTileLoc][1];
			gridContents[newCellX][newCellY].setText(""+this.newTileVal[newTileVal]);
			gridContents[newCellX][newCellY].setForeground(Color.RED);
			prevNew = gridContents[newCellX][newCellY];
			freeCells--;
		}else if (isGameOver()) {
			gameOver();
		}
	}




	//	37 left
	//	38 up
	//	39 right
	//	40 bottom
	@Override
	public void keyPressed(KeyEvent e) {
		if(gameStarted) {
			if(e.getKeyCode() == 37){
				moveCellsLeft();
				insertNewTile();
			}else if(e.getKeyCode() == 38){
				moveCellsUp();
				insertNewTile();
			}else if(e.getKeyCode() == 39){
				moveCellsRight();
				insertNewTile();
			}else if(e.getKeyCode() == 40){
				moveCellsDown();
				insertNewTile();
			}
		}
	}

	private boolean isGameOver() {
		for(int y = 0; y < gridSizeVal; y += 2) {
			for(int x = 0; x < gridSizeVal; x++) {
				String cellContents = gridContents[x][y].getText();
				if(x-1 >= 0) {
					if(cellContents.equals(gridContents[x-1][y].getText())) {
						return false;
					}
				}
				if(x+1 < gridSizeVal) {
					if(cellContents.equals(gridContents[x+1][y].getText())) {
						return false;
					}
				}
				if(y-1 >= 0) {
					if(cellContents.equals(gridContents[x][y-1].getText())) {
						return false;
					}
				}
				if(y+1 < gridSizeVal) {
					if(cellContents.equals(gridContents[x][y+1].getText())) {
						return false;
					}
				}
			}

		}
		return true;
	}

	private int getHighestTile() {
		int highest = 0;
		for(int y = 0; y < gridSizeVal; y++) {
			for(int x = 0; x < gridSizeVal; x++) {
				int cellValue = Integer.parseInt(gridContents[x][y].getText());
				if(cellValue > highest) {
					highest = cellValue;
				}
			}
		}
		return highest;
	}

	private void gameOver() {
		gameOverForm.setHighestTile(getHighestTile());
		mainFrame.setVisible(false);
		gOFrame.setVisible(true);
	}

	private void resetGrid() {
		gameStarted = false;
		mainFrame.remove(mainWind);
		gridSizeVal = (int) gridSize.getValue();
		mainWind = createPlayArea(gridSizeVal);
		mainFrame.add(mainWind);
		mainFrame.revalidate();
		mainFrame.requestFocus();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == gridSize) {
			resetGrid();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart) {
			gameStarted = true;
		}else if(e.getSource() == btnReset) {
			resetGrid();
		}
		mainFrame.requestFocus();
	}



}