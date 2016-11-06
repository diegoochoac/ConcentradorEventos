package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.concentrador.agrum.concentradoreventos.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import basedatos.DatabaseCrud;
import basedatos.contratista.Contratista;
import basedatos.contratista.Maquina;
import basedatos.contratista.Usuario;
import basedatos.evento.Evento;
import basedatos.evento.TipoEvento;
import basedatos.terreno.Hacienda;
import basedatos.terreno.Suerte;

/**
 * Created by diego on 14/09/16.
 */
public class EventoFragment extends Fragment implements OnClickListener {

    private Button btnContratista, btnUsuario, btnMaquina, btnHacienda, btnSuerte, btnSelecTipoEven, btnIniciarEvento;

    long time =0;
    private boolean inicio = false;

    //BASE DE DATOS
    private DatabaseCrud database;

    private List<Contratista> ContratistaList;
    private List<String> ContratistaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterContratista;
    private String contratista="";

    private List<Usuario> UsuarioList;
    private List<String> UsuarioListName = new ArrayList<>();
    private ArrayAdapter<String> adapterUsuario;
    private String usuario="";

    private List<Maquina> MaquinaList;
    private List<String> MaquinaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterMaquina;
    private String maquina="";

    private List<TipoEvento> tipoEventoList;
    private List<String> tipoEventoListName = new ArrayList<>();
    private ArrayAdapter<String> adapterTipoEvento;
    private String evento="";


    private List<Hacienda> HaciendaList;
    private List<String> HaciendaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterHacienda;
    private String hacienda="";

    private List<Suerte> SuerteList;
    private List<String> SuerteListName = new ArrayList<>();
    private ArrayAdapter<String> adapterSuerte;
    private String suerte="";

    //Variables que se van hacia el MAINACTIVITY
    public final static String SET_EVENTO = "Tipo evento";

    Context thiscontext;
    //private OnFragmentInteractionListener mListener;

    String tiempoInicio;
    String tiempoFin;
    String fecha;

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
        btnSelecTipoEven.setEnabled(false);

        btnIniciarEvento = (Button)view.findViewById(R.id.btnInicarEvento);
        btnIniciarEvento.setOnClickListener(this);
        btnIniciarEvento.setEnabled(false);

        btnContratista = (Button)view.findViewById(R.id.btnContratista);
        btnContratista.setOnClickListener(this);
        btnContratista.setEnabled(true);

        btnUsuario = (Button)view.findViewById(R.id.btnTrabajador);
        btnUsuario.setOnClickListener(this);
        btnUsuario.setEnabled(false);

        btnMaquina = (Button)view.findViewById(R.id.btnMaquina);
        btnMaquina.setOnClickListener(this);
        btnMaquina.setEnabled(false);

        btnHacienda = (Button)view.findViewById(R.id.btnHacienda);
        btnHacienda.setOnClickListener(this);
        btnHacienda.setEnabled(false);

