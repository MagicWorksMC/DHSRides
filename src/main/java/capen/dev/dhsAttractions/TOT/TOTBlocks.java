package capen.dev.dhsAttractions.TOT;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.TOT.Enums.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TOTBlocks {
/*
Ensure blocks get put in the startup state when ride begins
Define all block functions for each section (use enum section for details)
Correlate functions with redstone block locations

 */
    //connecting other classes
    public static Main main;
    public static TOTManager totManager;
    //Enums
    private static Block1a b1astate;
    private static Block1b b1bstate;
    private static Block2 b2state;
    private static Block3 b3state;
    private static Block99 b99state;
    private static Global glstate;

    //Define startup blocks
    public TOTBlocks(Main main, TOTManager rrcManager) {
        this.main = main;
        this.totManager = rrcManager;
        b1astate = Block1a.restraintsunlocked;
        b1bstate = Block1b.restraintsunlocked;
        b2state = Block2.occupied;
        b3state = Block3.clear;
        glstate = Global.waiting;
    }

    //Define setBlock and getBlock functions for each section
    public static void setblock1a(final Block1a b1astate) {
        TOTBlocks.b1astate = b1astate;
    }
    public static Block1a getblock1() { return b1astate; }
    public static void setblock1b(final Block1b b1bstate) {
        TOTBlocks.b1bstate = b1bstate;
    }
    public static Block1b getblock1b() { return b1bstate; }
    public static void setblock2(final Block2 b2state) {
        TOTBlocks.b2state = b2state;
    }
    public static Block2 getblock2() { return b2state; }
    public static void setblock3(final Block3 b3state) {
        TOTBlocks.b3state = b3state;
    }
    public static Block3 getblock3() { return b3state; }
    public static void setblock99(final Block99 b99state) {
        TOTBlocks.b99state = b99state;
    }
    public static Block99 getblock99() { return b99state; }
    public static void setglobal(final Global glstate) {
        TOTBlocks.glstate = glstate;
    }
    public static Global getglobal() { return glstate; }

    //spawnTrains
    public void spawnTrains() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -1, 42, 2183);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void killTrains() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -6, 42, 2184);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //block1
    //openGates
    public void doorOpenA() {
       final Location loc = new Location(Bukkit.getWorld("Parks"), -1, 42, 2188);
       loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void doorOpenB() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 9, 42, 2188);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void closeRestraintsA() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 9, 42, 2188);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void closeRestraintsB() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 42, 2192);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void doorCloseA() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -1, 42, 2188);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void doorCloseB() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 9, 42, 2190);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //Check
    public void dispatchA1() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 6, 43, 2191);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //
    public void openDoorHallwayA() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 45, 2186);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void openDoorDemA() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 45, 2184);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void dispatchA2() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 6, 43, 2191);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void openDoorHallwayB() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 9, 42, 2186);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void openSlidingDoorUnload() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 34, 2191);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void openDoorUnload() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 34, 2189);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void openRestraints() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 34, 2195);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void unloadDispatch() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 6, 35, 2191);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //Check
    public void dispatchB1() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 0, 43, 2182);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //
    public void dispatchB2() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), 0, 43, 2182);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //Animations
    public void boardingA() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2197);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void hallwayA5() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2195);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void hallwayA1() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2193);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void demA4() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2189);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void demA1() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2187);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void bottomA() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2191);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void boardingB() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2197);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void hallwayB5() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2195);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void hallwayB1() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2193);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void demB4() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2189);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void demB1() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2187);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void bottomB() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -3, 46, 2191);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
}
