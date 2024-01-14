import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import com.github.purofle.nmsl.download.fabric.FabricDownloader
import com.github.purofle.nmsl.game.GameManager

suspend fun main() {
    val provider = MCBBSDownloadProvider()
    val versions = GameManager.getVersionJson(GameManager.versions.first())
    FabricDownloader(provider, versions).apply {
        downloadFabric()
        editGameJson()
    }
}