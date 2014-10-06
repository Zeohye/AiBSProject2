import java.util.Map;

/**
 * Created by Simon on 28-09-2014.
 */
public class SinglePairWiseAlignmentImpl implements SinglePairwiseAlignment{

    public int[][] scoreMatrix;
    private int score;
    private String sequence1;
    private String sequence2;

    public SinglePairWiseAlignmentImpl(String seq1, String seq2, Map costMatrix, int gapcost){
        scoreMatrix = linearAlignment.fillTableLinear(seq1, seq2, costMatrix, gapcost);
//        for(int i = 0; i < scoreMatrix.length; i++){
//            for(int j = 0; j < scoreMatrix.length; j++){
//                System.out.print(scoreMatrix[i][j] + " ");
//            }
//            System.out.println();
//        }
        score = scoreMatrix[seq1.length()][seq2.length()];
        linearCostBackTrack backtrack = new linearCostBackTrack();
        backtrack.backTrack(seq1, seq2, scoreMatrix, costMatrix, "", "", false, gapcost);
        sequence1 = backtrack.getSequenses1().get(0);
        sequence2 = backtrack.getSequenses2().get(0);

        scoreMatrix = null;
    }


    @Override
    public void print() {
        System.out.println("single pairwise alignment score: " + score);

        System.out.println("sequence1: " + sequence1);
        System.out.println("sequence2: " + sequence2);
        System.out.println();
    }

    @Override
    public String getSeq1() {
        return sequence1;
    }

    @Override
    public String getSeq2() {
        return sequence2;
    }

    public int getScore() {
        return score;
    }
}
