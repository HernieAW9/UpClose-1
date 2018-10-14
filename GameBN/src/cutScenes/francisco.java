package cutScenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.AnimatedSprite;
import entity.Player;
import entity.Rectangle;
import entity.SpriteSheet;
import game.Game;
import handler.KeyBoardListener;
import handler.RenderHandler;

public class francisco {

	private BufferedImage scene;

	private Rectangle rect;
	private Rectangle timerRect;


	private Font f = new Font("arial", Font.PLAIN, 30);
	private Font fontKey = new Font("arial", Font.PLAIN, 20);

	public Player player;

	// dialog
	private String[] laila =
		{
				"There is only one master...",
				"And that is me, Francisco!",
				"We will take over your land, Pengiran!"
				"Come on fellas, stick 'em with the pointy end."
		};


	private String[] ratna = 
		{
				"adik! what?! stop coming into my room as you please..",
				"i'm busy rehearsing for my History play.",
				"abang dialog 3"
		};

	private String key = "press [A]";

	private int g = 0;	
	private int b = 0;

	private String addedlailaChar = "";
	private String addedratnaChar = "";

	private int addedlailaCharCounter = 0;
	private int addedratnaCharCounter = 0;

	private boolean beginratna = false;
	private boolean beginlaila = false;

	public francisco(Game game) {
		//bedroom bg
		scene = game.loadImage("/lailaRatna_Scene.png");

		// dialog
		rect = new Rectangle(40, 600, 300, 50);
		rect.generateGraphics(0xeff0f1);

		// TIMER RECT
		timerRect = new Rectangle(0, 0, 10, 32);
		timerRect.generateGraphics(1, 0xffffff);
	}

	public void update(Game game) {
		timerRect.x++;

		try {
			//DIALOGS

			if(timerRect.x > 10 && timerRect.x <= 40) {
				// ANIMATING DIALOGS - laila
				char lailaChar[] = laila[g].toCharArray();
				if(beginlaila == false) {
					addedlailaChar = "";
					addedlailaCharCounter = 0;
					beginlaila = true;
				}
				if(addedlailaCharCounter <= lailaChar.length-1) {
					addedlailaChar = addedlailaChar + lailaChar[addedlailaCharCounter];
					addedlailaCharCounter++;
				}
			}

			if(timerRect.x > 40 && timerRect.x < 100) {
				// ANIMATING DIALOGS - ratna
				char ratnaChar[] = ratna[b].toCharArray();
				if(beginratna == false) {
					addedratnaChar = "";
					addedratnaCharCounter = 0;
					beginratna = true;
					g++;
				}
				if(addedratnaCharCounter <= ratnaChar.length-1) {
					addedratnaChar = addedratnaChar + ratnaChar[addedratnaCharCounter];
					addedratnaCharCounter++;
				} else {
					beginlaila = false;
				}


			}


			if(timerRect.x > 100) {

				String adik2 = "adik dialog 2";
				// ANIMATING DIALOGS - laila
				char lailaChar[] = laila[g].toCharArray();
				if(beginlaila == false) {
					addedlailaChar = "";
					addedlailaCharCounter = 0;
					beginlaila = true;
					b++;
				}
				if(addedlailaCharCounter <= lailaChar.length-1) {
					addedlailaChar = addedlailaChar + lailaChar[addedlailaCharCounter];
					addedlailaCharCounter++;
				}
			}


			if(timerRect.x >= 10) {
				KeyBoardListener keyListener = game.getKeyListener();

				if(keyListener.a()) {
					Game.State = Game.STATE.LAILARATNA;
				}

			}

			Thread.sleep(100);

		} 	

		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void render(RenderHandler renderer, int xZoom, int yZoom) {
		renderer.renderImage(scene, 10, 150, xZoom, yZoom, true);;

		if(timerRect.x > 10) {
			renderer.renderRectangle(rect, xZoom, yZoom, true);
		}
		
		renderer.renderRectangle(timerRect, xZoom, yZoom, false);

	}

	public void render(Graphics graphics) {
		graphics.setFont(f);

		if(timerRect.x > 10 && timerRect.x < 40) {

			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedlailaChar, 70, 650);


		}

		if(timerRect.x >= 40 && timerRect.x < 100) {

			graphics.setColor(Color.BLUE);
			graphics.drawString(addedratnaChar, 70, 650);

			graphics.setFont(fontKey);
			graphics.setColor(Color.BLACK);
			graphics.drawString(key, 800, 740);
			System.out.println(addedlailaChar);

		}

		if(timerRect.x > 100) {
			graphics.setColor(Color.MAGENTA);
			graphics.drawString(addedlailaChar, 70, 650);
			System.out.println("Hello");
		}
	}

}