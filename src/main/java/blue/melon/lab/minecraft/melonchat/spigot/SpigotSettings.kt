package blue.melon.lab.minecraft.melonchat.spigot

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SpigotSettings {

    @SerializedName("channel")
    @Expose
    val channel = "standard"

    @SerializedName("usePlaceholderApi")
    @Expose
    var usePlaceholderApi = false

    @SerializedName("normalMessagePattern")
    @Expose
    val normalChatPattern = "&#F472B6\${source} &#FBCFE8> &#FDF2F8\${message}"

    @SerializedName("privateMessagePattern")
    @Expose
    val privateMessagePattern = "&#F472B6[\${source} &#A78BFA-> &#818CF8\${target}] &#EEF2FF\${message}"

    @SerializedName("playerOfflineNotice")
    @Expose
    val playerOfflineNotice = "&#4B5563It looks like \${target} is not available for now."

    @SerializedName("loopbackMessageNotice")
    @Expose
    val loopbackMessageNotice = "&#F9A8D4[MelonChat] &#C4B5FDYou are not able to send private message to yourself."

}