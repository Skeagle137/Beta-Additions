package net.skeagle.beta_additions.tasks;

import net.skeagle.beta_additions.BetaMain;
import net.skeagle.beta_additions.util.Commons;

public class AutoSave {

    public AutoSave(BetaMain main) {
        this.autoSave(main);
    }

    private void autoSave(BetaMain main) {
        main.getServer().getScheduler().scheduleSyncRepeatingTask(main, () ->
                Commons.dispatchCommand("save-all"), 0L, 20L * 60 * 60 * 6); //6 hours
    }
}
