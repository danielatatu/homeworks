#ifndef _DISPLAY_H
#define _DISPLAY_H

#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <iostream>
#include <errno.h>
#include <unistd.h>
#include <vector>
using namespace std;

/* Dimensiunea maxima a calupului de date */
#define BUFLEN 10000
/* Numarul maxim de directoare continute de un path */
#define PATHLEN 10
/* Numarul maxim de caractere continute de un URL */
#define LEN 100
/* Numarul intors de server pentru mesajul OK */
#define OK 200

/*
* Functie utilizata pentru atentionarea utilizatorului 
* asupra modului in care se ruleaza programul.
*/
void usage(char*file)
{
	fprintf (stderr,"Usage: %s [-r] [-e] [-o <fisier_log>] http://<server_name>/<file_path>\n", file);
	exit(EXIT_FAILURE);
}

/*
* Macro de verificare a erorilor. (Display Error)
*
* Daca "assertion" intoarce valoarea true,
* se verifica daca output_file este NULL;
* 	Daca da => se afiseaza eroarea la stderr
* 	Daca nu => se afiseaza eroarea in fisier
* Daca "fail" este setat, se si iese din program.
*
* Exemplu de utilizare: 
* 		int fd = open (file_name , O_RDONLY);	
* 		DE(fd == -1, "open failed", file_name, true);
*/

#define DE(assertion, call_description, output_file, fail)		\
	do {																												\
		if (assertion) {																					\
			if (output_file == NULL)																\
				fprintf (stderr, "%s\n", call_description);						\
			else																										\
				fprintf (output_file, "%s\n", call_description);			\
			if (fail)																								\
				exit (EXIT_FAILURE);																	\
		}																													\
	} while(0)

/*
* functie utilizata pentru tratarea erorilor de tip 4xx
* din cadrul protocolului
*/
void treat_errors (FILE* file, FILE* of) {

	fseek (file , 9 , SEEK_SET);
	char* line = new char[LEN];
	fgets (line, 2 * LEN, file);
	strtok(line, " ");
	int err_no = atoi(line);

	DE(err_no == 400, "ERROR 400 - Bad Request", of, false);
	DE(err_no == 401, "ERROR 401 - Unauthorized", of, false);
	DE(err_no == 403, "ERROR 403 - Forbidden", of, false);
	DE(err_no == 404, "ERROR 404 - Page not found", of, false);
}

#endif
