package capen.dev.dhsAttractions.TOT;

import capen.dev.dhsAttractions.TOT.Enums.*;

public class TOTCommandManager {
    /*
    /dhsride rrc <changeblockstate/blocktest/blockstatestatus/open/close/operate> <block> <status>
    changeblockstate changes the block state of a section, also some block states have a function, ensure it is commented.
    blocktest cmd block use only for test of block ahead
    blockstatestatus player use only for test of any block
     */

    //Connect to other classes
    public static TOTTimers totTimers;
    public static TOTBlocks totBlocks;

    public TOTCommandManager(TOTTimers totTimers, TOTBlocks totBlocks) {
        this.totTimers = totTimers;
        this.totBlocks = totBlocks;
    }

    //Change block state "rrcChangeBlockState"
    //block1 (clear, dispatch, restraintsunlocked, restraintsgatesunlocked, holding)
    //block2 (clear, holding, launch, occupied)
    //block2 (clear, holding, launch, occupied)
    //block4 (clear, holding, occupied)
    //block5 (clear, holding, occupied)
    //block6 (clear, holding, occupied,unlocked)
    public void totChangeBlockState(String block, String status) {
        if (block.equalsIgnoreCase("block1")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock1(Block1a.holding);
            }
            if (status.equalsIgnoreCase("restraintsgatesunlocked")) {
                TOTBlocks.setblock1(Block1a.restraintsgatesunlocked);
            }
            if (status.equalsIgnoreCase("timer")) {
                totTimers.loadingTimer();
            }
            if (status.equalsIgnoreCase("unlocked")) {
                TOTBlocks.setblock1(Block1a.restraintsunlocked);
                if (TOTBlocks.getblock1() == Block1a.restraintsunlocked) {
                }
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock1(Block1a.clear);
            }
        }
        if (block.equalsIgnoreCase("block2")) {
            if (status.equalsIgnoreCase("holding")) {
               TOTBlocks.setblock2(Block1b.holding);

            }
            if (status.equalsIgnoreCase("occupied")) {
                TOTBlocks.setblock2(Block1b.occupied);

            }
            if (status.equalsIgnoreCase("clear")) {
               TOTBlocks.setblock2(Block1b.clear);

            }
        }
        if (block.equalsIgnoreCase("block3")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock3(Block2a.holding);

            }
            if (status.equalsIgnoreCase("occupied")) {
                TOTBlocks.setblock3(Block2a.occupied);

            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock3(Block2a.clear);
                if (TOTBlocks.getblock2() == Block1b.holding) {
                    totBlocks.Release2();
                }
            }
        }
        if (block.equalsIgnoreCase("block4")) {
            if (status.equalsIgnoreCase("holding")) {
               TOTBlocks.setblock4(Block2b.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
               TOTBlocks.setblock4(Block2b.occupied);
            }
            if (status.equalsIgnoreCase("clear")) {
               TOTBlocks.setblock4(Block2b.clear);
                if (TOTBlocks.getblock3() == Block2a.holding) {
                    totBlocks.Release3();
                }
            }
        }
        if (block.equalsIgnoreCase("block5")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock5(Block3.holding);
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock5(Block3.clear);
                if (TOTBlocks.getblock4() == Block2b.holding) {
                    totBlocks.Release4();
                }
            }
        }
        if (block.equalsIgnoreCase("block6")) {
            if (status.equalsIgnoreCase("holding")) {
               TOTBlocks.setblock6(Block99.holding);

            }
            //This is to start timer for unload dispatch
            if (status.equalsIgnoreCase("Unlocked")) {
               TOTBlocks.setblock6(Block99.restraints);
            }
            if (status.equalsIgnoreCase("Timer")) {
                totTimers.unloadingTimer();
            }
            if (status.equalsIgnoreCase("clear")) {
               TOTBlocks.setblock6(Block99.clear);
                if (TOTBlocks.getblock5() == Block3.holding) {
                   totBlocks.Release5();
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
    public void totBlockTest(String block) {
        if (block.equalsIgnoreCase("block3")) {
            if (TOTBlocks.getblock3() == Block2a.holding) {
                totBlocks.Hold2();
                TOTBlocks.setblock2(Block1b.holding);
            }
        }
        if (block.equalsIgnoreCase("block4")) {
            if (TOTBlocks.getblock4() == Block2b.holding) {
                totBlocks.Hold3();
               TOTBlocks.setblock3(Block2a.holding);
            }
        }
        if (block.equalsIgnoreCase("block5")) {
            if (TOTBlocks.getblock5() == Block3.holding) {
               totBlocks.Hold4();
               TOTBlocks.setblock4(Block2b.holding);
            }
        }
        if (block.equalsIgnoreCase("block6")) {
            if (TOTBlocks.getblock6() != Block99.clear) {
                totBlocks.Hold5();
                TOTBlocks.setblock5(Block3.holding);
            }
        }
    }
    //Player cmd to check the status of a block "rrcBlockStatus" operator only
    public String totBlockStatus(String block) {
        switch (block.toLowerCase()) {
            case "block1":
                return TOTBlocks.getblock1().toString();
            case "block2":
                return TOTBlocks.getblock2().toString();
            case "block3":
                return TOTBlocks.getblock3().toString();
            case "block4":
                return TOTBlocks.getblock4().toString();
            case "block5":
                return TOTBlocks.getblock5().toString();
            case "block6":
                return TOTBlocks.getblock6().toString();
            case "global":
                return TOTBlocks.getglobal().toString();
            default:
                return "Invalid block name: " + block;
        }
    }
}