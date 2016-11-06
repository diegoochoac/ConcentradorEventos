package fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.concentrador.agrum.concentradoreventos.R;

import utils.AdaptadorInicio;
import utils.ItemClickListener;


public class FragmentoInicio extends Fragment implements ItemClickListener {

    private RecyclerView reciclador;
    private LinearLayoutManager layoutManager;
    //private GridLayoutManager layoutManager;
    private AdaptadorInicio adaptador;

    public FragmentoInicio() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_inicio, container, false);

        reciclador = (RecyclerView) view.findViewById(R.id.reciclador);
        layoutManager = new LinearLayoutManager(getActivity());
        //layoutManager = new GridLayoutManager(getActivity(),2);

        reciclador.setLayoutManager(layoutManager);

        adaptador = new AdaptadorInicio();
        reciclador.setAdapter(adaptador);
        adaptador.setClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view, int position) {

        Log.i("Fragmento Inicio","Registro Posicion: "+position);

    }
}
