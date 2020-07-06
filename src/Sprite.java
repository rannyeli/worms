
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import org.jgrapht.alg.DijkstraShortestPath;
/**
 * Class of each worm in the game
 * @author Ranny Elyashiv
 *
 */
public class Sprite {
	private GamePanel _gameView;// the panel the photo will sit on
	
	//worm variables
	protected int _bmpColumns;
	private int _bmpRows;
	private int _x;
	private int _y;
	private int _width;
	private int _height;
	protected int _currentFrame = 4;
	private int _currentRow = 1;
	
	//grenade variables
	private int _bmpColumnsG = 8;
	private int _bmpRowsG = 2;
	private double _xG;
	private double _yG;
	private int _widthG;
	private int _heightG;
	private int _currentFrameG;
	private int _currentRowG;
	
	protected Rectangle _recW1, _recW2, _recG1, _recG2;
	private int _xSpeed;
	private int _ySpeed;
	
	//images variables
	private ImageIcon _imgIcon;
	private Image _img;
	public Img _name;
	private Img _hpImg;
	private Image _grenade;
	protected Image _explotion;

	//aim variables
	private double _m;
	private Point _p1;
	private Point _p2;
	protected Img _intention;
	private int _aimCounter;
	private double _angle = 0;

	//direction variables
	private double _incline;
	private int _direct = 0;
	private int _side;
	
	private int _hp = 100;
	
	//status variables
	protected boolean _isJumping = false;
	protected boolean _isAiming = false;
	protected boolean _isShooting = false;
	protected boolean _exploded = false;
	
	//team variables
	public int _teamNum;
	private String _teamColor;

	//timer variables
	public Timer _walkTimer;
	public Timer _shootTimer;
	public Timer _jumpTimer;
	public Timer _delay;

