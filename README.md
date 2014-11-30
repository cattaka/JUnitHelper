JUnitHelper
===========

Utilities for JUnit test such as unlock and lock keyguards.
This helps JUnit testcases that needs screen lock and unlock.


## Unlock and turn on screen

Install this app and put following code to TestCase#setup.
```java
{   // Unlock keyguard and screen on
    // need https://github.com/cattaka/JUnitHelper
    KeyguardManager km = (KeyguardManager) getInstrumentation().getTargetContext().getSystemService(Context.KEYGUARD_SERVICE);
    PowerManager pm = (PowerManager) getInstrumentation().getTargetContext().getSystemService(Context.POWER_SERVICE);
    if (km.inKeyguardRestrictedInputMode() || !pm.isScreenOn()) {
        Intent intent = new Intent("net.cattaka.android.junithelper.ExportedReceiver.unlock");
        try {
            getInstrumentation().getTargetContext().sendBroadcast(intent);
            while (km.inKeyguardRestrictedInputMode()) {
                SystemClock.sleep(100);
            }
        } catch (Exception e) {
            // ignore
        }
    }
}
```

## Lock screen

Install this app and put following code to TestCase#setup.
```java
{   // Unlock keyguard and screen on
    // need https://github.com/cattaka/JUnitHelper
    Intent intent = new Intent("net.cattaka.android.junithelper.ExportedReceiver.lock");
    try {
        getInstrumentation().getTargetContext().sendBroadcast(intent);
        while (!km.inKeyguardRestrictedInputMode()) {
            SystemClock.sleep(100);
        }
    } catch (Exception e) {
        // ignore
    }
}
```
