package aed;

import java.util.ArrayList;

public class Heap {

    public ArrayList<Identificable> elems;
    public int maxID;
    public int longitud;
    public ArrayList<Integer> inds;
    public HeapComparator<Identificable> comparator;

    public Heap(HeapComparator<Identificable> c) {
        elems = new ArrayList<Identificable>();
        maxID = 0;
        inds = new ArrayList<Integer>();
        comparator = c;
    }

    public void encolar(Identificable nuevo) {
        if (maxID < nuevo.getId()) {
            maxID = nuevo.getId();
        }
        elems.add(nuevo);
        inds.add(longitud); // empieza en 0;
        longitud++;
        if (longitud > 1) {
            heapifyUp(longitud-1);
        }
    }

    public void heapifyUp(int n) {
        int indPadre = n / 2 - 1;
        Identificable padre = elems.get(indPadre);
        Identificable hijo = elems.get(n);
        int comparacion = comparator.compare(hijo, padre);
        if (comparacion > 0) {
            elems.set(n, padre);
            elems.set(indPadre, hijo);
            heapifyUp(indPadre);

        }
    }






   
}
