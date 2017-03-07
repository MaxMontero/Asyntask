package maxmontero.com.asyntask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //el asynstask es la alternaiva mas usada a los HILOS

    TextView textView;
    Button boton;

    //predifinimos el progressbar
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview_main);
        boton = (Button) findViewById(R.id.button_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_main);
        //cambiamos la visibilidad a invisible si no siempre va a aparecer
        progressBar.setVisibility(View.GONE);
        //por estandar se pone el setMax
        progressBar.setMax(100);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //llamamos la tarea asincrona
                //en este caso vamos a imprimir de numero 1 al n en este caso es 100
                //puede ser cualquier numero
                new MyAsintask().execute(100);
            }
        });
    }

    //por convencion la clase lleva el siguiente nombre:
    //en el <> van los parametros que vamos a estar utilizando cadenas objetos, etc

    public class MyAsintask extends AsyncTask<Integer, Integer, String>{

        //
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //antes de hacer la tarea deberia aparecer la carga del progressBar
            progressBar.setVisibility(View.VISIBLE);
        }

        //este es metodo mas importante aparece Integer por que nosotros estamos poniendo Integer arriba
        @Override
        protected String doInBackground(Integer... integers) {
            int max = integers[0];
            //queremos imprimir numeros del 1 al 100
            //el asyntask puede modificar la interfaz grafica
            for (int i =0; i<= max ; i++){
                //hacemos una pequeña pausa
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //llamamos al metodo publish
                publishProgress(i);
            }
            return "FIN";
        }

        //define la actualizacion
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            int contador = values[0];
            String texto = "Contador: " + contador;
            textView.setText(texto);
            textView.setTextSize(contador);
            //indicamos el progreso que lleva
            progressBar.setProgress(values[0]);

        }

        //este es lo que llegó al final del doInBacground la palabra "FIN "
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.append("\n" + s);
            progressBar.setVisibility(View.GONE);
        }
    }
}
