package com.hitchh1k3rsguide.ld26;

import java.util.Random;

import org.lwjgl.input.Keyboard;

public class BossFight implements IScene {

	private static final double HERO_INV_TIME = 1.75;

	private double dragonHeight = 480;
	private double heroX = 48, heroY = 240;
	private double velX, velY;
	private boolean faceLeft = false;
	private double attackCoolDown = 0;
	private boolean jumpReset = false;
	private double bossHitCooldown = 0;
	private boolean dragonFaceRight = false;
	private ParticleManager particles = new ParticleManager();
	private int health;
	private int monsterHealth = 24;
	private double heroHitCooldown = 0;
	private double heroInvincibleCooldown = 0;
	private double dragonAICooldown = 0;
	private int dragonAIState = 1;
	private double dragonSpeed = 100;
	private Fireball[] fireballs = new Fireball[20];
	private double introCooldown = 20;
	private int introPulses = 0;
	private Random rand = new Random();
	private boolean splishSplash = false;
	private boolean dragonHit = false;
	private boolean hardCore;
	private double timeFactor = 1;
	private boolean ending = false;

	public BossFight(boolean hardCore)
	{
		this.hardCore = hardCore;
		if(hardCore)
			dragonSpeed += 50;
		health = Buffs.getBuffi("totalHP");
		Sounds.playMusic("boss");
	}

	@Override
	public void onKey(int key, boolean isDown)
	{
		if(heroHitCooldown == 0 && key == Keyboard.KEY_LEFT)
			velX = (isDown ? -175 : 0) * (Buffs.getBuffb("canDoubleJump") ? 1.5 : 1);
		if(heroHitCooldown == 0 && key == Keyboard.KEY_RIGHT)
			velX = (isDown ? 175 : 0) * (Buffs.getBuffb("canDoubleJump") ? 1.5 : 1);
		if(heroHitCooldown == 0 && key == Keyboard.KEY_S && (velY == 0 || (Buffs.getBuffb("canDoubleJump") && jumpReset)) && isDown)
		{
			if(velY != 0)
			{
				jumpReset = false;
				for(int i = 0; i < 3; i++)
				{
					particles.addParticle((int)heroX+10, (int)heroY+48, "magic");
				}
				for(int i = 0; i < 3; i++)
				{
					particles.addParticle((int)heroX+22, (int)heroY+48, "magic");
				}
			}
			velY = 600;
			Sounds.playSound("jump");
		}
		if(heroHitCooldown == 0 && key == Keyboard.KEY_A && isDown && attackCoolDown == 0)
		{
			faceLeft = true;
			Sounds.playSound("attack");
			attackCoolDown = 0.3;
			if(Buffs.getBuffi("heroPower") == 3)
			{
				for(int i = 0; i < 5; i++)
				{
					particles.addParticle((int)(heroX+16-56), (int)heroY+24, "magic");
				}
			}
		}
		if(heroHitCooldown == 0 && key == Keyboard.KEY_D && isDown && attackCoolDown == 0)
		{
			faceLeft = false;
			Sounds.playSound("attack");
			attackCoolDown = 0.3;
			if(Buffs.getBuffi("heroPower") == 3)
			{
				for(int i = 0; i < 5; i++)
				{
					particles.addParticle((int)(heroX+16+56), (int)heroY+24, "magic");
				}
			}
		}
	}

	@Override
	public void onMouseMove(int x, int y)
	{
		
	}

	@Override
	public void onMouseClick(int x, int y, int button, boolean isDown)
	{
		
	}

	private void addFireball(Fireball ball)
	{
		for(int i = 0; i < 20; i++)
			if(fireballs[i] == null)
			{
				fireballs[i] = ball;
				return;
			}
	}

	private void refaceDragon()
	{
		if(heroX < 288)
			dragonFaceRight = false;
		else
			dragonFaceRight = true;
	}
	
	private void lavaPulse()
	{
		Sounds.playSound("lavasplash");
		for(int i = 0; i < 5; i++)
		{
			particles.addParticle(260, 420, "lavaSplash");
		}
		for(int i = 0; i < 10; i++)
		{
			particles.addParticle(304, 400, "lavaSplash");
		}
		for(int i = 0; i < 5; i++)
		{
			particles.addParticle(348, 420, "lavaSplash");
		}
	}

