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
                listSensorType.add(Integer.toString(i + 1) + " " + deviceSensors.get(i).getName() + "\n" +
                        getString(R.string.Index) + " " + deviceSensors.get(i).getType() + "\n" +
                        getString(R.string.Creator) + " " + deviceSensors.get(i).getVendor() + "\n" +
                        getString(R.string.Version) + " " + deviceSensors.get(i).getVersion() + "\n" +
                        getString(R.string.Power) + " " + deviceSensors.get(i).getPower() + " " + getString(R.string.Mbt) + "\n" +
                        getString(R.string.Delay) + " " + deviceSensors.get(i).getMinDelay() + getString(R.string.Kom) + deviceSensors.get(i).getMaxDelay() + getString(R.string.Mks) + "\n" +
                       getString(R.string.Max)  + " " + deviceSensors.get(i).getMaximumRange() + "\n" +
                        getString(R.string.Acc) + " " + deviceSensors.get(i).getResolution() + "\n"
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