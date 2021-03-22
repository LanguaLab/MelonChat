package blue.melon.lab.minecraft.melonchat.bungeecord

import blue.melon.lab.minecraft.melonchat.Constant
import com.google.gson.GsonBuilder
import net.md_5.bungee.api.plugin.Plugin
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.IOException

class BungeeLoader : Plugin() {
    lateinit var bungeeSettings: BungeeSettings

    private lateinit var configFile: File
    private val gsonInstance = GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()

    override fun onEnable() {
        dataFolder.mkdir()
        configFile = File(dataFolder, "config.yml")
        bungeeSettings = loadConfigFile(configFile)
        saveSettings(configFile, bungeeSettings)

        if (bungeeSettings.channels.contains(Constant.TELL_FAILURE_NOTICE_CHANNEL)) {
            throw IllegalArgumentException("Channel ${Constant.TELL_FAILURE_NOTICE_CHANNEL} is system reversed, which is not be able to used as a broadcast channel.")
        }

        for (channel in bungeeSettings.channels) {
            proxy.registerChannel(Constant.CHANNEL_PREFIX + channel)
        }

        proxy.registerChannel(Constant.CHANNEL_PREFIX + Constant.TELL_FAILURE_NOTICE_CHANNEL)

        proxy.pluginManager.registerListener(this, ClientMessageListener(this))


    }


    @Throws(IOException::class)
    private fun loadConfigFile(configFile: File): BungeeSettings {
        return when {
            configFile.createNewFile() -> BungeeSettings()
            configFile.isDirectory -> throw IOException(configFile.absolutePath + " should be a file, but found a directory.")
            else -> {
                var bungeeSettingsInstance: BungeeSettings?
                val configFileReader = FileReader(configFile, Charsets.UTF_8)
                bungeeSettingsInstance = gsonInstance.fromJson(configFileReader, BungeeSettings::class.java)
                if (bungeeSettingsInstance == null) {
                    bungeeSettingsInstance = BungeeSettings()
                }
                bungeeSettingsInstance
            }
        }
    }

    @Throws(IOException::class)
    private fun saveSettings(configFile: File, bungeeSettingsInstance: BungeeSettings) {
        val fileOutputStream = FileOutputStream(configFile, false)
        fileOutputStream.write(gsonInstance.toJson(bungeeSettingsInstance).toByteArray(Charsets.UTF_8))
        fileOutputStream.flush()
        fileOutputStream.close()
    }
}