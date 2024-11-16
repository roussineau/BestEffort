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
            inds.set(nuevo.getId(), longitud-1);
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
            T ultimo = elems.remove(longitud - 1);
            T primero = elems.get(0);
            elems.set(0, ultimo);
            inds.set(ultimo.getId(), 0);
            longitud--;
            heapifyDown(0, ultimo.getId());
            return primero;
        }
    }

    public T sacarElem (int pos){
        if (pos == 0) {
            return desencolar();
        } else {
            if (pos == longitud-1){
                T borrado = elems.remove(pos);
                longitud--;
                return borrado;
            } else {
                T elemAcambiar = elems.get(pos);
                T ultimo = elems.remove(longitud - 1);
                elems.set(pos, ultimo);
                inds.set(ultimo.getId(), pos);
                longitud--;
                heapifyDown(pos, ultimo.getId());
                return elemAcambiar;
            }
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
                    inds.set(padre.getId(), indIzq);
                    inds.set(izq.getId(), n);
                    heapifyDown(indIzq, izq.getId());
                }
            } else {
                if (comparacionHijos < 0) {
                    int comparacion = comparator.compare(der, padre);
                    if (comparacion > 0) {
                        elems.set(indDer, padre);
                        elems.set(n, der);
                        inds.set(padre.getId(), indDer);
                        inds.set(der.getId(), n);
                        heapifyDown(indDer, der.getId());
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
                    inds.set(padre.getId(), indIzq);
                    inds.set(izq.getId(), n);
                    heapifyDown(indIzq, izq.getId());
                }
            }
        }
    }

    public void actualizarPrioridad(T elem, T newElem) {
        elems.set(inds.get(elem.getId()), newElem);
        heapifyUp(inds.get(elem.getId()), elem.getId());
        heapifyDown(inds.get(elem.getId()), elem.getId());
    }

    public void array2heap(T[] array) {
        for (T elem : array) {
            encolar(elem);
        }
    }

    public void arrayList2heap(ArrayList<T> arrayList) {
        this.elems = new ArrayList<>(arrayList);
        this.longitud = elems.size();
        if (longitud != 0){
            for(int i = (longitud - 2) / 2; i >= 0; i--){
                heapify(this.elems, this.longitud, i);
            }
        }
    }

    public void heapify (ArrayList<T> elems, int longitud, int i){
        int nodoEnCuestion = i;
        int posizq = 2*i+1;
        int posder = 2*i+2;
        if(posizq < longitud && (comparator.compare(elems.get(posizq), elems.get(nodoEnCuestion))>0)){
            nodoEnCuestion = posizq;
        }
        if(posder< longitud && (comparator.compare(elems.get(posder), elems.get(nodoEnCuestion))>0)){
            nodoEnCuestion = posder;
        }
        if(nodoEnCuestion != i){
            T nuevoValorT = elems.get(nodoEnCuestion);
            T nodoACambiar = elems.get(i);
            elems.set(nodoEnCuestion, nodoACambiar);
            elems.set(i, nuevoValorT);
            heapify(elems, longitud, nodoEnCuestion);
        }
    }

    public T getElementById(int id) {
        return elems.get(inds.get(id));
    }

    public String toString() {
        String ret = "[";
        int i = 0;
        while (i < longitud) {
            ret += elems.get(i).getId();
            if (i + 1 != longitud) {
                ret += ", ";
            }
            i++;
        }
        ret += "]";
        return ret;
    }

}
