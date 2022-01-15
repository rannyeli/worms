package main.java;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * class for game frame
 * 
 * @author Ranny Elyashiv
 *
 */
public class GameFrame extends JFrame implements ActionListener, ChangeScreensInterface {

	public static Music _menu;// menu music
	private ImageIcon _logo;// frame logo
	private GamePanel _gamePanel;// the game panel
	private OpeningPanel _openingPanel;// the opening panel
	private InstructionsPanel _instructionsPanel;// the instruction panel
	private InstControlsPanel _instControlsPanel;// the controls instruction
													// panel
	private GameOptionsPanel _gameOptionsPanel;// the game options panel
	private ScreenTypes _screenType;// enum for screen Types
	// sizes of the frame
	public static int sizeOfScreenX = 1150;
	public static int sizeOfScreenY = 700;

	/**
	 * Ctor
	 */
	public GameFrame() {
		_logo = new ImageIcon(this.getClass().getClassLoader().getResource("Images\\logo.png"));
		_screenType = ScreenTypes.MainMenu;
		_gamePanel = new GamePanel();
		_gamePanel.getPreferredSize();
		_gamePanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
		_gamePanel.addListener(this);

		_openingPanel = new OpeningPanel();
		_openingPanel.getPreferredSize();
		this.add(_openingPanel);
		_openingPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
		_openingPanel.addListener(this);
		_menu = new Music("\\sounds\\menu.wav");
		_menu.Loop();

		_instructionsPanel = new InstructionsPanel();
		_instructionsPanel.getPreferredSize();
		_instructionsPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
		_instructionsPanel.addListener(this);

		_instControlsPanel = new InstControlsPanel();
		_instControlsPanel.getPreferredSize();
		_instControlsPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
		_instControlsPanel.addListener(this);

		_gameOptionsPanel = new GameOptionsPanel();
		_gameOptionsPanel.getPreferredSize();
		_gameOptionsPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
		_gameOptionsPanel.addListener(this);
		_gameOptionsPanel.setGameListener(_gamePanel);

		setLayout(null);
		setSize(1150, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		this.setIconImage(_logo.getImage());
	}

	// creating game frame object
	public static void main(String[] args) {
		GameFrame res = new GameFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * painting the current panel
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		if (_screenType == ScreenTypes.Game)
			_gamePanel.repaint();
		else if (_screenType == ScreenTypes.OverView)
			_instructionsPanel.repaint();
		else if (_screenType == ScreenTypes.Controls)
			_instControlsPanel.repaint();
		else if (_screenType == ScreenTypes.MainMenu)
			_openingPanel.repaint();
		else if (_screenType == ScreenTypes.GameOptions)
			_gameOptionsPanel.repaint();
	}

	/**
	 * changes the panels on the frame
	 */
	@Override
	public void changeScreenType(ScreenTypes st) {
		// TODO Auto-generated method stub
		this.remove(_openingPanel);
		this.remove(_instructionsPanel);
		this.remove(_instControlsPanel);
		this.remove(_gamePanel);
		this.remove(_gameOptionsPanel);

		if (st == ScreenTypes.Game) {
			this.add(_gamePanel);
			_gamePanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
			_screenType = ScreenTypes.Game;
			_menu.stop();
		} else if (st == ScreenTypes.OverView) {
			this.add(_instructionsPanel);
			_instructionsPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
			_screenType = ScreenTypes.OverView;
		} else if (st == ScreenTypes.Controls) {
			this.add(_instControlsPanel);
			_instControlsPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
			_screenType = ScreenTypes.Controls;
		} else if (st == ScreenTypes.MainMenu) {
			this.add(_openingPanel);
			_openingPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
			_screenType = ScreenTypes.MainMenu;
		} else if (st == ScreenTypes.GameOptions) {
			this.add(_gameOptionsPanel);
			_gameOptionsPanel.setBounds(0, 0, sizeOfScreenX, sizeOfScreenX);
			_screenType = ScreenTypes.GameOptions;
		}
		this.repaint();
	}

}
