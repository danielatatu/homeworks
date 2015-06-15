import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Daniela-Florentina Tatu, 325CA
 */
public class KColoring {

/**
 * N = numarul de noduri din graf
 * M = numarul de muchii din graf
 * C = numarul de culori disponibile
 * 
 * edges = matricea de adiacenta a grafului
 * 		   retine pe pozitia [i][j]: 
 * 			- 0 daca nu exista muchie intre nodurile i si j
 * 			- 1 daca exista muchie
 * 
 * costs = matrice ce retine pe pozitia [i][j] costul intre culorile i si j
 * 
 * selected = matrice cu N linii si C coloane ce retine pe linia i ce culori au fost deja
 * 			  selectate pentru nodul i in cadrul algoritmului de backtracking
 * 
 * tempSol = vector ce retine pe pozitia i culoarea asociata temporar nodului i
 * 
 * finalSol = solutia finala; pe pozitia i se afla culoarea asociata nodului i
 * 
 * tempCost = costul temporar, in functie de culorile asignate deja
 * 
 * minCost = costul minim final; se actualizeaza la gasirea unei noi solutii cu un cost mai mic
 */
private static int N, M, C, edges[][], costs[][];
private static int selected[][], tempSol[], finalSol[];
private static int tempCost, minCost;

/**
 * Functie utilizata pentru citirea datelor de intrare si initializari.
 */
public static void init() {
	try {
		Scanner sc = new Scanner (new File("kcol.in"));
		N = sc.nextInt();
		M = sc.nextInt();
		C = sc.nextInt();
		int x, y, z;
	
		edges = new int[N][N];
		for (int i = 0; i < M; i++) {
			x = sc.nextInt();
			y = sc.nextInt();
			edges[x][y] = 1;
			edges[y][x] = 1;
		}
	
		costs = new int[C][C];
		for (int i = 0; i < C*(C-1) / 2; i++) {
			x = sc.nextInt();
			y = sc.nextInt();
			z = sc.nextInt();
			costs[x][y] = z;
			costs[y][x] = z;
		}
		sc.close();
	} catch (FileNotFoundException e) {
		System.err.println("Input file not found!");
	}

	selected = new int[N][C];
	tempSol = new int[N];
	for (int i = 0; i < N; i++)
		tempSol[i] = -1;
	finalSol = new int[N];
	tempCost = 0;
	minCost = Integer.MAX_VALUE;
}

/**
 * Functia de backtracking.
 * @param k Nodul ce urmeaza a fi prelucrat.
 */
public static void kColoring (int k) {

	// cat timp mai exista culori neincercate
	for (int i = 0; i < C; i++) {
		find_color(k);  // colorez nodul k

		// in cazul in care colorarea a esuat 
		if (tempSol[k] == -1) {
			// resetez vectorul selected pentru nodul k
			for (int j = 0; j < C; j++)
				selected[k][j] = 0;
			return; // si ma intorc la nodul precedent
		}

		// daca toate nodurile au fost colorate cu succes
		if (k == N-1)
			saveSol(); // salvez noua solutie
		else
			kColoring(k+1);  // altfel, trec la urmatorul nod de colorat
	}
}

/**
 * Functia de colorare.
 * @param k Nodul ce urmeaza a fi colorat.
 */
private static void find_color (int k) {

	// daca nodul a mai fost colorat inainte
	if (tempSol[k] != -1) {
		// scad din tempCost costurile pe care le adaugase acest nod
		for (int i = 0; i < k; i++)
			if (edges[i][k] == 1)
				tempCost -= costs[tempSol[k]][tempSol[i]];
		// resetez culoarea lui k
		tempSol[k] = -1;
	}

	// caut culoarea care ofera un cost minim, tinand cont de vecinii deja colorati
	int costMin = Integer.MAX_VALUE, color = 0;

	for (int c = 0; c < C; c++) // parcurg toate culorile
		if (selected[k][c] == 0) { // daca nu a mai fost atribuita nodului k
			int i, cost = 0;
			boolean noNeighb = true; // true daca nodul nu are niciun vecin deja colorat

			for (i = 0; i < k; i++) // parcurg nodurile deja colorate
				if (edges[i][k] == 1) { // daca gasesc un vecin
					noNeighb = false;

					if (c == tempSol[i])  // daca are aceeasi culoare
						break; // renunt la culoarea curenta

					else { // daca are culoare diferita

						// adaug costul aferent legaturii cu acest vecin
						cost += costs[c][tempSol[i]];

						// daca am depasit deja costul minim asociat unei alte culori
						// sau costul minim al ultimei solutii gasite
						if (cost >= costMin || cost + tempCost >= minCost)
							break; // renunt la culoarea curenta
					}
				}
			// daca nodul k nu are niciun vecin ce are culoarea setata deja
			if (noNeighb == true) {
				// il colorez cu prima culoare valida
				color = c;
				// nu va influenta cu nimic tempCost, deoarece inca
				// nu sunt formate legaturile intre el si vecinii sai
				costMin = 0;
				break; // nu mai parcurg celelalte culori
			}
			// daca am parcurs toate nodurile si nu am gasit niciun vecin cu aceeasi culoare,
			// iar costul culorii curente este mai mic, actualizez informatiile
			if (i == k) {
				costMin = cost;
				color = c;
			}
		}
	/* daca in urma parcurgerii tuturor culorilor costMin nu se modifica:
			- fie toate culorile sunt atribuite unuia dintre vecini
			- fie au mai fost atribuite anterior nodului k
			- fie toate depasesc costul minim al ultimei solutii gasite
		=> nu pot asocia o culoare valida, valoarea lui tempSol[k] va ramane -1 */
	if (costMin == Integer.MAX_VALUE)
		return;

	// daca am gasit o culoare valida, o asociez nodului k
	tempSol[k] = color;
	tempCost += costMin;
	selected[k][color] = 1;
}

/**
 * Functie utilizata pentru retinerea unei solutii.
 * Nota! In urma parcurgerii algoritmului se gaseste o noua solutie doar daca
 * aceasta are un cost mai mic (strict) decat cea anterior salvata.
 */
private static void saveSol () {
	for (int i = 0; i < N; i++)
		finalSol[i] = tempSol[i];
	minCost = tempCost;
}

/**
 * Functie utilizata pentru scrierea solutiei finale in fisier.
 */
public static void showSol() {
	try {
		PrintWriter wr = new PrintWriter("kcol.out");

		if (minCost == Integer.MAX_VALUE)
			wr.println("-1"); // daca nu am gasit nicio solutie
		else {
			wr.println(minCost);
			for (int i = 0; i < N; i++)
				wr.println(i + " " + finalSol[i]);
		}
		wr.close();
	}
	catch (FileNotFoundException e) {
		System.err.println("Output file not found!");
	}
}
    
public static void main (String[] args) {
	init();
	kColoring(0); // pornesc de la primul nod
	showSol();
}
}