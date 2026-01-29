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

        if (block.equalsIgnoreCase("block1a")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock1a(Block1a.holding);
            }
            if (status.equalsIgnoreCase("restraintsunlocked")) {
                TOTBlocks.setblock1a(Block1a.restraintsunlocked);
            }
            if (status.equalsIgnoreCase("restraintsdoorsunlocked")) {
                TOTBlocks.setblock1a(Block1a.restraintsdoorsunlocked);
            }
            if (status.equalsIgnoreCase("dispatcha")) {
                TOTBlocks.setblock1a(Block1a.dispatcha);
            }
            if (status.equalsIgnoreCase("dispatchb")) {
                TOTBlocks.setblock1a(Block1a.dispatchb);
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock1a(Block1a.clear);
            }
        }

        if (block.equalsIgnoreCase("block1b")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock1b(Block1b.holding);
            }
            if (status.equalsIgnoreCase("restraintsunlocked")) {
                TOTBlocks.setblock1b(Block1b.restraintsunlocked);
            }
            if (status.equalsIgnoreCase("restraintsdoorsunlocked")) {
                TOTBlocks.setblock1b(Block1b.restraintsdoorsunlocked);
            }
            if (status.equalsIgnoreCase("dispatcha")) {
                TOTBlocks.setblock1b(Block1b.dispatcha);
            }
            if (status.equalsIgnoreCase("dispatchb")) {
                TOTBlocks.setblock1b(Block1b.dispatchb);
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock1b(Block1b.clear);
            }
        }

        if (block.equalsIgnoreCase("block2")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock2(Block2.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
                TOTBlocks.setblock2(Block2.occupied);
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock2(Block2.clear);
                if (TOTBlocks.getblock1b() == Block1b.holding) {
                    //totBlocks.ReleaseBravo();
                }
            }
        }

        if (block.equalsIgnoreCase("block3")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock3(Block3.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
                TOTBlocks.setblock3(Block3.occupied);
            }
            if (status.equalsIgnoreCase("restraints")) {
                TOTBlocks.setblock3(Block3.restraints);
            }
            if (status.equalsIgnoreCase("dispatch")) {
                TOTBlocks.setblock3(Block3.dispatch);
                totTimers.unloadingTimer();
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock3(Block3.clear);
                if (TOTBlocks.getblock2() == Block2.holding) {
                    //totBlocks.ReleaseEcho();
                }
            }
        }

        if (block.equalsIgnoreCase("block99")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock99(Block99.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
                TOTBlocks.setblock99(Block99.occupied);
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock99(Block99.clear);
                if (TOTBlocks.getblock3() == Block3.holding) {
                    //totBlocks.ReleaseUnload();
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

    //Player cmd to check the status of a block "rrcBlockStatus" operator only
    public String totBlockStatus(String block) {
        switch (block.toLowerCase()) {
            case "block1a":
                return TOTBlocks.getblock1a().toString();
            case "block1b":
                return TOTBlocks.getblock1b().toString();
            case "block2":
                return TOTBlocks.getblock2().toString();
            case "block3":
                return TOTBlocks.getblock3().toString();
            case "block99":
                return TOTBlocks.getblock99().toString();
            case "global":
                return TOTBlocks.getglobal().toString();
            default:
                return "Invalid block name: " + block;
        }
    }
}