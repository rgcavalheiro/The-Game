package com.rccode.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.rccode.main.Game;



public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8,4,100,16);
		g.setColor(Color.green);
		g.fillRect(8,4,(int)((Game.player.life/Game.player.maxlife)*100),16);
		
		
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,8));		
		g.drawString((int)Game.player.life + "/" + (int)Game.player.maxlife,16, 16);
//		g.setColor(Color.white);
//		g.setFont(new Font("arial",Font.BOLD,16));		
//		g.drawString("Blunder:"+Player.blunder,16, 32);
	}
}
