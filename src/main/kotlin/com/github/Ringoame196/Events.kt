package com.github.Ringoame196

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
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
        if (GUI.title == "${ChatColor.DARK_BLUE}チェックポイントGUI")
            CheckPoint().click(player, item)
    }
}
