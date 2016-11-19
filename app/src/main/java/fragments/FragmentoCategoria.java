package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.concentrador.agrum.concentradoreventos.R;

import basedatos.contratista.Contratista;
import utils.AdaptadorCategorias;
import utils.AdaptadorContratista;
import utils.Contratistas;
import utils.Eventos;
import utils.ItemClickListener;
import utils.OnFragmentInteractionListener;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Categorías"
 */
public class FragmentoCategoria extends Fragment implements ItemClickListener {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorCategorias adaptador;
    private AdaptadorContratista adaptadorContratista;

    private String evento = "";

    int indiceSeccion;
    Context thiscontext;

    //Variables que se van hacia el MAIN
    public final static String SET_EVENTO = "Evento";

    private OnFragmentInteractionListener mCallback = null;
    Uri uri = Uri.parse("");


    public static FragmentoCategoria nuevaInstancia(int indiceSeccion) {
        FragmentoCategoria fragment = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador1);

        indiceSeccion = getArguments().getInt(INDICE_SECCION);

        thiscontext = container.getContext();
        switch (indiceSeccion) {
            case 0:
                layoutManager = new GridLayoutManager(getActivity(), 2);
                reciclador.setLayoutManager(layoutManager);

                adaptador = new AdaptadorCategorias(Eventos.EVENTOS);
                reciclador.setAdapter(adaptador);
                adaptador.setClickListener(this);
                break;
        }

        return view;
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

        txtEvento.setText(evento);
        //alertdialogbuilder.setTitle("Pulse");
        final AlertDialog alert = alertdialogbuilder.create();

        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);
                botonIniciar.setEnabled(false);
                botonDetener.setEnabled(true);
                uri = Uri.parse(SET_EVENTO +":"+evento+":iniciar");
                mCallback.onFragmentIteration(uri);
            }
        });

        botonDetener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);
                botonIniciar.setEnabled(true);
                botonDetener.setEnabled(false);
                uri = Uri.parse(SET_EVENTO +":"+evento+":detener");
                mCallback.onFragmentIteration(uri);
                alert.cancel();
            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("PRESIONO","PRESIONO"+text[0]);
                uri = Uri.parse(SET_EVENTO +":"+evento+":salir");
                mCallback.onFragmentIteration(uri);
                alert.cancel();
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
        Log.i("Fragmento Categoria","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fragmento Categoria","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragmento Categoria","onDestroy");
    }

    @Override
    public void onClick(View view, int position) {

        switch (indiceSeccion) {
            case 0:
                Log.i("Fragmento Categoria","Evento posicion: "+position);
                evento = Eventos.EVENTOS.get(position).getNombre().toString();

                AlerDialogButton();
                //uri = Uri.parse(SET_EVENTO +":"+evento);
                //mCallback.onFragmentIteration(uri);
                break;
        }
    }
}