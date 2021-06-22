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
        data.loadMOTDFile();

        tabListManager.scheduleTabListUpdater();

        registerCommands();
        registerListeners();

        permissionManager.loadPerms();
        pU.registerTamedEntityFix();
        pU.scheduleEntityChunkCleaner();
        pU.scheduleBalanceTopUpdater();
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
        getCommand("summon").setExecutor(new SummonCMD());
        getCommand("message").setExecutor(new MessageCMD());
        getCommand("reply").setExecutor(new ReplyCMD());
        getCommand("enchant").setExecutor(new EnchantCMD());
        getCommand("heal").setExecutor(new HealCMD());
        getCommand("god").setExecutor(new GodCMD());
        getCommand("vanish").setExecutor(new VanishCMD());
        getCommand("head").setExecutor(new HeadCMD());
        getCommand("feed").setExecutor(new FeedCMD());
        getCommand("time").setExecutor(new TimeCMD());
        getCommand("staffchat").setExecutor(new StaffChatCMD());
        getCommand("give").setExecutor(new GiveCMD());
        getCommand("weather").setExecutor(new WeatherCMD());
        getCommand("help").setExecutor(new HelpCMD());
        getCommand("sell").setExecutor(new SellCMD());
        getCommand("worth").setExecutor(new WorthCMD());
        getCommand("sellall").setExecutor(new SellAllCMD());
        getCommand("home").setExecutor(new HomeCMD());
        getCommand("sethome").setExecutor(new SetHomeCMD());
        getCommand("deletehome").setExecutor(new DeleteHomeCMD());
        getCommand("ban").setExecutor(new BanCMD());
        getCommand("tempban").setExecutor(new TempBanCMD());
        getCommand("unban").setExecutor(new UnBanCMD());
        getCommand("banlist").setExecutor(new BanListCMD());
        getCommand("mute").setExecutor(new MuteCMD());
        getCommand("unmute").setExecutor(new UnMuteCMD());
        getCommand("kick").setExecutor(new KickCMD());
        getCommand("colors").setExecutor(new ColorsCMD());
        getCommand("playtime").setExecutor(new PlayTimeCMD());
        getCommand("enderchest").setExecutor(new EnderChestCMD());
        getCommand("essreload").setExecutor(new ReloadCMD());
        getCommand("itemlore").setExecutor(new ItemLoreCMD());
        getCommand("setsuffix").setExecutor(new SetSuffixCMD());

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
        getCommand("summon").setTabCompleter(new SummonTab());
        getCommand("message").setTabCompleter(new MessageTab());
        getCommand("reply").setTabCompleter(new ReplyTab());
        getCommand("enchant").setTabCompleter(new EnchantTab());
        getCommand("heal").setTabCompleter(new HealTab());
        getCommand("god").setTabCompleter(new GodTab());
        getCommand("vanish").setTabCompleter(new VanishTab());
        getCommand("head").setTabCompleter(new HeadTab());
        getCommand("feed").setTabCompleter(new FeedTab());
        getCommand("time").setTabCompleter(new TimeTab());
        getCommand("staffchat").setTabCompleter(new StaffChatTab());
        getCommand("give").setTabCompleter(new GiveTab());
        getCommand("weather").setTabCompleter(new WeatherTab());
        getCommand("help").setTabCompleter(new HelpTab());
        getCommand("sell").setTabCompleter(new HelpTab());
        getCommand("worth").setTabCompleter(new WorthTab());
        getCommand("sellall").setTabCompleter(new SellAllTab());
        getCommand("home").setTabCompleter(new HomeTab());
        getCommand("sethome").setTabCompleter(new SetHomeTab());
        getCommand("deletehome").setTabCompleter(new DeleteHomeTab());
        getCommand("ban").setTabCompleter(new BanTab());
        getCommand("tempban").setTabCompleter(new TempBanTab());
        getCommand("unban").setTabCompleter(new UnBanTab());
        getCommand("banlist").setTabCompleter(new BanListTab());
        getCommand("mute").setTabCompleter(new MuteTab());
        getCommand("unmute").setTabCompleter(new UnMuteTab());
        getCommand("kick").setTabCompleter(new KickTab());
        getCommand("colors").setTabCompleter(new ColorsTab());
        getCommand("playtime").setTabCompleter(new PlayTimeTab());
        getCommand("invsee").setTabCompleter(new InvseeTab());
        getCommand("enderchest").setTabCompleter(new EnderChestTab());
        getCommand("essreload").setTabCompleter(new ReloadTab());
        getCommand("itemlore").setTabCompleter(new ItemLoreTab());
        getCommand("setsuffix").setTabCompleter(new SetSuffixTab());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new ChunkEntityListener(), this);
        getServer().getPluginManager().registerEvents(new ChairListener(), this);
        getServer().getPluginManager().registerEvents(new AchievementListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);
        getServer().getPluginManager().registerEvents(new AnvilListener(), this);
        getServer().getPluginManager().registerEvents(new BookListener(), this);
        getServer().getPluginManager().registerEvents(new CommandTabListener(), this);
        getServer().getPluginManager().registerEvents(new HatListener(), this);
        getServer().getPluginManager().registerEvents(new GodModeListener(), this);
        getServer().getPluginManager().registerEvents(new SleepListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorStandListener(), this);
        getServer().getPluginManager().registerEvents(new HeadDropListener(), this);
        getServer().getPluginManager().registerEvents(new HopperFilterListener(), this);
        getServer().getPluginManager().registerEvents(new PvPListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }

    public static GoldmanEssentials getPlugin() {
        return GoldmanEssentials.getPlugin(GoldmanEssentials.class);
    }
}
