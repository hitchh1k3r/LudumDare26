package com.hitchh1k3rsguide.ld26.gui;

import org.lwjgl.input.Mouse;

import com.hitchh1k3rsguide.ld26.FontRenderer;
import com.hitchh1k3rsguide.ld26.ImageRenderer;
import com.hitchh1k3rsguide.ld26.MyMath;
import com.hitchh1k3rsguide.ld26.Sounds;

public class Button {

	private boolean isActive = false;
	private boolean isEnabled = true;
	private int x, y, width;
	private String text;

	public Button(int x, int y, int width, String text)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.text = text;
	}

	public void setEnabled(boolean state)
	{
		this.isEnabled = state;
	}

	public void update()
	{
		int x = Mouse.getX();
		int y = 480-Mouse.getY();
		if(isEnabled && x >= this.x && x <= this.x+this.width+4 && y >= this.y && y <= this.y+40)
		{
			if(!isActive)
				Sounds.playSound("hover");
			isActive = true;
		}
		else
			isActive = false;
	}

	public boolean isMouseOver(int x, int y)
	{
		if(x >= this.x && x <= this.x+this.width+4 && y >= this.y && y <= this.y+40)
			return true;
		return false;
	}

	public void draw()
	{
		ImageRenderer.renderImage(x, y, 4, 4, "gui", 0);
		ImageRenderer.renderImage(x+4, y, width-8, 4, "gui", 1);
		ImageRenderer.renderImage(x+width-4, y, 4, 4, "gui", 2);
		ImageRenderer.renderImage(x, y+4, 4, 32, "gui", 3);
		if(isActive)
			ImageRenderer.renderImage(x+4, y+4, width-8, 32, "gui", 9);
		else
			ImageRenderer.renderImage(x+4, y+4, width-8, 32, "gui", 4);
		ImageRenderer.renderImage(x+width-4, y+4, 4, 32, "gui", 5);
		ImageRenderer.renderImage(x, y+36, 4, 4, "gui", 6);
		ImageRenderer.renderImage(x+4, y+36, width-8, 4, "gui", 7);
		ImageRenderer.renderImage(x+width-4, y+36, 4, 4, "gui", 8);
		int offset = (width - (text.length()*22) + (MyMath.countChar(text, ' ')*11)) / 2;
		if(isActive)
			FontRenderer.renderTextWithColor(x+offset, y+6, text, 162, 222, 146);
		else if(isEnabled)
			FontRenderer.renderTextWithColor(x+offset, y+6, text, 66, 159, 103);
		else
			FontRenderer.renderTextWithColor(x+offset, y+6, text, 57, 74, 81);
	}

	public void setText(String argText)
	{
		text = argText;
		return;
	}

}
