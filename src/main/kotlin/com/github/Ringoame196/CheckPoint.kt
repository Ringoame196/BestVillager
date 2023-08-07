package com.github.Ringoame196

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class CheckPoint {
    fun open(player: Player) {
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
        val TGUI = Bukkit.createInventory(null, 9, "${ChatColor.DARK_BLUE}チェックポイントGUI")
        GUI().set_GUIitem(TGUI, 4, Material.SLIME_BALL, "${ChatColor.GREEN}テレポート", "シフトクリックで登録")
        player.openInventory(TGUI)
    }
    fun click(player: Player, item: ItemStack) {
        if (item.type != Material.SLIME_BALL || item.itemMeta?.displayName != "${ChatColor.GREEN}テレポート") { return }
        if(player.isSneaking){

        }
        else {

        }
    }
}
