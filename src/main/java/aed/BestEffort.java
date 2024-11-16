package aed;

import java.util.ArrayList;

public class BestEffort {
    private Promedio promedio;
    private ArrayList<Ciudad> ciudades;
    private Heap<Traslado> heapTimestamp;
    private Heap<Traslado> heapGanancia;
    private Heap<Ciudad> heapSuperavit;
    private ArrayList<Traslado> despachados;
    private Integer maxGanancia;
    private Integer maxPerdida;
    private ArrayList<Integer> arregloGanancias;
    private ArrayList<Integer> arregloPerdidas;

    public class Promedio {
        private int totalGanancia;
        private int despachados;
        private int promedio;

        public Promedio() {
            totalGanancia = 0;
            despachados = 0;
            promedio = 0;
        }
    }

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        promedio = new Promedio();
        ciudades = new ArrayList<>();
        for (int i = 0; i < cantCiudades; i++) {
            ciudades.add(new Ciudad(i));
        }
        heapGanancia = new Heap<>(false);
        heapTimestamp = new Heap<>(true);
        heapSuperavit = new Heap<>(false);
        ArrayList<Traslado> trasList = new ArrayList<Traslado>();
        for(int i = 0; i<traslados.length; i++) {
            trasList.add(traslados[i]);
        }
        heapTimestamp.arrayList2heap(trasList);
        heapGanancia.arrayList2heap(trasList);
        heapSuperavit.arrayList2heap(ciudades);
        despachados = new ArrayList<>();
        arregloGanancias = new ArrayList<>();
        arregloPerdidas = new ArrayList<>();
        maxGanancia = 0;
        maxPerdida = 0;
    }

    public void registrarTraslados(Traslado[] traslados) {
        for (Traslado traslado : traslados) {
            heapTimestamp.encolar(traslado);
            heapGanancia.encolar(traslado);
        }
    }

    private void actualizarPromedio(Traslado t) {
        promedio.totalGanancia += t.gananciaNeta;
        promedio.despachados++;
        promedio.promedio = promedio.totalGanancia / promedio.despachados;
    }

    private void actualizarEstadisticas(Ciudad ciudad, boolean esGanancia) {
        int valor;
        ArrayList<Integer> lista;
        Integer max;
    
        if (esGanancia) {
            valor = ciudad.ganancia;
            lista = arregloGanancias;
            max = maxGanancia;
        } else {
            valor = ciudad.perdida;
            lista = arregloPerdidas;
            max = maxPerdida;
        }
    
        if (valor > max) {
            lista.clear();
            lista.add(ciudad.id);
            if (esGanancia) {
                maxGanancia = valor;
            } else {
                maxPerdida = valor;
            }
        } else if (valor == max) {
            // Reemplazar contains() con un simple ciclo
            boolean yaEsta = false;
            for (int id : lista) {
                if (id == ciudad.id) {
                    yaEsta = true;
                    break;
                }
            }
            if (!yaEsta) {
                lista.add(ciudad.id);
            }
        }
    }
    public int[] despacharMasRedituables(int n) {
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        int cantDisponibles = Math.min(n, heapGanancia.longitud);
        for (int i = 0; i < cantDisponibles; i++) {
            Traslado traslado = heapGanancia.desencolar();
            heapTimestamp.sacarElem(heapTimestamp.inds.get(traslado.getId()));
            idsDespachados.add(traslado.id);
            despachados.add(traslado);
            actualizarPromedio(traslado);
    
            // Actualizar ciudades
            int monto = traslado.gananciaNeta;
            Ciudad origen = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.origen));
            Ciudad destino = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.destino));
            origen.ganancia += monto;
            origen.superavit += monto;
            destino.perdida += monto;
            destino.superavit -= monto;
    
            heapSuperavit.actualizarPrioridad(origen, origen);
            heapSuperavit.actualizarPrioridad(destino, destino);
    
            // Actualizar estadísticas
            actualizarEstadisticas(origen, true);
            actualizarEstadisticas(destino, false);
        }
    
        // Convertir idsDespachados a un arreglo de enteros
        int[] idsSeq = new int[idsDespachados.size()];
        for (int i = 0; i < idsDespachados.size(); i++) {
            idsSeq[i] = idsDespachados.get(i);
        }
    
        return idsSeq;
    }

    public int[] despacharMasAntiguos(int n) {
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        int cantDisponibles = Math.min(n, heapTimestamp.longitud);
        for (int i = 0; i < cantDisponibles; i++) {
            Traslado traslado = heapTimestamp.desencolar();
            heapGanancia.sacarElem(heapGanancia.inds.get(traslado.getId()));
            idsDespachados.add(traslado.id);
            despachados.add(traslado);
            actualizarPromedio(traslado);

            // Actualizar ciudades
            int monto = traslado.gananciaNeta;
            Ciudad origen = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.origen));
            Ciudad destino = heapSuperavit.elems.get(heapSuperavit.inds.get(traslado.destino));
            origen.ganancia += monto;
            origen.superavit += monto;
            destino.perdida += monto;
            destino.superavit -= monto;

            heapSuperavit.actualizarPrioridad(origen, origen);
            heapSuperavit.actualizarPrioridad(destino, destino);

            // Actualizar estadísticas
            actualizarEstadisticas(origen, true);
            actualizarEstadisticas(destino, false);
        }

        return idsDespachados.stream().mapToInt(Integer::intValue).toArray();
    }

    public int ciudadConMayorSuperavit() {
        heapSuperavit.heapifyDown(0, 0);
        return heapSuperavit.elems.get(0).getId();
    }

    public ArrayList<Integer> ciudadesConMayorGanancia() {
        return arregloGanancias;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida() {
        return arregloPerdidas;
    }

    public int gananciaPromedioPorTraslado() {
        return promedio.promedio;
    }
}
