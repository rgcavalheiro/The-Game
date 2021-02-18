package com.rccode.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rccode.main.Game;

public class Tile {
	
	
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 64,64);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(64, 0, 64, 64);
	
	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x ,int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite,  x, y, null);
	}
	

}
