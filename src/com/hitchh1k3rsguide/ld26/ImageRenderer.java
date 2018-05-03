package com.hitchh1k3rsguide.ld26;

import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class ImageRenderer {

	private static HashMap<String, Texture> spriteSheets = new HashMap<String, Texture>();
	private static HashMap<String, ISpriteSheet> spriteSheetUVMaps = new HashMap<String, ISpriteSheet>();
	private static String boundSheet = "";
	private static boolean drawingFlip = false;

	public static void init()
	{
		try
		{
			spriteSheets.put("font", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/CharacterMap.png")));
			spriteSheets.get("font").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("font", new BasicSpriteSheet(5, 7, 12, 64, 32));
			spriteSheets.put("gui", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/GUI.png")));
			spriteSheets.get("gui").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("gui", new BasicSpriteSheet(4, 4, 64, 256, 256));
			spriteSheets.put("bossFightBG", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/BossBG.png")));
			spriteSheets.get("bossFightBG").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("bossFightBG", new BasicSpriteSheet(320, 240, 1, 512, 512));
			spriteSheets.put("boss", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/DragonBody.png")));
			spriteSheets.get("boss").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("boss", new BasicSpriteSheet(48, 240, 10, 512, 512));
			spriteSheets.put("dude", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/Hero.png")));
			spriteSheets.get("dude").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("dude", new BasicSpriteSheet(48, 48, 5, 256, 256));
			spriteSheets.put("particles", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/Particles.png")));
			spriteSheets.get("particles").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("particles", new BasicSpriteSheet(16, 16, 16, 256, 256));
			spriteSheets.put("adventureStrip", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/AdventureSites.png")));
			spriteSheets.get("adventureStrip").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("adventureStrip", new BasicSpriteSheet(192, 192, 5, 1024, 512));
			spriteSheets.put("adventureGUI", TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("resources/AdventureGUI.png")));
			spriteSheets.get("adventureGUI").setTextureFilter(GL11.GL_NEAREST);
			spriteSheetUVMaps.put("adventureGUI", new BasicSpriteSheet(32, 192, 8, 256, 256));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void renderFlippedImage(int x, int y, int width, int height, String spriteSheet, int spriteId)
	{
		drawingFlip = true;
		renderImageWithColor(x, y, width, height, spriteSheet, spriteId, 255, 255, 255);
		drawingFlip = false;
	}

	public static void renderImage(int x, int y, int width, int height, String spriteSheet, int spriteId)
	{
		renderImageWithColor(x, y, width, height, spriteSheet, spriteId, 255, 255, 255);
	}

	public static void renderImageWithColor(int x, int y, int width, int height, String spriteSheet, int spriteId, int red, int green, int blue)
	{
		if(boundSheet != spriteSheet)
			spriteSheets.get(spriteSheet).bind();
		ISpriteSheet UVMap = spriteSheetUVMaps.get(spriteSheet);
		GL11.glColor3f((float)red/255, (float)green/255, (float)blue/255);
		float u1, u2, v1, v2;
		if(drawingFlip)
			u1 = UVMap.getRight(spriteId);
		else
			u1 = UVMap.getLeft(spriteId);
		if(drawingFlip)
			u2 = UVMap.getLeft(spriteId);
		else
			u2 = UVMap.getRight(spriteId);
		v1 = UVMap.getTop(spriteId);
		v2 = UVMap.getBottom(spriteId);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(u1, v2);
			GL11.glVertex2d(x, y+height);
			GL11.glTexCoord2f(u2, v2);
			GL11.glVertex2d(x+width, y+height);
			GL11.glTexCoord2f(u2, v1);
			GL11.glVertex2d(x+width, y);
			GL11.glTexCoord2f(u1, v1);
			GL11.glVertex2d(x, y);
		}
		GL11.glEnd();
	}

}
