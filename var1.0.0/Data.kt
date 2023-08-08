package com.github.Ringoame196

import java.util.UUID

class Data {
    object DataManager {
        val playerDataMap: MutableMap<UUID, PlayerData> = mutableMapOf()
    }
}