	/**
	 * Ctor
	 * @param gameView: the panel the photo will sit on
	 * @param cols: sprite image cols
	 * @param rows: sprite image rows
	 * @param tn: team number
	 * @param tc: team color
	 * @param name: worm name
	 * @param x: x cord
	 * @param y: y cord
	 */
	public Sprite(GamePanel gameView, int cols, int rows, int tn, String tc, String name, int x, int y) {
		_gameView = gameView;
		_teamNum = tn;
		_teamColor = tc;
		_bmpColumns = cols;
		_bmpRows = rows;
		_imgIcon = new ImageIcon(this.getClass().getClassLoader().getResource("Images\\sprites\\walk.png"));
		_img = _imgIcon.getImage();
		_imgIcon = new ImageIcon(this.getClass().getClassLoader().getResource("Images\\sprites\\grenade.png"));
		_grenade = _imgIcon.getImage();
		_imgIcon = new ImageIcon(this.getClass().getClassLoader().getResource("Images\\explotion.gif"));
		_explotion = _imgIcon.getImage();
		_width = _img.getWidth(null) / _bmpColumns;
		_height = _img.getHeight(null) / _bmpRows;
		_widthG = _grenade.getWidth(null) / _bmpColumnsG;
		_heightG = _grenade.getHeight(null) / _bmpRowsG;
		_x = x;
		_y = y;
		_xG = _x + 50;
		_yG = _y;
		_xSpeed = 6;
		_ySpeed = 6;
		_name = new Img("Images\\names\\" + name + "-" + _teamColor + ".png", _x, _y - 30, 80, 25);
		_hpImg = new Img("Images\\hp\\" + _hp + ".png", _x + 9, _y - 5, 40, 15);
		
		//walk timer
		_walkTimer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_currentRow == 0) {
					_x += _xSpeed;
					_currentFrame = (_currentFrame + 1) % (_bmpColumns - 1);
				}
				if (_currentRow == 1) {
					_x -= _xSpeed;
					_currentFrame = (_currentFrame + 1) % (_bmpColumns - 1);
				}
				if (_currentRow == 2) {
					_x += _xSpeed - 1;
					_y += _ySpeed - 1;
					_currentFrame = (_currentFrame + 1) % (_bmpColumns - 1);
				}
				if (_currentRow == 3) {
					_x -= _xSpeed - 1;
					_y += _ySpeed - 1;
					_currentFrame = (_currentFrame + 1) % (_bmpColumns - 1);
				}
				if (_currentRow == 4) {
					_x += _xSpeed - 1;
					_y -= _ySpeed - 1;
					_currentFrame = (_currentFrame + 1) % (_bmpColumns - 1);
				}
				if (_currentRow == 5) {
					_x -= _xSpeed - 1;
					_y -= _ySpeed - 1;
					_currentFrame = (_currentFrame + 1) % (_bmpColumns - 1);
				}
				if (_currentFrame == 0)
					_walkTimer.stop();
			}
		});

		_delay = new Timer(700, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_exploded = false;
				_gameView.repaint();
				_delay.stop();
			}
		});

		//shoot timer
		_shootTimer = new Timer(50, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_xG += 15 * _direct;
				_yG = lineQuery();
				_currentFrameG = (_currentFrameG + 1) % (_bmpColumnsG - 1);
				if (_xG < 0 || _xG > 1150 || _yG < 0 || _yG > 700) {
					_isShooting = false;
					_gameView.repaint();
					_gameView._turnTimer.start();
					_shootTimer.stop();
				}
				if (_gameView.isGrenadeOnGround() || _gameView.isGrenadeHit()) {
					_isShooting = false;
					_exploded = true;
					_gameView.repaint();
					_delay.start();
					_shootTimer.stop();
				}
				_gameView.repaint();
			}
		});

		//jump timer
		_jumpTimer = new Timer(30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (_gameView.isBlocked() || _angle >= 7.09) {
					_angle = 6;
				}
				_angle += 0.1;
				_y = _y + (int) ((Math.sin(_angle) + Math.cos(_angle)) * 7);
				_x += _direct * 3;
				if (_direct == -1)
					_currentFrame = 1;
				if (_gameView.isOnGround()) {
					_isJumping = false;
					if (_direct == -1)
						_currentRow = 1;
					_gameView.orderOnLine(_direct);
					_gameView.repaint();
					_jumpTimer.stop();
				}
				_gameView.moveFrame(_direct, _x);
				_gameView.repaint();
			}
		});
	}

	/**
	 * calculating y on line
	 * @return y
	 */
	public int lineQuery() {
		int y = (int) (_m * (_xG - _p1.getX()) + _p1.getY());
		return y;
	}

	/**
	 * starts the walk timer
	 */
	protected void walk() {
		_walkTimer.start();
	}

	/**
	 * updating the intention cords
	 * @param d: direction 1 or -1
	 * @param side: right=1 or left=-1
	 * @param r: up=1 or down=-1
	 */
	public void aim(int d, int side, int r) {
		if (r == 1) {
			if (_aimCounter < 100) {
				_aimCounter++;
				_angle += (0.03 * d);
				_intention.setImgCords((int) ((_x + 15) + ((120 * side) * Math.cos(_angle))),
						(int) ((_y + 15) + ((120 * side) * Math.sin(_angle))));
			}
		} else if (_aimCounter > 0) {
			_aimCounter--;
			_angle += (0.03 * d);
			_intention.setImgCords((int) ((_x + 15) + ((120 * side) * Math.cos(_angle))),
					(int) ((_y + 15) + ((120 * side) * Math.sin(_angle))));
		}
		if (_aimCounter >= 80 && _aimCounter < 100) {
			_currentFrame = 4;
		}
		if (_aimCounter >= 60 && _aimCounter < 80) {
			_currentFrame = 3;
		}
		if (_aimCounter >= 40 && _aimCounter < 60) {
			_currentFrame = 2;
		}
		if (_aimCounter >= 20 && _aimCounter < 40) {
			_currentFrame = 1;
		}
		if (_aimCounter >= 0 && _aimCounter < 20) {
			_currentFrame = 0;
		}
	}

	/**
	 * initalize the intention according to the current incline
	 * @param side
	 * @param incline
	 */
	public void initIntention(int side, double incline) {
		_incline = incline;
		_side = side;
		_aimCounter = 50;
		if (_incline > -0.5 && _incline <= 0 || _incline >= 0 && _incline < 0.5) {
			_angle = 0;
		}
		if (_incline >= 1 && side == 1) {// down right
			_angle = 44.75;
		} else if (_incline >= 1 && side == -1) {// up left
			_angle = 95;
		} else if (_incline <= -0.5 && side == 1) {// up right
			_angle = -44.75;
		} else if (_incline <= -0.5 && side == -1) {// down left
			_angle = -95;
		}
		_intention = new Img("Images//intention-" + _teamColor + ".png",
				(int) ((_x + 15) + ((120 * side) * Math.cos(_angle))),
				(int) ((_y + 15) + ((120 * side) * Math.sin(_angle))), 30, 30);
	}

	/**
	 * starts shoot timer
	 * @param d
	 */
	public void shoot(int d) {
		_xG = (d == 1) ? _x + 35 : _x + 5;
		_yG = _y + 30;
		_direct = d;
		_p1 = new Point((int) _xG, (int) _yG);
		_p2 = new Point(_intention.getImgXCords() + 7, _intention.getImgYCords() + 7);
		_m = (_p1.getY() - _p2.getY()) / (_p1.getX() - _p2.getX());
		_currentFrameG = 0;
		_currentRowG = (d == 1) ? 0 : 1;
		_shootTimer.start();
	}

	/**
	 * calculating size of damage
	 * @param xExplode
	 * @param yExplode
	 */
	public void hit(int xExplode, int yExplode) {
		int xWorm = _x + 10;
		int yWorm = _y + 15;
		if ((yWorm > yExplode && yWorm < yExplode + 64)
				|| ((yWorm + _height - 30) > yExplode && (yWorm + _height - 30) < yExplode + 64)) {
			if (xExplode < xWorm) {
				xExplode += 64;
				if (xExplode > xWorm && xExplode < (xWorm + _width - 30))
					_hp -= (xExplode - xWorm);
				else if (xExplode >= (xWorm + _width - 30)) {
					_hp -= _width - 30;
				}
			} else if (xExplode >= xWorm) {
				xWorm += (_width - 30);
				if (xExplode > (xWorm - _width - 30) && xExplode < xWorm)
					_hp -= (xWorm - xExplode);
			} else if (xWorm > xExplode && xWorm < xExplode + 64 && (xWorm + _width - 30) > xExplode
					&& (xWorm + _width - 30) < xExplode + 64) {
				_hp -= _width - 30;
			}
			if (_hp < 0)
				_hp = 0;
		}
	}

	/**
	 * starts jump timer
	 * @param d
	 * @param ang
	 */
	public void jump(int d, int ang) {
		_direct = d;
		_angle = ang;
		_isJumping = true;
		_jumpTimer.start();
	}

	public void setImage(Image bmp, int rows, int cols) {
		_bmpColumns = cols;
		_bmpRows = rows;
		this._img = bmp;
		this._width = bmp.getWidth(null) / _bmpColumns;
		this._height = bmp.getHeight(null) / _bmpRows;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int _width) {
		this._width = _width;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int _height) {
		this._height = _height;
	}

	public int getCurrentFrame() {
		return _currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		_currentFrame = currentFrame;
	}

	public int getCurrentRow() {
		return _currentRow;
	}

	public void setCurrentRow(int currentRow) {
		_currentRow = currentRow;
	}

	public void setX(int x) {
		_x = x;
	}

	public int getX() {
		return _x;
	}

	public void setY(int y) {
		_y = y;
	}

	public int getY() {
		return _y;
	}

	public int getDirect() {
		return _direct;
	}

	public void setDirect(int _direct) {
		this._direct = _direct;
	}

	public double getXG() {
		return _xG;
	}

	public void setXG(double _xG) {
		this._xG = _xG;
	}

	public double getYG() {
		return _yG;
	}

	public void setYG(double _yG) {
		this._yG = _yG;
	}

	public int getWidthG() {
		return _widthG;
	}

	public void setWidthG(int _widthG) {
		this._widthG = _widthG;
	}

	public int getHeightG() {
		return _heightG;
	}

	public void setHeightG(int _heightG) {
		this._heightG = _heightG;
	}

	public String getTeamColor() {
		return _teamColor;
	}

	public void setHp(int hp) {
		_hp = hp;
	}

	public int getHp() {
		return _hp;
	}

	/**
	 * draws the worm
	 * @param g
	 */
	public void drawImg(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		_recW1 = new Rectangle(_currentFrame * _width, _currentRow * _height, _width, _height);
		_recW2 = new Rectangle(_x, _y, _width, _height);
		g2d.drawImage(_img, _recW2.x, _recW2.y, _recW2.x + _recW2.width, _recW2.y + _recW2.height, _recW1.x, _recW1.y,
				_recW1.x + _recW1.width, _recW1.y + _recW1.height, null);
		_name.setImgCords(_x, _y - 30);
		_name.drawImg(g);
		_hpImg.setImgCords(_x + 9, _y - 5);
		_hpImg.setPath("Images\\hp\\" + _hp + ".png");
		_hpImg.drawImg(g);
		if (_isShooting) {
			_recG1 = new Rectangle(_currentFrameG * _widthG, _currentRowG * _heightG, _widthG, _heightG);
			_recG2 = new Rectangle((int) _xG, (int) _yG, _widthG, _heightG);
			g2d.drawImage(_grenade, _recG2.x, _recG2.y, _recG2.x + _recG2.width, _recG2.y + _recG2.height, _recG1.x,
					_recG1.y, _recG1.x + _recG1.width, _recG1.y + _recG1.height, null);
		}
		if (_isAiming) {
			_intention.setImgCords((int) ((_x + 15) + ((120 * _side) * Math.cos(_angle))),
					(int) ((_y + 15) + ((120 * _side) * Math.sin(_angle))));
			_intention.drawImg(g);
		}
	}
}
