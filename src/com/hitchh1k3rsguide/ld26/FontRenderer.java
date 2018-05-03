package com.hitchh1k3rsguide.ld26;

public class FontRenderer {

	public static void renderText(int x, int y, String text)
	{
		renderTextWithColor(x, y, text, 255, 255, 255);
	}

	public static void renderTextWithColor(int x, int y, String text, int red, int green, int blue)
	{
		text = text.toUpperCase();
		for(int i = 0; i < text.length(); i++)
		{
			int code = text.codePointAt(i);
			if(code == 32)
			{
				x += 11;
				continue;
			}
			if(code == 46)
				code = 36;
			if(code == 44)
				code = 37;
			if(code == 33)
				code = 39;
			if(code == 45)
				code = 42;
			if(code == 40)
				code = 43;
			if(code == 41)
				code = 44;
			if(code == 34)
				code = 45;
			if(code == 39)
				code = 46;
			if(code == 48)
				code = 35;
			if(code == 63)
				code = 38;
			if(code == 58)
				code = 40;
			if(code == 59)
				code = 41;
			if(code >= 49 && code <= 57)
				code -= 23;
			if(code >= 65 && code <= 90)
				code -= 65;
			if(code > 47)
				code = 47;
			ImageRenderer.renderImageWithColor(x, y, 20, 28, "font", code, red, green, blue);
			x += 22;
		}
	}

	public static void renderMiniText(int x, int y, String text)
	{
		text = text.toUpperCase();
		for(int i = 0; i < text.length(); i++)
		{
			int code = text.codePointAt(i);
			if(code == 32)
			{
				x += 11;
				continue;
			}
			if(code == 46)
				code = 36;
			if(code == 44)
				code = 37;
			if(code == 33)
				code = 39;
			if(code == 45)
				code = 42;
			if(code == 40)
				code = 43;
			if(code == 41)
				code = 44;
			if(code == 34)
				code = 45;
			if(code == 39)
				code = 46;
			if(code == 48)
				code = 35;
			if(code == 63)
				code = 38;
			if(code == 58)
				code = 40;
			if(code == 59)
				code = 41;
			if(code >= 49 && code <= 57)
				code -= 23;
			if(code >= 65 && code <= 90)
				code -= 65;
			if(code > 47)
				code = 47;
			ImageRenderer.renderImageWithColor(x, y, 10, 14, "font", code, 255, 255, 255);
			x += 11;
		}
	}

	public static void renderTextWithWidth(int x, int y, int width, String text) {
		int initX = x;
		String[] words = text.split(" ");
		for(int i = 0; i < words.length; i++)
		{
			if(x+(words[i].length()*11) > initX+width)
			{
				x = initX;
				y += 16;
			}
			renderMiniText(x, y, words[i]);
			x += words[i].length()*11+6;
		}
	}

	public static void renderBigTextWithWidth(int x, int y, int width, String text) {
		int initX = x;
		String[] words = text.split(" ");
		for(int i = 0; i < words.length; i++)
		{
			if(x+(words[i].length()*22) > initX+width)
			{
				x = initX;
				y += 30;
			}
			renderTextWithColor(x+4, y+4, words[i], 0, 0, 0);
			renderText(x, y, words[i]);
			x += words[i].length()*22+11;
		}
	}
}
