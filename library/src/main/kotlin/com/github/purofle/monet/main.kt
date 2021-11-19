package com.github.purofle.monet

import de.androidpit.colorthief.ColorThief
import java.io.File
import java.net.URI
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val wallpaper = "bash /home/purofle/project/NMSL-Launcher/library/src/test/kotlin/getwallpaper.sh".run()
    val file = File(URI(wallpaper.trimIndent()))
    val c = ColorThief.getColor(ImageIO.read(file))
    println(c)
}