package capen.dev.dhsAttractions;

import capen.dev.dhsAttractions.RRC.Enums.Block6;
import capen.dev.dhsAttractions.RRC.Enums.Global;
import capen.dev.dhsAttractions.RRC.RRCBlocks;
import capen.dev.dhsAttractions.RRC.RRCCommandManager;
import capen.dev.dhsAttractions.RRC.RRCManager;
import capen.dev.dhsAttractions.SDD.SDDBlocks;
import capen.dev.dhsAttractions.SDD.SDDCommandManager;
import capen.dev.dhsAttractions.SDD.SDDManager;
import capen.dev.dhsAttractions.TOT.TOTBlocks;
import capen.dev.dhsAttractions.TOT.TOTCommandManager;
import capen.dev.dhsAttractions.TOT.TOTManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static capen.dev.dhsAttractions.Main.sddCommandManager;

public class DHSRideCommand implements CommandExecutor {

    public static RRCManager rrcManager;
    public static RRCCommandManager rrcCommandManager;
    public static SDDManager sddManager;
    public static SDDCommandManager sddCommandManager;
    public static TOTManager totManager;
    public static TOTCommandManager totCommandManager;

    public DHSRideCommand (RRCManager rrcManager, RRCCommandManager rrcCommandManager, SDDManager sddManager, SDDCommandManager sddCommandManager, TOTManager totManager, TOTCommandManager totCommandManager) {
        this.rrcManager = rrcManager;
        this.rrcCommandManager = rrcCommandManager;
        this.sddManager = sddManager;
        this.sddCommandManager = sddCommandManager;
        this.totManager = totManager;
        this.totCommandManager = totCommandManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /dhsride <ride> <action> [block] [status]");
            return false;
        }

        String ride = args[0];
        String action = args[1];
        String block = args.length >= 3 ? args[2] : null;
        String status = args.length >= 4 ? args[3] : null;

