package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.concentrador.agrum.concentradoreventos.R;

import basedatos.contratista.Contratista;
import utils.AdaptadorCategorias;
import utils.AdaptadorContratista;
import utils.Contratistas;
import utils.Eventos;
import utils.ItemClickListener;
import utils.OnFragmentInteractionListener;

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

    int indiceSeccion;

    private OnFragmentInteractionListener mCallback = null;


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

        switch (indiceSeccion) {
            case 0:
                layoutManager = new GridLayoutManager(getActivity(), 2);
                reciclador.setLayoutManager(layoutManager);

                adaptador = new AdaptadorCategorias(Eventos.EVENTOS);
                reciclador.setAdapter(adaptador);
                adaptador.setClickListener(this);
                break;
            case 1:
                layoutManager = new GridLayoutManager(getActivity(), 3);
                reciclador.setLayoutManager(layoutManager);

                adaptadorContratista = new AdaptadorContratista(Contratistas.CONTRATISTAS);//TODO cambiar
                reciclador.setAdapter(adaptadorContratista);
                adaptadorContratista.setClickListener(this);
                break;
            case 2:
                //adaptador = new AdaptadorCategorias(Eventos.POSTRES);
                //reciclador.setAdapter(adaptador);
                //adaptador.setClickListener(this);
                break;
        }

        return view;
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
                Bundle args = new Bundle();
                args.putInt("EventoSelec",position);
                mCallback.onFragmentIteration(args);
                break;
            case 1:
                Log.i("Fragmento Categoria","Contratista posicion: "+position);
                break;
            case 2:
                Log.i("Fragmento Categoria","Lugar posicion: "+position);
                break;
        }


    }
}