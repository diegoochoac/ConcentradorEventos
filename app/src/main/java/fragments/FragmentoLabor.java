package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.concentrador.agrum.concentradoreventos.R;

import java.util.ArrayList;
import java.util.List;

import basedatos.DatabaseCrud;
import basedatos.terreno.Hacienda;
import basedatos.terreno.Suerte;
import utils.OnFragmentInteractionListener;


public class FragmentoLabor extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int eventoSelec = -1;
    String contratistaSelec="", trabajadorSelec="", maquinaSelec="", haciendaSelec ="", suerteSelec="";

    TextView contratista, trabajador, maquina, txhacienda, txsuerte;
    ImageView imagen;

    FloatingActionButton FAB1;
    FloatingActionButton FAB2;

    private DatabaseCrud database;

    private List<Hacienda> HaciendaList;
    private List<String> HaciendaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterHacienda;
    private String hacienda="";

    private List<Suerte> SuerteList;
    private List<String> SuerteListName = new ArrayList<>();
    private ArrayAdapter<String> adapterSuerte;
    private String suerte="";

    public final static String SET_HACIENDA = "Hacienda";
    public final static String SET_SUERTE= "Suerte";

    private OnFragmentInteractionListener mCallback = null;
    Uri uri = Uri.parse("");

    Context thiscontext;



    public FragmentoLabor() {
        // Required empty public constructor
    }

    public static FragmentoLabor newInstance(String param1, String param2) {
        FragmentoLabor fragment = new FragmentoLabor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventoSelec = getArguments().getInt("Evento");
            contratistaSelec = getArguments().getString("Contratista");
            trabajadorSelec = getArguments().getString("Trabajador");
            maquinaSelec = getArguments().getString("Maquina");
            haciendaSelec = getArguments().getString("Hacienda");
            suerteSelec = getArguments().getString("Suerte");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_labor, container, false);

        database = new DatabaseCrud(container.getContext());
        thiscontext = container.getContext();
        imagen = (ImageView) view.findViewById(R.id.imageEvento);

        switch (eventoSelec){
            case 0:
                imagen.setImageResource(R.drawable.inspeccion);
                break;
            case 1:
                imagen.setImageResource(R.drawable.desplazar);
                break;
            case 2:
                imagen.setImageResource(R.drawable.espera);
                break;
            case 3:
                imagen.setImageResource(R.drawable.alimentacion);
                break;
            case 4:
                imagen.setImageResource(R.drawable.mantenimiento);
                break;
            case 5:
                imagen.setImageResource(R.drawable.varado);
                break;
            case 6:
                imagen.setImageResource(R.drawable.tanquear);
                break;
            case 7:
                imagen.setImageResource(R.drawable.lluvia);
                break;
            case 8:
                imagen.setImageResource(R.drawable.labor);
                break;
        }

        contratista = (TextView) view.findViewById(R.id.txContratista);
        contratista.setText("Contratista: "+contratistaSelec);
        trabajador = (TextView) view.findViewById(R.id.txTrabajador);
        trabajador.setText("Trabajador: "+trabajadorSelec);
        maquina =  (TextView) view.findViewById(R.id.txMaquina);
        maquina.setText("Maquina: "+maquinaSelec);
        txhacienda = (TextView) view.findViewById(R.id.txHacienda);
        txhacienda.setText("Hacienda: "+haciendaSelec);
        txsuerte = (TextView) view.findViewById(R.id.txSuerte);
        txsuerte.setText("Suerte: "+suerteSelec);

        FAB2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        FAB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Fragmento Labor","Boton Iniciar");
                AlerDialogButton();
            }
        });

        listadoHacienda();

        return view;
    }

    void listadoHacienda(){
        HaciendaList = database.obtenerHaciendas();
        if(HaciendaList != null){
            if(HaciendaList.size()>0 ){
                HaciendaListName.clear();
                for(int i=0; i<HaciendaList.size(); i++){
                    HaciendaListName.add(HaciendaList.get(i).getNombre());
                }
                adapterHacienda = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,HaciendaListName);
                AlerDialogButton("Hacienda",adapterHacienda);
            }
        }else{
            HaciendaListName.clear();
            HaciendaListName.add("No hay Haciendas");
            adapterHacienda = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, HaciendaListName);
            AlerDialogButton( "Hacienda",adapterHacienda);
        }
    }

    void listadoSuerte(){
        SuerteList = database.obtenerSuertesporId(Suerte.KEY_HACIENDA,hacienda);
        if(SuerteList != null){
            if(SuerteList.size()>0 ) {
                SuerteListName.clear();
                for (int i = 0; i < SuerteList.size(); i++) {
                    SuerteListName.add(SuerteList.get(i).getNombre());
                }
                adapterSuerte = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, SuerteListName);
                AlerDialogButton("Suerte",adapterSuerte);
            }
        }else{
            SuerteListName.clear();
            SuerteListName.add("No hay Suertes");
            adapterSuerte = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, SuerteListName);
            AlerDialogButton("Suerte",adapterSuerte);
        }
    }


    void AlerDialogButton(final String titulo, final ArrayAdapter<String> adapter){

        final String[] text = {""};

        LayoutInflater li = LayoutInflater.from(thiscontext);
        View promptsView = li.inflate(R.layout.dialoglabor, null);

        final AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(thiscontext);
        alertdialogbuilder.setView(promptsView);

        final ListView listview = (ListView)promptsView.findViewById(R.id.listViewDialogLabor);
        final EditText editText = (EditText) promptsView.findViewById(R.id.editTextBusquedaLabor);
        final Button botonSeleccionar = (Button)promptsView.findViewById(R.id.btnSeleccionarLabor);

        listview.setAdapter(adapter);

        alertdialogbuilder.setTitle("Seleccione "+titulo);
        final AlertDialog alert = alertdialogbuilder.create();

        //<editor-fold desc="BOTON SELECCIONAR">
        botonSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO", "PRESIONO" + text[0]);
                if (text[0] != "") {
                    switch (titulo){
                        case "Hacienda":
                            txhacienda.setText("Hacienda: "+hacienda);
                            //args.putString("HaciendaSelec",hacienda);
                            uri = Uri.parse(SET_HACIENDA+":"+ hacienda);
                            mCallback.onFragmentIteration(uri);
                            alert.cancel();
                            listadoSuerte();
                            break;
                        case "Suerte":
                            txsuerte.setText("Suerte: "+suerte);
                            //args.putString("SuerteSelec",suerte);
                            uri = Uri.parse(SET_SUERTE+":"+ suerte);
                            mCallback.onFragmentIteration(uri);
                            alert.cancel();
                            break;
                    }
                }
            }
        });
        //</editor-fold>

        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text[0] = editText.getText().toString().toLowerCase().trim();
                switch (titulo){
                    case "Hacienda":
                        HaciendaList = database.obtenerHaciendasAutocompletar(Hacienda.NOMBRE, text[0]);
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
                            botonSeleccionar.setEnabled(false);
                        }
                        break;

                    case "Suerte":
                        SuerteList = database.obtenerSuertesAutocompletar(Suerte.NOMBRE, text[0],Suerte.KEY_HACIENDA, hacienda);
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
                            botonSeleccionar.setEnabled(false);
                        }
                        break;
                }
                listview.setAdapter(adapter);
            }
        });

        //<editor-fold desc="ITEMCLICKLISTENER">
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Object select= null;
                Bundle args = new Bundle();
                select= adapter.getItem(position);

                botonSeleccionar.setEnabled(true);
                switch (titulo){

                    case "Hacienda":
                        hacienda = select.toString();
                        editText.setText(hacienda);
                        break;

                    case "Suerte":
                        suerte = select.toString();
                        editText.setText(suerte);
                        break;
                }
                //alert.cancel();
            }
        });
        //</editor-fold>


        alert.show();
    }

    void AlerDialogButton(){

        final String[] text = {""};

        LayoutInflater li = LayoutInflater.from(thiscontext);
        View promptsView = li.inflate(R.layout.dialogevento, null);


        final AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(thiscontext);
        alertdialogbuilder.setView(promptsView);
        final TextView txtEvento = (TextView)promptsView.findViewById(R.id.txtEvento);
        final Button botonIniciar =(Button) promptsView.findViewById(R.id.btnIniciar);
        final Button botonDetener = (Button)promptsView.findViewById(R.id.btnDetener);
        final Button botonSalir = (Button)promptsView.findViewById(R.id.btnSalir);

        botonIniciar.setEnabled(true);
        botonDetener.setEnabled(false);
        botonSalir.setEnabled(true);

        txtEvento.setText("Registro de Labor");
        //alertdialogbuilder.setTitle("Pulse");
        final AlertDialog alert = alertdialogbuilder.create();

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);
                botonIniciar.setEnabled(false);
                botonDetener.setEnabled(true);
                //uri = Uri.parse(SET_EVENTO +":"+evento+":iniciar");
                //mCallback.onFragmentIteration(uri);
            }
        });

        botonDetener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);
                botonIniciar.setEnabled(true);
                botonDetener.setEnabled(false);
                //uri = Uri.parse(SET_EVENTO +":"+evento+":detener");
                //mCallback.onFragmentIteration(uri);
                alert.cancel();
            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);
                //uri = Uri.parse(SET_EVENTO +":"+evento+":salir");
                //mCallback.onFragmentIteration(uri);
                alert.cancel();
            }
        });

        alert.show();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (utils.OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + "El Activity debe implementar la interfaz FragmentIterationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("Fragmento Labor","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragmento Labor","onPause");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragmento Labor","onDestroy");
    }
}
