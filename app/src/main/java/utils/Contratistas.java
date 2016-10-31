package utils;

import com.concentrador.agrum.concentradoreventos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos estático para alimentar la aplicación
 */
public class Contratistas {
    private float precio;
    private String nombre;
    private int idDrawable;

    public Contratistas(float precio, String nombre, int idDrawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public static final List<Contratistas> CONTRATISTAS = new ArrayList<>();

    static {
        CONTRATISTAS.add(new Contratistas(0, "Maquina 1", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 2", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 3", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 4", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 5", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 6", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 7", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 8", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 9", R.drawable.labor));
        CONTRATISTAS.add(new Contratistas(0, "Maquina 10", R.drawable.labor));
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
