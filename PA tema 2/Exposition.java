import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author Daniela-Florentina Tatu, 325CA
 */
public class Exposition {

/**
 * N = numarul de noduri din graf
 * 
 * edges = lista ce retine pe pozitia i lista de adiacenta a nodului i
 * 
 * degree = vector ce retine pe pozitia i gradul extern al nodului i
 */
	private static int N;
	private static ArrayList<ArrayList<Integer>> edges;
	private static int[] degree;
	private static PrintWriter wr;

	/**
	 * Functie utilizata pentru citirea datelor de intrare si initializari.
	 */
	public static void init() {
		try {
			Scanner sc = new Scanner (new File("expozitie.in"));
			N = sc.nextInt();
			edges = new ArrayList<ArrayList<Integer>>(N);
			for (int i = 0; i < N; i++)
				edges.add (new ArrayList<Integer>());

			int x, y;
			while (sc.hasNextInt()) {
				x = sc.nextInt();
				y = sc.nextInt();
				edges.get(x).add(y);
				edges.get(y).add(x);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.err.println("Input file not found!");
		}

		degree = new int[N];
		try {
			wr = new PrintWriter("expozitie.out");
		} catch (FileNotFoundException e) {
			System.err.println("Output file not found!");
		}
	}

	/**
	 * Functie utilizata pentru verificarea finala si inchiderea fisierului de output.
	 */
	public static void end () {
		// daca gasesc un nod cu grad impar rescriu fisierul, afisand 'Imposibil'
		for (int i = 0; i < N; i++)
			if (degree[i] % 2 == 1) {
				try {
					wr = new PrintWriter("expozitie.out");
					wr.println("Imposibil");
				} catch (FileNotFoundException e) {
					System.err.println("Output file not found!");;
				}
				break;
			}
		// daca toate gradele sunt pare, atunci solutia afisata este corecta
		wr.close();
	}

	/**
	 * Functie ce executa pasii algoritmului:
	 * 	- gaseste nodul neprocesat cu numar minim de vecini si care inca are muchii neorientate
	 *  - daca nu mai sunt noduri de procesat, incheie algoritmul
	 *  - procesez nodul cu numar minim de vecini, adica:
	 *  	-> verific daca gradul sau extern gasit prin procesarea altor noduri adunat cu
	 *  	numarul de muchii neorientate pe care le are este par sau impar
	 *  	-> daca este par, orientez toate muchiile catre exterior
	 *  	-> daca este impar, orientez o muchie catre interior, iar celelalte catre exterior
	 *  	-> actualizez datele celorlalte noduri implicate (vecinii)
	 *  	-> marchez nodul ca fiind procesat cu ajutorul gradului sau extern
	 */
	public static void execute() {

		while (true) {
			int currentNode = -1, noEdges;
			int min = Integer.MAX_VALUE;

			// gasesc nodul cu numar minim de vecini
			for (int i = 0; i < N; i++)
				// daca nu a mai fost procesat
				if (degree[i] != -2) {
					noEdges = edges.get(i).size();
					// si inca are muchii neorientate
					if (noEdges != 0 && noEdges <= min) {
						min = noEdges;
						currentNode = i;
					}
				}
	
			// daca nu mai am niciun nod de procesat
			if (min == Integer.MAX_VALUE) break;

			// numarul de muchii neorientate ale nodului curent
			noEdges = min;

			// daca are deja un grad extern par dat de procesarea celorlalte noduri
			// si mai are un numar par de muchii neorientate
			// sau
			// are grad extern impar si un numar impar de muchii neorientate
			//
			// => fac toate muchiile sale externe
			if ( (noEdges + degree[currentNode]) % 2 == 0 ) {
				// pentru fiecare vecin (catre care e o muchie neorientata)
				for (Integer v : edges.get(currentNode)) {
					// afisez muchia externa
					wr.println(currentNode + " " + v);
					// sterg nodul curent din lista de adiacenta a vecinului
					edges.get(v).remove((Object)currentNode);
				}
			}
			// daca suma dintre gradul extern si numarul de muchii neorientate
			// este impara => fac toate muchiile sale externe
			// in afara de una, care va fi interna
			else {
				int v, i;
				for (i = 0; i < noEdges - 1; i++) {
					// pentru fiecare vecin (catre care e o muchie neorientata)
					v = edges.get(currentNode).get(i);
					// afisez muchia externa
					wr.println(currentNode + " " + v);
					// sterg nodul curent din lista de adiacenta a vecinului
					edges.get(v).remove((Object)currentNode);
				}
				// catre ultimul vecin voi avea o muchie interna
				v = edges.get(currentNode).get(i);
				wr.println(v + " " + currentNode);
				// gradul extern al vecinului creste
				degree[v]++;
				// sterg nodul curent din lista de adiacenta a vecinului
				edges.get(v).remove((Object)currentNode);
			}
			// nodul curent a fost procesat, deci sigur are grad extern par
			// am utilizat -2 pentru a le deosebi pe cele procesate de celelalte
			// in cadrul cautarii nodului cu numar minim de vecini
			degree[currentNode] = -2;
		}
	}

	public static void main (String[] args) {
		init();
		execute();
		end();
	}
}
