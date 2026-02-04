class ListaAdyacenciaGrafo<T> : Grafo<T> {
    private val adyacencias = mutableMapOf<T, mutableList<T>>()

    override fun agregarVertice(v: T): Boolean {
        if (adyacencias.containsKey(v)) return false
        adyacencias[v] = mutableListOf()
        return true

    override fun contiene (v: T): Boolean = v in adyacencias
    }
}