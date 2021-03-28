package lee.code.essentials;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lee.code.cache.CacheAPI;
import lee.code.essentials.commands.cmds.*;
import lee.code.essentials.commands.tabs.*;
import lee.code.essentials.database.Cache;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.listeners.*;
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
    @Getter private Cache cache;
    @Getter private CacheAPI cacheAPI;
    @Getter private EssentialsAPI essentialsAPI;
    @Getter private ProtocolManager protocolManagerAPI;

    @Override
    public void onEnable() {
        this.pU = new PU();
        this.permissionManager = new PermissionManager();
        this.data = new Data();
        this.sqLite = new SQLite();
        this.tabListManager = new TabListManager();
        this.cache = new Cache();
        this.cacheAPI = new CacheAPI();
        this.essentialsAPI = new EssentialsAPI();
        this.protocolManagerAPI = ProtocolLibrary.getProtocolManager();

        sqLite.connect();
        sqLite.loadTables();

        data.cacheDatabase();
        data.loadListData();

        tabListManager.scheduleTabListUpdater();

        registerCommands();
        registerListeners();

        permissionManager.loadPerms();
        pU.registerTamedEntityFix();
    }

    @Override
    public void onDisable() {
        pU.kickOnlinePlayers();
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
        getCommand("teleportdeny").setExecutor(new TeleportDenyCMD());
        getCommand("sound").setExecutor(new SoundCMD());
        getCommand("glow").setExecutor(new GlowCMD());
        getCommand("itemrename").setExecutor(new ItemRenameCMD());
        getCommand("zap").setExecutor(new ZapCMD());
        getCommand("advancement").setExecutor(new AdvancementCMD());
        getCommand("ranklist").setExecutor(new RankListCMD());
        getCommand("setrank").setExecutor(new SetRankCMD());

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
        getCommand("rankup").setTabCompleter(new RankupTab());
        getCommand("setprefix").setTabCompleter(new SetPrefixTab());
        getCommand("setcolor").setTabCompleter(new SetColorTab());
        getCommand("teleport").setTabCompleter(new TeleportTab());
        getCommand("teleportaccept").setTabCompleter(new TeleportAcceptTab());
        getCommand("sound").setTabCompleter(new SoundTab());
        getCommand("glow").setTabCompleter(new GlowTab());
        getCommand("teleportdeny").setTabCompleter(new TeleportDenyTab());
        getCommand("itemrename").setTabCompleter(new ItemRenameTab());
        getCommand("zap").setTabCompleter(new ZapTab());
        getCommand("advancement").setTabCompleter(new AdvancementTab());
        getCommand("ranklist").setTabCompleter(new RankListTab());
        getCommand("setrank").setTabCompleter(new SetRankTab());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new ChairListener(), this);
        getServer().getPluginManager().registerEvents(new AchievementListener(), this);
        getServer().getPluginManager().registerEvents(new EnderPearlListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getServer().getPluginManager().registerEvents(new BookListener(), this);
        getServer().getPluginManager().registerEvents(new CommandTabListener(), this);

    }

    public static GoldmanEssentials getPlugin() {
        return GoldmanEssentials.getPlugin(GoldmanEssentials.class);
    }
}
