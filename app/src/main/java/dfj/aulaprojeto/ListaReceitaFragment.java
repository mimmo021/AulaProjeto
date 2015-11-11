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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import dfj.aulaprojeto.model.Categoria;
import dfj.aulaprojeto.model.Dados;
import dfj.aulaprojeto.model.Receita;

/**
 * Created by mimmo on 05/11/2015.
 */
public class ListaReceitaFragment extends ListFragment
        implements SwipeRefreshLayout.OnRefreshListener {
    //The SwipeRefreshLayout should be used whenever the user can refresh the contents of a view via a vertical swipe gesture

    List<Receita> mReceitas;
    ReceitaAdapter mAdapter;
    SwipeRefreshLayout mSwipe;
    ReceitaTask mTask;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mReceitas = new ArrayList<>();
        Bus bus = ((ReceitaApp)getActivity().getApplication()).getBus();
        bus.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_receitas, null);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipe.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mReceitas.isEmpty()) {
            mAdapter = new ReceitaAdapter(getActivity(), mReceitas);

            setListAdapter(mAdapter);

            carregarReceitas();
        }


    }

    private void carregarReceitas() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            mSwipe.setRefreshing(true);
            if (mTask == null) {
                mTask = new ReceitaTask();

                mTask.execute();
                mSwipe.setRefreshing(true);
            }else{
                mSwipe.setRefreshing(false);
            }
        }else{
            mSwipe.setRefreshing(false);
                Toast.makeText(getActivity(), R.string.msg_sem_conexao,
                        Toast.LENGTH_SHORT).show();
            }
        }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Receita receita = mReceitas.get(position);

        exibiritem(receita);

    }

    private void exibiritem(Receita receita){
        if (getActivity() instanceof AoClicarNaRceita) {
            ((AoClicarNaRceita) getActivity()).clicouNaReceita(receita);
        }
    }


    @Override
    public void onRefresh() {

        carregarReceitas();
    }

/*The three types used by an asynchronous task are the following:

            Params, the type of the parameters sent to the task upon execution.
            Progress, the type of the progress units published during the background computation.
            Result, the type of the result of the background computation.
*/

    class ReceitaTask extends AsyncTask<Void,Void,Dados> {

        @Override
        protected Dados doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("https://dl.dropboxusercontent.com/u/61459175/JSON/projeto.json")
                    .build();

            Response response = null;
            try {
                Thread.sleep(1000);

                response = client.newCall(request).execute();
                String s = response.body().string();

                Gson gson = new Gson();

                Dados dados = gson.fromJson(s, Dados.class);
                return dados;
            } catch (Throwable e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Dados dados) {
            super.onPostExecute(dados);
            if (dados != null && dados.categorias != null) {
                mReceitas.clear();

                for (Categoria categoria : dados.categorias) {
                    mReceitas.addAll(categoria.receitas);

                }
                mAdapter.notifyDataSetChanged();

                if (getResources().getBoolean(R.bool.tablet)){
                    exibiritem(mReceitas.get(0));
                }

            }else{

                Toast.makeText(getActivity(), R.string.msg_erro_geral,
                        Toast.LENGTH_SHORT).show();

            }
            mSwipe.setRefreshing(false);
        }
    }
}
//No método que está baixando o JSON (doInBackground)
// temos um try/catch, para que caso ocorra algum problema,
// a exceção seja capturada. Nesse caso o método retornará null.
// Assim, se no método onPostExecute, o parâmetro result vier
// nulo é porque houve algum problema. E nesse caso, mostrar
// uma mensagem pro usuário.