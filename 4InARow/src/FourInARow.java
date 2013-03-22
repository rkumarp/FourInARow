

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

public class FourInARow implements ActionListener, ItemListener,MouseListener {
	// create objects
	Board board = new Board();
	JFrame frameMainWindow;
	JFrame frameWin;
	JFrame frameCredits;

	JPanel panelBoardNumbers;
	JLayeredPane layeredGameBoard;

	private static Player p1 = new Human();
	private static Player p2 = new MinMax();

	private static int p1TypeFlag = 0;
	private static int p2TypeFlag = 1;

	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {
		int col = e.getX() / (frameMainWindow.getWidth() / 7);

		if (board.next() == 1) p1.setMove(col);
		else p2.setMove(col);
		game();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu, submenu, subsubmenu;
		JMenuItem menuItem;
		JRadioButtonMenuItem rbMenuItem;
		int[][] boardView;

		// create and build first menu
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);
		// add items to menu
		menuItem = new JMenuItem("New game", KeyEvent.VK_N);
		menuItem.setName("NewGame");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
		menuItem.setName("QuitGame");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// create and build second menu
		menu = new JMenu("Players");
		menu.setMnemonic(KeyEvent.VK_P);
		menuBar.add(menu);
		// add items to menu
		// Submenu one
		submenu = new JMenu("Player 1");
		ButtonGroup groupPlayers1 = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Human");
		if (p1.getType() == 0) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P1Human");
		rbMenuItem.addItemListener(this);
		groupPlayers1.add(rbMenuItem);
		submenu.add(rbMenuItem);
		subsubmenu = new JMenu("Computer");

		rbMenuItem = new JRadioButtonMenuItem("Defensive Player");
		if (p1.getType() == 2) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P1Defensive");
		rbMenuItem.addItemListener(this);
		groupPlayers1.add(rbMenuItem);
		subsubmenu.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("Aggressive Player");
		if (p1.getType() == 3) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P1Aggressive");
		rbMenuItem.addItemListener(this);
		groupPlayers1.add(rbMenuItem);
		subsubmenu.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("MinMax Player");
		if (p1.getType() == 4) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P1MinMax");
		rbMenuItem.addItemListener(this);
		groupPlayers1.add(rbMenuItem);
		subsubmenu.add(rbMenuItem);
		submenu.add(subsubmenu);
		menu.add(submenu);
		// submenu 2
		submenu = new JMenu("Player 2");
		ButtonGroup groupPlayers2 = new ButtonGroup();
		subsubmenu = new JMenu("Computer");

