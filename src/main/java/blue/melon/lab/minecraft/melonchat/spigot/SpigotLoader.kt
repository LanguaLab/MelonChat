package blue.melon.lab.minecraft.melonchat.spigot

import blue.melon.lab.minecraft.melonchat.Constant
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException

class SpigotLoader : JavaPlugin() {
    lateinit var spigotSettings: SpigotSettings
    private lateinit var configFile: File
    private val gsonInstance = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

    override fun onEnable() {
        dataFolder.mkdir()
        configFile = File(dataFolder, "config.json")
        spigotSettings = loadConfigFile(configFile)
        saveSettings(configFile, spigotSettings)

        if (spigotSettings.channel == Constant.TELL_FAILURE_NOTICE_CHANNEL) {
            Bukkit.getPluginManager().disablePlugin(this)
            throw IllegalArgumentException("Channel ${Constant.TELL_FAILURE_NOTICE_CHANNEL} is system reversed, which is not be able to used as a broadcast channel.")
        }

        Bukkit.getPluginManager().registerEvents(ChatListener(this), this)

        val bungeeMessageListener = BungeeMessageListener(this)
        this.server.messenger.registerIncomingPluginChannel(
            this,
            Constant.CHANNEL_PREFIX + spigotSettings.channel,
            bungeeMessageListener
        )
        this.server.messenger.registerIncomingPluginChannel(
            this,
            Constant.CHANNEL_PREFIX + Constant.TELL_FAILURE_NOTICE_CHANNEL,
            bungeeMessageListener
        )

        this.server.messenger.registerOutgoingPluginChannel(this, Constant.CHANNEL_PREFIX + spigotSettings.channel)
        this.server.messenger.registerOutgoingPluginChannel(
            this,
            Constant.CHANNEL_PREFIX + Constant.TELL_FAILURE_NOTICE_CHANNEL
        )

        Bukkit.getPluginCommand("tell")!!.setExecutor(TellCommand(this))
    }


    @Throws(IOException::class)
    private fun loadConfigFile(configFile: File): SpigotSettings {
        return when {
            configFile.createNewFile() -> SpigotSettings()
            configFile.isDirectory -> throw IOException(configFile.absolutePath + " should be a file, but found a directory.")
            else -> {
                var spigotSettingsInstance: SpigotSettings?
                val configFileReader = FileReader(configFile, Charsets.UTF_8)
                spigotSettingsInstance = gsonInstance.fromJson(configFileReader, SpigotSettings::class.java)
                if (spigotSettingsInstance == null) {
                    spigotSettingsInstance = SpigotSettings()
                }
                spigotSettingsInstance
            }
        }
    }

    @Throws(IOException::class)
    private fun saveSettings(configFile: File, spigotSettingsInstance: SpigotSettings) {
        val fileOutputStream = FileOutputStream(configFile, false)
        fileOutputStream.write(gsonInstance.toJson(spigotSettingsInstance).toByteArray(Charsets.UTF_8))
        fileOutputStream.flush()
        fileOutputStream.close()
    }

}