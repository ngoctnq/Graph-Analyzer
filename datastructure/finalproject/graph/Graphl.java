package datastructure.finalproject.graph;

/** Adjacency list graph implementation */
class Graphl implements Graph {
	private GraphList[] vertex;            // The vertex list
	private int numEdge;                   // Number of edges
	String[] vrtDesc;			   // vertices' descriptions
	public int[] Mark;                     // The mark array
	Polynomial chroma;

	String DFSRep = "";
	String BFSRep = "";

	public Graphl() {}
	public Graphl(int n)                   // Constructor
	{ Init(n); }

	public void Init(int n) {
		Mark = new int[n];
		vertex = new GraphList[n];
		vrtDesc = new String[n];
		for (int i=0; i<n; i++)
			vertex[i] = new GraphList();
		numEdge = 0;
		for (int i = 0; i < n; i++) this.setMark(i, UNVISITED);
	}
	
	public void Init(int n, List<String> vrt){
		Init(n);
		vrt.toArray(vrtDesc);
	}

	/** no edges are kept whatsoever */
	public Graphl noEdge(){
		Graphl g = new Graphl(this.n());
		g.vrtDesc = vrtDesc;
		return g;
	}
		
	
	public int n() { return Mark.length; } // # of vertices
	public int e() { return numEdge; }     // # of edges
	
	/** Vertex description setter */
	void setVrtDesc(int n, String s){
		vrtDesc[n] = s;
	}
	
	/** Vertex description getter */
	String getVrtDesc(int n){
		if (vrtDesc[n]!= null) return vrtDesc[n];
		else return "<blank>";
	}
	
	/** @return v's first neighbor */
	public int first(int v) {
		if (vertex[v].length() == 0)
			return Mark.length;   // No neighbor
		vertex[v].moveToStart();
		Edge it = vertex[v].getValue();
		return it.vertex();
	}

	/** @return v's next neighbor after w */
	public int next(int v, int w) {
		Edge it = null;
		if (isEdge(v, w)) {
			vertex[v].next();
			it = vertex[v].getValue();
		}
		if (it != null) return it.vertex();
		return Mark.length; // No neighbor
	}
	
	
	/** Set the weight for an edge */
	public void setEdge(int i, int j, int weight) {		
		if (weight == 0) throw new RuntimeException("May not set weight to 0");
		if (i == j) return;
		if (i>=Mark.length || j>= Mark.length) throw new RuntimeException("Edge vertex out of range");
		Edge currEdge = new Edge(j, weight);
		if (isEdge(i, j)) { // Edge already exists in graph
			vertex[i].remove();
			vertex[i].insert(currEdge);
		}
		else { // Keep neighbors sorted by vertex index
			numEdge++;
			for (vertex[i].moveToStart();
					vertex[i].currPos() < vertex[i].length();
					vertex[i].next())
				if (vertex[i].getValue().vertex() > j) break;
			vertex[i].insert(currEdge);
		}
	}
	
	/** Set the weight for an edge with description*/
	public void setEdge(int i, int j, int weight, String s) {
		if (weight == 0) throw new RuntimeException("May not set weight to 0");
		if (i == j) return;
		if (i>=Mark.length || j>= Mark.length) throw new RuntimeException("Edge vertex out of range");
		Edge currEdge = new Edge(j, weight, s);
		if (isEdge(i, j)) { // Edge already exists in graph
			vertex[i].remove();
			vertex[i].insert(currEdge);
		}
		else { // Keep neighbors sorted by vertex index
			numEdge++;
			for (vertex[i].moveToStart();
					vertex[i].currPos() < vertex[i].length();
					vertex[i].next())
				if (vertex[i].getValue().vertex() > j) break;
			vertex[i].insert(currEdge);
		}
	}

	/** Delete an edge */
	public void delEdge(int i, int j) {
//		System.out.printf("delEdge i %d j %d\n",i,j);
		if (isEdge(i, j)) { vertex[i].remove(); numEdge--; }
	}

