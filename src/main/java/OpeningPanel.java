package main.java;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Opening panel
 * 
 * @author Ranny Elyashiv
 *
 */
public class OpeningPanel extends JPanel {
	private Image _imgBackground;// the opening image
	private Rectangle _start = new Rectangle(441, 259, 241, 92);
	private Rectangle _inst = new Rectangle(441, 365, 241, 92);
	private Rectangle _exit = new Rectangle(441, 472, 241, 92);
	private LinkedList<ChangeScreensInterface> _listeners;
	private Music _pressed;

	/**
	 * Ctor
	 */
	public OpeningPanel() {
		// TODO Auto-generated constructor stub
		ImageIcon imgI = new ImageIcon("src/Images/WormsMainMenu.png");
		_imgBackground = imgI.getImage();
		_listeners = new LinkedList<>();
		setPreferredSize(new Dimension(GameFrame.sizeOfScreenX, GameFrame.sizeOfScreenY));
		this.setLayout(null);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				if (_start.contains(e.getPoint())) {
					_pressed = new Music("\\sounds\\buttonPressed.wav");
					for (ChangeScreensInterface hl : _listeners)
						hl.changeScreenType(ScreenTypes.GameOptions);
				} else if (_inst.contains(e.getPoint())) {
					_pressed = new Music("\\sounds\\buttonPressed.wav");
					InstructionsPanel.setPrevScreen("menu");
					for (ChangeScreensInterface hl : _listeners)
						hl.changeScreenType(ScreenTypes.OverView);
				} else if (_exit.contains(e.getPoint())) {
					_pressed = new Music("\\sounds\\buttonPressed.wav");
					System.exit(0);
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
