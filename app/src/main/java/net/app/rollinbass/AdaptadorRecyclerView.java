package net.app.rollinbass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//Se crea la interfaz OnItemClickListener para ayudarnos a comunicar el adaptador con la actividad
public class AdaptadorRecyclerView extends RecyclerView.Adapter<AdaptadorRecyclerView.ViewHolder> {

    private List<Dj> djs; //lista donde añadiremos los distintos djs
    private Context context; //para obtener el contexto donde nos encontramos
    private OnItemClickListener listener; //listener necesario para la info

    //implementar metodos que faltan en la clase
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_dj, parent,
                false);//inflamos la vista tarjeta_dj
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    //clase viewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        //Referenciamos los elementos de la interfaz xml
        @BindView(R.id.ivTarjeta)
        AppCompatImageView ivTarjeta;
        @BindView(R.id.tvTarjeta)
        AppCompatTextView tvTarjeta;
        @BindView(R.id.tarjeta)
        RelativeLayout tarjeta;

        //constructor viewholder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /*setListener nos permite añadir un Listener al ViewHolder y asi poder enviar los objetos
        seleccionados a la actividad que deseemos.*/
        void setListener(Dj dj, OnItemClickListener listener) {
            tarjeta.setOnClickListener(new View.OnClickListener() {//cuando haya click en el relativelayout
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dj);
                }
            });
            tarjeta.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(dj);
                    return true;
                }
            });
        }
    }

    //insertamos un constructor que solo reciba la lista de djs y el listener
    public AdaptadorRecyclerView(List<Dj> djs, OnItemClickListener listener) {
        this.djs = djs;
        this.listener = listener;
    }


    //este metodo vamos dando valor a cada elemento de la tarjeta
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Dj dj = djs.get(position); //objeto de tipo dj segun posicion
        holder.setListener(dj, listener); //click en el holder con el listener
        holder.tvTarjeta.setText(dj.getAlias());//establecemos que el textView sea el alias del dj
        if (dj.getUrlFoto() != null) { //carga de imagen con Glide
            RequestOptions options = new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.centerCrop();
            options.placeholder(R.drawable.png_logo_rollin_pegatina);
            Glide.with(context)
                    .load(dj.getUrlFoto())
                    .apply(options)
                    .into(holder.ivTarjeta);
        } else {//en caso de que la url no sea valida ponemos imagen por defecto
            holder.ivTarjeta.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_logoconfondoblanco));
        }
    }

    //9 devolvemos el tamaño del listado
    @Override
    public int getItemCount() {
        return this.djs.size();
    }

   //metodo para agregar djs a la recyclerView.
    public void add(Dj dj) {
        if (!djs.contains(dj)) {
            djs.add(dj);
            notifyDataSetChanged();
        }
    }
}
