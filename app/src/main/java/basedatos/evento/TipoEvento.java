package basedatos.evento;

import android.content.Context;
import android.util.Log;

import com.concentrador.agrum.concentradoreventos.R;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import basedatos.DatabaseCrud;
import basedatos.DatabaseHelper;

/**
 * Created by diego on 12/09/16.
 */
public class TipoEvento implements Serializable {



    public static final String ID = "id";
    public static final String NOMBRE = "nombre";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(columnName = NOMBRE)
    private String nombre;

    private int idDrawable;

    public TipoEvento() {

    }

    public TipoEvento(final String tipoEventoName) {
        this.nombre = tipoEventoName;
    }

    public TipoEvento(String nombre, int idDrawable){
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }


    //<editor-fold desc="Metodos Get-Set">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    //</editor-fold>


    public static final List<TipoEvento> EVENTOS = new ArrayList<>();

    static {
        EVENTOS.add(new TipoEvento("Inspección diaria", R.drawable.inspeccion));
        EVENTOS.add(new TipoEvento("Desplazamiento a sitio de la labor", R.drawable.desplazar));
        EVENTOS.add(new TipoEvento("Espera antes de ejecutar trabajos", R.drawable.espera));
        EVENTOS.add(new TipoEvento("Alimentación", R.drawable.alimentacion));
        EVENTOS.add(new TipoEvento("Mantenimiento", R.drawable.mantenimiento));
        EVENTOS.add(new TipoEvento("Varado", R.drawable.varado));
        EVENTOS.add(new TipoEvento("Tanqueo", R.drawable.tanquear));
        EVENTOS.add(new TipoEvento("Parado lluvia", R.drawable.lluvia));
        EVENTOS.add(new TipoEvento("Labor en Terreno", R.drawable.labor));
    }
}
