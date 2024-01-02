package com.github.purofle.nmsl.utils.io

import com.github.purofle.nmsl.download.MCBBSDownloadProvider
import com.github.purofle.nmsl.game.Manifest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.BeforeTest

class HttpRequestTest {

    private lateinit var manifest: Manifest

    @BeforeTest
    fun beforeTest() = runBlocking {
        val provider = MCBBSDownloadProvider()
        manifest = provider.getManifest()
    }

    @Test
    fun downloadFiles() {
        runBlocking {
            HttpRequest.downloadFiles(
                files = manifest.versions.map {
                    HttpRequest.DownloadInfo(it.url, File.createTempFile(it.id, ".json"))
                },
            )
        }
    }
}