#ifndef _PARSING_H
#define _PARSING_H

/*
* functie utilizata pentru parsarea argumentelor de input
*
* se seteaza modurile (-r, -e, -o)
* si se deschide fisierul de log
* daca este cazul
*
* functia poate parsa argumentele indiferent de ordinea lor si afiseaza
* un mesaj de eroare in cazul in care au fost scrise incorect
*/
void parse_input_arguments (int argc, char** argv, 
														bool* mode, char* URL, FILE** of) {
	switch (argc) {
		case 2:
			strcpy (URL, argv[1]);
			break;

		case 3:
			strcpy (URL, argv[2]);

			if (strcmp (argv[1],"-r") == 0)
				mode[0] = true;
			else if (strcmp (argv[1],"-e") == 0)
				mode[1] = true;
			else usage(argv[0]);

			break;
		case 4:
			strcpy (URL, argv[3]);

			if (strcmp (argv[1],"-r") == 0 && strcmp (argv[2],"-e") == 0
					|| strcmp (argv[1],"-e") == 0 && strcmp (argv[2],"-r") == 0) {
				mode[0] = true;
				mode[1] = true;
			}
			else if (strcmp (argv[1],"-o") == 0)
				(*of) =	fopen (argv[2], "w");
			else usage(argv[0]);

			break;
		case 5:
			strcpy (URL, argv[4]);

			if (strcmp (argv[1],"-o") == 0) {
				(*of) =	fopen (argv[2], "w");
				if (strcmp (argv[3],"-r") == 0)
					mode[0] = true;
				else if (strcmp (argv[3],"-e") == 0)
					mode[1] = true;
				else usage(argv[0]);
			}
			else if (strcmp (argv[2],"-o") == 0) {
				(*of) =	fopen (argv[3], "w");
				if (strcmp (argv[1],"-r") == 0)
					mode[0] = true;
				else if (strcmp (argv[1],"-e") == 0)
					mode[1] = true;
				else usage(argv[0]);
			}
			else usage(argv[0]);

			break;
		case 6:
			strcpy (URL, argv[5]);
			mode[0] = true; mode[1] = true;

			if (strcmp (argv[1],"-o") == 0) {
				(*of) =	fopen (argv[2], "w");
				if ( ! (strcmp (argv[3],"-r") == 0 && strcmp (argv[4],"-e") == 0
					  || strcmp (argv[3],"-e") == 0 && strcmp (argv[4],"-r") == 0) )
					usage(argv[0]);
			}
			else if (strcmp (argv[2],"-o") == 0) {
				(*of) =	fopen (argv[3], "w");
				if ( ! (strcmp (argv[1],"-r") == 0 && strcmp (argv[4],"-e") == 0
					  || strcmp (argv[1],"-e") == 0 && strcmp (argv[4],"-r") == 0) )
					usage(argv[0]);
			}
			else if (strcmp (argv[3],"-o") == 0) {
				(*of) =	fopen (argv[4], "w");
				if ( ! (strcmp (argv[1],"-r") == 0 && strcmp (argv[2],"-e") == 0
					  || strcmp (argv[1],"-e") == 0 && strcmp (argv[2],"-r") == 0) )
					usage(argv[0]);
			}
			else usage(argv[0]);

			break;
		default:
			usage(argv[0]);
			break;
	}
}

/*
* functie utilizata pentru a parsa o cale relativa a unei resurse
* si a obtine numele paginii si calea efectiva
*/
void parse_URL (char** page, char* path[10], char* URL) {

	for (int i = 0; i < PATHLEN; ++i)
    path[i] = NULL;

	char* temp_URL = new char[LEN];
	strcpy(temp_URL, URL);
	char* dir = strtok (temp_URL, "/");

	int i = 0;
	while (dir != NULL) {
		path[i] = new char[LEN];
    path[i++] = dir;
    dir = strtok (NULL, "/");
  }

	(*page) = new char[LEN];
  (*page) = path[i-1];
  path[i-1] = NULL;
}

/*
* functie utilizata pentru a parsa link-ul initial
* (dat ca parametru) si a obtine host-ul si numele resursei
*/
void parse_initial_URL (char** host_name, char** resource, char* URL) {

	strtok (URL,"/");
	(*host_name) = strtok (NULL,"/");
	(*resource) = strtok (NULL,"");
}

/*
* functie utilizata pentru parsarea unei pagini html
* si a obtine toate link-urile valide din ea
*
* link-urile sunt salvate in 2 vectori,
* in functie de tipul lor (.html sau nu)
*/
void parse_page (char* page, FILE* of,
								 vector <char*> *Elinks, vector <char*> *Rlinks) {

	FILE* page_file = fopen (page, "r");
	DE(page_file == NULL, "Opening page file failed!", of, true);
	
	char *line = new char[2 * LEN], *str;
	char *header = new char[4], *extension = new char[5];

	while (! feof(page_file)) {

		memset(line, 0, sizeof(line));
		fgets (line, 2 * LEN, page_file);

		str = strstr(line, "<a");
		if (str) {
			str = strstr(line, "href=");
			if (str) {
				strtok (str, "=");
				str = strtok (NULL,"\"\'");
				if (str) {
					memset (header, 0, sizeof(header));
					strncpy (header, str, 4);

					if (strcmp(header, "http") != 0 && str[0] != '/' &&
							str[ strlen(str)-1 ] != '/') {

						char* link = new char[LEN];
						strcpy(link, str);
						memset (extension, 0, sizeof(extension));
						strcpy (extension, (str + strlen(str) - 5));

						if ( strcmp(extension, ".html") == 0 ||
								 strcmp(extension + 1, ".htm") == 0)
							(*Rlinks).push_back(link);
						else
							(*Elinks).push_back(link);
					}
				}
			}
		}
	}
	
	fclose (page_file);
}

#endif
