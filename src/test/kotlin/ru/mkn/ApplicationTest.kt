import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import ru.mkn.ROUTE
import kotlin.test.*

class ApplicationTest {
    @Test
    fun smokeTest() = testApplication {
        val response = client.get("/$ROUTE") {
            url {
                parameters.append("expression", "10+100.5")
            }
        }
        val value : Double = response.body<String>().toDouble()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(110.5, value)
    }

    @Test
    fun correctDBInsertTest() = testApplication {
        for (i in 0..<3) {
            client.get("/$ROUTE") {
                url {
                    parameters.append("expression", "10+100.5")
                }
            }
        }
        val response = client.get("/$ROUTE/hist")
        val expResp = "{\"expression\":\"10+100.5\",\"result\":\"110.5\"}"
        assertEquals("[$expResp,$expResp,$expResp]", response.body<String>())
    }

    @Test
    fun complexTest() = testApplication {
        val response = client.get("/$ROUTE") {
            url {
                parameters.append("expression", "((10+100)+(5-10*2.5))/100")
            }
        }
        assertEquals("0.9", response.body<String>())
    }
}