package datastructure.finalproject.graph;

/** Queue ADT */
public interface Queue<E> {

	/** Reinitialize the queue.  The user is responsible for
      reclaiming the storage used by the queue elements. */
	public void clear();

	/** Place an element at the rear of the queue.
      @param it The element being enqueued. */
	public void enqueue(E it);

	/** Remove and return element at the front of the queue.
      @return The element at the front of the queue. */
	public E dequeue();

	/** @return The front element. */
	public E frontValue();

	/** @return The number of elements in the queue. */
	public int length();
}
