package capen.dev.dhsAttractions.SDD;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.SDD.Enums.*;
import capen.dev.dhsAttractions.SDD.SDDManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SDDBlocks {
/*
Ensure blocks get put in the startup state when ride begins
Define all block functions for each section (use enum section for details)
Correlate functions with redstone block locations
 */
    //connecting other classes
    public static Main main;
    public static SDDManager sddManager;
    //Enums
    private static Block1 b1state;
    private static Block2 b2state;
    private static Block3 b3state;
    private static Block4 b4state;
    private static Block5 b5state;
    private static Block6 b6state;
    private static Global glstate;

    //Define startup blocks
    public SDDBlocks(Main main, SDDManager rrcManager) {
        this.main = main;
        this.sddManager = rrcManager;
        b1state = Block1.restraintsunlocked;
        b2state = Block2.occupied;
        b3state = Block3.clear;
        b4state = Block4.clear;
        b5state = Block5.clear;
        b6state = Block6.holding;
        glstate = Global.waiting;
    }

    //Define setBlock and getBlock functions for each section
    public static void setblock1(final Block1 b1state) {
        SDDBlocks.b1state = b1state;
    }
    public static Block1 getblock1() { return b1state; }
    public static void setblock2(final Block2 b2state) {
        SDDBlocks.b2state = b2state;
    }
    public static Block2 getblock2() { return b2state; }
    public static void setblock3(final Block3 b3state) {
        SDDBlocks.b3state = b3state;
    }
    public static Block3 getblock3() { return b3state; }
    public static void setblock4(final Block4 b4state) {
        SDDBlocks.b4state = b4state;
    }
    public static Block4 getblock4() { return b4state; }
    public static void setblock5(final Block5 b5state) {
        SDDBlocks.b5state = b5state;
    }
    public static Block5 getblock5() { return b5state; }
    public static void setblock6(final Block6 b6state) {
        SDDBlocks.b6state = b6state;
    }
    public static Block6 getblock6() { return b6state; }
    public static void setglobal(final Global glstate) {
        SDDBlocks.glstate = glstate;
    }
    public static Global getglobal() { return glstate; }

    //spawnTrains
    public void spawnTrains() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2626);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void killTrains() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2636);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //block1
    //openGates
    public void OpenGates() {
        if (this.b1state == Block1.restraintsunlocked) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2628);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            this.setblock1(Block1.restraintsgatesunlocked);
        }
    }
    //closeGates
    public void CloseGates() {
        if (this.b1state == Block1.restraintsgatesunlocked) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2630);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            this.setblock1(Block1.restraintsunlocked);
        }
    }
    //closeRestraints
    public void CloseRestraint() {
        if (this.b1state == Block1.restraintsunlocked) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2632);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            this.setblock1(Block1.holding);
        }
    }
    //loadDispatch
    public void LoadDispatch() {
        if (this.b1state == Block1.holding || this.b2state == Block2.clear) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2634);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            //Sensor will move block1 into Standby mode
        }
    }

    //block2
    //launchSequence
    public static void Launch() {
        if (SDDBlocks.getblock3() == Block3.clear && SDDBlocks.getblock2() == Block2.holding) {
            final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2640);
            loc.getBlock().setType(Material.REDSTONE_BLOCK);
            sddManager.msgOps("Block 2 launch sequence started");
        } else {
            //TEMP TEMP TEMP TEMP
            new BukkitRunnable() {
                @Override
                public void run() {
                    int sec = 7;
                    if (sec == 0) {
                        this.cancel();
                        SDDBlocks.setblock2(Block2.holding);
                        SDDBlocks.Launch();
                        return;
                    }
                    if (sec == 1 && SDDBlocks.getblock3() != Block3.clear) {
                        sddManager.msgOps("Block 2 launch waiting");
                        sec = 6;
                    }
                    --sec;
                }
            }.runTaskTimer((Plugin) main.getInstance(), 0L, 20L);
        }
    }
    //release3
    public void Release2() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2640);
        loc.getBlock().setType(Material.RED_STAINED_GLASS);
    }
    //hold2
    public void Hold2() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2640);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void Release3() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2654);
        loc.getBlock().setType(Material.RED_STAINED_GLASS);
    }
    //hold3
    public void Hold3() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2654);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //release4
    public void Release4() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2670);
        loc.getBlock().setType(Material.RED_STAINED_GLASS);
    }
    //hold4
    public void Hold4() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2670);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //release5
    public void Release5() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2684);
        loc.getBlock().setType(Material.RED_STAINED_GLASS);
    }
    //hold5
    public void Hold5() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -293, 36, 2684);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //release6
    public void OpenRestraints() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2696);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    //hold6
    public void Dispatch6() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 36, 2698);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
}
