import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 29-09-2014.
 */
public class MultipleAlignmentExact3 implements MultipleAllignment{

    private int[][][] score;
    private int optimal;
    private List<String> alignment;

    private String seq1, seq2, seq3;
    private Map<String, Integer> costMatrix;
    private int gapCost;

    public MultipleAlignmentExact3(String seq1, String seq2, String seq3, Map<String, Integer> costMatrix, int gapCost){

        this.seq1 = seq1;
        this.seq2 = seq2;
        this.seq3 = seq3;

        this.costMatrix = costMatrix;
        this.gapCost = gapCost;

        //start with an empty 3-dim score matrix
        score = new int[seq1.length()+1][seq2.length()+1][seq3.length()+1];
        //set base case
        score[0][0][0] = 0;

        // d stands for direction.
        // di is the score if coming from directly along the "i"-axis,
        // dij diagonally along the "ij"-plane,
        // dijk diagonally in all 3 dimensions
        // etc.
        int di, dj, dk, dij, dik, djk, dijk;

        String s;

        //set boundry cases. where i,j or k is =0
        int[][] score2 = new SinglePairWiseAlignmentImpl(seq1,seq2,costMatrix,gapCost).scoreMatrix;
        for(int i =0; i<=seq1.length();i++){
            for(int j = 0;j<=seq2.length();j++){

                score[i][j][0]= score2[i][j]+(i+j)*gapCost;
            }
        }
        score2 = new SinglePairWiseAlignmentImpl(seq1,seq3,costMatrix,gapCost).scoreMatrix;
        for(int i =0; i<=seq1.length();i++){
            for(int j = 0;j<=seq3.length();j++){

                score[i][0][j]= score2[i][j]+(i+j)*gapCost;
            }
        }
        score2 = new SinglePairWiseAlignmentImpl(seq2,seq3,costMatrix,gapCost).scoreMatrix;
        for(int i =0; i<=seq3.length();i++){
            for(int j = 0;j<=seq2.length();j++){

                score[0][i][j]= score2[i][j]+(i+j)*gapCost;
            }
        }

        for(int i = 1; i <= seq1.length(); i++){
            for(int j = 1; j <= seq2.length(); j++){
                for(int k = 1; k <= seq3.length(); k++){

                    //set the scores to maximum
                    //in case they don't get initialized
                    di = dj = dk =  dij = dik = djk = dijk = Integer.MAX_VALUE;

                    //the costs of moving only along 1 dimension
                    if(i > 0){
                        di = gapCost*2 + score[i-1][j][k];
                    }
                    if(j > 0){
                        dj = gapCost*2 + score[i][j-1][k];
                    }
                    if(k > 0){
                        dk = gapCost*2 + score[i][j][k-1];
                    }

                    //the costs of moving along 2 dimensions
                    if(i > 0 && j > 0){
                        s = "" + seq1.charAt(i-1) + seq2.charAt(j-1);
                        dij = score[i-1][j-1][k] + (gapCost*2) + costMatrix.get(s);
                    }
                    if(i > 0 && k > 0){
                        s = "" + seq1.charAt(i-1) + seq3.charAt(k-1);
                        dik = score[i-1][j][k-1] + (gapCost*2) + costMatrix.get(s);
                    }
                    if(j > 0 && k > 0){
                        s = "" + seq2.charAt(j-1) + seq3.charAt(k-1);
                        djk = score[i][j-1][k-1] + (gapCost*2) + costMatrix.get(s);
                    }

                    //the costs of moving along all 3 dimensions
                    if(i > 0 && j > 0 && k > 0){
                        s = "" + seq1.charAt(i-1) + seq2.charAt(j-1);
                        dijk = costMatrix.get(s);

                        s = "" + seq1.charAt(i-1) + seq3.charAt(k-1);
                        dijk += costMatrix.get(s);

                        s = "" + seq2.charAt(j-1) + seq3.charAt(k-1);
                        dijk += costMatrix.get(s);
                        dijk += score[i-1][j-1][k-1];
                    }

                    //all that is left is to put the lowest value into the scoreMatrix


                    score[i][j][k] = Util.min7(di,dj,dk,dij,dik,djk,dijk);
                }
            }
        }

        optimal = score[seq1.length()][seq2.length()][seq3.length()];

    }

    @Override
    public void print() {
        System.out.println("multiple alignment with optimal score: "+optimal);
        if(aligns1.isEmpty()){
            System.out.println("not yet backtracked");
        }else {
            System.out.println(aligns1.get(0));
            System.out.println(aligns2.get(0));
            System.out.println(aligns3.get(0));
        }

        System.out.println();
    }

