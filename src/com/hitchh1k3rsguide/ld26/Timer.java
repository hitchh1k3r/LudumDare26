package com.hitchh1k3rsguide.ld26;

import org.lwjgl.Sys;

public class Timer {

	private static double frameTime = 0;
	private static double lastFrame = 0;

	public static void startFrame()
	{
		if(lastFrame != 0)
			frameTime = ((double)Sys.getTime() - lastFrame) / (double)Sys.getTimerResolution();
		lastFrame = Sys.getTime();
	}

	public static double getFrameTime()
	{
		return frameTime;
	}

}
