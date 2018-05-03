package com.hitchh1k3rsguide.ld26;

public class BasicSpriteSheet implements ISpriteSheet {

	float uGap, vGap;
	int xEl;
	
	public BasicSpriteSheet(int elementWidth, int elementHeight, int elementsInRow, int sheetWidth, int sheetHeight)
	{
		uGap = (float)elementWidth/(float)sheetWidth;
		vGap = (float)elementHeight/(float)sheetHeight;
		xEl = elementsInRow;
	}

	@Override
	public float getLeft(int element) {
		return uGap*(float)(element % xEl);
	}

	@Override
	public float getRight(int element) {
		return uGap*((float)(element % xEl)+1);
	}

	@Override
	public float getTop(int element) {
		return vGap*(float)(element / xEl);
	}

	@Override
	public float getBottom(int element) {
		return vGap*((float)(element / xEl)+1);
	}

}
