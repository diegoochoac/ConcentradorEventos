package utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.concentrador.agrum.concentradoreventos.R;

import java.util.List;

import basedatos.evento.TipoEvento;

/**
 * Adaptador para comidas usadas en la sección "Categorías"
 */
public class AdaptadorCategorias extends RecyclerView.Adapter<AdaptadorCategorias.ViewHolder> {


    private final List<Eventos> items;

    static ItemClickListener clickListener;



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Campos respectivos de un item
        public TextView nombre;
        public TextView precio;
        public ImageView imagen;


        public ViewHolder(View v) {
            super(v);

            nombre = (TextView) v.findViewById(R.id.nombre_comida);
            precio = (TextView) v.findViewById(R.id.precio_comida);
            imagen = (ImageView) v.findViewById(R.id.miniatura_comida);
            v.setTag(v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("posision","posicion:");
            if(clickListener != null){
                clickListener.onClick(view, getAdapterPosition()); //OnItemClickListener mItemClickListener;
            }
        }
    }


    public void setClickListener(ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }



    public AdaptadorCategorias(List<Eventos> items) {
        this.items = items;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_categorias, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Eventos item = items.get(i);

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
        viewHolder.precio.setText("" + item.getPrecio());

    }


}