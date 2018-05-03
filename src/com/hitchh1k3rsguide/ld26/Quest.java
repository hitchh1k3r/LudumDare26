package com.hitchh1k3rsguide.ld26;

import java.util.Random;

import org.lwjgl.input.Mouse;

import com.hitchh1k3rsguide.ld26.gui.Button;
import com.hitchh1k3rsguide.ld26.gui.GUIBox;

public class Quest implements IScene {

	private boolean beep1, beep2;
	private int[] moveHistory = new int[4];
	private int lastId = 0;
	private int selectedId = 2;
	private int placesTraveled = 0;
	private boolean inLocationMenu = false;
	private Button button1, button2, button3;
	private Random rand = new Random();
	private boolean responseShowing = false;
	private GUIBox responseBox = new GUIBox(20, 48, 610, 224);
	private Button responseButton = new Button(510, 252, 120, "Next");
	private String responseText = "";
	private boolean enterLair = false;
	private boolean showHead = false;

	public Quest()
	{
		button1 = new Button(16, 340, 608, "");
		button2 = new Button(16, 385, 608, "");
		button3 = new Button(16, 430, 608, "");
	}

	@Override
	public void onKey(int key, boolean isDown) {
		
	}

	@Override
	public void onMouseMove(int x, int y) {
		
	}

	private String getDescription(int Id)
	{
		switch(Id)
		{
		case 1:
			return "Witches pretend to be magical but their charms cant even ward off potato blight, let alone something more destuctive.";
		case 2:
			return "This town is well protected unlike the outlying villages.";
		case 3:
			return "Protection of his subects should be the lord's priority."; // This lord has a duty to protect his subjects.
		case 4:
			return "They say the priest has an ear to the gods but what good are words when action is required.";
		case 5:
			return "This dragon must be stopped before it does more damage.";
		}
		return "";
	}

	private String getEffectAndText(int Id, int button)
	{
		showHead = false;
		String text = "";
		double odds = rand.nextDouble() * (Buffs.getBuffb("luckCharm") ? 2 : 1);
		int origButton = button;
		if(Id != 2)
			button = (odds >= (button == 0 ? 0 : (button == 1 ? 0.25 : 0.5)) ? button : 4);
		int health = Buffs.getBuffi("totalHP");
		if(button == 4)
		{
			Buffs.setBuff("totalHP", health-2);
			if(Id == 5)
				Buffs.setBuff("totalHP", health-2);
		}
		if(button == 1 && Id != 2 && Id != 5)
			Buffs.setBuff("totalHP", health+4);
		if(button == 2 && Id != 2 && Id != 5)
			Buffs.setBuff("potions", Buffs.getBuffi("potions")+1);
		switch(Id)
		{
		case 1:
			switch(button)
			{ // witch
			case 1:
				text = "YOU SPREAD BAD RUMORS ABOUT THE WITCH. THIS GIVES YOU NEW RESOLVE TO MOVE FOWARD."; // +4 HP
				break;
			case 2:
				text = "YOU STEAL A BREW FROM THE WITCH'S HUT. THIS WILL HELP WHEN LIFE IS LOW."; // POTION
				break;
			case 3:
				Buffs.setBuff("canDoubleJump", true);
				showHead = true;
				text = "YOU KILL THE WITCH AND TAKE HER HEAD ALONG WITH HER SHOES."; // ELF SHOES
				break;
			case 4:
				text = "THIS HUT UNERVES YOU AND YOU TURN BACK. THIS DAMAGES YOUR RESOLVE."; //-2 hp
				break;
			}
			break;
		case 2:
			switch(button)
			{ // town
			case 1:
				Buffs.setBuff("luckCharm", true);
				text = "YOU TRADE YOUR LAST POTATO CROP FOR A MAGIC TALISMAN. YOU FEEL YOUR LUCK CHANGING.";
				break;
			case 2:
				Buffs.setBuff("heroPower", 2);
				Buffs.setBuff("weapon", 1);
				text = "YOU TRADE YOUR ENGAGEMENT RING FOR A SWORD.";
				break;
			case 3:
				Buffs.setBuff("heroFireDamage", 2);
				Buffs.setBuff("armor", 1);
				text = "YOU TRADE YOUR ENGAGEMENT RING FOR ARMOR.";
				break;
			}
			break;
		case 3:
			switch(button)
			{ // lords hold
			case 1:
				text = "YOU DISGRACE THE KING. THIS GIVES YOU NEW RESOLVE TO MOVE FOWARD."; //+4HP
				break;
			case 2:
				text = "YOU STEAL FOOD FROM THE KING'S KITCHEN. THIS WILL HELP WHEN LIFE IS LOW."; //POTION
				break;
			case 3:
				Buffs.setBuff("shield", true);
				showHead = true;
				text = "YOU KILL THE LORD AND TAKE HIS HEAD ALONG WITH HIS SHIELD."; // SHIELD
				break;
			case 4:
				text = "THE GUARDS PREVENT YOU FROM ENTERING THE HOLDFAST. THIS DAMAGES YOUR RESOLVE."; //-2HP
				break;
			}
			break;
		case 4:
			switch(button)
			{ // temple
			case 1:
				text = "THE PRIEST RECOILS IN SHAME. THIS GIVES YOU NEW RESOLVE TO MOVE FOWARD."; //+4HP
				break;
			case 2:
				text = "YOU FIND AN OLD POTATO WINE. THIS WILL HELP WHEN LIFE IS LOW."; //POTION
				break;
			case 3:
				Buffs.setBuff("heroPower", 2);
				Buffs.setBuff("weapon", 2);
				showHead = true;
				text = "YOU KILL THE PRIEST AND TAKE HIS HEAD ALONG WITH HIS SPEAR.";
				break;
			case 4:
				text = "YOU GET SCOLDED BY THE PRIEST AND TURN BACK. THIS DAMAGES YOUR RESOLVE."; // -2HP
				break;
			}
			break;
		case 5:
			switch(button)
			{ // dragons cave
			case 1:
				text = "This is the place of that vile beast.";
				break;
			case 2:
				Buffs.setBuff("heroFireDamage", 1);
				Buffs.setBuff("heroLavaDamage", 2);
				Buffs.setBuff("armor", 2);
				text = "YOU WOUND THE TROLL AND TAKE HIS IRON ARMOR.";
				break;
			case 3:
				Buffs.setBuff("heroPower", 3);
				text = "YOU FOOL THE GIANT AND TAKE HIS MEAD. THIS FILLS YOU WITH STRENGTH.";
				break;
			case 4:
				if(origButton == 2)
					text = "THE TROLL WOUNDS YOU.";
				else
					text = "THE GIANT WOUNDS YOU.";
				break;
			}
			break;
		}
		return text;
	}

