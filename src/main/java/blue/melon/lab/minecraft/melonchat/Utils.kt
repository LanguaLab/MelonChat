package blue.melon.lab.minecraft.melonchat

import blue.melon.lab.minecraft.melonchat.message.Message
import net.md_5.bungee.api.ChatColor

object Utils {
    //private val colorFormatterRegex = "[&][0-fk-or]|[&][#][0-f]{6}".toRegex()
    //private val gsonInstance = Gson()
    private val hexColorPattern: Regex = "[&][#][0-9A-Fa-f]{6}".toRegex()
    private const val chars = "23456789ABCDEFHKMNPQRSTUVWXYZ+$%"

    fun applyFormatCode(message: String): String {
        return ChatColor.translateAlternateColorCodes('&', convertConvenientHexColorCode(message))
    }

    private fun convertConvenientHexColorCode(message: String): String {
        val splittedMessage = message.split(hexColorPattern)
        val colorCodeIterator = hexColorPattern.findAll(message).iterator()
        val messageBuilder = StringBuilder()

        for (part in splittedMessage) {
            messageBuilder.append(part)
            if (colorCodeIterator.hasNext()) {
                messageBuilder.append(convertHexColorCode(colorCodeIterator.next().value))
            }
        }
        return messageBuilder.toString()
    }

    fun applyPatternFromMessageInstance(message: Message): String {
        return message.pattern.replace("\${source}", message.source)
            .replace("\${target}", message.target)
            .replace("\${message}", message.message)
    }

    private fun convertHexColorCode(hexColorCode: String): String {
        val mainCode = hexColorCode.substring(2, 8)
        val codeBuilder = StringBuilder("&x")
        for (char in mainCode) {
            codeBuilder.append("&").append(char)
        }
        return codeBuilder.toString()
    }

//    //unused
//    fun serialize(text: String): String {
//        val textList = ArrayList<Text>()
//        val format = TextFormat()
//        val elements = text.split(colorFormatterRegex)
//        val formatCodes = colorFormatterRegex.findAll(text)
//        val formatCodeIterator = formatCodes.iterator()
//
//        textList.add(Text("", format))
//
//        elements.forEach {
//            if (it.isNotEmpty()) {
//                textList.add(Text(it, format))
//            }
//            if (formatCodeIterator.hasNext()) {
//                format.alternate(formatCodeIterator.next().value)
//            }
//        }
//
//        return gsonInstance.toJson(textList)
//    }

//    fun getBungeeFormatCode(format: String): ChatColor {
//        when (format) {
//            "&0" -> return ChatColor.BLACK
//            "&1" -> return ChatColor.DARK_BLUE
//            "&2" -> return ChatColor.DARK_GREEN
//            "&3" -> return ChatColor.DARK_AQUA
//            "&4" -> return ChatColor.DARK_RED
//            "&5" -> return ChatColor.DARK_PURPLE
//            "&6" -> return ChatColor.GOLD
//            "&7" -> return ChatColor.GRAY
//            "&8" -> return ChatColor.DARK_GRAY
//            "&9" -> return ChatColor.BLUE
//            "&a" -> return ChatColor.GREEN
//            "&b" -> return ChatColor.AQUA
//            "&c" -> return ChatColor.RED
//            "&d" -> return ChatColor.LIGHT_PURPLE
//            "&e" -> return ChatColor.YELLOW
//            "&f" -> return ChatColor.WHITE
//
//            "&k" -> return ChatColor.MAGIC
//            "&l" -> return ChatColor.BOLD
//            "&m" -> return ChatColor.STRIKETHROUGH
//            "&n" -> return ChatColor.UNDERLINE
//            "&o" -> return ChatColor.ITALIC
//            "&r" -> return ChatColor.RESET
//
//            else -> return ChatColor.of(format.substring(1, 8))
//        }
//    }
//
//    fun getRandomString(length: Int): String {
//        val stringBuilder = StringBuilder()
//        for (i in 1..length) {
//            stringBuilder.append(chars[Random.nextInt(0, chars.length)])
//        }
//        return stringBuilder.toString()
//    }
}
