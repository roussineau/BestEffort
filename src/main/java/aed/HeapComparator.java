package aed;

import java.util.Comparator;

public class HeapComparator implements Comparator<Object> {
    public Boolean atributo;

    public HeapComparator(Boolean atributo) {
        this.atributo = atributo;
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (atributo) {
            if (o1 instanceof Traslado && o2 instanceof Traslado) {   //--> para hacer una clase Heap genérica que funcione tanto para maxHeap como min Heap devolvemos el valor contrario al maxHeap comparator
                Traslado t1 = (Traslado) o1;
                Traslado t2 = (Traslado) o2;
                return -Integer.compare(t1.timestamp(), t2.timestamp());
            }
        } else {
            if (o1 instanceof Traslado && o2 instanceof Traslado) {
                Traslado t1 = (Traslado) o1;
                Traslado t2 = (Traslado) o2;
                if (t1.gananciaNeta() == t2.gananciaNeta()) {
                    return Integer.compare(t1.id(), t2.id());
                } else {
                    return Integer.compare(t1.gananciaNeta(), t2.gananciaNeta());
                }
            } else{
                if (o1 instanceof Ciudad && o2 instanceof Ciudad) {
                    Ciudad c1 = (Ciudad) o1;
                    Ciudad c2 = (Ciudad) o2;
                    return Integer.compare(c1.superavit(), c2.superavit());
                }
            }
        }
        return 0;
    }
}
