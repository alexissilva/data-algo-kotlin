package data

class MaxHeapInt : MaxHeap<Int>(Int::compareTo)

class MinHeapInt : MinHeap<Int>(Int::compareTo)

open class MaxHeap<T>(compare: (e1: T, e2: T) -> Int) : Heap<T>(compare, 1)

open class MinHeap<T>(compare: (e1: T, e2: T) -> Int) : Heap<T>(compare, -1)

open class Heap<T>(private val compareTo: T.(other: T) -> Int, private val expectedComparison: Int) {
    private val array = arrayListOf<T>()

    val top get() = array.getOrNull(0)

    fun add(element: T) {
        array.add(element)
        bubbleUp(array.size - 1)

    }

    private fun bubbleUp(index: Int) {
        val parent = array.getOrNull(index.parent) ?: return
        if (parent.compareTo(array[index]) == expectedComparison) return

        swap(index, index.parent)
        bubbleUp(index.parent)
    }

    fun removeRoot() = removeAt(0)

    /**
     * On steroids deletion. Deletes any elements of heap
     */
    fun removeAt(index: Int) {
        if (index < 0 || array.size - 1 < index) throw ArrayIndexOutOfBoundsException()

        array[index] = array[array.size - 1]

        val parent = array.getOrNull(index.parent)
        if (parent == null || parent.compareTo(array[index]) == expectedComparison) {
            bubbleDown(index)
        } else {
            bubbleUp(index)
        }
    }

    private fun bubbleDown(index: Int) {
        val firstChild = array.getOrNull(index.firstChild) ?: return
        val secondChild = array.getOrNull(index.secondChild)

        val childToSwapIndex = when {
            secondChild == null -> index.firstChild
            firstChild.compareTo(secondChild) == expectedComparison -> index.firstChild
            else -> index.secondChild
        }
        swap(index, childToSwapIndex)
        bubbleDown(childToSwapIndex)
    }

    private fun swap(i1: Int, i2: Int) {
        array[i1] = array[i2].also { array[i2] = array[i1] }
    }

    /*
    Index extensions
     */
    private val Int.firstChild get() = this * 2 + 1

    private val Int.secondChild get() = this * 2 + 2

    private val Int.parent: Int get() = (this - 1) / 2
}
