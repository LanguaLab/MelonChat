package blue.melon.lab.minecraft.melonchat.bungeecord

import blue.melon.lab.minecraft.melonchat.Constant
import blue.melon.lab.minecraft.melonchat.message.Message
import blue.melon.lab.minecraft.melonchat.message.TellFailure
import com.google.gson.GsonBuilder
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class ClientMessageListener(private val pluginInstance: BungeeLoader) : Listener {
    private val gsonInstance = GsonBuilder().disableHtmlEscaping().create()

    @EventHandler
    fun onPluginMessage(event: PluginMessageEvent) {
        val message = gsonInstance.fromJson(String(event.data, Charsets.UTF_8), Message::class.java)

        when (message.target) {
            "MELON_CHAT_REVERSED_BROADCAST" -> {
                pluginInstance.proxy.servers.values.forEach {
                    it.sendData(event.tag, event.data, false)
                }
            }

            else -> {
                val targetPlayer = pluginInstance.proxy.getPlayer(message.target)
                if (targetPlayer == null) {
                    pluginInstance.proxy.getPlayer(message.source).server.info.sendData(
                        Constant.TELL_FAILURE_NOTICE_CHANNEL, gsonInstance.toJson(
                            TellFailure(
                                message.source,
                                message.target
                            )
                        ).toByteArray(Charsets.UTF_8)
                    )
                } else {
                    targetPlayer.server.info.sendData(event.tag, event.data)
                    pluginInstance.proxy.getPlayer(message.source).server.info.sendData(event.tag, event.data)
                }
            }
        }
    }
}