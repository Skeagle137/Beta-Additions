package net.skeagle.beta_additions;

import net.skeagle.beta_additions.tasks.AutoSave;
import net.skeagle.beta_additions.util.Commons;
import net.skeagle.beta_additions.util.Resources;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static net.skeagle.beta_additions.util.Commons.*;

public class BetaMain extends JavaPlugin {

    private static Server server;
    private Resources r;
    private ClassLoader loader;

    @Override
    public void onEnable() {
        new AutoSave(this);
        this.loader = this.getClassLoader();
        this.r = new Resources(this);
        server = this.getServer();
        log("Beta Additions is now enabled.");
        PluginManager pm = this.getServer().getPluginManager();
        BetaPlayerListener playerListener = new BetaPlayerListener(this.r);
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Event.Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Event.Priority.Normal, this);
    }

    @Override
    public void onDisable() {
        Commons.dispatchCommand("save-all");
        for (Player pl : this.getServer().getOnlinePlayers()) {
            this.r.saveNicks(pl);
        }
        log("Beta Additions is now disabled.");
    }

    public static Server getServerInstance() {
        return server;
    }

    public ClassLoader getLoader() {
        return this.loader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heal")) {
            if (args.length < 1) {
                Player p = checkConsole(sender);
                if (p == null) {
                    return false;
                }
                p.setHealth(20);
                say(p, "&6You have been healed.");
                return true;
            }
            Player a = this.getServer().getPlayer(args[0] + "&f");
            if (a != null) {
                a.setHealth(20);
                say(a,"&6You have been healed.");
                say(sender,"&6Healed &e" + a.getName() + "&6.");
                return true;
            }
            say(sender, "&cThat player is not online.");
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("nick")) {
            Player p = checkConsole(sender);
            if (p == null) {
                return false;
            }
            if (args.length < 1) {
                say(p, "&cYou must provide a nickname.");
                return true;
            }
            PlayerCache cache = PlayerCache.getCache(p);
            cache.setNickname(args[0]);
            p.setDisplayName(args[0]);
            say(p, "&6Your nickname is now " + color(args[0]));
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("broadcast")) {
            if (args.length < 1) {
                say(sender, "&cYou must provide a message after the command.");
                return true;
            }
            StringBuilder broadcast = new StringBuilder();
            for (String arg : args) {
                broadcast.append(arg).append(" ");
            }
            this.getServer().broadcastMessage(color("&a[Broadcast] &f" + broadcast.toString()));
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("kill")) {
            if (!sender.isOp()) {
                return true;
            }
            if (args.length < 1) {
                say(sender, "&cYou must provide a player name after the command.");
                return true;
            }
            Player a = this.getServer().getPlayer(args[0]);
            if (a != null) {
                a.setHealth(0);
                this.getServer().broadcastMessage(color("&cLol " + a.getDisplayName() + "&c just got REKT!"));
                return true;
            }
            say(sender, "&cThat player is not online.");
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("list")) {
            List<String> names = new ArrayList<>();
            for (Player pl : this.getServer().getOnlinePlayers()) {
                names.add(pl.getDisplayName());
            }
            say(sender, "&6Players online now: &f" + String.join("&6, &f", names));
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("coords")) {
            if (args.length < 1) {
                say(sender, "&cYou must provide a player name after the command.");
                return true;
            }
            Player a = this.getServer().getPlayer(args[0]);
            if (a != null) {
                say(sender, "&6The coordinates of &f" + a.getDisplayName() + "&6 are &bx: " +
                        (int) a.getLocation().getX() + "&6, &by: " +
                        (int) a.getLocation().getY() + "&6, &bz: " +
                        (int) a.getLocation().getZ() + "&6.");
                return true;
            }
            say(sender, "&cThat player is not online.");
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("durability")) {
            Player p = checkConsole(sender);
            if (p == null) {
                return false;
            }
            say(p, "&6Your item's durability is &b" + p.getItemInHand().getDurability() + "&6.");
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("reloadconfigs")) {
            if (!sender.isOp()) {
                return true;
            }
            this.r.save();
            this.r.load();
            say(sender, "&b[Beta Additions] &6Configs reloaded!");
        }
        return true;
    }
}
