package aed;

import java.util.Comparator;

public class HeapComparator implements Comparator<Traslado> {
    public Boolean atributo;

    public HeapComparator(Boolean atributo) {
        this.atributo = atributo;
    }

    @Override
    public int compare(Traslado o1, Traslado o2) {
        if (atributo) {
            return -Integer.compare(o1.timestamp, o2.timestamp);
        } else {
            if (o1.gananciaNeta == o2.gananciaNeta) {
                return Integer.compare(o1.id(), o2.id());
            } else {
                return Integer.compare(o1.gananciaNeta, o2.gananciaNeta);
            }
        }
    }
}
