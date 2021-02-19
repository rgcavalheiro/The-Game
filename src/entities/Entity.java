package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.rccode.main.Game;
import com.rccode.world.Camera;

public class Entity {

	public static BufferedImage BANANA_EN = Game.spritesheet.getSprite(0, 64, 64, 64);
	public static BufferedImage BLUNDER_EN = Game.spritesheet.getSprite(64, 64, 64, 64);
	public static BufferedImage FIREBOMB_EN = Game.spritesheet.getSprite(64*2, 64, 64, 64);
	
	
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(0,4*64, 64, 64);
	
	
	
	protected double x,y;
	protected int width,height;
	
	
	private BufferedImage sprite;
	
	private int maskx,masky,mwidth,mheight;
	
	public Entity(int x,int y, int width, int height,BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
		
		
	}
	
	public void setMask(int maskx,int masky,int mwidth,int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public void tick() {
		
	}
	
	
	
	public static boolean isColidding(Entity e1,Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY()+e1.masky,e1.mwidth,e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY()+e2.masky,e2.mwidth,e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY()- Camera.y,null);		
	}
	
	
	
	

	public int getX() {
		return (int)x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	
	
}
