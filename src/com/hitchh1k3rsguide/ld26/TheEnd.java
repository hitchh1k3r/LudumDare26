package com.hitchh1k3rsguide.ld26;

public class TheEnd implements IScene {

	private int pictureId;
	private int opacity = 0;
	private double totalTime = 0;

	public TheEnd(int pictureId)
	{
		this.pictureId = pictureId;
		Sounds.stopMusic();
	}
	
	public void onKey(int key, boolean isDown)
	{
		
	}

	public void onMouseMove(int x, int y)
	{
		
	}

	public void onMouseClick(int x, int y, int button, boolean isDown)
	{
		
	}

	public IScene update(double time)
	{
		totalTime += time;
		if(totalTime > 0.5 && totalTime < 3.5)
			opacity = (int) (((totalTime-0.5)/3)*255);
		if(totalTime > 5.5 && totalTime < 10.5)
			opacity = 255 - (int) (((totalTime-7.5)/5)*255);
		if(totalTime > 11)
			return new Menu();
		return null;
	}

	public void draw()
	{
		ImageRenderer.renderImageWithColor(320-192, -64, 384, 384, "adventureStrip", pictureId, opacity, opacity, opacity);
		if(pictureId == 6)
			FontRenderer.renderTextWithColor(320-93, 336, "Well Done", opacity, opacity, opacity);
		else
			FontRenderer.renderTextWithColor(320-71, 336, "The End", opacity, opacity, opacity);
	}
}
