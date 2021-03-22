package blue.melon.lab.minecraft.melonchat.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Text {
    @Expose
    @SerializedName("text")
    var text = ""

    @Expose
    @SerializedName("bold")
    var bold: Boolean? = null

    @Expose
    @SerializedName("italic")
    var italic: Boolean? = null

    @Expose
    @SerializedName("underlined")
    var underlined: Boolean? = null

    @Expose
    @SerializedName("strikethrough")
    var strikethrough: Boolean? = null

    @Expose
    @SerializedName("obfuscated")
    var obfuscated: Boolean? = null

    @Expose
    @SerializedName("color")
    var color = "white"


    constructor(text: String, format: TextFormat) {
        this.text = text
        bold = format.bold
        italic = format.italic
        underlined = format.underlined
        strikethrough = format.strikethrough
        obfuscated = format.obfuscated
        color = format.color
    }
}