package blue.melon.lab.minecraft.melonchat.bungeecord

import blue.melon.lab.minecraft.melonchat.Constant
import blue.melon.lab.minecraft.melonchat.message.MessageContainer
import blue.melon.lab.minecraft.melonchat.message.TellFailure
import com.google.gson.GsonBuilder
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class ClientMessageListener(private val pluginInstance: BungeeLoader) : Listener {
    private val gsonInstance = GsonBuilder().disableHtmlEscaping().create()

    @EventHandler
    fun onPluginMessage(event: PluginMessageEvent) {
        if (!event.tag.startsWith(Constant.CHANNEL_PREFIX)) {
            return
        }

        val messageContainer = gsonInstance.fromJson(String(event.data, Charsets.UTF_8), MessageContainer::class.java)

        when (messageContainer.to) {
            Constant.BROADCAST_CHANNEL -> {
                pluginInstance.proxy.servers.values.forEach {
                    it.sendData(event.tag, event.data, false)
                }
            }

            else -> {
                val targetPlayer = pluginInstance.proxy.getPlayer(messageContainer.to)
                if (targetPlayer == null) {
                    pluginInstance.proxy.getPlayer(messageContainer.from).server.info.sendData(
                        Constant.TELL_FAILURE_NOTICE_CHANNEL, gsonInstance.toJson(
                            TellFailure(
                                messageContainer.from,
                                messageContainer.to
                            )
                        ).toByteArray(Charsets.UTF_8)
                    )
                } else {
                    targetPlayer.server.info.sendData(event.tag, event.data)
                    pluginInstance.proxy.getPlayer(messageContainer.from).server.info.sendData(
                        event.tag, gsonInstance.toJson(
                            MessageContainer(
                                Constant.SYSTEM_CHANNEL,
                                messageContainer.from,
                                messageContainer.content
                            )
                        ).toByteArray(Charsets.UTF_8)
                    )
                }
            }
        }
    }
}