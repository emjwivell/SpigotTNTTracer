package de.wolfi.tnttracer;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by root on 29.06.2017.
 */
public class Main extends JavaPlugin{

    private static Main main;
    @Override
    public void onEnable() {
        main = this;
        this.getConfig().addDefault("tickSpeed",5);
        this.saveDefaultConfig();
        System.out.println("[TNTTracer] Plugin enabled!");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getPluginManager().registerEvents(new ListenerInstance((Player) sender),this);
        ((Player) sender).sendMessage("Start tracking...");
        return true;
    }

    @Override
    public void onDisable() {
        System.out.println("[TNTTracer] Plugin disabled!");
    }

    public static Main getInstance(){
        return main;
    }

    public static long getTickSpeed() {
        return Main.getInstance().getConfig().getLong("tickSpeed");
    }
}
