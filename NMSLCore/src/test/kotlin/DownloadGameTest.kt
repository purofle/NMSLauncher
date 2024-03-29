import com.github.purofle.nmsl.download.GameDownloader
import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

fun main() {
    runBlocking {

        val provider = MCBBSDownloadProvider()
        val manifest = provider.getManifest()
        val time = measureTime {
            GameDownloader(provider, manifest.versions.first()).apply {
                downloadGameJson()
                downloadLibrary()
                downloadAssets()
                downloadClientJar()
                extraNatives()
            }
        }
        println("downloaded ${manifest.versions.first().id} in $time")
    }
}