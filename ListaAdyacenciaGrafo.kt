class ListaAdyacenciaGrafo<T> : Grafo<T> {
    private val adyacencias = mutableMapOf<T, mutableList<T>>()

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
        return emptyList()
    }

    override fun tamano(): Int {
        return 0
    }

    override fun subgrafo(vertices: Collection<T>): Grafo<T> {
    }
    
    }
}