package de.wolfi.tnttracer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by root on 29.06.2017.
 */
public class ListenerInstance implements Listener{
    private final Player player;
private final HashMap<UUID,Tnt> explosions;
    private final ArrayList<ArmorStand> stands;
    private final BukkitTask task;
    public ListenerInstance(Player player){
        this.player = player;
        this.explosions = new HashMap<UUID, Tnt>();
        this.stands = new ArrayList<ArmorStand>();
        this.task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(),()->{ for(TNTPrimed tnt : player.getWorld().getEntitiesByClass(TNTPrimed.class)){
            if(!this.explosions.containsKey(tnt.getUniqueId())) this.explosions.put(tnt.getUniqueId(),new Tnt(tnt));
        }},1,1);
    }

    /*    @EventHandler(priority = EventPriority.MONITOR)
        public void onPrime(Worlde e){
            if(e.getBlock().getType() == Material.TNT && e.getChangedType() == Material.AIR){
                System.out.println("Dingsen found");
                this.explosions.put(e.getEntity().getUniqueId(),new Tnt((TNTPrimed) e.getEntity()));
            }
        }*/
    @EventHandler(priority = EventPriority.MONITOR)
    public void onExplode(EntityExplodeEvent e){
        if(e.getEntityType() == EntityType.PRIMED_TNT){


            explosions.get(e.getEntity().getUniqueId()).stop();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e){
        if(e.getPlayer().equals(this.player) && e.getMessage().equals("/show")){
            e.setCancelled(true);
            this.task.cancel();
            for(Tnt tnt : explosions.values()){
                ArmorStand a = (ArmorStand) tnt.getLast().getWorld().spawnEntity(tnt.getLast(),EntityType.ARMOR_STAND);
                a.setGravity(false);
                a.setVisible(false);
                a.setSmall(true);
                a.setHelmet(new ItemStack(Material.TNT));
                stands.add(a);
                tnt.spawnParticles();
            }
        }else if(e.getPlayer().equals(this.player) && e.getMessage().equals("/hide")){
            e.setCancelled(true);
            for(Tnt tnt : explosions.values())
                tnt.stopParticles();
            for(ArmorStand stand : stands)
                stand.remove();
            HandlerList.unregisterAll(this);
        }else if(e.getPlayer().equals(this.player) && e.getMessage().equals("/block")){
            for(Tnt tnt : explosions.values()){
                for(Location loc : tnt.getLocations()){
                    loc.getBlock().setType(Material.QUARTZ_BLOCK);
                }
                tnt.getLast().getBlock().setType(Material.DIAMOND_BLOCK);
            }
        }
    }
}
