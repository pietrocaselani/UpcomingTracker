package pietrocaselani.github.io.upcomingtracker

import android.app.Application

class UpcomingTrackerApplication: Application() {
    companion object {
        lateinit var INSTANCE: Application
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}