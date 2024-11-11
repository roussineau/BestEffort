package aed;
public class Traslado {

    private int origen;
    private int destino;
    private int timestamp;
    private int id;
    private int gananciaNeta;


    public Traslado (int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }


    public int id (){
        return id;
    }

    public int origen (){
        return origen;
    }

    public int destino (){
        return destino;
    }

    public int gananciaNeta (){
        return gananciaNeta;
    }

    public int timestamp (){
        return timestamp;
    }

}