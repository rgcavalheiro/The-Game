package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.rccode.graficos.Spritesheet;
import com.rccode.main.Game;
import com.rccode.world.Camera;
import com.rccode.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 3;

	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] damagedPlayer;

	public static int blunder = 0;

	public boolean isDamaged = false;
	private int damageFrames = 0;

	public double life = 100, maxlife = 100;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		damagedPlayer = new BufferedImage[4];

		int posDamagedPlayerX[] = { 64 * 4, 64 * 9, 64 * 9, 64 * 4 };
		int posDamagedPlayerY[] = { 64 * 2, 64 * 2, 64 * 3, 64 * 3 };

		int atual = 0;
		int pixels = 64;
		int total_cols = 10;

		int[] posicaosprite = new int[total_cols];

		for (int x1 = 0; x1 < total_cols; x1++) {
			posicaosprite[x1] = atual;
			atual += pixels;
		}

		for (int i = 0; i < 4; i++) {
			rightPlayer[i] = Game.spritesheet.getSprite(posicaosprite[i], 64 * 2, 64, 64);
		}
		for (int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(posicaosprite[i + 5], 64 * 2, 64, 64);
		}

		for (int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(posicaosprite[i], 64 * 3, 64, 64);
		}
		for (int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(posicaosprite[i + 5], 64 * 3, 64, 64);
		}

		for (int i = 0; i < 4; i++) {
			damagedPlayer[i] = Game.spritesheet.getSprite(posDamagedPlayerX[i], posDamagedPlayerY[i], 64, 64);
		}

	}

	public void tick() {
		moved = false;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			dir = right_dir;
			x += speed;

		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			dir = left_dir;
			x -= speed;
		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			dir = up_dir;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			dir = down_dir;
			y += speed;
		}
		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex)
					index = 0;
			}
		}

		checkCollisionItem();

		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				this.damageFrames = 0;
				isDamaged = false;

			}
		}
		
		if(life<=0) {
			
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.spritesheet = new Spritesheet("/spritesheet.png");
			int atual = 0;
			int pixels = 64;
			int total_sprites = 10;		
			int[] posicaosprite = new int[total_sprites];
			for (int x = 0; x < total_sprites; x++) {
				posicaosprite[x] = atual;
				atual += pixels;}
			Game.player = new Player(0, 0, 32, 32, Game.spritesheet.getSprite(posicaosprite[0], 64*3, pixels, pixels));
			Game.entities.add(Game.player);
			Game.world = new World("/map.png");
			
		}

		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 64 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 64 - Game.HEIGHT);

	}

	private void checkCollisionItem() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Banana) {
				if (Entity.isColidding(this, atual)) {
					life += 20;
					if (life >= 100)
						life = 100;
					Game.entities.remove(atual);
				}
			} else if (atual instanceof Blunder) {
				if (Entity.isColidding(this, atual)) {
					blunder++;
					Game.entities.remove(atual);
				}
			}
		}

	}

	public void render(Graphics g) {
		int varX = this.getX() - Camera.x;
		int varY = this.getY() - Camera.y;
		BufferedImage spriteAtual = null;

		if (dir == right_dir) {
			spriteAtual = rightPlayer[index];
		} else if (dir == left_dir) {
			spriteAtual = leftPlayer[index];
		} else if (dir == down_dir) {
			spriteAtual = downPlayer[index];
		} else if (dir == up_dir) {
			spriteAtual = upPlayer[index];
		}
		if (!isDamaged) {
			g.drawImage(spriteAtual, varX, varY, null);
		} else {
			g.drawImage(damagedPlayer[dir], varX, varY, null);
		}
	}

}
