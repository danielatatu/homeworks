/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/******************************************************************************/

#ifndef _SIM_H_
#define _SIM_H_

#include "network.h"

void init_sim (Network *network, char **argv);
void clean_sim (Network *network);

void trigger_events (Network *network);
void process_messages (Network *network);
void update_routing_table (Network *network);

#endif /* _SIM_H_ */
