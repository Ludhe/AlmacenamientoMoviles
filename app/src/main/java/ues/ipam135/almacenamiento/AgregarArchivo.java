package ues.ipam135.almacenamiento;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AgregarArchivo extends AppCompatActivity {

    EditText txtNombre;
    EditText txtContenido;
    Button btnAgregar;
    Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_archivo);
        intento = getIntent();
        txtNombre = findViewById(R.id.txtNombreArchivo);
        txtContenido = findViewById(R.id.txtContenidoArchivo);
        btnAgregar = findViewById(R.id.btnAgregarArchivo);
        if(intento.hasExtra("archivo")){
            txtNombre.setText(intento.getStringExtra("archivo"));
            try {
                FileInputStream fis = null;
                if(intento.hasExtra("externo")){
                    File[] dirs = getExternalFilesDirs(null);

                    File[] archs = dirs[0].listFiles();
                    for (File arc : archs
                    ) {
                        if (arc.getName().equals(intento.getStringExtra("archivo"))) {
                            fis = new FileInputStream(arc);
                        }
                    }
                }else{
                    fis = openFileInput(intento.getStringExtra("archivo"));
                }
                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();
                fis.close();

                txtContenido.setText(sb);
                //btnAgregar.setVisibility(View.INVISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos;
                    if(intento.hasExtra("externo")){
                        File file = new File(getExternalFilesDir(null), txtNombre.getText().toString());
                        fos = new FileOutputStream(file);
                    }else{
                        fos = openFileOutput(txtNombre.getText().toString(), Context.MODE_PRIVATE);
                    }
                    fos.write(txtContenido.getText().toString().getBytes());
                    fos.close();
                    setResult(RESULT_OK);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
