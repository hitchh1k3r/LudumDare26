package com.hitchh1k3rsguide.ld26;

import java.util.ArrayList;
import java.util.Random;

public class ParticleManager {

	private ArrayList<Particle> particles = new ArrayList<Particle>(0);
	private Random rand = new Random();

	public void update(double time)
	{
		for(int i = 0; i < particles.size(); i++)
		{
			if(particles.get(i) != null)
			{
				if(particles.get(i).update(time))
					particles.set(i, null);
			}
		}
	}

	public void draw()
	{
		for(int i = 0; i < particles.size(); i++)
		{
			if(particles.get(i) != null)
			{
				particles.get(i).draw();
			}
		}
	}

	public void addParticle(int x, int y, String type)
	{
		Particle newPart = null;
		if(type == "lavaSplash")
		{
			newPart = new Particle("particles", new int[]{16, 17, 18, 19, 20, 21, 22, 23, 24}, x, y, 32, 32, 1000, (rand.nextDouble()*200)-100, (rand.nextDouble()*300)+200, 1, 255, 255, 255);
		}
		else if(type == "bossHit")
		{
			newPart = new Particle("particles", new int[]{32, 33, 34, 35}, x, y, 32, 32, 500, (rand.nextDouble()*100)-100, (rand.nextDouble()*200)+100, 0.5, 255, 255, 255);
		}
		else if(type == "lavaLeftGush")
		{
			newPart = new Particle("particles", new int[]{16, 17, 18, 19, 20, 21, 22, 23, 24}, x, y, 32, 32, 500, -80-(rand.nextDouble()*50), 600+(rand.nextDouble()*100), 3.5, 255, 255, 255);
		}
		else if(type == "lavarightGush")
		{
			newPart = new Particle("particles", new int[]{16, 17, 18, 19, 20, 21, 22, 23, 24}, x, y, 32, 32, 500, 80+(rand.nextDouble()*50), 600+(rand.nextDouble()*100), 3.5, 255, 255, 255);
		}
		else if(type == "magic")
		{
			newPart = new Particle("particles", new int[]{64, 65, 66, 67}, x, y, 32, 32, 0, (rand.nextDouble()*80)-40, (rand.nextDouble()*80)-40, 1, 255, 255, 255);
		}
		
		if(newPart != null)
		{
			int p = -1;
			for(int i = 0; i < particles.size(); i++)
			{
				if(particles.get(i) == null)
				{
					p = i;
					break;
				}
			}
			if(p == -1)
				p = particles.size();
			particles.ensureCapacity(p+1);
			while(particles.size() <= p)
				particles.add(null);
			particles.set(p, newPart);
		}
	}

}
