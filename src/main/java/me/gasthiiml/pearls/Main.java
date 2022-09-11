package me.gasthiiml.pearls;

import lombok.Getter;
import me.gasthiiml.pearls.listeners.GeneralListener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new GeneralListener(), this);
    }

}
