package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.concentrador.agrum.concentradoreventos.R;

import basedatos.evento.Evento;
import basedatos.evento.TipoEvento;
import utils.AdaptadorCategorias;
import utils.Eventos;
import utils.ItemClickListener;

/**
 * Fragmento que representa el contenido de cada pestaña dentro de la sección "Categorías"
 */
public class FragmentoCategoria extends Fragment implements ItemClickListener {

    private static final String INDICE_SECCION
            = "com.restaurantericoparico.FragmentoCategoriasTab.extra.INDICE_SECCION";

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private AdaptadorCategorias adaptador;

    public static FragmentoCategoria nuevaInstancia(int indiceSeccion) {
        FragmentoCategoria fragment = new FragmentoCategoria();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_grupo_items, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        reciclador.setLayoutManager(layoutManager);

        int indiceSeccion = getArguments().getInt(INDICE_SECCION);

        switch (indiceSeccion) {
            case 0:
                adaptador = new AdaptadorCategorias(Eventos.EVENTOS);
                Log.i("posision","Adaptadorrrrrrrrrrr:");
                break;
            case 1:
                adaptador = new AdaptadorCategorias(Eventos.BEBIDAS);//TODO cambiar
                break;
            case 2:
                adaptador = new AdaptadorCategorias(Eventos.POSTRES);
                break;
        }

        reciclador.setAdapter(adaptador);
        adaptador.setClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view, int position) {
        Log.i("PRUEBA CLICK","posicion: "+position);
    }
}