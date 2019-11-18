package structures;

import java.util.ArrayList;

/**
 * This class is a repository of sorting methods used by the interval tree.
 * It's a utility class - all methods are static, and the class cannot be instantiated
 * i.e. no objects can be created for this class.
 * 
 * @author runb-cs112
 */
public class Sorter {

	private Sorter() { }

	/**
	 * Sorts a set of intervals in place, according to left or right endpoints.  
	 * At the end of the method, the parameter array list is a sorted list. 
	 * 
	 * @param intervals Array list of intervals to be sorted.
	 * @param lr If 'l', then sort is on left endpoints; if 'r', sort is on right endpoints
	 */
	public static void sortIntervals(ArrayList<Interval> intervals, char lr) {

		if(intervals.isEmpty() || intervals.size() == 1){
			return;
		}

		ArrayList<Interval> mySortedIntervals = new ArrayList<Interval>();

		if(lr == 'l'){
			// Goes through every set in given intervals list
			for(int n = 0; n < intervals.size(); n++){

				Interval currentSet = intervals.get(n);
				// System.out.println("Current Set:" + currentSet);

				if(mySortedIntervals.isEmpty()){
					mySortedIntervals.add(currentSet);
					continue;
				}

				Interval firstSet = mySortedIntervals.get(0);
				Interval lastSet = mySortedIntervals.get(mySortedIntervals.size()-1);
				// System.out.println("mySortedIntervals:" + mySortedIntervals);
				// System.out.println("first Set:" + firstSet);
				// System.out.println("last Set:" + lastSet);

				// Adds current set to front of sorted intervals
				if(currentSet.leftEndPoint <= firstSet.leftEndPoint){
					mySortedIntervals.add(0, currentSet);
					continue;
				}

				// Adds current set to back of sorted intervals
				if(currentSet.leftEndPoint >= lastSet.leftEndPoint){
					mySortedIntervals.add(currentSet);
					continue;
				}

				// You know it's somewhere in the middle now
				for(int k=0; k < mySortedIntervals.size(); k++){
					if(currentSet.leftEndPoint <= mySortedIntervals.get(k).leftEndPoint){
						mySortedIntervals.add(k, currentSet);
						break;
					}
				}
			}
		} // end 'l' if

		if(lr == 'r'){
			// Goes through every set in given intervals list
			for(int n = 0; n < intervals.size(); n++){

				Interval currentSet = intervals.get(n);

				if(mySortedIntervals.isEmpty()){
					mySortedIntervals.add(currentSet);
					continue;
				}

				Interval firstSet = mySortedIntervals.get(0);
				Interval lastSet = mySortedIntervals.get(mySortedIntervals.size()-1);

				// Adds current set to front of sorted intervals
				if(currentSet.rightEndPoint <= firstSet.rightEndPoint){
					mySortedIntervals.add(0, currentSet);
					continue;
				}

				// Adds current set to back of sorted intervals
				if(currentSet.rightEndPoint >= lastSet.rightEndPoint){
					mySortedIntervals.add(currentSet);
					continue;
				}

				// You know it's somewhere in the middle now
				for(int k=0; k < mySortedIntervals.size(); k++){
					if(currentSet.rightEndPoint <= mySortedIntervals.get(k).rightEndPoint){
						mySortedIntervals.add(k, currentSet);
						break;
					}
				}
			}
		} // closing 'r' if

		intervals.clear();
		for(Interval aInterval : mySortedIntervals){
			intervals.add(aInterval);
		}
		// System.out.println("Sorted Interval List:" + intervals);
	}


	/**
	 * Given a set of intervals (left sorted and right sorted), extracts the left and right end points,
	 * and returns a sorted list of the combined end points without duplicates.
	 * 
	 * @param leftSortedIntervals Array list of intervals sorted according to left endpoints
	 * @param rightSortedIntervals Array list of intervals sorted according to right endpoints
	 * @return Sorted array list of all endpoints without duplicates
	 */
	public static ArrayList<Integer> getSortedEndPoints(ArrayList<Interval> leftSortedIntervals, ArrayList<Interval> rightSortedIntervals) {

		ArrayList<Integer> newList = new ArrayList<Integer>();
		if(leftSortedIntervals.isEmpty()){
			return newList;
		}
		
		// System.out.println("leftSortedInterval:" + leftSortedIntervals);

		// Checks if leftEndPoint is already in newList
		for(Interval iv : leftSortedIntervals){
			Integer k = iv.leftEndPoint;
			if(!newList.contains(k)){
				newList.add(k);
			}

		} // End for loop

		// System.out.println("newList with left sort:" + newList);

		// Adds in right end points into newList
		for(int n=0; n != rightSortedIntervals.size(); n++){

			// Checks if rightEndPoint is already in newList
			for(Interval iv : rightSortedIntervals){
				Integer k = iv.rightEndPoint;
				if(!newList.contains(k)){

					// Adds k to back
					if(k > newList.get(newList.size()-1)){
						newList.add(k);
						continue;
					}

					// Adds to front
					if(k < newList.get(0)){
						newList.add(0, k);
						continue;
					}

					// If you get here, you know it goes somewhere in the middle.
					for(int j = newList.size()-1; j >= 0; j--){
						if(k < newList.get(j)){
							newList.add(j, k);
							break;
						}
					}
				}
			}

		}	// End for loop

		// System.out.println("newList:" + newList);
		return newList;
	}
}
