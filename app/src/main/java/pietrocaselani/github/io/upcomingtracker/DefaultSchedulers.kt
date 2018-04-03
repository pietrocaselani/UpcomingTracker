package pietrocaselani.github.io.upcomingtracker

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class DefaultSchedulers: Schedulers {
    override val mainScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val ioScheduler: Scheduler
        get() = io.reactivex.schedulers.Schedulers.io()

}