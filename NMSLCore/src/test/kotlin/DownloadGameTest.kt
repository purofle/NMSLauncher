import com.github.purofle.nmsl.download.GameDownloader
import com.github.purofle.nmsl.download.MojangDownloadProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

fun main() {
    runBlocking {

        val provider = MojangDownloadProvider()
        val manifest = provider.getManifest().first()
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