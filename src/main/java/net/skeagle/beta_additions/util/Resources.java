package net.skeagle.beta_additions.util;

import net.skeagle.beta_additions.BetaMain;
import net.skeagle.beta_additions.Nicks;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import java.io.File;

public class Resources
{
    private final Resource nicks;
    private Resource warps;
    private final Nicks nickscfg = new Nicks(this);

    public Resources(BetaMain main) {
        this.nicks = new Resource("nicks.yml", main);
    }

    public void loadNicks(Player p) {
        this.nickscfg.loadNicks(p);
    }

    public void saveNicks(Player p) {
        this.nickscfg.saveNicks(p);
    }

    public Configuration getNicks() {
        return this.nicks.getSettings();
    }

    public File getNicksFile() {
        return this.nicks.getFile();
    }
}

