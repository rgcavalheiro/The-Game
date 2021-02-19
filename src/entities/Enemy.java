package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.rccode.main.Game;
import com.rccode.world.Camera;
import com.rccode.world.World;

public class Enemy extends Entity {

	private double speed = 1;

	private int maskx = 8, masky = 8, maskw = 10, maskh = 10;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 4;

	private BufferedImage[] sprites;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[5];
		int atual = 0;
		int pixels = 64;
		int total_sprites = 10;
		int[] posicaosprite = new int[total_sprites];
		for (int x1 = 0; x1 < total_sprites; x1++) {
			posicaosprite[x1] = atual;
			atual += pixels;
		}

		for (int i = 0; i < 5; i++) {
			sprites[i] = Game.spritesheet.getSprite(posicaosprite[i], 4 * 64, 64, 64);
		}

	}

	public void tick() {
		maskx = 11;
		masky = 1;
		maskw = 44;
		maskh = 63;

		if (this.isColiddingWithPlayer() == false) {
			if ((int) x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
					&& !isColidding((int) (x + speed), this.getY())) {
				x += speed;
			} else if ((int) x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
					&& !isColidding((int) (x - speed), this.getY())) {
				x -= speed;
			}

			if ((int) y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
					&& !isColidding(this.getX(), (int) (y + speed))) {
				y += speed;
			} else if ((int) y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
					&& !isColidding(this.getX(), (int) (y - speed))) {
				y -= speed;
			}
		}else {
			//encontrou um player
			if(Game.rand.nextInt(100)<10) {
				Game.player.life-=Game.rand.nextInt(3);
				Game.player.isDamaged=true;
				
			}
			
		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex)
				index = 0;
		}

	}

	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 64, 64);

		return enemyCurrent.intersects(player);
	}

	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, maskw, maskh);
		for (int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this)
				continue;

			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, maskw, maskh);
			if (enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}

		return false;
	}

	public void render(Graphics g) {

		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
//		g.setColor(Color.blue);
//		g.fillRect(this.getX()+ maskx-Camera.x,this.getY()+masky-Camera.y,maskw,maskh);
	}

}
