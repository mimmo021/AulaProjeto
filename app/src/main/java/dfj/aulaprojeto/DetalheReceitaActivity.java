package dfj.aulaprojeto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dfj.aulaprojeto.model.Receita;

public class DetalheReceitaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_receita);

        if (savedInstanceState == null) {
            Receita receita = (Receita) getIntent().getSerializableExtra("receita");

            DetalheReceitaFragment drf = DetalheReceitaFragment.novaInstancia(receita);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detalhe, drf)
                    .commit();
        }
    }
}






//onstart fragment
//onstart activity
//onresume activity
//onresume fragment
//onpause frag
//onpause act
//onstop frag
//onstop act