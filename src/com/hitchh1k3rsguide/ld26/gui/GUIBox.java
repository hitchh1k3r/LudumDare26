package com.hitchh1k3rsguide.ld26.gui;

import com.hitchh1k3rsguide.ld26.ImageRenderer;

public class GUIBox {

	private int x, y, width, height, stretch;
	/* no longer used, switched to values in the function draw()
	public enum guiCorner {
        NORTHWEST(0), NORTH(1), NORTHEAST(2), WEST(3), CENTER(4), EAST(5), SOUTHWEST(6), SOUTH(7), SOUTHEAST(8);
        private int value;
        private guiCorner(int value) {
                this.value = value;
        }
	};
	*/  
	public GUIBox(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.stretch = 4;
	}

	public void draw()
	{
		int northwestCorner = 0;
		int northCorner = 1;
		int northeastCorner = 2;
		int eastCorner = 5;
		int southwestCorner = 6;
		int southeastCorner = 8;
		int southCorner = 7;
		int westCorner = 3;
		int centerCorner = 10;

		//ImageRenderer.renderImage(x, y, width, height, spriteSheet, spriteId)
		ImageRenderer.renderImage(x, y, stretch, stretch, "gui", northwestCorner);
		ImageRenderer.renderImage(x+stretch, y, width-2*stretch, stretch, "gui", northCorner);
		ImageRenderer.renderImage(x+width-stretch, y, stretch, stretch, "gui", northeastCorner);
		ImageRenderer.renderImage(x, y+stretch, stretch, height-2*stretch, "gui", westCorner);
		ImageRenderer.renderImage(x+stretch, y+stretch, width-2*stretch, height-2*stretch, "gui", centerCorner);
		ImageRenderer.renderImage(x+width-stretch, y+stretch, stretch, height-2*stretch, "gui", eastCorner);
		ImageRenderer.renderImage(x, y+height-stretch, stretch, stretch, "gui", southwestCorner);
		ImageRenderer.renderImage(x+stretch, y+height-stretch, width-2*stretch, stretch, "gui", southCorner);
		ImageRenderer.renderImage(x+width-stretch, y+height-stretch, stretch, stretch, "gui", southeastCorner);

	}

}
