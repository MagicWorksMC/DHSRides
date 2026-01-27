package capen.dev.dhsAttractions.RRC;

import capen.dev.dhsAttractions.RRC.Enums.*;
import org.bukkit.Bukkit;

public class RRCCommandManager {
    /*
    /dhsride rrc <changeblockstate/blocktest/blockstatestatus/open/close/operate> <block> <status>
    changeblockstate changes the block state of a section, also some block states have a function, ensure it is commented.
    blocktest cmd block use only for test of block ahead
    blockstatestatus player use only for test of any block
     */

    //Connect to other classes
    public static RRCTimers rrcTimers;
    public static RRCBlocks rrcBlocks;

    public RRCCommandManager(RRCTimers rrcTimers, RRCBlocks rrcBlocks) {
        this.rrcTimers = rrcTimers;
        this.rrcBlocks = rrcBlocks;
    }

    //Change block state "rrcChangeBlockState"
    //block1 (clear, dispatch, restraintsunlocked, restraintsgatesunlocked, holding)
    //block2 (clear, holding, launch, occupied)
    //block3 (clear, holding, occupied)
    //block4 (clear, holding, occupied)
    //block5 (clear, holding, occupied)
    //block6 (clear, holding, occupied)
    //block7 (clear, holding, occupied,unlocked)
    public void rrcChangeBlockState(String block, String status) {
        if (block.equalsIgnoreCase("block1")) {
            if (status.equalsIgnoreCase("holding")) {
                RRCBlocks.setblock1(Block1.holding);
            }
            if (status.equalsIgnoreCase("restraintsgatesunlocked")) {
                RRCBlocks.setblock1(Block1.restraintsgatesunlocked);
            }
            if (status.equalsIgnoreCase("timer")) {
                rrcTimers.loadingTimer();
            }
            if (status.equalsIgnoreCase("unlocked")) {
                RRCBlocks.setblock1(Block1.restraintsunlocked);
                if (RRCBlocks.getblock1() == Block1.restraintsunlocked) {
                }
            }
            if (status.equalsIgnoreCase("clear")) {
                RRCBlocks.setblock1(Block1.clear);
            }
        }
        if (block.equalsIgnoreCase("block2")) {
            if (status.equalsIgnoreCase("holding")) {
               RRCBlocks.setblock2(Block2.holding);

            }
            if (status.equalsIgnoreCase("Launch")) {
               RRCBlocks.setblock2(Block2.launch);

            }
            if (status.equalsIgnoreCase("clear")) {
               RRCBlocks.setblock2(Block2.clear);

            }
        }
        if (block.equalsIgnoreCase("block3")) {
            if (status.equalsIgnoreCase("holding")) {
               RRCBlocks.setblock3(Block3.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
               RRCBlocks.setblock3(Block3.occupied);
            }
            if (status.equalsIgnoreCase("clear")) {
               RRCBlocks.setblock3(Block3.clear);
            }
        }
        if (block.equalsIgnoreCase("block4")) {
            if (status.equalsIgnoreCase("holding")) {
               RRCBlocks.setblock4(Block4.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
               RRCBlocks.setblock4(Block4.occupied);
            }
            if (status.equalsIgnoreCase("clear")) {
               RRCBlocks.setblock4(Block4.clear);
                if (RRCBlocks.getblock3() == Block3.holding) {
                   rrcBlocks.release3();
                }
            }
        }
        if (block.equalsIgnoreCase("block5")) {
            if (status.equalsIgnoreCase("holding")) {
               RRCBlocks.setblock5(Block5.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
               RRCBlocks.setblock5(Block5.occupied);
            }
            if (status.equalsIgnoreCase("clear")) {
               RRCBlocks.setblock5(Block5.clear);
                if (RRCBlocks.getblock4() == Block4.holding) {
                   rrcBlocks.Release4();
                }
            }
        }
        if (block.equalsIgnoreCase("block6")) {
            if (status.equalsIgnoreCase("holding")) {
               RRCBlocks.setblock6(Block6.holding);
            }
            if (status.equalsIgnoreCase("clear")) {
               RRCBlocks.setblock6(Block6.clear);
                if (RRCBlocks.getblock5() == Block5.holding) {
                   rrcBlocks.Release5();
                }
            }
        }
        if (block.equalsIgnoreCase("block7")) {
            if (status.equalsIgnoreCase("holding")) {
               RRCBlocks.setblock7(Block7.holding);

            }
            //This is to start timer for unload dispatch
            if (status.equalsIgnoreCase("Unlocked")) {
               RRCBlocks.setblock7(Block7.restraints);
            }
            if (status.equalsIgnoreCase("Timer")) {
                rrcTimers.unloadingTimer();
            }
            if (status.equalsIgnoreCase("clear")) {
               RRCBlocks.setblock7(Block7.clear);
                if (RRCBlocks.getblock6() == Block6.holding) {
                   rrcBlocks.Release6();
                }
            }
        }
    }

    //Block Test state "rrcBlockTest" cmd only
    //block2
    //block4
    //block5
    //block6
    //block7
    public void rrcBlockTest(String block) {
        if (block.equalsIgnoreCase("block2")) {
           rrcBlocks.Launch();
        }
        if (block.equalsIgnoreCase("block4")) {
            if (RRCBlocks.getblock4() == Block4.holding) {
               rrcBlocks.hold3();
               RRCBlocks.setblock3(Block3.holding);
            }
        }
        if (block.equalsIgnoreCase("block5")) {
            if (RRCBlocks.getblock5() == Block5.holding) {
               rrcBlocks.Hold4();
               RRCBlocks.setblock4(Block4.holding);
            }
        }
        if (block.equalsIgnoreCase("block6")) {
            if (RRCBlocks.getblock6() == Block6.holding) {
               rrcBlocks.Hold5();
               RRCBlocks.setblock5(Block5.holding);
            }
        }
        if (block.equalsIgnoreCase("block7")) {
            if (RRCBlocks.getblock7() != Block7.clear) {
               rrcBlocks.Hold6();
               RRCBlocks.setblock6(Block6.holding);
            }
        }
    }
    //Player cmd to check the status of a block "rrcBlockStatus" operator only
    public String rrcBlockStatus(String block) {
        switch (block.toLowerCase()) {
            case "block1":
                return RRCBlocks.getblock1().toString();
            case "block2":
                return RRCBlocks.getblock2().toString();
            case "block3":
                return RRCBlocks.getblock3().toString();
            case "block4":
                return RRCBlocks.getblock4().toString();
            case "block5":
                return RRCBlocks.getblock5().toString();
            case "block6":
                return RRCBlocks.getblock6().toString();
            case "block7":
                return RRCBlocks.getblock7().toString();
            case "global":
                return RRCBlocks.getglobal().toString();
            default:
                return "Invalid block name: " + block;
        }
    }
}