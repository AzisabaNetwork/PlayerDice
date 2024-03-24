package azisaba.playerdice;

import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerDice extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new UseDice(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
