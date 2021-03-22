package blue.melon.lab.minecraft.melonchat.bungeecord

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BungeeSettings {
    @SerializedName("channels")
    @Expose
    val channels = ArrayList<String>().also { it.add("standard") }
}