package blue.melon.lab.minecraft.melonchat.spigot

import blue.melon.lab.minecraft.melonchat.Constant
import blue.melon.lab.minecraft.melonchat.message.Message
import com.google.gson.GsonBuilder
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TellCommand(private val pluginInstance: SpigotLoader) : CommandExecutor {
    private val gsonInstance = GsonBuilder().disableHtmlEscaping().create()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be used by a player.")
        }

        if (args.size != 2) return false

        val pattern = when {
            pluginInstance.spigotSettings.usePlaceholderApi -> PlaceholderAPI.setPlaceholders(
                sender as Player,
                pluginInstance.spigotSettings.privateMessagePattern
            )
            else -> pluginInstance.spigotSettings.privateMessagePattern
        }

        (sender as Player).sendPluginMessage(
            pluginInstance, Constant.CHANNEL_PREFIX + pluginInstance.spigotSettings.channel,
            gsonInstance.toJson(
                Message(
                    pattern,
                    sender.name,
                    args[0],
                    args[1]
                )
            ).toByteArray(Charsets.UTF_8)
        )
        return true
    }
}