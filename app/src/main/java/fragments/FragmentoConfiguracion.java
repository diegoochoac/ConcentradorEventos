package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.ListView;

import com.concentrador.agrum.concentradoreventos.R;

import java.util.ArrayList;
import java.util.List;

import basedatos.DatabaseCrud;
import basedatos.contratista.Contratista;
import basedatos.contratista.Maquina;
import basedatos.contratista.Usuario;
import basedatos.evento.TipoEvento;
import basedatos.terreno.Hacienda;
import basedatos.terreno.Suerte;
import utils.OnFragmentInteractionListener;

public class FragmentoConfiguracion extends Fragment implements OnClickListener {

    private Button btnContratista, btnUsuario, btnMaquina, btnHacienda, btnSuerte, btnTipoEven;

    private DatabaseCrud database;

    private List<Contratista> ContratistaList;
    private List<String> ContratistaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterContratista;
    private String contratista="";
    Contratista nuevoContra;

    private List<Usuario> UsuarioList;
    private List<String> UsuarioListName = new ArrayList<>();
    private ArrayAdapter<String> adapterUsuario;
    private String usuario="";
    Usuario nuevoUser;

    private List<Maquina> MaquinaList;
    private List<String> MaquinaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterMaquina;
    private String maquina="";
    Maquina nuevoMaquina;

    private List<Hacienda> HaciendaList;
    private List<String> HaciendaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterHacienda;
    private String hacienda="";

    private List<Suerte> SuerteList;
    private List<String> SuerteListName = new ArrayList<>();
    private ArrayAdapter<String> adapterSuerte;
    private String suerte="";

    private List<TipoEvento> tipoEventoList;
    private List<String> tipoEventoListName = new ArrayList<>();
    private ArrayAdapter<String> adapterTipoEvento;
    private String evento="";

    Context thiscontext;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mCallback = null;
    Uri uri = Uri.parse("");

    public FragmentoConfiguracion() {
        // Required empty public constructor
    }

    public static FragmentoConfiguracion newInstance(String param1, String param2) {
        FragmentoConfiguracion fragment = new FragmentoConfiguracion();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);
        database = new DatabaseCrud(container.getContext());
        thiscontext = container.getContext();

        btnContratista = (Button)view.findViewById(R.id.btnAgregarContra);
        btnContratista.setOnClickListener(this);
        btnContratista.setEnabled(true);

        btnUsuario = (Button)view.findViewById(R.id.btnAgregarUsua);
        btnUsuario.setOnClickListener(this);
        btnUsuario.setEnabled(false);

        btnMaquina = (Button)view.findViewById(R.id.btnAgregarMaqui);
        btnMaquina.setOnClickListener(this);
        btnMaquina.setEnabled(false);

        btnHacienda = (Button)view.findViewById(R.id.btnAgregarHacienda);
        btnHacienda.setOnClickListener(this);
        btnHacienda.setEnabled(true);

        btnSuerte = (Button)view.findViewById(R.id.btnAgregarSuerte);
        btnSuerte.setOnClickListener(this);
        btnSuerte.setEnabled(false);

        btnTipoEven= (Button)view.findViewById(R.id.btnAgregarEvento);
        btnTipoEven.setOnClickListener(this);
        btnTipoEven.setEnabled(true);

