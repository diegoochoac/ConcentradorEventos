package basedatos;

import android.content.Context;
import android.util.EventLog;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import basedatos.contratista.Contratista;
import basedatos.contratista.Maquina;
import basedatos.contratista.Usuario;
import basedatos.evento.Evento;
import basedatos.evento.TipoEvento;
import basedatos.terreno.Hacienda;
import basedatos.terreno.Suerte;
import basedatos.terreno.Variedad;
import basedatos.terreno.Zona;

/**
 * Created by diego on 8/09/16.
 */
public class DatabaseCrud {

    private DatabaseHelper databaseHelper = null;

    private Dao<TipoEvento, Integer> tipoEventoDao;
    private Dao<Evento, Integer> eventoDao;

    private Dao<Usuario, Integer> usuarioDao;
    private Dao<Contratista,Integer> contratistaDao;
    private Dao<Maquina, Integer> maquinaDao;

    private Dao<Hacienda, Integer> haciendaDao;
    private Dao<Suerte, Integer> suerteDao;
    private Dao<Variedad, Integer> variedadDao;
    private Dao<Zona, Integer> zonaDao;


    Context thiscontext;

    public DatabaseCrud(Context context) {
        this.thiscontext = context;
        try {

            tipoEventoDao = getHelper().getTipoeventoDao();
            eventoDao = getHelper().getEventoDao();
            maquinaDao = getHelper().getMaquinaDao();
            usuarioDao = getHelper().getUsuarioDao();
            contratistaDao = getHelper().getContratistaDao();
            haciendaDao = getHelper().getHaciendaDao();
            suerteDao = getHelper().getSuerteDao();
            variedadDao = getHelper().getVariedadDao();
            zonaDao= getHelper().getZonaDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private DatabaseHelper getHelper() {
        Log.i("DatabaseCrud", "DatabaseHelper");
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(thiscontext, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    public void releaseHelper() {
        Log.i("DatabaseCrud", "releaseHelper");

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }


    //<editor-fold desc="CRUD Evento">



    public int crearEvento(Evento nuevo){
        try {
            Log.i("DatabaseCrud","crearEvento Bien: "+nuevo.getTipoEvento()+"-"+nuevo.getId());
            return eventoDao.create(nuevo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int actualizarEvento(Evento actualizar)
    {
        try {
            return eventoDao.update(actualizar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarEvento(Evento eliminar)
    {
        try {
            return eventoDao.delete(eliminar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Evento> obtenerEvento()
    {
        try {
            return eventoDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Evento> obtenerEventoAutocompletar(String campo, String palabra){
        try {
            QueryBuilder<Evento, Integer> db= eventoDao.queryBuilder();
            Where<Evento, Integer> where = db.where();
            where.like(campo, palabra+"%");
            return where.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String composeJSONfromSQLiteEvento(){
        List<Evento> listaNoSincronizados = null;
        try {
            listaNoSincronizados = eventoDao.queryBuilder().where().eq("updateState","No").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(listaNoSincronizados);
    }

    public int dbSyncCountEvento() {
        int count = 0;
        try {
            return (int) eventoDao.queryBuilder().where().eq("updateState","No").countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateSyncStatusEvento(String id, String status) {
        try {
            UpdateBuilder<Evento,Integer> actualizar = eventoDao.updateBuilder();
            actualizar.where().eq("id",id);
            actualizar.updateColumnValue("updateState",status);
            actualizar.update();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="CRUD TipoEvento">


    public int crearTipoEvento(TipoEvento nuevo){
        try {
            Log.i("DatabaseCrud","crearTipoEvento Bien: "+nuevo.getNombre());
            return tipoEventoDao.create(nuevo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int actualizarTipoEvento(TipoEvento actualizar)
    {
        try {
            return tipoEventoDao.update(actualizar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarTipoEvento(TipoEvento eliminar)
    {
        try {
            return tipoEventoDao.delete(eliminar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public TipoEvento obtenerTipoEvento(String nombre)
    {
        try {
            QueryBuilder<TipoEvento, Integer> db= tipoEventoDao.queryBuilder();
            db.where().eq(TipoEvento.NOMBRE, nombre);
            return db.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TipoEvento> obtenerTipoEvento()
    {
        try {
            return tipoEventoDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TipoEvento> obtenerTipoEventoAutocompletar(String campo, String palabra){
        try {
            QueryBuilder<TipoEvento, Integer> db= tipoEventoDao.queryBuilder();
            Where<TipoEvento, Integer> where = db.where();
            where.like(campo, palabra+"%");
            return where.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String composeJSONfromSQLiteTipoEvento(){
        List<TipoEvento> listaNoSincronizados = null;
        try {
            listaNoSincronizados = tipoEventoDao.queryBuilder().where().eq("updateState","No").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(listaNoSincronizados);
    }

    public int dbSyncCountTipoEvento() {
        int count = 0;
        try {
            return (int) tipoEventoDao.queryBuilder().where().eq("updateState","No").countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateSyncStatusTipoEvento(String id, String status) {
        try {
            UpdateBuilder<TipoEvento,Integer> actualizar = tipoEventoDao.updateBuilder();
            actualizar.where().eq("usuario_id",id);
            actualizar.updateColumnValue("updateState",status);
            actualizar.update();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="CRUD Usuario">
    public int crearUsuario(Usuario nuevo){
        try {
            Log.i("DatabaseCrud","crearUsuario Bien: "+nuevo.getNombre());
            return usuarioDao.create(nuevo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int actualizarUsuario(Usuario actualizar)
    {
        try {
            return usuarioDao.update(actualizar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarUsuario(Usuario eliminar)
    {
        try {
            return usuarioDao.delete(eliminar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Usuario obtenerUsuario(String nombre)
    {
        try {
            QueryBuilder<Usuario, Integer> db= usuarioDao.queryBuilder();
            db.where().eq(Usuario.NOMBRE, nombre);
            return db.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> obtenerUsuariosporId(String id, String nombre)
    {
        try {
            Contratista contra = obtenerContratista(nombre);
            Log.i("DatabaseCrud", "obtenerUsuariosporId contratista: " + contra.getNombre());

            QueryBuilder<Usuario, Integer> db= usuarioDao.queryBuilder();
            db.where().eq(id,contra);
            PreparedQuery<Usuario> preparedQuery = db.prepare();
            List<Usuario> accountList = usuarioDao.query(preparedQuery);
            Log.i("DatabaseCrud", "obtenerUsuariosporId numero: " + accountList.size());
            if(accountList.size()>0){
                return accountList;
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> obtenerUsuarios(String contratista)
    {
        try {
            return usuarioDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Usuario> obtenerUsuarioAutocompletar(String campo, String palabra, String id, String nombre){
        try {
            Contratista contra = obtenerContratista(nombre);

            QueryBuilder<Usuario, Integer> db= usuarioDao.queryBuilder();
            Where<Usuario, Integer> where = db.where();
            where.like(campo, palabra+"%");
            where.and();
            where.eq(id,contra);
            return where.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String composeJSONfromSQLiteUsuario(){
        List<Usuario> listaNoSincronizados = null;
        try {
            listaNoSincronizados = usuarioDao.queryBuilder().where().eq("updateState","No").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(listaNoSincronizados);
    }

    public int dbSyncCountUsuario() {
        int count = 0;
        try {
            return (int) usuarioDao.queryBuilder().where().eq("updateState","No").countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateSyncStatusUsuario(String id, String status) {
        try {
            UpdateBuilder<Usuario,Integer> actualizar = usuarioDao.updateBuilder();
            actualizar.where().eq("usuario_id",id);
            actualizar.updateColumnValue("updateState",status);
            actualizar.update();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="CRUD Contratista">
    public int crearContratista(Contratista nuevo){
        try {
            return contratistaDao.create(nuevo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int actualizarContratista(Contratista actualizar)
    {
        try {
            return contratistaDao.update(actualizar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarContratista(Contratista eliminar)
    {
        try {
            return contratistaDao.delete(eliminar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Contratista obtenerContratista(String nombre)
    {
        try {
            QueryBuilder<Contratista, Integer> db= contratistaDao.queryBuilder();
            db.where().eq(Contratista.NOMBRE, nombre);
            return db.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Contratista> obtenerContratistas()
    {
        try {
            return contratistaDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Contratista> obtenerContratistaAutocompletar(String campo, String palabra){
        try {
            QueryBuilder<Contratista, Integer> db= contratistaDao.queryBuilder();
            Where<Contratista, Integer> where = db.where();
            where.like(campo, palabra+"%");
            return where.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String composeJSONfromSQLiteContratista(){
        List<Contratista> listaNoSincronizados = null;
        try {
            listaNoSincronizados = contratistaDao.queryBuilder().where().eq("updateState","No").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(listaNoSincronizados);
    }

    public int dbSyncCountContratista() {
        int count = 0;
        try {
            return (int) contratistaDao.queryBuilder().where().eq("updateState","No").countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateSyncStatusContratistao(String id, String status) {
        try {
            UpdateBuilder<Contratista,Integer> actualizar = contratistaDao.updateBuilder();
            actualizar.where().eq("usuario_id",id);
            actualizar.updateColumnValue("updateState",status);
            actualizar.update();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="CRUD Maquina">
    public int crearMaquina(Maquina nuevo){
        try {
            Log.i("DatabaseCrud","crearUsuario Bien: "+nuevo.getNombre());
            return maquinaDao.create(nuevo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int actualizarMaquina(Maquina actualizar)
    {
        try {
            return maquinaDao.update(actualizar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarMaquina(Maquina eliminar)
    {
        try {
            return maquinaDao.delete(eliminar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Maquina obtenerMaquina(String nombre)
    {
        try {
            QueryBuilder<Maquina, Integer> db= maquinaDao.queryBuilder();
            db.where().eq(Maquina.NOMBRE, nombre);
            return db.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Maquina> obtenerMaquinasporId(String id, String nombre)
    {
        try {
            Contratista contra = obtenerContratista(nombre);
            Log.i("DatabaseCrud", "obtenerUsuariosporId contratista: " + contra.getNombre());

            QueryBuilder<Maquina, Integer> db= maquinaDao.queryBuilder();
            db.where().eq(id,contra);
            PreparedQuery<Maquina> preparedQuery = db.prepare();
            List<Maquina> accountList = maquinaDao.query(preparedQuery);
            Log.i("DatabaseCrud", "obtenerUMaquinaporId numero: " + accountList.size());
            if(accountList.size()>0){
                return accountList;
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Maquina> obtenerMaquinas(String contratista)
    {
        try {
            return maquinaDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Maquina> obtenerMaquinaAutocompletar(String campo, String palabra, String id, String nombre){
        try {
            Contratista contra = obtenerContratista(nombre);

            QueryBuilder<Maquina, Integer> db= maquinaDao.queryBuilder();
            Where<Maquina, Integer> where = db.where();
            where.like(campo, palabra+"%");
            where.and();
            where.eq(id,contra);
            return where.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String composeJSONfromSQLiteMaquina(){
        List<Maquina> listaNoSincronizados = null;
        try {
            listaNoSincronizados = maquinaDao.queryBuilder().where().eq("updateState","No").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(listaNoSincronizados);
    }

    public int dbSyncCountMaquina() {
        int count = 0;
        try {
            return (int) maquinaDao.queryBuilder().where().eq("updateState","No").countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateSyncStatusMaquina(String id, String status) {
        try {
            UpdateBuilder<Maquina,Integer> actualizar = maquinaDao.updateBuilder();
            actualizar.where().eq("usuario_id",id);
            actualizar.updateColumnValue("updateState",status);
            actualizar.update();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="CRUD Terreno Hacienda">
    public int crearHacienda(Hacienda nuevo){
        try {
            List<Hacienda> existentes = haciendaDao.queryForEq(Hacienda.NOMBRE,nuevo.getNombre());
            Log.i("hacienda existentes", "numero:"+existentes.size());
            if (existentes.size() == 0) {
                Log.i("DatabaseCrud","crearHacienda Bien: "+nuevo.getNombre());
                return haciendaDao.create(nuevo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int actualizarHacienda(Hacienda actualizar)
    {
        try {
            return haciendaDao.update(actualizar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarHacienda(Hacienda eliminar)
    {
        try {
            return haciendaDao.delete(eliminar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Hacienda obtenerHacienda(String nombre)
    {
        try {
            QueryBuilder<Hacienda, Integer> db = haciendaDao.queryBuilder();
            db.where().eq(Hacienda.NOMBRE, nombre);
            PreparedQuery<Hacienda> preparedQuery = db.prepare();
            List<Hacienda> accountList = haciendaDao.query(preparedQuery);
            Log.i("DatabaseCrud", "obtenerHacienda numero: " + accountList.size());
            if (accountList.size() > 0) {
                return accountList.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Hacienda> obtenerHaciendas()
    {
        try {
            return haciendaDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Hacienda> obtenerHaciendasAutocompletar(String campo, String palabra){
        try {
            QueryBuilder<Hacienda, Integer> db= haciendaDao.queryBuilder();
            Where<Hacienda, Integer> where = db.where();
            where.like(campo, palabra+"%");
            return where.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String composeJSONfromSQLiteHacienda(){
        List<Hacienda> listaNoSincronizados = null;
        try {
            listaNoSincronizados = haciendaDao.queryBuilder().where().eq("updateState","No").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(listaNoSincronizados);
    }

    public int dbSyncCountHacienda() {
        int count = 0;
        try {
            return (int) haciendaDao.queryBuilder().where().eq("updateState","No").countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateSyncStatusHacienda(String id, String status) {
        try {
            UpdateBuilder<Hacienda,Integer> actualizar = haciendaDao.updateBuilder();
            actualizar.where().eq("usuario_id",id);
            actualizar.updateColumnValue("updateState",status);
            actualizar.update();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="CRUD Terreno Suerte">
    public int crearSuerte(Suerte nuevo){
        try {
            Log.i("DatabaseCrud","crearSuerte Bien: "+nuevo.getNombre());
            return suerteDao.create(nuevo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int actualizarSuerte(Suerte actualizar)
    {
        try {
            return suerteDao.update(actualizar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int eliminarSuerte(Suerte eliminar)
    {
        try {
            return suerteDao.delete(eliminar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Suerte obtenerSuerte(String nombre)
    {
        try {
            QueryBuilder<Suerte, Integer> db= suerteDao.queryBuilder();
            db.where().eq(Suerte.NOMBRE, nombre);
            return db.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Suerte> obtenerSuertesporId(String id, String nombre)
    {
        try {
            Hacienda haciendasele = obtenerHacienda(nombre);
            Log.i("DatabaseCrud", "obtenerSuertesporId haceinda: " + haciendasele.getNombre());

            QueryBuilder<Suerte, Integer> db= suerteDao.queryBuilder();
            db.where().eq(id,haciendasele);
            PreparedQuery<Suerte> preparedQuery = db.prepare();
            List<Suerte> accountList = suerteDao.query(preparedQuery);
            Log.i("DatabaseCrud", "obtenerSuertesporId numero: " + accountList.size());
            if(accountList.size()>0){
                return accountList;
            }else{
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Suerte> obtenerSuertes()
    {
        try {
            return suerteDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Suerte> obtenerSuertesAutocompletar(String campo, String palabra, String id, String nombre){
        try {
            Hacienda haciendasele = obtenerHacienda(nombre);
            QueryBuilder<Suerte, Integer> db= suerteDao.queryBuilder();
            Where<Suerte, Integer> where = db.where();
            where.like(campo, palabra+"%");
            where.and();
            where.eq(id,haciendasele);
            return where.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String composeJSONfromSQLiteSuerte(){
        List<Suerte> listaNoSincronizados = null;
        try {
            listaNoSincronizados = suerteDao.queryBuilder().where().eq("updateState","No").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().create();
        return gson.toJson(listaNoSincronizados);
    }

    public int dbSyncCountSuerte() {
        int count = 0;
        try {
            return (int) suerteDao.queryBuilder().where().eq("updateState","No").countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void updateSyncStatusSuerte(String id, String status) {
        try {
            UpdateBuilder<Suerte,Integer> actualizar = suerteDao.updateBuilder();
            actualizar.where().eq("usuario_id",id);
            actualizar.updateColumnValue("updateState",status);
            actualizar.update();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //</editor-fold>



    public int crearVariedad(Variedad nuevo){
        try {
            List<Variedad> existentes = variedadDao.queryForEq(Variedad.VALOR,nuevo.getValor());
            Log.i("variedad existentes", "numero:"+existentes.size());
            if (existentes.size() == 0) {
                Log.i("DatabaseCrud","crearVariedad Bien: "+nuevo.getValor());
                return variedadDao.create(nuevo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Variedad obtenerVariedad(String valor)
    {
        try {
            QueryBuilder<Variedad, Integer> db= variedadDao.queryBuilder();
            db.where().eq(Variedad.VALOR, valor);
            return db.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int crearZona(Zona nuevo){
        try {
            List<Zona> existentes = zonaDao.queryForEq(Zona.VALOR,nuevo.getValor());
            Log.i("zona existentes", "numero:"+existentes.size());
            if (existentes.size() == 0) {
                Log.i("DatabaseCrud","crearZona Bien: "+nuevo.getValor());
                return zonaDao.create(nuevo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Zona obtenerZona(String valor)
    {
        try {
            QueryBuilder<Zona, Integer> db= zonaDao.queryBuilder();
            db.where().eq(Zona.VALOR, valor);
            return db.query().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
