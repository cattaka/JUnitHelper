package net.cattaka.android.testutil;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by cattaka on 14/11/29.
 */
public class UnlockKeyguardActivity extends Activity {
    private static Handler sHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_keyguard);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUnlocked();
            }
        }, 100);
    }

    private void checkUnlocked() {
        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        if (!km.inKeyguardRestrictedInputMode()) {
            finish();
        } else {
            sHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkUnlocked();
                }
            }, 100);
        }
    }
}
