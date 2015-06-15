/**
 * student: Tatu Daniela Florentina
 * grupa: 325CA
 */

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Sum {

	/**
	 * Datele de intrare.
	 */
	private static int N;
	private static long K;
	private static int[] v;

	/**
	 *  Pozitia minima, respectiv maxima, pe care se poate afla o suma.
	 *  Acest interval apare datorita sumelor duplicate
	 *  (v[i] + v[j] si v[j] + v[i] sau alte cazuri in care obtin aceeasi suma).
	 */
	private static long minPos;
	private static long maxPos;

	/**
	 * Suma finala.
	 */
	private static long finalSum;

	/**
	 * Functie de citire a datelor.
	 */
	public static void readData () {
		try {
			Scanner sc = new Scanner(new File("patrat.in"));

			N = sc.nextInt();
			K = sc.nextLong();

			v = new int[N];
			for (int i = 0; i < N; i++)
				v[i] = sc.nextInt();

			sc.close();
			
			/**
			 * Sortam vectorul de numere (necesitatea acestei operatii este explicata in readme). 
			 */
			Arrays.sort(v);
		}
		catch (FileNotFoundException e) {
			System.out.println("Input file not found!");
		}
		catch (InputMismatchException e) {
			System.out.println("File contains something that is not a number!");
		}
	}
	
	/**
	 * Functie de scriere a sumei finale in fisier.
	 */
	public static void writeData () {
		try {
			PrintWriter wr = new PrintWriter("patrat.out");
			wr.println(finalSum);
			wr.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Output file not found!");
		}
	}
	
	/**
	 * Functie care primeste ca parametru o suma si calculeaza 
	 * pozitia minima si pozitia maxima pe care se poate afla.
	 * (mai multe detalii despre modul de functionare in readme)
	 */
	public static void evaluate (long sum) {
		long pos = 1;
		int j = 0, k = 0;

		for (int i = N-1; i >= 0; --i) {
			while (j < N && v[i] + v[j] <= sum) {
				if (v[i] + v[j] == sum)
					k++;
				j++;
			}
			pos += j;
		}
		minPos = pos - k;
		maxPos = pos - 1;
	}
	
	/**
	 * Functie care simuleaza cautarea binara pe multimea de sume.
	 * Calculeaza o suma medie si verifica daca aceasta se afla pe o 
	 * pozitie mai mica sau mai mare decat K.
	 * 
	 * Apelul recursiv se opreste in momentul in care diferenta dintre cele 2
	 * sume primite ca parametri este 1 sau cand K se afla intre pozitia minima si 
	 * pozitia maxima a sumei medii generate.
	 */
	public static void getFinalSum (long minSum, long maxSum) {
		if (maxSum - minSum == 1)
			finalSum = maxSum;
		else {
			long sum = (minSum + maxSum) / 2;
			evaluate(sum);
			
			if (minPos > K)
				getFinalSum (minSum, sum);
			else if (maxPos < K)
				getFinalSum (sum, maxSum);
			else
				finalSum = sum;
		}
	}
	
	public static void main(String[] args) {
		readData();
		getFinalSum ( 2 * v[0], 2 * v[N-1] );
		writeData();
	}
}
