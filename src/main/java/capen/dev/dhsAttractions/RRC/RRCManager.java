package capen.dev.dhsAttractions.RRC;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.RRC.Enums.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class RRCManager implements Listener {
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
    public static Global glstate;
    public static RRCBlocks blocks;
    public static RRCTimers rrcTimers;
    //Arraylists
    public static ArrayList<UUID> load;
    public static ArrayList<UUID> op;
    public static ArrayList<UUID> inArea;

    /*Ride check at startup
      Checks config.yml that ride is ok to be operational.
     */
    public RRCManager(Main main, RRCBlocks blocks, RRCTimers rrcTimers) {
        Bukkit.getLogger().info("RRCManager constructor reached");

        RRCManager.main = main;
        this.blocks = blocks;
        this.rrcTimers = rrcTimers;

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
        String rideState = main.getConfig().getString("rrc.status");
        Bukkit.getLogger().info("RRC startup blocks");
        if ("closed".equalsIgnoreCase(rideState)) {
            RRCBlocks.setglobal(Global.closed);
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
        new RRCBlocks(main, this);
        RRCBlocks.setglobal(Global.automatic);
        blocks.spawnTrains();
        rrcTimers.checkTimer();
        msgOps("Ride is activated at " + getESTTime());

        RRCBlocks.setblock1(Block1.restraintsunlocked);
        RRCBlocks.setblock2(Block2.clear);
        RRCBlocks.setblock3(Block3.clear);
        RRCBlocks.setblock4(Block4.clear);
        RRCBlocks.setblock5(Block5.occupied);
        RRCBlocks.setblock6(Block6.occupied);
        RRCBlocks.setblock7(Block7.restraints);
    }

    /*Ride Waiting Function
      Triggered by "rideAreaCheck"
      Change ride state to "waiting"
      Declare new block states
      Setblock redstone block that kills trains
      Kills "block1Timer", "block2Timer", and "launchTimer"
     */
    public void rideWaiting() {
        new RRCBlocks(main, this);
        RRCBlocks.setglobal(Global.waiting);
        blocks.killTrains();
        rrcTimers.killcheckTimer();
        rrcTimers.killLoadingTimer();
        rrcTimers.killUnloadingTimer();
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
            p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "RRCOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + message);
        }
    }

    /*Ride open
    Triggered by cmd "DHSRide rrc open"
    Change ride state to "waiting"
     */
    public void rideOpen() {
        new RRCBlocks(main, this);
        RRCBlocks.setglobal(Global.waiting);
        blocks.killTrains();
        rrcTimers.killcheckTimer();
        rrcTimers.killLoadingTimer();
        rrcTimers.killUnloadingTimer();
        //config change
        setRideStatus("open");
        msgOps("Ride is open & waiting at " + getESTTime());
        Bukkit.broadcastMessage(ChatColor.GRAY + "\n[" + ChatColor.RED + "Magic" + ChatColor.GOLD + "Works" + ChatColor.YELLOW + "MC" + ChatColor.GRAY
        + "]" + ChatColor.WHITE + " Rock'n' Roller Coaster Starring Aerosmith is now open!\n");
        onOpenSign();
    }

    /*Ride close
    Triggered by cmd "DHSRide rrc close"
    Change ride state to "close"
     */
    public void rideClose() {
        RRCBlocks.setglobal(Global.closed);
        blocks.killTrains();
        rrcTimers.killcheckTimer();
        rrcTimers.killLoadingTimer();
        rrcTimers.killUnloadingTimer();
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
        final Location cord1 = new Location(Bukkit.getWorld("Parks"), -78, 94, 2319);
        final Location cord2 = new Location(Bukkit.getWorld("Parks"), -230, 40, 2231);
        final Player p = e.getPlayer();
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord1.getBlockX() && p.getLocation().getBlockX() >= cord2.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord1.getBlockY() && p.getLocation().getBlockY() >= cord2.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord1.getBlockZ() && p.getLocation().getBlockZ() >= cord2.getBlockZ()) {
                        if (!inArea.contains(p.getUniqueId()) && RRCBlocks.getglobal() != Global.closed) {
                            if (RRCBlocks.getglobal() == Global.waiting && inArea.isEmpty()) {
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
        final Location cord1 = new Location(Bukkit.getWorld("Parks"), -79, 57, 2305);
        final Location cord2 = new Location(Bukkit.getWorld("Parks"), -91, 54, 2289);
        final Player p = e.getPlayer();
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord1.getBlockX() && p.getLocation().getBlockX() >= cord2.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord1.getBlockY() && p.getLocation().getBlockY() >= cord2.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord1.getBlockZ() && p.getLocation().getBlockZ() >= cord2.getBlockZ()) {
                        if (!load.contains(p.getUniqueId()) && (RRCBlocks.getglobal() == Global.automatic)) {
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
    public void setTimers(RRCTimers timers) {
        this.rrcTimers = timers;
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
        main.getConfig().set("rrc.status", status);
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
        final Location loc = new Location(Bukkit.getWorld("Parks"), -111, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
    public void onCloseSign() {
        final Location loc = new Location(Bukkit.getWorld("Parks"), -113, 41, 2238);
        loc.getBlock().setType(Material.REDSTONE_BLOCK);
    }
}

