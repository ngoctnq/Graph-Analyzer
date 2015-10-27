package datastructure.finalproject.graph;

import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
//import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

// FOR ALL THE STUPID TYPE CLOSURE THINGY WITH PRIMITIVES
class FinalBoolean{
	boolean value;
	FinalBoolean(boolean b) { value = b; }
}
class FromTo{
	int from;
	int to;
	FromTo() {
		from = to = 0;
	}
}

class GUI {
	static final Font BOLD = new Font("Dialog",Font.BOLD, 20);
	static final Font ITALIC = new Font("Dialog",Font.ITALIC, 12);
	static final Font NORMAL = new Font("Dialog", Font.PLAIN, 13);
	static final Font CODE = new Font("Monospaced", Font.PLAIN, 12);
	static final String TITLE =
			"\uff24\uff2f\uff34\uff2f\uff22\uff2f\uff39\uff33\uff26\uff29\uff2e\uff21\uff2c\uff30\uff32\uff2f\uff2a\uff25\uff23\uff34";

	static final JLabel HEADER = new JLabel("GRAPH MANIPULATOR v1.0");
	static final JLabel SUBTITLE = new JLabel("by Ngoc N. Tran and Son N. Hoang");

	// FANCY INITIALIZATION BLOCKS
	// THANK YOU BASED J2SE CERT PROG
	{
		HEADER.setFont(BOLD);
		SUBTITLE.setFont(ITALIC);
	}

