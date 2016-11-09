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

import basedatos.DatabaseCrud;
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

        database = new DatabaseCrud(getApplicationContext()); //TODO revisar por que al quitar el context deja de funcionar

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

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
                args.putString(usuario, sharedpreferences.getString(usuario,""));
                args.putString(maquina, sharedpreferences.getString(maquina,""));
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
                Log.i("main","ENTROGuardarUsuario"+ spl[1]);
                editor.putString(usuario,spl[1]);
                editor.commit();
                break;

            case FragmentoCuenta.SET_MAQUINA:
                Log.i("main","ENTROGuardarMaquina"+ spl[1]);
                editor.putString(maquina,spl[1]);
                editor.commit();
                break;


        }

        //int EventoSeleccionado = param.getInt("Evento");
        //boolean regresar = param.getBoolean("Regresar");
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
