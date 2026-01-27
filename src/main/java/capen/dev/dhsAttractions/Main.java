package capen.dev.dhsAttractions;

import capen.dev.dhsAttractions.RRC.RRCBlocks;
import capen.dev.dhsAttractions.RRC.RRCCommandManager;
import capen.dev.dhsAttractions.RRC.RRCManager;
import capen.dev.dhsAttractions.RRC.RRCTimers;
import capen.dev.dhsAttractions.SDD.SDDBlocks;
import capen.dev.dhsAttractions.SDD.SDDCommandManager;
import capen.dev.dhsAttractions.SDD.SDDManager;
import capen.dev.dhsAttractions.SDD.SDDTimers;
import capen.dev.dhsAttractions.TOT.TOTBlocks;
import capen.dev.dhsAttractions.TOT.TOTCommandManager;
import capen.dev.dhsAttractions.TOT.TOTManager;
import capen.dev.dhsAttractions.TOT.TOTTimers;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main main;
    public static RRCManager rrcManager;
    public static RRCBlocks rrcBlocks;
    public static RRCTimers rrcTimers;
    public static RRCCommandManager rrcCommandManager;

    public static SDDManager sddManager;
    public static SDDBlocks sddBlocks;
    public static SDDTimers sddTimers;
    public static SDDCommandManager sddCommandManager;

    public static TOTManager totManager;
    public static TOTBlocks totBlocks;
    public static TOTTimers totTimers;
    public static TOTCommandManager totCommandManager;

    @Override
    public void onEnable() {
        getLogger().info("onEnable started");

        main = this;

        // Step 1: Initialize Blocks
        rrcBlocks = new RRCBlocks(this, rrcManager);
        sddBlocks = new SDDBlocks(this, sddManager);
        totBlocks = new TOTBlocks(this, totManager);

        // Step 2: Temporarily assign null or delay rrcTimers creation
        // Step 3: Create rrcManager (without rrcTimers yet)
        rrcManager = new RRCManager(this, rrcBlocks, null);
        sddManager = new SDDManager(this, sddBlocks, null);
        totManager = new TOTManager(this, totBlocks, null);// TEMP null

        // Step 4: Create rrcTimers and inject rrcManager
        rrcTimers = new RRCTimers(this, rrcManager);
        sddTimers = new SDDTimers(this, sddManager, sddBlocks);
        totTimers = new TOTTimers(this, totManager, totBlocks);

        // Step 5: Update rrcManager with the now-complete rrcTimers
        rrcManager.setTimers(rrcTimers); // Add a setter in RRCManager
        sddManager.setTimers(sddTimers);
        totManager.setTimers(totTimers);


        // Step 6: Continue setup
        rrcCommandManager = new RRCCommandManager(rrcTimers, rrcBlocks);
        sddCommandManager = new SDDCommandManager(sddTimers, sddBlocks);
        totCommandManager = new TOTCommandManager(totTimers, totBlocks);
        Bukkit.getPluginManager().registerEvents(rrcManager, this);
        Bukkit.getPluginManager().registerEvents(sddManager, this);
        Bukkit.getPluginManager().registerEvents(totManager, this);
        getCommand("dhsride").setExecutor(new DHSRideCommand(rrcManager, rrcCommandManager, sddManager, sddCommandManager, totManager, totCommandManager));
        saveDefaultConfig();

        getLogger().info("onEnable completed");
    }
    public static Main getInstance() {
        return main;
    }
}
