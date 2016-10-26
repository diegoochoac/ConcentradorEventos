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

    public static final List<Eventos> COMIDAS_POPULARES = new ArrayList<Eventos>();
    public static final List<Eventos> BEBIDAS = new ArrayList<>();
    public static final List<Eventos> POSTRES = new ArrayList<>();
    public static final List<Eventos> EVENTOS = new ArrayList<>();



    static {
        /*COMIDAS_POPULARES.add(new Eventos(0, "Camarones Tismados", R.drawable.camarones));
        COMIDAS_POPULARES.add(new Eventos(0, "Rosca Herbárea", R.drawable.rosca));
        COMIDAS_POPULARES.add(new Eventos(0, "Sushi Extremo", R.drawable.sushi));
        COMIDAS_POPULARES.add(new Eventos(0, "Sandwich Deli", R.drawable.sandwich));
        COMIDAS_POPULARES.add(new Eventos(0, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));*/

        EVENTOS.add(new Eventos(0, "Inspección diaria", R.drawable.inspeccion));
        EVENTOS.add(new Eventos(0, "Desplazamiento a sitio de la labor", R.drawable.desplazar));
        EVENTOS.add(new Eventos(0, "Espera antes de ejecutar trabajos", R.drawable.espera));
        EVENTOS.add(new Eventos(0, "Alimentación", R.drawable.alimentacion));
        EVENTOS.add(new Eventos(0, "Mantenimiento", R.drawable.mantenimiento));
        EVENTOS.add(new Eventos(0, "Varado", R.drawable.varado));
        EVENTOS.add(new Eventos(0, "Tanqueo", R.drawable.tanquear));
        EVENTOS.add(new Eventos(0, "Parado lluvia", R.drawable.lluvia));
        EVENTOS.add(new Eventos(0, "Labor en Terreno", R.drawable.labor));

        /*BEBIDAS.add(new Eventos(3, "Taza de Café", R.drawable.cafe));
        BEBIDAS.add(new Eventos(12, "Coctel Tronchatoro", R.drawable.coctel));
        BEBIDAS.add(new Eventos(5, "Jugo Natural", R.drawable.jugo_natural));
        BEBIDAS.add(new Eventos(24, "Coctel Jordano", R.drawable.coctel_jordano));
        BEBIDAS.add(new Eventos(30, "Botella Vino Tinto Darius", R.drawable.vino_tinto));

        POSTRES.add(new Eventos(2, "Postre De Vainilla", R.drawable.postre_vainilla));
        POSTRES.add(new Eventos(3, "Flan Celestial", R.drawable.flan_celestial));
        POSTRES.add(new Eventos(2.5f, "Cupcake Festival", R.drawable.cupcakes_festival));
        POSTRES.add(new Eventos(4, "Pastel De Fresa", R.drawable.pastel_fresa));
        POSTRES.add(new Eventos(5, "Muffin Amoroso", R.drawable.muffin_amoroso));*/
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
