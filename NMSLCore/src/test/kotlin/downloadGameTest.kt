import com.github.purofle.nmsl.download.DownloadGame
import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val provider = MCBBSDownloadProvider()
        val versionList = provider.getVersionList().first()
        val least= versionList.first()

        val downloader = DownloadGame(provider, least)
        downloader.apply {
            downloadJson()
            downloadAssetJson()
            downloadAllLibrary()
            downloadAllAssets()
            downloadClient()
            println(generateCommand())
        }
    }
}