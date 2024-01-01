/*
 * mini-cp is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License  v3
 * as published by the Free Software Foundation.
 *
 * mini-cp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY.
 * See the GNU Lesser General Public License  for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with mini-cp. If not, see http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * Copyright (c)  2018. by Laurent Michel, Pierre Schaus, Pascal Van Hentenryck
 */
package net.codenest.kotcp.engine

/**
 * Interface for integer domain implementation.
 * A domain is encapsulated in an [IntVar] implementation.
 * A domain is like a set of integers.
 */
interface IntDomain {
    /**
     * Returns the minimum value of the domain.
     *
     * @return the minimum value of the domain
     */
    fun min(): Int

    /**
     * Returns the maximum value of the domain.
     *
     * @return the maximum value of the domain
     */
    fun max(): Int

    /**
     * Returns the cardinality of the domain.
     *
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

    /**
     * Checks if the domain contains a single element.
     *
     * @return true if the domain contains a single element,
     * false otherwise
     */
    val isSingleton: Boolean

    /**
     * Removes a value from the domain and notifies appropriately the listener.
     *
     * @param v the value to be removed
     * @param l the methods of the listener are notified as follows:
     *
     *  *  [DomainListener.change] is called
     * if v belongs to the domain
     *  *  [DomainListener.changeMax] is called
     * if v is equal to the maximum value
     *  *  [DomainListener.changeMin] is called
     * if v is equal to the minimum value
     *  *  [DomainListener.fix] is called
     * if v belongs to the domain and after its removal
     * the domain has a single value
     *  *  [DomainListener.empty]  is called
     * if v is the last value in the domain i.e.
     * the domain is empty after this operation
     *
     */
    fun remove(v: Int, l: DomainListener?)

    /**
     * Removes every value from the domain except the specified one.
     *
     * @param v the value to be kept
     * @param l the methods of the listener are notified as follows:
     *
     *  *  [DomainListener.change] is called
     * if some value is removed during the operation
     *  *  [DomainListener.changeMax] is called
     * if v is not equal to the maximum value
     *  *  [DomainListener.changeMin] is called
     * if v is not equal to the minimum value
     *  *  [DomainListener.fix] is called
     * if v belongs to the domain and after its removal
     * the domain has a single value
     *  *  [DomainListener.empty]  is called
     * if v is not in the domain i.e.
     * the domain is empty after this operation
     *
     */
    fun removeAllBut(v: Int, l: DomainListener?)

    /**
     * Removes every value less than the specified value from the domain.
     *
     * @param v the value such that all the values less than v are removed
     * @param l the methods of the listener are notified as follows:
     *
     *  *  [DomainListener.change] is called
     * if some value is removed during the operation
     *  *  [DomainListener.changeMax] is called
     * if v is is larger than the minimum value
     *  *  [DomainListener.fix] is called
     * if v is equal to the maximum value
     *  *  [DomainListener.empty] is called
     * if v is larger than the maximum value i.e.
     * the domain is empty after this operation
     *
     */
    fun removeBelow(v: Int, l: DomainListener?)

    /**
     * Removes every value larger than the specified value from the domain.
     *
     * @param v the value such that all the values larger than v are removed
     * @param l the methods of the listener are notified as follows:
     *
     *  *  [DomainListener.change] is called
     * if some value is removed during the operation
     *  *  [DomainListener.changeMax] is called
     * if v is is less than the maximum value
     *  *  [DomainListener.fix] is called
     * if v is equal to the minimum value
     *  *  [DomainListener.empty] is called
     * if v is less than the minimum value i.e.
     * the domain is empty after this operation
     *
     */
    fun removeAbove(v: Int, l: DomainListener?)

    /**
     * Copies the values of the domain into an array.
     *
     * @param dest an array large enough `dest.length >= size()`
     * @return the size of the domain and `dest[0,...,size-1]` contains
     * the values in the domain in an arbitrary order
     */
    fun fillArray(dest: IntArray?): Int

    override fun toString(): String
}
