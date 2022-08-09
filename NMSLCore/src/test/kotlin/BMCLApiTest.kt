import com.github.purofle.nmsl.download.BMCLAPIDownloadProvider
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BMCLApiTest {
    @Test
    fun getVersionList() {
        runBlocking {
            BMCLAPIDownloadProvider().getVersionList()
                .collect { println(it) }
        }
    }
}