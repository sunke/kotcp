package net.codenest.kotcp.engine

/**
 * Implement IntDomain with Spare Set. The advantages of Spare Set are it is very time efficient with CRUD operations;
 * and it is very time & space efficient to back up and restore the domain.
 */
class SparseSetIntDomain(private var min: Int, private var max: Int) : IntDomain {
    private val offset = min
    private val cap = max - min + 1
    private var size = cap
    private val sparse = IntArray(cap)     // array to store the index of elements
    private val dense = IntArray(cap)      // array to store the actual elements

    init {
        for (i in 0..<cap) {
            sparse[i] = i
            dense[i] = i + offset
        }
    }

    override fun min() = min

    override fun max() = max

    override fun size() = size

    override fun contains(v: Int) = v in min..< max + 1 && sparse[v - offset] < size

    override fun clear() {
        size = 0
    }

    override fun isFixed() = size == 1

    override fun fix(v: Int): Boolean {
        if (!contains(v)) {
            return false
        }

        swap(dense[0] - offset, v - offset)
        min = v; max = v; size = 1

        return true
    }

    override fun remove(v: Int): Boolean {
        if (!contains(v)) {
            return false
        }

        swap(v - offset, dense[size - 1] - offset)
        size--; min = updateMinRemoved(v); max = updateMaxRemoved(v)

        return true
    }

    override fun restore(rs: Int) {
        if (rs in 1..<cap + 1) {
            size = rs
            min = dense.slice(IntRange(0, rs - 1)).min()
            max = dense.slice(IntRange(0, rs - 1)).max()
        } else {
            throw IndexOutOfBoundsException("$rs is out of the range of spare set.")
        }
    }

    override fun toString(): String {
        return dense.slice(IntRange(0, size - 1)).toString()
    }


    private fun swap(i: Int, j: Int) {
        if (i == j) {
            return
        }

        val idx = sparse[j]
        val value = dense[idx]

        dense[sparse[j]] = dense[sparse[i]]
        dense[sparse[i]] = value

        sparse[j] = sparse[i]
        sparse[i] = idx
    }

    private fun updateMinRemoved(v: Int) =
        if (min != v || size == 0) min else dense.slice(IntRange(0, size - 1)).min()

    private fun updateMaxRemoved(v: Int) =
        if (max != v || size == 0) max else dense.slice(IntRange(0, size - 1)).max()


    override fun equals(other: Any?) =
        other is SparseSetIntDomain
                && other.size == this.size
                && other.min == this.min
                && other.max == this.max
                && other.dense.slice(IntRange(0, other.size - 1)).sorted() == this.dense.slice(IntRange(0, this.size - 1)).sorted()
    override fun hashCode(): Int {
        var result = sparse.contentHashCode()
        result = 31 * result + dense.contentHashCode()
        result = 31 * result + size
        return result
    }
}