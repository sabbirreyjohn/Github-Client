package xyz.androidrey.githubclient.network.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import xyz.androidrey.githubclient.network.NetworkException
import xyz.androidrey.githubclient.network.NetworkResult
import kotlin.coroutines.cancellation.CancellationException

class RequestHandler(val httpClient: HttpClient) {

     val bearerToken = MutableStateFlow<String?>(null)

    fun setBearerToken(token: String?) {
        bearerToken.value = token
    }

    suspend inline fun <reified B, reified R> executeRequest(
        method: HttpMethod,
        urlPathSegments: List<Any>,
        body: B? = null,
        queryParams: Map<String, Any>? = null,
    ): NetworkResult<R> {
        delay(1000L)
        return withContext(Dispatchers.IO) {
            try {
                val response = httpClient.prepareRequest {
                    this.method = method
                    url {
                        val pathSegments = urlPathSegments.map { it.toString() }
                        appendPathSegments(pathSegments)
                    }
                    body?.let { setBody(body) }
                    queryParams?.let { params ->
                        params.forEach { (key, value) ->
                            parameter(key, value)
                        }
                    }
                    // Add the Bearer Token dynamically
                    bearerToken.value?.let { token ->
                        header(HttpHeaders.Authorization, "Bearer $token")
                    }
                }.execute().body<R>()
                NetworkResult.Success(response)
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> throw e
                    is ResponseException -> {
                        val errorBody = try {
                            e.response.body<DefaultError>()
                        } catch (ex: Exception) {
                            DefaultError("Unknown API Error")
                        }

                        val exception = when (e.response.status) {
                            HttpStatusCode.BadRequest -> NetworkException.BadRequestException(errorBody.message, e)
                            HttpStatusCode.Unauthorized -> NetworkException.UnauthorizedException(errorBody.message, e)
                            HttpStatusCode.Forbidden -> NetworkException.ForbiddenException(errorBody.message, e)
                            HttpStatusCode.NotFound -> NetworkException.NotFoundException(errorBody.message, e)
                            HttpStatusCode.InternalServerError -> NetworkException.ServerException(errorBody.message, e)
                            HttpStatusCode.ServiceUnavailable -> NetworkException.ServiceUnavailableException(errorBody.message, e)
                            else -> NetworkException.UnknownException(errorBody.message, e)
                        }

                        NetworkResult.Error(null, exception)
                    }
                    is HttpRequestTimeoutException -> NetworkResult.Error(null, NetworkException.TimeoutException("Timeout", e))
                    else -> NetworkResult.Error(null, NetworkException.UnknownException(e.message ?: "Unknown Error", e))
                }
            }
        }
    }

    suspend inline fun <reified R> get(
        urlPathSegments: List<Any>,
        queryParams: Map<String, Any>? = null,
    ): NetworkResult<R> = executeRequest<Any, R>(
        method = HttpMethod.Get,
        urlPathSegments = urlPathSegments.toList(),
        queryParams = queryParams,
    )

    suspend inline fun <reified B, reified R> post(
        urlPathSegments: List<Any>,
        body: B? = null,
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Post,
        urlPathSegments = urlPathSegments.toList(),
        body = body,
    )

    suspend inline fun <reified B, reified R> patch(
        urlPathSegments: List<Any>,
        body: B? = null,
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Patch,
        urlPathSegments = urlPathSegments.toList(),
        body = body,
    )

    suspend inline fun <reified B, reified R> put(
        urlPathSegments: List<Any>,
        body: B? = null,
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Put,
        urlPathSegments = urlPathSegments.toList(),
        body = body,
    )

    suspend inline fun <reified B, reified R> delete(
        urlPathSegments: List<Any>,
        body: B? = null,
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Delete,
        urlPathSegments = urlPathSegments.toList(),
        body = body,
    )
}
