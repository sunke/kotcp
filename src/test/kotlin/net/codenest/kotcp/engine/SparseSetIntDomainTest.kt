package net.codenest.kotcp.engine

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SparseSetIntDomainTest {

    @Test
    fun `only the given value left after fix`() {
        val set = SparseSetIntDomain(0, 4)

        set.fix(2)

        assertTrue(set.contains(2))
        assertEquals(1, set.size())
        assertEquals(2, set.min())
        assertEquals(2, set.max())
    }

    @Test
    fun `remove a value from the set`() {
        val set = SparseSetIntDomain(1, 5)

        set.remove(3)

        assertTrue(set.contains(1))
        assertTrue(set.contains(2))
        assertTrue(set.contains(4))
        assertTrue(set.contains(5))
        assertFalse(set.contains(3))

        assertEquals(4, set.size())
        assertEquals(1, set.min())
        assertEquals(5, set.max())
    }

    @Test
    fun `restore set after several ops`() {
        val set = SparseSetIntDomain(0, 4)

        val backupSize = set.backup()

        set.remove(3)
        set.remove(2)
        assertEquals(3, set.size())

        set.fix(1)
        assertEquals(1, set.size())

        set.restore(backupSize)
        assertEquals(SparseSetIntDomain(0, 4), set)
    }
}