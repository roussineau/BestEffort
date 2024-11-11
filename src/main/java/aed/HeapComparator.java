package aed;

import java.util.Comparator;

public class HeapComparator implements Comparator<Traslado> {
    private String atributo;

    public HeapComparator(String atributo) {
        this.atributo = atributo;
    }

    @Override
    public int compare(Traslado o1, Traslado o2) {
        if (atributo == "timeStamp") {
            return -Integer.compare(o1.timestamp(), o2.timestamp());
        } 
        if (atributo == "ganancia"){
            if (o1.gananciaNeta == o2.gananciaNeta){
                return Integer.compare(o1.id(), o2.id());
            } else {
                return Integer.compare(o1.ganancia(), o2.ganancia());
            }
        }
    
}
