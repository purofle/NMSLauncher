import com.github.purofle.nmsl.download.BMCLAPIDownloadProvider
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

internal class BMCLApiTest {
    @Test
    fun getVersionList() {
        runBlocking {
            BMCLAPIDownloadProvider().getVersionList()
                .collect { println(it) }
        }
    }
}