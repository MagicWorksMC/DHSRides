package capen.dev.dhsAttractions.RRC;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.RRC.Enums.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RRCBlocks {
/*
Ensure blocks get put in the startup state when ride begins
Define all block functions for each section (use enum section for details)
Correlate functions with redstone block locations
 */
    //connecting other classes
    public static Main main;
    public static RRCManager rrcManager;
    //Enums
    private static Block1 b1state;
    private static Block2 b2state;
    private static Block3 b3state;
    private static Block4 b4state;
    private static Block5 b5state;
    private static Block6 b6state;
    private static Block7 b7state;
    private static Global glstate;

    //Define startup blocks
    public RRCBlocks(Main main, RRCManager rrcManager) {
        this.main = main;
        this.rrcManager = rrcManager;
        b1state = Block1.restraintsunlocked;
        b2state = Block2.clear;
        b3state = Block3.clear;
        b4state = Block4.clear;
        b5state = Block5.holding;
        b6state = Block6.holding;
        b7state = Block7.holding;
        glstate = Global.waiting;
    }

    //Define setBlock and getBlock functions for each section
    public static void setblock1(final Block1 b1state) {
        RRCBlocks.b1state = b1state;
    }
    public static Block1 getblock1() { return b1state; }
    public static void setblock2(final Block2 b2state) {
        RRCBlocks.b2state = b2state;
    }
    public static Block2 getblock2() { return b2state; }
    public static void setblock3(final Block3 b3state) {
        RRCBlocks.b3state = b3state;
    }
    public static Block3 getblock3() { return b3state; }
    public static void setblock4(final Block4 b4state) {
        RRCBlocks.b4state = b4state;
    }
    public static Block4 getblock4() { return b4state; }
    public static void setblock5(final Block5 b5state) {
        RRCBlocks.b5state = b5state;
    }
    public static Block5 getblock5() { return b5state; }
    public static void setblock6(final Block6 b6state) {
        RRCBlocks.b6state = b6state;
    }
    public static Block6 getblock6() { return b6state; }
    public static void setblock7(final Block7 b7state) {
        RRCBlocks.b7state = b7state;
    }
    public static Block7 getblock7() { return b7state; }
    public static void setglobal(final Global glstate) {
        RRCBlocks.glstate = glstate;
    }
    public static Global getglobal() { return glstate; }

    //spawnTrains
    public void spawnTrains() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -117, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void killTrains() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -127, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //block1
    //openGates
    public void OpenGates() {
        if (this.b1state == Block1.restraintsunlocked) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -119, 41, 2238);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            this.setblock1(Block1.restraintsgatesunlocked);
        }
    }
    //closeGates
    public void CloseGates() {
        if (this.b1state == Block1.restraintsgatesunlocked) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -121, 41, 2238);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            this.setblock1(Block1.restraintsunlocked);
        }
    }
    //closeRestraints
    public void CloseRestraint() {
        if (this.b1state == Block1.restraintsunlocked) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -123, 41, 2238);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            this.setblock1(Block1.holding);
        }
    }
    //loadDispatch
    public void LoadDispatch() {
        if (this.b1state == Block1.holding || this.b2state == Block2.clear) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -125, 41, 2238);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            //Sensor will move block1 into Standby mode
        }
    }

    //block2
    //launchSequence
    public static void Launch() {
        if (RRCBlocks.getblock3() == Block3.clear && RRCBlocks.getblock2() == Block2.holding) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -156, 13, 2287);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            rrcManager.msgOps("Block 2 launch sequence started");
        } else {
            //TEMP TEMP TEMP TEMP
            new BukkitRunnable() {
                @Override
                public void run() {
                    int sec = 7;
                    if (sec == 0) {
                        this.cancel();
                        RRCBlocks.setblock2(Block2.holding);
                        RRCBlocks.Launch();
                        return;
                    }
                    if (sec == 1 && RRCBlocks.getblock3() != Block3.clear) {
                        rrcManager.msgOps("Block 2 launch waiting");
                        sec = 6;
                    }
                    --sec;
                }
            }.runTaskTimer((Plugin) main.getInstance(), 0L, 20L);
        }
    }
    //release3
    public static void release3() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -184, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //hold3
    public static void hold3() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -186, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //release4
    public static void Release4() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -205, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //hold4
    public static void Hold4() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -207, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //release5
    public static void Release5() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -119, 41, 2306);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //hold5
    public static void Hold5() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -117, 41, 2306);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //release6
    public static void Release6() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -139, 41, 2306);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //hold6
    public static void Hold6() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -141, 41, 2306);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }

    //block7
    //dispatch7
    public static void Dispatch7() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -163, 41, 2306);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
        RRCBlocks.setblock7(Block7.dispatch);
    }
    //openRestraints
    public static void OpenRestraints() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -165, 41, 2306);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
}