    @Override
    public List<String> getAlignment() {
        ArrayList<String> alignment = new ArrayList<String>();
        alignment.add(aligns1.get(0));
        alignment.add(aligns2.get(0));
        alignment.add(aligns3.get(0));

        return alignment;
    }

    public void backTrack(boolean full){
        //initiates the recursive backtrack.
        //possibly time consuming, so not done in constructor

        System.out.println("performing backtrack...");
        backTrackRecursive(seq1.length(), seq2.length(), seq3.length(), "", "", "", full);
        System.out.println("backtrack completed");
    }

    private List<String> aligns1 = new ArrayList<String>();
    private List<String> aligns2 = new ArrayList<String>();
    private List<String> aligns3 = new ArrayList<String>();

    public void backTrackRecursive(int i, int j, int k, String align1, String align2, String align3, boolean full){
        //if at 0,0,0, store alignments and stop

        if(i == 0 && j == 0 && k == 0){
            aligns1.add(align1);
            aligns2.add(align2);
            aligns3.add(align3);
            return;
        }

        //check 3d diagonally back
        int pathCost;
        String s;
        if(i>0 && j > 0 && k > 0){
            s = "" + seq1.charAt(i-1) + seq2.charAt(j-1);
            pathCost = costMatrix.get(s);

            s = "" + seq1.charAt(i-1) + seq3.charAt(k-1);
            pathCost += costMatrix.get(s);

            s = "" + seq2.charAt(j-1) + seq3.charAt(k-1);
            pathCost += costMatrix.get(s);

            if(score[i-1][j-1][k-1] + pathCost == score[i][j][k]){
                align1 = seq1.charAt(i-1) + align1;
                align2 = seq2.charAt(j-1) + align2;
                align3 = seq3.charAt(k-1) + align3;

                backTrackRecursive(i-1,j-1,k-1, align1,align2,align3, full);

                if(!full){
                    return;
                }
            }

        }


        if(i > 0 && j > 0){
            s = "" + seq1.charAt(i-1) + seq2.charAt(j-1);
            pathCost = costMatrix.get(s) + 2*gapCost;

            if(score[i-1][j-1][k] + pathCost == score[i][j][k]){
                align1 = seq1.charAt(i-1) + align1;
                align2 = seq2.charAt(j-1) + align2;
                align3 = "-" + align3;

                backTrackRecursive(i-1,j-1,k, align1,align2,align3, full);

                if(!full){
                    return;
                }
            }
        }

        if(i > 0 && k > 0){
            s = "" + seq1.charAt(i-1) + seq3.charAt(k-1);
            pathCost = costMatrix.get(s) + 2*gapCost;

            if(score[i-1][j][k-1] + pathCost == score[i][j][k]){
                align1 = seq1.charAt(i-1) + align1;
                align2 = "-" + align2;
                align3 = seq3.charAt(k-1) + align3;

                backTrackRecursive(i-1,j,k-1, align1,align2,align3, full);

                if(!full){
                    return;
                }
            }
        }

        if(j > 0 && k > 0){
            s = "" + seq2.charAt(j-1) + seq3.charAt(k-1);
            pathCost = costMatrix.get(s) + 2*gapCost;

            if(score[i][j-1][k-1] + pathCost == score[i][j][k]){
                align1 = "-" + align1;
                align2 = seq2.charAt(j-1) + align2;
                align3 = seq3.charAt(k-1) + align3;

                backTrackRecursive(i,j-1,k-1, align1,align2,align3, full);

                if(!full){
                    return;
                }
            }
        }

        if(i > 0){
            pathCost = 2*gapCost;

            if(score[i-1][j][k] + pathCost == score[i][j][k]){
                align1 = seq1.charAt(i-1) + align1;
                align2 = "-" + align2;
                align3 = "-" + align3;

                backTrackRecursive(i-1,j,k, align1,align2,align3, full);

                if(!full){
                    return;
                }
            }
        }

        if(j > 0){
            pathCost = 2*gapCost;

            if(score[i][j-1][k] + pathCost == score[i][j][k]){
                align1 = "-" + align1;
                align2 = seq2.charAt(j-1) + align2;
                align3 = "-" + align3;

                backTrackRecursive(i,j-1,k, align1,align2,align3, full);

                if(!full){
                    return;
                }
            }
        }

        if(k > 0){
            pathCost = 2*gapCost;

            if(score[i][j][k-1] + pathCost == score[i][j][k]){
                align1 = "-" + align1;
                align2 = "-" + align2;
                align3 = seq3.charAt(k-1) + align3;

                backTrackRecursive(i,j,k-1, align1,align2,align3, full);

                if(!full){
                    return;
                }
            }
        }



    }
}
