package dfj.aulaprojeto.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mimmo on 30/10/2015.
 */

public class ReceitaDbhelper extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "dbReceita";
    public static final int VERSAO_BANCO = 1;
    public static final String  TABELA_RECEITA = "receita";
    public static final String COL_ID = "_id";
    public static final String COL_TITULO = "titulo";
    public static final String COL_TEMPO = "tempo";
    public static final String COL_RENDIMENTO = "rendimento";
    public static final String COL_INGREDIENTES = "ingredientes";
    public static final String COL_IMAGEM = "imagem";
    public static final String COL_IMAGEMBIG = "imagembig" ;


    public ReceitaDbhelper(Context context) {

        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABELA_RECEITA +"("+
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TITULO +" TEXT NOT NULL, " +
                COL_TEMPO +" TEXT NOT NULL, " +
                COL_RENDIMENTO +" TEXT NOT NULL, " +
                COL_INGREDIENTES +" TEXT NOT NULL, " +
                COL_IMAGEMBIG +" TEXT NOT NULL, " +
                COL_IMAGEM +" TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
//classe que herda
// de android.database.sqlite.SQLiteOpenHelper.
// Ela será responsável por criar o banco quando
// a aplicação for instalada (método onCreate) e
// atualizá-lo para novas versões da aplicação
// (método onUpgrade).