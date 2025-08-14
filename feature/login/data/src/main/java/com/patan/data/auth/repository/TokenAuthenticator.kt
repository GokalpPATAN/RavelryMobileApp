import android.util.Base64
import com.patan.data.auth.ravelry.RavelryOAuthService
import com.patan.data.auth.settings.RavelrySettings
import com.patan.data.auth.storage.TokenStore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenStore: TokenStore,
    private val settings: RavelrySettings,
    private val oauth: RavelryOAuthService // DİKKAT: bu servis "unauth" client ile oluşturulmalı
) : Authenticator {

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        // Eğer zaten Bearer yoksa ya da çok kez denendiyse null dön.
        if (responseCount(response) >= 2) return null

        return runBlocking {
            val newAccess = refreshLocked() ?: return@runBlocking null
            // Aynı isteği yeni access token ile yeniden dene
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccess")
                .build()
        }
    }

    private suspend fun refreshLocked(): String? = mutex.withLock {
        val refresh = tokenStore.getRefresh() ?: return null
        return try {
            val basic = "Basic " + Base64.encodeToString(
                "${settings.clientId}:${settings.clientSecret}".toByteArray(),
                Base64.NO_WRAP
            )
            val t = oauth.refreshAccessToken(
                basic = basic,
                refreshToken = refresh
            )
            tokenStore.save(t)
            tokenStore.getAccess()
        } catch (_: Throwable) {
            tokenStore.clear()
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var r: Response? = response
        var result = 1
        while (r?.priorResponse != null) {
            result++
            r = r.priorResponse
        }
        return result
    }
}