package de.wolfi.tnttracer;

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
