
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Controls Instructions panel
 * 
 * @author Ranny Elyashiv
 *
 */
public class InstControlsPanel extends JPanel {

	private Image _imgBackground;// the controls instruction image;
	private Rectangle _Overview = new Rectangle(252, 106, 32, 32);// back button area
	private Rectangle _return = new Rectangle(168, 154, 39, 39);// exit from instructions button area
	private LinkedList<ChangeScreensInterface> _listeners;
	private static String _prevScreen;// which screen changed to instructions
	private Music _pressed;
	private Music _pressedBack;

	/**
	 * Ctor
	 */
	public InstControlsPanel() {
		// TODO Auto-generated constructor stub
		ImageIcon imgI = new ImageIcon("src/Images/Controls.png");
		_imgBackground = imgI.getImage();
		_listeners = new LinkedList();
		setPreferredSize(new Dimension(GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY));
		this.setLayout(null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				if (_Overview.contains(e.getPoint())) {
					_pressed = new Music("\\sounds\\buttonPressed.wav");
					for (ChangeScreensInterface hl : _listeners)
						hl.changeScreenType(ScreenTypes.OverView);
				} else if (_return.contains(e.getPoint())) {
					_pressedBack = new Music("\\sounds\\backPressed.wav");
					if (_prevScreen == "menu") {
						for (ChangeScreensInterface hl : _listeners)
							hl.changeScreenType(ScreenTypes.MainMenu);
					} else {
						for (ChangeScreensInterface hl : _listeners)
							hl.changeScreenType(ScreenTypes.Game);
					}
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

	public static String getPrevScreen() {
		return _prevScreen;
	}

	public static void setPrevScreen(String prevScreen) {
		InstControlsPanel._prevScreen = prevScreen;
	}

	/**
	 * painting the panel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(_imgBackground, 0, 0, GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY, this);
	}
}
