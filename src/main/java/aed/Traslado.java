package aed;
public class Traslado {

    int origen;
    int destino;
    int timestamp;
    int id;
    int gananciaNeta;


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