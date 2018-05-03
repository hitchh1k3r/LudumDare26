package com.hitchh1k3rsguide.ld26;

public class Particle {

	private String sheet;
	private int red, green, blue, width, height;
	private double x, y, gravity, xVel, yVel, life, startLife;
	private int[] ids;

	public Particle(String sheet, int[] ids, int x, int y, int width, int height, double gravity, double xVel, double yVel, double life, int red, int green, int blue)
	{
		this.sheet = sheet;
		this.ids = ids;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.gravity = gravity;
		this.xVel = xVel;
		this.yVel = yVel;
		this.startLife = life;
		this.life = life;
	}

	public boolean update(double time)
	{
		this.life -= time;
		if(this.life < 0)
			return true;
		this.x += (this.xVel * time);
		this.y -= (this.yVel * time) - (this.gravity * time * time);
		this.yVel = this.yVel - (this.gravity * time);
		return false;
	}

	public void draw()
	{
		int index = (int)(((this.startLife-this.life)/this.startLife) * (double)this.ids.length);
		if(index < 0)
			index = 0;
		if(index >= this.ids.length)
			index = this.ids.length-1;
		ImageRenderer.renderImageWithColor((int)this.x, (int)this.y, this.width, this.height, this.sheet, this.ids[index], this.red, this.green, this.blue);
	}

}
