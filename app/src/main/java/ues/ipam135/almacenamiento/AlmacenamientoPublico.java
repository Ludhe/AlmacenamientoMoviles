package ues.ipam135.almacenamiento;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;

public class AlmacenamientoPublico extends AppCompatActivity {

    EditText txtNombre, txtContent;
    Button btnGuardar;
    String[] permiso = {Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento_publico);

        txtNombre = findViewById(R.id.txtNombreArchivoPublico);
        txtContent = findViewById(R.id.txtContenidoArchivoPublico);
        btnGuardar = findViewById(R.id.btnAgregarArchivoPublico);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Build.VERSION.SDK_INT > 22){
                        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "almacenamiento_app");
                    if(!dir.mkdirs()){
                        System.out.println("Directorio: "+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
                        System.out.println("NO SE CREO EL DIRECTORIO");
                    }else{
                        File file = new File(dir, txtNombre.getText().toString());
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(txtContent.getText().toString().getBytes());
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED )){
                    Toast.makeText(AlmacenamientoPublico.this, "Permission denied to access your external storage.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
