package blue.melon.lab.minecraft.melonchat.spigot

import blue.melon.lab.minecraft.melonchat.Constant
import blue.melon.lab.minecraft.melonchat.Utils
import blue.melon.lab.minecraft.melonchat.message.Message
import blue.melon.lab.minecraft.melonchat.message.TellFailure
import com.google.gson.GsonBuilder
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

class BungeeMessageListener(private val pluginInstance: SpigotLoader) : PluginMessageListener {
    private val gsonInstance = GsonBuilder().disableHtmlEscaping().create()

    override fun onPluginMessageReceived(channel: String, player: Player, messageByteArray: ByteArray) {
        if (!channel.startsWith("melon_chat:")) return

        when (channel.substring(11, channel.length)) {
            Constant.STANDARD_CHANNEL -> {
                val message = gsonInstance.fromJson(
                    String(messageByteArray, Charsets.UTF_8),
                    Message::class.java
                )

                when (message.target) {
                    "MELON_CHAT_REVERSED_BROADCAST" -> {
                        Bukkit.getOnlinePlayers().forEach {
                            it.sendMessage(Utils.applyFormatCode(Utils.applyPatternFromMessageInstance(message)))
                        }
                    }
                    else -> {
                        val player = Bukkit.getPlayerExact(message.target) ?: return
                        player.sendMessage(Utils.applyFormatCode(Utils.applyPatternFromMessageInstance(message)))
                    }
                }
            }

            Constant.TELL_FAILURE_NOTICE_CHANNEL -> {
                val tellFailure =
                    gsonInstance.fromJson(String(messageByteArray, Charsets.UTF_8), TellFailure::class.java)
                val message = when {
                    pluginInstance.spigotSettings.usePlaceholderApi -> PlaceholderAPI.setPlaceholders(
                        player,
                        pluginInstance.spigotSettings.playerOfflineNotice
                    )
                    else -> pluginInstance.spigotSettings.playerOfflineNotice
                }.replace("\${source}", tellFailure.source).replace("\${target}", tellFailure.target)
                Bukkit.getPlayerExact(tellFailure.source)?.sendMessage(
                    Utils.applyFormatCode(message)
                )
            }
        }


//        var splittedMessage = message.message.split("\${source}")
//        var messageComponent = TextComponent()
//        for ((tagged, part) in splittedMessage.withIndex()) {
//            messageComponent.addExtra(part)
//            if (tagged < splittedMessage.size - 1) {
//                messageComponent.addExtra(getNameTag(message.source))
//            }
//        }
//
//        splittedMessage = messageComponent.toLegacyText().split("\${target}")
//        messageComponent = TextComponent()
//        for ((tagged, part) in splittedMessage.withIndex()) {
//            messageComponent.addExtra(part)
//            if (tagged < splittedMessage.size - 1) {
//                messageComponent.addExtra(getNameTag(message.target))
//            }
//        }
    }


//    private fun getNameTag(userInfo: UserInfo): TextComponent {
//        val nameTag = TextComponent(userInfo.name)
//        nameTag.hoverEvent = (HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(userInfo.uuid)))
//        return nameTag
//    }

}