	private String getButtonText(int Id, int button)
	{
		switch(Id)
		{
		case 1:
			switch(button)
			{ // witch
			case 1:
				return "Badmouth the witch";
			case 2:
				return "Ransack the witch's hut";
			case 3:
				return "Kill the witch";
			}
		case 2:
			switch(button)
			{ // town
			case 1:
				return "Trade crop for Talisman";
			case 2:
				return "Trade ring for Sword";
			case 3:
				return "Trade ring for Armor";
			}
		case 3:
			switch(button)
			{ // king (lords holdfast)
			case 1:
				return "Disgrace the lord";
			case 2:
				return "Steal food from the lord";
			case 3:
				return "Kill the lord";
			}
		case 4:
			switch(button)
			{ // temple
			case 1:
				return "Insult the priest";
			case 2:
				return "Ransack the temple";
			case 3:
				return "Kill the priest";
			}
		case 5:
			switch(button)
			{ // wiggle-butt ditherdargons who likes to darv's lair
			case 1:
				return "Sneak to dragon's lair";
			case 2:
				return "Fight mountain troll";
			case 3:
				return "Fight mountain giant";
			}
		}
		return "";
	}

	private boolean isInHistory(int Id)
	{
		for(int i = 0; i < 4; i++)
			if(moveHistory[i] == selectedId)
				return true;
		return false;
	}
	
	@Override
	public void onMouseClick(int x, int y, int button, boolean isDown) {
		if(responseShowing && isDown && button == 0)
		{
			if(responseButton.isMouseOver(x, y))
			{
				if(selectedId == 5)
					enterLair = true;
				else
				{
					inLocationMenu = false;
					responseShowing = false;
					lastId = selectedId;
					moveHistory[placesTraveled] = selectedId;
					placesTraveled += 1;
					if(placesTraveled >= 4)
						selectedId = 5;
					else
					{
						boolean good = true;
						while(good)
						{
							selectedId += 1;
							selectedId %= 5;
							if(!isInHistory(selectedId))
								good = false;
						}
					}
				}
			}
		}
		else if(inLocationMenu && isDown && button == 0)
		{
			if(button1.isMouseOver(x, y))
			{
				Sounds.playSound("select");
				responseShowing = true;
				responseText = getEffectAndText(selectedId, 1);
			}
			if(button2.isMouseOver(x, y))
			{
				Sounds.playSound("select");
				responseShowing = true;
				responseText = getEffectAndText(selectedId, 2);
			}
			if(button3.isMouseOver(x, y))
			{
				Sounds.playSound("select");
				responseShowing = true;
				responseText = getEffectAndText(selectedId, 3);
			}
		}
		else if(isDown && button == 0)
		{
			if(x >= 576 && y >= 64 && y <= 192)
			{ // click right
				Sounds.playSound("select");
				boolean good = true;
				while(good)
				{
					selectedId += 1;
					selectedId %= 5;
					if(!isInHistory(selectedId))
						good = false;
				}
			}
			if(x >= 192 && x <= 256 && y >= 64 && y <= 192)
			{ // click left
				Sounds.playSound("select");
				boolean good = true;
					while(good)
					{
						selectedId += 4;
						selectedId %= 5;
						if(!isInHistory(selectedId))
							good = false;
					}
			}
			if(x >= 256 && x <= 576 && y >= 0 && y <= 384)
			{ // click location
				Sounds.playSound("select");
				inLocationMenu = true;
				button1.setText(getButtonText(selectedId, 1));
				button2.setText(getButtonText(selectedId, 2));
				button3.setText(getButtonText(selectedId, 3));
			}
		}
	}

