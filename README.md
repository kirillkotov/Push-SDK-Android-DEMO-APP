# New Push-SDK-Android DEMO application
## General information
### To use the app, fist of all you need to obtain:
1. Client API key
1. App fingerprint
1. Api URL (e.g. https://yourlink.com/api/3.0/)

The values can be obtained through PushSDK representatives.

A firebase account and project (with cloud messaging enabled) are required to use the PushSDK. For demo purposes, a demo firebase project is already connected to the app, so the app will be receiving push messages through the firebase project.

## Using the app

<p><img align="left" src="https://user-images.githubusercontent.com/34152018/100575978-c0f8f800-330f-11eb-8f21-1b39216ab7a2.png">

### Credentials screen
Upon launching the app for the first time, you will be prompted to enter Api URL, Client API key and App fingerprint. You should have these values at this point, so it's pretty straightforward - enter the values then press "Init SDK" button.

</p>
<p>
<img align="left" src="https://user-images.githubusercontent.com/34152018/100575978-c0f8f800-330f-11eb-8f21-1b39216ab7a2.png">

### Main screen
This screen allows you to interact with the SDK.

Basic cycle:

1. First, register your device by pressing "Register device"
1. Second, press "Update registration" to let the SDK set and update you current FCM token
1. Verify that your device is registered by pressing "Get all devices" button
</p>

After following these steps, the app should now be able to receive push messages from the Messaging Hub.

Note: By default, system tray notifications will only be displayed when the app is in background. When the app is in foreground - it will only receive broadcasts, and show them in the output area.
