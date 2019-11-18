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
