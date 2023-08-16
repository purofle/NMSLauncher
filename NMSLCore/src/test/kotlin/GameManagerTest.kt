import com.github.purofle.nmsl.game.GameManager
import kotlin.test.Test

class GameManagerTest {
    @Test
    fun testGetAllGame() {
        val allGame = GameManager.getAllGame()
        assert(allGame.isNotEmpty())
    }
}