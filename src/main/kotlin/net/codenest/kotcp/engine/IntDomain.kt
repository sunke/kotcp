package net.codenest.kotcp.engine

interface IntDomain {

    /**
     * @return the minimum value of the domain
     */
    fun min(): Int

    /**
     * @return the maximum value of the domain
     */
    fun max(): Int

    /**
     * @return the cardinality value of the domain
     */
    fun size(): Int

    /**
     * Checks if the specified value belongs to the domain.
     *
     * @param v the value to be tested
     * @return true if v belongs to the domain, false otherwise
     */
    fun contains(v: Int): Boolean

    fun clear()

    /**
     * Checks if the domain contains a single element.
     *
     * @return true if the domain contains a single element, false otherwise
     */
    fun isFixed(): Boolean

    fun fix(v: Int): Boolean

    fun remove(v: Int): Boolean

    fun restore(rs: Int)
}
