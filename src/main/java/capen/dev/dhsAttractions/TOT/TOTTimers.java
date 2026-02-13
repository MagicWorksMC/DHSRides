package capen.dev.dhsAttractions.TOT;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.RRC.Enums.Block3;
import capen.dev.dhsAttractions.SDD.SDDBlocks;
import capen.dev.dhsAttractions.TOT.Enums.Block1a;
import capen.dev.dhsAttractions.TOT.Enums.Block1b;
import capen.dev.dhsAttractions.TOT.Enums.Block2;
import capen.dev.dhsAttractions.TOT.Enums.Block99;
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

    private int secBoardA;
    private int secBoardB;
    private int secTowerA;
    private int secTowerB;
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
    public void boardingATimer() {
        secBoardA = 85;
        loadingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (secBoardA == 1) {
                    Tower1Timer();
                    totManager.removeAllLoad();
                    this.cancel();
                    return;
                }
                if (secBoardA == 4) {
                    blocks.doorCloseA();
                }
                if (secBoardA == 11) {
                    blocks.closeRestraintsA();
                    TOTBlocks.setblock1a(Block1a.holding);
                }
                if (secBoardA == 12) {
                    totManager.msgLoad(ChatColor.WHITE + "Restraints locking in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                }
                if (secBoardA <= 54 && secBoardA >= 13) {
                    totManager.msgLoad(ChatColor.WHITE + "Restraints locking in " + ChatColor.AQUA + (secBoardA - 11) + ChatColor.WHITE + " seconds!");
                }
                if (secBoardA == 56) {
                    blocks.doorOpenA();
                }
                if (secBoardA == 70) {
                    blocks.boardingA();
                }
                if (secBoardA <= 85) {
                    totManager.msgOps("BoardA: Timer at" + secBoardA);
                }
                --secBoardA;
            }
        };
        loadingTask.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void boardingBTimer() {
        secBoardB = 85;
        loading2Task = new BukkitRunnable() {
            @Override
            public void run() {
                if (secBoardB == 1) {
                    Tower2Timer();
                    totManager.removeAllLoad2();
                    this.cancel();
                    return;
                }
                if (secBoardB == 4) {
                    blocks.doorCloseB();
                }
                if (secBoardB == 11) {
                    blocks.closeRestraintsB();
                    TOTBlocks.setblock1b(Block1b.holding);
                }
                if (secBoardB == 12) {
                    totManager.msgLoad2(ChatColor.WHITE + "Restraints locking in " + ChatColor.AQUA + "1" + ChatColor.WHITE + " second!");
                }
                if (secBoardB <= 54 && secBoardB >= 13) {
                    totManager.msgLoad2(ChatColor.WHITE + "Restraints locking in " + ChatColor.AQUA + (secBoardB - 11) + ChatColor.WHITE + " second!");
                }
                if (secBoardB == 56) {
                    blocks.doorOpenB();
                }
                if (secBoardB == 70) {
                    blocks.boardingB();
                }
                if (secBoardB <= 85) {
                    totManager.msgOps("BoardB: Timer at" + secBoardB);
                }
                --secBoardB;
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
        secTowerA = 59;
        tower1Task = new BukkitRunnable() {
            @Override
            public void run() {
                if (secTowerA == 1) {
                    blocks.dispatchA2();
                    this.cancel();
                    return;
                }
                if (secTowerA == 5) {
                    if (TOTBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        totManager.msgOps("a->d");
                    } else {
                        secTowerA = 6;
                        totManager.msgOps("a -- b");
                    }
                }
                if (secTowerA == 7) {
                    blocks.demA1();
                }
                if (secTowerA == 8) {
                    blocks.demA15();
                }
                if (secTowerA == 11) {
                    blocks.demA4();
                }
                if (secTowerA == 54) {
                    blocks.hallwayA1();
                }
                if (secTowerA == 55) {
                    blocks.hallwayA15();
                }
                if (secTowerA == 59) {
                    blocks.hallwayA5();
                }
                if (secTowerA <= 59) {
                    totManager.msgOps("A: Timer at" + secTowerA);
                }
                --secTowerA;
            }
        };
        tower1Task.runTaskTimer((Plugin)main.getInstance(), 0L, 20L);
    }
    public void Tower2Timer() {
        secTowerB = 59;
        tower2Task = new BukkitRunnable() {
            @Override
            public void run() {
                if (secTowerB == 1) {
                    blocks.dispatchB2();
                    this.cancel();
                    return;
                }
                if (secTowerB == 5) {
                    if (TOTBlocks.getblock2() == Block2.clear) {
                        //dispatch redstone block
                        totManager.msgOps("b->d");
                    } else {
                        secTowerB = 6;
                        totManager.msgOps("a -- b");
                    }
                }
                if (secTowerB == 8) {
                    blocks.demB1();
                }
                if (secTowerB == 9) {
                    blocks.demB15();
                }
                if (secTowerB == 13) {
                    blocks.demB5();
                }
                if (secTowerB == 55) {
                    blocks.hallwayB1();
                }
                if (secTowerB == 56) {
                    blocks.hallwayB15();
                }
                if (secTowerB == 59) {
                    blocks.hallwayB4();
                }
                if (secTowerB <= 59) {
                    totManager.msgOps("B: Timer at" + secTowerB);
                }
                --secTowerB;
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
        secUnload = -5;
        unloadingTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (secUnload == 86) {
                    this.cancel();
                    return;
                }
                if (secUnload == 85) {
                    if (TOTBlocks.getblock1a() == Block1a.clear) {
                        blocks.unloadDispatch();
                        blocks.switch1a();
                        TOTBlocks.setblock1a(Block1a.holding);
                        totManager.msgOps("Block1a Selected");
                    } else if (TOTBlocks.getblock1b() == Block1b.clear) {
                        blocks.unloadDispatch();
                        blocks.switch1b();
                        TOTBlocks.setblock1b(Block1b.holding);
                        totManager.msgOps("Block1b Selected");
                    } else {
                        secUnload = 82;
                    }
                }
                if (secUnload == 80) {
                    blocks.exit1();
                }
                if (secUnload == 77) {
                    //back
                }
                if (secUnload == 63) {
                    //door closed
                }
                if (secUnload == 43) {
                    blocks.openDoorUnload();
                    blocks.openRestraints();
                }
                if (secUnload == 36) {
                    //forward
                }
                if (secUnload == 17) {
                    blocks.exit7();
                }
                if (secUnload == 5) {
                    blocks.openSlidingDoorUnload();
                }
                if (secUnload >= 0) {
                    totManager.msgOps("Un: Timer at" + secUnload);
                }
                secUnload++;
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
