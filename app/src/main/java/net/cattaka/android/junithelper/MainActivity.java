package net.cattaka.android.junithelper;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {
    private static Handler sHandler = new Handler();
    private static final int RESULT_ENABLE = 1;
    private ComponentName mComponentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mComponentName = new ComponentName(this, MyAdminReceiver.class);

        findViewById(R.id.aquire_admin_button).setOnClickListener(this);
        findViewById(R.id.lock_now_button).setOnClickListener(this);
        findViewById(R.id.test_unlock_keyguard_after_5_sec_button).setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.aquire_admin_button) {
            DevicePolicyManager deviceManger = (DevicePolicyManager)getSystemService(
                    Context.DEVICE_POLICY_SERVICE);
            if (!deviceManger.isAdminActive(mComponentName)) {
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Additional text explaining why this needs to be added.");
                startActivityForResult(intent, RESULT_ENABLE);
            } else {
                Toast.makeText(this, "Admin already enabled!", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.lock_now_button) {
            DevicePolicyManager deviceManger = (DevicePolicyManager)getSystemService(
                    Context.DEVICE_POLICY_SERVICE);
            if (deviceManger.isAdminActive(mComponentName)) {
                deviceManger.lockNow();
            } else {
                Toast.makeText(this, "Admin not enabled!", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.test_unlock_keyguard_after_5_sec_button) {
            sHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, UnlockKeyguardActivity.class);
                    startActivity(intent);
                }
            }, 5000);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Admin enabled!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Admin enable FAILED!", Toast.LENGTH_SHORT).show();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
