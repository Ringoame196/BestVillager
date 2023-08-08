package com.github.Ringoame196

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import java.io.IOException
import java.util.UUID

data class PlayerData(
    var CheckPoint: Location? = null
)

class PlayerDataManager() {

    private val playerDataFile = File("plugins/CheckPoint/player_data.yml")
    private val playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile)

    init {
        // プラグインデータフォルダにファイルが存在しない場合は新規作成
        if (!playerDataFile.exists()) {
            playerDataFile.createNewFile()
        }
    }

    // プレイヤーデータをYAMLファイルに保存
    fun savePlayerData(uniqueId: UUID, playerData: PlayerData) {
        playerDataConfig.set("$uniqueId.CheckPoint", playerData.CheckPoint?.serialize())

        try {
            playerDataConfig.save(playerDataFile)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    // プレイヤーデータをYAMLファイルからロード
    fun loadPlayerLocation(player: Player) {
        val uniqueId = player.uniqueId
        if (!playerDataConfig.contains("$uniqueId.CheckPoint")) { return }
        val locationData = playerDataConfig.getConfigurationSection("$uniqueId.CheckPoint")?.getValues(true) ?: return
        val worldName = locationData["world"] as? String
        val world = Bukkit.getWorld(worldName.toString()) ?: return
        val x = locationData["x"] as? Double
        val y = locationData["y"] as? Double
        val z = locationData["z"] as? Double
        val pitch = (locationData["pitch"] as? Number)?.toFloat() ?: 0.0f
        val yaw = (locationData["yaw"] as? Number)?.toFloat() ?: 0.0f

        val location = Location(world, x ?: 0.0, y ?: 0.0, z ?: 0.0, yaw, pitch)

        val playerData = GET().CheckPoint(player)
        if (playerData != null) { return }
        Data.DataManager.playerDataMap[player.uniqueId]?.CheckPoint = location
    }
}
