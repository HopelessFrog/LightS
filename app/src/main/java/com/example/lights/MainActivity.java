package com.example.lights;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.lights.R;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ImageButton switchButton;
    private boolean isOn = false;
    private TextView textOn, textOff;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView textLightData;

    private View view;
    private NotificationManager notificationManager;
    private static final String CHANNEL_ID = "lightSensorChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS")
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, 111);
        }

        textOn = findViewById(R.id.text_on);
        textOff = findViewById(R.id.text_off);
        view = findViewById(R.id.main);
        textLightData = findViewById(R.id.textLightData);
        switchButton = findViewById(R.id.switchButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null) {
            textLightData.setText(getString(R.string.SensorUn));
            return;
        }


        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();

        switchButton.setOnClickListener(view -> {
            isOn = !isOn;
            updateSwitch();
        });

    }


    public void toSensors(View view) {
        Intent intent = new Intent(MainActivity.this, SensorsList.class);
        startActivity(intent);
    }
    private void updateSwitch() {
        if (isOn) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            switchButton.setSelected(true);
            textOn.setVisibility(View.VISIBLE);
            textOff.setVisibility(View.GONE);
        } else {
            sensorManager.unregisterListener(this);
            notificationManager.cancel(1);
            textLightData.setText(getString(R.string.Smile));
            switchButton.setSelected(false);
            textOn.setVisibility(View.GONE);
            textOff.setVisibility(View.VISIBLE);

        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = event.values[0];
            String lightText = lightValue +" "+ getString(R.string.Lux);
            textLightData.setText(lightText);

            if (isOn) {
                showNotification(lightText);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private void showNotification(String lightText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bulb)
                .setContentTitle(getString(R.string.Sensor))
                .setContentText(lightText)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Light Sensor Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
