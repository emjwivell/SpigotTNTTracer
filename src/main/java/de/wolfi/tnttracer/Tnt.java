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
    private final BukkitTask task;
    private BukkitTask particle;

    private final ArrayList<Location> locs;
    private Location lastLoc;
    public Tnt(TNTPrimed tnt){
        this.tntPrimed = tnt;
        this.locs = new ArrayList<Location>();
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(),this,0,Main.getTickSpeed());
    }

    public Location getLast(){
        return this.lastLoc;
    }
    public void stop(){
        this.task.cancel();
        this.lastLoc = this.tntPrimed.getLocation();
    }

    public void stopParticles(){
        this.particle.cancel();
    }
    public void spawnParticles(){
        this.particle = Bukkit.getScheduler().runTaskTimer(Main.getInstance(),()->{for(Location loc : locs) loc.getWorld().spawnParticle(Particle.FLAME,loc,0);},10,10);
    }

    public void run() {
        locs.add(this.tntPrimed.getLocation());
    }
}
