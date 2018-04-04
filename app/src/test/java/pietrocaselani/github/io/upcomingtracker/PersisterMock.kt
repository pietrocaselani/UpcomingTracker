package pietrocaselani.github.io.upcomingtracker

import com.nytimes.android.external.store3.base.Persister
import com.nytimes.android.external.store3.base.impl.BarCode
import io.reactivex.Maybe
import io.reactivex.Single

class PersisterMock<T>(data: Map<BarCode, T> = emptyMap(), private val error: Throwable? = null): Persister<T, BarCode> {
    var cache = HashMap<BarCode, T>()
    var writeCalls = 0
    var readCalls = 0

    init {
        cache.putAll(data)
    }

    override fun write(key: BarCode, raw: T): Single<Boolean> {
        writeCalls++

        if (error != null) {
            return Single.error(error)
        }

        cache[key] = raw
        return Single.just(true)
    }

    override fun read(key: BarCode): Maybe<T> {
        readCalls++

        if (error != null) {
            return Maybe.error(error)
        }

        val data = cache[key]
        if (data != null) {
            return Maybe.just(data)
        }

        return Maybe.empty()
    }

}