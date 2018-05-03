package com.hitchh1k3rsguide.ld26;

public interface IScene {

	public void onKey(int key, boolean isDown);
	public void onMouseMove(int x, int y);
	public void onMouseClick(int x, int y, int button, boolean isDown);
	public IScene update(double time);
	public void draw();

}
