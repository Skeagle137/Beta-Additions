package net.skeagle.beta_additions.util;

import net.minecraft.server.MinecraftServer;
import net.skeagle.beta_additions.BetaMain;
import net.skeagle.beta_additions.ChatColorsReplace;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class Commons {

    public static void say(CommandSender cs, String... message) {
        for (String m : message) {
            cs.sendMessage(color(m));
        }
    }

    public static String color(String s) {
        for (ChatColorsReplace ccr : ChatColorsReplace.values()) {
            String colors = "&" + ccr.getCode();
            if (s.toLowerCase().contains(colors)) {
                s = s.replaceAll(colors, "" + ccr.getColor());
            }
        }
        return s;
    }

    public static void log(String... message) {
        Logger logger = Logger.getLogger("Minecraft");
        for (String m : message) {
            logger.info(color("[Beta Additions] " + m));
        }
    }

    public static void log(Exception ex, String... message) {
        Logger logger = Logger.getLogger("Minecraft");
        for (String m : message) {
            logger.info(color("[Beta Additions] " + m));
            ex.printStackTrace();
        }
    }

    public static Player checkConsole(CommandSender sender) {
        if (!(sender instanceof Player)) {
            log("no :)");
            return null;
        }
        return (Player) sender;
    }

    public static void dispatchCommand(String cmd) { //now if we were on beta 1.7.3, maybe we wouldn't have to do this
        try {

            Field f = CraftServer.class.getDeclaredField("console");
            f.setAccessible(true);
            MinecraftServer ms = (MinecraftServer) f.get(BetaMain.getServerInstance());

            if ((!ms.g) && (MinecraftServer.a(ms))) {
                ms.a(cmd, ms);
            }

        } catch (Exception ignored) {}
    }

}
