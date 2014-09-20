import java.util.Map;

/**
 * Created by Nils Henning on 20-09-2014.
 */
public class linearAlignment {

    public static int[][] fillTableLinear(String seq1, String seq2, Map<String, Integer> matrix, int gapCost){
        int[][] scoreTable = new int [seq1.length()+1][seq2.length()+1];
        scoreTable[0][0]=0;
        for(int i = 1; i<=seq1.length();i++){
            scoreTable [i][0]=i*gapCost;
        }
        for(int j = 1; j<=seq2.length(); j++){
            scoreTable [0][j]=j*gapCost;
        }

        for(int i = 1; i<=seq1.length();i++){
            for(int j = 1; j<=seq2.length();j++){
                int v1,v2,v3;
                String value = ""+seq1.charAt(i-1)+seq2.charAt(j-1);
                v1 = scoreTable[i-1][j-1]+matrix.get(value);
                v2 = scoreTable[i-1][j]+gapCost;
                v3 = scoreTable[i][j-1]+gapCost;
                scoreTable[i][j] = Util.min3(v1, v2, v3);
            }
        }
        return scoreTable;
    }
}
