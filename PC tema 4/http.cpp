// Tatu Daniela Florentina, 325CA

#include "display.h"
#include "parsing.h"

/*
* mode[0] => recursive
* mode[1] => everything
*/
bool* mode;
/* fisierul de log */
FILE* of = NULL;
/* informatii despre server */
char* host_name;
struct sockaddr_in toServer;

/* 
* functie utilizata pentru a reveni la directorul initial
* dupa ce am downloadat o pagina
* no_dir = numarul de fisiere in care am intrat pentru downloadarea paginii
*/ 
void go_back (int no_dir) {
	for (int i = 0; i < no_dir; ++i)
    chdir("..");
}

/*
* functie utilizata pentru downloadarea unei pagini
*
* resource = URL-ul paginii curente
* Ppath = calea pana la radacina a paginii care a referit pagina curenta
* page = numele paginii (este necesar si in functia de procesare)
* path = calea relativa a paginii curente (este necesara in functia de procesare)
*/
int download_page (char* resource, char* Ppath[PATHLEN],
									 char** page, char* path[PATHLEN]) {

	// se parseaza URL-ul paginii pentru a obtine numele ei
	// si calea catre ea
	parse_URL (page, path, resource);

	// numarul de directoare in care am intrat
	// (din care urmeaza sa ma intorc)
	int no_dir = 0;
	char* command;
  for (int i = 0; path[i] != NULL; ++i) {
  	if (strcmp(path[i], ".") != 0) {
  		// daca directorul nu exista il creez
			if (access (path[i], F_OK) == -1) {
				command = new char[LEN];
				strcpy (command, "mkdir ");
				strcat (command, path[i]);
				system (command);
			}
			if (strcmp(path[i],"..") == 0) {
				go_back(no_dir);
  			return -1;
			}
			// mutarea in directorul curent
		  chdir(path[i]);
		  no_dir++; 
    } 
  }
  
  char* temp_page = (*page);

	// daca pagina exista deja nu o mai downloadez
  if (access (temp_page, F_OK) != -1) {
  	go_back(no_dir);
  	return -1;
  }
	errno = 0;

	// deschiderea socketului
  int sockfd = socket(PF_INET, SOCK_STREAM, 0);
	DE(sockfd < 0, "Opening socket failed!", of, true);

	// conexiunea socketului
	int connection = connect(sockfd, (struct sockaddr*)&toServer, 
													 sizeof(struct sockaddr));
	DE(connection != 0, "Connecting socket failed!", of, true);

	// obtin numele complet al resursei (calea pana la radacina)
  char* complete_res = new char[LEN];
  memset (complete_res, 0, sizeof(complete_res));
  if (Ppath != NULL)
  	for (int i = 0; Ppath[i] != NULL; ++i) {
  		strcat (complete_res, Ppath[i]);
  		strcat (complete_res, "/");
  	}
  strcat (complete_res, resource);

	// trimiterea cererii
  char buffer[BUFLEN];
 	sprintf (buffer, "GET /%s HTTP/1.0\nHost: %s\n\n", complete_res, host_name);
	send (sockfd, buffer, strlen(buffer), 0);
 	DE(errno != 0, strerror(errno), of, true);

	// crearea fisierului in care voi descarca pagina
	FILE* resource_file = fopen (temp_page, "w+");
	DE(resource_file == NULL, "Opening resource file failed!", of, true);

	// descarcarea paginii
	ssize_t bytes_read;
	do
	{
		memset(buffer, 0, sizeof(buffer));
		bytes_read = recv(sockfd, buffer, sizeof(buffer), 0);
		if ( bytes_read > 0 )
		    fprintf(resource_file, "%s", buffer);
	}
	while ( bytes_read > 0 );

	// tratarea erorilor
	treat_errors (resource_file, of);

	// inchiderea fisierului in care s-a descarcat pagina
	fclose (resource_file);

	return no_dir;
}

/*
* functie utilizata pentru a procesa o pagina; adica:
*
* - se apeleaza functia de  download
* - se obtin link-urile ce trebuie procesate 
* 	daca modurile -e sau -r sunt setate
* - se proceseaza aceste link-uri
* - se efectueaza mutarea in directorul initial
*		(in care se afla programul inainte de download)
*/
void process_page (char* resource, char* Ppath[PATHLEN], int level) {

	char *page, *path[PATHLEN];
	int no_dir = download_page (resource, Ppath, &page, path);
	
	// daca pagina exista deja nu o mai procesez
  if (no_dir == -1)
  	return;

	// parseaza si obtine doi vectori cu resursele ce trebuie downloadate
	// in cazul in care sunt setate modurile recursive sau everything
	vector <char*> Elinks;
	vector <char*> Rlinks;
	parse_page (page, of, &Elinks, &Rlinks);

	// obtine calea paginii curente pana la radacina
	// utilizant calea paginii mame
	char* complete_path [2 * PATHLEN];
	for (int i = 0; i < 2 * PATHLEN; ++i)
		complete_path[i] = NULL;
	
	int i = 0;
	if (Ppath != NULL)
		for (i = 0; Ppath[i] != NULL; ++i) {
			complete_path[i] = new char[LEN];
			complete_path[i] = Ppath[i];
		}
	for (int j = 0; path[j] != NULL; ++j) {
		complete_path[i] = new char[LEN];
		complete_path[i++] = path[j];
	}

	// daca trebuie descarcate toate fisierele de pe aceasta pagina
	if (mode[1] == true) {
		char *E_page, *E_path[PATHLEN];
		for (int i = 0; i < Elinks.size(); ++i) {
			int E_no_dir = download_page (Elinks[i], complete_path, &E_page, E_path);
			go_back(E_no_dir);
		}
	}

	// daca trebuie descarcate paginile recursiv
	if (level < 5 && mode[0] == true)
		for (int i = 0; i < Rlinks.size(); ++i)
			process_page (Rlinks[i], complete_path, level++);
	
	// mutare inapoi in directorul initial
	go_back(no_dir);
}

int main (int argc,char** argv) {

	mode = new bool[2];
	for (int i = 0; i < 2; ++i)
		mode[i] = false;

	// se parseaza argumentele de input
	char* URL = new char[LEN];
	parse_input_arguments (argc, argv, mode, URL, &of);

	// se parseaza link-ul initial
	char *resource;
	parse_initial_URL (&host_name, &resource, URL);

	// se obtin informatii despre server
	struct hostent* h = gethostbyname(host_name);
	DE(h == NULL, "Getting host by name failed!", of, true);

	toServer.sin_family = AF_INET;
	toServer.sin_port = htons(80);
	memcpy (&toServer.sin_addr.s_addr, h->h_addr, sizeof(h->h_addr_list[0]));

	// se proceseaza prima pagina
	// si urmatoarele recursiv, daca e cazul
	process_page(resource, NULL, 1);

	// se inchide fisierul de log
	if (of)
		fclose (of);

	return 0;
}