        return view;
    }


    void AlerDialogList(final ArrayAdapter<String> adapter, final String lista){

        final String[] text = {""};


        LayoutInflater li = LayoutInflater.from(thiscontext);
        View promptsView = li.inflate(R.layout.dialogconfiguracion, null);


        final AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(thiscontext);
        alertdialogbuilder.setView(promptsView);

        final ListView listview = (ListView)promptsView.findViewById(R.id.listViewDialog);
        final EditText editText = (EditText) promptsView.findViewById(R.id.editTextBusqueda);
        final Button botonAgregar =(Button) promptsView.findViewById(R.id.btnAgregar);
        final Button botonEliminar = (Button)promptsView.findViewById(R.id.btnEliminar);
        final Button botonSeleccionar = (Button)promptsView.findViewById(R.id.btnSeleccionar);
        botonAgregar.setEnabled(false);

        listview.setAdapter(adapter);

        alertdialogbuilder.setTitle("Configuración");
        final AlertDialog alert = alertdialogbuilder.create();

        //<editor-fold desc="BOTON AGREGAR">
        botonAgregar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);

                switch (lista){
                    case "Contratista":
                        contratista = text[0];
                        nuevoContra = new Contratista(contratista);
                        database.crearContratista(nuevoContra);
                        btnContratista.setText("CONTRATISTA: "+contratista);
                        btnUsuario.setEnabled(true);
                        break;
                    case "Usuario":
                        usuario = text[0];
                        nuevoUser = new Usuario(usuario,nuevoContra);
                        database.crearUsuario(nuevoUser);
                        btnUsuario.setText("USUARIO: "+usuario);
                        btnMaquina.setEnabled(true);
                        break;
                    case "Maquina":
                        maquina = text[0];
                        nuevoMaquina = new Maquina(maquina,nuevoContra);
                        database.crearMaquina(nuevoMaquina);
                        btnMaquina.setText("MAQUINA: "+maquina);
                        break;
                    case "Evento":
                        break;
                    case "Hacienda":
                        break;
                    case "Suerte":
                        break;
                }
                alert.cancel();
            }
        });
        //botonAgregar.setVisibility(View.INVISIBLE);
        //</editor-fold>

        //<editor-fold desc="BOTON SELECCIONAR">
        botonSeleccionar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);
                switch (lista){
                    case "Contratista":
                        btnContratista.setText("CONTRATISTA: "+contratista);
                        btnUsuario.setText("AGREGAR USUARIO");
                        btnUsuario.setEnabled(true);
                        btnMaquina.setText("AGREGAR MAQUINA");
                        nuevoContra = database.obtenerContratista(contratista);
                        break;
                    case "Usuario":
                        btnUsuario.setText("USUARIO: "+usuario);
                        btnMaquina.setText("AGREGAR MAQUINA");
                        btnMaquina.setEnabled(true);
                        break;
                    case "Maquina":
                        btnMaquina.setText("MAQUINA: "+maquina);
                        break;
                    case "Evento":
                        break;
                    case "Hacienda":
                        break;
                    case "Suerte":
                        break;
                }
                alert.cancel();
            }
        });
        //botonAgregar.setVisibility(View.INVISIBLE);
        //</editor-fold>

        //<editor-fold desc="BOTON ELIMINAR">
        botonEliminar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","ELIMINAR"+text[0]);

                switch (lista){
                    case "Contratista":
                        contratista = text[0];
                        nuevoContra = database.obtenerContratista(contratista);
                        database.eliminarContratista(nuevoContra);
                        break;
                    case "Usuario":
                        usuario = text[0];
                        nuevoUser = database.obtenerUsuario(usuario);
                        database.eliminarUsuario(nuevoUser);
                        break;
                    case "Maquina":
                        maquina = text[0];
                        nuevoMaquina= database.obtenerMaquina(maquina);
                        database.eliminarMaquina(nuevoMaquina);
                        break;
                    case "Evento":
                        break;
                    case "Hacienda":
                        break;
                    case "Suerte":
                        break;
                }
                alert.cancel();
            }
        });
        //botonAgregar.setVisibility(View.INVISIBLE);
        //</editor-fold>

        editText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text[0] = editText.getText().toString().toLowerCase().trim();
                botonAgregar.setEnabled(false);
                switch (lista){
                    case "Contratista":
                        ContratistaList = database.obtenerContratistaAutocompletar(Contratista.NOMBRE, text[0]);
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
                            botonAgregar.setEnabled(true);
                        }
                        break;

                    case "Usuario":
                        UsuarioList = database.obtenerUsuarioAutocompletar(Usuario.NOMBRE, text[0],Usuario.KEY_CONTRATISTA,contratista);
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
                            botonAgregar.setEnabled(true);
                        }
                        break;

                    case "Maquina":
                        MaquinaList = database.obtenerMaquinaAutocompletar(Maquina.NOMBRE, text[0],Maquina.KEY_CONTRATISTA,contratista);
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
                            botonAgregar.setEnabled(true);
                        }
                        break;

                    case "Evento":
                        tipoEventoList = database.obtenerTipoEventoAutocompletar(TipoEvento.NOMBRE, text[0]);
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
                            botonAgregar.setEnabled(true);
                        }
                        break;

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
                            botonAgregar.setEnabled(true);
                        }
                        break;

                    case "Suerte":
                        SuerteList = database.obtenerSuertesAutocompletar(Suerte.NOMBRE, text[0],Suerte.KEY_HACIENDA,hacienda);
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
                            botonAgregar.setEnabled(true);
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
                Uri uri = Uri.parse("");
                select= adapter.getItem(position);

                switch (lista){
                    case "Contratista":
                        contratista = select.toString();
                        editText.setText(contratista);
                        //btnContratista.setText("CONTRATISTA: "+contratista);
                        //btnUsuario.setEnabled(true);
                        //nuevoContra = database.obtenerContratista(contratista);
                        //uri = Uri.parse(SET_EVENTO +":"+ select.toString());
                        //mListener.onFragmentInteraction(uri);
                        break;
                    case "Usuario":
                        usuario = select.toString();
                        editText.setText(usuario);
                        //btnUsuario.setText("USUARIO: "+usuario);
                        //btnMaquina.setEnabled(true);
                        //uri = Uri.parse(SET_EVENTO +":"+ select.toString());
                        //mListener.onFragmentInteraction(uri);
                        break;
                    case "Maquina":
                        maquina = select.toString();
                        editText.setText(maquina);
                        //btnMaquina.setText("MAQUINA: "+maquina);
                        //btnTipoEven.setEnabled(true);
                        break;
                    case "Evento":
                        evento = select.toString();
                        btnTipoEven.setText("EVENTO: "+evento);
                        //uri = Uri.parse(SET_EVENTO +":"+ select.toString());

                        if(select.equals("FragmentoLabor en Terreno")){
                            btnHacienda.setVisibility(View.VISIBLE);
                            btnSuerte.setVisibility(View.VISIBLE);
                            btnHacienda.setEnabled(true);
                        }
                        else {
                            btnHacienda.setVisibility(View.INVISIBLE);
                            btnSuerte.setVisibility(View.INVISIBLE);
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
                        break;
                }

                //alert.cancel();
            }
        });
        //</editor-fold>

        alert.show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnFragmentInteractionListener) activity;
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
        Log.i("Fragmento Configu","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragmento Configu","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragmento Configu","onDestroy");
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnAgregarContra:
                ContratistaList = database.obtenerContratistas();
                if(ContratistaList != null){
                    if(ContratistaList.size()>0){
                        ContratistaListName.clear();
                        for(int i=0; i<ContratistaList.size(); i++){
                            ContratistaListName.add(ContratistaList.get(i).getNombre());
                        }
                        adapterContratista = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,ContratistaListName);
                        AlerDialogList(adapterContratista, "Contratista");
                    }
                }else{
                    ContratistaListName.clear();
                    ContratistaListName.add("No hay Contratistas");
                    adapterContratista = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, ContratistaListName);
                    AlerDialogList(adapterContratista, "Contratista");
                }
                break;

            case R.id.btnAgregarUsua:
                UsuarioList = database.obtenerUsuariosporId(Usuario.KEY_CONTRATISTA,contratista);
                if(UsuarioList != null) {
                    if (UsuarioList.size() > 0) {
                        UsuarioListName.clear();
                        for (int i = 0; i < UsuarioList.size(); i++) {
                            UsuarioListName.add(UsuarioList.get(i).getNombre());
                        }
                        adapterUsuario = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, UsuarioListName);
                        AlerDialogList(adapterUsuario, "Usuario");
                    }
                }else{
                    UsuarioListName.clear();
                    UsuarioListName.add("No hay Usuarios");
                    adapterUsuario = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, UsuarioListName);
                    AlerDialogList(adapterUsuario, "Usuario");
                }
                break;

            case R.id.btnAgregarMaqui:
                MaquinaList = database.obtenerMaquinasporId(Maquina.KEY_CONTRATISTA,contratista);
                if(MaquinaList != null){
                    if(MaquinaList.size()>0){
                        MaquinaListName.clear();
                        for(int i=0; i<MaquinaList.size(); i++){
                            MaquinaListName.add(MaquinaList.get(i).getNombre());
                        }
                        adapterMaquina = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,MaquinaListName);
                        AlerDialogList(adapterMaquina, "Maquina");
                    }
                }else{
                    MaquinaListName.clear();
                    MaquinaListName.add("No hay Maquinas");
                    adapterMaquina = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, MaquinaListName);
                    AlerDialogList(adapterMaquina, "Maquina");
                }
                break;

            case R.id.btnAgregarEvento:
                Log.i("Fragmen Config", "onClick btnSeleEvento");
                tipoEventoList = database.obtenerTipoEvento();
                if(tipoEventoList != null){
                    if(tipoEventoList.size()>0 ){
                        tipoEventoListName.clear();
                        for(int i=0; i<tipoEventoList.size(); i++){
                            tipoEventoListName.add(tipoEventoList.get(i).getNombre());
                        }
                        adapterTipoEvento = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,tipoEventoListName);
                        AlerDialogList(adapterTipoEvento, "Evento");
                    }
                }else{
                    tipoEventoListName.clear();
                    tipoEventoListName.add("No hay Eventos");
                    adapterTipoEvento = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, tipoEventoListName);
                    AlerDialogList(adapterTipoEvento, "Evento");
                }
                break;

            case R.id.btnAgregarHacienda:
                Log.i("Fragmen Config", "onClick btnHacienda");
                HaciendaList = database.obtenerHaciendas();
                if(HaciendaList != null){
                    if(HaciendaList.size()>0 ){
                        HaciendaListName.clear();
                        for(int i=0; i<HaciendaList.size(); i++){
                            HaciendaListName.add(HaciendaList.get(i).getNombre());
                        }
                        adapterHacienda = new ArrayAdapter<String>(thiscontext,android.R.layout.simple_list_item_1,HaciendaListName);
                        AlerDialogList(adapterHacienda, "Hacienda");
                    }
                }else{
                    HaciendaListName.clear();
                    HaciendaListName.add("No hay Haciendas");
                    adapterHacienda = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, HaciendaListName);
                    AlerDialogList(adapterHacienda, "Hacienda");
                }
                break;

            case R.id.btnAgregarSuerte:
                Log.i("Fragmen Config", "onClick btnSuerte");
                Log.i("Fragmen Config",hacienda);
                SuerteList = database.obtenerSuertesporId(Suerte.KEY_HACIENDA,hacienda);
                if(SuerteList != null){
                    Log.i("EventosFragment","Tamaño: "+SuerteList.size());
                    if(SuerteList.size()>0 ) {
                        SuerteListName.clear();
                        for (int i = 0; i < SuerteList.size(); i++) {
                            SuerteListName.add(SuerteList.get(i).getNombre());
                        }
                        adapterSuerte = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, SuerteListName);
                        AlerDialogList(adapterSuerte, "Suerte");
                    }
                }else{
                    SuerteListName.clear();
                    SuerteListName.add("No hay Suertes");
                    adapterSuerte = new ArrayAdapter<String>(thiscontext, android.R.layout.simple_list_item_1, SuerteListName);
                    AlerDialogList(adapterSuerte, "Suerte");
                }
                break;
        }
    }
}
