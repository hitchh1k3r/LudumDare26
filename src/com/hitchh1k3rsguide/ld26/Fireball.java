package com.hitchh1k3rsguide.ld26;

public class Fireball {

	private double animationTime = 0.1;
	private int animationIndex = 0;
	private double x, y, velX, velY, gravity;

	public Fireball(double x, double y, double velX, double velY, double gravity)
	{
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.gravity = gravity;
	}
	
	public double getOX()
	{
		return x + 16;
	}

	public double getOY()
	{
		return y + 16;
	}

	public boolean collides(double left, double right, double top, double bottom)
	{
		return MyMath.BoxCollision(x, x+32, y, y+32, left, right, top, bottom);
	}

	public boolean update(double time)
	{
		animationTime -= time;
		if(animationTime < 0)
		{
			animationTime = 0.05;
			animationIndex++;
			if(animationIndex >= 4)
				animationIndex = 0;
		}
		x += time*velX;
		this.y -= (this.velY * time) - (this.gravity * time * time);
		this.velY = this.velY - (this.gravity * time);
		if(x < -32 || x > 640 || y < -32 || y > 480)
			return true;
		return false;
	}

	public void draw()
	{
		ImageRenderer.renderImage((int)x, (int)y, 32, 32, "particles", animationIndex);
	}

}
