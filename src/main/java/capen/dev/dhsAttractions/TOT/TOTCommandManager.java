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
    public static TOTManager totManager;

    public TOTCommandManager(TOTTimers totTimers, TOTBlocks totBlocks, TOTManager totManager) {
        this.totTimers = totTimers;
        this.totBlocks = totBlocks;
        this.totManager = totManager;
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
                totManager.msgOps("Block1a: " + TOTBlocks.getblock1a());
            }
            if (status.equalsIgnoreCase("restraintsunlocked")) {
                TOTBlocks.setblock1a(Block1a.restraintsunlocked);
                totManager.msgOps("Block1a: " + TOTBlocks.getblock1a());
            }
            if (status.equalsIgnoreCase("restraintsdoorsunlocked")) {
                TOTBlocks.setblock1a(Block1a.restraintsdoorsunlocked);
                totManager.msgOps("Block1a: " + TOTBlocks.getblock1a());
            }
            if (status.equalsIgnoreCase("dispatcha")) {
                TOTBlocks.setblock1a(Block1a.dispatcha);
                totManager.msgOps("Block1a: " + TOTBlocks.getblock1a());
            }
            if (status.equalsIgnoreCase("dispatchb")) {
                TOTBlocks.setblock1a(Block1a.dispatchb);
                totManager.msgOps("Block1a: " + TOTBlocks.getblock1a());
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock1a(Block1a.clear);
                totManager.msgOps("Block1a: " + TOTBlocks.getblock1a());

            }
            if (status.equalsIgnoreCase("timer")) {
                totTimers.boardingATimer();
                totManager.msgOps("TowerA Boarding Timer has begun! Block1a: " + TOTBlocks.getblock1a() + " Block3: " + TOTBlocks.getblock3());
            }
        }

        if (block.equalsIgnoreCase("block1b")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock1b(Block1b.holding);
                totManager.msgOps("Block1b: " + TOTBlocks.getblock1b());
            }
            if (status.equalsIgnoreCase("restraintsunlocked")) {
                TOTBlocks.setblock1b(Block1b.restraintsunlocked);
                totManager.msgOps("Block1b: " + TOTBlocks.getblock1b());
            }
            if (status.equalsIgnoreCase("restraintsdoorsunlocked")) {
                TOTBlocks.setblock1b(Block1b.restraintsdoorsunlocked);
                totManager.msgOps("Block1b: " + TOTBlocks.getblock1b());
            }
            if (status.equalsIgnoreCase("dispatcha")) {
                TOTBlocks.setblock1b(Block1b.dispatcha);
                totManager.msgOps("Block1b: " + TOTBlocks.getblock1b());
            }
            if (status.equalsIgnoreCase("dispatchb")) {
                TOTBlocks.setblock1b(Block1b.dispatchb);
                totManager.msgOps("Block1b: " + TOTBlocks.getblock1b());
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock1b(Block1b.clear);
                totManager.msgOps("Block1b: " + TOTBlocks.getblock1b());
            }
            if (status.equalsIgnoreCase("timer")) {
                totTimers.boardingBTimer();
                totManager.msgOps("TowerB Boarding Timer has begun! Block1b: " + TOTBlocks.getblock1b() + " Block3: " + TOTBlocks.getblock3());

            }
        }

        if (block.equalsIgnoreCase("block2")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock2(Block2.holding);
                totManager.msgOps("Block2: " + TOTBlocks.getblock2());

            }
            if (status.equalsIgnoreCase("occupied")) {
                TOTBlocks.setblock2(Block2.occupied);
                totManager.msgOps("Block2: " + TOTBlocks.getblock2());

            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock2(Block2.clear);
                totManager.msgOps("Block2: " + TOTBlocks.getblock2());
                if (TOTBlocks.getblock1b() == Block1b.holding) {
                    //totBlocks.ReleaseBravo();
                }
            }
        }

        if (block.equalsIgnoreCase("block3")) {
            if (status.equalsIgnoreCase("holding")) {
                TOTBlocks.setblock3(Block3.holding);
                totManager.msgOps("Block3: " + TOTBlocks.getblock3());

            }
            if (status.equalsIgnoreCase("occupied")) {
                TOTBlocks.setblock3(Block3.occupied);
                totManager.msgOps("Block3: " + TOTBlocks.getblock3());

            }
            if (status.equalsIgnoreCase("restraints")) {
                TOTBlocks.setblock3(Block3.restraints);
                totManager.msgOps("Block3: " + TOTBlocks.getblock3());

            }
            if (status.equalsIgnoreCase("dispatch")) {
                TOTBlocks.setblock3(Block3.dispatch);
                totManager.msgOps("Block3: " + TOTBlocks.getblock3());

                //totTimers.unloadingTimer();
            }
            if (status.equalsIgnoreCase("clear")) {
                TOTBlocks.setblock3(Block3.clear);
                totManager.msgOps("Block3: " + TOTBlocks.getblock3());
                if (TOTBlocks.getblock2() == Block2.holding) {
                    //totBlocks.ReleaseEcho();
                }
            }
            if (status.equalsIgnoreCase("timer")) {
                totTimers.unloadingTimer();
                totManager.msgOps("Unload Timer has begun! Block3: " + TOTBlocks.getblock3() + " Block2: " + TOTBlocks.getblock2());

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
            case "all":
                return "\nblock1a: " + TOTBlocks.getblock1a().toString() + "\nblock1b: " + TOTBlocks.getblock1b().toString() + "\nblock2: " + TOTBlocks.getblock2().toString() + "\nblock3: " + TOTBlocks.getblock3().toString() + "\nglobal: " + TOTBlocks.getglobal().toString();

            default:
                return "Invalid block name: " + block;
        }
    }
}