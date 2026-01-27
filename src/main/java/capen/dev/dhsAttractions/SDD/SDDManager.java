package capen.dev.dhsAttractions.SDD;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.SDD.Enums.Block1;
import capen.dev.dhsAttractions.SDD.Enums.Block2;
import capen.dev.dhsAttractions.SDD.Enums.Block3;
import capen.dev.dhsAttractions.SDD.Enums.Block4;
import capen.dev.dhsAttractions.SDD.Enums.Block5;
import capen.dev.dhsAttractions.SDD.Enums.Block6;
import capen.dev.dhsAttractions.RRC.Enums.Block7;
import capen.dev.dhsAttractions.SDD.Enums.Global;
import capen.dev.dhsAttractions.SDD.SDDBlocks;
import capen.dev.dhsAttractions.RRC.RRCTimers;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class SDDManager implements Listener {
    /*
    Startup location is defined
    Load location is defined
    Station location is defined
    Ride Activate Function
    Ride Close Function
    Load ArrayList
    Loading Message Function
    OperatorArrayList
    Operator Message Function
     */
    //Call other classes
    public static Main main;
    public static capen.dev.dhsAttractions.SDD.Enums.Global glstate;
    public static SDDBlocks blocks;
    public static SDDTimers sddTimers;
    //Arraylists
    public static ArrayList<UUID> load;
    public static ArrayList<UUID> op;
    public static ArrayList<UUID> inArea;

    /*Ride check at startup
      Checks config.yml that ride is ok to be operational.
     */
    public SDDManager(Main main, SDDBlocks blocks, SDDTimers sddTimers) {
        Bukkit.getLogger().info("SDDManager constructor reached");

        SDDManager.main = main;
        this.blocks = blocks;
        this.sddTimers = sddTimers;

        this.op = new ArrayList<>();
        this.load = new ArrayList<>();
        this.inArea = new ArrayList<>();

        pluginStartUp();
    }

    /*
     Plugin ride check
     checks at the beginning of plugin if ride is functioning
     */
    public void pluginStartUp() {
        String rideState = main.getConfig().getString("sdd.status");
        Bukkit.getLogger().info("SDD startup blocks");
        if ("closed".equalsIgnoreCase(rideState)) {
            SDDBlocks.setglobal(capen.dev.dhsAttractions.SDD.Enums.Global.closed);
        }
    }

    /*Ride activate
      Triggered by "startupArea"
      Check if ride global must be set at "waiting"
      Change ride state to "automatic"
      Declare block states
      Begin "block1Timer" and "block7Timer"
      Begin "rideAreaCheck" timer
      Spawn ride
     */
    public void rideActivate() {
        new SDDBlocks(main, this);
        SDDBlocks.setglobal(capen.dev.dhsAttractions.SDD.Enums.Global.automatic);
        blocks.spawnTrains();
        sddTimers.checkTimer();
        msgOps("Ride is activated at " + getESTTime());

        SDDBlocks.setblock1(Block1.restraintsunlocked);
        SDDBlocks.setblock2(Block2.clear);
        SDDBlocks.setblock3(Block3.clear);
        SDDBlocks.setblock4(Block4.clear);
        SDDBlocks.setblock5(Block5.occupied);
        SDDBlocks.setblock6(Block6.restraints);
    }

    /*Ride Waiting Function
      Triggered by "rideAreaCheck"
      Change ride state to "waiting"
      Declare new block states
      Setblock redstone block that kills trains
      Kills "block1Timer", "block2Timer", and "launchTimer"
     */
    public void rideWaiting() {
        new SDDBlocks(main, this);
        SDDBlocks.setglobal(capen.dev.dhsAttractions.SDD.Enums.Global.waiting);
        blocks.killTrains();
        sddTimers.killcheckTimer();
        sddTimers.killLoadingTimer();
        sddTimers.killUnloadingTimer();
        msgOps("Ride is waiting at " + getESTTime());
        load.clear();
        inArea.clear();
    }

    /*Loading Message Function "msgLoad"
      Gives loading players 3 messages
      "Ride is apporaching station in X seconds."
      "Prepare to board!"
      "Restraints close in X seconds."
     */
    public void msgLoad(final String message) {
        for (final UUID uuid : load) {
            final Player p = Bukkit.getPlayer(uuid);
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent) new TextComponent(message));
        }
    }

    /*Operator Message Function "msgOps"
      Gives all operators important ride messages
      "[RRCEvent] <message>"
     */
    public void msgOps(final String message) {
        for (final UUID uuid : op) {
            final Player p = Bukkit.getPlayer(uuid);
            p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "SDDOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + message);
        }
    }

    /*Ride open
    Triggered by cmd "DHSRide rrc open"
    Change ride state to "waiting"
     */
    public void rideOpen() {
        new SDDBlocks(main, this);
        SDDBlocks.setglobal(capen.dev.dhsAttractions.SDD.Enums.Global.waiting);
        blocks.killTrains();
        sddTimers.killcheckTimer();
        sddTimers.killLoadingTimer();
        sddTimers.killUnloadingTimer();
        //config change
        setRideStatus("open");
        msgOps("Ride is open & waiting at " + getESTTime());
        Bukkit.broadcastMessage(ChatColor.GRAY + "\n[" + ChatColor.RED + "Magic" + ChatColor.GOLD + "Works" + ChatColor.YELLOW + "MC" + ChatColor.GRAY
        + "]" + ChatColor.WHITE + " Slinky Dog Dash is now open!\n");
        onOpenSign();
    }

    /*Ride close
    Triggered by cmd "DHSRide rrc close"
    Change ride state to "close"
     */
    public void rideClose() {
        SDDBlocks.setglobal(capen.dev.dhsAttractions.SDD.Enums.Global.closed);
        blocks.killTrains();
        sddTimers.killcheckTimer();
        sddTimers.killLoadingTimer();
        sddTimers.killUnloadingTimer();
        //config change
        setRideStatus("closed");
        msgOps("Ride is closed at " + getESTTime());
        load.clear();
        inArea.clear();
        onCloseSign();
    }

    //Startup area
    @EventHandler
    public void startupArea(final PlayerMoveEvent e) {
        //Location of the timer load current loc -79 57 2305 -88 54 2282
        final Location cord1 = new Location(Bukkit.getWorld("Parks"), -162, 92, 2766);
        final Location cord2 = new Location(Bukkit.getWorld("Parks"), -338, 15, 2579);
        final Player p = e.getPlayer();
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord1.getBlockX() && p.getLocation().getBlockX() >= cord2.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord1.getBlockY() && p.getLocation().getBlockY() >= cord2.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord1.getBlockZ() && p.getLocation().getBlockZ() >= cord2.getBlockZ()) {
                        if (!inArea.contains(p.getUniqueId()) && SDDBlocks.getglobal() != capen.dev.dhsAttractions.SDD.Enums.Global.closed) {
                            if (SDDBlocks.getglobal() == capen.dev.dhsAttractions.SDD.Enums.Global.waiting && inArea.isEmpty()) {
                                rideActivate();
                            }
                            addInArea(p);
                        }
                    }
                    else if (inArea.contains(p.getUniqueId())) {
                        this.removeInArea(p);
                    }
                }
                else if (inArea.contains(p.getUniqueId())) {
                    this.removeInArea(p);
                }
            }
            //if player is removed from region
            else if (inArea.contains(p.getUniqueId())) {
                this.removeInArea(p);
            }
        }
    }
    //Load area
    @EventHandler
    public void loadArea(final PlayerMoveEvent e) {
        //Location of the timer load current loc -79 57 2305 -88 54 2282
        final Location cord1 = new Location(Bukkit.getWorld("Parks"), -299, 74, 2628);
        final Location cord2 = new Location(Bukkit.getWorld("Parks"), -294, 74, 2606);
        final Player p = e.getPlayer();
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord1.getBlockX() && p.getLocation().getBlockX() >= cord2.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord1.getBlockY() && p.getLocation().getBlockY() >= cord2.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord1.getBlockZ() && p.getLocation().getBlockZ() >= cord2.getBlockZ()) {
                        if (!load.contains(p.getUniqueId()) && (SDDBlocks.getglobal() == Global.automatic)) {
                            addLoad(p);
                        }
                    }
                    else if (load.contains(p.getUniqueId())) {
                        this.removeLoad(p);
                    }
                }
                else if (load.contains(p.getUniqueId())) {
                    this.removeLoad(p);
                }
            }
            //if player is removed from region
            else if (load.contains(p.getUniqueId())) {
                this.removeLoad(p);
            }
        }
        final Location cord3 = new Location(Bukkit.getWorld("Parks"), -294, 74, 2635);
        final Location cord4 = new Location(Bukkit.getWorld("Parks"), -313, 54, 2606);
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord3.getBlockX() && p.getLocation().getBlockX() >= cord4.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord3.getBlockY() && p.getLocation().getBlockY() >= cord4.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord3.getBlockZ() && p.getLocation().getBlockZ() >= cord4.getBlockZ()) {
                        if (!load.contains(p.getUniqueId()) && (SDDBlocks.getglobal() == Global.automatic)) {
                            addLoad(p);
                        }
                    }
                    else if (load.contains(p.getUniqueId())) {
                        this.removeLoad(p);
                    }
                }
                else if (load.contains(p.getUniqueId())) {
                    this.removeLoad(p);
                }
            }
            //if player is removed from region
            else if (load.contains(p.getUniqueId())) {
                this.removeLoad(p);
            }
        }
    }
    //Set timers
    public void setTimers(SDDTimers timers) {
        this.sddTimers = timers;
    }
    //Adding load
    public void addLoad(final Player p) {
        if (!load.contains(p.getUniqueId())) {
            load.add(p.getUniqueId());
        }
    }

    //Adding op
    public void addOp(final Player p) {
        if (!op.contains(p.getUniqueId())) {
            op.add(p.getUniqueId());
        }
    }

    //Adding inArea
    public void addInArea(final Player p) {
        if (!inArea.contains(p.getUniqueId())) {
            inArea.add(p.getUniqueId());
        }
    }
    //Check how many players are InArea
    public int checkInAreaSize() {
        int size = inArea.size();
        return size;
    }
    //removing all of the arraylists
    public void removeLoad(final Player p) {
        load.remove(p.getUniqueId());
    }
    public void removeAllLoad() {
        load.clear();
    }
    public void removeOp(final Player p) {
        op.remove(p.getUniqueId());
    }

    public void removeInArea(final Player p) {
        inArea.remove(p.getUniqueId());
    }
    //get est time
    public String getESTTime() {
        ZoneId estZone = ZoneId.of("America/New_York"); // EST/EDT zone
        LocalDateTime now = LocalDateTime.now(estZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        return now.format(formatter);
    }
    //Config ride config change
    public void setRideStatus(String status) {
        main.getConfig().set("sdd.status", status);
        main.saveConfig();
    }
    //Player quit
    @EventHandler
    public void onQuitPre(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        this.removeOp(p);
        this.removeLoad(p);
        this.removeInArea(p);
    }
    //Change outdoor signs
    public void onOpenSign() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 27, 2712);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void onCloseSign() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -297, 27, 2710);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
}

