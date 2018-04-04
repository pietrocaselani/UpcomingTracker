package pietrocaselani.github.io.upcomingtracker

import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.TrampolineScheduler

class TestSchedulers: Schedulers {
    override val mainScheduler: Scheduler
        get() = TrampolineScheduler.instance()
    override val ioScheduler: Scheduler
        get() = TrampolineScheduler.instance()

}