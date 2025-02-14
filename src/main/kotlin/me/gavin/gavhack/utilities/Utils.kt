package me.gavin.gavhack.utilities

import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import optifine.BlockPos
import kotlin.math.cos

object Utils {

    fun resolveCape(player: String): String {
//        // fetch json file from cape api
//        val url = try {
//            URL("https://api.capes.dev/load/$player/minecraft")
//        } catch (e: MalformedURLException) {
//            e.printStackTrace()
//            return "URL error"
//        }
//
//        val jsonObj = try {
//            JsonParser().parse(IOUtils.toString(url, StandardCharsets.UTF_8)).asJsonObject
//        } catch (e: IOException) {
//            return "IOException when fetching cape"
//        }
//
//        // check if player even exists
//        if (jsonObj.has("success")) {
//            return "Player invalid in Cape API (offline account?)"
//        }
//        // pull message from json
//        if (!jsonObj.has("msg") or !jsonObj.has("imageUrl"))
//            return "Cape API JSON missing fields"
//
//        val msg = jsonObj["msg"].asString
//        val errMsg = "($player) $msg"
//
//        if (msg == "Cape not found")
//            return errMsg
//
//        if (jsonObj["msg"].asString == "Cape found")
//            return jsonObj["imageUrl"].asString
//
//        return errMsg
        return ""
    }

    fun gameMessage(msg: String) {
        val mc = Minecraft.getMinecraft()
        if (mc != null && mc.ingameGUI.chatGUI != null) {
            val gray = EnumChatFormatting.GRAY
            val green = EnumChatFormatting.GREEN;
            mc.ingameGUI.chatGUI.addChatMessage(ChatComponentText(
                    "$gray[${green}gh$gray]${EnumChatFormatting.RESET} $msg"))
        }
    }

    fun easeInSine(x: Float) : Float = 1f - cos((x * Math.PI) / 2f).toFloat()

    fun getRainbow(ms: Int, brightness: Float, saturation: Float, offset: Int = 0) : Int {
        return 0
    }

    fun blockPlacePacket(pos: BlockPos, side: Int, stack: ItemStack) {
        this.blockPlacePacket(pos.x, pos.y, pos.z, side, stack)
    }

    fun blockPlacePacket(x: Int, y: Int, z: Int, side: Int, stack: ItemStack) {
        Minecraft.getMinecraft().netHandler.addToSendQueue(C08PacketPlayerBlockPlacement(x, y, z, side, stack, 0f, 0f, 0f))
    }
}