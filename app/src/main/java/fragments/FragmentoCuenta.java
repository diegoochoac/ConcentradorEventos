package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import basedatos.contratista.Contratista;
import basedatos.contratista.Maquina;
import basedatos.contratista.Usuario;
import basedatos.evento.TipoEvento;
import basedatos.terreno.Hacienda;
import basedatos.terreno.Suerte;
import utils.ItemClickListener;
import utils.OnFragmentInteractionListener;

public class FragmentoCuenta extends Fragment {


    TextView txtUsuario, txtMaquina;
    ImageView imagenUsuario, imagenMaquina;
    Context thiscontext;

    private DatabaseCrud database;

    private String contratista="";

    private List<Usuario> UsuarioList;
    private List<String> UsuarioListName = new ArrayList<>();
    private ArrayAdapter<String> adapterUsuario;
    private String usuario="";

    private List<Maquina> MaquinaList;
    private List<String> MaquinaListName = new ArrayList<>();
    private ArrayAdapter<String> adapterMaquina;
    private String maquina="";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mCallback = null;

    public FragmentoCuenta() {
        // Required empty public constructor
    }

    public static FragmentoCuenta newInstance(String param1, String param2) {
        FragmentoCuenta fragment = new FragmentoCuenta();
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
        View view = inflater.inflate(R.layout.fragment_fragmento_cuenta, container, false);

        contratista = "Operadores Campo";   //TODO hay que tomar el contratista desde el main
        database = new DatabaseCrud(container.getContext());
        thiscontext = container.getContext();
        txtUsuario = (TextView)view.findViewById(R.id.textUsuario);
        txtMaquina = (TextView)view.findViewById(R.id.textMaquina);

        imagenUsuario  = (ImageView) view.findViewById(R.id.imageViewUsuario);
        //<editor-fold desc="setOnClickListener Usuario">
        imagenUsuario.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.i("FragCuenta","Presiono Usuario");
                Log.i("FragCuenta",contratista);
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
                    Log.i("FragCuenta", "onClick btnTrabajador Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }
            }

        });
        //</editor-fold>

        imagenMaquina = (ImageView) view.findViewById(R.id.imageViewMaquina);
        //<editor-fold desc="setOnClickListener Maquina">
        imagenMaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("FragCuenta","Presiono Maquina");
                Log.i("FragCuenta",maquina);
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
                    Log.i("FragCuenta", "onClick btnMaquina Ninguno");
                    Toast.makeText(thiscontext,"No se encuentran registros",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //</editor-fold>

        return view;
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
                    case "Usuario":
                        usuario = select.toString();
                        txtUsuario.setText("USUARIO: "+usuario);
                        //uri = Uri.parse(SET_EVENTO +":"+ select.toString());
                        //mListener.onFragmentInteraction(uri);
                        break;
                    case "Maquina":
                        maquina = select.toString();
                        txtMaquina.setText("MAQUINA: "+maquina);

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
        Log.i("Fragmento Cuenta","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragmento Cuenta","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragmento Cuenta","onDestroy");
    }

}