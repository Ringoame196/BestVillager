package com.github.Ringoame196

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class CheckPoint {
    fun open(player: Player) {
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
        val GUI = Bukkit.createInventory(null, 9, "${ChatColor.DARK_BLUE}チェックポイントGUI")
        GUI().set_GUIitem(GUI, 4, Material.SLIME_BALL, "${ChatColor.GREEN}テレポート", "シフトクリックで登録")
        player.openInventory(GUI)
    }
    fun click(player: Player, item: ItemStack, shiftclick: Boolean) {
        if (item.type != Material.SLIME_BALL) { return }
        if (shiftclick) {
            save(player)
        } else {
            load(player)
        }
    }
    fun save(player: Player) {
        val location = set(player, player.location)

        player.sendMessage("${ChatColor.GREEN}チェックポイント登録しました")
        player.sendMessage("${ChatColor.AQUA}$location → ${LocationMessage(player.location)}")
        player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)

        // ymlファイルに保存する
        PlayerDataManager().savePlayerData(player.uniqueId, Data.DataManager.playerDataMap.getOrPut(player.uniqueId) { PlayerData() })
        player.closeInventory()
        return
    }
    fun load(player: Player) {
        val checkPoint: Location = GET().CheckPoint(player) ?: run {
            PlayerDataManager().loadPlayerLocation(player)
            GET().CheckPoint(player) ?: run {
                error(player, "${ChatColor.RED}チェックポイントが未設定です")
                return
            }
        }
        if (checkPoint.world != player.location.world) {
            error(player, "${ChatColor.RED}別のディメンションへ移動することはできません")
        } else {
            distance(player)
        }
    }
    fun set(player: Player, PlayerLocation: Location): String {
        val location = when (val checkpoint = GET().CheckPoint(player)) {
            null -> "未設定"
            else -> LocationMessage(checkpoint)
        }
        Data.DataManager.playerDataMap[player.uniqueId]?.CheckPoint = PlayerLocation
        return location
    }
    fun error(player: Player, message: String) {
        player.sendMessage(message)
        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 1f, 1f)
        player.closeInventory()
    }
    fun Teleport(player: Player) {
        player.teleport(GET().CheckPoint(player)!!)
        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
    }
    fun distance(player: Player) {
        val CheckPoint = GET().CheckPoint(player)
        val maxDistance = calculateMaxDistanceToLocation(player, CheckPoint!!)
        val estimate = 3 + maxDistance / 20
        val price = if (estimate >= 65) 64 else estimate
        ClaimGUI(player, price.toString(), maxDistance.toString())
    }
    fun ClaimGUI(player: Player, price: String, maxDistance: String) {
        val GUI = Bukkit.createInventory(null, 9, "${ChatColor.DARK_BLUE}[チェックポイント]請求")
        GUI().set_GUIitem(GUI, 0, Material.PAPER, "${ChatColor.GREEN}請求値段:鉄${price}個", "移動距離:${maxDistance}ブロック")
        GUI().set_GUIitem(GUI, 2, Material.COMMAND_BLOCK, "${ChatColor.YELLOW}支払い", "")
        for (i in 3..8) {
            GUI().set_GUIitem(GUI, i, Material.BARRIER, "${ChatColor.RED}選択禁止", "")
        }
        player.openInventory(GUI)
    }
    fun calculateMaxDistanceToLocation(player: Player, targetLocation: Location): Int {
        val deltaX = Math.abs(targetLocation.blockX - player.location.blockX)
        val deltaY = Math.abs(targetLocation.blockY - player.location.blockY)
        val deltaZ = Math.abs(targetLocation.blockZ - player.location.blockZ)

        return maxOf(deltaX, deltaY, deltaZ)
    }
    fun LocationMessage(location: Location): String {
        return "x:${location.x.toInt()},y:${location.y.toInt()},z:${location.z.toInt()}"
    }
    fun payment(GUI: InventoryView, player: Player) {
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
        var ironCount: Int = GUI.getItem(1)?.amount ?: 0
        val price = GUI.getItem(0)?.itemMeta?.displayName
        val regex = Regex("\\d+")
        val matchResult = regex.find(price.toString())
        val number = matchResult?.value?.toIntOrNull() ?: 0
        GUI.setItem(1, ItemStack(Material.AIR))
        if (ironCount >= number) {
            ironCount -= number
            Teleport(player)
        } else {
            error(player, "${ChatColor.RED}${(number - ironCount)}個鉄が足りません")
        }
        player.inventory.addItem(ItemStack(Material.IRON_INGOT, ironCount))
    }
    fun close(GUI: InventoryView, player: Player) {
        player.inventory.addItem(GUI.getItem(1))
    }
}
