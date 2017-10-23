package de.wolfi.tnttracer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

/**
 * Created by root on 29.06.2017.
 */
public class Tnt implements Runnable{

    private final TNTPrimed tntPrimed;
    private final Thread task;
    private BukkitTask particle;

    private final ArrayList<Location> locs;
    private Location lastLoc;
    public Tnt(TNTPrimed tnt){
        this.tntPrimed = tnt;
        this.locs = new ArrayList<Location>();
        this.task = new Thread(this);
        this.task.start();
    }

    public Location getLast(){
        return this.lastLoc;
    }
    public void stop(){
        this.task.interrupt();
        this.lastLoc = this.tntPrimed.getLocation();
    }

    public void stopParticles(){
        this.particle.cancel();
    }
    public void spawnParticles(){
        this.particle = Bukkit.getScheduler().runTaskTimer(Main.getInstance(),()->{
            Location prev = null;
            for(Location loc : locs){
                if(prev != null) {
                    if (loc.distanceSquared(prev) > Math.pow(Main.getMinDistance(), 2))

                        loc.getWorld().spawnParticle(Particle.FLAME, loc, 0);
                        prev = loc;
                    } else{  loc.getWorld().spawnParticle(Particle.FLAME, loc, 0);
                            prev = loc;
                }

            }
        },10,10);
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            locs.add(this.tntPrimed.getLocation());
            try {
                Thread.sleep(Main.getTickSpeed());
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public ArrayList<Location> getLocations() {
        return locs;
    }
}
