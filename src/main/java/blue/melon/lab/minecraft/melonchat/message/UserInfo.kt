package blue.melon.lab.minecraft.melonchat.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserInfo(
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("uuid")
    val uuid: String
)