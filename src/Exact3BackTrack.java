import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Nils Henning on 20-09-2014.
 */
public class Exact3BackTrack {

    ArrayList<String> Sequences1 = new ArrayList<String>();
    ArrayList<String> Sequences2 = new ArrayList<String>();
    ArrayList<String> Sequences3 = new ArrayList<String>();

    public void backTrack(String seq1, String seq2, String seq3, int[][][] table, Map<String, Integer> matrix, String result1, String result2, String result3, int gap, boolean full) {
        int s1Length = seq1.length();
        int s2Length = seq2.length();
        int s3Length = seq3.length();

        if((s1Length !=0 && s2Length != 0) ||(s1Length !=0 && s3Length != 0)||(s2Length !=0 && s3Length != 0)) {
            String v12 = "" + seq1.charAt(s1Length - 1) + seq2.charAt(s2Length - 1);
            String v23 = "" + seq2.charAt(s2Length - 1) + seq3.charAt(s3Length - 1);
            String v31 = "" + seq3.charAt(s3Length - 1) + seq1.charAt(s1Length - 1);
            if(s1Length>0 && s2Length>0 && s3Length>0 && table[s1Length][s2Length][s3Length]==table[s1Length-1][s2Length-1][s3Length-1]+matrix.get(v12)+matrix.get(v23)+matrix.get(v31)){
                String res1 = seq1.charAt(s1Length-1) + result1;
                String res2 = seq2.charAt(s2Length-1) + result2;
                String res3 = seq3.charAt(s3Length-1) + result3;
                backTrack(seq1.substring(0, s1Length-1),seq2.substring(0,s2Length-1),seq3.substring(0,s3Length-1),table,matrix,res1,res2,res3,gap,full);
                if (!full) return;
            }
            if(s1Length>0 && s2Length>0&& table[s1Length][s2Length][s3Length]==table[s1Length-1][s2Length-1][s3Length]+matrix.get(v12)+2*gap){
                String res1 = seq1.charAt(s1Length-1) + result1;
                String res2 = seq2.charAt(s2Length-1) + result2;
                String res3 = "-" + result3;
                backTrack(seq1.substring(0, s1Length-1),seq2.substring(0,s2Length-1),seq3,table,matrix,res1,res2,res3,gap,full);
                if (!full) return;
            }
            if(s1Length>0&& s3Length>0 && table[s1Length][s2Length][s3Length]==table[s1Length-1][s2Length][s3Length-1]+matrix.get(v31)+2*gap){
                String res1 = seq1.charAt(s1Length-1) + result1;
                String res2 = "-" + result2;
                String res3 = seq3.charAt(s3Length-1) + result3;
                backTrack(seq1.substring(0, s1Length-1),seq2,seq3.substring(0,s3Length-1),table,matrix,res1,res2,res3,gap,full);
                if (!full) return;
            }
            if(s2Length>0 && s3Length>0 && table[s1Length][s2Length][s3Length]==table[s1Length][s2Length-1][s3Length-1]+matrix.get(v23)+2*gap){
                String res1 = "-" + result1;
                String res2 = seq2.charAt(s2Length-1) + result2;
                String res3 = seq3.charAt(s3Length-1) + result3;
                backTrack(seq1,seq2.substring(0,s2Length-1),seq3.substring(0,s3Length-1),table,matrix,res1,res2,res3,gap,full);
                if (!full) return;
            }
            if(s1Length>0 && table[s1Length][s2Length][s3Length]==table[s1Length-1][s2Length][s3Length]+2*gap){
                String res1 = seq1.charAt(s1Length-1) + result1;
                String res2 = "-" + result2;
                String res3 = "-" + result3;
                backTrack(seq1.substring(0, s1Length-1),seq2,seq3,table,matrix,res1,res2,res3,gap,full);
                if (!full) return;
            }
            if(s2Length>0 && table[s1Length][s2Length][s3Length]==table[s1Length][s2Length-1][s3Length]+2*gap){
                String res1 = "-" + result1;
                String res2 = seq2.charAt(s2Length-1) + result2;
                String res3 = "-" + result3;
                backTrack(seq1,seq2.substring(0,s2Length-1),seq3,table,matrix,res1,res2,res3,gap,full);
                if (!full) return;
            }
            if(s3Length>0 && table[s1Length][s2Length][s3Length]==table[s1Length][s2Length][s3Length-1]+2*gap){
                String res1 = "-" + result1;
                String res2 = "-" + result2;
                String res3 = seq3.charAt(s3Length-1) + result3;
                backTrack(seq1,seq2,seq3.substring(0,s3Length-1),table,matrix,res1,res2,res3,gap,full);
                if (!full) return;
            }

            return;
        }

        //a min of 2 strings is empty.
        //add rest of sequence 1
        if(s1Length !=0) {
            for (int n = 0; n < s1Length; n++) {
                result1 = seq1.charAt(s1Length - n -1) + result1;
                result2 = "-" + result2;
                result3 = "-" + result3;
            }
        }
        if(s2Length !=0) {
            for (int n = 0; n < s2Length; n++) {
                result1 = "-" + result1;
                result2 = seq2.charAt(s2Length - n -1) + result2;
                result3 = "-" + result3;
            }
        }
        if(s3Length !=0) {
            for (int n = 0; n < s3Length; n++) {
                result1 = "-" + result1;
                result2 = "-" + result2;
                result3 =  seq3.charAt(s3Length - n -1) + result3;
            }
        }

        Sequences1.add(result1);
        Sequences2.add(result2);
        Sequences3.add(result3);
    }


    public ArrayList<String> getSequenses1 (){
        return Sequences1;
    }
    public ArrayList<String> getSequenses2 (){
        return Sequences2;
    }
    public ArrayList<String> getSequenses3 (){
        return Sequences3;
    }
}