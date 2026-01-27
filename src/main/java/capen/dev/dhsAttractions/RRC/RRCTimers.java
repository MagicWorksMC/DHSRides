package capen.dev.dhsAttractions.RRC;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.RRC.Enums.Block1;
import capen.dev.dhsAttractions.RRC.Enums.Block2;
import capen.dev.dhsAttractions.RRC.Enums.Block7;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RRCTimers {
    /*
    PreshowCountdown
    LoadingTimer
    UnloadingTimer
    LaunchTimer
    checkTimer
     */
    //connects the other classes
    private static Main main;
    private static RRCManager rrcManager;

    private int secLoad;
    private int secUnload;
    private int secCheck;

    private BukkitRunnable loadingTask;
    private BukkitRunnable unloadingTask;
    private BukkitRunnable checkTask;


    public RRCTimers(Main main, RRCManager rrcManager){
        this.main = main;
        this.rrcManager = rrcManager;
    }

    /*
    preshowCountdown
     */

    /*
    loadingTimer
    35 sec timer overall
    30 sec restraintsgatesunlocked
    15 sec restraintsunlocked
    10 sec hold (lock restraints)
    if time = 2 checks block2 if good sends if not sets sec to 9
    1 sec cancel
     */
    public void loadingTimer() {
        secLoad = 35;
        loadingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (secLoad == 1) {
                    rrcManager.removeAllLoad();
                    this.cancel();
                    return;
                }
                if (secLoad == 5) {
                    if (RRCBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        final Location loc = new Location(Bukkit.getWorld("Parks"), -125, 41, 2238);
                        loc.getBlock().setType(Material.REDSTONE_BLOCK);
                        rrcManager.msgOps("Block1 dispatched");
                    } else {
                        secLoad = 9;
                        rrcManager.msgOps("Block1 waiting block2");
                    }
                }
                if (secLoad == 10) {
                    //closing restraints
                    final Location loc = new Location(Bukkit.getWorld("Parks"), -123, 41, 2238);
                    loc.getBlock().setType(Material.REDSTONE_BLOCK);
                    RRCBlocks.setblock1(Block1.holding);
                }
                    //closing gates
                if (secLoad == 15) {
                    RRCBlocks.setblock1(Block1.restraintsunlocked);
                    final Location loc = new Location(Bukkit.getWorld("Parks"), -121, 41, 2238);
                    loc.getBlock().setType(Material.REDSTONE_BLOCK);
                }
                if (secLoad == 16) {
                    rrcManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                    //change later
                }
                    //open gates
                if (secLoad == 30) {
                    RRCBlocks.setblock1(Block1.restraintsgatesunlocked);
                    final Location loc = new Location(Bukkit.getWorld("Parks"), -119, 41, 2238);
                    loc.getBlock().setType(Material.REDSTONE_BLOCK);
                }
                if (secLoad == 35) {
                    rrcManager.msgOps("Block1 timer started");
                }
                if (secLoad <= 30 && secLoad >= 17) {
                    rrcManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + (secLoad - 15) + ChatColor.WHITE + " seconds!");
                    //change later
                }
                --secLoad;
            }
        };
        loadingTask.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void killLoadingTimer() {
        if (loadingTask != null) {
            loadingTask.cancel();
            loadingTask = null; // Optional: clear the reference
        }
    }

    /*
    unloadingTimer
    40 sec tiemr overall
    30 sec unlock
    6 sec checks next block
    2 send message
    1 prepare to board and cancel
     */
    public void unloadingTimer() {
        secUnload = 40;
        unloadingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (secUnload == 1) {
                    this.cancel();
                    rrcManager.msgLoad(ChatColor.WHITE + "Prepare to Board!");
                    return;
                }
                if (secUnload == 2) {
                    rrcManager.msgLoad(ChatColor.WHITE + "Train is apporaching station in " + ChatColor.AQUA + (secUnload - 1) + ChatColor.WHITE + " second!");
                }
                //testing next block
                if (secUnload == 6) {
                    if (RRCBlocks.getblock1() == Block1.clear) {
                        //change later
                        final Location loc = new Location(Bukkit.getWorld("Parks"), -163, 41, 2306);
                        loc.getBlock().setType(Material.REDSTONE_BLOCK);
                        rrcManager.msgOps("block7 dispatched");

                    } else {
                        secUnload = 10;
                        rrcManager.msgOps("block7 waiting block1");
                    }

                }
                //open restraints
                if (secUnload == 30) {
                    final Location loc = new Location(Bukkit.getWorld("Parks"), -165, 41, 2306);
                    loc.getBlock().setType(Material.REDSTONE_BLOCK);
                    RRCBlocks.setblock7(Block7.restraints);
                }
                if (secUnload == 35) {
                    rrcManager.msgOps("block7 timer started");
                }
                if (secUnload <= 70 && secUnload >= 3 && (RRCBlocks.getblock1() == Block1.dispatch || RRCBlocks.getblock1() == Block1.clear)) {
                    rrcManager.msgLoad(ChatColor.WHITE + "Train is apporaching station in " + ChatColor.AQUA + (secUnload - 1) + ChatColor.WHITE + " seconds!");
                    //change later
                }
                --secUnload;
            }
        };
        unloadingTask.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void killUnloadingTimer() {
        if (unloadingTask != null) {
            unloadingTask.cancel();
            unloadingTask = null; // Optional: clear the reference
        }
    }

    /*
    launchTimer
    need dan for this
     */

    /*
    checkTimer
    5 sec timer overall
    2 sec check if player is in ride if not continue
    1 sec cancel
     */
    public void checkTimer() {
        secCheck = 5;
        checkTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (secCheck == 1) {
                    this.cancel();
                    rrcManager.rideWaiting();
                    return;
                }
                if (secCheck == 2) {
                    if (rrcManager.checkInAreaSize() != 0) {
                        secCheck = 5;
                    }
                }
                --secCheck;
            }
        };
        checkTask.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void killcheckTimer() {
        if (checkTask != null) {
            checkTask.cancel();
            checkTask = null; // Optional: clear the reference
        }
    }
}
