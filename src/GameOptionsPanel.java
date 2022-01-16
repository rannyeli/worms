
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Game options panel
 * 
 * @author Ranny Elyashiv
 *
 */
public class GameOptionsPanel extends JPanel {

	private Image _imgBackground; // the game options image;
	private Rectangle _game; // game button area
	private Rectangle _mainMenu; // menu button area
	private JButton _buttons[];
	private Img _usaLayer;
	private Img _israelLayer;
	private Img _russiaLayer;
	private Img _plusL;
	private Img _minusL;
	private Img _number;

	private InitWormsInterface _gameListener;
	private LinkedList<ChangeScreensInterface> _listeners;
	private static int _numOfWorms;
	private static int _teamNum;// usa-1, israel-2, russia-3

	/**
	 * Ctor
	 */
	public GameOptionsPanel() {
		// TODO Auto-generated constructor stub
		_numOfWorms = 3;
		_teamNum = 0;

		ImageIcon imgI = new ImageIcon("src/Images/Game Options.png");
		_imgBackground = imgI.getImage();
		_game = new Rectangle(483, 510, 166, 70);
		_mainMenu = new Rectangle(168, 154, 39, 39);
		_usaLayer = new Img("Images\\USA-layer.png", 345, 379, 40, 20);
		_israelLayer = new Img("Images\\Israel-layer.png", 528, 379, 70, 20);
		_russiaLayer = new Img("Images\\Russia-layer.png", 732, 379, 70, 20);
		_plusL = new Img("Images\\plus.png", 635, 445, 25, 25);
		_minusL = new Img("Images\\minus.png", 547, 452, 22, 10);
		_number = new Img("Images\\3.png", 590, 444, 25, 25);
		_listeners = new LinkedList<ChangeScreensInterface>();
		_buttons = new JButton[5];
		for (int i = 0; i < 5; i++) {
			_buttons[i] = new JButton();
			_buttons[i].setRolloverEnabled(false);
			_buttons[i].setBorderPainted(false);
			_buttons[i].setContentAreaFilled(false);
			add(_buttons[i]);
		}
		_buttons[0].setBounds(303, 257, 131, 110);
		_buttons[1].setBounds(500, 257, 131, 110);
		_buttons[2].setBounds(699, 257, 131, 110);
		_buttons[3].setBounds(635, 445, 25, 25);
		_buttons[4].setBounds(547, 452, 22, 10);
		_buttons[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				_teamNum = 1;
				_israelLayer.setPath("Images\\Israel-layer.png");
				_russiaLayer.setPath("Images\\Russia-layer.png");
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				new Music("\\sounds\\overButton.wav");
				_usaLayer.setPath("Images\\USA-layer-s.png");
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				if (_teamNum != 1) {
					_usaLayer.setPath("Images\\USA-layer.png");
					repaint();
				}
			}
		});

		_buttons[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				_teamNum = 2;
				_usaLayer.setPath("Images\\USA-layer.png");
				_russiaLayer.setPath("Images\\Russia-layer.png");
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				new Music("\\sounds\\overButton.wav");
				_israelLayer.setPath("Images\\Israel-layer-s.png");
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				if (_teamNum != 2) {
					_israelLayer.setPath("Images\\Israel-layer.png");
					repaint();
				}
			}
		});

		_buttons[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				_teamNum = 3;
				_usaLayer.setPath("Images\\USA-layer.png");
				_israelLayer.setPath("Images\\Israel-layer.png");
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				new Music("\\sounds\\overButton.wav");
				_russiaLayer.setPath("Images\\Russia-layer-s.png");
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				if (_teamNum != 3) {
					_russiaLayer.setPath("Images\\Russia-layer.png");
					repaint();
				}
			}
		});

		_buttons[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				if (_numOfWorms < 5)
					_numOfWorms++;
				_number.setPath("Images\\" + _numOfWorms + ".png");
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				new Music("\\sounds\\overButton.wav");
				_plusL.setPath("Images\\plus-s.png");
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				_plusL.setPath("Images\\plus.png");
				repaint();
			}
		});

		_buttons[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				if (_numOfWorms > 0)
					_numOfWorms--;
				_number.setPath("Images\\" + _numOfWorms + ".png");
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				new Music("\\sounds\\overButton.wav");
				_minusL.setPath("Images\\minus-s.png");
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
				_minusL.setPath("Images\\minus.png");
				repaint();
			}
		});
		setPreferredSize(new Dimension(GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY));
		this.setLayout(null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				if (_game.contains(e.getPoint())) {
					new Music("\\sounds\\buttonPressed.wav");
					if (_teamNum != 0) {
						for (ChangeScreensInterface hl : _listeners)
							hl.changeScreenType(ScreenTypes.Game);
						_gameListener.restart();
					}
				} else if (_mainMenu.contains(e.getPoint())) {
					new Music("\\sounds\\backPressed.wav");
					for (ChangeScreensInterface hl : _listeners)
						hl.changeScreenType(ScreenTypes.MainMenu);
				}
			}
		});

	}

	/**
	 * adds a new listener to listeners list
	 * 
	 * @param toAdd
	 */
	public void addListener(ChangeScreensInterface toAdd) {
		_listeners.add(toAdd);
	}

	public void setGameListener(InitWormsInterface toSet) {
		_gameListener = toSet;
	}

	public static int getNumOfWorms() {
		return _numOfWorms;
	}

	public static int getTeamNum() {
		return _teamNum;
	}

	public static void setNumOfWorms(int num) {
		_numOfWorms = num;
	}

	public static void setTeamNum(int num) {
		_teamNum = num;
	}

	/**
	 * painting the panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(_imgBackground, 0, 0, GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY, this);
		_usaLayer.drawImg(g);
		_israelLayer.drawImg(g);
		_russiaLayer.drawImg(g);
		_plusL.drawImg(g);
		_minusL.drawImg(g);
		_number.drawImg(g);
	}
}