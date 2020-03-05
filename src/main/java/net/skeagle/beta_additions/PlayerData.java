package net.skeagle.beta_additions;

import net.skeagle.beta_additions.util.Resource;
import net.skeagle.beta_additions.util.Resources;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PlayerData {

    private final Resources r;
    private final Map<StringIgnoreCase, Resource> players;
    private final BetaMain main;

    public PlayerData(Resources r, BetaMain main) {
        this.players = new HashMap<>();
        this.r = r;
        this.main = main;
        if (!this.r.getDataFile().exists()) {
            this.r.getDataFile().mkdirs();
        }
    }

    public void saveNicks(Player p) {
        Resource res = this.players.get(new StringIgnoreCase(p.getName()));
        if (res == null) {
            File confFile = new File(this.r.getDataFile(), p.getName() + ".yml");
            res = new Resource(confFile, this.main);
            this.players.put(new StringIgnoreCase(p.getName()), res);
        }
        res.setProperty("nick", PlayerCache.getCache(p).getNickname());
        res.save();
    }

    public void loadNicks(Player p) {
        Resource res = this.players.get(new StringIgnoreCase(p.getName()));
        if (res == null) {
            return;
        }
        PlayerCache cache = PlayerCache.getCache(p);
        cache.setNickname(res.getString("nick"));
        p.setDisplayName(cache.getNickname());
    }

    private static class StringIgnoreCase {
        private final String string;

        StringIgnoreCase(String string) {
            this.string = string;
        }

        @Override
        public int hashCode() {
            return this.getString().toLowerCase().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof StringIgnoreCase && this.getString().equalsIgnoreCase(((StringIgnoreCase) o).getString());
        }

        String getString() {
            return this.string;
        }
    }
}
