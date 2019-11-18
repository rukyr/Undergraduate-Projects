public class WebReg {
	
	public static Course lookupCourseByName(Course[] catalog, String courseName) {
		int n = catalog.length;
		int count = -1;
		for (int i = 0; i < n; i ++) {
			String name = catalog[i].getName();
			String lowercasename = name.toLowerCase();
			if (lowercasename.equals(courseName.toLowerCase())){
				count = i;
			} 
		}
		if (count != -1) {
			return catalog[count];
		} else {
		return null;
		}
	
	}
	
	public static Course[] commonCourses(Student one, Student two) {
		int counter = 0;
		Course[] a = one.getSchedule();
		Course[] b = two.getSchedule();
		//Course[] c;
		//int count2 = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if ((a[i]!=null) && (b[j]!=null) && (a[i] == b[j])){
					counter++;
					//c[i] = a[i];
					break;
				}
				
			}
			
		}
		
		if (counter == 0) {
			return null;
		} else {
			Course[] c = new Course[counter];
			int holder = 0;
			for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < b.length; j++) {
					if ((a[i]!=null) && (b[j]!=null) && (a[i] == b[j])){
						//counter++;
						c[holder] = a[i];
						holder++;
						break;
					}
				}
			}
			return c;
		}
	}
	
	
	public static void sortByNumber(Course[] catalog) {
		//static void selectionSort(int[] A) {
		      // Sort A into increasing order, using selection sort
		      
		for (int lastPlace = catalog.length-1; lastPlace > 0; lastPlace--) {
		         // Find the largest item among A[0], A[1], ...,
		         // A[lastPlace], and move it into position lastPlace 
		         // by swapping it with the number that is currently 
		         // in position lastPlace.
		         
			int maxLoc = 0;  // Location of largest item seen so far.
		      
			for (int j = 1; j <= lastPlace; j++) {
				if (catalog[j].getDepartment() > catalog[maxLoc].getDepartment() || (catalog[j].getDepartment() == catalog[maxLoc].getDepartment() && catalog[j].getCourseNumber() > catalog[maxLoc].getCourseNumber())) {
		               // Since A[j] is bigger than the maximum we've seen
		               // so far, j is the new location of the maximum value
		               // we've seen so far.
				maxLoc = j;  
				}
		         
			}
		      
		    Course temp = catalog[maxLoc];  // Swap largest item with A[lastPlace].
		    catalog[maxLoc] = catalog[lastPlace];
		    catalog[lastPlace] = temp;
		      
		   }  // end of for loop
		   
	}
	
	public static void sortByTime(Course[] catalog) {
		//static void selectionSort(int[] A) {
	      // Sort A into increasing order, using selection sort
		char []days = {'M', 'T', 'W', 'H', 'F', 'S'};
		int dayValue1= -1;
		int dayValue2= -1;
		for (int lastPlace = catalog.length-1; lastPlace > 0; lastPlace--) {
	         // Find the largest item among A[0], A[1], ...,
	         // A[lastPlace], and move it into position lastPlace 
	         // by swapping it with the number that is currently 
	         // in position lastPlace.
	         
			int maxLoc = 0;  // Location of largest item seen so far.
	      
			for (int j = 1; j <= lastPlace; j++) {
				for (int i =0; i <= 5; i++) { //convert day value to integer equivalent (0-5)
					if (catalog[j].getPeriod().getDay() == days[i]) {
						dayValue1 = i;
					}
					if (catalog[maxLoc].getPeriod().getDay() == days[i]) {
						dayValue2 = i;
					}
				}
				if (dayValue1 != -1 && dayValue2 != -1 && (dayValue1 > dayValue2 || (dayValue1 == dayValue2 && catalog[j].getPeriod().getTimeSlot() > catalog[maxLoc].getPeriod().getTimeSlot()))) {
						//(catalog[j].getDepartment() == catalog[maxLoc].getDepartment() && catalog[j].getCourseNumber() > catalog[maxLoc].getCourseNumber())) {
	               // Since A[j] is bigger than the maximum we've seen
	               // so far, j is the new location of the maximum value
	               // we've seen so far.
					maxLoc = j;  
			}
	         
		}
	      
	    Course temp = catalog[maxLoc];  // Swap largest item with A[lastPlace].
	    catalog[maxLoc] = catalog[lastPlace];
	    catalog[lastPlace] = temp;
	      
	   }  // end of for loop
	}
    
	
	public static Course[] lookupCoursesByDept(Course[] catalog, int department) {
		int n = catalog.length;
		int count = 0;
		int count2 = 0;
		
		for (int j = 0; j < n; j++) {
			if (catalog[j].getDepartment() == department) {
				count++;
			}
		}
		Course[] departmentCollection = new Course[count];
		
		if (count == 0){
			return null;
		} else {
		for (int j = 0; j < n; j++) {
			if (catalog[j].getDepartment() == department) {
				departmentCollection[count2] = catalog[j];
				count2++;
			}
		}
		return departmentCollection;
		}
	}
	
	public static int countCredits(Student s) {
		int creditCount=0;
		Course[] studentSchedule = s.getSchedule();
		int scheduleLength = studentSchedule.length;
		for (int k = 0; k < scheduleLength; k++) {
			if (studentSchedule[k] != null) {
				creditCount = creditCount + studentSchedule[k].getCredits();
			}
		}
		return creditCount;
	}
	
	public static boolean addCourse(Student s, Course c) {
		Course[] studentSchedule = s.getSchedule();
		int scheduleLength = studentSchedule.length;
		int count = 0;
		int count2= 0;
		//check course size, max size 20
		Student[] classRoster = c.getRoster();
		int classSize = classRoster.length;
		for (int l = 0; l <  classSize; l ++) {
			if (classRoster[l] != null) {
				count++;
			}
		
		}
		if (count == 20) {
			return false;
		}
		//check student's schedule if he/she is registered for max six classes
		for (int m = 0; m <  scheduleLength; m ++) {
			if (studentSchedule[m] != null) {
				count2++;
			}
		}
		if (count2 == 6) {
			return false;
		}
		//check for class conflicts
		for (int n = 0; n < scheduleLength; n++) {
			if (studentSchedule[n] != null) {
				Course beta = studentSchedule[n];
				Period alpha = beta.getPeriod();
				if (alpha.compareTo(c.getPeriod()) == 0) {
			   		return false;
			   	}
			}
		
			
		}
		for (int o = 0; o < scheduleLength; o++) {
		   	if (studentSchedule[o]==null) {
		   		studentSchedule[o] = c;
		   		Student[] a = c.getRoster();
		   		int k = a.length;
		   		for (int r =0; r<k; r++) {
		   			if (a[r] == null) {
		   				a[r] = s;
		   				break;
		   			}
 		   		}
		   			break;
		   		
	    	}
		}
		return true;
		
		
	}
	
	public static boolean dropCourse(Student s, Course c) {
		Course[] studentSchedule = s.getSchedule();
		int schedLength = studentSchedule.length;
		int indexOfClassInSchedule = -1;
		
		for (int p =0; p < schedLength; p++) {
			if (studentSchedule[p] != null) {
				if (studentSchedule[p] == c && p != schedLength -1) {
			
					//indexOfClassInSchedule = p;
					Student[] classSize = studentSchedule[p].getRoster();
					
					int size = classSize.length;
					for (int g = 0; g < size; g++) {
						if (classSize[g] != null) {
							if (s.getId() == classSize[g].getId() && g!= size-1) {
								for (int d = g; d < size-1; d++) {
									classSize[d] = classSize[d+1];
								}
							classSize[19] = null;
							} else if (s.getId() == classSize[g].getId() && g == size-1) {
								classSize[g] = null;
							}
						}
					}	
					
					
					for ( int a = p ; a < schedLength - 1 ; a++ ) {
						studentSchedule[ a ] = studentSchedule[ a + 1 ]; 
				}
					studentSchedule[5] = null;
					return true;
				} else if (studentSchedule[p] == c && p == schedLength -1) {
					//indexOfClassInSchedule = p;
					Student[] classSize = studentSchedule[p].getRoster();
					
					int size = classSize.length;
					for (int g = 0; g < size; g++) {
						if (classSize[g] != null) {
							if (s.getId() == classSize[g].getId() && g!= size-1) {
								for (int d = g; d < size-1; d++) {
									classSize[d] = classSize[d+1];
								}
							classSize[19] = null;
							} else if (s.getId() == classSize[g].getId() && g == size-1) {
								classSize[g] = null;
							}
						}
					}
					studentSchedule[p] = null;
					return true;
				}
			}
		}		
		
		
		return false;
		
		
	}
}