		rbMenuItem = new JRadioButtonMenuItem("Defensive Player");
		if (p2.getType() == 2) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P2Defensive");
		rbMenuItem.addItemListener(this);
		groupPlayers2.add(rbMenuItem);
		subsubmenu.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("Aggressive Player");
		if (p2.getType() == 3) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P2Aggressive");
		rbMenuItem.addItemListener(this);
		groupPlayers2.add(rbMenuItem);
		subsubmenu.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("MinMax Player");
		if (p2.getType() == 4) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P2MinMax");
		rbMenuItem.addItemListener(this);
		groupPlayers2.add(rbMenuItem);
		subsubmenu.add(rbMenuItem);
		submenu.add(subsubmenu);
		rbMenuItem = new JRadioButtonMenuItem("Human");
		if (p1.getType() == 0) rbMenuItem.setSelected(true);
		rbMenuItem.setName("P2Human");
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_H);
		groupPlayers2.add(rbMenuItem);
		rbMenuItem.addItemListener(this);
		submenu.add(rbMenuItem);
		menu.add(submenu);
		menuBar.add(menu);
		return menuBar;
	}

	public  JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(570, 490));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("4 in a Row"));

		paintBG();
		return layeredGameBoard;
	}

	public  void createNewGame() {
		board = new Board();
		board.out=false;

		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("4 in a Row");
		frameMainWindow.setBounds(100, 100, 400, 300);
		frameMainWindow.setJMenuBar( this.createMenuBar() );
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frameMainWindow.addMouseListener(this);
		frameMainWindow.pack();
		frameMainWindow.setVisible(true);

		if (p1TypeFlag == 1) {
			p1.go(board);
			redrawBoard();
		}

		if ( (p1TypeFlag == 1) && (p2TypeFlag == 1) ) {
			panelBoardNumbers.setEnabled(false);
			panelBoardNumbers.repaint();
			while (!board.over() ){
				if (board.next() == 1) p1.go(board);
				else p2.go(board);
				redrawBoard();
			}
			panelBoardNumbers.setVisible(false);
			panelBoardNumbers.repaint();
		}
		panelBoardNumbers.setVisible(false);
	}
	public  void createNewGameBoard(Board board) {
		board.out=false;

		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("4 in a row");
		frameMainWindow.setBounds(100, 100, 400, 300);
		FourInARow fourInARow = new FourInARow();
		frameMainWindow.setJMenuBar( fourInARow.createMenuBar() );
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		frameMainWindow.pack();
		frameMainWindow.setVisible(true);

		if (p1TypeFlag == 1) {
			p1.go(board);
			redrawBoard();
		}

		if ( (p1TypeFlag == 1) && (p2TypeFlag == 1) ) {
			panelBoardNumbers.setEnabled(false);
			panelBoardNumbers.repaint();
			while (!board.over() ){
				if (board.next() == 1) p1.go(board);
				else p2.go(board);
				redrawBoard();
			}
			panelBoardNumbers.setVisible(false);
			panelBoardNumbers.repaint();
		}
	}
	public  void paintRed(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon redIcon = new ImageIcon("images/red.png");
		JLabel redIconLabel = new JLabel(redIcon);
		redIconLabel.setBounds(27 + xOffset, 27 + yOffset, redIcon.getIconWidth(),redIcon.getIconHeight());
		layeredGameBoard.add(redIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	public void paintBG(){
		for(int i = 0; i < 6;i++){
			for(int j = 0; j < 7 ; j++){
				int xOffset = 75 * j;
				int yOffset = 75 * i;
				ImageIcon redIcon = new ImageIcon("images/images.jpg");
				JLabel redIconLabel = new JLabel(redIcon);
				redIconLabel.setBounds(22 + xOffset, 22 + yOffset, redIcon.getIconWidth(),redIcon.getIconHeight());
				layeredGameBoard.add(redIconLabel, new Integer(0), 0);
				frameMainWindow.paint(frameMainWindow.getGraphics());
			}
		}
	}

	public  void paintBlack(int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon blackIcon = new ImageIcon("images/whiteB.jpg");
		JLabel blackIconLabel = new JLabel(blackIcon);
		blackIconLabel.setBounds(27 + xOffset, 27 + yOffset, blackIcon.getIconWidth(),blackIcon.getIconHeight());
		layeredGameBoard.add(blackIconLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}

	public  void redrawBoard() {

		int[][] boardView = board.view();
		int r = board.m_x;
		int c = board.m_y;

		int playerPos = boardView[r][c];
		if (playerPos == 1) {
			paintRed(r, c);
		} else if (playerPos == 2) {
			paintBlack(r, c);
		}
		if (board.over() == true) {
			gameOver();
		}

	}

	public  void game(){

		if (board.next() == 1) p1.go(board);
		else p2.go(board);
		redrawBoard();

		if (!board.over()) {
			int nextTypeFlag = 0;
			if (board.next() == 1) nextTypeFlag = p1TypeFlag;
			else nextTypeFlag = p2TypeFlag;
			if (nextTypeFlag == 1) {
				if (board.next() == 1) p1.go(board);
				else p2.go(board);
				redrawBoard();

			}

		}

	}

	/**
	 * @return Component - Returns a component to be drawn by main window
	 * @see main()
	 * This function creates the main window components.
	 */
	public  Component createContentComponents() {

		// create panels to hold and organize board numbers
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, 7, 4, 4));
		JButton buttonCol0 = new JButton("0");
		buttonCol0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(0);
				else p2.setMove(0);
				game();
			}
		});
		JButton buttonCol1 = new JButton("1");
		buttonCol1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(1);
				else p2.setMove(1);
				game();
			}
		});
		JButton buttonCol2 = new JButton("2");
		buttonCol2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(2);
				else p2.setMove(2);
				game();
			}
		});
		JButton buttonCol3 = new JButton("3");
		buttonCol3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(3);
				else p2.setMove(3);
				game();
			}
		});
		JButton buttonCol4 = new JButton("4");
		buttonCol4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(4);
				else p2.setMove(4);
				game();
			}
		});
		JButton buttonCol5 = new JButton("5");
		buttonCol5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(5);
				else p2.setMove(5);
				game();
			}
		});
		JButton buttonCol6 = new JButton("6");
		buttonCol6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (board.next() == 1) p1.setMove(6);
				else p2.setMove(6);
				game();
			}
		});
		panelBoardNumbers.add(buttonCol0);
		panelBoardNumbers.add(buttonCol1);
		panelBoardNumbers.add(buttonCol2);
		panelBoardNumbers.add(buttonCol3);
		panelBoardNumbers.add(buttonCol4);
		panelBoardNumbers.add(buttonCol5);
		panelBoardNumbers.add(buttonCol6);

		// create game board with pieces
		layeredGameBoard = createLayeredBoard();

		// create panel to hold all of above
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		//panelMain.setLayout(new GridLayout(1, 2, 4, 4));

		// add objects to pane
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		return panelMain;
	}

	// Returns just the class name -- no package info.
	protected String getClassName(Object o) {
		String classString = o.getClass().getName();
		int dotIndex = classString.lastIndexOf(".");
		return classString.substring(dotIndex+1);
	}

	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		String s = source.getName();

		if ( s.equals("NewGame") ) {
			// create new game
			createNewGame();
		} else if ( s.equals("QuitGame")) {
			System.exit(0);
		} else if (s.equals("Contents")) {
			// contents window link
		} else if (s.equals("About")) {
			showCredits();
		}

	}

	public void itemStateChanged(ItemEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		String s = source.getName() +
				((e.getStateChange() == ItemEvent.SELECTED) ?
						"-selected":"-unselected");

		if (s.equals("P1Human-selected")) {
			p1 = new Human();
			p1TypeFlag = 0;
		} else if (s.equals("P1Defensive-selected")) {
			p1 = new Defensive();
			p1TypeFlag = 1;
		} else if (s.equals("P1Aggressive-selected")) {
			p1 = new Agressive();
			p1TypeFlag = 1;
		} else if (s.equals("P1MinMax-selected")) {
			p1 = new MinMax();
			p1TypeFlag = 1;
		} else if (s.equals("P2Human-selected")) {
			p2 = new Human();
			p2TypeFlag = 0;
		} else if (s.equals("P2Defensive-selected")) {
			p2 = new Defensive();
			p2TypeFlag = 1;
		} else if (s.equals("P2Aggressive-selected")) {
			p2 = new Agressive();
			p2TypeFlag = 1;
		} else if (s.equals("P2MinMax-selected")) {
			p2 = new MinMax();
			p2TypeFlag = 1;
		}

	}

	public  void gameOver() {

		//System.out.println(board.movelist);

		panelBoardNumbers.setVisible(false);
		frameWin = new JFrame("You Win!");
		frameWin.setBounds(300, 300, 220, 120);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

		//ImageIcon winIcon = new ImageIcon("images/win.gif");
		//JLabel winLabel = new JLabel(winIcon);
		JLabel winLabel;
		if (board.winner == 1) {
			winLabel = new JLabel("Player 1 wins!");
			winPanel.add(winLabel);
		} else if (board.winner == 2) {
			winLabel = new JLabel("Player 2 wins!");
			winPanel.add(winLabel);
		} else {
			winLabel = new JLabel("Nobody Win! - You both loose!");
			winPanel.add(winLabel);
		}
		winPanel.add(winLabel, BorderLayout.NORTH);
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameWin.setVisible(false);
			}
		});
		winPanel.add(okButton, BorderLayout.SOUTH);
		frameWin.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameWin.setVisible(true);
	}

	public  void showCredits() {
		frameCredits = new JFrame("Credits");
		frameCredits.setBounds(300, 300, 480, 320);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));


		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frameCredits.setVisible(false);
			}
		});

		frameCredits.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameCredits.setVisible(true);
	}

	public static void main(String[] args) {
		// Set look and feel to the java look
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) { }

		new FourInARow().createNewGame();
	}

}
