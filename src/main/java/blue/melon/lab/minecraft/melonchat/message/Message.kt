package blue.melon.lab.minecraft.melonchat.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Message(
    @Expose
    @SerializedName("source")
    val source: String,
    @Expose
    @SerializedName("target")
    val target: String,
    @Expose
    @SerializedName("pattern")
    val pattern: String,
    @Expose
    @SerializedName("message")
    val message: String,
)