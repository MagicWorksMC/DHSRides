package capen.dev.dhsAttractions.TOT;

import capen.dev.dhsAttractions.Main;
import capen.dev.dhsAttractions.TOT.Enums.*;
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

public class TOTManager implements Listener {
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
    public static TOTBlocks blocks;
    public static TOTTimers totTimers;
    //Arraylists
    public static ArrayList<UUID> load;
    public static ArrayList<UUID> load2;
    public static ArrayList<UUID> op;
    public static ArrayList<UUID> inArea;

    /*Ride check at startup
      Checks config.yml that ride is ok to be operational.
     */
    public TOTManager(Main main, TOTBlocks blocks, TOTTimers totTimers) {
        Bukkit.getLogger().info("TOTManager constructor reached");

        TOTManager.main = main;
        this.blocks = blocks;
        this.totTimers = totTimers;

        this.op = new ArrayList<>();
        this.load = new ArrayList<>();
        this.load2 = new ArrayList<>();
        this.inArea = new ArrayList<>();

        pluginStartUp();
    }

    /*
     Plugin ride check
     checks at the beginning of plugin if ride is functioning
     */
    public void pluginStartUp() {
        String rideState = main.getConfig().getString("tot.status");
        Bukkit.getLogger().info("TOT startup blocks");
        if ("closed".equalsIgnoreCase(rideState)) {
            TOTBlocks.setglobal(Global.closed);
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
        new TOTBlocks(main, this);
        TOTBlocks.setglobal(Global.automatic);
        blocks.spawnTrains();
        totTimers.checkTimer();
        msgOps("Ride is activated at " + getESTTime());

        TOTBlocks.setblock1(Block1a.restraintsunlocked);
        TOTBlocks.setblock2(Block1b.clear);
        TOTBlocks.setblock3(Block2a.clear);
        TOTBlocks.setblock4(Block2b.clear);
        TOTBlocks.setblock5(Block3.occupied);
        TOTBlocks.setblock6(Block99.restraints);
    }

    /*Ride Waiting Function
      Triggered by "rideAreaCheck"
      Change ride state to "waiting"
      Declare new block states
      Setblock redstone block that kills trains
      Kills "block1Timer", "block2Timer", and "launchTimer"
     */
    public void rideWaiting() {
        new TOTBlocks(main, this);
        TOTBlocks.setglobal(Global.waiting);
        blocks.killTrains();
        totTimers.killcheckTimer();
        totTimers.killLoadingTimer();
        totTimers.killUnloadingTimer();
        msgOps("Ride is waiting at " + getESTTime());
        load.clear();
        load2.clear();
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
    public void msgLoad2(final String message) {
        for (final UUID uuid : load2) {
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
            p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + "TOTOps" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + message);
        }
    }

    /*Ride open
    Triggered by cmd "DHSRide rrc open"
    Change ride state to "waiting"
     */
    public void rideOpen() {
        new TOTBlocks(main, this);
        TOTBlocks.setglobal(Global.waiting);
        blocks.killTrains();
        totTimers.killcheckTimer();
        totTimers.killLoadingTimer();
        totTimers.killUnloadingTimer();
        //config change
        setRideStatus("open");
        msgOps("Ride is open & waiting at " + getESTTime());
        Bukkit.broadcastMessage(ChatColor.GRAY + "\n[" + ChatColor.RED + "Magic" + ChatColor.GOLD + "Works" + ChatColor.YELLOW + "MC" + ChatColor.GRAY
        + "]" + ChatColor.WHITE + " Tower of Terror is now open!\n");
        onOpenSign();
    }

    /*Ride close
    Triggered by cmd "DHSRide rrc close"
    Change ride state to "close"
     */
    public void rideClose() {
        TOTBlocks.setglobal(Global.closed);
        blocks.killTrains();
        totTimers.killcheckTimer();
        totTimers.killLoadingTimer();
        totTimers.killUnloadingTimer();
        //config change
        setRideStatus("closed");
        msgOps("Ride is closed at " + getESTTime());
        load.clear();
        load2.clear();
        inArea.clear();
        onCloseSign();
    }
//final Location cord1 = new Location(Bukkit.getWorld("Parks"), -162, 92, 2766); 2318
//final Location cord2 = new Location(Bukkit.getWorld("Parks"), -338, 15, 2579);
    //Startup area
    @EventHandler
    public void startupArea(final PlayerMoveEvent e) {
        //Location of the timer load current loc -79 57 2305 -88 54 2282
        final Location cord1 = new Location(Bukkit.getWorld("Parks"), 78, 123, 2318);
        final Location cord2 = new Location(Bukkit.getWorld("Parks"), -52, 28, 2176);
        final Player p = e.getPlayer();
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord1.getBlockX() && p.getLocation().getBlockX() >= cord2.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord1.getBlockY() && p.getLocation().getBlockY() >= cord2.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord1.getBlockZ() && p.getLocation().getBlockZ() >= cord2.getBlockZ()) {
                        if (!inArea.contains(p.getUniqueId()) && TOTBlocks.getglobal() != Global.closed) {
                            if (TOTBlocks.getglobal() == Global.waiting && inArea.isEmpty()) {
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
    public void loadArea1(final PlayerMoveEvent e) {
        //Location of the timer load current loc -79 57 2305 -88 54 2282
        final Location cord1 = new Location(Bukkit.getWorld("Parks"), 5, 65, 2215);
        final Location cord2 = new Location(Bukkit.getWorld("Parks"), 0, 60, 2210);
        final Player p = e.getPlayer();
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord1.getBlockX() && p.getLocation().getBlockX() >= cord2.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord1.getBlockY() && p.getLocation().getBlockY() >= cord2.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord1.getBlockZ() && p.getLocation().getBlockZ() >= cord2.getBlockZ()) {
                        if (!load.contains(p.getUniqueId()) && (TOTBlocks.getglobal() == Global.automatic)) {
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
    //final Location cord1 = new Location(Bukkit.getWorld("Parks"), -299, 74, 2628);
    //        final Location cord2 = new Location(Bukkit.getWorld("Parks"), -294, 74, 2606);
    @EventHandler
    public void loadArea2(final PlayerMoveEvent e) {
        //Location of the timer load current loc -79 57 2305 -88 54 2282
        final Location cord1 = new Location(Bukkit.getWorld("Parks"), 10, 65, 2218);
        final Location cord2 = new Location(Bukkit.getWorld("Parks"), 5, 60, 2213);
        final Player p = e.getPlayer();
        if (p.getWorld().getName().equals("Parks")) {
            if (p.getLocation().getBlockX() <= cord1.getBlockX() && p.getLocation().getBlockX() >= cord2.getBlockX()) {
                if (p.getLocation().getBlockY() <= cord1.getBlockY() && p.getLocation().getBlockY() >= cord2.getBlockY()) {
                    if (p.getLocation().getBlockZ() <= cord1.getBlockZ() && p.getLocation().getBlockZ() >= cord2.getBlockZ()) {
                        if (!load2.contains(p.getUniqueId()) && (TOTBlocks.getglobal() == Global.automatic)) {
                            addLoad2(p);
                        }
                    }
                    else if (load2.contains(p.getUniqueId())) {
                        this.removeLoad2(p);
                    }
                }
                else if (load2.contains(p.getUniqueId())) {
                    this.removeLoad2(p);
                }
            }
            //if player is removed from region
            else if (load2.contains(p.getUniqueId())) {
                this.removeLoad2(p);
            }
        }
    }
    //Set timers
    public void setTimers(TOTTimers timers) {
        this.totTimers = timers;
    }
    //Adding load
    public void addLoad(final Player p) {
        if (!load.contains(p.getUniqueId())) {
            load.add(p.getUniqueId());
        }
    }
    public void addLoad2(final Player p) {
        if (!load2.contains(p.getUniqueId())) {
            load2.add(p.getUniqueId());
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
    public void removeLoad2(final Player p) {
        load2.remove(p.getUniqueId());
    }
    public void removeAllLoad() {
        load.clear();
    }
    public void removeAllLoad2() {
        load2.clear();

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
        main.getConfig().set("tot.status", status);
        main.saveConfig();
    }
    //Player quit
    @EventHandler
    public void onQuitPre(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        this.removeOp(p);
        this.removeLoad(p);
        this.removeLoad2(p);
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

