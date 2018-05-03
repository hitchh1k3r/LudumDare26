package com.hitchh1k3rsguide.ld26;

import java.util.HashMap;

public class Buffs {

	private static HashMap<String, Boolean> boolBuffs = new HashMap<String, Boolean>();
	private static HashMap<String, Integer> intBuffs = new HashMap<String, Integer>();

	public static void reset()
	{
		boolBuffs.put("canDoubleJump", false);
		intBuffs.put("totalHP", 12);
		intBuffs.put("heroPower", 1);
		intBuffs.put("heroBumpDamage", 1);
		intBuffs.put("potions", 0);
		intBuffs.put("heroFireDamage", 3);
		intBuffs.put("heroLavaDamage", 3);
		boolBuffs.put("luckCharm", false);
		intBuffs.put("weapon", 0);
		intBuffs.put("armor", 0);
		boolBuffs.put("shield", false);
	}

	public static void setBuff(String key, int value)
	{
		intBuffs.put(key, value);
	}

	public static void setBuff(String key, boolean value)
	{
		boolBuffs.put(key, value);
	}

	public static int getBuffi(String key)
	{
		return intBuffs.get(key);
	}

	public static boolean getBuffb(String key)
	{
		return boolBuffs.get(key);
	}
}