	private void dragonAI(double time)
	{
		if(dragonAICooldown > 0)
		{
			dragonAICooldown -= time;
			if(dragonAICooldown < 0 && dragonAIState != 7)
			{
				dragonAICooldown = 0;
				if(this.heroY > this.dragonHeight+16)
				{
					refaceDragon();
					dragonAIState = 2;
				}
				else if(this.heroY < this.dragonHeight-80)
				{
					refaceDragon();
					dragonAIState = 1;
					if(dragonHeight > 475)
					{
						lavaPulse();
						addFireball(new Fireball(304, 470, -30-(rand.nextDouble()*20), 500+(rand.nextDouble()*10), 350));
						addFireball(new Fireball(304, 470, 30+(rand.nextDouble()*20), 500+(rand.nextDouble()*10), 350));
						addFireball(new Fireball(304, 470, -70-(rand.nextDouble()*20), 650+(rand.nextDouble()*10), 500));
						addFireball(new Fireball(304, 470, 70+(rand.nextDouble()*20), 650+(rand.nextDouble()*10), 500));
						addFireball(new Fireball(304, 470, -10-(rand.nextDouble()*20), 400+(rand.nextDouble()*10), 250));
						addFireball(new Fireball(304, 470, 10+(rand.nextDouble()*20), 400+(rand.nextDouble()*10), 250));
					}
				}
				else if((this.heroX < 288 && this.dragonFaceRight) || (this.heroX > 288 && !this.dragonFaceRight))
				{
					dragonAIState = 3;
					
				}
				else
				{
					if(dragonAIState != 4)
						Sounds.playSound("lavadeath");
					dragonAICooldown = 50/dragonSpeed;
					dragonAIState = 4;
				}
				if(dragonAIState == 4)
				{
					addFireball(new Fireball((dragonFaceRight?352:272-16), dragonHeight, (dragonFaceRight?150:-150), 0, 0));
				}
			}
		}
		else if(dragonAIState == 4)
		{
			dragonAIState = 0;
		}
		if(dragonAIState == 1)
		{
			this.dragonHeight -= dragonSpeed*time;
			if(this.heroY > this.dragonHeight-16)
			{
				dragonAIState = 0;
			}
		}
		if(dragonAIState == 2)
		{
			this.dragonHeight += dragonSpeed*time;
			if(this.heroY < this.dragonHeight-16)
			{
				dragonAIState = 0;
			}
		}
		if(dragonAIState == 6)
		{
			this.dragonHeight -= dragonSpeed*time*3;
			if(this.dragonHeight < -480)
			{
				dragonAIState = 7;
				splishSplash = false;
				dragonAICooldown = 3;
			}
		}
		if(dragonAIState == 7 && dragonAICooldown <= 0)
		{
			this.dragonHeight += dragonSpeed*time*6;
			if(this.dragonHeight > 480)
			{
				dragonAIState = 0;
				dragonAICooldown = 5;
			}
			if(this.dragonHeight > 40 && !splishSplash)
			{
				splishSplash = true;
				lavaPulse();
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(50+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(55+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(60+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(65+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(70+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(75+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(80+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(85+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(90+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
				addFireball(new Fireball(304, 470, (dragonFaceRight?1:-1)*(95+(rand.nextDouble()*50)), 450+(rand.nextDouble()*250), 500));
			}
		}
		if(dragonAIState == 3)
		{
			this.dragonHeight += dragonSpeed*time*1.25;
			if(this.dragonHeight > 480)
			{
				dragonAIState = 0;
				lavaPulse();
				if((hardCore || monsterHealth <= 12) && dragonHit)
				{
					if(!hardCore || monsterHealth > 12)
						dragonHit = false;
					if(dragonFaceRight)
						for(int i = 0; i < 30; i++)
							particles.addParticle(304, 470, "lavaRightGush");
					else
						for(int i = 0; i < 30; i++)
							particles.addParticle(304, 470, "lavaLeftGush");
					dragonAIState = 6;
					lavaPulse();
					addFireball(new Fireball(304, 470, -30-(rand.nextDouble()*20), 500+(rand.nextDouble()*10), 350));
					addFireball(new Fireball(304, 470, 30+(rand.nextDouble()*20), 500+(rand.nextDouble()*10), 350));
					addFireball(new Fireball(304, 470, -70-(rand.nextDouble()*20), 650+(rand.nextDouble()*10), 500));
					addFireball(new Fireball(304, 470, 70+(rand.nextDouble()*20), 650+(rand.nextDouble()*10), 500));
					addFireball(new Fireball(304, 470, -10-(rand.nextDouble()*20), 400+(rand.nextDouble()*10), 250));
					addFireball(new Fireball(304, 470, 10+(rand.nextDouble()*20), 400+(rand.nextDouble()*10), 250));
				}
			}
		}
		if(dragonAIState == 0 && dragonAICooldown <= 0)
		{
			dragonAICooldown = 10/dragonHeight;
		}
	}

	@Override
	public IScene update(double time)
	{
		if(ending)
			timeFactor *= Math.pow(Math.E, -2*time);
		time *= timeFactor;
		if(health <= 0 && Buffs.getBuffi("potions") > 0)
		{
			Buffs.setBuff("potions", (int)(Buffs.getBuffi("potions")-1));
			health = 6;
			for(int i = 0; i < 15; i++)
			{
				particles.addParticle((int)(heroX+16), (int)heroY+16, "magic");
			}
		}
		if((health <= 0 && Buffs.getBuffi("potions") == 0) || monsterHealth <= 0)
			ending = true;
		if(health <= 0 && timeFactor < 0.01)
			if(hardCore)
				return new TheEnd(7);
			else
				return new TheEnd(5);
		if(monsterHealth <= 0 && timeFactor < 0.01)
		{
			if(hardCore)
				return new TheEnd(6);
			else
			{
				Menu.beatGame();
				return new TheEnd(0);
			}
		}
		particles.update(time);
		for(int i = 0; i < 20; i++)
			if(fireballs[i] != null)
				if(fireballs[i].update(time))
					fireballs[i] = null;
		if(heroInvincibleCooldown > 0)
		{
			heroInvincibleCooldown -= time;
			if(heroInvincibleCooldown < 0)
				heroInvincibleCooldown = 0;
		}
		if(Buffs.getBuffb("shield"))
		{
			for(int i = 0; i < 20; i++)
				if(fireballs[i] != null)
				{
					double shieldOffset = -16;
					if(faceLeft)
						shieldOffset = 40;
					if(fireballs[i].collides(heroX+shieldOffset, heroX+shieldOffset+24, heroY+16, heroY+58))
					{
						for(int t = 0; t < 5; t++)
							particles.addParticle((int)fireballs[i].getOX(), (int)fireballs[i].getOY(), "lavaSplash");
						fireballs[i] = null;
						continue;
					}
				}
		}
		if(heroInvincibleCooldown <= 0)
		{
			for(int i = 0; i < 20; i++)
				if(fireballs[i] != null)
				{
					if(fireballs[i].collides(heroX+16, heroX+48, heroY+(Keyboard.isKeyDown(Keyboard.KEY_DOWN)?7:0), heroY+64))
					{
						heroInvincibleCooldown = HERO_INV_TIME;
						health -= Buffs.getBuffi("heroFireDamage");
						Sounds.playSound("hurt");
						for(int t = 0; t < 5; t++)
							particles.addParticle((int)fireballs[i].getOX(), (int)fireballs[i].getOY(), "lavaSplash");
						fireballs[i] = null;
					}
				}
		}
		double dragonSliming = 0;
		if(dragonAIState == 6 || dragonAIState == 7)
			dragonSliming = 32;
		if(!ending && heroHitCooldown == 0 && MyMath.BoxCollision(heroX+16, heroX+48, heroY+(Keyboard.isKeyDown(Keyboard.KEY_DOWN)?7:0), heroY+64, 272+dragonSliming, 368-dragonSliming, dragonHeight, dragonHeight+480))
		{
			heroHitCooldown = 0.3;
			heroInvincibleCooldown = HERO_INV_TIME;
			health -= Buffs.getBuffi("heroBumpDamage");
			Sounds.playSound("hurt");
			velY = 750;
			if(heroX > 288)
				velX = 500;
			else
				velX = -500;
		}
		if(!ending && attackCoolDown > 0)
		{
			if(bossHitCooldown == 0)
			{
				int weaponBoost = 0;
				if(Buffs.getBuffi("weapon") == 0)
					weaponBoost = -16;
				if(Buffs.getBuffi("weapon") == 2)
					weaponBoost = 32;
				double hitBoxCalc = 0;
				if(faceLeft)
					hitBoxCalc = heroX - 48 - weaponBoost;
				else
					hitBoxCalc = heroX + 64;
				double yOffset = 0;
				if(dragonAIState == 7)
					yOffset = 448;
				if(dragonFaceRight == false)
				{
					if(MyMath.BoxCollision(hitBoxCalc, hitBoxCalc+48+weaponBoost, heroY+32, heroY+48, 272+dragonSliming, 304+dragonSliming, dragonHeight+yOffset, dragonHeight+32+yOffset))
					{
						dragonAICooldown = 0;
						if(dragonAIState < 6)
							dragonAIState = 3;
						bossHitCooldown = 0.5;
						dragonSpeed += 10;
						dragonHit = true;
						Sounds.playSound("bosshit");
						monsterHealth -= Buffs.getBuffi("heroPower");
						for(int i = 0; i < 4; i++)
						{
							particles.addParticle(288, (int)dragonHeight+16, "bossHit");
						}
					}
				}
				if(dragonFaceRight == true)
				{
					if(MyMath.BoxCollision(hitBoxCalc, hitBoxCalc+48+weaponBoost, heroY+32, heroY+48, 336-dragonSliming, 368-dragonSliming, dragonHeight+yOffset, dragonHeight+32+yOffset))
					{
						dragonAICooldown = 0;
						if(dragonAIState < 6)
							dragonAIState = 3;
						dragonSpeed += 10*Buffs.getBuffi("heroPower");
						monsterHealth -= Buffs.getBuffi("heroPower");
						bossHitCooldown = 0.5;
						dragonHit = true;
						Sounds.playSound("bosshit");
						for(int i = 0; i < 4; i++)
						{
							particles.addParticle(352, (int)dragonHeight+16, "bossHit");
						}
					}
				}
			}
			attackCoolDown -= time;
			if(attackCoolDown < 0)
				attackCoolDown = 0;
		}
		if(bossHitCooldown > 0)
		{
			bossHitCooldown -= time;
			if(bossHitCooldown < 0)
				bossHitCooldown = 0;
		}
		double startY = heroY;
		if(introCooldown > 0)
		{
			introCooldown -= time*2;
			if(introCooldown < 15 && introPulses < 1)
			{
				introPulses = 1;
				lavaPulse();
			}
			if(introCooldown < 10 && introPulses < 2)
			{
				introPulses = 2;
				lavaPulse();
			}
			if(introCooldown < 7 && introPulses < 3)
			{
				introPulses = 3;
				lavaPulse();
			}
			if(introCooldown < 5 && introPulses < 4)
			{
				introPulses = 4;
				lavaPulse();
			}
			if(introCooldown < 4 && introPulses < 5)
			{
				introPulses = 5;
				lavaPulse();
			}
			if(introCooldown < 3 && introPulses < 6)
			{
				introPulses = 6;
				lavaPulse();
			}
			if(introCooldown < 2 && introPulses < 7)
			{
				introPulses = 7;
				lavaPulse();
			}
			if(introCooldown < 1 && introPulses < 8)
			{
				introPulses = 8;
				lavaPulse();
			}
			if(introCooldown < 0.07125 && introPulses < 9)
			{
				introPulses = 9;
				lavaPulse();
				refaceDragon();
			}
		}
		else
			dragonAI(time);
		double gravity = (Keyboard.isKeyDown(Keyboard.KEY_DOWN)?2000:1500)*(Buffs.getBuffb("canDoubleJump")?0.75:1);
		this.heroY -= (this.velY * time) - (gravity * time * time);
		this.velY = this.velY - (gravity * time);
		if(heroHitCooldown > 0 || heroY < 320 || (heroX < 176 || heroX > 400))
			this.heroX += this.velX * time;
		if(heroY > 320)
		{
			if(heroX < 176 || heroX > 400)
			{
				heroY = 320;
				if(velY < 0)
					velY = 0;
				jumpReset = true;
			}
		}
		if(heroX >= 176 && heroX <= 400 && heroY > 400)
		{
			health -= Buffs.getBuffi("heroLavaDamage");
			heroInvincibleCooldown = HERO_INV_TIME;
			heroHitCooldown = 0.3;
			Sounds.playSound("lavadeath");
			velY = 800;
			if(heroX > 288)
				velX = 250;
			else
				velX = -250;
			for(int i = 0; i < 10; i++)
			{
				particles.addParticle((int)heroX+32, 408, "lavaSplash");
			}
		}
		if(heroY < 32)
		{
			heroY = 32;
			velY = 0;
		}
		if(heroX < 16)
			heroX = 16;
		if(heroX > 560)
			heroX = 560;
		if(velX < 0)
			faceLeft = true;
		if(velX > 0)
			faceLeft = false;
		if(!Keyboard.isKeyDown(Keyboard.KEY_DOWN) && ((heroX > 48 && heroX < 240) || (heroX > 336 && heroX < 528)))
		{
			if(startY <= 192-64 && heroY > 192-64)
			{
				heroY = 192-64;
				velY = 0;
				jumpReset = true;
			}
			if(startY <= 288-64 && heroY > 288-64)
			{
				heroY = 288-64;
				velY = 0;
				jumpReset = true;
			}
		}
		if(heroHitCooldown > 0)
		{
			heroHitCooldown -= time;
			if(heroHitCooldown < 0)
			{
				heroHitCooldown = 0;
				velX = 0;
			}
		}
		if(health < 0)
			health = 0;
		if(monsterHealth < 0)
			monsterHealth = 0;
		return null;
	}

	@Override
	public void draw()
	{
		ImageRenderer.renderImage(0, 0, 640, 480, "bossFightBG", 0);
		if(dragonFaceRight)
			ImageRenderer.renderFlippedImage(272, (int)this.dragonHeight, 96, 480, "boss", ((dragonAIState == 6)?2:((dragonAIState == 7)?4:0))+((bossHitCooldown>0)?1:0));
		else
			ImageRenderer.renderImage(272, (int)this.dragonHeight, 96, 480, "boss", ((dragonAIState == 6)?2:((dragonAIState == 7)?4:0))+((bossHitCooldown>0)?1:0));
		if(health > 0)
		{
			if(heroInvincibleCooldown == 0 || (int)((heroInvincibleCooldown/HERO_INV_TIME)*6) % 2 == 1)
			{
				if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) && monsterHealth > 0)
					ImageRenderer.renderImage((int)heroX, (int)heroY, 96, 96, "dude", 5+Buffs.getBuffi("armor"));
				else
					ImageRenderer.renderImage((int)heroX, (int)heroY, 96, 96, "dude", Buffs.getBuffi("armor"));
				if(faceLeft)
				{
					if(attackCoolDown == 0)
						ImageRenderer.renderFlippedImage((int)heroX-80, (int)heroY-32, 96, 96, "dude", 10+(5*Buffs.getBuffi("weapon")));
					else if(attackCoolDown > 0.2)
						ImageRenderer.renderFlippedImage((int)heroX-80, (int)heroY-32, 96, 96, "dude", 11+(5*Buffs.getBuffi("weapon")));
					else if(attackCoolDown > 0.1)
						ImageRenderer.renderFlippedImage((int)heroX-80, (int)heroY-32, 96, 96, "dude", 12+(5*Buffs.getBuffi("weapon")));
					else
						ImageRenderer.renderFlippedImage((int)heroX-80, (int)heroY-32, 96, 96, "dude", 11+(5*Buffs.getBuffi("weapon")));
					if(Buffs.getBuffb("shield"))
						ImageRenderer.renderImage((int)heroX+40, (int)heroY-32, 96, 96, "dude", 3);
					else
						ImageRenderer.renderImage((int)heroX+48, (int)heroY-32, 96, 96, "dude", 10);
				}
				else
				{
					if(attackCoolDown == 0)
						ImageRenderer.renderImage((int)heroX+48, (int)heroY-32, 96, 96, "dude", 10+(5*Buffs.getBuffi("weapon")));
					else if(attackCoolDown > 0.2)
						ImageRenderer.renderImage((int)heroX+48, (int)heroY-32, 96, 96, "dude", 11+(5*Buffs.getBuffi("weapon")));
					else if(attackCoolDown > 0.1)
						ImageRenderer.renderImage((int)heroX+48, (int)heroY-32, 96, 96, "dude", 12+(5*Buffs.getBuffi("weapon")));
					else
						ImageRenderer.renderImage((int)heroX+48, (int)heroY-32, 96, 96, "dude", 11+(5*Buffs.getBuffi("weapon")));
					if(Buffs.getBuffb("shield"))
						ImageRenderer.renderFlippedImage((int)heroX-72, (int)heroY-32, 96, 96, "dude", 3);
					else
						ImageRenderer.renderFlippedImage((int)heroX-80, (int)heroY-32, 96, 96, "dude", 10);
				}
			}
		}
		particles.draw();
		for(int i = 0; i < 20; i++)
			if(fireballs[i] != null)
				fireballs[i].draw();
		ImageRenderer.renderImage(0, 0, 640, 480, "bossFightBG", 1);
		for(int i = 0; i < health; i++)
		{
			ImageRenderer.renderImage(32+(16*(i%12)), 416, 32, 32, "particles", (i>=12?49:48));
		}
		for(int i = health; i < 12; i++)
		{
			ImageRenderer.renderImage(32+(16*i), 416, 32, 32, "particles", 50);
		}
		for(int i = 0; i < monsterHealth; i++)
		{
			ImageRenderer.renderImage(416+(16*(i%12)), 416, 32, 32, "particles", (i>=12?52:51));
		}
		for(int i = monsterHealth; i < 12; i++)
		{
			ImageRenderer.renderImage(416+(16*i), 416, 32, 32, "particles", 53);
		}
	}
}
