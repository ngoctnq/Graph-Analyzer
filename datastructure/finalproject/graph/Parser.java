package datastructure.finalproject.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

class Parser {
	
	/** Trivial Graph Format parser
 		parses the graph file in PGF.
 		http://en.wikipedia.org/wiki/Trivial_Graph_Format
 		@param file the graph file input
 		@param G a random graph
 		@param directed whether the graph is directed
 		@return the parsed graph
 		@throws IOException
	 */
	static Graphl TGF(BufferedReader file, Graphl G, boolean directed) throws IOException {
		String line = null;
		String desc = null;
		String temp = null;
		StringTokenizer token;
		int v1, v2, weight, n;

		// if file is blank
		if ((line = file.readLine()) == null) throw new RuntimeException("Unable to read vertices");
		// get rid of all the opening #'s
		while(line.length() == 0 || line.charAt(0) == '#'){
			if ((line = file.readLine()) == null) throw new RuntimeException("Unable to read vertices");
		}

		// get vertices
		List<String> vrt = new LList<String>();
		n = 0;
		while (line.charAt(0) != '#'){
			desc = "";
			token = new StringTokenizer(line);
			try {
				String s = token.nextToken();
				if (Integer.parseInt(s) != (n + 1)) throw new RuntimeException("Invaid vertex");
			}
			catch (NumberFormatException e){
				throw new RuntimeException("Invalid vertex");
			}
			while (token.hasMoreTokens() && (temp = token.nextToken()).charAt(0) != '#') desc += (temp + " ");
			vrt.append(desc);
			n += 1;				// increment vertices count
			if ((line = file.readLine()) == null) throw new RuntimeException("Unable to read vertices");
		}

		G.Init(n, vrt);

		// get edges
		while((line = file.readLine()) != null) {
			desc = "";
			temp = "";
			token = new StringTokenizer(line);
			try {
				v1 = Integer.parseInt(token.nextToken());
				v2 = Integer.parseInt(token.nextToken());
			}
			catch (NumberFormatException e){
				throw new RuntimeException("Invalid edge");
			}
			if (token.hasMoreTokens()) {
				try{
					temp = token.nextToken();
					weight = Integer.parseInt(temp);
					while (token.hasMoreTokens() && (temp = token.nextToken()).charAt(0) != '#') desc += (temp + " ");
				}
				catch (NumberFormatException e) {
					weight = 1;
					desc += (temp + " ");
					while (token.hasMoreTokens() && (temp = token.nextToken()).charAt(0) != '#') desc += (temp + " ");
				}
			}
			else weight = 1;
			G.setEdge(v1-1, v2-1, weight, desc);
			if (!directed) G.setEdge(v2-1, v1-1, weight, desc);
		}
		return G;
	}

	/** DOT Format parser
	 	parses the graph file in DOT format.
	 	http://en.wikipedia.org/w/index.php?title=DOT_(graph_description_language)
	 	@param file the graph file input
 		@param G a random graph
 		@param directed whether the graph is directed
 		@return the parsed graph
 		@throws IOException
	 */
	static Graphl DOT(BufferedReader file, Graph G, boolean directed) throws IOException {
		//TODO IMPLEMENT
		throw new RuntimeException("We're sorry, DOT parser not implemented yet");
		// return again
	}

	/** GraphML Format parser
 	parses the graph file in GraphML format.
 	http://en.wikipedia.org/wiki/GraphML
 	@param file the graph file input
		@param G a random graph
 		@param directed whether the graph is directed
		@return the parsed graph
		@throws IOException
	 */
	static Graphl GraphML(BufferedReader file, Graph G, boolean directed) throws IOException {
		//TODO IMPLEMENT
		throw new RuntimeException("We're sorry, GraphML parser not implemented yet");
		// return again
	}

}
