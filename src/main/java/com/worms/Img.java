package com.worms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
/**
 * a class for image
 * @author Ran Elishayev
 *
 */
public class Img
{
	private static final HashMap<String, Image> scaledCache = new HashMap<>();
	private Image _image;
	private int x, y, width, height;
	/**
	 * rebooting all variables
	 * @param path image path
	 * @param x image x
	 * @param y image y
	 * @param width image width
	 * @param height image height
	 */
	public Img(String path, int x, int y, int width, int height)
	{
		ImageIcon ic=  new ImageIcon(this.getClass().getClassLoader().getResource(path));
		ic.setImage(ic.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		_image=ic.getImage();
		
		setImgCords(x, y);
		setImgSize(width, height);
	}
	
	/**
	 * method that draw the image
	 * @param g
	 */
	public void drawImg(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(_image, x, y, width, height, null);
	}
	/**
	 * method for changing the x and of image 
	 * @param x image x
	 * @param y image y
	 */
	public void setImgCords(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	/**
	 * returning the image x
	 * @return x
	 */
	public int getImgXCords()
	{
		return this.x;
	}
	/**
	 * returning the image y
	 * @return y
	 */
	public int getImgYCords()
	{
		return this.y;
	}
	/**
	 * method for changing the image size
	 * @param width image width
	 * @param height image height
	 */
	public void setImgSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	/**
	 * returning the image width
	 * @return width
	 */
	public int getImgWidth() 
	{
		return this.width;
	}
	/**
	 * returning the image height
	 * @return height
	 */
	public int getImgHeight() 
	{
		return this.height;
	}
	/**
	 * method for changing the image 
	 * @param image
	 */
	public void setImg(Image image)
	{
		_image = image;
	}
	/**
	 * method for changing the image path
	 * @param path
	 */
	public void setPath(String path)
	{
		String key = path + ":" + width + "x" + height;
		Image cached = scaledCache.get(key);
		if (cached != null) {
			_image = cached;
			return;
		}
		Image raw = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage();
		BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = scaled.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g2.drawImage(raw, 0, 0, width, height, null);
		g2.dispose();
		_image = scaled;
		scaledCache.put(key, scaled);
	}
	/**
	 * method for drawing the image
	 * can draw the image with an angle
	 * @param g the graphics component
	 * @param angle the angle in which the component will be drawn
	 */
	public void drawImg(Graphics g, float angle) 
	{
		Graphics2D g2d = (Graphics2D) g.create(); 
		AffineTransform af = g2d.getTransform();
		g2d.translate(width/2, height/2);
		g2d.translate(-width/2, -height/2);
		g2d.rotate(Math.toRadians(angle),x+(width/2),y+(height/2));
        g2d.drawImage(_image, x, y, height, width, null);
        g2d.setTransform(af);
        g2d.dispose();
	}
}


