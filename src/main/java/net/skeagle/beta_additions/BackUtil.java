package net.skeagle.beta_additions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BackUtil {

    private static BackUtil back;
    private final Map<Player, Location> backLoc = new HashMap<>();

    private BackUtil() {

    }

    public static BackUtil getBack() {
        if (back == null) {
            back = new BackUtil();
        }
        return back;
    }

    public void setBackLoc(Player p, Location loc) {
        this.backLoc.remove(p);
        this.backLoc.put(p, loc);
    }

    public boolean hasBackLoc(Player p) {
        return this.backLoc.containsKey(p);
    }

    public void teleToBackLoc(String name, Player targetLoc) {
        BetaMain.getServerInstance().getPlayer(name).teleportTo(backLoc.get(targetLoc));
    }
}
