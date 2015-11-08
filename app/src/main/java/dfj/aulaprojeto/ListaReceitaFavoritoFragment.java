package dfj.aulaprojeto;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import dfj.aulaprojeto.data.ReceitaDAO;
import dfj.aulaprojeto.model.Categoria;
import dfj.aulaprojeto.model.Dados;
import dfj.aulaprojeto.model.Receita;

/**
 * Created by mimmo on 05/11/2015.
 */
public class ListaReceitaFavoritoFragment extends ListFragment {

    List<Receita> mReceitas;
    ReceitaAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bus bus = ((ReceitaApp) getActivity().getApplication()).getBus();
        bus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Bus bus = ((ReceitaApp) getActivity().getApplication()).getBus();
        bus.unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mReceitas = new ArrayList<>();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mReceitas.isEmpty()) {
            mAdapter = new ReceitaAdapter(getActivity(), mReceitas);

            setListAdapter(mAdapter);
}
 }

    @Override
    public void onResume() {
        super.onResume();
        carregarReceitas();
    }

    private void carregarReceitas() {
        ReceitaDAO dao = new ReceitaDAO(getActivity());
        mReceitas.clear();
        mReceitas.addAll(dao.listar());
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Receita receita = mReceitas.get(position);

        if (getActivity() instanceof AoClicarNaRceita) {
            ((AoClicarNaRceita) getActivity()).clicouNaReceita(receita);
        }

    }
    @Subscribe
    public  void listaDeFavoritosAtualizada(Receita receita){

        carregarReceitas();
    }
}