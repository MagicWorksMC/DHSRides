package capen.dev.dhsAttractions.TOT;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.TOT.Enums.Block1;
import capen.dev.dhsAttractions.TOT.Enums.Block2;
import capen.dev.dhsAttractions.TOT.Enums.Block6;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TOTTimers {
    /*
    LoadingTimer
    UnloadingTimer
    LaunchTimer
    checkTimer
     */
    //connects the other classes
    private static Main main;
    private static TOTManager totManager;
    public static TOTBlocks blocks;

    private int secLoad;
    private int secUnload;
    private int secCheck;

    private BukkitRunnable loadingTask;
    private BukkitRunnable loading2Task;
    private BukkitRunnable tower1Task;
    private BukkitRunnable tower2Task;
    private BukkitRunnable unloadingTask;
    private BukkitRunnable checkTask;


    public TOTTimers(Main main, TOTManager totManager, TOTBlocks blocks){
        this.main = main;
        this.totManager = totManager;
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
                    totManager.removeAllLoad();
                    this.cancel();
                    return;
                }
                if (secLoad == 5) {
                    if (TOTBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        blocks.LoadDispatch();
                        totManager.msgOps("Block1 dispatched");
                    } else {
                        secLoad = 9;
                        totManager.msgOps("Block1 waiting block2");
                    }
                }
                if (secLoad == 10) {
                    //closing restraints
                    blocks.CloseRestraint();
                    TOTBlocks.setblock1(Block1.holding);
                }
                    //closing gates
                if (secLoad == 15) {
                    TOTBlocks.setblock1(Block1.restraintsunlocked);
                    blocks.CloseGates();
                }
                if (secLoad == 16) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                    //change later
                }
                    //open gates
                if (secLoad == 50) {
                    TOTBlocks.setblock1(Block1.restraintsgatesunlocked);
                    blocks.OpenGates();
                }
                if (secLoad == 60) {
                    totManager.msgOps("Block1 timer started");
                }
                if (secLoad <= 50 && secLoad >= 17) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + (secLoad - 15) + ChatColor.WHITE + " seconds!");
                    //change later
                }
                --secLoad;
            }
        };
        loadingTask.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void loading2Timer() {
        secLoad = 60;
        loading2Task = new BukkitRunnable() {
            @Override
            public void run() {
                if (secLoad == 1) {
                    totManager.removeAllLoad();
                    this.cancel();
                    return;
                }
                if (secLoad == 5) {
                    if (TOTBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        blocks.LoadDispatch();
                        totManager.msgOps("Block1 dispatched");
                    } else {
                        secLoad = 9;
                        totManager.msgOps("Block1 waiting block2");
                    }
                }
                if (secLoad == 10) {
                    //closing restraints
                    blocks.CloseRestraint();
                    TOTBlocks.setblock1(Block1.holding);
                }
                //closing gates
                if (secLoad == 15) {
                    TOTBlocks.setblock1(Block1.restraintsunlocked);
                    blocks.CloseGates();
                }
                if (secLoad == 16) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                    //change later
                }
                //open gates
                if (secLoad == 50) {
                    TOTBlocks.setblock1(Block1.restraintsgatesunlocked);
                    blocks.OpenGates();
                }
                if (secLoad == 60) {
                    totManager.msgOps("Block1 timer started");
                }
                if (secLoad <= 50 && secLoad >= 17) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + (secLoad - 15) + ChatColor.WHITE + " seconds!");
                    //change later
                }
                --secLoad;
            }
        };
        loading2Task.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void killLoadingTimer() {
        if (loadingTask != null) {
            loadingTask.cancel();
            loadingTask = null; // Optional: clear the reference
        }
    }
    public void killLoading2Timer() {
        if (loading2Task != null) {
            loading2Task.cancel();
            loading2Task = null; // Optional: clear the reference
        }
    }
    /*
        -> Hallway -> Dim
        59 sec timer overall
        59 sec raise (hallway 6)
        54.5 sec raise (hallway 1)
        12 sec raise (dem 5)
        7.5 sec raise (dem 1)
        1 dispatch and cancel
         */
    public void Tower1Timer() {
        secLoad = 60;
        tower1Task = new BukkitRunnable() {
            @Override
            public void run() {
                if (secLoad == 1) {
                    totManager.removeAllLoad();
                    this.cancel();
                    return;
                }
                if (secLoad == 5) {
                    if (TOTBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        blocks.LoadDispatch();
                        totManager.msgOps("Block1 dispatched");
                    } else {
                        secLoad = 9;
                        totManager.msgOps("Block1 waiting block2");
                    }
                }
                if (secLoad == 10) {
                    //closing restraints
                    blocks.CloseRestraint();
                    TOTBlocks.setblock1(Block1.holding);
                }
                //closing gates
                if (secLoad == 15) {
                    TOTBlocks.setblock1(Block1.restraintsunlocked);
                    blocks.CloseGates();
                }
                if (secLoad == 16) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                    //change later
                }
                //open gates
                if (secLoad == 50) {
                    TOTBlocks.setblock1(Block1.restraintsgatesunlocked);
                    blocks.OpenGates();
                }
                if (secLoad == 60) {
                    totManager.msgOps("Block1 timer started");
                }
                if (secLoad <= 50 && secLoad >= 17) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + (secLoad - 15) + ChatColor.WHITE + " seconds!");
                    //change later
                }
                --secLoad;
            }
        };
        tower1Task.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void Tower2Timer() {
        secLoad = 60;
        tower2Task = new BukkitRunnable() {
            @Override
            public void run() {
                if (secLoad == 1) {
                    totManager.removeAllLoad();
                    this.cancel();
                    return;
                }
                if (secLoad == 5) {
                    if (TOTBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        blocks.LoadDispatch();
                        totManager.msgOps("Block1 dispatched");
                    } else {
                        secLoad = 9;
                        totManager.msgOps("Block1 waiting block2");
                    }
                }
                if (secLoad == 10) {
                    //closing restraints
                    blocks.CloseRestraint();
                    TOTBlocks.setblock1(Block1.holding);
                }
                //closing gates
                if (secLoad == 15) {
                    TOTBlocks.setblock1(Block1.restraintsunlocked);
                    blocks.CloseGates();
                }
                if (secLoad == 16) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                    //change later
                }
                //open gates
                if (secLoad == 50) {
                    TOTBlocks.setblock1(Block1.restraintsgatesunlocked);
                    blocks.OpenGates();
                }
                if (secLoad == 60) {
                    totManager.msgOps("Block1 timer started");
                }
                if (secLoad <= 50 && secLoad >= 17) {
                    totManager.msgLoad(ChatColor.WHITE + "Gates closing in " + ChatColor.AQUA + (secLoad - 15) + ChatColor.WHITE + " seconds!");
                    //change later
                }
                --secLoad;
            }
        };
        tower2Task.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void killTower1Timer() {
        if (tower1Task != null) {
            tower1Task.cancel();
            tower1Task = null; // Optional: clear the reference
        }
    }
    public void killTower2Timer() {
        if (tower2Task != null) {
            tower2Task.cancel();
            tower2Task = null; // Optional: clear the reference
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
                    totManager.msgLoad(ChatColor.WHITE + "Prepare to Board!");
                    return;
                }
                if (secUnload == 2) {
                    totManager.msgLoad(ChatColor.WHITE + "Train is apporaching station in " + ChatColor.AQUA + (secUnload - 1) + ChatColor.WHITE + " second!");
                }
                //testing next block
                if (secUnload == 6) {
                    if (TOTBlocks.getblock1() == Block1.clear) {
                        //change later
                        blocks.Dispatch6();
                        totManager.msgOps("block6 dispatched");

                    } else {
                        secUnload = 10;
                        totManager.msgOps("block6 waiting block1");
                    }

                }
                //open restraints
                if (secUnload == 34) {
                    blocks.OpenRestraints();
                    TOTBlocks.setblock6(Block6.restraints);
                }
                if (secUnload == 35) {
                    totManager.msgOps("block6 timer started");
                }
                if (secUnload <= 70 && secUnload >= 3 && (TOTBlocks.getblock1() == Block1.dispatch || TOTBlocks.getblock1() == Block1.clear)) {
                    totManager.msgLoad(ChatColor.WHITE + "Train is apporaching station in " + ChatColor.AQUA + (secUnload - 1) + ChatColor.WHITE + " seconds!");
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
                    totManager.rideWaiting();
                    return;
                }
                if (secCheck == 2) {
                    if (totManager.checkInAreaSize() != 0) {
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
