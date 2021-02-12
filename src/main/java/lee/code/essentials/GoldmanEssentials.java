package lee.code.essentials;

import lee.code.essentials.commands.cmds.*;
import lee.code.essentials.commands.tabs.*;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.listeners.ChatListener;
import lee.code.essentials.listeners.EntityListener;
import lee.code.essentials.listeners.JoinListener;
import lee.code.essentials.listeners.TameListener;
import lee.code.essentials.managers.PermissionManager;
import lee.code.essentials.managers.TabListManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldmanEssentials extends JavaPlugin {

    @Getter private PU pU;
    @Getter private PermissionManager permissionManager;
    @Getter private Data data;
    @Getter private SQLite sqLite;
    @Getter private TabListManager tabListManager;

    @Override
    public void onEnable() {
        this.pU = new PU();
        this.permissionManager = new PermissionManager();
        this.data = new Data();
        this.sqLite = new SQLite();
        this.tabListManager = new TabListManager();

        sqLite.connect();
        sqLite.loadTables();
        data.loadListData();
        tabListManager.scheduleTabListUpdater();

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        sqLite.disconnect();
    }

    private void registerCommands() {

        //cmds
        getCommand("spawn").setExecutor(new SpawnCMD());
        getCommand("setspawn").setExecutor(new SetSpawnCMD());
        getCommand("gamemode").setExecutor(new GameModeCMD());
        getCommand("fly").setExecutor(new FlyCMD());
        getCommand("flyspeed").setExecutor(new FlySpeedCMD());
        getCommand("world").setExecutor(new WorldCMD());
        getCommand("balance").setExecutor(new BalanceCMD());
        getCommand("balancetop").setExecutor(new BalanceTopCMD());
        getCommand("money").setExecutor(new MoneyCMD());
        getCommand("invsee").setExecutor(new InvseeCMD());
        getCommand("rankup").setExecutor(new RankupCMD());
        getCommand("setprefix").setExecutor(new SetPrefixCMD());
        getCommand("setcolor").setExecutor(new SetColorCMD());
        getCommand("teleport").setExecutor(new TeleportCMD());
        getCommand("teleportaccept").setExecutor(new TeleportAcceptCMD());
        getCommand("sound").setExecutor(new SoundCMD());
        getCommand("glow").setExecutor(new GlowCMD());

        //tabs
        getCommand("spawn").setTabCompleter(new SpawnTab());
        getCommand("setspawn").setTabCompleter(new SetSpawnTab());
        getCommand("gamemode").setTabCompleter(new GameModeTab());
        getCommand("fly").setTabCompleter(new FlyTab());
        getCommand("flyspeed").setTabCompleter(new FlySpeedTab());
        getCommand("world").setTabCompleter(new WorldTab());
        getCommand("balance").setTabCompleter(new BalanceTab());
        getCommand("balanceTop").setTabCompleter(new BalanceTopTab());
        getCommand("money").setTabCompleter(new MoneyTab());
        getCommand("setprefix").setTabCompleter(new SetPrefixTab());
        getCommand("setcolor").setTabCompleter(new SetColorTab());
        getCommand("teleport").setTabCompleter(new TeleportTab());
        getCommand("teleportaccept").setTabCompleter(new TeleportAcceptTab());
        getCommand("sound").setTabCompleter(new SoundTab());
        getCommand("glow").setTabCompleter(new GlowTab());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new TameListener(), this);
    }

    public static GoldmanEssentials getPlugin() {
        return GoldmanEssentials.getPlugin(GoldmanEssentials.class);
    }
}
