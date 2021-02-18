package com.rccode.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {
	
	private Tile[] tiles;
	public static int WIDTH,HEIGHT;
	
	
	
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
					
					switch(pixelAtual) {
					case 0xFF000000:						
						//chao
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*64,yy*64,Tile.TILE_FLOOR);
					break;
					case 0xFFFFFFFF:
						//parede
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*64,yy*64,Tile.TILE_WALL);
					break;
					case 0xFF0026FF:
						//player
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*64,yy*64,Tile.TILE_FLOOR);
					break;
					default:
						tiles[xx+(yy * WIDTH)] = new FloorTile(xx*64,yy*64,Tile.TILE_FLOOR);
					break;
					
					}
					
					
					
					
				}
			}
			
			for(int i=0;i < pixels.length;i++) {
				if(pixels[i]== 0xFFFF0000) {
					System.out.println("RED!");
				}
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		for(int xx=0;xx < WIDTH; xx++) {
			for(int yy=0;yy < HEIGHT; yy++) {
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}

}
