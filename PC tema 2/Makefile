CXX=g++
CXXFLAGS=`pkg-config libgvc --cflags` -g -Wall -std=c++11 $(EXTRA)
LDFLAGS=`pkg-config libgvc --libs`

build: simulation

simulation: main.cpp sim.cpp network.cpp router.cpp topology.cpp routing_table.cpp
	$(CXX) $(CXXFLAGS) -o $@ $^ -lapi -L . $(LDFLAGS)

clean:
	rm -f -R simulation *.o
