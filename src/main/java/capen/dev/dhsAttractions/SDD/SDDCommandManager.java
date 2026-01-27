package capen.dev.dhsAttractions.SDD;

import capen.dev.dhsAttractions.RRC.Enums.Block7;
import capen.dev.dhsAttractions.RRC.RRCBlocks;
import capen.dev.dhsAttractions.SDD.Enums.*;
import capen.dev.dhsAttractions.SDD.SDDBlocks;
import capen.dev.dhsAttractions.SDD.SDDTimers;

public class SDDCommandManager {
    /*
    /dhsride rrc <changeblockstate/blocktest/blockstatestatus/open/close/operate> <block> <status>
    changeblockstate changes the block state of a section, also some block states have a function, ensure it is commented.
    blocktest cmd block use only for test of block ahead
    blockstatestatus player use only for test of any block
     */

    //Connect to other classes
    public static SDDTimers sddTimers;
    public static SDDBlocks sddBlocks;

    public SDDCommandManager(SDDTimers sddTimers, SDDBlocks sddBlocks) {
        this.sddTimers = sddTimers;
        this.sddBlocks = sddBlocks;
    }

    //Change block state "rrcChangeBlockState"
    //block1 (clear, dispatch, restraintsunlocked, restraintsgatesunlocked, holding)
    //block2 (clear, holding, launch, occupied)
    //block2 (clear, holding, launch, occupied)
    //block4 (clear, holding, occupied)
    //block5 (clear, holding, occupied)
    //block6 (clear, holding, occupied,unlocked)
    public void sddChangeBlockState(String block, String status) {
        if (block.equalsIgnoreCase("block1")) {
            if (status.equalsIgnoreCase("holding")) {
                SDDBlocks.setblock1(Block1.holding);
            }
            if (status.equalsIgnoreCase("restraintsgatesunlocked")) {
                SDDBlocks.setblock1(Block1.restraintsgatesunlocked);
            }
            if (status.equalsIgnoreCase("timer")) {
                sddTimers.loadingTimer();
            }
            if (status.equalsIgnoreCase("unlocked")) {
                SDDBlocks.setblock1(Block1.restraintsunlocked);
                if (SDDBlocks.getblock1() == Block1.restraintsunlocked) {
                }
            }
            if (status.equalsIgnoreCase("clear")) {
                SDDBlocks.setblock1(Block1.clear);
            }
        }
        if (block.equalsIgnoreCase("block2")) {
            if (status.equalsIgnoreCase("holding")) {
               SDDBlocks.setblock2(Block2.holding);

            }
            if (status.equalsIgnoreCase("occupied")) {
                SDDBlocks.setblock2(Block2.occupied);

            }
            if (status.equalsIgnoreCase("clear")) {
               SDDBlocks.setblock2(Block2.clear);

            }
        }
        if (block.equalsIgnoreCase("block3")) {
            if (status.equalsIgnoreCase("holding")) {
                SDDBlocks.setblock3(Block3.holding);

            }
            if (status.equalsIgnoreCase("occupied")) {
                SDDBlocks.setblock3(Block3.occupied);

            }
            if (status.equalsIgnoreCase("clear")) {
                SDDBlocks.setblock3(Block3.clear);
                if (SDDBlocks.getblock2() == Block2.holding) {
                    sddBlocks.Release2();
                }
            }
        }
        if (block.equalsIgnoreCase("block4")) {
            if (status.equalsIgnoreCase("holding")) {
               SDDBlocks.setblock4(Block4.holding);
            }
            if (status.equalsIgnoreCase("occupied")) {
               SDDBlocks.setblock4(Block4.occupied);
            }
            if (status.equalsIgnoreCase("clear")) {
               SDDBlocks.setblock4(Block4.clear);
                if (SDDBlocks.getblock3() == Block3.holding) {
                    sddBlocks.Release3();
                }
            }
        }
        if (block.equalsIgnoreCase("block5")) {
            if (status.equalsIgnoreCase("holding")) {
                SDDBlocks.setblock5(Block5.holding);
            }
            if (status.equalsIgnoreCase("clear")) {
                SDDBlocks.setblock5(Block5.clear);
                if (SDDBlocks.getblock4() == Block4.holding) {
                    sddBlocks.Release4();
                }
            }
        }
        if (block.equalsIgnoreCase("block6")) {
            if (status.equalsIgnoreCase("holding")) {
               SDDBlocks.setblock6(Block6.holding);

            }
            //This is to start timer for unload dispatch
            if (status.equalsIgnoreCase("Unlocked")) {
               SDDBlocks.setblock6(Block6.restraints);
            }
            if (status.equalsIgnoreCase("Timer")) {
                sddTimers.unloadingTimer();
            }
            if (status.equalsIgnoreCase("clear")) {
               SDDBlocks.setblock6(Block6.clear);
                if (SDDBlocks.getblock5() == Block5.holding) {
                   sddBlocks.Release5();
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
    public void sddBlockTest(String block) {
        if (block.equalsIgnoreCase("block3")) {
            if (SDDBlocks.getblock3() == Block3.holding) {
                sddBlocks.Hold2();
                SDDBlocks.setblock2(Block2.holding);
            }
        }
        if (block.equalsIgnoreCase("block4")) {
            if (SDDBlocks.getblock4() == Block4.holding) {
                sddBlocks.Hold3();
               SDDBlocks.setblock3(Block3.holding);
            }
        }
        if (block.equalsIgnoreCase("block5")) {
            if (SDDBlocks.getblock5() == Block5.holding) {
               sddBlocks.Hold4();
               SDDBlocks.setblock4(Block4.holding);
            }
        }
        if (block.equalsIgnoreCase("block6")) {
            if (SDDBlocks.getblock6() != Block6.clear) {
                sddBlocks.Hold5();
                SDDBlocks.setblock5(Block5.holding);
            }
        }
    }
    //Player cmd to check the status of a block "rrcBlockStatus" operator only
    public String sddBlockStatus(String block) {
        switch (block.toLowerCase()) {
            case "block1":
                return SDDBlocks.getblock1().toString();
            case "block2":
                return SDDBlocks.getblock2().toString();
            case "block3":
                return SDDBlocks.getblock3().toString();
            case "block4":
                return SDDBlocks.getblock4().toString();
            case "block5":
                return SDDBlocks.getblock5().toString();
            case "block6":
                return SDDBlocks.getblock6().toString();
            case "global":
                return SDDBlocks.getglobal().toString();
            default:
                return "Invalid block name: " + block;
        }
    }
}