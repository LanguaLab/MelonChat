package blue.melon.lab.minecraft.melonchat.spigot

import blue.melon.lab.minecraft.melonchat.Constant
import blue.melon.lab.minecraft.melonchat.Utils
import blue.melon.lab.minecraft.melonchat.message.Message
import blue.melon.lab.minecraft.melonchat.message.MessageContainer
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

        if (args.size < 2) return false

        if (sender.name == args[0]) {
            sender.sendMessage(Utils.applyFormatCode(pluginInstance.spigotSettings.loopbackMessageNotice))
            return true
        }

        val pattern = when {
            pluginInstance.spigotSettings.usePlaceholderApi -> PlaceholderAPI.setPlaceholders(
                sender as Player,
                pluginInstance.spigotSettings.privateMessagePattern
            )
            else -> pluginInstance.spigotSettings.privateMessagePattern
        }

        val messageBuilder = StringBuilder()
        for (i in args.indices) {
            if (i == 0) continue
            messageBuilder.append(args[i])
            if (i < args.size - 1)
                messageBuilder.append(" ")
        }

        (sender as Player).sendPluginMessage(
            pluginInstance, Constant.STANDARD_CHANNEL,
            gsonInstance.toJson(
                MessageContainer(
                    sender.name,
                    args[0],
                    Message(
                        sender.name,
                        args[0],
                        pattern,
                        messageBuilder.toString()
                    )
                )
            ).toByteArray(Charsets.UTF_8)
        )
        return true
    }
}