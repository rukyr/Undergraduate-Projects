package apps;

import structures.*;


import java.util.ArrayList;





public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		// Step 1: Create an empty PartialTreeList
				PartialTreeList list = new PartialTreeList();
				// Step 2: Perform steps for each vertex v in the graph
				for (int i = 0; i < graph.vertices.length; i++)
				{
					// Create a tree / mark v as belonging to the tree
					// Heap was created in constructor of PartialTree
					PartialTree tree = new PartialTree(graph.vertices[i]);
					// Insert all of the arcs connected to v
					Vertex.Neighbor curr = graph.vertices[i].neighbors;	// iterator
					while (curr != null)
					{
						PartialTree.Arc arc = new PartialTree.Arc(graph.vertices[i], curr.vertex, curr.weight);
						tree.getArcs().insert(arc);
						curr = curr.next;
					}
					// Add the partial tree T to the list
					list.append(tree);
				}	// end of for() loop

				// Return list with single-vertex partial trees
				return list;
	}
	
	

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {

		ArrayList<PartialTree.Arc> myMST = new ArrayList<PartialTree.Arc>();
		if(ptlist == null || /*graph == null ||*/ ptlist.size()<=1){
			return myMST;
		}

		while(ptlist.size() > 1){
			PartialTree PTX = ptlist.remove();
			MinHeap<PartialTree.Arc> PQX = PTX.getArcs();
			//PartialTree.Arc alpha = null;

			// Basically, this just gets the alpha you need to use.
			boolean suh = true;
			PartialTree.Arc arc = null;
			Vertex x1 = null;
			Vertex x2 = null;
			while (suh) {
				arc = PTX.getArcs().getMin();
				PTX.getArcs().deleteMin();
				x1 = arc.v1;
				x2 = arc.v2;
				Vertex check = x1.getRoot();
				Vertex check2 = x2.getRoot();
				if (check.equals(check2)) {
					continue;
				} else {
					suh = false;
				}
					
			}
			/*while( ! PQX.isEmpty()){
				alpha = PQX.deleteMin();
				if( ! isVertexInTree(PTX, alpha.v1) || ! isVertexInTree(PTX,alpha.v2)){
					break;
				}
			}*/
		
			myMST.add(arc);
			
			PartialTree PTY = ptlist.removeTreeContaining(arc.v2);
			PTX.merge(PTY);
			ptlist.append(PTX);
		}

		return myMST;
		
	}
}
