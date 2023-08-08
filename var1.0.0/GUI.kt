package com.github.Ringoame196

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class GUI {
    fun set_GUIitem(GUI: Inventory, number: Int, set_item: Material, displayname: String, lore: String) {
        // GUIにアイテムを楽にセットする
        val item = ItemStack(set_item)
        val itemMeta: ItemMeta? = item.itemMeta
        itemMeta?.setDisplayName(displayname)
        lore(itemMeta, lore)
        item.setItemMeta(itemMeta)
        GUI.setItem(number, item)
    }

    fun lore(meta: ItemMeta?, lore: String) {
        val loreList: MutableList<String> = mutableListOf(lore)
        meta?.lore = loreList
    }
}
