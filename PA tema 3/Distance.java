import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Distance {

	/**
	 * N = numarul de noduri din graf
	 * 
	 * edges = lista ce retine pe pozitia i lista de adiacenta a nodului i
	 * 
	 * source, destination = nodurile intre care se calculeaza distanta
	 * 
	 * distance = distanta dintre sursa si destinatie
	 * 
	 * capacity = matricea de capacitati asociata grafului
	 */
	private static int N;
	private static ArrayList<ArrayList<Integer>> edges;
	private static int [][] capacity;
	private static int source;
	private static int destination;
	private static int distance = 0;

	/**
	 * Functie utilizata pentru citirea datelor de intrare si initializari.
	 */
	private static void init() {
		try {
			Scanner sc = new Scanner (new File("prieteni.in"));
			N = sc.nextInt();

			edges = new ArrayList<ArrayList<Integer>>(N);
			for (int i = 0; i < N; ++i)
				edges.add (new ArrayList<Integer>());

			int friends;
			for (int i = 0; i < N; ++i)
				for (int j = 0; j < N; ++j) {
					friends = sc.nextInt();
					if (friends == 1)
						edges.get(i).add(j);	
				}
			source = sc.nextInt();
			destination = sc.nextInt();
			sc.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Input file not found!");
		}

		/* capacity[i][j] = 1, daca muchia i-j face parte dintr-un drum
		 * 					   ce leaga sursa de destinatie si are 
		 * 					   lungimea mai mica sau egala cu 3
		 * capacity[i][j] = 0, altfel
		 */
		capacity = new int[N][N];
		for (int first : edges.get(source))
			for (int second : edges.get(first))
				if (second == destination) {
					capacity[source][first] = 1;
					capacity[first][destination] = 1;
				}
				else if (edges.get(second).contains(destination)) {
        			capacity[source][first] = 1;
        			capacity[first][second] = 1;
        			capacity[second][destination] = 1;
				}
	}
	
	/**
	 * Functie utilizata pentru afisarea datelor de iesire.
	 */
	private static void end() {
		try {
			PrintWriter wr = new PrintWriter("prieteni.out");
			wr.println(distance);
			wr.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("Output file not found!");
		}
	}

    /**
     * Functie apelata in mod repetat pentru a determina drumuri de ameliorare
     * folosind BFS si pentru a le satura.
     * 
     * Functia returneaza false cand un astfel de drum nu mai poate fi determinat.
     */
	public static boolean execute() {

		/* vector de parinti, necesar pentru refacerea caii de la sursa la destinatie,
		 * dar si pentru a determina daca un nod a fost sau nu introdus in coada
		 */
		int [] parents = new int[N];
		for (int i = 0; i < N; i++)
			parents[i] = -1;

		/* coada utilizata in cadrul algoritmului BFS */
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(source);

		/* cat timp mai am noduri de prelucrat si nu am ajuns la destinatie */
		while ( parents[destination] == -1 && !q.isEmpty() ) {
			/* extrag un nod din coada */
			int node = q.poll();
			
			/* verific fiecare vecin nevizitat si daca intre nodul extras si acest vecin
			 * este o capacitate mai mare decat 0, il adaug in coada */
			for (int neigh : edges.get(node))
				if (capacity[node][neigh] > 0 && parents[neigh] == -1 && neigh != source) {
					parents[neigh] = node;
					q.add(neigh);
				}
		}

	    /* daca nu s-a atins destinatia, atunci nu mai exista drumuri 
	     * intre sursa si destinatie de lungime maxim 3 */
		if (parents[destination] == -1)
			return false;

	    /* reconstitui drumul de la destinatie spre sursa */
		ArrayList<Integer> path = new ArrayList<Integer>();
		for (int node = destination; node != -1; node = parents[node])
			path.add(node);
		Collections.reverse(path);

		/* saturez drumul gasit */
		saturate_path(path);
		return true;
	}

	/**
	 * Functie utilizata pentru saturarea unui drum de ameliorare.
	 * @param path
	 * 			calea ce urmeaza a fi saturata
	 */
	private static void saturate_path(ArrayList<Integer> path) {
		/* in urma saturarii, capacitatea de pe fiecare muchie a caii va scadea cu 1 */
		for (int i = 0; i < path.size() - 1; ++i) {
			int u = path.get(i), v = path.get(i + 1);
			capacity[u][v] -= 1;
			capacity[v][u] += 1; 
		}

		/* saturarea unei cai este echivalenta cu eliminarea unui nod din graf
		 * in cazul acestei probleme, deci incrementam distanta calculata */
		distance++;
	}

	public static void main(String[] args) {
		init();
		while(execute());
		end();
	}
}
