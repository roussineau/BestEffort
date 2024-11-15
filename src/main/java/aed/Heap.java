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
            i++;
        }
        comparator = new HeapComparator(c);
    }

    public void encolar(T nuevo) {
        if (maxID < nuevo.getId()) {
            int i = 0;
            while (i < nuevo.getId() - maxID) {
                inds.add(-1);
                i++;
            }
            maxID = nuevo.getId();
        }
        elems.add(nuevo);
        longitud++;
        if (longitud > 1) {
            heapifyUp(longitud - 1, nuevo.getId());
        } else {
            inds.add(nuevo.getId());
        }
    }

    public void heapifyUp(int n, int id) {
        int indPadre = (n - 1) / 2;
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
            return elems.remove(0);
        } else {
            T ultimo = elems.get(longitud - 1);
            T primero = elems.remove(0);
            inds.set(ultimo.getId(), 0);
            elems.set(0, ultimo);
            heapifyDown(0, ultimo.getId());
            longitud--;
            return primero;
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

    public void actualizarPrioridad(T elem, T newElem) {
        elems.set(inds.get(elem.getId()), newElem);
        heapifyUp(inds.get(newElem.getId()), newElem.getId());
        heapifyDown(inds.get(newElem.getId()), newElem.getId());
    }

    public void array2heap(T[] array) {
        for (T elem : array) {
            encolar(elem);
        }
    }

    public void arrayList2heap(ArrayList<T> arrayList) {
        this.elems = new ArrayList<T>(arrayList.size());
        int j = (this.elems.size() - 2) / 2;
        int i = hijo(j);
        while (j >= 0){
            if(comparator.compare(elems.get(i), elems.get(i))> 0){
                heapify(j, i);
            }
            j --;
            i = hijo(j);
        } 
        
    }

    public int hijo(int j){
        int i;
        if (2*j + 2 < elems.size() && j >= 0){
            if (comparator.compare(elems.get(2*j + 1), elems.get(2*j + 2))> 0){
                i = 2*j + 1;
            } else {
                i = 2*j + 2;
            }
        } else {
            i = 2*j + 1;
        }
        return i;
    }

    public void heapify (int j, int i){
        while (i < elems.size() &&  (comparator.compare(elems.get(i), elems.get(j))> 0)){
            T padre = elems.get(j); 
            elems.set(j, elems.get(i));
            elems.set(i, padre);
            j = i;
            i = hijo(j);
        }
        }


    public T getElementById(int id) {
        return elems.get(inds.get(id));
    }

}
