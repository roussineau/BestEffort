package aed;

public interface Identificable {
    int getId();
}

// Algo que tienen en común tanto los objetos Traslado como los Ciudad es que
// son identificables a través de un ID único; esto lo vamos a utilizar para
// crear un arreglo de índices donde en la posición del ID se guarde la posición
// de ese elemento en el arreglo de elementos.