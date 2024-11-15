package aed;

import java.util.ArrayList;

public class Heap<T extends Identificable> {

    public ArrayList<T> elems;
    public int maxID;
    public int longitud;
    public ArrayList<Integer> inds;
    public HeapComparator comparator;

    public Heap(boolean c) {
        elems = new ArrayList<T>();
        maxID = 0;
        inds = new ArrayList<Integer>();
        int i = 0;
        while (i < maxID) {
            inds.add(-1);
        }
        comparator = new HeapComparator(c);
    }

    public void encolar(T nuevo) {
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
        T padre = elems.get(indPadre);
        T hijo = elems.get(n);
        int comparacion = comparator.compare(hijo, padre);
        if (comparacion > 0) {
            elems.set(n, padre);
            elems.set(indPadre, hijo);
            inds.set(id, indPadre);
            heapifyUp(indPadre, id);
        }
    }

    public T desencolar() {
        if (longitud == 1) {
            longitud--;
            inds.set(0, -1);
            return elems.get(0);
        } else {
            T ultimo = elems.get(longitud - 1);
            inds.set(ultimo.getId(), 0);
            T ret = elems.set(0, ultimo);
            heapifyDown(0, ultimo.getId());
            return ret;
        }
    }

    public void heapifyDown(int n, int id) {
        int indIzq = 2 * n + 1;
        int indDer = 2 * n + 2;
        T padre = elems.get(n);
        if (indDer < longitud) { // Hay dos hijos
            T izq = elems.get(indIzq);
            T der = elems.get(indDer);
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
                T izq = elems.get(indIzq);
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

    public void array2heap(T[] array) {
        for (T elem : array) {
            encolar(elem);
        }
    }

    public T getElementById(int id) {
        return elems.get(inds.get(id));
    }

}
