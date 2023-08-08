package com.github.Ringoame196

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() { // 読み込み時
        super.onEnable()
        val event = Events()
        server.pluginManager.registerEvents(event, this)
        dataFolder.mkdirs()
    }
}
