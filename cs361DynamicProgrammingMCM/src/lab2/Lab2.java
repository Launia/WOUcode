package lab2;
/* This is Lab2 parts 1-4. *See Lab2A for parts 5 & 6* Our goal is to implement a Dynamic 
 * Programming version of the MCM algorithm as well as the Memoization version of the MCM 
 * algorithm and to print out some values of the array m.
 * 
 * Launia Davis
 * Lab 2
 * CS361 - Algorithms
 * February 25, 2018
 */

public class Lab2 {

	private int [][] m;
	private int [] p;
	final static int INFINITY = Integer.MAX_VALUE;
	
	/*
	 * This method determines the optimal number of scalar multiplications needed
	 * to compute a matrix chain product.
	 * Source : https://www.geeksforgeeks.org/dynamic-programming-set-8-matrix-chain-multiplication/
	 */
	static int myMatrixCO(int p[], int n)
	{
        /* For simplicity of the program, one extra row and one
        extra column are allocated in m[][].  0th row and 0th
        column of m[][] are not used */
        int m[][] = new int[n][n];
 
        int i, j, k, L, q;
 
        /* m[i,j] = Minimum number of scalar multiplications needed
        to compute the matrix A[i]A[i+1]...A[j] = A[i..j] where
        dimension of A[i] is p[i-1] x p[i] */
 
        // cost is zero when multiplying one matrix.
        for (i = 1; i < n; i++)
            m[i][i] = 0;
 
        // L is chain length.
        for (L=2; L<n; L++)
        {
            for (i=1; i<n-L+1; i++)
            {
                j = i+L-1;
                if(j == n) continue;
                m[i][j] = Integer.MAX_VALUE;
                for (k=i; k<=j-1; k++)
                {
                    // q = cost/scalar multiplications
                    q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                    if (q < m[i][j])
                        m[i][j] = q;
                }
            }
        }
 
        return m[1][n-1];
		}
	
	/*
	 * Method runs memoization version of MCM
	 * used pseudo code from book: Page 388 Dynamic Programming
	 */
	static int myLookUpChain(int m[][], int p[], int i, int j)
	{
		if (m[i][j] < INFINITY)
		{
			return m[i][j];
		}
		if (i == j)
		{
			m[i][j] = 0;
		}
		else
		{
			for (int k = i; k < j; k++)
			{
				int q = (myLookUpChain(m,p,i,k)) + (myLookUpChain(m,p,(k+1),j)) + (p[i-1]*p[k]*p[j]);
				if (q < m[i][j])
				{
					m[i][j] = q;
				}
			}
		}
		return m[i][j];
	}

	/*
	 * Main method will execute both versions of MCM.
	 */
	public static void main(String[] args) {
		
		Lab2 myLab2 = new Lab2();
		
		myLab2.m = new int [7][7];
		
//		myLab2.p = new int[] {40,20,30,10,30};  -- test case data - Top Right value: 26000 <-- https://www.geeksforgeeks.org/dynamic-programming-set-8-matrix-chain-multiplication/
		
		myLab2.p = new int[] {30,4,8,5,10,25,15};
		
		// call DP verion of MCM algorithm - returns the most efficient way to multiply
		
		int myExecutionSteps = myMatrixCO(myLab2.p, myLab2.p.length);
		System.out.println();
		System.out.println(" Top Right Value = " + myExecutionSteps);
		System.out.println();
		
		//initialize m array to infinity
		int n = myLab2.p.length-1;
		for (int i = 1; i <= n; i++)
		{
			for (int j = i; j <= n; j++)
			{
				myLab2.m[i][j] = INFINITY;
			}
		}
		
		// call memoization version of MCM algorithm
		
		int myRet = myLookUpChain(myLab2.m, myLab2.p, 1, n);
		System.out.println();
		
		
		// output the M array
		
		for (int i = 1; i <= n; i++)
		{
			
			// padding to make array line up: make assumption that m[i][j] value will be 5 digits or smaller
			
			for (int j = 1; j < i; j++)
			{
				String myStr = String.format("   m[%d][%d] = %5d", i, j, myLab2.m[i][j]);
				int myLen = myStr.length();
				for (int w = 1; w <= myLen; w++) 
				{
					System.out.print(" ");
				}
			}
	
			// output the current values of the array
			
			for (int j = i; j <= n; j++)
			{
				String myStr = String.format("   m[%d][%d] = %5d", i, j, myLab2.m[i][j]);
				System.out.print(myStr);
			}
			System.out.println();
		}
	}

}