	/** Determine if an edge is in the graph */
	public boolean isEdge(int v, int w) {
		if (v>=Mark.length) throw new RuntimeException("Edge vertex out of range");
		Edge it = vertex[v].getValue();
		// Check if j is the current neighbor in the list
		if ((it != null) && (it.vertex() == w)) return true;
		for (vertex[v].moveToStart();
				vertex[v].currPos() < vertex[v].length();
				vertex[v].next())              // Check whole list
			if (vertex[v].getValue().vertex() == w) return true;
		return false;
	}

	/** @return an edge's weight */
	public int weight(int i, int j) {
		if (isEdge(i, j)) return vertex[i].getValue().weight();
		return 0;
	}

	/** Set/Get the mark value for a vertex */
	public void setMark(int v, int val) { Mark[v] = val; }
	public int getMark(int v) { return Mark[v]; }
	public void clearMark() {for (int i = 0; i<Mark.length; i++) setMark(i, UNVISITED);};
	
	/** toString() overriding */
	public String toString(){
		String s = "Number of vertices: "+vertex.length+"\n";
		for (String i:vrtDesc) s+=(i+"\n");
		s += "Number of edges: "+numEdge+"\n";
		for (GraphList i: vertex) s+=(i.toString() + "\n");
		return s;
	}

	/** no description will be kept. */
	Graphl oneLess(int n, int q) {		
		if (n>=Mark.length || q >=Mark.length) throw new NullPointerException("No such vertex");
		// make q bigger than n
		if (q < n) {
			q = q + n;
			n = q - n;
			q = q - n;
		}
		Graphl g = new Graphl(Mark.length-1);
		for (int i = 0; i < q; i++){
			vertex[i].moveToStart();
			while (vertex[i].currPos() < vertex[i].length()){
				int x = vertex[i].getValue().vertex();				
				if (x<q) g.setEdge(i, x, vertex[i].getValue().weight());
				if (x>q) g.setEdge(i, x-1, vertex[i].getValue().weight());				
				vertex[i].next();
			}
		}
		
		for (int i = q+1; i < Mark.length; i++){
			vertex[i].moveToStart();
			while (vertex[i].currPos() < vertex[i].length()){
				int x = vertex[i].getValue().vertex();
				if (x<q) g.setEdge(i-1, x, vertex[i].getValue().weight());
				if (x>q) g.setEdge(i-1, x-1, vertex[i].getValue().weight());				
				vertex[i].next();
			}
		}
		
		vertex[q].moveToStart();
		while (vertex[q].currPos() < vertex[q].length()){
			int x = vertex[q].getValue().vertex();
			if (x<q) g.setEdge(n, x, vertex[q].getValue().weight());
			if (x>q) g.setEdge(n, x-1, vertex[q].getValue().weight());
			vertex[q].next();
		}		
		return g;
	}
	
	/** clone without any description. use at your own risk. */
	Graphl noEdge(int n, int q) {		
		if (n>=Mark.length || q >=Mark.length) throw new NullPointerException("No such vertex");
		Graphl g = new Graphl(Mark.length);
		// cutting corners a bit as I don't copy all the edges
		for (int i = 0; i < Mark.length; i++){
			vertex[i].moveToStart();
			while (vertex[i].currPos() < vertex[i].length()){
				g.setEdge(i, vertex[i].getValue().vertex(), vertex[i].getValue().weight());
				vertex[i].next();
			}
		}
		g.delEdge(n, q);
		g.delEdge(q, n);		
		return g;
	}
	
	/** convert a Graph into undirected by adding the missing edges */
	public Graphl undirecticize() {
		Graphl g = new Graphl(Mark.length);
		g.vrtDesc = vrtDesc;
		for (int i = 0; i < Mark.length; i++){
			vertex[i].moveToStart();
			while (vertex[i].currPos() < vertex[i].length()){
				g.setEdge(i, vertex[i].getValue().vertex(), vertex[i].getValue().weight());
				g.setEdge(vertex[i].getValue().vertex(), i, vertex[i].getValue().weight());
				vertex[i].next();
			}
		}		
		return g;
	}
	
//	public static void main(String[] args) {
//		A[] a = new A[]{new A(1)};
//		A[] b = a.clone();
//		b[0].setA(0);
//		System.out.println(a[0]);
//	}
}