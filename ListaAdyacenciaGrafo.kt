class ListaAdyacenciaGrafo<T> : Grafo<T> {
    private val adyacencias = mutableMapOf<T, mutableList<T>>()
}