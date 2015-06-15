/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/******************************************************************************/

#include "sim.h"

void init_sim (Network *network, char **argv) {

	// adaug legaturile initiale in topologiile routerelor implicate
	network->add_initial_links (argv[1]);
	// trimit primele mesaje de gestionare
	for (int i = 0; i < network->num_routers; i++)
		network->routers[i]->start_protocol_msg ();

	// retin informatiile despre mesajele rutabile ce urmeaza a fi transmise
	network->save_messages (argv[2]);
	
	// retin informatiile despre schimbarile ce vor aparea in cadrul retelei
	network->save_events (argv[3]);
}

void trigger_events (Network *network) {

  for (int i = 0; i < network->num_events; i++) {
		Event e = network->events[i];
		// daca evenimentul este triggered
		if (e.time == get_time()) {

			// daca este de tip ADD
			if (e.type == EVENT_TYPE_ADD) {
				// adaug legatura in topologia routerelor implicate 
				// si trimit mesajele de gestionare vecinilor acestora
				Router* r = network->routers[e.r1];
				r->add_link(e.r1, e.r2, e.cost);
				r->start_protocol_msg ();

				r = network->routers[e.r2];
				r->add_link(e.r1, e.r2, e.cost);
				r->start_protocol_msg ();
			}
			// daca este de tip REMOVE
			else {
				// sterg legatura din topologia routerelor implicate 
				// si trimit mesajele de gestionare vecinilor acestora
				Router* r = network->routers[e.r1];
				r->erase_link(e.r1, e.r2);
				r->start_protocol_msg ();

				r = network->routers[e.r2];
				r->erase_link(e.r1, e.r2);
				r->start_protocol_msg ();
			}
		}
	}
}

void process_messages (Network *network) {

// redistribuirea mesajelor de protocol si a celor rutabile
  for (int i = 0; i < network->num_routers; i++) {
		Router* r = network->routers[i];
		r->process_protocol_msg ();
		r->process_routing_msg ();
	}

// pornirea in retea a mesajelor ce trebuie transmise la momentul de timp actual
	for (int i = 0; i < network->num_messages; i++) {
		Message m = network->messages[i];
		if (m.time == get_time())
			network->routers[m.src]->start_routing_msg (m);
	}
}

void update_routing_table (Network *network) {
	// fiecare router isi recalculeaza tabela de rutare pe baza topologiei sale
	for (int i = 0; i < network->num_routers; i++)
		network->routers[i]->update_table ();
}

void clean_sim (Network *network) {
	network->routers.clear();
	network->events.clear();
	network->messages.clear();
}
