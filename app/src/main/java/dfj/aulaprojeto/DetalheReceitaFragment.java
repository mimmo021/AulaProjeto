package dfj.aulaprojeto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import dfj.aulaprojeto.data.ReceitaDAO;
import dfj.aulaprojeto.model.Receita;

/**
 * Created by mimmo on 05/11/2015.
 */
public class DetalheReceitaFragment extends Fragment {

    Receita mReceita;
    MenuItem mMenuItemFavoritos;
    ReceitaDAO mDao;

    public static DetalheReceitaFragment novaInstancia(Receita r){
        Bundle args= new Bundle();
        args.putSerializable("receita", r);

        DetalheReceitaFragment drf= new DetalheReceitaFragment();
        drf.setArguments(args);
        return drf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mReceita = (Receita)getArguments().getSerializable("receita");

        View layout = inflater.inflate(R.layout.fragment_detalhe_receita, null);

        ImageView imagemRec = (ImageView)layout.findViewById(R.id.imagemReceita);
        TextView txtTitulo = (TextView)layout.findViewById(R.id.txtTituloReceita);
        TextView txtTempo = (TextView)layout.findViewById(R.id.txtTempoReceita);
        TextView txtRendimento = (TextView)layout.findViewById(R.id.txtRendimentoReceita);
        TextView txtIngredientes = (TextView)layout.findViewById(R.id.txtIngredientesReceita);



        txtTitulo.setText(mReceita.titulo);
        txtTempo.setText(mReceita.tempo);
        txtRendimento.setText(mReceita.rendimento);
        txtIngredientes.setText(mReceita.ingredientes);

        if (mReceita.imagem==null||imagemRec==null) {
            imagemRec.setImageResource(R.mipmap.ic_launcher);
        }else{
            Picasso.with(getContext()).
                    load(mReceita.imagembig).
                    into(imagemRec);
        }

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDao = new ReceitaDAO(getActivity());
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_detalhe, menu);

        mMenuItemFavoritos = menu.findItem(R.id.acao_favorito);
        atualizarIemMenu(mDao.isfavorito(mReceita));
    }

    private void atualizarIemMenu(boolean favorito){
        mMenuItemFavoritos.setIcon(favorito ?
        android.R.drawable.ic_menu_delete :
        android.R.drawable.ic_menu_save);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if (item.getItemId()==R.id.acao_favorito){


            boolean favorito = mDao.isfavorito(mReceita);

            if(favorito){
                mDao.excluir(mReceita);

            }else{
                mDao.inserir(mReceita);
            }
            atualizarIemMenu(!favorito);
            Bus bus = ((ReceitaApp)getActivity().getApplication()).getBus();
            bus.post(mReceita);
        }
        return super.onOptionsItemSelected(item);
    }
}
