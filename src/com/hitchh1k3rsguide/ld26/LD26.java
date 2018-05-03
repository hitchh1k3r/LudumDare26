package com.hitchh1k3rsguide.ld26;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.openal.SoundStore;

@SuppressWarnings("serial")
public class LD26 extends Applet {

	volatile Canvas display_parent;
	Thread gameThread;
	boolean running = false;
	IScene scene;
	Random rand = new Random();

	public void startLWJGL()
	{
		gameThread = new Thread()
		{
			public void run()
			{
				running = true;
				try
				{
					Display.setParent(display_parent);
					Display.create();
					initGL();
				}
				catch (LWJGLException e)
				{
					Display.destroy();
					e.printStackTrace();
				}
				gameLoop();
			}
		};
		gameThread.start();
	}

	public void stopLWJGL()
	{
		running = false;
		try
		{
			gameThread.join();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void init()
	{
		Sounds.init();
		setLayout(new BorderLayout());
		try
		{
			display_parent = new Canvas()
			{
				public final void addNotify()
				{
					super.addNotify();
					startLWJGL();
				}
				public final void removeNotify()
				{
					stopLWJGL();
					super.removeNotify();
				}
			};
			display_parent.setSize(getWidth(), getHeight());
			add(display_parent);
			display_parent.setFocusable(true);
			display_parent.requestFocus();
			display_parent.setIgnoreRepaint(true);
			setVisible(true);
		}
		catch (Exception e)
		{
			System.err.println(e);
			throw new RuntimeException("Unable to create display");
		}
	}

	public void destroy()
	{
		remove(display_parent);
		super.destroy();
	}

	protected void initGL()
	{
		ImageRenderer.init();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glOrtho(0, 640, 480, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslated(0.375, 0.375, 0.0);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	public void gameLoop()
	{
		scene = new Menu();
		while(running)
		{
			Timer.startFrame();
			Display.update();
			while(Keyboard.next())
			{
				scene.onKey(Keyboard.getEventKey(), Keyboard.getEventKeyState());
			}
			while(Mouse.next())
			{
				if(Mouse.getEventButton() == -1)
					scene.onMouseMove(Mouse.getX(), 480-Mouse.getY());
				else
					scene.onMouseClick(Mouse.getEventX(), 480-Mouse.getEventY(), Mouse.getEventButton(), Mouse.getEventButtonState());
			}
			IScene newScene = scene.update(Timer.getFrameTime());
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			scene.draw();
			if(newScene != null)
			{
				scene = newScene;
			}
			Display.sync(60);
			SoundStore.get().poll(0);
		}
		AL.destroy();
		Display.destroy();
	}

}