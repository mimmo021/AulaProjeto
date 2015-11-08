package dfj.aulaprojeto.model;

import java.io.Serializable;

/**
 * Created by mimmo on 05/11/2015.
 */
public class Receita implements Serializable {
    public String titulo;
    public String tempo;
    public String rendimento;
    public String ingredientes;
    public String imagem;
    public String imagembig;

    public Receita(String titulo,
                   String tempo,
                   String rendimento,
                   String ingredientes,
                   String imagem,
                   String imagembig) {

        this.titulo = titulo;
        this.tempo = tempo;
        this.rendimento = rendimento;
        this.ingredientes = ingredientes;
        this.imagem = imagem;
        this.imagembig = imagembig;
    }

    //@Override
    //public String toString() {
//
  //      return titulo+" - "+ tempo +" - "+ rendimento;
    //}
}

