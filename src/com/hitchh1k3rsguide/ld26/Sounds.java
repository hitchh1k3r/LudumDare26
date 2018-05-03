package com.hitchh1k3rsguide.ld26;

import java.io.IOException;
import java.util.HashMap;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sounds {

	private static HashMap<String, Audio> sounds = new HashMap<String, Audio>();
	private static HashMap<String, Audio> music = new HashMap<String, Audio>();
	private static String playingSong = "";
	
	public static void init()
	{
		try {
			sounds.put("jump", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/Jump.wav")));
			sounds.put("hover", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/Hover.wav")));
			sounds.put("select", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/Select.wav")));
			sounds.put("attack", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/Attack.wav")));
			sounds.put("bosshit", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/BossHit.wav")));
			sounds.put("lavadeath", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/LavaDeath.wav")));
			sounds.put("lavasplash", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/LavaSplash.wav")));
			sounds.put("hurt", AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("resources/Hurt.wav")));
			music.put("menu", AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("resources/Title.ogg")));
			music.put("boss", AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("resources/Boss.ogg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void playSound(String sound)
	{
		if(sounds.containsKey(sound))
			sounds.get(sound).playAsSoundEffect(1.0f, 1.0f, false);
	}

	public static void playMusic(String sound)
	{
		if(music.containsKey(sound))
		{
			if(playingSong != "")
				music.get(playingSong).stop();
			playingSong = sound;
			music.get(sound).playAsMusic(1.0f, 1.0f, true);
		}
	}

	public static void stopMusic()
	{
		if(playingSong != "")
		{
			music.get(playingSong).stop();
			playingSong = "";
		}
	}

}
