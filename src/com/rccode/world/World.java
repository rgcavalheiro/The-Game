package com.rccode.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.rccode.main.Game;

import entities.*;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 64;
	
	
	
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			
			
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0,map.getWidth(),map.getHeight(),pixels,0,map.getWidth());
			
			
			
			for(int xx = 0; xx < map.getWidth();xx++) {
				for(int yy = 0;yy < map.getHeight();yy++) {
					int pixelAtual = pixels[xx+(yy*map.getWidth())];
					
					tiles[xx+(yy * WIDTH)] = new FloorTile(xx*64,yy*64,Tile.TILE_FLOOR);
					
					switch(pixelAtual) {
					
					case 0xFF000000:						
						//chao
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*64,yy*64,Tile.TILE_FLOOR);
					break;
					
					case 0xFFFFFFFF:
						//parede
						tiles[xx+(yy * WIDTH)] = new WallTile(xx*64,yy*64,Tile.TILE_WALL);
					break;
					
					case 0xFF0026FF:
						//player
						Game.player.setX(xx*64);
						Game.player.setY(yy*64);
					break;
					
					case 0xFFFF00DC:
						//banana
						Game.entities.add(new Banana(xx*64,yy*64,64,64,Entity.BANANA_EN));
					break;
					
					case 0xFFFF0000:
						// Enemy
						Enemy en = new Enemy(xx*64,yy*64,64,64,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);						
					break;
					
					case 0xFF00FFFF:
						// Blunder
						Game.entities.add(new Blunder(xx*64,yy*64,64,64,Entity.BLUNDER_EN));
					break;
					
					case 0xFFFF6A00:
						// firebomb
						Game.entities.add(new Firebomb(xx*64,yy*64,64,64,Entity.FIREBOMB_EN));
					break;

					
					}
					
					
					
					
				}
			}
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext) {
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !(
				(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile) 
				);
	}
	
	public void render(Graphics g) {
		int var = 6;
		int xstart = Camera.x >>var;
		int ystart = Camera.y >>var;
		
		int xfinal = xstart + (Game.WIDTH >>var);
		int yfinal = ystart + (Game.HEIGHT >>var);
		
		for(int xx= xstart;xx <= xfinal; xx++) {
			for(int yy=ystart;yy <= yfinal; yy++) {
				if(xx<0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) 
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
				
			}
		}
	}

}
