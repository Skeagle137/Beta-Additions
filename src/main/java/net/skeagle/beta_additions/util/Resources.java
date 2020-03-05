package net.skeagle.beta_additions.util;

import net.skeagle.beta_additions.BetaMain;
import net.skeagle.beta_additions.PlayerData;
import org.bukkit.entity.Player;

import java.io.File;

public class Resources
{
    private final Resource data;
    private Resource warps;
    private final PlayerData dataConfig;

    public Resources(BetaMain main) {
        this.data = new Resource(new File(main.getDataFolder(), "playerdata"), main);
        this.dataConfig = new PlayerData(this, main);
    }

    public void loadNicks(Player p) {
        this.dataConfig.loadNicks(p);
    }

    public void saveNicks(Player p) {
        this.dataConfig.saveNicks(p);
    }

    public File getDataFile() {
        return this.data.getFile();
    }
}

