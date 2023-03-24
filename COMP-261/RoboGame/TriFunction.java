/**
 * Functional interface that takes in 3 argument types and a return type.
 */
interface TriFunction<T, U, V, R> {
    /**
     * Returns the applied function on parameters T, U, and V as type R.
     */
    R apply(T t, U u, V v);
}
