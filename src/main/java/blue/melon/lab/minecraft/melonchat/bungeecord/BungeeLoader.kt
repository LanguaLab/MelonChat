package blue.melon.lab.minecraft.melonchat.bungeecord

import blue.melon.lab.minecraft.melonchat.Constant
import net.md_5.bungee.api.plugin.Plugin

class BungeeLoader : Plugin() {

    override fun onEnable() {
        proxy.registerChannel(Constant.STANDARD_CHANNEL)
        proxy.registerChannel(Constant.TELL_FAILURE_NOTICE_CHANNEL)
        proxy.pluginManager.registerListener(this, ClientMessageListener(this))
    }
}