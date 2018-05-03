package com.hitchh1k3rsguide.ld26;

public class MyMath {

	public static boolean BoxCollision(double x1, double right1, double y1, double bottom1, double x2, double right2, double y2, double bottom2)
	{
		double w1 = (right1 - x1);
		double w2 = (right2 - x2);
		double h1 = (bottom1 - y1);
		double h2 = (bottom2 - y2);
	    w2 += x2;
	    w1 += x1;
	    if (x2 > w1 || x1 > w2) return false;
	    h2 += y2;
	    h1 += y1;
	    if (y2 > h1 || y1 > h2) return false;
	    return true;
	}

	public static int countChar(String text, char c) {
		int count = 0;
		for(int i = 0; i < text.length(); i++)
			if(text.charAt(i) == c)
				count++;
		return count;
	}

}
