package com.hitchh1k3rsguide.ld26;

import com.hitchh1k3rsguide.ld26.gui.Button;

public class Menu implements IScene {

	private Button start = new Button(196, 300, 250, "Start Game");
	private Button hard = new Button(171, 360, 300, "Challenge Mode");
	private int enterGame = 0;
	private static boolean hasBeatGame = false;

	public Menu()
	{
		Sounds.playMusic("menu");
	}
	
	public void onKey(int key, boolean isDown)
	{
		
	}

	public void onMouseMove(int x, int y)
	{
		
	}

	public void onMouseClick(int x, int y, int button, boolean isDown)
	{
		if(isDown && button == 0 && start.isMouseOver(x, y))
		{
			Sounds.playSound("select");
			enterGame = 1;
		}
		if(hasBeatGame && isDown && button == 0 && hard.isMouseOver(x, y))
		{
			Sounds.playSound("select");
			enterGame = 2;
		}
	}

	public IScene update(double time)
	{
		start.update();
		if(hasBeatGame)
			hard.update();
		if(enterGame == 1)
		{
			Buffs.reset();
			return new Quest();
		}
		if(enterGame == 2)
		{
			Buffs.reset();
			Buffs.setBuff("totalHP", 9);
			return new BossFight(true);
		}
		return null;
	}

	public void draw()
	{
		FontRenderer.renderTextWithColor(320-104, 152, "Small Tale", 141, 48, 80);
		FontRenderer.renderTextWithColor(320-106, 150, "Small Tale", 215, 108, 31);
		start.draw();
		if(hasBeatGame)
			hard.draw();
	}

	public static void beatGame()
	{
		hasBeatGame = true;
	}
}
