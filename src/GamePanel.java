
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Game panel
 * 
 * @author Ranny Elyashiv
 *
 */
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseMotionListener, InitWormsInterface {

	private Thread _wormsThread;// computer thread
	protected boolean _isRunning;

	// map variables
	private int _numOfRows;
	private int _numOfCols;
	private int _blockSize;
	private Img _block;
	protected static Map _map;
	private String _mapName;
	private int _mapNumber;
	private Line2D _currentLine;
	private LinkedList<Point> _upPoints;
	private LinkedList<Point> _downPoints;
	private LinkedList<Point> _strightPoints;
	private LinkedList<Point> _blockPoints;
	private LinkedList<Point> _startPoints;
	private LinkedList<String> _israelNames;
	private LinkedList<String> _usaNames;
	private LinkedList<String> _russiaNames;
	private Random _rnd;
	private Image _gameImg;

	// key variables
	private int _pressedCode;

	// win variables
	private boolean _isWin;
	private Img _winPanel;

	// pause variables
	private JButton _pauseBtn;
	private Img _pauseB;
	private Img _shadow;
	private Img _pausePanel;
	private boolean _isPaused;
	private Rectangle _resume;
	private Rectangle _restart;
	private Rectangle _inst;
	private Rectangle _menu;
	private LinkedList<ChangeScreensInterface> _listeners;

	// enemy variables
	private Rectangle _intention;
	private Point _p1;
	private Point _p2;
	private double _m;
	private int _direct;
	private Line2D _shootRange1;
	private Line2D _shootRange2;
	private int _wormToShoot;

	// worm variables
	private int _teamNum = 0;
	private int _enemyTeamNum;
	private int _numOfWorms = 0;
	protected Sprite[] _worm;
	private ImageIcon _imgIcon;
	private Image _wormImg;

	// turn variables
	private int _wormTurn;
	private Img _turnName;
	private int _r;
	private int _b;
	private int _teamTurn;
	private Img _timer;
	protected Timer _turnTimer;
	private int _seconds;

	// offset variables
	protected static int _xOffSet = 0;
	private int _lastX;
	private int _mouseSpeedX = 20;
	protected int _speedX = 2;
	protected int _limit = 1150;

	// music variables
	protected Music _launch;
	protected Music _explotion;
	protected Music _jump;
	protected Music _BG;
	protected Music _winning;

	/**
	 * Ctor
	 */
	public GamePanel() {
		_rnd = new Random();
		_upPoints = new LinkedList<Point>();
		_downPoints = new LinkedList<Point>();
		_strightPoints = new LinkedList<Point>();
		_blockPoints = new LinkedList<Point>();
		_startPoints = new LinkedList<Point>();
		_israelNames = new LinkedList<String>();
		_usaNames = new LinkedList<String>();
		_russiaNames = new LinkedList<String>();

		// israel names
		_israelNames.add("yosef");
		_israelNames.add("jacob");
		_israelNames.add("yehuda");
		_israelNames.add("david");
		_israelNames.add("avraham");

		// usa names
		_usaNames.add("jhon");
		_usaNames.add("thomas");
		_usaNames.add("james");
		_usaNames.add("michael");
		_usaNames.add("louis");

		// russia names
		_russiaNames.add("alex");
		_russiaNames.add("dmitry");
		_russiaNames.add("igor");
		_russiaNames.add("juri");
		_russiaNames.add("emil");

		_numOfRows = 16;
		_numOfCols = 55;
		_blockSize = 42;

		_isWin = false;

		_listeners = new LinkedList<ChangeScreensInterface>();
		_resume = new Rectangle(477, 181, 171, 75);
		_restart = new Rectangle(477, 295, 171, 75);
		_inst = new Rectangle(477, 404, 171, 75);
		_menu = new Rectangle(477, 514, 171, 75);
		_isPaused = false;
		_shadow = new Img("Images//shadow.png", 0, 0, 1150, 700);
		_pausePanel = new Img("Images//pause.png", 330, 30, 475, 625);
		_pauseB = new Img("Images//pauseButton.png", 20, 20, 50, 50);
		_pauseBtn = new JButton();
		_pauseBtn.setRolloverEnabled(false);
		_pauseBtn.setBorderPainted(false);
		_pauseBtn.setContentAreaFilled(false);
		_pauseBtn.setBounds(20, 20, 50, 50);
		add(_pauseBtn);
		_pauseBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				if (!_isWin) {
					new Music("\\sounds\\buttonPressed.wav");
					_isPaused = true;
					_BG.stop();
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseEntered(e);
				new Music("\\sounds\\overButton.wav");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseExited(e);
			}
		});

		addKeyListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY));
		this.setFocusable(true);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				// System.out.println("(" + e.getX() + ", " + e.getY() + ")");
				_lastX = e.getX();
				if (_isPaused) {
					_restart = new Rectangle(477, 295, 171, 75);
					_menu = new Rectangle(477, 514, 171, 75);
					if (_resume.contains(e.getPoint())) {
						new Music("\\sounds\\buttonPressed.wav");
						_isPaused = false;
						_BG = new Music("\\sounds\\" + _mapName + ".wav");
						_BG.Loop();
						repaint();
					} else if (_restart.contains(e.getPoint())) {
						new Music("\\sounds\\buttonPressed.wav");
						restart();
					} else if (_inst.contains(e.getPoint())) {
						new Music("\\sounds\\buttonPressed.wav");
						_BG.stop();
						InstructionsPanel.setPrevScreen("game");
						for (ChangeScreensInterface hl : _listeners)
							hl.changeScreenType(ScreenTypes.OverView);
					} else if (_menu.contains(e.getPoint())) {
						new Music("\\sounds\\backPressed.wav");
						_BG.stop();
						GameFrame._menu = new Music("\\sounds\\menu.wav");
						GameFrame._menu.Loop();
						for (ChangeScreensInterface hl : _listeners)
							hl.changeScreenType(ScreenTypes.MainMenu);
					}
				}
				if (_isWin) {
					_restart = new Rectangle(343, 366, 171, 75);
					_menu = new Rectangle(573, 366, 171, 75);
					if (_restart.contains(e.getPoint())) {
						new Music("\\sounds\\buttonPressed.wav");
						_winning.stop();
						restart();
					} else if (_menu.contains(e.getPoint())) {
						new Music("\\sounds\\backPressed.wav");
						_winning.stop();
						GameFrame._menu = new Music("\\sounds\\menu.wav");
						GameFrame._menu.Loop();
						for (ChangeScreensInterface hl : _listeners)
							hl.changeScreenType(ScreenTypes.MainMenu);
					}
				}
			}

		});

		// timer which managing the turns
		_turnTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!_isPaused && !_isWin) {
					_seconds--;
					_timer.setPath("Images\\timer\\" + _seconds + ".png");
					repaint();
					if (_seconds == 0) {
						if (_worm[_wormTurn] != null) {
							_worm[_wormTurn]._isAiming = false;
							_worm[_wormTurn].setCurrentFrame(4);
						}
						repaint();
						_teamTurn *= (-1);
						if (_teamTurn == 1) {
							_r++;
							if (_r == _numOfWorms) {
								_r = 0;
							}
							while (_worm[_r] == null && !_isWin) {
								_r++;
								if (_r == _numOfWorms) {
									_r = 0;
								}
							}
							_wormTurn = _r;
						} else {
							_b++;
							if (_b == _numOfWorms * 2) {
								_b = _numOfWorms;
							}
							while (_worm[_b] == null && !_isWin) {
								_b++;
								if (_b == _numOfWorms * 2) {
									_b = _numOfWorms;
								}
							}
							_wormTurn = _b;
							if (_worm[_wormTurn].getX() - _xOffSet < 1150)
								_direct = 1;
							else
								_direct = -1;
						}
						focusOnWorm();
						_seconds = 30;
						_timer.setPath("Images\\timer\\" + _seconds + ".png");
						repaint();
					}
				}
			}
		});

	}

	/**
	 * initalize all worms
	 */
	public void initWorms() {
		// TODO Auto-generated method stub
		_teamNum = GameOptionsPanel.getTeamNum();
		_numOfWorms = GameOptionsPanel.getNumOfWorms();
		_b = _numOfWorms - 1;
		_r = 0;
		_teamTurn = 1;
		_enemyTeamNum = _teamNum;
		while (_enemyTeamNum == _teamNum) {
			_enemyTeamNum = _rnd.nextInt(3) + 1;
		}
		_worm = new Sprite[_numOfWorms * 2];
		for (int i = 0; i < _numOfWorms; i++) {
			String name;
			int j = _rnd.nextInt(_startPoints.size());
			Point start = _startPoints.get(j);
			if (_teamNum == 1)
				name = _usaNames.get(i);
			else if (_teamNum == 2)
				name = _israelNames.get(i);
			else
				name = _russiaNames.get(i);
			_worm[i] = new Sprite(this, 5, 6, _teamNum, "red", name, (int) start.getX(), (int) start.getY());
			_startPoints.remove(j);
		}
		for (int i = _numOfWorms; i < _numOfWorms * 2; i++) {
			String name;
			int j = _rnd.nextInt(_startPoints.size());
			Point start = _startPoints.get(j);
			if (_enemyTeamNum == 1)
				name = _usaNames.get(i - _numOfWorms);
			else if (_enemyTeamNum == 2)
				name = _israelNames.get(i - _numOfWorms);
			else
				name = _russiaNames.get(i - _numOfWorms);
			_worm[i] = new Sprite(this, 5, 6, _enemyTeamNum, "blue", name, (int) start.getX(), (int) start.getY());
			_startPoints.remove(j);
		}
		_wormTurn = _r;
		_seconds = 30;
		_timer = new Img("Images\\timer\\" + _seconds + ".png", 1000, 10, 140, 120);
		_turnTimer.start();
		repaint();
		focusOnWorm();
		startRunning();
	}

	/**
	 * creating a new game
	 */
	@Override
	public void restart() {
		_upPoints = new LinkedList<Point>();
		_downPoints = new LinkedList<Point>();
		_strightPoints = new LinkedList<Point>();
		_blockPoints = new LinkedList<Point>();
		_startPoints = new LinkedList<Point>();
		_turnTimer.stop();
		_mapNumber = _rnd.nextInt(3) + 1;
		Point p;
		if (_mapNumber == 1) {
			_mapName = "grass";
			// add up points
			p = new Point(134, 377);
			_upPoints.add(p);
			p = new Point(336, 175);
			_upPoints.add(p);
			p = new Point(257, 503);
			_upPoints.add(p);
			p = new Point(337, 424);
			_upPoints.add(p);
			p = new Point(627, 633);
			_upPoints.add(p);
			p = new Point(883, 381);
			_upPoints.add(p);
			p = new Point(964, 381);
			_upPoints.add(p);
			p = new Point(1136, 214);
			_upPoints.add(p);

			p = new Point(420 + 1140, 335);
			_upPoints.add(p);
			p = new Point(501 + 1140, 254);
			_upPoints.add(p);
			p = new Point(583 + 1140, 255);
			_upPoints.add(p);
			p = new Point(625 + 1140, 212);
			_upPoints.add(p);
			p = new Point(798 + 1140, 209);
			_upPoints.add(p);
			p = new Point(836 + 1140, 169);
			_upPoints.add(p);
			p = new Point(590 + 1140, 503);
			_upPoints.add(p);
			p = new Point(626 + 1140, 463);
			_upPoints.add(p);
			p = new Point(920 + 1140, 172);
			_upPoints.add(p);
			p = new Point(1004 + 1140, 86);
			_upPoints.add(p);
			p = new Point(843 + 1140, 418);
			_upPoints.add(p);
			p = new Point(877 + 1140, 380);
			_upPoints.add(p);
			// add down points
			p = new Point(82, 423);
			_downPoints.add(p);
			p = new Point(213, 551);
			_downPoints.add(p);
			p = new Point(294, 554);
			_downPoints.add(p);
			p = new Point(379, 637);
			_downPoints.add(p);
			p = new Point(463, 173);
			_downPoints.add(p);
			p = new Point(589, 300);
			_downPoints.add(p);
			p = new Point(587, 426);
			_downPoints.add(p);
			p = new Point(665, 503);
			_downPoints.add(p);
			p = new Point(713, 301);
			_downPoints.add(p);
			p = new Point(791, 378);
			_downPoints.add(p);

			p = new Point(117 + 1140, 212);
			_downPoints.add(p);
			p = new Point(288 + 1140, 382);
			_downPoints.add(p);
			p = new Point(370 + 1140, 382);
			_downPoints.add(p);
			p = new Point(627 + 1140, 636);
			_downPoints.add(p);
			p = new Point(705 + 1140, 212);
			_downPoints.add(p);
			p = new Point(828 + 1140, 335);
			_downPoints.add(p);
			p = new Point(789 + 1140, 464);
			_downPoints.add(p);
			p = new Point(828 + 1140, 503);
			_downPoints.add(p);
			p = new Point(1044 + 1140, 381);
			_downPoints.add(p);
			p = new Point(1081 + 1140, 419);
			_downPoints.add(p);
			// add stright points
			p = new Point(85, 421);
			_strightPoints.add(p);
			p = new Point(1, 420);
			_strightPoints.add(p);
			p = new Point(211, 548);
			_strightPoints.add(p);
			p = new Point(294, 549);
			_strightPoints.add(p);
			p = new Point(379, 632);
			_strightPoints.add(p);
			p = new Point(629, 631);
			_strightPoints.add(p);
			p = new Point(322, 421);
			_strightPoints.add(p);
			p = new Point(589, 420);
			_strightPoints.add(p);
			p = new Point(871, 379);
			_strightPoints.add(p);
			p = new Point(970, 378);
			_strightPoints.add(p);
			p = new Point(589, 294);
			_strightPoints.add(p);
			p = new Point(716, 295);
			_strightPoints.add(p);
			p = new Point(322, 169);
			_strightPoints.add(p);
			p = new Point(464, 169);
			_strightPoints.add(p);

			p = new Point(287 + 1140, 379);
			_strightPoints.add(p);
			p = new Point(375 + 1140, 379);
			_strightPoints.add(p);
			p = new Point(485 + 1140, 253);
			_strightPoints.add(p);
			p = new Point(583 + 1140, 253);
			_strightPoints.add(p);
			p = new Point(609 + 1140, 463);
			_strightPoints.add(p);
			p = new Point(793 + 1140, 463);
			_strightPoints.add(p);
			p = new Point(863 + 1140, 379);
			_strightPoints.add(p);
			p = new Point(1046 + 1140, 379);
			_strightPoints.add(p);
			p = new Point(610 + 1140, 211);
			_strightPoints.add(p);
			p = new Point(711 + 1140, 211);
			_strightPoints.add(p);
			p = new Point(820 + 1140, 168);
			_strightPoints.add(p);
			p = new Point(922 + 1140, 168);
			_strightPoints.add(p);
			p = new Point(988 + 1140, 85);
			_strightPoints.add(p);
			p = new Point(1143 + 1140, 85);
			_strightPoints.add(p);
			p = new Point(621 + 1140, 631);
			_strightPoints.add(p);
			p = new Point(1143 + 1140, 631);
			_strightPoints.add(p);
			p = new Point(520 + 600, 211);
			_strightPoints.add(p);
			p = new Point(662 + 600, 211);
			_strightPoints.add(p);
			// add block points
			p = new Point(256, 502);
			_blockPoints.add(p);
			p = new Point(667, 502);
			_blockPoints.add(p);
			p = new Point(135, 376);
			_blockPoints.add(p);
			p = new Point(791, 376);
			_blockPoints.add(p);

			p = new Point(419 + 1140, 334);
			_blockPoints.add(p);
			p = new Point(826 + 1140, 334);
			_blockPoints.add(p);
			p = new Point(590 + 1140, 503);
			_blockPoints.add(p);
			p = new Point(826 + 1140, 503);
			_blockPoints.add(p);
			p = new Point(842 + 1140, 418);
			_blockPoints.add(p);
			p = new Point(1080 + 1140, 418);
			_blockPoints.add(p);
			p = new Point(798 + 1140, 208);
			_blockPoints.add(p);
			p = new Point(1141 + 1140, 208);
			_blockPoints.add(p);
			// start points
			p = new Point(376, 116);
			_startPoints.add(p);
			p = new Point(617, 242);
			_startPoints.add(p);
			p = new Point(503, 370);
			_startPoints.add(p);
			p = new Point(412, 581);
			_startPoints.add(p);

			p = new Point(19 + 1140, 159);
			_startPoints.add(p);
			p = new Point(697 + 1140, 410);
			_startPoints.add(p);
			p = new Point(722 + 1140, 579);
			_startPoints.add(p);
			p = new Point(630 + 1140, 158);
			_startPoints.add(p);
			p = new Point(861 + 1140, 116);
			_startPoints.add(p);
			p = new Point(1002 + 1140, 327);
			_startPoints.add(p);
		} else if (_mapNumber == 2) {
			_mapName = "hot";
			// add up points
			p = new Point(383, 251);
			_upPoints.add(p);
			p = new Point(507, 126);
			_upPoints.add(p);
			p = new Point(551, 419);
			_upPoints.add(p);
			p = new Point(632, 336);
			_upPoints.add(p);
			p = new Point(720, 502);
			_upPoints.add(p);
			p = new Point(759, 462);
			_upPoints.add(p);
			p = new Point(972, 418);
			_upPoints.add(p);
			p = new Point(1053, 336);
			_upPoints.add(p);
			p = new Point(972, 208);
			_upPoints.add(p);
			p = new Point(1011, 169);
			_upPoints.add(p);
			p = new Point(970, 630);
			_upPoints.add(p);
			p = new Point(1095, 504);
			_upPoints.add(p);

			p = new Point(42 + 1140, 293);
			_upPoints.add(p);
			p = new Point(166 + 1140, 168);
			_upPoints.add(p);
			p = new Point(126 + 1140, 418);
			_upPoints.add(p);
			p = new Point(166 + 1140, 378);
			_upPoints.add(p);
			p = new Point(251 + 1140, 503);
			_upPoints.add(p);
			p = new Point(291 + 1140, 462);
			_upPoints.add(p);
			p = new Point(248 + 1140, 171);
			_upPoints.add(p);
			p = new Point(332 + 1140, 84);
			_upPoints.add(p);
			p = new Point(498 + 1140, 633);
			_upPoints.add(p);
			p = new Point(837 + 1140, 294);
			_upPoints.add(p);
			p = new Point(921 + 1140, 295);
			_upPoints.add(p);
			p = new Point(1088 + 1140, 126);
			_upPoints.add(p);
			// add down points
			p = new Point(81, 127);
			_downPoints.add(p);
			p = new Point(252, 298);
			_downPoints.add(p);
			p = new Point(333, 295);
			_downPoints.add(p);
			p = new Point(671, 636);
			_downPoints.add(p);
			p = new Point(799, 214);
			_downPoints.add(p);
			p = new Point(711, 126);
			_downPoints.add(p);
			p = new Point(710, 336);
			_downPoints.add(p);
			p = new Point(792, 419);
			_downPoints.add(p);
			p = new Point(963, 461);
			_downPoints.add(p);
			p = new Point(1003, 503);
			_downPoints.add(p);
			p = new Point(879, 210);
			_downPoints.add(p);
			p = new Point(918, 251);
			_downPoints.add(p);

			p = new Point(111 + 1020, 169);
			_downPoints.add(p);
			p = new Point(150 + 1020, 209);
			_downPoints.add(p);
			p = new Point(111 + 1020, 337);
			_downPoints.add(p);
			p = new Point(192 + 1020, 419);
			_downPoints.add(p);
			p = new Point(245 + 1140, 379);
			_downPoints.add(p);
			p = new Point(282 + 1140, 419);
			_downPoints.add(p);
			p = new Point(75 + 1140, 504);
			_downPoints.add(p);
			p = new Point(206 + 1140, 635);
			_downPoints.add(p);
			p = new Point(496 + 1140, 463);
			_downPoints.add(p);
			p = new Point(534 + 1140, 504);
			_downPoints.add(p);
			p = new Point(454 + 1140, 84);
			_downPoints.add(p);
			p = new Point(540 + 1140, 173);
			_downPoints.add(p);
			p = new Point(622 + 1140, 169);
			_downPoints.add(p);
			p = new Point(744 + 1140, 293);
			_downPoints.add(p);
			// add stright points
			p = new Point(1, 126);
			_strightPoints.add(p);
			p = new Point(86, 126);
			_strightPoints.add(p);
			p = new Point(250, 294);
			_strightPoints.add(p);
			p = new Point(338, 294);
			_strightPoints.add(p);
			p = new Point(500, 127);
			_strightPoints.add(p);
			p = new Point(716, 127);
			_strightPoints.add(p);
			p = new Point(626, 337);
			_strightPoints.add(p);
			p = new Point(717, 337);
			_strightPoints.add(p);
			p = new Point(672, 630);
			_strightPoints.add(p);
			p = new Point(971, 630);
			_strightPoints.add(p);
			p = new Point(752, 462);
			_strightPoints.add(p);
			p = new Point(968, 462);
			_strightPoints.add(p);
			p = new Point(1005, 168);
			_strightPoints.add(p);
			p = new Point(1136, 168);
			_strightPoints.add(p);
			p = new Point(1046, 337);
			_strightPoints.add(p);
			p = new Point(1137, 337);
			_strightPoints.add(p);
			p = new Point(797, 210);
			_strightPoints.add(p);
			p = new Point(884, 210);
			_strightPoints.add(p);

			p = new Point(157 + 1140, 168);
			_strightPoints.add(p);
			p = new Point(249 + 1140, 168);
			_strightPoints.add(p);
			p = new Point(315 + 1140, 84);
			_strightPoints.add(p);
			p = new Point(459 + 1140, 84);
			_strightPoints.add(p);
			p = new Point(538 + 1140, 169);
			_strightPoints.add(p);
			p = new Point(628 + 1140, 169);
			_strightPoints.add(p);
			p = new Point(158 + 1140, 378);
			_strightPoints.add(p);
			p = new Point(249 + 1140, 378);
			_strightPoints.add(p);
			p = new Point(285 + 1140, 463);
			_strightPoints.add(p);
			p = new Point(500 + 1140, 463);
			_strightPoints.add(p);
			p = new Point(202 + 1140, 630);
			_strightPoints.add(p);
			p = new Point(499 + 1140, 630);
			_strightPoints.add(p);
			p = new Point(831 + 1140, 293);
			_strightPoints.add(p);
			p = new Point(922 + 1140, 293);
			_strightPoints.add(p);
			p = new Point(1083 + 1140, 127);
			_strightPoints.add(p);
			p = new Point(1142 + 1140, 127);
			_strightPoints.add(p);
			p = new Point(200 + 880, 505);
			_strightPoints.add(p);
			p = new Point(339 + 880, 505);
			_strightPoints.add(p);
			// add block points
			p = new Point(381, 250);
			_blockPoints.add(p);
			p = new Point(920, 250);
			_blockPoints.add(p);
			p = new Point(548, 418);
			_blockPoints.add(p);
			p = new Point(794, 418);
			_blockPoints.add(p);
			p = new Point(716, 502);
			_blockPoints.add(p);
			p = new Point(1004, 502);
			_blockPoints.add(p);

			p = new Point(40 + 1140, 293);
			_blockPoints.add(p);
			p = new Point(745 + 1140, 293);
			_blockPoints.add(p);
			p = new Point(123 + 1140, 418);
			_blockPoints.add(p);
			p = new Point(283 + 1140, 418);
			_blockPoints.add(p);
			p = new Point(249 + 1140, 502);
			_blockPoints.add(p);
			p = new Point(536 + 1140, 502);
			_blockPoints.add(p);
			p = new Point(128 + 840, 208);
			_blockPoints.add(p);
			p = new Point(332 + 840, 208);
			_blockPoints.add(p);
			p = new Point(130 + 840, 418);
			_blockPoints.add(p);
			p = new Point(374 + 840, 418);
			_blockPoints.add(p);
			// start points
			p = new Point(584, 76);
			_startPoints.add(p);
			p = new Point(659, 284);
			_startPoints.add(p);
			p = new Point(251, 241);
			_startPoints.add(p);
			p = new Point(782, 577);
			_startPoints.add(p);
			p = new Point(1150, 453);
			_startPoints.add(p);
			p = new Point(415 + 1140, 577);
			_startPoints.add(p);
			p = new Point(180 + 1140, 326);
			_startPoints.add(p);
			p = new Point(203 + 1140, 115);
			_startPoints.add(p);
			p = new Point(563 + 1140, 116);
			_startPoints.add(p);
			p = new Point(858 + 1140, 240);
			_startPoints.add(p);
		} else if (_mapNumber == 3) {
			_mapName = "cold";
			// add up points
			p = new Point(130, 461);
			_upPoints.add(p);
			p = new Point(254, 337);
			_upPoints.add(p);
			p = new Point(381, 254);
			_upPoints.add(p);
			p = new Point(423, 211);
			_upPoints.add(p);
			p = new Point(593, 335);
			_upPoints.add(p);
			p = new Point(632, 295);
			_upPoints.add(p);
			p = new Point(713, 425);
			_upPoints.add(p);
			p = new Point(802, 336);
			_upPoints.add(p);
			p = new Point(545, 634);
			_upPoints.add(p);
			p = new Point(632, 546);
			_upPoints.add(p);
			p = new Point(973, 292);
			_upPoints.add(p);
			p = new Point(1012, 252);
			_upPoints.add(p);

			p = new Point(210 + 1140, 420);
			_upPoints.add(p);
			p = new Point(292 + 1140, 336);
			_upPoints.add(p);
			p = new Point(462 + 1140, 250);
			_upPoints.add(p);
			p = new Point(541 + 1140, 169);
			_upPoints.add(p);
			p = new Point(628 + 1140, 335);
			_upPoints.add(p);
			p = new Point(670 + 1140, 294);
			_upPoints.add(p);
			p = new Point(499 + 1140, 633);
			_upPoints.add(p);
			p = new Point(627 + 1140, 504);
			_upPoints.add(p);
			p = new Point(961 + 1140, 551);
			_upPoints.add(p);
			p = new Point(1088 + 1140, 421);
			_upPoints.add(p);
			p = new Point(924 + 1140, 378);
			_upPoints.add(p);
			p = new Point(962 + 1140, 336);
			_upPoints.add(p);
			p = new Point(372 + 1140, 339);
			_upPoints.add(p);
			p = new Point(417 + 1140, 294);
			_upPoints.add(p);
			p = new Point(51 + 1000, 591);
			_upPoints.add(p);
			p = new Point(179 + 1000, 462);
			_upPoints.add(p);
			// add down points
			p = new Point(81, 504);
			_downPoints.add(p);
			p = new Point(210, 636);
			_downPoints.add(p);
			p = new Point(333, 336);
			_downPoints.add(p);
			p = new Point(420, 425);
			_downPoints.add(p);
			p = new Point(544, 210);
			_downPoints.add(p);
			p = new Point(582, 251);
			_downPoints.add(p);
			p = new Point(711, 298);
			_downPoints.add(p);
			p = new Point(750, 336);
			_downPoints.add(p);
			p = new Point(752, 546);
			_downPoints.add(p);
			p = new Point(800, 592);
			_downPoints.add(p);
			p = new Point(879, 335);
			_downPoints.add(p);
			p = new Point(1002, 461);
			_downPoints.add(p);

			p = new Point(202 + 1140, 252);
			_downPoints.add(p);
			p = new Point(239 + 1140, 293);
			_downPoints.add(p);
			p = new Point(159 + 1140, 462);
			_downPoints.add(p);
			p = new Point(331 + 1140, 635);
			_downPoints.add(p);
			p = new Point(495 + 1140, 294);
			_downPoints.add(p);
			p = new Point(619 + 1140, 420);
			_downPoints.add(p);
			p = new Point(621 + 1140, 168);
			_downPoints.add(p);
			p = new Point(701 + 1140, 252);
			_downPoints.add(p);
			p = new Point(830 + 1140, 294);
			_downPoints.add(p);
			p = new Point(871 + 1140, 336);
			_downPoints.add(p);
			p = new Point(747 + 1140, 504);
			_downPoints.add(p);
			p = new Point(794 + 1140, 550);
			_downPoints.add(p);
			p = new Point(1042 + 1140, 336);
			_downPoints.add(p);
			p = new Point(1080 + 1140, 378);
			_downPoints.add(p);
			// add stright points
			p = new Point(1, 505);
			_strightPoints.add(p);
			p = new Point(86, 505);
			_strightPoints.add(p);
			p = new Point(249, 337);
			_strightPoints.add(p);
			p = new Point(337, 337);
			_strightPoints.add(p);
			p = new Point(418, 211);
			_strightPoints.add(p);
			p = new Point(548, 211);
			_strightPoints.add(p);
			p = new Point(627, 295);
			_strightPoints.add(p);
			p = new Point(716, 295);
			_strightPoints.add(p);
			p = new Point(419, 420);
			_strightPoints.add(p);
			p = new Point(713, 420);
			_strightPoints.add(p);
			p = new Point(795, 337);
			_strightPoints.add(p);
			p = new Point(885, 337);
			_strightPoints.add(p);
			p = new Point(628, 547);
			_strightPoints.add(p);
			p = new Point(759, 547);
			_strightPoints.add(p);
			p = new Point(210, 631);
			_strightPoints.add(p);
			p = new Point(547, 631);
			_strightPoints.add(p);
			p = new Point(797, 588);
			_strightPoints.add(p);
			p = new Point(1050, 588);
			_strightPoints.add(p);

			p = new Point(34 + 1140, 463);
			_strightPoints.add(p);
			p = new Point(163 + 1140, 463);
			_strightPoints.add(p);
			p = new Point(329 + 1140, 632);
			_strightPoints.add(p);
			p = new Point(498 + 1140, 632);
			_strightPoints.add(p);
			p = new Point(285 + 1140, 336);
			_strightPoints.add(p);
			p = new Point(372 + 1140, 336);
			_strightPoints.add(p);
			p = new Point(410 + 1140, 294);
			_strightPoints.add(p);
			p = new Point(499 + 1140, 294);
			_strightPoints.add(p);
			p = new Point(538 + 1140, 168);
			_strightPoints.add(p);
			p = new Point(627 + 1140, 168);
			_strightPoints.add(p);
			p = new Point(622 + 1140, 504);
			_strightPoints.add(p);
			p = new Point(752 + 1140, 504);
			_strightPoints.add(p);
			p = new Point(665 + 1140, 295);
			_strightPoints.add(p);
			p = new Point(837 + 1140, 295);
			_strightPoints.add(p);
			p = new Point(791 + 1140, 548);
			_strightPoints.add(p);
			p = new Point(960 + 1140, 548);
			_strightPoints.add(p);
			p = new Point(958 + 1140, 336);
			_strightPoints.add(p);
			p = new Point(1046 + 1140, 336);
			_strightPoints.add(p);
			p = new Point(1083 + 1140, 420);
			_strightPoints.add(p);
			p = new Point(1143 + 1140, 420);
			_strightPoints.add(p);
			p = new Point(46 + 960, 252);
			_strightPoints.add(p);
			p = new Point(384 + 960, 253);
			_strightPoints.add(p);
			// add block points
			p = new Point(379, 251);
			_blockPoints.add(p);
			p = new Point(583, 251);
			_blockPoints.add(p);
			p = new Point(593, 335);
			_blockPoints.add(p);
			p = new Point(748, 335);
			_blockPoints.add(p);
			p = new Point(131, 461);
			_blockPoints.add(p);
			p = new Point(1002, 461);
			_blockPoints.add(p);

			p = new Point(207 + 1140, 419);
			_blockPoints.add(p);
			p = new Point(618 + 1140, 419);
			_blockPoints.add(p);
			p = new Point(461 + 1140, 251);
			_blockPoints.add(p);
			p = new Point(699 + 1140, 251);
			_blockPoints.add(p);
			p = new Point(628 + 1140, 334);
			_blockPoints.add(p);
			p = new Point(869 + 1140, 334);
			_blockPoints.add(p);
			p = new Point(921 + 1140, 376);
			_blockPoints.add(p);
			p = new Point(1082 + 1140, 376);
			_blockPoints.add(p);
			p = new Point(149 + 820, 294);
			_blockPoints.add(p);
			p = new Point(561 + 820, 293);
			_blockPoints.add(p);
			// start points
			p = new Point(316, 578);
			_startPoints.add(p);
			p = new Point(493, 367);
			_startPoints.add(p);
			p = new Point(421, 158);
			_startPoints.add(p);
			p = new Point(882, 535);
			_startPoints.add(p);
			p = new Point(22, 452);
			_startPoints.add(p);
			p = new Point(307 + 1140, 283);
			_startPoints.add(p);
			p = new Point(734 + 1140, 242);
			_startPoints.add(p);
			p = new Point(855 + 1140, 496);
			_startPoints.add(p);
			p = new Point(387 + 1140, 580);
			_startPoints.add(p);
			p = new Point(28 + 1140, 199);
			_startPoints.add(p);
		}
		_BG = new Music("\\sounds\\" + _mapName + ".wav");
		_BG.Loop();
		ImageIcon imgI = new ImageIcon("src/Images/" + _mapName + ".jpg");
		_gameImg = imgI.getImage();
		_block = new Img("Images\\blocks\\" + _mapName + "-1.png", 0, 0, _blockSize, _blockSize);
		_map = new Map(_numOfRows, _numOfCols, "XmlFiles\\" + _mapName + ".xml");
		for (int i = 0; i < _numOfWorms * 2; i++) {
			_worm[i] = null;
		}
		_isPaused = false;
		_isWin = false;
		_xOffSet = 0;
		_limit = 1150;
		stopRunning();
		initWorms();
		GraphFacilities.CreateGraph();
	}

	/**
	 * adds new listener to listeners list
	 * 
	 * @param toAdd
	 */
	public void addListener(ChangeScreensInterface toAdd) {
		_listeners.add(toAdd);
	}

	/**
	 * painting the panel
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(_gameImg, 0, 0, GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY, this);
		_pauseB.drawImg(g);
		for (int i = 0; i < _numOfRows; i++) {
			for (int j = 0; j < _numOfCols; j++) {
				if (_map.get_map()[i][j] == 1) {
					_block.setPath("Images\\blocks\\" + _mapName + "-1.png");
					_block.setImgCords((j * _blockSize) + _xOffSet, (i) * _blockSize);
					_block.drawImg(g);
				}
				if (_map.get_map()[i][j] == 2) {
					_block.setPath("Images\\blocks\\" + _mapName + "-2.png");
					_block.setImgCords((j * _blockSize) + _xOffSet, (i) * _blockSize);
					_block.drawImg(g);
				}
				if (_map.get_map()[i][j] == 3) {
					_block.setPath("Images\\blocks\\" + _mapName + "-3.png");
					_block.setImgCords((j * _blockSize) + _xOffSet, (i) * _blockSize);
					_block.drawImg(g);
				}
				if (_map.get_map()[i][j] == 4) {
					_block.setPath("Images\\blocks\\" + _mapName + "-4.png");
					_block.setImgCords((j * _blockSize) + _xOffSet, (i) * _blockSize);
					_block.drawImg(g);
				}
				if (_map.get_map()[i][j] == 5) {
					_block.setPath("Images\\blocks\\" + _mapName + "-5.png");
					_block.setImgCords((j * _blockSize) + _xOffSet, (i) * _blockSize);
					_block.drawImg(g);
				}
				if (_map.get_map()[i][j] == 6) {
					_block.setPath("Images\\blocks\\" + _mapName + "-6.png");
					_block.setImgCords((j * _blockSize) + _xOffSet, (i) * _blockSize);
					_block.drawImg(g);
				}
			}
		}
		if (_worm[_wormTurn] != null) {
			if (_worm[_wormTurn]._isJumping) {
				_imgIcon = new ImageIcon(this.getClass().getClassLoader().getResource("Images\\sprites\\jump.png"));
				_wormImg = _imgIcon.getImage();
				_worm[_wormTurn].setImage(_wormImg, 1, 2);
			} else if (_worm[_wormTurn]._isAiming) {
				_imgIcon = new ImageIcon(this.getClass().getClassLoader()
						.getResource("Images\\sprites\\shoot_" + _worm[_wormTurn].getTeamColor() + ".png"));
				_wormImg = _imgIcon.getImage();
				_worm[_wormTurn].setImage(_wormImg, 6, 5);
			} else {
				_imgIcon = new ImageIcon(this.getClass().getClassLoader().getResource("Images\\sprites\\walk.png"));
				_wormImg = _imgIcon.getImage();
				_worm[_wormTurn].setImage(_wormImg, 6, 5);
			}
			for (int i = 0; i < _numOfWorms * 2; i++) {
				if (_worm[i] != null) {
					_worm[i].drawImg(g);
				}
			}
			// g.drawLine(_worm[_wormTurn].getX() + _worm[_wormTurn].getWidth()
			// -
			// 25, _worm[_wormTurn].getY() + 10,
			// _worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
			// _worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 7);
			// g.drawLine(_worm[_wormTurn].getX() + 27, _worm[_wormTurn].getY()
			// +
			// 10, _worm[_wormTurn].getX() + 27,
			// _worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 7);
			// g.drawLine(_worm[_wormTurn].getX() + 25, _worm[_wormTurn].getY()
			// +
			// _worm[_wormTurn].getHeight() - 11,
			// _worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
			// _worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 11);
			// for (int i = 0; i < _strightPoints.size(); i += 2) {
			// g.drawLine((int) _strightPoints.get(i).getX() + _xOffSet, (int)
			// _strightPoints.get(i).getY(),
			// (int) _strightPoints.get(i + 1).getX() + _xOffSet, (int)
			// _strightPoints.get(i + 1).getY());
			// }
			// for (int i = 0; i < _upPoints.size(); i += 2) {
			// g.drawLine((int) _upPoints.get(i).getX() + _xOffSet, (int)
			// _upPoints.get(i).getY(),
			// (int) _upPoints.get(i + 1).getX() + _xOffSet, (int)
			// _upPoints.get(i +
			// 1).getY());
			// }
			// for (int i = 0; i < _downPoints.size(); i += 2) {
			// g.drawLine((int) _downPoints.get(i).getX() + _xOffSet, (int)
			// _downPoints.get(i).getY(),
			// (int) _downPoints.get(i + 1).getX() + _xOffSet, (int)
			// _downPoints.get(i + 1).getY());
			// }
			// for (int i = 0; i < _blockPoints.size(); i += 2) {
			// g.drawLine((int) _blockPoints.get(i).getX() + _xOffSet, (int)
			// _blockPoints.get(i).getY(),
			// (int) _blockPoints.get(i + 1).getX() + _xOffSet, (int)
			// _blockPoints.get(i + 1).getY());
			// }
			// g.drawLine((int) _worm[_wormTurn].getXM() + 10,
			// (int) _worm[_wormTurn].getYM() + _worm[_wormTurn].getHeightM() -
			// 11,
			// (int) _worm[_wormTurn].getXM() + _worm[_wormTurn].getWidthM() -
			// 25,
			// (int) _worm[_wormTurn].getYM() + _worm[_wormTurn].getHeightM() -
			// 5);
			// g.drawRect((int) _worm[_wormTurn].getXM()+5, (int)
			// _worm[_wormTurn].getYM()+5, _worm[_wormTurn].getWidthM()-12,
			// _worm[_wormTurn].getHeightM()-12);
			// g.drawRect(_worm[_wormTurn].getX() + 10, _worm[_wormTurn].getY()
			// +
			// 15, _worm[_wormTurn].getWidth() - 30,
			// _worm[_wormTurn].getHeight() - 30);
			// g.drawLine(_worm[_wormTurn].getX() + 20, _worm[_wormTurn].getY()
			// + 40, _worm[_numOfWorms].getX() + 20,
			// _worm[_numOfWorms].getY() + 40);
			if (_worm[_wormTurn]._exploded) {
				try {
					g.drawImage(_worm[_wormTurn]._explotion, (int) _worm[_wormTurn].getXG() - 20,
							(int) _worm[_wormTurn].getYG() - 20, 64, 64, this);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			_timer.drawImg(g);
			_turnName = _worm[_wormTurn]._name;
			_turnName.setImgCords(1035, 38);
			_turnName.drawImg(g);
		}
		if (_isPaused) {
			_shadow.drawImg(g);
			_pausePanel.drawImg(g);
		}
		if (_isWin)
			_winPanel.drawImg(g);
		requestFocusInWindow();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	/**
	 * calculate y on line
	 * 
	 * @param x: x cord
	 * @return
	 */
	public double retYOnLine(double x) {
		double y = (_m * (x - _p1.getX()) + _p1.getY());
		return y;
	}

	/**
	 * managing the computer aiming
	 */
	private void compAim() {
		if (_worm[_wormTurn].getX() > _worm[_wormToShoot].getX()) {
			_worm[_wormTurn].setCurrentRow(isLeftLinesIntersects());
		} else {
			_worm[_wormTurn].setCurrentRow(isRightLinesIntersects());
		}
		_worm[_wormTurn]._isAiming = true;
		_worm[_wormTurn].setCurrentRow(_worm[_wormTurn].getCurrentRow());
		_worm[_wormTurn].setCurrentFrame(2);
		_worm[_wormTurn].initIntention((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1,
				orderOnLine((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1));
		repaint();
		_intention = new Rectangle(_worm[_wormTurn]._intention.getImgXCords(),
				_worm[_wormTurn]._intention.getImgYCords(), 10, 10);
		int up = 0, down = 0;
		while (!_shootRange2.intersects(_intention)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (_intention.getY() > retYOnLine(_intention.getX())) {
				if (_worm[_wormTurn]._isAiming) {
					_worm[_wormTurn].aim((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? -1 : 1,
							(_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1, 1);
					up++;
				}
			} else {
				if (_worm[_wormTurn]._isAiming) {
					_worm[_wormTurn].aim((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1,
							(_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1, -1);
					down++;
				}
			}
			_intention.setBounds(_worm[_wormTurn]._intention.getImgXCords(), _worm[_wormTurn]._intention.getImgYCords(),
					10, 10);
			repaint();
			if (up > 0 && down > 0)
				break;
		}
	}

	/**
	 * managing the computer shooting
	 */
	private void compShoot() {
		_worm[_wormTurn]._isAiming = false;
		_worm[_wormTurn]._isShooting = true;
		_worm[_wormTurn].shoot((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1);
		_worm[_wormTurn].setCurrentFrame(4);
		_launch = new Music("\\sounds\\launch.wav");
		_turnTimer.stop();
		_seconds = 1;
		repaint();
	}

	/**
	 * managing the computer right walking
	 */
	private void compGoRight() {
		if (!_worm[_wormTurn]._isJumping) {
			if (_worm[_wormTurn]._isAiming)
				_worm[_wormTurn]._isAiming = false;
			_worm[_wormTurn].setCurrentRow(isRightLinesIntersects());
			_worm[_wormTurn].walk();
			moveFrame(1, _worm[_wormTurn].getX());
			orderOnLine((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1);
			repaint();
		}
	}

	/**
	 * managing the computer left walking
	 */
	private void compGoLeft() {
		if (!_worm[_wormTurn]._isJumping) {
			if (_worm[_wormTurn]._isAiming)
				_worm[_wormTurn]._isAiming = false;
			_worm[_wormTurn].setCurrentRow(isLeftLinesIntersects());
			_worm[_wormTurn].walk();
			moveFrame(-1, _worm[_wormTurn].getX());
			orderOnLine((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1);
			repaint();
		}
	}

	/**
	 * while the thread is working only the computer player will be able to move
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean isRangeFound = false;
		int right = 0, left = 0;
		while (_isRunning) {
			try {
				if (_teamTurn == -1 && !_isPaused && !_isWin) {
					if (!isRangeFound && !_worm[_wormTurn]._isJumping) {
						if (findWormToShoot() == true) {
							isRangeFound = true;
							_worm[_wormTurn]._walkTimer.stop();
							repaint();
							compAim();
							compShoot();
							right = 0;
							left = 0;
							repaint();
						} else {
							if (!(right > 0 && left > 0)) {
								if (_worm[getNearestWorm()].getX() > _worm[_wormTurn].getX()) {
									compGoRight();
									right++;
								} else {
									compGoLeft();
									left++;
								}
							} else {
								if (_direct == 1)
									compGoRight();
								else
									compGoLeft();
							}
						}
					}
				} else {
					isRangeFound = false;
				}
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * starting the thread
	 */
	public void startRunning() {
		if (!_isRunning) {
			_isRunning = true;
			_wormsThread = new Thread(this);
			_wormsThread.start();
		}
	}

	/**
	 * stopping the thread
	 */
	public void stopRunning() {
		_isRunning = false;
		_wormsThread = null;
	}

	/**
	 * check intersects with right map lines
	 * 
	 * @return sprite current row
	 */
	public int isRightLinesIntersects() {
		Line2D charLine = new Line2D.Double(_worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + 13, _worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 5);
		Line2D l2;
		for (int i = 0; i < _upPoints.size(); i += 2) {
			l2 = new Line2D.Double(_upPoints.get(i).getX() + _xOffSet, _upPoints.get(i).getY(),
					_upPoints.get(i + 1).getX() + _xOffSet, _upPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				return 4;
			}
		}
		charLine = new Line2D.Double(_worm[_wormTurn].getX() + 27, _worm[_wormTurn].getY() + 13,
				_worm[_wormTurn].getX() + 27, _worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 5);
		for (int i = 0; i < _downPoints.size(); i += 2) {
			l2 = new Line2D.Double(_downPoints.get(i).getX() + _xOffSet, _downPoints.get(i).getY(),
					_downPoints.get(i + 1).getX() + _xOffSet, _downPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				return 2;
			}
		}
		for (int i = 0; i < _strightPoints.size(); i += 2) {
			l2 = new Line2D.Double(_strightPoints.get(i).getX() + _xOffSet, _strightPoints.get(i).getY(),
					_strightPoints.get(i + 1).getX() + _xOffSet, _strightPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				return 0;
			}
		}
		if (!_worm[_wormTurn]._isJumping) {
			_worm[_wormTurn]._isJumping = true;
			_worm[_wormTurn]._isAiming = false;
			_worm[_wormTurn].setCurrentFrame(_worm[_wormTurn].getCurrentRow() % 2);
			_worm[_wormTurn].setCurrentRow(0);
			_worm[_wormTurn].jump(1, 6);
			orderOnLine(1);
			repaint();
		}
		return 0;
	}

	/**
	 * check intersects with left map lines
	 * 
	 * @return sprite current row
	 */
	public int isLeftLinesIntersects() {
		Line2D charLine = new Line2D.Double(_worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + 13, _worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 5);
		Line2D l2;
		for (int i = 0; i < _upPoints.size(); i += 2) {
			l2 = new Line2D.Double(_upPoints.get(i).getX() + _xOffSet, _upPoints.get(i).getY(),
					_upPoints.get(i + 1).getX() + _xOffSet, _upPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				return 3;
			}
		}
		charLine = new Line2D.Double(_worm[_wormTurn].getX() + 27, _worm[_wormTurn].getY() + 13,
				_worm[_wormTurn].getX() + 27, _worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 5);
		for (int i = 0; i < _downPoints.size(); i += 2) {
			l2 = new Line2D.Double(_downPoints.get(i).getX() + _xOffSet, _downPoints.get(i).getY(),
					_downPoints.get(i + 1).getX() + _xOffSet, _downPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				return 5;
			}
		}
		for (int i = 0; i < _strightPoints.size(); i += 2) {
			l2 = new Line2D.Double(_strightPoints.get(i).getX() + _xOffSet, _strightPoints.get(i).getY(),
					_strightPoints.get(i + 1).getX() + _xOffSet, _strightPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				return 1;
			}
		}
		if (!_worm[_wormTurn]._isJumping) {
			_worm[_wormTurn]._isJumping = true;
			_worm[_wormTurn]._isAiming = false;
			_worm[_wormTurn].setCurrentFrame(_worm[_wormTurn].getCurrentRow() % 2);
			_worm[_wormTurn].setCurrentRow(0);
			_worm[_wormTurn].jump(-1, 6);
			orderOnLine(-1);
			repaint();
		}
		return 0;
	}

	/**
	 * check intersects with all map lines
	 * 
	 * @return true if intersects, else false
	 */
	public boolean isOnGround() {
		Line2D charLine = new Line2D.Double(_worm[_wormTurn].getX() + 20,
				_worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 11,
				_worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 20,
				_worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 11);
		Line2D l2;
		for (int i = 0; i < _upPoints.size(); i += 2) {
			l2 = new Line2D.Double(_upPoints.get(i).getX() + _xOffSet, _upPoints.get(i).getY(),
					_upPoints.get(i + 1).getX() + _xOffSet, _upPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				_worm[_wormTurn]._isJumping = false;
				_worm[_wormTurn].setCurrentFrame(4);
				return true;
			}
		}
		for (int i = 0; i < _downPoints.size(); i += 2) {
			l2 = new Line2D.Double(_downPoints.get(i).getX() + _xOffSet, _downPoints.get(i).getY(),
					_downPoints.get(i + 1).getX() + _xOffSet, _downPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				_worm[_wormTurn]._isJumping = false;
				_worm[_wormTurn].setCurrentFrame(4);
				return true;
			}
		}
		charLine = new Line2D.Double(_worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + 13, _worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 7);
		for (int i = 0; i < _strightPoints.size(); i += 2) {
			l2 = new Line2D.Double(_strightPoints.get(i).getX() + _xOffSet, _strightPoints.get(i).getY(),
					_strightPoints.get(i + 1).getX() + _xOffSet, _strightPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				_currentLine = l2;
				_worm[_wormTurn]._isJumping = false;
				_worm[_wormTurn].setCurrentFrame(4);
				return true;
			}
		}
		return false;
	}

	/**
	 * check intersects with block map lines
	 * 
	 * @return true if intersects, else false
	 */
	public boolean isBlocked() {
		Line2D charLine = new Line2D.Double(_worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + 13, _worm[_wormTurn].getX() + _worm[_wormTurn].getWidth() - 25,
				_worm[_wormTurn].getY() + _worm[_wormTurn].getHeight() - 5);
		Line2D l2;
		for (int i = 0; i < _blockPoints.size(); i += 2) {
			l2 = new Line2D.Double(_blockPoints.get(i).getX() + _xOffSet, _blockPoints.get(i).getY(),
					_blockPoints.get(i + 1).getX() + _xOffSet, _blockPoints.get(i + 1).getY());
			if (charLine.intersectsLine(l2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check intersects of grenade with all map lines
	 * 
	 * @return true if intersects, else false
	 */
	public boolean isGrenadeOnGround() {
		Rectangle grenadeRec = new Rectangle((int) _worm[_wormTurn].getXG() + 5, (int) _worm[_wormTurn].getYG() + 5,
				_worm[_wormTurn].getWidthG() - 12, _worm[_wormTurn].getHeightG() - 12);
		Line2D l2;
		for (int i = 0; i < _upPoints.size(); i += 2) {
			l2 = new Line2D.Double(_upPoints.get(i).getX() + _xOffSet, _upPoints.get(i).getY(),
					_upPoints.get(i + 1).getX() + _xOffSet, _upPoints.get(i + 1).getY());
			if (grenadeRec.intersectsLine(l2)) {
				checkHits();
				_turnTimer.start();
				_explotion = new Music("\\sounds\\explotion.wav");
				return true;
			}
		}
		for (int i = 0; i < _downPoints.size(); i += 2) {
			l2 = new Line2D.Double(_downPoints.get(i).getX() + _xOffSet, _downPoints.get(i).getY(),
					_downPoints.get(i + 1).getX() + _xOffSet, _downPoints.get(i + 1).getY());
			if (grenadeRec.intersectsLine(l2)) {
				checkHits();
				_turnTimer.start();
				_explotion = new Music("\\sounds\\explotion.wav");
				return true;
			}
		}
		for (int i = 0; i < _strightPoints.size(); i += 2) {
			l2 = new Line2D.Double(_strightPoints.get(i).getX() + _xOffSet, _strightPoints.get(i).getY(),
					_strightPoints.get(i + 1).getX() + _xOffSet, _strightPoints.get(i + 1).getY());
			if (grenadeRec.intersectsLine(l2)) {
				checkHits();
				_turnTimer.start();
				_explotion = new Music("\\sounds\\explotion.wav");
				return true;
			}
		}
		for (int i = 0; i < _blockPoints.size(); i += 2) {
			l2 = new Line2D.Double(_blockPoints.get(i).getX() + _xOffSet, _blockPoints.get(i).getY(),
					_blockPoints.get(i + 1).getX() + _xOffSet, _blockPoints.get(i + 1).getY());
			if (grenadeRec.intersectsLine(l2)) {
				checkHits();
				_turnTimer.start();
				_explotion = new Music("\\sounds\\explotion.wav");
				return true;
			}
		}
		return false;
	}

	/**
	 * check intersects of grenade with some worm
	 * 
	 * @return true if intersects, else false
	 */
	public boolean isGrenadeHit() {
		Rectangle grenadeRec = new Rectangle((int) _worm[_wormTurn].getXG() + 5, (int) _worm[_wormTurn].getYG() + 5,
				_worm[_wormTurn].getWidthG() - 12, _worm[_wormTurn].getHeightG() - 12);
		Rectangle wormRec;
		for (int i = 0; i < _numOfWorms * 2; i++) {
			if (_worm[i] != null) {
				wormRec = new Rectangle(_worm[i].getX() + 10, _worm[i].getY() + 15, _worm[i].getWidth() - 30,
						_worm[i].getHeight() - 30);
				if (grenadeRec.intersects(wormRec)) {
					checkHits();
					_turnTimer.start();
					_explotion = new Music("\\sounds\\explotion.wav");
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * check which worms are damaged
	 */
	public void checkHits() {
		for (int i = 0; i < _numOfWorms * 2; i++) {
			if (_worm[i] != null) {
				_worm[i].hit((int) _worm[_wormTurn].getXG() - 20, (int) _worm[_wormTurn].getYG() - 20);
			}
		}
		checkAliveWorms();
		checkWin();
	}

	/**
	 * check which worms are dead
	 */
	public void checkAliveWorms() {
		for (int i = 0; i < _numOfWorms * 2; i++) {
			if (_worm[i] != null) {
				if (_worm[i].getHp() == 0) {
					_worm[i] = null;
				}
			}
		}
	}

	/**
	 * check if there is a winner team
	 */
	public void checkWin() {
		int reds = 0, blues = 0;
		String winTeam = "";
		for (int i = 0; i < _numOfWorms * 2; i++) {
			if (_worm[i] != null) {
				if (_worm[i].getTeamColor() == "red")
					reds++;
				else
					blues++;
			}
		}
		if (blues == 0) {
			if (_teamNum == 1)
				winTeam = "usa";
			else if (_teamNum == 2)
				winTeam = "israel";
			else
				winTeam = "russia";
			_winPanel = new Img("Images//winners//" + winTeam + " won-red.png", 0, 0, 1150, 700);
			_isWin = true;
			_turnTimer.stop();
			stopRunning();
		} else if (reds == 0) {
			if (_enemyTeamNum == 1)
				winTeam = "usa";
			else if (_enemyTeamNum == 2)
				winTeam = "israel";
			else
				winTeam = "russia";
			_winPanel = new Img("Images//winners//" + winTeam + " won-blue.png", 0, 0, 1150, 700);
			_isWin = true;
			_turnTimer.stop();
			stopRunning();
		}
		if (winTeam != "") {
			_BG.stop();
			_winning = new Music("\\sounds\\" + winTeam + ".wav");
		}
	}

	/**
	 * gets the nearest worm to the current worm turn
	 * 
	 * @return worm number
	 */
	public int getNearestWorm() {
		int nearest = 0;
		double d, min = 5000;
		for (int i = 0; i < _numOfWorms; i++) {
			if (_worm[i] != null) {
				d = Math.sqrt((Math.pow((_worm[i].getX() - _worm[_wormTurn].getX()), 2)
						+ (Math.pow((_worm[i].getY() - _worm[_wormTurn].getY()), 2))));
				if (d < min) {
					min = d;
					nearest = i;
				}
			}
		}
		return nearest;
	}

	/**
	 * finds a worm with possibility to shoot on
	 * 
	 * @return true if there is a worm, else false
	 */
	public boolean findWormToShoot() {
		for (int i = 0; i < _numOfWorms; i++) {
			if (_worm[i] != null) {
				if (tryToShoot(i) == true) {
					_wormToShoot = i;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * check if there is a shoot range with a worm
	 * 
	 * @param wormIndx: worm that checked
	 * @return true if there is a shoot range, else false
	 */
	public boolean tryToShoot(int wormIndx) {
		_shootRange1 = new Line2D.Double(_worm[_wormTurn].getX() + 20, _worm[_wormTurn].getY() + 50,
				_worm[wormIndx].getX() + 20, _worm[wormIndx].getY() + 50);
		_shootRange2 = new Line2D.Double(_worm[_wormTurn].getX() + 20, _worm[_wormTurn].getY() + 25,
				_worm[wormIndx].getX() + 20, _worm[wormIndx].getY() + 25);
		_p1 = new Point(_worm[_wormTurn].getX() + 20, _worm[_wormTurn].getY() + 25);
		_p2 = new Point(_worm[wormIndx].getX() + 20, _worm[wormIndx].getY() + 25);
		_m = (_p1.getY() - _p2.getY()) / (_p1.getX() - _p2.getX());
		Line2D l2;
		for (int i = 0; i < _upPoints.size(); i += 2) {
			l2 = new Line2D.Double(_upPoints.get(i).getX() + _xOffSet, _upPoints.get(i).getY(),
					_upPoints.get(i + 1).getX() + _xOffSet, _upPoints.get(i + 1).getY());
			if (_shootRange1.intersectsLine(l2) || _shootRange2.intersectsLine(l2)) {
				return false;
			}
		}
		for (int i = 0; i < _downPoints.size(); i += 2) {
			l2 = new Line2D.Double(_downPoints.get(i).getX() + _xOffSet, _downPoints.get(i).getY(),
					_downPoints.get(i + 1).getX() + _xOffSet, _downPoints.get(i + 1).getY());
			if (_shootRange1.intersectsLine(l2) || _shootRange2.intersectsLine(l2)) {
				return false;
			}
		}
		for (int i = 0; i < _strightPoints.size(); i += 2) {
			l2 = new Line2D.Double(_strightPoints.get(i).getX() + _xOffSet, _strightPoints.get(i).getY(),
					_strightPoints.get(i + 1).getX() + _xOffSet, _strightPoints.get(i + 1).getY());
			if (_shootRange1.intersectsLine(l2) || _shootRange2.intersectsLine(l2)) {
				return false;
			}
		}
		for (int i = 0; i < _blockPoints.size(); i += 2) {
			l2 = new Line2D.Double(_blockPoints.get(i).getX() + _xOffSet, _blockPoints.get(i).getY(),
					_blockPoints.get(i + 1).getX() + _xOffSet, _blockPoints.get(i + 1).getY());
			if (_shootRange1.intersectsLine(l2) || _shootRange2.intersectsLine(l2)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * order worm on a stright line
	 * 
	 * @param direct: right=1 or left=-1
	 * @return incline of current line
	 */
	public double orderOnLine(int direct) {
		double incline = (_currentLine.getY2() - _currentLine.getY1()) / (_currentLine.getX2() - _currentLine.getX1());
		if (incline > -0.5 && incline <= 0 || incline >= 0 && incline < 0.5) {
			_worm[_wormTurn].setY((int) _currentLine.getY1() - _worm[_wormTurn].getHeight() + 15);
		}
		return incline;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * taking care of keys pressing
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		_pressedCode = e.getKeyCode();
		if (!_isPaused && !_worm[_wormTurn]._isShooting && _teamTurn == 1 && !_isWin) {
			if (_pressedCode == KeyEvent.VK_RIGHT) {
				if (!_worm[_wormTurn]._isJumping) {
					if (_worm[_wormTurn]._isAiming)
						_worm[_wormTurn]._isAiming = false;
					_worm[_wormTurn].setCurrentRow(isRightLinesIntersects());
					_worm[_wormTurn].walk();
					moveFrame(1, _worm[_wormTurn].getX());
					orderOnLine((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1);
					repaint();
				}
			}

			if (_pressedCode == KeyEvent.VK_LEFT) {
				if (!_worm[_wormTurn]._isJumping) {
					if (_worm[_wormTurn]._isAiming)
						_worm[_wormTurn]._isAiming = false;
					_worm[_wormTurn].setCurrentRow(isLeftLinesIntersects());
					_worm[_wormTurn].walk();
					moveFrame(-1, _worm[_wormTurn].getX());
					orderOnLine((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1);
					repaint();
				}
			}

			if (_pressedCode == KeyEvent.VK_UP) {
				if (_worm[_wormTurn]._isAiming) {
					_worm[_wormTurn].aim((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? -1 : 1,
							(_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1, 1);
					repaint();
				}
			}

			if (_pressedCode == KeyEvent.VK_DOWN) {
				if (_worm[_wormTurn]._isAiming) {
					_worm[_wormTurn].aim((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1,
							(_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1, -1);
					repaint();
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!_worm[_wormTurn]._isJumping) {
					_worm[_wormTurn]._isJumping = true;
					_worm[_wormTurn]._isAiming = false;
					_worm[_wormTurn].setCurrentFrame(_worm[_wormTurn].getCurrentRow() % 2);
					_worm[_wormTurn].setCurrentRow(0);
					_worm[_wormTurn].jump((_worm[_wormTurn].getCurrentFrame() == 0) ? 1 : -1, 4);
					_jump = new Music("\\sounds\\jump.wav");
					repaint();
				}
			}

			if (_pressedCode == KeyEvent.VK_SPACE) {
				if (!_worm[_wormTurn]._isAiming) {
					isOnGround();
					_worm[_wormTurn]._isAiming = true;
					_worm[_wormTurn].setCurrentRow(_worm[_wormTurn].getCurrentRow());
					_worm[_wormTurn].setCurrentFrame(2);
					_worm[_wormTurn].initIntention((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1,
							orderOnLine((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1));
				} else {
					_worm[_wormTurn]._isAiming = false;
					_worm[_wormTurn]._isShooting = true;
					_worm[_wormTurn].shoot((_worm[_wormTurn].getCurrentRow() % 2 == 0) ? 1 : -1);
					_launch = new Music("\\sounds\\launch.wav");
					_turnTimer.stop();
					_seconds = 1;
				}
				repaint();
			}
		}
		if (_pressedCode == KeyEvent.VK_CONTROL)
			_seconds = 1;
	}

	/**
	 * taking care of keys releasing
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (_teamTurn == 1 && !_isPaused && _worm[_wormTurn] != null) {
			if (_pressedCode == e.getKeyCode() && !_worm[_wormTurn]._isJumping) {
				_worm[_wormTurn]._walkTimer.stop();
			}
			if (!_worm[_wormTurn]._isAiming && !_worm[_wormTurn]._isJumping) {
				_worm[_wormTurn].setCurrentFrame(4);
			}
			repaint();
		}
	}

	/**
	 * moves the frame and makes the worm which its his turn to be in the center of
	 * the frame
	 */
	public void focusOnWorm() {
		if (_worm[_wormTurn].getX() > 575) {
			while ((_worm[_wormTurn].getX() < 574 || _worm[_wormTurn].getX() > 576) && _limit + _speedX >= 0) {
				if (_speedX > 0)
					_speedX = -_speedX;
				if (_limit + _speedX >= 0) {
					_xOffSet += _speedX;
					for (int i = 0; i < _numOfWorms * 2; i++) {
						if (_worm[i] != null) {
							_worm[i].setX(_speedX + _worm[i].getX());
							_worm[i].setXG(_mouseSpeedX + _worm[i].getXG());
						}
					}
					_limit += _speedX;
				}
			}
		} else if (_worm[_wormTurn].getX() < 575) {
			while ((_worm[_wormTurn].getX() < 574 || _worm[_wormTurn].getX() > 576) && _limit + _speedX < 1150) {
				if (_speedX < 0)
					_speedX = -_speedX;
				if (_limit + _speedX < 1150) {
					_xOffSet += _speedX;
					for (int i = 0; i < _numOfWorms * 2; i++) {
						if (_worm[i] != null) {
							_worm[i].setX(_speedX + _worm[i].getX());
							_worm[i].setXG(_mouseSpeedX + _worm[i].getXG());
						}
					}
					_limit += _speedX;
				}
			}
		}
		repaint();
	}

	/**
	 * moving the frame and keeps the worm with the current turn in the center of
	 * the frame
	 * 
	 * @param direct
	 * @param x
	 */
	public void moveFrame(int direct, int x) {
		if (direct == 1) {
			if (x > 575) {
				if (_speedX > 0)
					_speedX = -_speedX;
				if (_limit + _speedX > 0) {
					_xOffSet += _speedX;
					for (int i = 0; i < _numOfWorms * 2; i++) {
						if (_worm[i] != null) {
							_worm[i].setX(_speedX + _worm[i].getX());
							_worm[i].setXG(_mouseSpeedX + _worm[i].getXG());
						}
					}
					_limit += _speedX;
				}
			}
		}

		else if (x < 575) {
			if (_speedX < 0)
				_speedX = -_speedX;
			if (_limit + _speedX < 1150) {
				_xOffSet += _speedX;
				for (int i = 0; i < _numOfWorms * 2; i++) {
					if (_worm[i] != null) {
						_worm[i].setX(_speedX + _worm[i].getX());
						_worm[i].setXG(_mouseSpeedX + _worm[i].getXG());
					}
				}
				_limit += _speedX;
			}
		}
	}

	/**
	 * moves the frame with mouse dragging
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!_isPaused) {
			if (e.getX() < _lastX) {
				if (_mouseSpeedX > 0)
					_mouseSpeedX = -_mouseSpeedX;
				if (_limit + _mouseSpeedX >= 0) {
					_lastX = e.getX();
					_xOffSet += _mouseSpeedX;
					for (int i = 0; i < _numOfWorms * 2; i++) {
						if (_worm[i] != null) {
							_worm[i].setX(_mouseSpeedX + _worm[i].getX());
							_worm[i].setXG(_mouseSpeedX + _worm[i].getXG());
						}
					}
					_limit += _mouseSpeedX;
				}
			}

			else {
				if (_mouseSpeedX < 0)
					_mouseSpeedX = -_mouseSpeedX;
				if (_limit + _mouseSpeedX < 1150) {
					_lastX = e.getX();
					_xOffSet += _mouseSpeedX;
					for (int i = 0; i < _numOfWorms * 2; i++) {
						if (_worm[i] != null) {
							_worm[i].setX(_mouseSpeedX + _worm[i].getX());
							_worm[i].setXG(_mouseSpeedX + _worm[i].getXG());
						}
					}
					_limit += _mouseSpeedX;
				}
			}
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
