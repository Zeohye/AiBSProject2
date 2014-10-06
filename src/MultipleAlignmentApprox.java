import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 27-09-2014.
 */
public class MultipleAlignmentApprox implements MultipleAllignment{

    private List<String> alignment;

    private List<String> sequences;
    private int center;
    private SinglePairwiseAlignment[][] pairwiseAlignments;
    private ExtendStrategy extendStrat;

    public MultipleAlignmentApprox(List<String> sequences, Map costMatrix, int gapCost, ExtendStrategy extendStrat){
        this.extendStrat = extendStrat;
        this.sequences = sequences;
        alignment = new ArrayList<String>();


        //calculate every pariwise alignment, and store them in the pairwiseAlignments 2d array
        calculatePairWiseAlignments(costMatrix, gapCost);

        SinglePairwiseAlignment single;
        for(int i = 0; i < sequences.size();i++) {
            //extend only if i != center
            if(i != center ) {
                extendStrat.extend(alignment, pairwiseAlignments[center], center, i);
                print();
            }

        }
    }

    public void calculatePairWiseAlignments(Map costMatrix, int gapCost){
        String seq1, seq2;
        int[] scores = new int[sequences.size()];
        pairwiseAlignments = new SinglePairwiseAlignment[sequences.size()][sequences.size()];

        //for every pair of sequences
        for(int i = 0; i < sequences.size(); i++){
            for(int j = i+1; j < sequences.size(); j++){
                seq1 = sequences.get(i);
                seq2 = sequences.get(j);

                System.out.println(i + ", " + j);

                //align them
                SinglePairWiseAlignmentImpl pairWiseAlignment = new SinglePairWiseAlignmentImpl(seq1, seq2, costMatrix, gapCost);
                //sum scores
                scores[i] += pairWiseAlignment.getScore();
                scores[j] += pairWiseAlignment.getScore();

                //and store them
                pairwiseAlignments[i][j] = pairWiseAlignment;
                pairwiseAlignments[j][i] = pairWiseAlignment;
            }
        }

        //find the best score, and the center sequence
        int bestScore = Integer.MAX_VALUE;
        for(int i = 0; i < scores.length; i++){
            if(scores[i] < bestScore){
                bestScore = scores[i];
                center = i;
            }
        }
    }

    public void print(){
        System.out.println("Approximate alignment. Index of center sequence: " + center);
        for(String s : alignment){
            System.out.println(s);
        }
        System.out.println();

    }

    @Override
    public List<String> getAlignment() {
        return alignment;
    }
}
