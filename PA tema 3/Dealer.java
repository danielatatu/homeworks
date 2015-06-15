import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Dealer {

	/**
	 * N = numarul de noduri din graf
	 * 
	 * M = numarul de muchii din graf
	 * 
	 * edges = lista ce retine muchiile din graf
	 * 
	 * cost = matrice de costuri; pe diagonala are profitul fiecarui nod
	 * 
	 * epsilon = constanta utilizata pentru a determina ca doua raporturi
	 * 			 sunt egale cu o eroare de 10^-5
	 */
	private static int N, M;
	private static ArrayList<Edge> edges;
	private static double [][] cost;
	private static final double epsilon = 0.000001;

	/**
	 * Functie utilizata pentru citirea datelor de intrare si initializari.
	 */
	private static void init() {
		try {
			Scanner sc = new Scanner (new File("negustori.in"));
			N = sc.nextInt();
			M = sc.nextInt();

			cost = new double [N][N];
			for (int i = 0; i < N; ++i)
				cost[i][i] = sc.nextInt();

			int fst, snd, c;
			edges = new ArrayList<Edge>(M);

			for (int i = 0; i < M; ++i) {
				fst = sc.nextInt();
				snd = sc.nextInt();
				edges.add(new Edge(fst, snd));
				
				c = sc.nextInt();
				cost[fst][snd] = c;
			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Input file not found!");
		}
	}
	
    /**
     * Functie utilizata pentru a verifica daca exista un ciclu negativ in graf.
     * Implementeaza algoritmul Bellman Ford.
     */
    private static boolean BellmanFord (double r) {
    	
    	// initializari
    	double [] distance = new double[N];

    	for (int node = 1; node < N; ++node)
    		// daca nodul este vecin cu sursa
    	    if (cost[0][node] != 0)
    	    	// distanta initiala va fi chiar w [sursa, nod]
    	    	// nota : aceasta formula este data de algoritmul explicat mai jos
    	        distance[node] = r * cost[0][node] - cost[node][node];
    	    else
    	        distance[node] = Double.MAX_VALUE;
    	// distanta pana la sursa este 0
    	distance[0] = 0;
    	 
    	// se efectueaza relaxari succesive
    	// cum in initializare se face o relaxare
    	// (daca exista drum direct de la sursa la nod => d[nod] = w[sursa, nod])
    	// mai sunt necesare N-2 relaxari
    	boolean cont = false;
    	for (int i = 0; i < N-2; ++i) {
    	    for (Edge edge : edges) {
    	    	double d = distance[edge.fst] + r * cost[edge.fst][edge.snd] - cost[edge.snd][edge.snd];
    	        if (distance[edge.snd] > d) {
    	            distance[edge.snd] = d;
    	            cont = true;
    	        }
    	    }
    	    // daca nu s-a efectuat nicio relaxare inainte de a ajunge la pasul N-2
    	    // => nu exista ciclu negativ in graf
    	    if (cont == false)
    	    	return false;
    	    else
    	    	cont = false;
    	}

    	// daca se mai pot face relaxari dupa pasul N-2
    	// => exista ciclu negativ in graf
    	for (Edge edge : edges)
    	   if (distance[edge.snd] > distance[edge.fst] + r * cost[edge.fst][edge.snd] - cost[edge.snd][edge.snd])
    	     return true;

    	return false;
    }
    
    public static void maxProfit () {
    
    	// pornim de la o limita inferioara si o limita superioara
    	// ale raportului cerut (profit / cost)
    	double low_r = 0, upp_r = 500;
    	double temp;
    	// cu ajutorul cautarii binare ne apropiem de solutie pana cand
    	// diferenta dintre 2 raporturi gasite consecutiv devine mai mica decat epsilon
	    double max_r = (low_r + upp_r) / 2;

	    do {	    	
	    	temp = max_r;

	    	// daca exista ciclu negativ in graf
	    	// actualizam limita inferioara (raportul real este mai mare)
	    	// daca nu
	    	// actualizam limita superioara (raportul real este mai mic)
		    if ( BellmanFord (max_r) )
		    	low_r = max_r;
		    else
		    	upp_r = max_r;
		    
		    max_r = (low_r + upp_r) / 2;
	    }
	    while( Math.abs (temp - max_r) > epsilon);
	    
	    // printam rezultatul cu 5 zecimale
		try {
			PrintWriter wr = new PrintWriter("negustori.out");
			DecimalFormat custom = new DecimalFormat("#.00000");
			wr.println(custom.format(max_r));
			wr.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Output file not found!");
		}
    }
    
    public static void main(String[] args) {
		init();
		maxProfit();
	}
}

/**
 * Clasa utilizata pentru a retine cele 2 noduri ale unei muchii.
 */
class Edge {
	public int fst;
	public int snd;
	
	Edge (int fst, int snd) {
		this.fst = fst;
		this.snd = snd;
	}
}