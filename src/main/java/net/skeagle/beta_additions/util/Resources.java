package net.skeagle.beta_additions.util;

import net.skeagle.beta_additions.BetaMain;
import net.skeagle.beta_additions.Nicks;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class Resources
{
    private final Resource nicks;
    private Resource warps;
    private final Nicks nickscfg = new Nicks(this);

    public Resources(BetaMain main) {
        this.nicks = new Resource("nicks.yml", main);
    }

    public void load(Player p) {
        this.nickscfg.loadNicks(p);
    }

    public void save(Player p) {
        this.nickscfg.save();
    }

    public Configuration getNicks() {
        return this.nicks.getSettings();
    }
}

