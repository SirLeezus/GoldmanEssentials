package lee.code.essentials;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lee.code.cache.CacheAPI;
import lee.code.chunks.ChunkAPI;
import lee.code.enchants.EnchantsAPI;
import lee.code.essentials.commands.cmds.*;
import lee.code.essentials.commands.tabs.*;
import lee.code.essentials.database.Cache;
import lee.code.essentials.database.SQLite;
import lee.code.essentials.hooks.Pl3xMapHook;
import lee.code.essentials.listeners.*;
import lee.code.essentials.managers.PermissionManager;
import lee.code.essentials.managers.WorldManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldmanEssentials extends JavaPlugin {

    @Getter private PU pU;
    @Getter private PermissionManager permissionManager;
    @Getter private Data data;
    @Getter private SQLite sqLite;
    @Getter private WorldManager worldManager;
    @Getter private Cache cache;
    @Getter private CacheAPI cacheAPI;
    @Getter private EssentialsAPI essentialsAPI;
    @Getter private EnchantsAPI enchantsAPI;
    @Getter private ProtocolManager protocolManagerAPI;
    @Getter private ChunkAPI chunkAPI;
    @Getter private Pl3xMapHook pl3xMapHook;

    @Getter private boolean pl3xMapInstalled = false;

    @Override
    public void onEnable() {
        checkDependencies();
        this.pU = new PU();
        this.permissionManager = new PermissionManager();
        this.data = new Data();
        this.sqLite = new SQLite();
        this.cache = new Cache();
        this.chunkAPI = new ChunkAPI();
        this.cacheAPI = new CacheAPI();
        this.essentialsAPI = new EssentialsAPI();
        this.enchantsAPI = new EnchantsAPI();
        this.worldManager = new WorldManager();
        this.protocolManagerAPI = ProtocolLibrary.getProtocolManager();
        if (pl3xMapInstalled) this.pl3xMapHook = new Pl3xMapHook();

        registerCommands();
        registerListeners();

        sqLite.connect();
        //sqLite.updateTable(SQLTables.PLAYER_DATA);

        sqLite.loadTables();
        data.cacheDatabase();

        worldManager.resourceWorldResets();
        data.loadListData();
        data.loadMOTDFile();

        permissionManager.loadPerms();
        pU.registerTamedEntityFix();
        pU.scheduleEntityChunkCleaner();
        pU.scheduleBoosterChecker();
        pU.scheduleAutoRestart();
        pU.scheduleTabListUpdater();
        pU.scheduleAFKChecker();
        pU.schedulePlayTimeChecker();
        pU.clearScoreBoard();
    }

    @Override
    public void onDisable() {
        pU.kickOnlinePlayers();
        sqLite.disconnect();
        if (pl3xMapInstalled) pl3xMapHook.disable();
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
        getCommand("back").setExecutor(new BackCMD());
        getCommand("teleportaccept").setExecutor(new TeleportAcceptCMD());
        getCommand("teleportdeny").setExecutor(new TeleportDenyCMD());
        getCommand("sound").setExecutor(new SoundCMD());
        getCommand("glow").setExecutor(new GlowCMD());
        getCommand("itemname").setExecutor(new ItemNameCMD());
        getCommand("zap").setExecutor(new ZapCMD());
        getCommand("advancement").setExecutor(new AdvancementCMD());
        getCommand("checkadvancement").setExecutor(new CheckAdvancementCMD());
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
        getCommand("tempmute").setExecutor(new TempMuteCMD());
        getCommand("unmute").setExecutor(new UnMuteCMD());
        getCommand("kick").setExecutor(new KickCMD());
        getCommand("colors").setExecutor(new ColorsCMD());
        getCommand("playtime").setExecutor(new PlayTimeCMD());
        getCommand("playtimetop").setExecutor(new PlayTimeTopCMD());
        getCommand("enderchest").setExecutor(new EnderChestCMD());
        getCommand("essreload").setExecutor(new ReloadCMD());
        getCommand("itemlore").setExecutor(new ItemLoreCMD());
        getCommand("setsuffix").setExecutor(new SetSuffixCMD());
        getCommand("randomteleport").setExecutor(new RandomTeleportCMD());
        getCommand("rules").setExecutor(new RulesCMD());
        getCommand("addperm").setExecutor(new AddPermCMD());
        getCommand("removeperm").setExecutor(new RemovePermCMD());
        getCommand("serversendmessage").setExecutor(new ServerSendMessageCMD());
        getCommand("namecolor").setExecutor(new NameColorCMD());
        getCommand("clear").setExecutor(new ClearCMD());
        getCommand("spawner").setExecutor(new SpawnerCMD());
        getCommand("addbooster").setExecutor(new AddBoosterCMD());
        getCommand("removebooster").setExecutor(new RemoveBoosterCMD());
        getCommand("booster").setExecutor(new BoosterCMD());
        getCommand("restartwarning").setExecutor(new RestartWarningCMD());
        getCommand("motd").setExecutor(new MessageOfTheDayCMD());
        getCommand("pay").setExecutor(new PayCMD());
        getCommand("resourceworlds").setExecutor(new ResourceWorldsCMD());
        getCommand("resetresourceworlds").setExecutor(new ResetResourceWorldsCMD());
        getCommand("seen").setExecutor(new SeenCMD());
        getCommand("iteminfo").setExecutor(new ItemInfoCMD());
        getCommand("afk").setExecutor(new AfkCMD());
        getCommand("vote").setExecutor(new VoteCMD());
        getCommand("votetop").setExecutor(new VoteTopCMD());
        getCommand("trade").setExecutor(new TradeCMD());
        getCommand("duel").setExecutor(new DuelCMD());
        getCommand("sort").setExecutor(new SortCMD());
        getCommand("bottlexp").setExecutor(new BottleXPCMD());
        getCommand("emojis").setExecutor(new EmojisCMD());

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
        getCommand("back").setTabCompleter(new BackTab());
        getCommand("teleportaccept").setTabCompleter(new TeleportAcceptTab());
        getCommand("sound").setTabCompleter(new SoundTab());
        getCommand("glow").setTabCompleter(new GlowTab());
        getCommand("teleportdeny").setTabCompleter(new TeleportDenyTab());
        getCommand("itemname").setTabCompleter(new ItemNameTab());
        getCommand("zap").setTabCompleter(new ZapTab());
        getCommand("advancement").setTabCompleter(new AdvancementTab());
        getCommand("checkadvancement").setTabCompleter(new CheckAdvancementTab());
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
        getCommand("sell").setTabCompleter(new SellTab());
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
        getCommand("tempmute").setTabCompleter(new TempMuteTab());
        getCommand("unmute").setTabCompleter(new UnMuteTab());
        getCommand("kick").setTabCompleter(new KickTab());
        getCommand("colors").setTabCompleter(new ColorsTab());
        getCommand("playtime").setTabCompleter(new PlayTimeTab());
        getCommand("playtimetop").setTabCompleter(new PlayTimeTopTab());
        getCommand("invsee").setTabCompleter(new InvseeTab());
        getCommand("enderchest").setTabCompleter(new EnderChestTab());
        getCommand("essreload").setTabCompleter(new ReloadTab());
        getCommand("itemlore").setTabCompleter(new ItemLoreTab());
        getCommand("setsuffix").setTabCompleter(new SetSuffixTab());
        getCommand("randomteleport").setTabCompleter(new RandomTeleportTab());
        getCommand("rules").setTabCompleter(new RulesTab());
        getCommand("addperm").setTabCompleter(new AddPermTab());
        getCommand("removeperm").setTabCompleter(new RemovePermTab());
        getCommand("serversendmessage").setTabCompleter(new ServerSendMessageTab());
        getCommand("namecolor").setTabCompleter(new NameColorTab());
        getCommand("clear").setTabCompleter(new ClearTab());
        getCommand("spawner").setTabCompleter(new SpawnerTab());
        getCommand("addbooster").setTabCompleter(new AddBoosterTab());
        getCommand("removebooster").setTabCompleter(new RemoveBoosterTab());
        getCommand("booster").setTabCompleter(new BoosterTab());
        getCommand("restartwarning").setTabCompleter(new RestartWarningTab());
        getCommand("motd").setTabCompleter(new MessageOfTheDayTab());
        getCommand("pay").setTabCompleter(new PayTab());
        getCommand("resourceworlds").setTabCompleter(new ResourceWorldsTab());
        getCommand("resetresourceworlds").setTabCompleter(new ResetResourceWorldsTab());
        getCommand("seen").setTabCompleter(new SeenTab());
        getCommand("iteminfo").setTabCompleter(new ItemInfoTab());
        getCommand("afk").setTabCompleter(new AfkTab());
        getCommand("vote").setTabCompleter(new VoteTab());
        getCommand("votetop").setTabCompleter(new VoteTopTab());
        getCommand("trade").setTabCompleter(new TradeTab());
        getCommand("duel").setTabCompleter(new DuelTab());
        getCommand("sort").setTabCompleter(new SortTab());
        getCommand("bottlexp").setTabCompleter(new BottleXPTab());
        getCommand("emojis").setTabCompleter(new EmojisTab());
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
        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
        getServer().getPluginManager().registerEvents(new BackListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getPluginManager().registerEvents(new BoosterListener(), this);
        getServer().getPluginManager().registerEvents(new ItemFrameListener(), this);
        getServer().getPluginManager().registerEvents(new DragonEggListener(), this);
        getServer().getPluginManager().registerEvents(new PortalListener(), this);
        getServer().getPluginManager().registerEvents(new AFKListener(), this);
        getServer().getPluginManager().registerEvents(new VoteListener(), this);
        getServer().getPluginManager().registerEvents(new PatchesListener(), this);
        getServer().getPluginManager().registerEvents(new WrenchListener(), this);
        getServer().getPluginManager().registerEvents(new DurabilityListener(), this);
        getServer().getPluginManager().registerEvents(new TargetBlockListener(), this);
        getServer().getPluginManager().registerEvents(new HiveBlockListener(), this);
        getServer().getPluginManager().registerEvents(new CustomMobSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new MineCartListener(), this);
        getServer().getPluginManager().registerEvents(new TotemListener(), this);
    }

    private void checkDependencies() {
       pl3xMapInstalled = getServer().getPluginManager().getPlugin("Pl3xMap") != null;
    }

    public static GoldmanEssentials getPlugin() {
        return GoldmanEssentials.getPlugin(GoldmanEssentials.class);
    }
}
