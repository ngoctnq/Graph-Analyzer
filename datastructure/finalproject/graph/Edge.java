package datastructure.finalproject.graph;

class Edge {
	private int vert, wt;
	private String desc;
	
	// Constructor
	public Edge(int v, int w) { vert = v; wt = w; desc = "";}
	public Edge(int v, int w, String s) { vert = v; wt = w; desc = s;}
	
	public int vertex() { return vert; }
	public int weight() { return wt; }
	public String desc() { return desc; }
	public String toString() {return String.format("//Node to %d wt %d desc %s //", vert, wt, desc); }
}