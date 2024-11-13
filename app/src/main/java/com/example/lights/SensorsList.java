package com.example.lights;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class SensorsList extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private SensorManager sensorManager;
    private List<Sensor> deviceSensors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sensor_list);
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
            List<String> listSensorType = new ArrayList<>();
            for (int i = 0; i < deviceSensors.size(); i++) {
                listSensorType.add(Integer.toString(i) + " " + deviceSensors.get(i).getName() + "\n" +
                        "индекс типа: " + deviceSensors.get(i).getType() + "\n" +
                        "производитель: " + deviceSensors.get(i).getVendor() + "\n" +
                        "версия: " + deviceSensors.get(i).getVersion() + "\n" +
                        "мощность: " + deviceSensors.get(i).getPower() + " мВт\n" +
                        "задержка: [" + deviceSensors.get(i).getMinDelay() + "," + deviceSensors.get(i).getMaxDelay() + "] мкс\n" +
                        "макс. значение: " + deviceSensors.get(i).getMaximumRange() + "\n" +
                        "точность: " + deviceSensors.get(i).getResolution() + "\n"
                );
            }
            adapter = new ArrayAdapter<>(this, R.layout.list_item, listSensorType);
            ListView listView = (ListView)findViewById(R.id.list_view);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}