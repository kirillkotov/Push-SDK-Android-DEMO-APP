# Push-SDK-Android DEMO application

This is a test application for PushSDK

Installation SDK

        dependencies {
            implementation 'com.github.kirillkotov:Push-SDK-Android:1.0.0.47'
        }

Для использования необходимо:

1) Указать в файле MainActivity.kt актуальный Fingerprint (разрешенный на сервере)
в переменную private val apiFingerprint

2) Указать актуальный ключ сервера в переменную private val apiServerKey

3) В методе инициализации SDK указать в качестве параметра basePushURL актуальный URL платформы

        val hPlatformPushAdapterSdk =
            PushSDK(
                context = this,
                log_level = "debug",
                basePushURL = "https://example.com/push/{version}"
            )

4) В папку app загрузить актуальный файл конфигурации Firebase сервиса: google-services.json

5) Подключить актуальную версию SDK (на данный момент 1.0.0.47)
