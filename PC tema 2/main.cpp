/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/******************************************************************************/

#include "sim.h"

int main(int argc, char **argv)
{
	Network network; // pastreaza informatii despre intreaga retea

	init_api(argc, argv);
	init_sim(&network, argv);

	loop {
		trigger_events(&network);
		process_messages(&network);
		update_routing_table(&network);
		api_update_state();
		if (api_simulation_ended())
			break;
	}

	clean_sim(&network);
	clean_api();

	return 0;
}
