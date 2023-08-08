package com.github.Ringoame196

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Events : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val item = e.item
        if (item?.type == Material.BOOK && item.itemMeta?.displayName == "チェックポイント") {
            CheckPoint().open(player)
        }
    }
    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val player = e.whoClicked as Player
        val item = ItemStack(e.currentItem!!)
        val GUI = e.view
        when (GUI.title) {
            "${ChatColor.DARK_BLUE}チェックポイントGUI" -> {
                e.isCancelled = true
                CheckPoint().click(player, item, e.isShiftClick)
                player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
            }
            "${ChatColor.DARK_BLUE}[チェックポイント]請求" -> {
                if (item.type == Material.IRON_INGOT) { return }
                e.isCancelled = true
                if (item.type != Material.COMMAND_BLOCK) { return }
                CheckPoint().payment(GUI, player)
            }
        }
    }
    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val player = e.player as Player
        val GUI = e.view
        if (GUI.title != "${ChatColor.DARK_BLUE}[チェックポイント]請求") { return }
        CheckPoint().close(GUI, player)
    }
}
