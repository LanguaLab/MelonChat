package blue.melon.lab.minecraft.melonchat.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TellFailure(
    @Expose
    @SerializedName("source")
    val source: String,
    @Expose
    @SerializedName("target")
    val target: String
)