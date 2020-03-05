package net.skeagle.beta_additions;

import net.skeagle.beta_additions.util.Resources;
import org.bukkit.entity.Player;
import org.bukkit.event.player.*;

import static net.skeagle.beta_additions.util.Commons.*;

public class BetaPlayerListener extends PlayerListener {

    private final Resources r;

    BetaPlayerListener(Resources r) {
        this.r = r;
    }


    @Override
    public void onPlayerChat(PlayerChatEvent e) {
        e.setFormat(color(e.getPlayer().getDisplayName() + "&f: " + e.getMessage()));
    }

    @Override
    public void onPlayerJoin(PlayerEvent e) {
        this.r.loadNicks(e.getPlayer());
    }

    @Override
    public void onPlayerQuit(PlayerEvent e) {
        this.r.saveNicks(e.getPlayer());
    }

    @Override
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        for (Player pl : BetaMain.getServerInstance().getOnlinePlayers()) {
            say(pl, "&cLol, " + e.getPlayer().getDisplayName() + "&c died, what a nub.",
                    "&6They respawned at &bx: " +
                            (int) e.getRespawnLocation().getX() + "&6, &by: " +
                            (int) e.getRespawnLocation().getY() + "&6, &bz: " +
                            (int) e.getRespawnLocation().getZ() + "&6.");
        }
    }

    @Override
    public void onPlayerTeleport(PlayerMoveEvent e) {
        //possible /back?
    }

    @Override
    public void onPlayerCommandPreprocess(PlayerChatEvent e) {
        log(e.getPlayer().getName() + " executed command: " + e.getMessage());
    }
}
