package dfj.aulaprojeto.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mimmo on 18/10/2015.
 */
public class Dados {
    @SerializedName("metadados")
    public List<Categoria> categorias;
}
