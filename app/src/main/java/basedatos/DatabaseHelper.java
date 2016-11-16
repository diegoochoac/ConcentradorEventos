package basedatos;

/**
 * Created by diego on 26/08/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;




import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import basedatos.contratista.Contratista;
import basedatos.contratista.Maquina;
import basedatos.contratista.Usuario;
import basedatos.evento.Evento;
import basedatos.evento.TipoEvento;
import basedatos.terreno.Hacienda;
import basedatos.terreno.Suerte;
import basedatos.terreno.Variedad;
import basedatos.terreno.Zona;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "concentrador.db";
    private static final int DATABASE_VERSION = 1;

    //Objeto Dao qye se utiliza para acceder a la tabla usuario
    //Contratista
    private Dao<Usuario,Integer> usuarioDao;
    private Dao<Contratista, Integer> contratistaDao;
    private Dao<Maquina, Integer> maquinaDao;
    //Terreno
    private Dao<Hacienda, Integer> haciendaDao;
    private Dao<Suerte, Integer> suerteDao;
    private Dao<Variedad, Integer> variedadDao;
    private Dao<Zona, Integer> zonaDao;
    //Evento
    private Dao<Evento, Integer> eventoDao;
    private Dao<TipoEvento, Integer> tipoeventoDao;



    public DatabaseHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Metodo invocado cuando la base de datos es creada. Usualmente se hacen llamadas a los metodos
     * createTable para crear las tablas que almacenaran los datos
     * @param db
     * @param source
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource source) {
        try {
            Log.i(DatabaseHelper.class.getSimpleName(), "onCreate()");
            //
            TableUtils.createTable(source,Usuario.class);
            TableUtils.createTable(source,Contratista.class);
            TableUtils.createTable(source,Maquina.class);

            TableUtils.createTable(source,Hacienda.class);
            TableUtils.createTable(source,Suerte.class);
            TableUtils.createTable(source,Variedad.class);
            TableUtils.createTable(source,Zona.class);

            TableUtils.createTable(source,Evento.class);
            TableUtils.createTable(source,TipoEvento.class);


            registrosInicialesEventos();
            registrosInicialesContratistasUsuario();
            registrosInicialesTerreno();

        }catch (SQLException ex) {
            Log.e(DatabaseHelper.class.getSimpleName(),"Imposible crear base de datos",ex);
            throw  new RuntimeException(ex);
        }
    }

    /**
     * Este metodo es invocado cuando la aplicacion es actualizada y tiene un numero de version
     * superio. permite el ajuste a los mtados para alinearse con la nueva version
     * @param db
     * @param source
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource source, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getSimpleName(), "onUpgrade()");
            //
            TableUtils.dropTable(source,Usuario.class, true);
            TableUtils.dropTable(source,Contratista.class, true);
            TableUtils.dropTable(source,Maquina.class, true);
            //
            TableUtils.dropTable(source,Hacienda.class, true);
            TableUtils.dropTable(source,Suerte.class, true);
            TableUtils.dropTable(source,Variedad.class, true);
            TableUtils.dropTable(source,Zona.class, true);
            //
            TableUtils.dropTable(source,Evento.class, true);
            TableUtils.dropTable(source,TipoEvento.class, true);

            onCreate(db, source);
        } catch (SQLException ex) {
            Log.e(DatabaseHelper.class.getSimpleName(),"Imposible actualizar base de datos",ex);
            throw  new RuntimeException(ex);
        }

    }

    //<editor-fold desc="Metodos Get">


    public Dao<Maquina, Integer> getMaquinaDao() throws SQLException {
        if(maquinaDao==null){
            maquinaDao = getDao(Maquina.class);
        }
        return maquinaDao;
    }

    public Dao<Usuario, Integer> getUsuarioDao()  throws SQLException {
        if(usuarioDao==null){
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Dao<Contratista, Integer> getContratistaDao()  throws SQLException {
        if(contratistaDao==null){
            contratistaDao = getDao(Contratista.class);
        }
        return contratistaDao;
    }

    public Dao<Hacienda, Integer> getHaciendaDao() throws SQLException {
        if(haciendaDao==null){
            haciendaDao = getDao(Hacienda.class);
        }
        return haciendaDao;
    }

    public Dao<Suerte, Integer> getSuerteDao() throws SQLException {
        if(suerteDao==null){
            suerteDao = getDao(Suerte.class);
        }
        return suerteDao;
    }

    public Dao<Variedad, Integer> getVariedadDao() throws SQLException {
        if(variedadDao==null){
            variedadDao = getDao(Variedad.class);
        }
        return variedadDao;
    }

    public Dao<Zona, Integer> getZonaDao() throws SQLException {
        if(zonaDao==null){
            zonaDao = getDao(Zona.class);
        }
        return zonaDao;
    }

    public Dao<Evento, Integer> getEventoDao() throws SQLException {
        if(eventoDao==null){
            eventoDao = getDao(Evento.class);
        }
        return eventoDao;
    }

    public Dao<TipoEvento, Integer> getTipoeventoDao() throws SQLException {
        if(tipoeventoDao==null){
            tipoeventoDao = getDao(TipoEvento.class);
        }
        return tipoeventoDao;
    }

    //</editor-fold>

    public void registrosInicialesEventos(){
        try {
            TipoEvento nuevo = new TipoEvento("Inspección diaria");
            tipoeventoDao.create(nuevo);
            nuevo = new TipoEvento("Desplazamiento a sitio de la labor");
            tipoeventoDao.create(nuevo);
            nuevo = new TipoEvento("Espera antes de ejecutar trabajos");
            tipoeventoDao.create(nuevo);
            nuevo = new TipoEvento("Alimentación");
            tipoeventoDao.create(nuevo);
            nuevo = new TipoEvento("Mantenimiento");
            tipoeventoDao.create(nuevo);
            nuevo = new TipoEvento("Varado");
            tipoeventoDao.create(nuevo);
            nuevo = new TipoEvento("Tanqueo");
            tipoeventoDao.create(nuevo);
            nuevo = new TipoEvento("Parado lluvia");
            tipoeventoDao.create(nuevo);

        }
        catch (SQLException ex) {
            Log.e(DatabaseHelper.class.getSimpleName(),"Imposible crear registrosIniciales",ex);
            throw  new RuntimeException(ex);
        }
    }

    public void registrosInicialesContratistasUsuario(){
        try {
            Contratista nuevoCon = new Contratista("Operadores Campo");
            contratistaDao.create(nuevoCon);

            Usuario nuevoUsu = new Usuario("Diego Ochoa",nuevoCon);
            usuarioDao.create(nuevoUsu);
            nuevoUsu = new Usuario("Andres Millan",nuevoCon);
            usuarioDao.create(nuevoUsu);

            Maquina nuevaMaq = new Maquina("deere f2000",nuevoCon);
            maquinaDao.create(nuevaMaq);

        }
        catch (SQLException ex) {
            Log.e(DatabaseHelper.class.getSimpleName(),"Imposible crear registrosIniciales",ex);
            throw  new RuntimeException(ex);
        }
    }

    public void registrosInicialesTerreno(){
        try {
            Hacienda nuevaHac= new Hacienda("001","Hacienda Maria");
            haciendaDao.create(nuevaHac);

            Variedad nuevaVar = new Variedad("0101");
            variedadDao.create(nuevaVar);
            Zona nuevaZona = new Zona("0101");
            zonaDao.create(nuevaZona);

            Suerte nuevaSuer = new Suerte("Suerte1","120",nuevaHac,nuevaVar,nuevaZona);
            suerteDao.create(nuevaSuer);

            nuevaHac = new Hacienda("002","Ingenio Manuelita");
            haciendaDao.create(nuevaHac);

            nuevaHac = new Hacienda("003","Ingenio Incauca");
            haciendaDao.create(nuevaHac);


        }
        catch (SQLException ex) {
            Log.e(DatabaseHelper.class.getSimpleName(),"Imposible crear registrosIniciales",ex);
            throw  new RuntimeException(ex);
        }
    }



}
