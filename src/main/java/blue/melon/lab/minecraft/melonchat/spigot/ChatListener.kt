package blue.melon.lab.minecraft.melonchat.spigot

import blue.melon.lab.minecraft.melonchat.Constant
import blue.melon.lab.minecraft.melonchat.message.Message
import com.google.gson.GsonBuilder
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class ChatListener(private val pluginInstance: SpigotLoader) : Listener {

    private val gsonInstance = GsonBuilder().disableHtmlEscaping().create()

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        event.isCancelled = true

        val pattern = when {
            pluginInstance.spigotSettings.usePlaceholderApi -> PlaceholderAPI.setPlaceholders(
                event.player,
                pluginInstance.spigotSettings.normalChatPattern
            )
            else -> pluginInstance.spigotSettings.normalChatPattern
        }

        event.player.sendPluginMessage(
            pluginInstance,
            Constant.CHANNEL_PREFIX + pluginInstance.spigotSettings.channel,
            gsonInstance.toJson(
                Message(
                    pattern,
                    event.player.name,
                    "MELON_CHAT_REVERSED_BROADCAST",
                    event.message
                )
            )
                .toByteArray(Charsets.UTF_8)
        )
    }
}