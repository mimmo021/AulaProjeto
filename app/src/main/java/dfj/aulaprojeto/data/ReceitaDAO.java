package dfj.aulaprojeto.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dfj.aulaprojeto.model.Receita;


/**
 * Created by mimmo on 30/10/2015.
 */
public class ReceitaDAO {

    ReceitaDbhelper mHelper;

    public ReceitaDAO(Context context){

        mHelper = new ReceitaDbhelper(context);
    }
    public void inserir(Receita receita){

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReceitaDbhelper.COL_TITULO, receita.titulo);
        values.put(ReceitaDbhelper.COL_TEMPO, receita.tempo);
        values.put(ReceitaDbhelper.COL_RENDIMENTO, receita.rendimento);
        values.put(ReceitaDbhelper.COL_INGREDIENTES, receita.ingredientes);
        values.put(ReceitaDbhelper.COL_IMAGEM, receita.imagem);
        values.put(ReceitaDbhelper.COL_IMAGEMBIG, receita.imagembig);

        Long id = db.insert(ReceitaDbhelper.TABELA_RECEITA, null, values);
        if(id == -1){
            throw  new RuntimeException("Erro ao inserir Registro");
        }
        db.close();
    }

    public void excluir(Receita receita){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(ReceitaDbhelper.TABELA_RECEITA,
                ReceitaDbhelper.COL_TITULO + " = ? AND "+
                ReceitaDbhelper.COL_RENDIMENTO +" = ?",
                new String[]{receita.titulo, receita.rendimento});
    }


    public List<Receita> listar(){
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ReceitaDbhelper.TABELA_RECEITA +
                " ORDER BY " + ReceitaDbhelper.COL_TITULO, null);

        int idx_titulo = cursor.getColumnIndex(ReceitaDbhelper.COL_TITULO);
        int idx_tempo = cursor.getColumnIndex(ReceitaDbhelper.COL_TEMPO);
        int idx_rendimento = cursor.getColumnIndex(ReceitaDbhelper.COL_RENDIMENTO);
        int idx_ingredientes = cursor.getColumnIndex(ReceitaDbhelper.COL_INGREDIENTES);
        int idx_imagem = cursor.getColumnIndex(ReceitaDbhelper.COL_IMAGEM);
        int idx_imagembig = cursor.getColumnIndex(ReceitaDbhelper.COL_IMAGEMBIG);

        List<Receita> receitas = new ArrayList<>();

        while (cursor.moveToNext()){
            String titulo = cursor.getString(idx_titulo);
            String tempo = cursor.getString(idx_tempo);
            String rendimento = cursor.getString(idx_rendimento);
            String ingredientes = cursor.getString(idx_ingredientes);
            String imagem = cursor.getString(idx_imagem);
            String imagembig = cursor.getString(idx_imagembig);

            Receita receita = new Receita(titulo,tempo,rendimento, ingredientes,imagem,imagembig);
            receitas.add(receita);
        }

        cursor.close();
        db.close();
        return receitas;
    }

    public boolean isfavorito(Receita receita){

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "select _id from " + ReceitaDbhelper.TABELA_RECEITA +
                " WHERE " + ReceitaDbhelper.COL_TITULO + " = ? AND "+
                        ReceitaDbhelper.COL_RENDIMENTO +" = ?",
                new String[]{receita.titulo, receita.rendimento});

        boolean existe = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return  existe;
    }
}
//