import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * Created by Nils Henning on 20-09-2014.
 */
public class Main {

    public static void main(String[] args){

        //run experiments
        if(args[0].equals("experiment")){
            int a = Integer.parseInt(args[1]);
            int b = Integer.parseInt(args[2]);
            //experiment(a,b);
            return;
        }

        int gap = Integer.parseInt(args[0]);
        String seq1 = args[1];
        String seq2 = args[2];
        String seq3 = args[3];
        Map matrix = null;
        try {
            seq1 = FASTAParser.Parse("input/"+seq1+".fasta");
        } catch (IOException e) {
            seq1 = "";
        }
        try {
            seq2 = FASTAParser.Parse("input/"+seq2+".fasta");
        } catch (IOException e) {
            seq2="";
        }
        try {
            seq3 = FASTAParser.Parse("input/"+seq3+".fasta");
        } catch (IOException e) {
            seq3="";
        }
        try {
            matrix = matrixParser.Parse("input/scoreMatrix.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[][][] score = multiAlignmentExact.fillTableAffine(seq1,seq2,seq3,matrix,gap);
        System.out.println(score[seq1.length()][seq2.length()][seq3.length()]);
        Exact3BackTrack backTrack = new Exact3BackTrack();
        backTrack.backTrack(seq1,seq2,seq3,score,matrix,"","","",gap,false);
        System.out.println(backTrack.getSequenses1().get(0));
        System.out.println();
        System.out.println(backTrack.getSequenses2().get(0));
        System.out.println();
        System.out.println(backTrack.getSequenses3().get(0));

    }
}
