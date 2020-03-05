package net.skeagle.beta_additions;

import net.skeagle.beta_additions.util.Resources;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import static net.skeagle.beta_additions.util.Commons.log;

public class Nicks {
    //TODO: fix this nightmare

    private final Resources r;

    public Nicks(Resources r) {
        this.r = r;
    }

    public void loadNicks(Player p) {
        this.r.getNicks().load();
        try {
            BufferedReader br = this.r.openNicks();
            String line;
            PlayerCache cache = PlayerCache.getCache(p);
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("#")) {
                    continue;
                }
                if (line.startsWith(p.getName().toLowerCase() + ":")) {
                    String value = line.replaceFirst(p.getName().toLowerCase() + ":", "");
                    if (value.trim().equals("")) {
                        return;
                    }
                    cache.setNickname(value);
                    p.setDisplayName(cache.getNickname());
                }
            }
            br.close();
        }
    }

    public void saveNicks(Player p) {
        try {
            BufferedReader br = this.r.openNicks();
            String line;
            PlayerCache cache = PlayerCache.getCache(p);
            HashMap<String, String> nicks = new HashMap<>();
            int lines = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("#")) {
                    continue;
                }
                String[] nicknames = new String[0];
                if (line.contains(":")) {
                    nicknames = line.trim().split(":");
                }
                nicks.put(nicknames[0], nicknames[1]);
                lines++;
            }
            this.r.regenNicks();
            BufferedWriter bw = this.r.writeToNicks();
            lines = 0;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("#")) {
                    continue;
                }
                String[] newNames = (String[]) nicks.entrySet().toArray();
                String[] newNicks = nicks.values().toArray(new String[0]);
                bw.write("\n" + newNames[lines] + ":" + newNicks[lines]);
            }
            bw.write("\n" + p.getName().toLowerCase() + ":" + cache.getNickname());
            bw.flush();
            bw.close();
            br.close();
        }
        catch (IOException e) {
            log(e, "Could not save to the nicknames file.");
        }
    }
}