        btnSuerte = (Button)view.findViewById(R.id.btnSuerte);
        btnSuerte.setOnClickListener(this);
        btnSuerte.setEnabled(false);
    }


    void AlerDialogList(final ArrayAdapter<String> adapter, final String lista){


        final AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(thiscontext);

        final LinearLayout layout= new LinearLayout(thiscontext);
        final ListView listview = new ListView(thiscontext);
        final TextView Message = new TextView(thiscontext);
        final EditText editText = new EditText(thiscontext);

        Message.setText("Ingrese busqueda:");
        Message.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        editText.setSingleLine();
        editText.clearFocus();

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(listview);
        layout.addView(Message);
        layout.addView(editText);

        alertdialogbuilder.setTitle("Por favor seleccione");
        alertdialogbuilder.setView(layout);

        listview.setAdapter(adapter);

        final AlertDialog alert = alertdialogbuilder.create();

        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editText.getText().toString().toLowerCase().trim();
                switch (lista){
                    case "Contratista":
                        ContratistaList = database.obtenerContratistaAutocompletar(Contratista.NOMBRE,text);
                        Log.i("EventosFragment", "Contratista tamaño: "+ContratistaList.size()+" otras:"+ContratistaListName.size());
                        if(ContratistaList.size()>0 && ContratistaList != null){
                            ContratistaListName.clear();
                            for(int i=0; i<ContratistaList.size(); i++){
                                ContratistaListName.add(ContratistaList.get(i).getNombre());
                                Log.i("EventosFragment", "valores: "+ContratistaList.get(i).getNombre());
                            }
                        }else{
                            ContratistaListName.clear();
                            ContratistaListName.add("No se encuentra Busqueda");
                        }
                        break;

                    case "Usuario":
                        UsuarioList = database.obtenerUsuarioAutocompletar(Usuario.NOMBRE,text,Usuario.KEY_CONTRATISTA,contratista);
                        Log.i("EventosFragment", "Usuario tamaño: "+UsuarioList.size()+" otras:"+UsuarioListName.size());
                        if(UsuarioList.size()>0 && UsuarioList != null){
                            UsuarioListName.clear();
                            for(int i=0; i<UsuarioList.size(); i++){
                                UsuarioListName.add(UsuarioList.get(i).getNombre());
                                Log.i("EventosFragment", "valores: "+UsuarioList.get(i).getNombre());
                            }
                        }else{
                            UsuarioListName.clear();
                            UsuarioListName.add("No se encuentra Busqueda");
                        }
                        break;

                    case "Maquina":
                        MaquinaList = database.obtenerMaquinaAutocompletar(Maquina.NOMBRE,text,Maquina.KEY_CONTRATISTA,contratista);
                        Log.i("EventosFragment", "Maquina tamaño: "+MaquinaList.size()+" otras:"+MaquinaListName.size());
                        if(MaquinaList.size()>0 && MaquinaList != null){
                            MaquinaListName.clear();
                            for(int i=0; i<MaquinaList.size(); i++){
                                MaquinaListName.add(MaquinaList.get(i).getNombre());
                                Log.i("EventosFragment", "valores: "+MaquinaList.get(i).getNombre());
                            }
                        }else{
                            MaquinaListName.clear();
                            MaquinaListName.add("No se encuentra Busqueda");
                        }
                        break;

                    case "Evento":
                        tipoEventoList = database.obtenerTipoEventoAutocompletar(TipoEvento.NOMBRE,text);
                        Log.i("EventosFragment", "Evento tamaño: "+tipoEventoList.size()+" otras:"+tipoEventoListName.size());
                        if(tipoEventoList.size()>0 && tipoEventoList != null){
                            tipoEventoListName.clear();
                            for(int i=0; i<tipoEventoList.size(); i++){
                                tipoEventoListName.add(tipoEventoList.get(i).getNombre());
                                Log.i("EventosFragment", "valores: "+tipoEventoList.get(i).getNombre());
                            }
                        }else{
                            tipoEventoListName.clear();
                            tipoEventoListName.add("No se encuentra Busqueda");
                        }
                        break;

                    case "Hacienda":
                        HaciendaList = database.obtenerHaciendasAutocompletar(Hacienda.NOMBRE,text);
                        Log.i("EventosFragment", "Hacienda tamaño: "+HaciendaList.size()+" otras:"+HaciendaListName.size());
                        if(HaciendaList.size()>0 && HaciendaList != null){
                            HaciendaListName.clear();
                            for(int i=0; i<HaciendaList.size(); i++){
                                HaciendaListName.add(HaciendaList.get(i).getNombre());
                                Log.i("EventosFragment", "valores: "+HaciendaList.get(i).getNombre());
                            }
                            adapterHacienda = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,HaciendaListName);
                            listview.setAdapter(adapterHacienda);
                        }else{
                            HaciendaListName.clear();
                            HaciendaListName.add("No se encuentra Busqueda");
                        }
                        break;

                    case "Suerte":
                        SuerteList = database.obtenerSuertesAutocompletar(Suerte.NOMBRE,text,Suerte.KEY_HACIENDA,hacienda);
                        Log.i("EventosFragment", "Suerte tamaño: "+HaciendaList.size()+" otras:"+SuerteListName.size());
                        if(SuerteList.size()>0 && SuerteList != null){
                            SuerteListName.clear();
                            for(int i=0; i<SuerteList.size(); i++){
                                SuerteListName.add(SuerteList.get(i).getNombre());
                                Log.i("EventosFragment", "valores"+SuerteList.get(i).getNombre());
                            }
                        }else{
                            SuerteListName.clear();
                            SuerteListName.add("No se encuentra Busqueda");
                        }
                        break;
                }
                listview.setAdapter(adapter);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Object select= null;
                Uri uri = Uri.parse("");
                select= adapter.getItem(position);

                switch (lista){
                    case "Contratista":
                        contratista = select.toString();
                        btnContratista.setText("CONTRATISTA: "+contratista);
                        btnUsuario.setEnabled(true);
                        uri = Uri.parse(SET_EVENTO +":"+ select.toString());
                        //mListener.onFragmentInteraction(uri);
                        break;
                    case "Usuario":
                        usuario = select.toString();
                        btnUsuario.setText("USUARIO: "+usuario);
                        btnMaquina.setEnabled(true);
                        uri = Uri.parse(SET_EVENTO +":"+ select.toString());
                        //mListener.onFragmentInteraction(uri);
                        break;
                    case "Maquina":
                        maquina = select.toString();
                        btnMaquina.setText("MAQUINA: "+maquina);
                        btnSelecTipoEven.setEnabled(true);
                        break;
                    case "Evento":
                        evento = select.toString();
                        btnSelecTipoEven.setText("EVENTO: "+evento);
                        uri = Uri.parse(SET_EVENTO +":"+ select.toString());

                        if(select.equals("FragmentoLabor en Terreno")){
                            btnHacienda.setVisibility(View.VISIBLE);
                            btnSuerte.setVisibility(View.VISIBLE);
                            btnHacienda.setEnabled(true);
                        }
                        else {
                            btnHacienda.setVisibility(View.INVISIBLE);
                            btnSuerte.setVisibility(View.INVISIBLE);
                            btnIniciarEvento.setEnabled(true);
                            btnIniciarEvento.setBackgroundColor(Color.GREEN);
                            btnIniciarEvento.setVisibility(view.VISIBLE);
                        }

                        //mListener.onFragmentInteraction(uri);
                        break;
                    case "Hacienda":
                        hacienda = select.toString();
                        btnHacienda.setText("HACIENDA: "+select.toString());
                        btnSuerte.setEnabled(true);
                        break;
                    case "Suerte":
                        suerte = select.toString();
                        btnSuerte.setText("SUERTE: "+select.toString());
                        btnIniciarEvento.setEnabled(true);
                        btnIniciarEvento.setBackgroundColor(Color.GREEN);
                        btnIniciarEvento.setVisibility(view.VISIBLE);
                        break;
                }

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

            case R.id.btnContratista:
                Log.i("EventosFragment", "onClick btnContratista");
                ContratistaList = database.obtenerContratistas();
                if(ContratistaList.size()>0 && ContratistaList != null){
                    ContratistaListName.clear();
                    for(int i=0; i<ContratistaList.size(); i++){
                        ContratistaListName.add(ContratistaList.get(i).getNombre());
                    }
                    adapterContratista = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,ContratistaListName);
                    AlerDialogList(adapterContratista, "Contratista");
                }else{
                    Log.i("EventosFragment", "onClick btnContratista Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnTrabajador:
                Log.i("EventosFragment", "onClick btnTrabajador");
                Log.i("EventosFragment",contratista);
                UsuarioList = database.obtenerUsuariosporId(Usuario.KEY_CONTRATISTA,contratista);
                Log.i("EventosFragment","Tamaño: "+UsuarioList.size());
                if(UsuarioList.size()>0 && UsuarioList != null){
                    UsuarioListName.clear();
                    for(int i=0; i<UsuarioList.size(); i++){
                        UsuarioListName.add(UsuarioList.get(i).getNombre());
                    }
                    adapterUsuario = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,UsuarioListName);
                    AlerDialogList(adapterUsuario, "Usuario");
                }else{
                    Log.i("EventosFragment", "onClick btnTrabajador Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnMaquina:
                Log.i("EventosFragment", "onClick btnMaquina");
                Log.i("EventosFragment",maquina);
                MaquinaList = database.obtenerMaquinasporId(Maquina.KEY_CONTRATISTA,contratista);
                Log.i("EventosFragment","Tamaño: "+MaquinaList.size());
                if(MaquinaList.size()>0 && MaquinaList != null){
                    MaquinaListName.clear();
                    for(int i=0; i<MaquinaList.size(); i++){
                        MaquinaListName.add(MaquinaList.get(i).getNombre());
                    }
                    adapterMaquina = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,MaquinaListName);
                    AlerDialogList(adapterMaquina, "Maquina");
                }else{
                    Log.i("EventosFragment", "onClick btnMaquina Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnSeleEvento:
                Log.i("EventosFragment", "onClick btnSeleEvento");
                tipoEventoList = database.obtenerTipoEvento();
                if(tipoEventoList.size()>0 && tipoEventoList != null){
                    tipoEventoListName.clear();
                    for(int i=0; i<tipoEventoList.size(); i++){
                        tipoEventoListName.add(tipoEventoList.get(i).getNombre());
                    }
                    adapterTipoEvento = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,tipoEventoListName);
                    AlerDialogList(adapterTipoEvento, "Evento");
                }else{
                    Log.i("EventosFragment", "onClick btnSeleEvento Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnHacienda:
                Log.i("EventosFragment", "onClick btnHacienda");
                HaciendaList = database.obtenerHaciendas();
                if(HaciendaList.size()>0 && HaciendaList != null){
                    HaciendaListName.clear();
                    for(int i=0; i<HaciendaList.size(); i++){
                        HaciendaListName.add(HaciendaList.get(i).getNombre());
                    }
                    adapterHacienda = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,HaciendaListName);
                    AlerDialogList(adapterHacienda, "Hacienda");
                }else{
                    Log.i("EventosFragment", "onClick btnSeleEvento Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnSuerte:
                Log.i("EventosFragment", "onClick btnSuerte");
                Log.i("EventosFragment",hacienda);
                SuerteList = database.obtenerSuertesporId(Suerte.KEY_HACIENDA,hacienda);
                Log.i("EventosFragment","Tamaño: "+SuerteList.size());
                if(SuerteList.size()>0 && SuerteList != null){
                    SuerteListName.clear();
                    for(int i=0; i<SuerteList.size(); i++){
                        SuerteListName.add(SuerteList.get(i).getNombre());
                    }
                    adapterSuerte = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,SuerteListName);
                    AlerDialogList(adapterSuerte, "Suerte");
                }else{
                    Log.i("EventosFragment", "onClick btnSuerte Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }

                break;



            case R.id.btnInicarEvento:

                if(!inicio) {
                    inicio = true;
                    btnIniciarEvento.setText("Detener");
                    btnIniciarEvento.setBackgroundColor(Color.RED);

                    tiempoInicio = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());

                }
                else{
                    inicio = false;
                    btnIniciarEvento.setBackgroundColor(Color.GREEN);
                    btnIniciarEvento.setText("Iniciar");
                    btnSelecTipoEven.setEnabled(true);

                    tiempoFin = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
                    fecha =java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                    TipoEvento tipoEventoSel = database.obtenerTipoEvento(evento);
                    Evento nuevo = new Evento(tipoEventoSel,tiempoInicio,tiempoFin,fecha,"No");
                    database.crearEvento(nuevo);
                    //Toast.makeText(thiscontext,"Se creo Evento- Fecha:"+fecha+" Hora ini: "+tiempoInicio
                    //        +" Hora fin: "+tiempoFin,Toast.LENGTH_SHORT).show();
                    syncServerEventos();
                }

                break;
        }
    }

    public void syncServerEventos(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("eventoJSON", database.composeJSONfromSQLiteEvento());
        Log.i("Json Send",""+database.composeJSONfromSQLiteEvento());
        client.post("http://medicionenergia.net23.net/agrum/insertevent.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    Log.i("EventoFragment","response: "+response);
                    JSONArray arr = new JSONArray(response);
                    for(int i=0; i<arr.length();i++){
                        JSONObject obj = (JSONObject)arr.get(i);
                        Log.i("Sincronizando BD Local"," Event id: "+obj.get("id").toString()+" status: "+obj.get("updateState").toString());
                        database.updateSyncStatusEvento(obj.get("id").toString(),obj.get("updateState").toString());
                    }
                    //Toast.makeText(thiscontext, "DB Event Sync completed!", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    //Toast.makeText(thiscontext, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if(statusCode == 404){
                    Toast.makeText(thiscontext, "Requested resource not found", Toast.LENGTH_LONG).show();
                }else if(statusCode == 500){
                    Toast.makeText(thiscontext, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(thiscontext, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}
