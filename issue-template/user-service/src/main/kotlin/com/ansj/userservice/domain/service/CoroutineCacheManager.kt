package com.ansj.userservice.domain.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Component
class CoroutineCacheManager<T> {

    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()

    /*
        캐시에 대한 결과를 가지고 있는 객체
        캐시가 되는 객체와 그에 대응되는 TTL 정보가 있어서
        특정 캐시 키에 따라서 캐시가 한번 되게 되면, 특정한 시간만큼만 캐시가 살아 있게 된다.
        그러한 정보들을 담기 위해서 만든 객체
     */
    data class CacheWrapper<T>(val cached: T, val ttl: Instant)

    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache[key] = CacheWrapper(cached = value, Instant.now().plusMillis(ttl.toMillis()))
    }

    suspend fun awaitEvict(key: String) {
        localCache.remove(key)
    }

    suspend fun awaitGetOrPut(key: String, ttl: Duration? = Duration.ofMinutes(5), supplier: suspend () -> T): T {
        val now = Instant.now()
        val cachedWrapper = localCache[key]

        val cached = if (cachedWrapper == null) {
            CacheWrapper(cached = supplier(), ttl = now.plusMillis(ttl!!.toMillis())).also {
                localCache[key] = it
            }
        } else if (now.isAfter(cachedWrapper.ttl)) {
            // 캐시 ttl 이 지난 경우
            localCache.remove(key)
            CacheWrapper(cached = supplier(), ttl = now.plusMillis(ttl!!.toMillis())).also {
                localCache[key] = it

            }
        } else {
            cachedWrapper
        }
        checkNotNull(cached.cached)
        return cached.cached
    }

}