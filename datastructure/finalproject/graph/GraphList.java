package datastructure.finalproject.graph;

/** Linked list for graphs: Provides access to curr */
class GraphList extends LList<Edge> {
	public Link<Edge> currLink() { return curr; }
	public void setCurr(Link<Edge> who) { curr = who; }
}
