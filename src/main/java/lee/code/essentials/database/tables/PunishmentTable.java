package lee.code.essentials.database.tables;

import lee.code.core.ormlite.field.DatabaseField;
import lee.code.core.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@DatabaseTable(tableName = "punishment")
public class PunishmentTable {

    @DatabaseField(id = true, canBeNull = false)
    private UUID player;

    @DatabaseField(columnName = "staff", canBeNull = false)
    private String staff;

    @DatabaseField(columnName = "date_banned", canBeNull = false)
    private long dateBanned;

    @DatabaseField(columnName = "date_muted", canBeNull = false)
    private long dateMuted;

    @DatabaseField(columnName = "banned", canBeNull = false)
    private boolean banned;

    @DatabaseField(columnName = "temp_banned", canBeNull = false)
    private long tempBanned;

    @DatabaseField(columnName = "muted", canBeNull = false)
    private boolean muted;

    @DatabaseField(columnName = "temp_muted", canBeNull = false)
    private long tempMuted;

    @DatabaseField(columnName = "ban_reason", canBeNull = false)
    private String banReason;

    @DatabaseField(columnName = "mute_reason", canBeNull = false)
    private String muteReason;

    @DatabaseField(columnName = "bot", canBeNull = false)
    private boolean bot;

    public PunishmentTable(UUID player) {
        this.player = player;
        this.staff = "0";
        this.dateBanned = 0;
        this.dateMuted = 0;
        this.banned = false;
        this.tempBanned = 0;
        this.muted = false;
        this.tempMuted = 0;
        this.banReason = "0";
        this.muteReason = "0";
        this.bot = true;
    }
}