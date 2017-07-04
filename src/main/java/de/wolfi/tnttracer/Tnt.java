package de.wolfi.tnttracer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

/**
 * Created by root on 29.06.2017.
 */
public class Tnt implements Runnable{

    private final TNTPrimed tntPrimed;
    private final BukkitTask task;
    private final ArrayList<Location> locs;
    private Location lastLoc;
    public Tnt(TNTPrimed tnt){
        this.tntPrimed = tnt;
        this.locs = new ArrayList<Location>();
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(),this,Main.getTickSpeed(),Main.getTickSpeed());
    }

    public Location getLast(){
        return this.locs.get(this.locs.size()-1);
    }
    public void stop(){
        this.task.cancel();
        this.lastLoc = this.tntPrimed.getLocation();
    }

    public void run() {
        locs.add(this.tntPrimed.getLocation());
    }
}
