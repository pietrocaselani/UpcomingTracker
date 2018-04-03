package pietrocaselani.github.io.upcomingtracker

import io.reactivex.Scheduler

interface Schedulers {
    val mainScheduler: Scheduler
    val ioScheduler: Scheduler
}