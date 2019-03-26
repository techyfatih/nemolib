package edu.uwb.nemolib;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class NetworkMotif {
    public static RelativeFrequencyAnalyzer findNetworkMotifs(Graph targetGraph, int motifSize, int randGraphCount) {
        if (targetGraph == null) {
            return null;
        }
        // Hard-code probs for now. This vector will take about ~10% sample
        List<Double> probs = new LinkedList<>();
        for (int i = 0; i < motifSize - 2; i++) {
            probs.add(1.0);
        }
        probs.add(1.0);
        probs.add(0.1);

        SubgraphEnumerationResult subgraphCount = new SubgraphCount();
        SubgraphEnumerator targetGraphESU = new ESU();
        TargetGraphAnalyzer targetGraphAnalyzer =
                new TargetGraphAnalyzer(targetGraphESU, subgraphCount);
        Map<String, Double> targetLabelToRelativeFrequency =
                targetGraphAnalyzer.analyze(targetGraph, motifSize);

        SubgraphEnumerator randESU = new RandESU(probs);
        RandomGraphAnalyzer randomGraphAnalyzer =
                new RandomGraphAnalyzer(randESU, randGraphCount);
        Map<String, List<Double>> randomLabelToRelativeFrequencies =
                randomGraphAnalyzer.analyze(targetGraph, motifSize);

        return new RelativeFrequencyAnalyzer(randomLabelToRelativeFrequencies, targetLabelToRelativeFrequency);
    }
}
