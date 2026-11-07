# MyLesson2

A second lessons app focused on data and device APIs: SQLite, SMS, telephony and bottom-sheet navigation.

## Features

- SQLite CRUD screen with `MyDbManager` and `MySQLiteHelper`
- Contact list with a custom adapter and search fragment
- Sending SMS and receiving them through a `BroadcastReceiver`
- Instagram-style bottom navigation with fragments
- Splash screen and Navigation component graph

## Tech stack

- **Language:** Kotlin
- **UI:** XML layouts with View binding
- **SDK:** min 24, target 34
- **Package:** `com.example.mylesson2`
- **Libraries:** Navigation component

<sub>19 source files · 474 lines of code · 14 layouts</sub>

## Getting started

```bash
git clone https://github.com/rajabbov/MyLesson2.git
```

Open the folder in Android Studio, let Gradle sync, and run the `app` configuration on a device or emulator.

### Configuration

Uses the `SEND_SMS`, `BROADCAST_SMS` and `CALL_PHONE` permissions — grant them at runtime on Android 6+.

## License

MIT
