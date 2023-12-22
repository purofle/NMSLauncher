import com.github.purofle.nmsl.download.GameDownloader
import com.github.purofle.nmsl.download.MojangDownloadProvider
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() {
    runBlocking {
        val provider = MojangDownloadProvider()
        val manifest = provider.getManifest()
        val command = listOf("/usr/bin/java") + GameDownloader(provider,
            manifest.versions.first { it.id == "1.12.2" }).getLauncherArgument()

        println(command.joinToString(" "))
        val sh = File.createTempFile("nmsl", "sh")
        sh.writeText(command.joinToString(" "))
        sh.deleteOnExit()

        ProcessBuilder(
            "/bin/sh", sh.absolutePath
        )
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
    }
}