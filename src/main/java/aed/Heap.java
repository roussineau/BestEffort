package aed;

import java.util.ArrayList;

public class Heap {

    public ArrayList<Identificable> elems;
    public int maxID;
    public int longitud;
    public ArrayList<Integer> inds;
    public HeapComparator comparator;

    public Heap(HeapComparator c) {
        elems = new ArrayList<Identificable>();
        maxID = 0;
        inds = new ArrayList<Integer>();
        int i = 0;
        while (i < maxID) {
            inds.add(-1);
        }
        comparator = c;
    }

    public void encolar(Identificable nuevo) {
        if (maxID < nuevo.getId()) {
            int i = 0;
            while (i < maxID - nuevo.getId()) {
                inds.add(-1);
            }
            maxID = nuevo.getId();
        }
        elems.add(nuevo);
        longitud++;
        if (longitud > 1) {
            heapifyUp(longitud - 1, nuevo.getId());
        } else {
            inds.set(nuevo.getId(), 0);
        }
    }

    public void heapifyUp(int n, int id) {
        int indPadre = n / 2 - 1;
        Identificable padre = elems.get(indPadre);
        Identificable hijo = elems.get(n);
        int comparacion = comparator.compare(hijo, padre);
        if (comparacion > 0) {
            elems.set(n, padre);
            elems.set(indPadre, hijo);
            inds.set(id, indPadre);
            heapifyUp(indPadre, id);
        }
    }

    public Identificable desencolar() {
        if (longitud == 1) {
            longitud--;
            inds.set(0, -1);
            return elems.get(0);
        } else {
            Identificable ultimo = elems.get(longitud - 1);
            inds.set(ultimo.getId(), 0);
            Identificable ret = elems.set(0, ultimo);
            heapifyDown(0, ultimo.getId());
            return ret;
        }
    }

    public void heapifyDown(int n, int id) {
        int indIzq = 2 * n + 1;
        int indDer = 2 * n + 2;
        Identificable padre = elems.get(n);
        if (indDer < longitud) { // Hay dos hijos
            Identificable izq = elems.get(indIzq);
            Identificable der = elems.get(indDer);
            int comparacionHijos = comparator.compare(izq, der);
            if (comparacionHijos > 0) {
                int comparacion = comparator.compare(izq, padre);
                if (comparacion > 0) {
                    elems.set(indIzq, padre);
                    elems.set(n, izq);
                    inds.set(izq.getId(), n);
                    inds.set(id, indIzq);
                    heapifyDown(indIzq, izq.getId());
                }
            } else {
                if (comparacionHijos < 0) {
                    int comparacion = comparator.compare(der, padre);
                    if (comparacion > 0) {
                        elems.set(indDer, padre);
                        elems.set(n, der);
                        inds.set(der.getId(), id);
                        inds.set(id, indDer);
                        heapifyDown(comparacion, der.getId());
                    }
                }
            }
        } else {
            if (indIzq < longitud) {
                Identificable izq = elems.get(indIzq);
                int comparacion = comparator.compare(izq, padre);
                if (comparacion > 0) {
                    elems.set(indIzq, padre);
                    elems.set(n, izq);
                    inds.set(izq.getId(), id);
                    inds.set(id, indIzq);
                    heapifyDown(indIzq, izq.getId());
                }
            }
        }
    }

    public Identificable getElementById(int id) {
        return elems.get(inds.get(id));
    }

}
