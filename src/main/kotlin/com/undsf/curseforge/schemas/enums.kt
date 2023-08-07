package com.undsf.curseforge.schemas

enum class ModLoaderType(val value: Int) {
    Any(0),
    Forge(1),
    Cauldron(2),
    LiteLoader(3),
    Fabric(4),
    Quilt(5),
    NeoForge(6);

    companion object {
        fun valueOf(value: Int): ModLoaderType? {
            return ModLoaderType.values().find { it.value == value }
        }
    }
}

enum class ModsSearchSortField(val value: Int) {
    Featured(1),
    Popularity(2),
    LastUpdated(3),
    Name(4),
    Author(5),
    TotalDownloads(6),
    Category(7),
    GameVersion(8),
    EarlyAccess(9);
}

enum class ModStatus(val value: Int) {
    New(1),
    ChangesRequired(2),
    UnderSoftReview(3),
    Approved(4),
    Rejected(5),
    ChangesMade(6),
    Inactive(7),
    Abandoned(8),
    Deleted(9),
    UnderReview(10);
}

enum class ReleaseType {
    Unknown,
    Release,
    Beta,
    Alpha;
}