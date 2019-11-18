package structures;

import java.util.*;

/**
 * Encapsulates an interval tree.
 * 
 * @author runb-cs112
 */
public class IntervalTree {

	/**
	 * The root of the interval tree
	 */
	IntervalTreeNode root;

	/**
	 * Constructs entire interval tree from set of input intervals. Constructing the tree
	 * means building the interval tree structure and mapping the intervals to the nodes.
	 * 
	 * @param intervals Array list of intervals for which the tree is constructed
	 */
	public IntervalTree(ArrayList<Interval> intervals) {

		// make a copy of intervals to use for right sorting
		ArrayList<Interval> intervalsRight = new ArrayList<Interval>(intervals.size());
		for (Interval iv : intervals) {
			intervalsRight.add(iv);
		}

		// rename input intervals for left sorting
		ArrayList<Interval> intervalsLeft = intervals;

		// sort intervals on left and right end points
		Sorter.sortIntervals(intervalsLeft, 'l');
		Sorter.sortIntervals(intervalsRight,'r');

		// get sorted list of end points without duplicates
		ArrayList<Integer> sortedEndPoints = Sorter.getSortedEndPoints(intervalsLeft, intervalsRight);

		// build the tree nodes
		root = buildTreeNodes(sortedEndPoints);

		// map intervals to the tree nodes
		mapIntervalsToTree(intervalsLeft, intervalsRight);
	}

	/**
	 * Builds the interval tree structure given a sorted array list of end points.
	 * 
	 * @param endPoints Sorted array list of end points
	 * @return Root of the tree structure
	 */
	public static IntervalTreeNode buildTreeNodes(ArrayList<Integer> endPoints) {

		if(endPoints.isEmpty()){
			return null;
		}

		Queue<IntervalTreeNode> Q = new Queue<IntervalTreeNode>();

		for(Integer p : endPoints){
			IntervalTreeNode T = new IntervalTreeNode(p, p, p);
			T.leftIntervals = new ArrayList<Interval>();
			T.rightIntervals = new ArrayList<Interval>();
			Q.enqueue(T);
		}

		do{		// Begin step 6
			int s = Q.size();
			if(s == 1){
				IntervalTreeNode T = Q.dequeue();
				return T;
			}

			int tmpS = s;
			while(tmpS > 1){
				IntervalTreeNode T1= Q.dequeue();
				IntervalTreeNode T2 = Q.dequeue();
				float v1 = T1.maxSplitValue;
				float v2 = T2.minSplitValue;
				float x = (v1+v2)/2;
				IntervalTreeNode N = new IntervalTreeNode(x,v2,v1);	// N is new node
				N.leftIntervals = new ArrayList<Interval>();
				N.rightIntervals = new ArrayList<Interval>();
				N.leftChild = T1;
				N.rightChild = T2;
				Q.enqueue(N);
				tmpS = tmpS - 2;
			}

			if(tmpS == 1){
				IntervalTreeNode x = Q.dequeue();
				Q.enqueue(x);
			}
		} while(true);

	}

	/**
	 * Maps a set of intervals to the nodes of this interval tree. 
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 */
	public void mapIntervalsToTree(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {

		if(leftSortedIntervals.isEmpty()){
			return;
		}

		for(Interval j: leftSortedIntervals){
			IntervalTreeNode N = root;
			while(N != null){
				if(j.contains(N.splitValue)){
					N.leftIntervals.add(j);
					break;
				}
				if(N.splitValue < j.leftEndPoint){
					N = N.rightChild;
				}
				if(N.splitValue > j.rightEndPoint){
					N = N.leftChild;
				}
			}
		} 	// End leftSortedInterval for loop

		for(Interval j: rightSortedIntervals){
			IntervalTreeNode N = root;
			while(N != null){
				if(j.contains(N.splitValue)){
					N.rightIntervals.add(j);
					break;
				}
				if(N.splitValue < j.leftEndPoint){
					N = N.rightChild;
				}
				if(N.splitValue > j.rightEndPoint){
					N = N.leftChild;
				}
			}
		}	// End rightSortedIntervals for loop

	}	// End method

	/**
	 * Gets all intervals in this interval tree that intersect with a given interval.
	 * 
	 * @param q The query interval for which intersections are to be found
	 * @return Array list of all intersecting intervals; size is 0 if there are no intersections
	 */
	public ArrayList<Interval> findIntersectingIntervals(Interval q) {

		return queryThing(root, q);
	}

	private ArrayList<Interval> queryThing(IntervalTreeNode N, Interval q){

		ArrayList<Interval> resultList = new ArrayList<Interval>();

		IntervalTreeNode R = N;
		float splitVal = R.splitValue;
		ArrayList<Interval> LList = R.leftIntervals;
		ArrayList<Interval> RList = R.rightIntervals;
		IntervalTreeNode Lsub = R.leftChild;
		IntervalTreeNode Rsub = R.rightChild;

		if(R.leftChild == null && R.rightChild == null){
			return resultList;
		}

		if(q.contains(splitVal)){
			resultList.addAll(LList);
			resultList.addAll(queryThing(Rsub, q));
			resultList.addAll(queryThing(Lsub, q));
		}
		else if(splitVal < q.leftEndPoint){
			int i = RList.size()-1;
			while(i >= 0){
				if((RList.get(i).intersects(q) == true)){
					resultList.add(RList.get(i));
				}
				i--;
			}
			resultList.addAll(queryThing(Rsub, q));
		}
		else if(splitVal > q.rightEndPoint){
			int i = 0;
			while(i < LList.size()){
				if(LList.get(i).intersects(q) == true){
					resultList.add(LList.get(i));
				}
				i++;
			}
			resultList.addAll(queryThing(Lsub,q));
		}
		
		return resultList;

	} // End method

	/**
	 * Returns the root of this interval tree.
	 * 
	 * @return Root of interval tree.
	 */
	public IntervalTreeNode getRoot() {
		return root;
	}
}

