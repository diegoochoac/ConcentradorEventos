package com.concentrador.agrum.concentradoreventos;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import basedatos.DatabaseCrud;
import basedatos.contratista.Contratista;
import basedatos.evento.Evento;
import basedatos.evento.TipoEvento;
import fragments.FragmentoCategoria;
import fragments.FragmentoCategorias;
import fragments.FragmentoConfiguracion;
import fragments.FragmentoCuenta;
import fragments.FragmentoInicio;
import fragments.FragmentoLabor;
import utils.OnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private DrawerLayout drawerLayout;

    //BASE DE DATOS
    private DatabaseCrud database;

    public static final String MyPREFERENCES = "MyPrefs" ;
    private String contratista="ContratistaKey";
    private String usuario="UsuarioKey";
    private String maquina="MaquinaKey";
    private String suerte="SuerteKey";
    private String hacienda="HaciendaKey";


    private String eventoSelec ="";

    private String tiempoInicio;
    private String tiempoFin;
    private String fecha;


    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        database = new DatabaseCrud(getApplicationContext());

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        List<Contratista> contratistalista = database.obtenerContratistas();
        editor.putString(contratista,contratistalista.get(0).getNombre());
        editor.commit();

        //<editor-fold desc="Ocultar toolbar naview">
/*      requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

      getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.INVISIBLE);


        inicializarEvento();*/
        //</editor-fold>

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta();
                Bundle args = new Bundle();
                args.putString(contratista, sharedpreferences.getString(contratista,""));
                args.putString(usuario, sharedpreferences.getString(usuario,""));
                args.putString(maquina, sharedpreferences.getString(maquina,""));
                args.putString(hacienda, sharedpreferences.getString(hacienda,""));
                args.putString(suerte, sharedpreferences.getString(suerte,""));
                fragmentoGenerico.setArguments(args);
                break;
            case R.id.item_categorias:

                break;
            case R.id.item_configuracion:
                fragmentoGenerico = new FragmentoConfiguracion();
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    @Override
    public void onFragmentIteration(Uri uri) {

        Log.i("MAIN ITERA","URI: "+uri.toString());
        Fragment fragmentoGenerico= null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();

        String[] spl = uri.toString().split(":");
        switch (spl[0]){

            case FragmentoInicio.SET_REGISTRO:

                if (spl[1].equals("0") ){
                    Log.i("main","ENTRO LABOR"+spl[1]);
                    fragmentoGenerico = new FragmentoLabor();
                    args.putString("Contratista",  sharedpreferences.getString(contratista,""));
                    args.putString("Trabajador", sharedpreferences.getString(usuario,""));
                    args.putString("Maquina",  sharedpreferences.getString(maquina,""));
                    args.putString("Hacienda",  sharedpreferences.getString(hacienda,""));
                    args.putString("Suerte",  sharedpreferences.getString(suerte,""));
                    fragmentoGenerico.setArguments(args);
                }
                else if (spl[1].equals("1")){
                    Log.i("main","ENTRO EVENTOS"+spl[1]);
                    fragmentoGenerico = new FragmentoCategorias();
                }

                break;

            case FragmentoCuenta.SET_USUARIO:
                Log.i("main","ENTROGuardarUsuario "+ spl[1]);
                editor.putString(usuario,spl[1]);
                editor.commit();
                break;

            case FragmentoCuenta.SET_MAQUINA:
                Log.i("main","ENTROGuardarMaquina "+ spl[1]);
                editor.putString(maquina,spl[1]);
                editor.commit();
                break;

            case FragmentoLabor.SET_HACIENDA:
                Log.i("main","ENTROGuardarHacienda "+ spl[1]);
                editor.putString(hacienda,spl[1]);
                editor.commit();
                break;

            case FragmentoLabor.SET_SUERTE:
                Log.i("main","ENTROGuardarSuerte"+ spl[1]);
                editor.putString(suerte,spl[1]);
                editor.commit();
                break;

            case FragmentoCategoria.SET_EVENTO:
                Log.i("main","ENTRO EVENTO "+ spl[1]+" valor: "+spl[2]);
                eventoSelec = spl[1];
                if(spl[2].equals("iniciar")) {
                    Log.i("main","ENTRO EVENTO INICIAR");
                    tiempoInicio = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
                }else if(spl[2].equals("detener")){
                    Log.i("main","ENTRO EVENTO DETENER");
                    tiempoFin = java.text.DateFormat.getTimeInstance().format(Calendar.getInstance().getTime());
                    fecha =java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                    TipoEvento tipoEventoSel = database.obtenerTipoEvento(eventoSelec);
                    Evento nuevo = new Evento(tipoEventoSel,tiempoInicio,tiempoFin,fecha,"No");
                    database.crearEvento(nuevo);

                    syncServerEventos();
                }

                break;


        }

        //if(regresar==true){
        //    fragmentoGenerico = new FragmentoInicio();
        //}

        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        int numero = getFragmentManager().getBackStackEntryCount();

        Log.i("MAIN","back"+numero);
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else{
            super.onBackPressed();
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
                    Toast.makeText(getApplicationContext(), "DB Event Sync completed!", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


//<editor-fold desc="Ocultar">
 /*   @TargetApi(19)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                | View.INVISIBLE);
            }
        }
    }

    public void inicializarEvento(){
        Fragment fragment = null;
        fragment = new EventoFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.fragmentMain, fragment);
        trans.addToBackStack(null);
        trans.commit();
    }*/
    //</editor-fold>


}
