import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
            seq1 = FASTAParser.Parse("input/"+seq1+".fasta").get(0);
        } catch (IOException e) {
            seq1 = "";
        }
        try {
            seq2 = FASTAParser.Parse("input/"+seq2+".fasta").get(0);
        } catch (IOException e) {
            seq2="";
        }
        try {
            seq3 = FASTAParser.Parse("input/"+seq3+".fasta").get(0);
        } catch (IOException e) {
            seq3="";
        }
        try {
            matrix = matrixParser.Parse("input/scoreMatrix.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Beginning exact alignment. Score:");
        int[][][] score = multiAlignmentExact.fillTable("AAGGCCTT", "GGCCTTAA", "TTAAGGCC", matrix, gap);
//        System.out.println(score[seq1.length()][seq2.length()][seq3.length()]);
        System.out.println(score[8][8][8]);
        System.out.println();

        MultipleAlignmentExact3 myExact = new MultipleAlignmentExact3("AAGGCCTT", "GGCCTTAA", "TTAAGGCC", matrix, gap);
        myExact.print();
        myExact.backTrack(false);
        myExact.print();

//        Exact3BackTrack backTrack = new Exact3BackTrack();
//        backTrack.backTrack("AAGGCCTT","GGCCTTAA","TTAAGGCC",score,matrix,"","","",gap,false);



        int alignmentScore = Util.computeScoreMulti(myExact.getAlignment(), 5, matrix);
        System.out.println("Score of exact alignment: " + alignmentScore);

//        List<String> sequences = new ArrayList<String>();
//        sequences.add(seq1);
//        sequences.add(seq2);
//        sequences.add(seq3);

        List<String> sequences = new ArrayList<String>();
        sequences.add("AAGGCCTT");
        sequences.add("GGCCTTAA");
        sequences.add("TTAAGGCC");
        sequences.add("TTAACCCC");
        sequences.add("TTGAGGAC");
        sequences.add("TAAAGGCT");



//        MultipleAlignmentApprox alignment = new MultipleAlignmentApprox(sequences, matrix, 5, new NaiveExtend());
//        alignment.print();

//        String[] input = {seq1,seq2,seq3};
//        String[] input = {"AAGGCCTT","GGCCTTAA","TTAAGGCC"};
//        input = multAlignmentApprox.findApprox(input,matrix,gap,new weirdExtend());
//        System.out.println(input[0]);
//        System.out.println();
//        System.out.println(input[1]);
//        System.out.println();
//        System.out.println(input[2]);

    }
}
