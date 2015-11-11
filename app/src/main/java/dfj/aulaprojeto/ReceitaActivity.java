package dfj.aulaprojeto;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dfj.aulaprojeto.model.Receita;

public class ReceitaActivity extends AppCompatActivity
        implements AoClicarNaRceita {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        //Viewpager controla o swiping entre diferentes paginas
        //seu conteudo vem do pageAdapter
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        //paginasAdapter classe interna

        viewPager.setAdapter(new PaginasAdapter
                (getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //tabLayout.setTabMode(TableLayout.MODE);
        //tabLayout.setTabGravity(TableLayout.GRAVITY_FILL);

        // hookin up com apenas um método
        tabLayout.setupWithViewPager(viewPager);
   }

    @Override
    public void clicouNaReceita(Receita receita) {
        //ListaReceitaFragment onpostExecute está tablet
        if(getResources().getBoolean(R.bool.fone)){
        Intent it = new Intent(this, DetalheReceitaActivity.class);
        it.putExtra("receita", receita);
        startActivity(it);
    } else {
            DetalheReceitaFragment drf = DetalheReceitaFragment.novaInstancia(receita);

            getSupportFragmentManager()
                     .beginTransaction()
                     .replace(R.id.detalhe, drf)
                     .commit();
            }
        }

    private class PaginasAdapter extends FragmentPagerAdapter {
        //fragmentPageAdapter mantem na memória
        //fragmentstateAdapter destroi e recria fragments quando necessário apenas salvando o estado.
        //nesse casso o fragmentPageAdapter resolve por possuir poucas paginas reduzindo a necessidade de memória

        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }


        //retorna o fragment associado a cada posição
        @Override
        public Fragment getItem(int position) {
            if (position ==0){
                return  new ListaReceitaFragment();
            }else{
                return new ListaReceitaFavoritoFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        //rotorna os titulos das páginas associadas.
        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Receitas" : "Favoritos";
        }
    }
}

