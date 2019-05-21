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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class AlmacenamientoExterno extends AppCompatActivity {
    private static ListViewAdapter adaptador;
    ArrayList<String> dataModels;
    ListView listView;
    ImageButton btnAgregar, btnAgregarPublico;
    TextView path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento_externo);
        listView = findViewById(R.id.listExterno);
        btnAgregar = findViewById(R.id.btnAgregarExterno);
        btnAgregarPublico = findViewById(R.id.btnAgregarExternoPublico);
        path = findViewById(R.id.pathExterno);
        path.setText(getExternalFilesDir(null).getAbsolutePath());
        File[] dirs = getExternalFilesDirs(null);
        File[] files = dirs[0].listFiles();
        dataModels = new ArrayList<String>();
        for (File arc :
                files) {
            dataModels.add(arc.getName());
        }


        if (dataModels.isEmpty()) {
            dataModels.add(0, "No hay archivos");
        }
        adaptador = new ListViewAdapter(dataModels, AlmacenamientoExterno.this, false);
        listView.setAdapter(adaptador);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(AlmacenamientoExterno.this, AgregarArchivo.class);
                intento.putExtra("externo", 1);
                startActivityForResult(intento, 1);
            }
        });


        btnAgregarPublico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(AlmacenamientoExterno.this, AlmacenamientoPublico.class);
                startActivity(intento);
                finish();
            }
        });

    }


    public void startEditingActivity(Intent i, int requestCode) {
        startActivityForResult(i, requestCode);
    }

    public boolean deleteFile(String fileName) {
        File[] dirs = getExternalFilesDirs(null);

        File[] archs = dirs[0].listFiles();
        for (File arc : archs
        ) {
            if (arc.getName().equals(fileName)) {
                return arc.delete();
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        actualizarAdaptador();
    }

    public void actualizarAdaptador() {
        File[] dirs = getExternalFilesDirs(null);
        File[] files = dirs[0].listFiles();
        dataModels = new ArrayList<String>();
        for (File arc :
                files) {
            dataModels.add(arc.getName());
        }

        if (dataModels.isEmpty()) {
            dataModels.add(0, "No hay archivos");
        }
        adaptador = new ListViewAdapter(dataModels, AlmacenamientoExterno.this, false);
        listView.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


}