	@Override
	public IScene update(double time) {
		if(enterLair)
			return new BossFight(false);
		if(responseShowing)
		{
			responseButton.update();
		}
		else if(inLocationMenu)
		{
			button1.update();
			button2.update();
			button3.update();
		}
		return null;
	}

	private void renderMainMenu()
	{
		ImageRenderer.renderImage(-192, -64, 384, 384, "adventureStrip", lastId);

		ImageRenderer.renderImage(224, -64, 384, 384, "adventureStrip", selectedId);
				
		ImageRenderer.renderFlippedImage(576, 0, 64, 384, "adventureGUI", 0);
		if(placesTraveled < 3)
		{
			if(Mouse.getX() >= 576 && 480-Mouse.getY() >= 64 && 480-Mouse.getY() <= 192)
			{
				if(!beep1)
					Sounds.playSound("hover");
				beep1 = true;
				ImageRenderer.renderFlippedImage(576, 0, 64, 384, "adventureGUI", 2);
			}
			else
			{
				beep1 = false;
				ImageRenderer.renderFlippedImage(576, 0, 64, 384, "adventureGUI", 1);
			}
		}
		ImageRenderer.renderImage(192, 0, 64, 384, "adventureGUI", 0);
		if(placesTraveled < 3)
		{
			if(Mouse.getX() >= 192 && Mouse.getX() <= 256 && 480-Mouse.getY() >= 64 && 480-Mouse.getY() <= 192)
			{
				if(!beep2)
					Sounds.playSound("hover");
				beep2 = true;
				ImageRenderer.renderImage(192, 0, 64, 384, "adventureGUI", 2);
			}
			else
			{
				beep2 = false;
				ImageRenderer.renderImage(192, 0, 64, 384, "adventureGUI", 1);
			}
		}

		ImageRenderer.renderImage(176, 232, 96, 96, "dude", Buffs.getBuffi("armor"));
		ImageRenderer.renderImage(224, 200, 96, 96, "dude", 10+(5*Buffs.getBuffi("weapon")));
		if(Buffs.getBuffb("shield"))
			ImageRenderer.renderFlippedImage(104, 200, 96, 96, "dude", 3);
		else
			ImageRenderer.renderFlippedImage(96, 200, 96, 96, "dude", 10);
		if(showHead)
			ImageRenderer.renderFlippedImage(190, 216, 96, 96, "dude", 4);

		if(placesTraveled >= 1)
			ImageRenderer.renderImage(0, 320, 160, 160, "adventureStrip", moveHistory[0]);
		if(placesTraveled >= 2)
			ImageRenderer.renderImage(160, 320, 160, 160, "adventureStrip", moveHistory[1]);
		if(placesTraveled >= 3)
			ImageRenderer.renderImage(320, 320, 160, 160, "adventureStrip", moveHistory[2]);
		if(placesTraveled >= 4)
			ImageRenderer.renderImage(480, 320, 160, 160, "adventureStrip", moveHistory[3]);
	}
	
	private void renderLocation()
	{
		ImageRenderer.renderImage(0, 0, 384, 384, "adventureStrip", selectedId);
		FontRenderer.renderTextWithWidth(400, 112, 224, getDescription(selectedId));
		button1.draw();
		button2.draw();
		button3.draw();
	}

	private void drawResponse()
	{
		responseBox.draw();
		FontRenderer.renderBigTextWithWidth(30, 58, 580, responseText);
		responseButton.draw();
	}

	@Override
	public void draw() {
		if(inLocationMenu)
		{
			renderLocation();
			if(responseShowing)
			drawResponse();
		}
		else
			renderMainMenu();
	}

}
