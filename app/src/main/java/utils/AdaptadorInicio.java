package utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.concentrador.agrum.concentradoreventos.R;

/**
 * Adaptador para mostrar las comidas más pedidas en la sección "Inicio"
 */
public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder> {

    static ItemClickListener clickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        // Campos respectivos de un item
        public TextView nombre;
        public ImageView imagen;

        public ViewHolder(View v) {
            super(v);
            nombre = (TextView) v.findViewById(R.id.nombre_registro);
            imagen = (ImageView) v.findViewById(R.id.miniatura_registro);

            v.setTag(v);
            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            //Log.i("posision","posicion:");
            if(clickListener != null){
                clickListener.onClick(view, getAdapterPosition()); //OnItemClickListener mItemClickListener;
            }
        }
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }

    public AdaptadorInicio() {
    }

    @Override
    public int getItemCount() {
        return Eventos.MENU_REGISTRO.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lista_inicio, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Eventos item = Eventos.MENU_REGISTRO.get(i);          //TODO: CAmbiar por algo real

        Glide.with(viewHolder.itemView.getContext())
                .load(item.getIdDrawable())
                .centerCrop()
                .into(viewHolder.imagen);
        viewHolder.nombre.setText(item.getNombre());
    }


}