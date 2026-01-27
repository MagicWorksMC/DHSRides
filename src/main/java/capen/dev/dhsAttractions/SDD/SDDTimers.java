package capen.dev.dhsAttractions.SDD;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.SDD.Enums.Block1;
import capen.dev.dhsAttractions.SDD.Enums.Block2;
import capen.dev.dhsAttractions.SDD.Enums.Block3;
import capen.dev.dhsAttractions.SDD.Enums.Block6;
import capen.dev.dhsAttractions.SDD.SDDBlocks;
import capen.dev.dhsAttractions.SDD.SDDManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SDDTimers {
    /*
    LoadingTimer
    UnloadingTimer
    LaunchTimer
    checkTimer
     */
    //connects the other classes
    private static Main main;
    private static SDDManager sddManager;
    public static SDDBlocks blocks;

    private int secLoad;
    private int secUnload;
    private int secCheck;

    private BukkitRunnable loadingTask;
    private BukkitRunnable unloadingTask;
    private BukkitRunnable checkTask;


    public SDDTimers(Main main, SDDManager sddManager, SDDBlocks blocks){
        this.main = main;
        this.sddManager = sddManager;
        this.blocks = blocks;
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
        secLoad = 60;
        loadingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (secLoad == 1) {
                    sddManager.removeAllLoad();
                    this.cancel();
                    return;
                }
                if (secLoad == 5) {
                    if (SDDBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        blocks.LoadDispatch();
                        sddManager.msgOps("Block1 dispatched");
                    } else {
                        secLoad = 9;
                        sddManager.msgOps("Block1 waiting block2");
                    }
                }
                if (secLoad == 10) {
                    //closing restraints
                    blocks.CloseRestraint();
                    SDDBlocks.setblock1(Block1.holding);
                }
                    //closing gates
                if (secLoad == 15) {
                    SDDBlocks.setblock1(Block1.restraintsunlocked);
                    blocks.CloseGates();
                }
                if (secLoad == 16) {
                    sddManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                    //change later
                }
                    //open gates
                if (secLoad == 50) {
                    SDDBlocks.setblock1(Block1.restraintsgatesunlocked);
                    blocks.OpenGates();
                }
                if (secLoad == 60) {
                    sddManager.msgOps("Block1 timer started");
                }
                if (secLoad <= 50 && secLoad >= 17) {
                    sddManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + (secLoad - 15) + ChatColor.WHITE + " seconds!");
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
        secUnload = 36;
        unloadingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (secUnload == 1) {
                    this.cancel();
                    sddManager.msgLoad(ChatColor.WHITE + "Prepare to Board!");
                    return;
                }
                if (secUnload == 2) {
                    sddManager.msgLoad(ChatColor.WHITE + "Train is apporaching station in " + ChatColor.AQUA + (secUnload - 1) + ChatColor.WHITE + " second!");
                }
                //testing next block
                if (secUnload == 6) {
                    if (SDDBlocks.getblock1() == Block1.clear) {
                        //change later
                        blocks.Dispatch6();
                        sddManager.msgOps("block6 dispatched");

                    } else {
                        secUnload = 10;
                        sddManager.msgOps("block6 waiting block1");
                    }

                }
                //open restraints
                if (secUnload == 34) {
                    blocks.OpenRestraints();
                    SDDBlocks.setblock6(Block6.restraints);
                }
                if (secUnload == 35) {
                    sddManager.msgOps("block6 timer started");
                }
                if (secUnload <= 70 && secUnload >= 3 && (SDDBlocks.getblock1() == Block1.dispatch || SDDBlocks.getblock1() == Block1.clear)) {
                    sddManager.msgLoad(ChatColor.WHITE + "Train is apporaching station in " + ChatColor.AQUA + (secUnload - 1) + ChatColor.WHITE + " seconds!");
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
                    sddManager.rideWaiting();
                    return;
                }
                if (secCheck == 2) {
                    if (sddManager.checkInAreaSize() != 0) {
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
