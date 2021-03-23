package blue.melon.lab.minecraft.melonchat.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MessageContainer(
    @Expose
    @SerializedName("from")
    val from: String,
    @Expose
    @SerializedName("to")
    val to: String,
    @Expose
    @SerializedName("content")
    val content: Message
)