        if (ride.equalsIgnoreCase("rrc")) {
            if (action.equalsIgnoreCase("changeblockstate")) {
                if (block == null || status == null) {
                    sender.sendMessage("Usage: /dhsride rrc changeblockstate <block> <status>");
                    return false;
                }
                rrcCommandManager.rrcChangeBlockState(block, status);
            } else if (action.equalsIgnoreCase("blocktest")) {
                if (block == null) {
                    sender.sendMessage("Usage: /dhsride rrc blocktest <block>");
                    return false;
                }
                rrcCommandManager.rrcBlockTest(block);
            } else if (action.equalsIgnoreCase("blockstatestatus")) {
                if (block == null) {
                    sender.sendMessage("Usage: /dhsride rrc blockstatestatus <block>");
                    return false;
                }
                String state = rrcCommandManager.rrcBlockStatus(block);
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + block + " status is " + state);
            } else if (action.equalsIgnoreCase("open")) {
                if (RRCBlocks.getglobal() == Global.closed) {
                    rrcManager.rideOpen();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You have opened RRC!");
                    rrcManager.msgOps("RRC has been opened by " + sender.getName() + " and is waiting for players!");
                } else {
                    sender.sendMessage("Ride is already open!");
                }
            } else if (action.equalsIgnoreCase("close")) {
                if (RRCBlocks.getglobal() != Global.closed) {
                    rrcManager.rideClose();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You have closed RRC!");
                    if (sender instanceof Player p) {
                        rrcManager.msgOps("RRC has been closed by " + p.getName());
                    } else {
                        rrcManager.msgOps("RRC has been closed by server");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "Ride is already closed");
                }
            } else if (action.equalsIgnoreCase("operate")) {
                if (sender instanceof Player p) {
                    if (RRCManager.op.contains(p.getUniqueId())) {
                        rrcManager.removeOp(p);
                    } else {
                        rrcManager.addOp(p);
                        p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You are now a operator");
                        rrcManager.msgOps(p.getName() + " is now a operator!");

                    }
                }
            } else {
                sender.sendMessage("Unknown action. Use: changeblockstate, blocktest, blockstatestatus, open, close, operate");
            }
        }
        if (ride.equalsIgnoreCase("sdd")) {
            if (action.equalsIgnoreCase("changeblockstate")) {
                if (block == null || status == null) {
                    sender.sendMessage("Usage: /dhsride sdd changeblockstate <block> <status>");
                    return false;
                }
                sddCommandManager.sddChangeBlockState(block, status);
            } else if (action.equalsIgnoreCase("blocktest")) {
                if (block == null) {
                    sender.sendMessage("Usage: /dhsride sdd blocktest <block>");
                    return false;
                }
                sddCommandManager.sddBlockTest(block);
            } else if (action.equalsIgnoreCase("blockstatestatus")) {
                if (block == null) {
                    sender.sendMessage("Usage: /dhsride sdd blockstatestatus <block>");
                    return false;
                }
                String state = sddCommandManager.sddBlockStatus(block);
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "SDDOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + block + " status is " + state);
            } else if (action.equalsIgnoreCase("open")) {
                if (SDDBlocks.getglobal() == capen.dev.dhsAttractions.SDD.Enums.Global.closed) {
                    sddManager.rideOpen();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You have opened SDD!");
                    rrcManager.msgOps("SDD has been opened by " + sender.getName() + " and is waiting for players!");
                } else {
                    sender.sendMessage("Ride is already open!");
                }
            } else if (action.equalsIgnoreCase("close")) {
                if (SDDBlocks.getglobal() != capen.dev.dhsAttractions.SDD.Enums.Global.closed) {
                    sddManager.rideClose();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "SDDOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You have closed SDD!");
                    if (sender instanceof Player p) {
                        rrcManager.msgOps("SDD has been closed by " + p.getName());
                    } else {
                        rrcManager.msgOps("SDD has been closed by server");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "SDDOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "Ride is already closed");
                }
            } else if (action.equalsIgnoreCase("operate")) {
                if (sender instanceof Player p) {
                    if (SDDManager.op.contains(p.getUniqueId())) {
                        sddManager.removeOp(p);
                    } else {
                        sddManager.addOp(p);
                        p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "SDDOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You are now a operator");
                        sddManager.msgOps(p.getName() + " is now a operator!");

                    }
                }
            } else {
                sender.sendMessage("Unknown action. Use: changeblockstate, blocktest, blockstatestatus, open, close, operate");
            }
        }
        if (ride.equalsIgnoreCase("tot")) {
            if (action.equalsIgnoreCase("changeblockstate")) {
                if (block == null || status == null) {
                    sender.sendMessage("Usage: /dhsride tot changeblockstate <block> <status>");
                    return false;
                }
                totCommandManager.totChangeBlockState(block, status);
            } else if (action.equalsIgnoreCase("blocktest")) {
                if (block == null) {
                    sender.sendMessage("Usage: /dhsride tot blocktest <block>");
                    return false;
                }
                //totCommandManager.totBlockTest(block);
            } else if (action.equalsIgnoreCase("blockstatestatus")) {
                if (block == null) {
                    sender.sendMessage("Usage: /dhsride tot blockstatestatus <block>");
                    return false;
                }
                String state = totCommandManager.totBlockStatus(block);
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "TOTOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + block + " status is " + state);
            } else if (action.equalsIgnoreCase("open")) {
                if (TOTBlocks.getglobal() == capen.dev.dhsAttractions.TOT.Enums.Global.closed) {
                    totManager.rideOpen();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You have opened TOT!");
                    rrcManager.msgOps("TOT has been opened by " + sender.getName() + " and is waiting for players!");
                } else {
                    sender.sendMessage("Ride is already open!");
                }
            } else if (action.equalsIgnoreCase("close")) {
                if (TOTBlocks.getglobal() != capen.dev.dhsAttractions.TOT.Enums.Global.closed) {
                    totManager.rideClose();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "TOTOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You have closed TOT!");
                    if (sender instanceof Player p) {
                        rrcManager.msgOps("TOT has been closed by " + p.getName());
                    } else {
                        rrcManager.msgOps("TOT has been closed by server");
                    }
                } else {
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "TOTOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "Ride is already closed");
                }
            } else if (action.equalsIgnoreCase("operate")) {
                if (sender instanceof Player p) {
                    if (TOTManager.op.contains(p.getUniqueId())) {
                        totManager.removeOp(p);
                    } else {
                        totManager.addOp(p);
                        p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "TOTOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "You are now a operator");
                        totManager.msgOps(p.getName() + " is now a operator!");

                    }
                }
            } else {
                sender.sendMessage("Unknown action. Use: changeblockstate, blocktest, blockstatestatus, open, close, operate");
            }
        }

        return true;
    }
}

