package ues.ipam135.almacenamiento;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListViewAdapter extends ArrayAdapter<String> {
    private ArrayList<String> dataSet;
    Context mContext;
    Context insideContext;
    MainActivity mact;
    AlmacenamientoExterno ame;
    private static boolean interno;


    // View lookup cache
    private static class ViewHolder {
        TextView txtNombre;
        TextView txtApellido;
        TextView txtCarrera;
        TextView txtSexo;
        TextView txtCarnet;
    }


    public ListViewAdapter(ArrayList<String> data, Context context, Boolean interno) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
        this.interno = interno;
        if(interno){
            mact = (MainActivity) mContext;
        }else{
            ame = (AlmacenamientoExterno) mContext;
        }

    }


    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (insideContext == null) {
            insideContext = parent.getContext();
        }


        // Get the data item for this position
        final String dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtNombre = (TextView) convertView.findViewById(R.id.nombre);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(mContext, AgregarArchivo.class);
                    intento.putExtra("archivo", dataModel);
                    if(interno){
                        mact.startEditingActivity(intento, 2);
                    }else{
                        intento.putExtra("externo", 1);
                        ame.startEditingActivity(intento, 2);
                    }

                }
            });


            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(interno){
                        mact.deleteFile(dataModel);
                        mact.actualizarAdaptador();
                    }else{
                        ame.deleteFile(dataModel);
                        ame.actualizarAdaptador();
                    }

                    return true;
                }
            });

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        lastPosition = position;


        viewHolder.txtNombre.setText(dataModel);

        // Return the completed view to render on screen
        return convertView;
    }



}

