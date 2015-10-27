package datastructure.finalproject.graph;

/** Singly linked list node */
class Link<E> {
	private E element;        // Value for this node
	private Link<E> next;     // Pointer to next node in list

	// Constructors
	Link(E it, Link<E> nextval)
	{ element = it;  next = nextval; }
	Link(Link<E> nextval) { next = nextval; }

	Link<E> next() { return next; }  // Return next field
	Link<E> setNext(Link<E> nextval) // Set next field
	{ return next = nextval; }     // Return element field
	E element() { return element; }  // Set element field
	E setElement(E it) { return element = it; }
}
