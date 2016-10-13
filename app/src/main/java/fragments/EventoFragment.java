package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.concentrador.agrum.concentradoreventos.R;

import java.util.ArrayList;
import java.util.List;

import basedatos.DatabaseCrud;
import basedatos.evento.TipoEvento;

/**
 * Created by diego on 14/09/16.
 */
public class EventoFragment extends Fragment implements OnClickListener {

    private Button btnSelecTipoEven, btnIniciarEvento, btnDetenerEvento;
    private Chronometer crono;
    long time =0;
    private Button BtnMenu;

    //BASE DE DATOS
    private DatabaseCrud database;
    private List<TipoEvento> tipoEventoList;
    private List<String> tipoEventoListName = new ArrayList<>();
    private ArrayAdapter<String> adapterTipoEvento;

    //Variables que se van hacia el MAINACTIVITY
    public final static String SET_EVENTO = "Tipo evento";

    Context thiscontext;
    //private OnFragmentInteractionListener mListener;

    public EventoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_evento, container, false);
        database = new DatabaseCrud(container.getContext());
        inicializarComponentes(rootview);
        thiscontext = container.getContext();
        return rootview;
    }


    private void inicializarComponentes(final View view) {
        btnSelecTipoEven= (Button)view.findViewById(R.id.btnSeleEvento);
        btnSelecTipoEven.setOnClickListener(this);
        btnIniciarEvento = (Button)view.findViewById(R.id.btnInicarEvento);
        btnIniciarEvento.setOnClickListener(this);
        btnIniciarEvento.setEnabled(false);
        btnDetenerEvento = (Button)view.findViewById(R.id.btnDetenerEvento);
        btnDetenerEvento.setOnClickListener(this);
        btnDetenerEvento.setEnabled(false);

        crono = (Chronometer)view.findViewById(R.id.chronometer);
    }

    void AlerDialogListEvento(){
        final AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(thiscontext);

        LinearLayout layout= new LinearLayout(thiscontext);

        final ListView listview = new ListView(thiscontext);
        final TextView Message = new TextView(thiscontext);
        final EditText editText = new EditText(thiscontext);

        Message.setText("Ingrese busqueda:");
        editText.setSingleLine();
        editText.clearFocus();

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(listview);
        layout.addView(Message);
        layout.addView(editText);

        alertdialogbuilder.setTitle("Por favor seleccione");
        alertdialogbuilder.setView(layout);

        listview.setAdapter(adapterTipoEvento);

        final AlertDialog alert = alertdialogbuilder.create();

        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Log.i("EventosFragment", "onTextChanged");
                String text = editText.getText().toString().toLowerCase().trim();
                tipoEventoList = database.obtenerTipoEventoAutocompletar(TipoEvento.NOMBRE,text);
                Log.i("EventosFragment", "onTextChanged tamaño: "+tipoEventoList.size()+" otras:"+tipoEventoListName.size());

                if(tipoEventoList.size()>0 && tipoEventoList != null){
                    tipoEventoListName.clear();
                    for(int i=0; i<tipoEventoList.size(); i++){
                        tipoEventoListName.add(tipoEventoList.get(i).getNombre());
                        Log.i("EventosFragment", "valores"+tipoEventoList.get(i).getNombre());
                    }
                    adapterTipoEvento = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,tipoEventoListName);
                    listview.setAdapter(adapterTipoEvento);
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Object select= null;
                Uri uri = Uri.parse("");
                select= adapterTipoEvento.getItem(position);
                btnSelecTipoEven.setText(select.toString());
                btnIniciarEvento.setEnabled(true);
                btnSelecTipoEven.setEnabled(false);
                uri = Uri.parse(SET_EVENTO +":"+ select.toString());
                //mListener.onFragmentInteraction(uri);

                alert.cancel();
            }
        });

        alertdialogbuilder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onResume() {
        Log.i("EventosFragment", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("EventosFragment", "onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.i("EventosFragment", "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Uri uri = Uri.parse("");
        switch (view.getId()) {

            case R.id.btnSeleEvento:
                Log.i("EventosFragment", "onClick btnSeleEvento");
                tipoEventoList = database.obtenerTipoEvento();
                if(tipoEventoList.size()>0 && tipoEventoList != null){
                    tipoEventoListName.clear();
                    for(int i=0; i<tipoEventoList.size(); i++){
                        tipoEventoListName.add(tipoEventoList.get(i).getNombre());
                    }
                    adapterTipoEvento = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,tipoEventoListName);
                    AlerDialogListEvento();
                }else{
                    Log.i("EventosFragment", "onClick btnSeleEvento Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnInicarEvento:
                btnIniciarEvento.setEnabled(false);
                btnDetenerEvento.setEnabled(true);
                crono.setBase(SystemClock.elapsedRealtime());
                crono.start();

                break;

            case R.id.btnDetenerEvento:
                btnDetenerEvento.setEnabled(false);
                btnIniciarEvento.setEnabled(true);
                btnSelecTipoEven.setEnabled(true);
                crono.stop();
                break;
        }
    }

}
