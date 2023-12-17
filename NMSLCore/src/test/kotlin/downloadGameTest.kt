import com.github.purofle.nmsl.download.GameDownloader
import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val provider = MCBBSDownloadProvider()
        val manifest = provider.getManifest().first()
        GameDownloader(provider, manifest.versions.last { it.id == "1.20.4" }).apply {
            downloadGameJson(false)
            println(getSerializerGameJson())
        }
    }
}