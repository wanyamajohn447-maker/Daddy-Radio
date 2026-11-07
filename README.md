# DADDY LIVE RADIO — Android starter app

A native Android app starter branded in black, red and gold. It includes a live internet-radio player powered by AndroidX Media3/ExoPlayer.

## What is included
- Native Android Studio project (Kotlin + Jetpack Compose)
- Live audio stream player with play/pause
- Playback status and connection-error feedback
- Internet permission and audio-focus handling
- Branded DADDY LIVE RADIO home screen

## Required before the radio can stream
You must have a radio-hosting provider and its direct audio stream URL (for example, an HTTPS MP3 or AAC stream URL). A website homepage or admin-panel URL is not a stream URL.

1. Open `app/src/main/java/com/daddyliveradio/app/MainActivity.kt`.
2. Replace `https://YOUR-STREAM-URL-HERE` in `STREAM_URL` with your provider's direct stream URL.
3. In Android Studio, open this project folder, allow Gradle sync, then choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. Install the generated APK on your Android phone and test using mobile data and Wi-Fi.

## Current prototype limits
- The live player is ready to connect once a valid stream URL is added.
- Song requests, social links and sharing currently show setup prompts; add your WhatsApp number and social URLs to activate them.
- This starter keeps playback active while the app is open. Background/lock-screen playback, notification controls, a schedule, and a hosted admin dashboard are not included yet.
- No listener count is fabricated; a real count requires analytics from your radio-streaming provider.

## Build requirements
- Android Studio with Android SDK 35
- JDK 17
- Internet access for Gradle dependencies

## Stream URL safety
Use a stream URL supplied by your radio provider. Prefer HTTPS. Do not put hosting-account passwords or admin credentials in the app.
