import com.github.purofle.nmsl.auth.microsoft.DeviceCodeFlow
import com.github.purofle.nmsl.config.LauncherConfig
import com.github.purofle.nmsl.download.DownloadGame
import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun main() {

    runBlocking {
        val provider = MCBBSDownloadProvider()
        val versionList = provider.getVersionList().first()

        val downloader = DownloadGame(provider, versionList.find { it.id == "1.18.2" }!!)

        LauncherConfig.config.profile.let {
            downloader.apply {
                downloadJson()
                downloadAssetJson()
                downloadAllLibrary()
                downloadAllAssets()
                downloadClient()
                println(
                    generateCommand(
                        username = it.minecraftProfile.name,
                        clientId = DeviceCodeFlow.CLIENT_ID,
                        accessToken = it.minecraftAccessToken,
                        uuid = it.minecraftProfile.id,
                        xuid = it.xstsToken
                    )
                )
            }
        }
    }
}