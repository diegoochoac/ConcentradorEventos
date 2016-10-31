package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.concentrador.agrum.concentradoreventos.R;

import utils.OnFragmentInteractionListener;


public class EventoSeleccionado extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int eventoSelec = -1;
    String contratistaSelec="", trabajadorSelec="", maquinaSelec="", haciendaSelec ="", suerteSelec="";

    TextView evento, contratista, trabajador, maquina, hacienda, suerte;
    ImageView imagen;

    FloatingActionButton FAB1;
    FloatingActionButton FAB2;

    private OnFragmentInteractionListener mCallback = null;



    public EventoSeleccionado() {
        // Required empty public constructor
    }

    public static EventoSeleccionado newInstance(String param1, String param2) {
        EventoSeleccionado fragment = new EventoSeleccionado();
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

        View view = inflater.inflate(R.layout.fragment_evento_seleccionado, container, false);

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


        evento = (TextView) view.findViewById(R.id.txEvento);
        evento.setText(""+eventoSelec); //TODO Cambiar
        contratista = (TextView) view.findViewById(R.id.txContratista);
        contratista.setText("Contratista: "+contratistaSelec);
        trabajador = (TextView) view.findViewById(R.id.txTrabajador);
        trabajador.setText("Trabajador: "+trabajadorSelec);
        maquina =  (TextView) view.findViewById(R.id.txMaquina);
        maquina.setText("Maquina: "+maquinaSelec);
        hacienda = (TextView) view.findViewById(R.id.txHacienda);
        hacienda.setText("Hacienda: "+haciendaSelec);
        suerte = (TextView) view.findViewById(R.id.txSuerte);
        suerte.setText("Suerte: "+suerteSelec);


        FAB1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        FAB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Evento Selecciondado","Boton Regresar");
                Bundle args = new Bundle();
                args.putBoolean("Regresar",true);
                mCallback.onFragmentIteration(args);
            }
        });

        FAB2 = (FloatingActionButton) view.findViewById(R.id.fab2);
        FAB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Evento Selecciondado","Boton Iniciar");
            }
        });

        return view;
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
        Log.i("Evento Seleccionado","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Evento Seleccionado","onPause");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Evento Seleccionado","onDestroy");
    }
}
