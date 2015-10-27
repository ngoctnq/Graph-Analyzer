package datastructure.finalproject.graph;

//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;

public class MLGraph{
	Graphl G = new Graphl();
	static int padding = 1;
	private final static int UNVISITED = 0;
	private final static int VISITED = 1;
	final static int DFS = 0;
	final static int BFS = 1;
	
	/** Graph Traversal
	 	@param G the graph being traversed
	 	@param i DFS (Depth-First) or BFS (Breath-First)
	 */
	static void graphTraverse(Graphl G, int i){
		int v;
		for (v = 0; v < G.n(); v++) G.setMark(v, UNVISITED);
		for (v = 0; v < G.n(); v++)
			if (G.getMark(v) == UNVISITED) doTraverse(G, v, i);
	}

	/** The actual traversal.
	 	@param G the graph being traversed
	  	@param v the current vertex
	 	@param i traversal choice
	 */
	static void doTraverse(Graphl G, int v, int i) {
		switch (i){
		case DFS: DFS(G, v); break;
		case BFS: BFS(G, v); break;
		default: throw new RuntimeException("Not an available traversal method");
		}
	}

	/** Depth-First search
	 	since it appeared first in the book.
	 	@param G the graph being traversed
	 	@param v the current vertex
	 */
	static void DFS(Graphl G, int v) {
//		PreVisit(G, v);
		PreVisit();
		
		String head = "";
		for (int i = 1; i<padding-1; i++) head+="   ";
		if (padding>=1) head+="|__";
		String desc = ": <no description>";
		if (G.getVrtDesc(v)!="") desc = ": "+G.getVrtDesc(v);
		G.DFSRep+=String.format(head+"Node %d%s\n", v, desc);
		
		G.setMark(v, VISITED);
		for (int w = G.first(v); w < G.n(); w = G.next(v,  w))
			if (G.getMark(w) == UNVISITED) DFS(G, w);
//		PostVisit(G, v);
		PostVisit();
	}
	
	/** is connected? based on DFS */
	static boolean isConnected(Graphl G, int v, int f){
		if (v == f) return true;
		G.setMark(v, VISITED);
		for (int w = G.first(v); w < G.n(); w = G.next(v,  w))
			if (G.getMark(w) == UNVISITED) return isConnected(G, w, f);
		return false;
	}

	/** Breadth-First search
		exactly what is in the book.
	 	@param G the graph being traversed
	 	@param v the current vertex
	 */
	static void BFS(Graphl G, int start) {
		Queue<Integer> Q = new AQueue<Integer>(G.n());
		Q.enqueue(start);
		G.setMark(start, VISITED);
		while (Q.length() > 0) {
			int v = Q.dequeue();
//			PreVisit(G, v);
			PreVisit();

			String head = "";
			for (int i = 1; i<padding-1; i++) head+="   ";
			if (padding>=1) head+="|__";
			String desc = ": <no description>";
			if (G.getVrtDesc(v)!="") desc = ": "+G.getVrtDesc(v);
			G.BFSRep+=String.format(head+"Node %d%s\n", v, desc);
			
			for (int w = G.first(v); w < G.n(); w = G.next(v, w))
				if (G.getMark(w) == UNVISITED) {
					G.setMark(w, VISITED);
					Q.enqueue(w);
				}
//			PostVisit(G, v);
			PostVisit();
		}
	}

	static void PreVisit() {
		padding+=1;
	}

	static void PostVisit() {
		padding-=1;
	}
	
	static Polynomial chromaPolynomial(Graphl G){
		return undirectedChromaPolynomial(G.undirecticize());
	}
	
	static Polynomial undirectedChromaPolynomial(Graphl G){
		Polynomial p = new Polynomial();
		if (G.e() == 0) {
			for (int i = 0; i< G.n(); i++) p.append(0);
			p.append(1);
			return p;
		}
		// if not a special case
		int n = getFirst(G);
		int q = G.first(n);
				
		//noedge and oneless - the name should be self-explanatory
		Graphl noEdge = G.noEdge(n, q);
		Graphl oneLess = G.oneLess(n, q);
		G.chroma = Polynomial.sub(chromaPolynomial(noEdge),chromaPolynomial(oneLess));
		return G.chroma;
	}
	
	static int getFirst(Graphl G){
		int i = 0;
		while (i < G.n()) {
			if (G.first(i) != G.n()) break;
			else i++;
		}
		return i;
	}

	static int minVertex(Graph G, int[] D) {
		int v = 0;  // Initialize v to any unvisited vertex;
		for (int i=0; i<G.n(); i++)
			if (G.getMark(i) == UNVISITED) { v = i; break; }
		for (int i=0; i<G.n(); i++)  // Now find smallest value
			if ((G.getMark(i) == UNVISITED) && (D[i] < D[v]))
				v = i;
		return v;
	}
	
	/** Compute shortest path distances from s, store them in D */
	static void Dijkstra(Graph G, int s, int[] D) {
		for (int i=0; i<G.n(); i++)    // Initialize
			D[i] = Integer.MAX_VALUE;
		D[s] = 0;
		for (int i=0; i<G.n(); i++) {  // Process the vertices
			int v = minVertex(G, D);     // Find next-closest vertex
			G.setMark(v, VISITED);
			if (D[v] == Integer.MAX_VALUE) return; // Unreachable
			for (int w = G.first(v); w < G.n(); w = G.next(v, w))
				if (D[w] > (D[v] + G.weight(v, w)))
					D[w] = D[v] + G.weight(v, w);
		}
	}
	
	/** Kruskal's MST algorithm */
	static Graphl Kruskal(Graphl G) {
		Graphl A = G.noEdge(); // Equivalence array
		KruskalElem[] E = new KruskalElem[G.e()]; // Minheap array
		int edgecnt = 0; // Count of edges

		for (int i=0; i<G.n(); i++)    // Put edges in the array
			for (int w = G.first(i); w < G.n(); w = G.next(i, w))
				E[edgecnt++] = new KruskalElem(G.weight(i, w), i, w);
				
		MinHeap<KruskalElem> H =
				new MinHeap<KruskalElem>(E, edgecnt, edgecnt);
		
		int numMST = G.n();            // Initially n classes
		while (numMST > 1) { // Combine equiv classes
						
			KruskalElem temp = H.removemin(); // Next cheapest
			int v = temp.v1();  int u = temp.v2();
			A.clearMark();
			if (!isConnected(A.undirecticize(), v, u)) {        // If in different classes
				
//				Graphl A1 = A.undirecticize();
//				A1.clearMark();
//				graphTraverse(A1, DFS);
//				System.out.println(A1.DFSRep);
//				System.out.println("u "+u+" v "+v+"\n");
				
				A.setEdge(v, u, temp.key());             // Combine equiv classes
				//A.setEdge(u, v, temp.key());
				numMST--;                  // One less MST
			}
		}
		return A;
	}

	public static void main(String[] args) {
		final MLGraph MLG = new MLGraph();
		final GUI gui = new GUI();
		gui.start(MLG.G);
		
		// delete this
//		try {
//			Parser.TGF(new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Ngoc\\Documents\\nextLVL.tgf"))), MLG.G, true);
//		}
//		catch (Exception e) {}
//		gui.optionsChooser(MLG.G);
		
	}
}