package performance;

import org.addition.epanet.hydraulic.HydraulicSim;
import org.addition.epanet.network.Network;
import org.addition.epanet.network.io.input.InpParser;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.DataOutput;
import java.io.File;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HydraulicSimulationBenchmark {

    private final DataOutput out = new NullDataOutput();
    private Network network;
    private Logger logger;

    @Before
    public void setup() throws Exception {
        network = new Network();
        logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.OFF);
        URI networkUri = getClass().getClassLoader().getResource("networks/THE_MICROPOLIS_MODEL.inp").toURI();
        new InpParser(logger).parse(network, new File(networkUri));
    }

    @Ignore
    @Test
    public void benchmark_Micropolis() throws Exception {
        for (int i = 0; i < 10; ++i) {
            HydraulicSim sim = new HydraulicSim(network, logger);
            sim.simulate(out);
        }
    }
}
