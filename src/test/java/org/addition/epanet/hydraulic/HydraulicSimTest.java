package org.addition.epanet.hydraulic;

import org.addition.epanet.network.Network;
import org.addition.epanet.network.io.input.InpParser;
import org.addition.epanet.util.ENException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertArrayEquals;

public class HydraulicSimTest {

    @Test
    public void producesCorrectResult_quickStart() throws Exception {
        assertNetworkHasCorrectResult("quick-start");
    }

    @Test
    public void producesCorrectResult_Net1() throws Exception {
        assertNetworkHasCorrectResult("Net1");
    }

    @Test
    public void producesCorrectResult_Net2() throws Exception {
        assertNetworkHasCorrectResult("Net2");
    }

    @Test
    public void producesCorrectResult_Net3() throws Exception {
        assertNetworkHasCorrectResult("Net3");
    }

    @Test
    public void producesCorrectResult_PRV() throws Exception {
        assertNetworkHasCorrectResult("PRV");
    }

    @Test
    public void producesCorrectResult_simple() throws Exception {
        assertNetworkHasCorrectResult("simple");
    }

    private void assertNetworkHasCorrectResult(String networkName) throws URISyntaxException, ENException, IOException {
        URI hydUri = getClass().getClassLoader().getResource(String.format("simulations/%s.hyd", networkName)).toURI();
        URI netUri = getClass().getClassLoader().getResource(String.format("networks/%s.inp", networkName)).toURI();

        byte[] currentSimulation = simulate(netUri);
        byte[] preparedSimulation = Files.readAllBytes(Paths.get(hydUri));

        assertArrayEquals(preparedSimulation, currentSimulation);
    }

    private byte[] simulate(URI netUri) throws ENException, IOException {
        Logger logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.OFF);
        HydraulicSim sim = new HydraulicSim(getNetwork(netUri, logger), logger);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 1024);
        sim.simulate(out);
        out.close();
        return out.toByteArray();
    }

    private Network getNetwork(URI netUri, Logger logger) throws ENException {
        Network network = new Network();
        new InpParser(logger).parse(network, new File(netUri));
        return network;
    }
}
