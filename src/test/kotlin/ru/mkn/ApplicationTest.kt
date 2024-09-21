import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import ru.mkn.Value
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        val response = client.get("/calc") {
            url {
                parameters.append("expression", "10")
            }
        }
        val value : Value.FloatValue = Json.decodeFromString<Value.FloatValue>(response.body())
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(10.0, value.value)
    }
}