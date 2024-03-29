
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
 * Instructions panel
 * 
 * @author Ranny Elyashiv
 *
 */
public class InstructionsPanel extends JPanel {

	private Image _imgBackground;// the instruction image;
	private Rectangle _controls = new Rectangle(855, 105, 32, 32);
	private Rectangle _retrun = new Rectangle(168, 154, 39, 39);
	private LinkedList<ChangeScreensInterface> _listeners;
	private static String _prevScreen;// which screen changed to instructions

	/**
	 * rebooting the variables
	 */
	public InstructionsPanel() {
		// TODO Auto-generated constructor stub
		ImageIcon imgI = new ImageIcon("src/Images/An Overview.png");
		_imgBackground = imgI.getImage();
		_listeners = new LinkedList<ChangeScreensInterface>();
		setPreferredSize(new Dimension(GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY));
		this.setLayout(null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				if (_controls.contains(e.getPoint())) {
					new Music("\\sounds\\buttonPressed.wav");
					InstControlsPanel.setPrevScreen(_prevScreen);
					for (ChangeScreensInterface hl : _listeners)
						hl.changeScreenType(ScreenTypes.Controls);
				} else if (_retrun.contains(e.getPoint())) {
					new Music("\\sounds\\backPressed.wav");
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
		InstructionsPanel._prevScreen = prevScreen;
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