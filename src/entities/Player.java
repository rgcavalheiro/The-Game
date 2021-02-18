package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.rccode.main.Game;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public int right_dir = 0,left_dir = 1;
	public int dir = right_dir;	
	public double speed = 2;
	
	private int frames = 0,maxFrames = 5,index = 0,maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		
		
		int atual = 128;
		int pixels = 64;
		int total_sprites = 12;
		
		int[] posicaosprite = new int[total_sprites];

		for (int x1 = 0; x1 < total_sprites; x1++) {
			posicaosprite[x1] = atual;
			atual += pixels;
		}
		
		
		for(int i=0;i<4;i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(posicaosprite[i], 0, 64, 64);
		}
		for(int i=0;i<4;i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(posicaosprite[i], 64, 64, 64);
		}
		
		
		
	}

	public void tick() {
		moved = false;
		if(right) {
			moved = true;
			dir = right_dir;
			x+=speed;
		}
		else if(left) {
			moved = true;
			dir = left_dir;
			x-=speed;
		}
		if(up) {
			moved = true;
			y-=speed;
		}
		else if(down) {
			moved = true;
			y+=speed;
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
	}
	
	public void render(Graphics g) {
		if(dir == right_dir) {
		g.drawImage(rightPlayer[index],this.getX(),this.getY(),null);
		}else if(dir == left_dir) {
		g.drawImage(leftPlayer[index],this.getX(),this.getY(),null);
		}
		
	}
}
