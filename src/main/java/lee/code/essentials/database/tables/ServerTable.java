package lee.code.essentials.database.tables;

import lee.code.core.ormlite.field.DatabaseField;
import lee.code.core.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "server")
public class ServerTable {

    @DatabaseField(id = true, canBeNull = false)
    private String server;

    @DatabaseField(columnName = "spawn", canBeNull = false)
    private String spawn;

    @DatabaseField(columnName = "joins", canBeNull = false)
    private int joins;

    @DatabaseField(columnName = "resource_reset_time", canBeNull = false)
    private long resourceResetTime;

    @DatabaseField(columnName = "world_resource_spawn", canBeNull = false)
    private String worldResourceSpawn;

    @DatabaseField(columnName = "nether_resource_spawn", canBeNull = false)
    private String netherResourceSpawn;

    @DatabaseField(columnName = "end_resource_spawn", canBeNull = false)
    private String endResourceSpawn;

    @DatabaseField(columnName = "bot_check_enabled", canBeNull = false)
    private boolean botCheckEnabled;

    public ServerTable(String server) {
        this.server = server;
        this.spawn = "0";
        this.joins = 0;
        this.resourceResetTime = 0;
        this.worldResourceSpawn = "0";
        this.netherResourceSpawn = "0";
        this.endResourceSpawn = "0";
        this.botCheckEnabled = false;
    }
}