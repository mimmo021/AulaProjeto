package dfj.aulaprojeto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dfj.aulaprojeto.model.Receita;

/**
 * Created by mimmo on 05/11/2015.
 */
public class ReceitaAdapter extends ArrayAdapter<Receita> {
    public ReceitaAdapter(Context context, List<Receita> receitas) {
        super(context, 0,receitas);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        Receita receita = getItem(position);


        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_receita, null);

            holder = new ViewHolder();
            holder.imagemRec = (ImageView)convertView.findViewById(R.id.imagemReceita);
            holder.tituloRec = (TextView)convertView.findViewById(R.id.txtTituloReceita);
            holder.rendimentoRec = (TextView)convertView.findViewById(R.id.txtRendimentoReceita);
            holder.tempoRec = (TextView)convertView.findViewById(R.id.txtTempoReceita);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }


        if (receita.imagem==null||holder.imagemRec==null) {
            holder.imagemRec.setImageResource(R.mipmap.ic_launcher);
        }else{
            Picasso.with(getContext()).
                    load(receita.imagem).
                    into(holder.imagemRec);
        }

        holder.tituloRec.setText(receita.titulo);
        holder.tempoRec.setText("Tempo: "+receita.tempo);
        holder.rendimentoRec.setText("Rendimento: "+receita.rendimento);
        //passo 4

        return convertView;


    }
    class ViewHolder{

        ImageView imagemRec;
        TextView tituloRec;
        TextView rendimentoRec;
        TextView tempoRec;

    }
}
