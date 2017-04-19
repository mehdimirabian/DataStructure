/*
 * PA1: Recursion(Extrema)
 * Writer: Mohammad Mirabian
 * Student ID#: 1377020
 * CruzId: mmirabia
 * File name: Extrema.java
 * Date: 04/09/2015
 */



public class Extrema {
	


	//helper function to find the max between two integers.
	static int max(int p, int r) {

		if (p < r)
			return r;
		else
			return p;

	}
	
	//helper method to find the min between two integers.
	static int min(int p, int r) {

		if (p > r)
			return r;
		else
			return p;

	}
	
	

    //This method returns the largest value in a given array A
    static int maxArray(int[] A, int p, int r) {
        if (r - p <= 1) {
            return max(A[p], A[r]);
        } else {
            int q = (p + r) / 2;
            int left = maxArray(A, p, q);
            int right = maxArray(A, q + 1, r);
            return max(left, right);

        }
        
    }
    

    //This method returns the smallest value in a given array A
    static int minArray(int[] A, int p, int r) {
        if (r - p <= 1) {
            return min(A[p], A[r]);
        } else {
            int q = (p + r) / 2;
            int left = minArray(A, p, q);
            int right = minArray(A, q + 1, r);
            return min(left, right);

        }
        
    }
		


	
	   public static void main(String[] args){
    	 int[] B = {-1, 2, 6, 3, 9, 2, -3, -2, 11, 5, 7};
    	 System.out.println( "max = " + maxArray(B, 0, B.length-1) );
    	 System.out.println( "min = " + minArray(B, 0, B.length-1) );
    	 }
}
