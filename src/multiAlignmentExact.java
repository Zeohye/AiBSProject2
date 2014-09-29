import java.util.Map;

/**
 * Created by Nils Henning on 20-09-2014.
 */
public class multiAlignmentExact {
    public static int[][][] fillTable(String seq1, String seq2, String seq3, Map<String, Integer> matrix, int g) {
        int[][][] scoreTable = new int[seq1.length()+1][seq2.length()+1][seq3.length()+1];
        scoreTable[0][0][0]=0;

        int[][] score2 = linearAlignment.fillTableLinear(seq1,seq2,matrix,g);
        for(int i =0; i<=seq1.length();i++){
            for(int j = 0;j<=seq2.length();j++){

                scoreTable[i][j][0]= score2[i][j]+(i+j)*g;
            }
        }
        score2 = linearAlignment.fillTableLinear(seq1,seq3,matrix,g);
        for(int i =0; i<=seq1.length();i++){
            for(int j = 0;j<=seq3.length();j++){

                scoreTable[i][0][j]= score2[i][j]+(i+j)*g;
            }
        }
        score2 = linearAlignment.fillTableLinear(seq2,seq3,matrix,g);
        for(int i =0; i<=seq3.length();i++){
            for(int j = 0;j<=seq2.length();j++){

                scoreTable[0][i][j]= score2[i][j]+(i+j)*g;
            }
        }

        for(int i = 1; i<=seq1.length();i++){
            for(int j = 1; j<=seq2.length();j++){
                for(int k = 1; k<=seq3.length();k++){
                    int v1,v2,v3,v4,v5,v6,v7;

                    String vij = "" + seq1.charAt(i - 1) + seq2.charAt(j - 1);
                    String vjk = "" + seq2.charAt(j - 1) + seq3.charAt(k - 1);
                    String vik = "" + seq3.charAt(k - 1) + seq1.charAt(i - 1);

                    v1=scoreTable[i-1][j-1][k-1]+matrix.get(vij)+matrix.get(vjk)+matrix.get(vik);
                    v2=scoreTable[i-1][j-1][k]+matrix.get(vij)+2*g;
                    v3=scoreTable[i-1][j][k-1]+matrix.get(vik)+2*g;
                    v4=scoreTable[i][j-1][k-1]+matrix.get(vjk)+2*g;
                    v5=scoreTable[i-1][j][k]+2*g;
                    v6=scoreTable[i][j-1][k]+2*g;
                    v7=scoreTable[i][j][k-1]+2*g;

                    scoreTable[i][j][k] = Util.min7(v1,v2,v3,v4,v5,v6,v7);
                }
            }
        }
        return scoreTable;
    }
 }
