package edu.uwb.nemolib_examples.network_motif_detector;

import edu.uwb.nemolib.Graph;
import edu.uwb.nemolib.GraphParser;
import edu.uwb.nemolib.NetworkMotif;
import edu.uwb.nemolib.RelativeFrequencyAnalyzer;

import java.io.IOException;

public class NetworkMotifDetector {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("usage: NetworkMotifDetector path_to_data " +
                    "motif_size, random_graph_count");
            System.exit(1);
        }

        String filename = args[0];
        System.out.println("filename = " + args[0]);
        int motifSize = Integer.parseInt(args[1]);
        int randGraphCount = Integer.parseInt(args[2]);

        if (motifSize < 3) {
            System.err.println("Motif getSize must be 3 or larger");
            System.exit(-1);
        }

        // parse input graph
        System.out.println("Parsing target graph...");
        Graph targetGraph = null;
        try {
            targetGraph = GraphParser.parse(filename);
        } catch (IOException e) {
            System.err.println("Could not process " + filename);
            e.printStackTrace();
            System.exit(-1);
        }

        RelativeFrequencyAnalyzer relativeFrequencyAnalyzer =
                NetworkMotif.findNetworkMotifs(targetGraph, motifSize, randGraphCount);
        System.out.println(relativeFrequencyAnalyzer);
        System.out.println("Complete");
    }
}

