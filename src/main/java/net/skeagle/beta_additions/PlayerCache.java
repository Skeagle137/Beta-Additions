package net.skeagle.beta_additions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class PlayerCache {

    private static Map<String, PlayerCache> cacheMap = new HashMap<>();

    private String nickname;

    public static PlayerCache getCache(final Player player) {
        PlayerCache cache = cacheMap.get(player.getName());

        if (cache == null) {
            cache = new PlayerCache();

            cacheMap.put(player.getName(), cache);
        }

        return cache;
    }
}