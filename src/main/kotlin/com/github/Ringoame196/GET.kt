package com.github.Ringoame196

import org.bukkit.entity.Player

class GET {
    fun CheckPoint(player: Player): org.bukkit.Location? {
        val CheckPoint = Data.DataManager.playerDataMap.getOrPut(player.uniqueId) { PlayerData() }.CheckPoint
        return CheckPoint
    }
}
