package blue.melon.lab.minecraft.melonchat.spigot

import blue.melon.lab.minecraft.melonchat.Constant
import blue.melon.lab.minecraft.melonchat.Utils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.IOException

class MelonChatCommand(private val pluginInstance: SpigotLoader) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        when (args[0]) {
            "reload" -> {
                try {
                    pluginInstance.reloadConfigFile(sender)
                    sender.sendMessage(Utils.applyFormatCode("${Constant.PLUGIN_PREFIX_COLOR}[MelonChat] ${Constant.PLUGIN_NOTICE_COLOR}plugin reloaded."))
                } catch (exception: IOException) {
                    exception.printStackTrace()
                    sender.sendMessage(Utils.applyFormatCode("${Constant.PLUGIN_PREFIX_COLOR}[MelonChat] ${Constant.PLUGIN_NOTICE_COLOR}IOException occurred while reloading the plugin. Check console for more information."))
                }
                return true
            }
        }
        return false
    }
}