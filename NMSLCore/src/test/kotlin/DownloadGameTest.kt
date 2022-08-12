import com.github.purofle.nmsl.download.BMCLAPIDownloadProvider
import com.github.purofle.nmsl.download.DownloadGame
import com.github.purofle.nmsl.game.version.Version
import com.github.purofle.nmsl.utils.json.JsonUtils.toJsonString
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class DownloadGameTest {
    private val downloadGame = DownloadGame(
        BMCLAPIDownloadProvider(), Version(
            1,
            "1.12.2",
            "2022-08-05T11:57:05+00:00",
            "68cded4616fba9fbefb3f895033c261126c5f89c",
            "2022-08-05T12:01:02+00:00",
            "release",
            "https://piston-meta.mojang.com/v1/packages/68cded4616fba9fbefb3f895033c261126c5f89c/1.19.2.json"
            // https://piston-meta.mojang.com/v1/packages/68cded4616fba9fbefb3f895033c261126c5f89c/1.19.2.json
            // https://launchermeta.mojang.com/v1/packages/cfd75871c03119093d7c96a6a99f21137d00c855/1.12.2.json
        )
    )

    @Test
    fun createDirs() {
        downloadGame.createDirs()
    }

    @Test
    fun downloadTest() {
        runBlocking {
            downloadGame.downloadJson()
        }
    }

    @Test
    fun testGetLibrary() {
        runBlocking {
            downloadGame.downloadJson()
        }
        println(downloadGame.getAllLibrary().toJsonString())
    }

    @Test
    fun testDownloadAllLibrary() {
        runBlocking {
            downloadGame.downloadJson()
            downloadGame.getAllLibrary().forEach { library ->
                library.downloads.artifact?.let { downloadGame.downloadLibrary(it) }
            }
        }
    }
}