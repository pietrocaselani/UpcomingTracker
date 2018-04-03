# UpcomingTracker

Keep track of upcoming movies on your Android using TMDB API

## Instructions

After cloning the project, just import to Android Studio

To use your own TMDB API key, add the properties

```shell
TMDB_API_KEY_DEV=your_api_key_for_development
TMDB_API_KEY_RELEASE=your_api_key_for_release
```

on `$HOME/.gradle/gradle.properties`

## Dependencies

* Kotlin
* Support Library v7 (AppCompat, Design, RecyclerView, ConstraintLayout)
* RxAndroid - To work with Android and Main (UI) scheduler
* Retrofit 2 - To abstract network layer
* NYTimes Store - Async Repository implementation for data loading and cache
* AAC - To help implement MVVM handling lifecycles

## Translations

You can help translate the app [here](https://localise.biz/pietro-caselani/moviestracker)

## Author

Pietro Caselani, pc1992@gmail.com