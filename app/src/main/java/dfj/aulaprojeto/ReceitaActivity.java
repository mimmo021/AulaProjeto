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

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        //paginasAdapter classe interna
        viewPager.setAdapter(new PaginasAdapter
                (getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        //tabLayout.setTabMode(TableLayout.MODE);
        //tabLayout.setTabGravity(TableLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
   }

    @Override
    public void clicouNaReceita(Receita receita) {
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


        public PaginasAdapter(FragmentManager fm) {
            super(fm);
        }

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

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Receitas" : "Favoritos";
        }
    }
}