	/** GUI interface to choose a graph file */
	void start(Graphl G){
		JFrame frame = new JFrame(TITLE);
		frame.setResizable(false);
		JLayeredPane heartbroken = new JLayeredPane();
		JButton chooseButton = new JButton("Import");
//		JCheckBox directedToggle = new JCheckBox("Undirected", false);
		FinalBoolean directed = new FinalBoolean(true);

		// debatable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 160);
		frame.setLocationRelativeTo(null);

		// working systematically, NOT scalable
		HEADER.setBounds(60, 0, 300, 40);
		SUBTITLE.setBounds(100, 20, 300, 50);
		chooseButton.setBounds(155, 72, 80, 30);
//		directedToggle.setBounds(152, 102, 150, 20);

		chooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				String fileName = "";
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Graph files", "tgf", "dot", "xml");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						fileName = fileChooser.getSelectedFile().getAbsolutePath();
						System.out.println("Opened " + fileName);
						if (fileName.endsWith(".tgf")) {
							int response = JOptionPane.showConfirmDialog(null, "Is this graph directed?", "Graph Directedness",
							        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.YES_OPTION) directed.value = true;
							if (response == JOptionPane.NO_OPTION) directed.value = false;
							if (response == JOptionPane.CLOSED_OPTION) JOptionPane.showMessageDialog(frame, new JLabel("Graph is set to be directed by default.", JLabel.CENTER), "Option cancelled", JOptionPane.INFORMATION_MESSAGE);

							Parser.TGF(new BufferedReader(new InputStreamReader(new FileInputStream(fileName))), G, directed.value);
							JOptionPane.showMessageDialog(frame, new JLabel("This parser converted the graph into zero-based.", JLabel.CENTER), "TGF is not zero-based!", JOptionPane.WARNING_MESSAGE);
						}
						else if (fileName.endsWith(".dot")) Parser.DOT(new BufferedReader(new InputStreamReader(new FileInputStream(fileName))), G, directed.value);
						else if (fileName.endsWith(".xml")) Parser.GraphML(new BufferedReader(new InputStreamReader(new FileInputStream(fileName))), G, directed.value);
						else throw new RuntimeException("Invalid file extension!");
						frame.dispose();

						optionsChooser(G);

					} catch (Exception err) {
						JOptionPane.showMessageDialog(frame, new JLabel(err.getMessage()+"!", JLabel.CENTER), "An error occured.", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

//		directedToggle.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				directed.value = !directedToggle.isSelected();
//				if (directed.value) System.out.println("Directed");
//				else System.out.println("Undirected");
//			}
//		});

		frame.add(heartbroken);
		heartbroken.add(HEADER);
		heartbroken.add(SUBTITLE);
		heartbroken.add(chooseButton);
//		heartbroken.add(directedToggle);

		frame.setVisible(true);
	}

	void optionsChooser(Graphl G){
		JFrame frame = new JFrame(TITLE);
		frame.setResizable(false);
		JLayeredPane heartbroken = new JLayeredPane();
		JLabel status = new JLabel("Graph loaded. I guess.", SwingConstants.CENTER);
		JButton traverse = new JButton("Graph traversal");
		JButton shortestPath = new JButton("Shortest path");
		JButton MSP = new JButton("Minimal Spanning Tree");
		JButton chroma = new JButton("Chromatic function and number");
		JButton reset = new JButton("choose new graph file");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(765, 320);
		frame.setLocationRelativeTo(null);// working systematically, NOT scalable
		HEADER.setBounds(250, 10, 300, 40);
		SUBTITLE.setBounds(280, 30, 300, 50);
		status.setBounds(0, 72, 765, 30);
		traverse.setBounds(50, 130, 300, 30);
		shortestPath.setBounds(400, 130, 300, 30);
		MSP.setBounds(50, 180, 300, 30);
		chroma.setBounds(400, 180, 300, 30);
		reset.setBounds(290, 235, 160, 25);

		traverse.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) { status.setText("Choose an option."); }

			public void mouseEntered(MouseEvent e) { status.setText("Traverse through the graph."); }

			public void mouseClicked(MouseEvent e) { traverse(frame, G); }
		});
		shortestPath.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) { status.setText("Choose an option."); }

			public void mouseEntered(MouseEvent e) { status.setText("Calculate the graph's shortest path."); }

			public void mouseClicked(MouseEvent e) { shortestPath(frame, G); }
		});
		MSP.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) { status.setText("Choose an option."); }
			public void mouseEntered(MouseEvent e) { status.setText("Display the graph's Minimal Spanning Tree."); }
			public void mouseClicked(MouseEvent e) { MST(frame, G, MSP); }
		});
		chroma.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) { status.setText("Choose an option."); }

			public void mouseEntered(MouseEvent e) { status.setText("Calculate the graph's chromatic function and chromatic number."); }

			public void mouseClicked(MouseEvent e) { chroma(frame, G); }
		});
		reset.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) { status.setText("Choose an option."); }

			public void mouseEntered(MouseEvent e) { status.setText("Choose another graph file to operate on."); }

			public void mouseClicked(MouseEvent e) { frame.dispose(); start(G); }
		});

		frame.add(heartbroken);
		heartbroken.add(HEADER);
		heartbroken.add(SUBTITLE);
		heartbroken.add(status);
		heartbroken.add(traverse);
		heartbroken.add(shortestPath);
		heartbroken.add(MSP);
		heartbroken.add(chroma);
		heartbroken.add(reset);

		frame.setVisible(true);
	}

	void traverse(JFrame frame, Graphl G){
		JDialog f = new JDialog(frame, "Graph Traversal", ModalityType.DOCUMENT_MODAL);
		f.setLocationRelativeTo(frame);
		f.setSize(700,600);
		f.setResizable(false);
		JLayeredPane heartbroken = new JLayeredPane();
		JLabel dfsL = new JLabel("Depth-First Search:");
		JLabel bfsL = new JLabel("Breadth-First Search:");
		JTextArea dfs = new JTextArea();
		JTextArea bfs = new JTextArea();
		JScrollPane dfsScr = new JScrollPane(dfs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane bfsScr = new JScrollPane(bfs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		dfs.setEditable(false);
		bfs.setEditable(false);
		dfs.setFont(CODE);
		bfs.setFont(CODE);

		G.clearMark();
		if (G.DFSRep.length()!=0) G.DFSRep = "";
		MLGraph.graphTraverse(G, 0);
		dfs.setText(G.DFSRep);

		G.clearMark();
		if (G.BFSRep.length()!=0) G.BFSRep = "";
		MLGraph.graphTraverse(G, 1);
		bfs.setText(G.BFSRep);

		dfsL.setBounds(37, 10, 300, 20);
		bfsL.setBounds(363, 10, 300, 20);
		dfsScr.setBounds(37, 40, 300, 500);
		bfsScr.setBounds(363, 40, 300, 500);

		heartbroken.add(dfsL);
		heartbroken.add(bfsL);
		heartbroken.add(dfsScr);
		heartbroken.add(bfsScr);
		f.add(heartbroken);
		f.setVisible(true);
	}

	void shortestPath(JFrame frame, Graphl G){
		JDialog f = new JDialog(frame, "Shortest Path", ModalityType.DOCUMENT_MODAL);
		f.setLocationRelativeTo(frame);
		f.setSize(700, 170);
		f.setResizable(false);
		JLayeredPane heartbroken = new JLayeredPane();
		f.add(heartbroken);

		JLabel frmL = new JLabel("Origin node:");
		JLabel toL = new JLabel("Destination node:");
		JLabel rsltL = new JLabel ("Distance:");

		int length = G.vrtDesc.length;
		String[] newDesc = new String[length];
		for (int i = 0; i<length; i++){
			if (G.vrtDesc[i] == "") newDesc[i] = "Node "+i+": <no description>";
			else newDesc[i] = "Node "+i+": "+G.vrtDesc[i];
		}

		JComboBox<String> from = new JComboBox<String>(newDesc);
		JComboBox<String> to = new JComboBox<String>(newDesc);
		JLabel rslt = new JLabel("0");
		rslt.setFont(CODE);
		frmL.setBounds(50, 30, 220, 30);
		toL.setBounds(300, 30, 220, 30);
		rsltL.setBounds(550, 30, 100, 30);
		from.setBounds(50, 60, 220, 30);
		to.setBounds(300, 60, 220, 30);
		rslt.setBounds(550, 60, 100, 30);

		int D[] = new int[100];
		FromTo ft = new FromTo();
		G.clearMark();
		MLGraph.Dijkstra(G, 0, D);

		from.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				ft.from = from.getSelectedIndex();
				G.clearMark();
				MLGraph.Dijkstra(G, ft.from, D);
				if (D[ft.to] == Integer.MAX_VALUE) rslt.setText("\u221e");
				else rslt.setText(Integer.toString(D[ft.to]));
			}
		});

		to.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ft.to = to.getSelectedIndex();
				if (D[ft.to] == Integer.MAX_VALUE) rslt.setText("\u221e");
				else rslt.setText(Integer.toString(D[ft.to]));
			}
		});

		heartbroken.add(frmL);
		heartbroken.add(toL);
		heartbroken.add(rsltL);
		heartbroken.add(from);
		heartbroken.add(to);
		heartbroken.add(rslt);

		f.setVisible(true);
	}

	void MST(JFrame frame, Graphl G, JButton MSP){
		JDialog f = new JDialog(frame, "Minimal Spanning Tree", ModalityType.DOCUMENT_MODAL);
		f.setLocationRelativeTo(frame);
		f.setSize(500, 500);
		f.setResizable(false);
//		JLayeredPane heartbroken = new JLayeredPane();
//		f.add(heartbroken);
		
		try{
			Graphl A = MLGraph.Kruskal(G).undirecticize();
			A.clearMark();
			MLGraph.graphTraverse(A, MLGraph.DFS);
			JTextArea dfs = new JTextArea(A.DFSRep);
			dfs.setFont(CODE);
			JScrollPane dfsScr = new JScrollPane(dfs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//			dfsScr.setBounds(0, 0, 500, 500);
//			heartbroken.add(dfsScr);
			f.add(dfsScr);
			f.setVisible(true);
		}
		catch (RuntimeException e) {
			JOptionPane.showMessageDialog(frame, new JLabel("The graph is not connected!", JLabel.CENTER), "An error occured.", JOptionPane.ERROR_MESSAGE);
			MSP.setEnabled(false);
		}		
	}

	void chroma(JFrame frame, Graphl G){
		Polynomial p = MLGraph.chromaPolynomial(G);

		JDialog f = new JDialog(frame, "Chroma", ModalityType.DOCUMENT_MODAL);
		JLayeredPane heartbroken = new JLayeredPane();
		JLabel chrfnDesc = new JLabel("Chromatic function:");
		JTextArea chrfn = new JTextArea();
		JScrollPane chrfnScr = new JScrollPane(chrfn, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JLabel chrnoDesc = new JLabel("Chromatic number:");
		JTextArea chrno = new JTextArea();
		JLabel insDesc = new JLabel("Insert x and hit Enter:");
		JTextField inp = new JTextField();
		JLabel func = new JLabel("f(0)=0", SwingConstants.CENTER);
		f.setLocationRelativeTo(frame);
		f.setSize(500,220);
		f.setResizable(false);

		chrfnDesc.setBounds(15, 15, 150, 10);
		chrfnScr.setBounds(15, 30, 465, 40);
		chrfn.setFont(CODE);
		chrfn.setText("f(x) = "+p.toString());
		chrfn.setEditable(false);
		chrnoDesc.setBounds(15, 80, 150, 10);
		chrno.setBounds(150, 76, 200, 20);
		chrno.setFont(CODE);
		chrno.setText(Integer.toString(p.minNo()));
		chrno.setEditable(false);
		insDesc.setBounds(15, 90, 200, 50);
		inp.setBounds(149, 100, 330, 30);
		inp.setFont(CODE);
		func.setBounds(0, 131, 500, 50);
		func.setFont(CODE);

		inp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int x = Integer.parseInt(inp.getText());
					func.setText(String.format("f(%d) = %d", x, p.calculate(x)));
				} catch(Exception err){
					func.setText("Error.");
				}
			}
		});

		f.add(heartbroken);
		heartbroken.add(chrfnDesc);
		heartbroken.add(chrfnScr);
		heartbroken.add(chrnoDesc);
		heartbroken.add(chrno);
		heartbroken.add(insDesc);
		heartbroken.add(inp);
		heartbroken.add(func);

		f.setVisible(true);
	}
}
