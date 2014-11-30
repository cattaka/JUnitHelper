package net.cattaka.android.junithelper;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cattaka on 14/11/30.
 */
public class ExportedReceiver extends BroadcastReceiver {
    public static final String ACTION_LOCK = "net.cattaka.android.junithelper.ExportedReceiver.lock";
    public static final String ACTION_UNLOCK = "net.cattaka.android.junithelper.ExportedReceiver.unlock";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_LOCK.equals(action)) {
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            devicePolicyManager.lockNow();
        } else if (ACTION_UNLOCK.equals(action)) {
            Intent i = new Intent(context, UnlockKeyguardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
