class ListaAdyacenciaGrafo<T> : Grafo<T> {
    private val adyacencias = mutableMapOf<T, MutableList<T>>()

    override fun agregarVertice(v: T): Boolean {
        if (adyacencias.containsKey(v)) { 
            return false
        }
        adyacencias[v] = mutableListOf()
        return true
    }

    override fun contiene (v: T): Boolean = v in adyacencias

    override fun conectar(desde: T, hasta: T): Boolean {

        return false
    }

    override fun eliminarVertice(v: T): Boolean {
        return false
    }

    override fun obtenerArcosSalida(v: T): List<T> {
        return emptyList()
    }

    override fun obtenerArcosEntrada(v: T): List<T> {
        if (!adyacencias.containsKey(v)) 
        return emptyList()

        return adyacencias
        .filter { it.value.contains(v) }
        .map { it.key }
    }

    override fun tamano(): Int = adyacencias.size

    override fun subgrafo(vertices: Collection<T>): Grafo<T> {
        val nuevoGrafo = ListaAdyacenciaGrafo<T>()

        for (v in vertices) {
            if (adyacencias.containsKey(v)) {
                nuevoGrafo.agregarVertice(v)
            }
        }

        for (v in vertices) {
            if (this.contiene(v)) {
                val listaDeVecinos = this.obtenerArcosSalida(v)
                for (vecino in listaDeVecinos) {
                    if (nuevoGrafo.contiene(vecino)) {
                        nuevoGrafo.conectar(v, vecino)
                    }
                }
            }
        }
    return nuevoGrafo
    }
}