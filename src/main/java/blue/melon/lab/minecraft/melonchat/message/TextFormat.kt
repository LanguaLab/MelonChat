package blue.melon.lab.minecraft.melonchat.message

class TextFormat {
    var bold: Boolean? = null
    var italic: Boolean? = null
    var underlined: Boolean? = null
    var strikethrough: Boolean? = null
    var obfuscated: Boolean? = null
    var color = "white"
        set(value: String) {
            field = value
            bold = null
            italic = null
            underlined = null
            strikethrough = null
            obfuscated = null
        }

    private fun reset() {
        color = "white"
    }

    fun alternate(formatCode: String) {
        when (formatCode) {
            "&0" -> this.color = "black"
            "&1" -> this.color = "dark_blue"
            "&2" -> this.color = "dark_green"
            "&3" -> this.color = "dark_aqua"
            "&4" -> this.color = "dark_red"
            "&5" -> this.color = "dark_purple"
            "&6" -> this.color = "gold"
            "&7" -> this.color = "gray"
            "&8" -> this.color = "dark_gray"
            "&9" -> this.color = "blue"
            "&a" -> this.color = "green"
            "&b" -> this.color = "aqua"
            "&c" -> this.color = "red"
            "&d" -> this.color = "light_purple"
            "&e" -> this.color = "yellow"
            "&f" -> this.color = "white"

            "&k" -> this.obfuscated = true
            "&l" -> this.bold = true
            "&m" -> this.strikethrough = true
            "&n" -> this.underlined = true
            "&o" -> this.italic = true
            "&r" -> reset()

            else -> this.color = formatCode.substring(1, 8)
        }
    }

}