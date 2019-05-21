package ues.ipam135.almacenamiento;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static ListViewAdapter adaptador;
    ArrayList<String> dataModels;
    ListView listView;
    ImageButton btnAgregar;
    TextView path;
    Button btnExterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAgregar = (ImageButton) findViewById(R.id.btnAgregar);
        btnExterno = (Button) findViewById(R.id.btnExterno);
        path = findViewById(R.id.path);
        path.setText(getFilesDir().getAbsolutePath());

        listView = (ListView) findViewById(R.id.list);
        dataModels = new ArrayList<String>(Arrays.asList(getApplicationContext().fileList()));
        if (dataModels.isEmpty()) {
            dataModels.add(0, "No hay archivos");
        }
        adaptador = new ListViewAdapter(dataModels, MainActivity.this, true);
        listView.setAdapter(adaptador);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(MainActivity.this, AgregarArchivo.class);
                startActivityForResult(intento, 1);
            }
        });
        btnExterno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(MainActivity.this, AlmacenamientoExterno.class);
                startActivityForResult(intento, 1);
            }
        });


    }

    public void startEditingActivity(Intent i, int requestCode) {
        startActivityForResult(i, requestCode);
    }

    public boolean deleteFile(String fileName) {
       return getApplicationContext().deleteFile(fileName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        actualizarAdaptador();
    }

    public void actualizarAdaptador() {
        dataModels = new ArrayList<String>(Arrays.asList(getApplicationContext().fileList()));
        if (dataModels.isEmpty()) {
            dataModels.add(0, "No hay archivos");
        }
        adaptador = new ListViewAdapter(dataModels, MainActivity.this, true);
        listView.setAdapter(adaptador);
    }
}
