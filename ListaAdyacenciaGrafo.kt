class ListaAdyacenciaGrafo<T> : Grafo<T> {
    private val adyacencias = mutableMapOf<T, MutableList<T>>()

    override fun agregarVertice(v: T): Boolean {
        if (contiene(v)) { 
            return false
        }
        adyacencias[v] = mutableListOf()
        return true
    }

    override fun contiene (v: T): Boolean = v in adyacencias

    override fun conectar(desde: T, hasta: T): Boolean {
        if (!contiene(desde) || !contiene(hasta)) { 
            return false
        }
        val adyacentes = adyacencias[desde] ?: return false
        if (adyacentes.contains(hasta)) {
            return false
        }
        adyacentes.add(hasta)
        return true
    }

    override fun eliminarVertice(v: T): Boolean {
        if(!contiene(v)){
            return false
        }
        adyacencias.remove(v)
        for (adyacentes in adyacencias.values) {
            adyacentes.remove(v)
        }
        return true
    }

    override fun obtenerArcosSalida(v: T): List<T> {
        return adyacencias[v] ?: emptyList()
    }

    override fun obtenerArcosEntrada(v: T): List<T> {
        if (!contiene(v)) 
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
    
    override fun toString(): String {
        return buildString {
            append("Grafo de ${tamano()} vertices:\n")
            for (vertice in adyacencias) {
                if (vertice.value.isEmpty()) {
                    append("${vertice.key} -> Sin adyacentes\n")
                }
                append("${vertice.key} -> ${vertice.value.joinToString(" -> ")}\n")
            }
        }
    }
}