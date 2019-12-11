package com.example.piedrapapeltijera;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnPiedra, btnPapel, btnTijera;
    TextView txtMarcador;
    ImageView ImgJugador, ImgCPU;
    int JugadorPuntaje = 0;
    int CPUPuntaje = 0;
    SensorManager sensorManager;
    Sensor sensor ;
    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPiedra = (Button) findViewById(R.id.btnPiedra);
        btnPapel = (Button) findViewById(R.id.btnPapel);
        btnTijera = (Button) findViewById(R.id.btnTijera);

        txtMarcador = (TextView) findViewById(R.id.txtMarcador);

        ImgJugador = (ImageView) findViewById(R.id.ImgJugador);
        ImgCPU = (ImageView) findViewById(R.id.ImgCPU);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensor == null)
            finish();
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                Toast.makeText(MainActivity.this, "El valor del gyro "+x+", "+y, Toast.LENGTH_SHORT).show();
                if(y < 8){
                    ImgJugador.setImageResource(R.drawable.papel);
                    String mensaje = turno("Papel");
                    Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                    txtMarcador.setText("Jugador: " + Integer.toString(JugadorPuntaje) + "CPU: " + Integer.toString(CPUPuntaje));
                }else if (x < -5) {
                    ImgJugador.setImageResource(R.drawable.tijera);
                    String mensaje = turno("Tijera");
                    Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                    txtMarcador.setText("Jugador: " + Integer.toString(JugadorPuntaje) + "CPU: " + Integer.toString(CPUPuntaje));
                } else if (x > 5) {
                    ImgJugador.setImageResource(R.drawable.piedra);
                    String mensaje = turno("Piedra");
                    Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                    txtMarcador.setText("Jugador: " + Integer.toString(JugadorPuntaje) + "CPU: " + Integer.toString(CPUPuntaje));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        start();

      btnPiedra.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ImgJugador.setImageResource(R.drawable.piedra);
              String mensaje = turno("Piedra");
              Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
              txtMarcador.setText("Jugador: " + Integer.toString(JugadorPuntaje) + "CPU: " + Integer.toString(CPUPuntaje));

          }
      });

      btnPapel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ImgJugador.setImageResource(R.drawable.papel);
              String mensaje = turno("Papel");
              Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
              txtMarcador.setText("Jugador: " + Integer.toString(JugadorPuntaje) + "CPU: " + Integer.toString(CPUPuntaje));

          }
      });

      btnTijera.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ImgJugador.setImageResource(R.drawable.tijera);
              String mensaje = turno("Tijera");
              Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
              txtMarcador.setText("Jugador: " + Integer.toString(JugadorPuntaje) + "CPU: " + Integer.toString(CPUPuntaje));

          }
      });
    }

        public String turno(String elegido){
            String dispositivo_selecciono = " ";
            Random r = new Random();

            int dispos_selecciono_numero = r.nextInt(3) + 1;

            if (dispos_selecciono_numero == 1) {
                dispositivo_selecciono = "Piedra";
            } else if (dispos_selecciono_numero == 2) {
                dispositivo_selecciono = "Papel";
            } else if (dispos_selecciono_numero == 3) {
                dispositivo_selecciono = "Tijera";
            }

            if (dispositivo_selecciono == "Piedra") {
                ImgCPU.setImageResource(R.drawable.piedra);
            } else if (dispositivo_selecciono == "Papel") {
                ImgCPU.setImageResource(R.drawable.papel);
            } else if (dispositivo_selecciono == "Tijera") {
                ImgCPU.setImageResource(R.drawable.tijera);
            }

            if (dispositivo_selecciono == elegido) {
                return "Empatados";

            } else if (elegido == "Piedra" && dispositivo_selecciono == "Tijera") {
                JugadorPuntaje++;
                return "Piedra vence tijera    FELICIDADES GANASTE";

            } else if (elegido == "Piedra" && dispositivo_selecciono == "Papel") {
                CPUPuntaje++;
                return "Papel vence piedra    PERDISTE";

            } else if (elegido == "Tijera" && dispositivo_selecciono == "Piedra") {
                CPUPuntaje++;
                return "Piedra vence tijera    PERDISTE";

            } else if (elegido == "Tijera" && dispositivo_selecciono == "Papel") {
                JugadorPuntaje++;
                return "Tijera vence papel    FELICIDADES GANASTE";

            } else if (elegido == "Papel" && dispositivo_selecciono == "Piedra") {
                CPUPuntaje++;
                return "Papel vence piedra    FELICIDADES GANASTE";

            } else if (elegido == "Papel" && dispositivo_selecciono == "Tijeras") {
                JugadorPuntaje++;
                return "Tijera vence papel    PERDISTE";
            }

            else return "no seguro";
        }

    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop (){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}
