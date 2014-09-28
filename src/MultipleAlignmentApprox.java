import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 27-09-2014.
 */
public class MultipleAlignmentApprox {

    private List<String> alignment;

    private List<String> sequences;
    private int bestSequence;
    private SinglePairwiseAlignment[][] pairwiseAlignments;
    private ExtendStrategy extendStrat;

    public MultipleAlignmentApprox(List<String> sequences, Map costMatrix, int gapCost, ExtendStrategy extendStrat){
        this.extendStrat = extendStrat;
        this.sequences = sequences;
        alignment = new ArrayList<String>();


        calculatePairWiseAlignments(costMatrix, gapCost);

        SinglePairwiseAlignment single;
        for(int i = 0; i < sequences.size();i++) {
            single = pairwiseAlignments[bestSequence][i];
            if(i < bestSequence) {
                extendStrat.extend(alignment, single.getSeq2(), single.getSeq1());
            }else if(i > bestSequence){
                extendStrat.extend(alignment, single.getSeq1(), single.getSeq2());
            }
            if(single != null) {
                single.print();
            }
            print();
        }

    }

    public void calculatePairWiseAlignments(Map costMatrix, int gapCost){
        String seq1, seq2;
        int[] scores = new int[sequences.size()];
        pairwiseAlignments = new SinglePairwiseAlignment[sequences.size()][sequences.size()];
        for(int i = 0; i < sequences.size(); i++){
            for(int j = i+1; j < sequences.size(); j++){
                seq1 = sequences.get(i);
                seq2 = sequences.get(j);
                SinglePairWiseAlignmentImpl pairWiseAlignment = new SinglePairWiseAlignmentImpl(seq1, seq2, costMatrix, gapCost);
                scores[i] += pairWiseAlignment.getScore();
                scores[j] += pairWiseAlignment.getScore();
                pairwiseAlignments[i][j] = pairWiseAlignment;
                pairwiseAlignments[j][i] = pairWiseAlignment;

            }
        }

        int bestScore = Integer.MAX_VALUE;
        for(int i = 0; i < scores.length; i++){
            if(scores[i] < bestScore){
                bestScore = scores[i];
                bestSequence = i;
            }
        }
    }

    public void print(){
        System.out.println("Shitty alignment best sequence: " + bestSequence);
        for(String s : alignment){
            System.out.println(s);
        }
        System.out.println();

    }
}
