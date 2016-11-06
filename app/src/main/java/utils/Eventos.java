package utils;

import com.concentrador.agrum.concentradoreventos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Eventos {
    private float precio;
    private String nombre;
    private int idDrawable;

    public Eventos(float precio, String nombre, int idDrawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public static final List<Eventos> MENU_REGISTRO = new ArrayList<Eventos>();

    public static final List<Eventos> EVENTOS = new ArrayList<>();



    static {
        MENU_REGISTRO.add(new Eventos(0, "REGISTRO LABOR", R.drawable.labor));
        MENU_REGISTRO.add(new Eventos(0, "REGISTRO EVENTO", R.drawable.inspeccion));

        EVENTOS.add(new Eventos(0, "Inspección diaria", R.drawable.inspeccion));
        EVENTOS.add(new Eventos(0, "Desplazamiento a sitio de la labor", R.drawable.desplazar));
        EVENTOS.add(new Eventos(0, "Espera antes de ejecutar trabajos", R.drawable.espera));
        EVENTOS.add(new Eventos(0, "Alimentación", R.drawable.alimentacion));
        EVENTOS.add(new Eventos(0, "Mantenimiento", R.drawable.mantenimiento));
        EVENTOS.add(new Eventos(0, "Varado", R.drawable.varado));
        EVENTOS.add(new Eventos(0, "Tanqueo", R.drawable.tanquear));
        EVENTOS.add(new Eventos(0, "Parado lluvia", R.drawable.lluvia));
    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }
}
