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

//        System.out.println("Beginning exact alignment. Score:");
//        int[][][] score = multiAlignmentExact.fillTable(seq1, seq2, seq3, matrix, gap);
////        System.out.println(score[seq1.length()][seq2.length()][seq3.length()]);
//        System.out.println(score[seq1.length()][seq2.length()][seq3.length()]);
//        System.out.println();
//
//        MultipleAlignmentExact3 myExact = new MultipleAlignmentExact3(seq1, seq2, seq3, matrix, gap);
//        myExact.print();
//        myExact.backTrack(false);
//        myExact.print();

//        Exact3BackTrack backTrack = new Exact3BackTrack();
//        backTrack.backTrack("AAGGCCTT","GGCCTTAA","TTAAGGCC",score,matrix,"","","",gap,false);



//        int alignmentScore = Util.computeScoreMulti(myExact.getAlignment(), 5, matrix);
//        System.out.println("Score of exact alignment: " + alignmentScore);

//        List<String> sequences = new ArrayList<String>();
//        sequences.add(seq1);
//        sequences.add(seq2);
//        sequences.add(seq3);

        List<String> sequences = new ArrayList<String>();
//        sequences.add("AAGGCCTT");
//        sequences.add("GGCCTTAA");
//        sequences.add("TTAAGGCC");
//        sequences.add("TTAACCCC");
//        sequences.add("TTGAGGAC");
//        sequences.add("TAAAGGCT");
        sequences.add(seq1);
        sequences.add(seq2);
        sequences.add(seq3);



//        MultipleAlignmentApprox alignment = new MultipleAlignmentApprox(sequences, matrix, 5, new SimpleExtend());
//        alignment.print();
//        System.out.println(Util.computeScoreMulti(alignment.getAlignment(), gap, matrix));


        try {
            sequences = FASTAParser.Parse("input/brca1-testseqs.fasta");
            sequences.remove(7);
            sequences.remove(6);
            sequences.remove(5);

        } catch (IOException e) {
            e.printStackTrace();
        }

//        String[] combination = new String[24];
//        int[] scores = new int[24];
//
//        int count = 0;
//
//        ArrayList<String> orderedSequences;
//        for(int a = 1; a <= 4; a++){
//            for(int b = 1; b <= 4; b++){
//                if(b == a){
//                    continue;
//                }
//                for(int c = 1; c <= 4; c++){
//                    if(c == a || c == b){
//                        continue;
//                    }
//                    for(int d = 1; d <= 4; d++){
//                        if(d == a || d == b || d == c){
//                            continue;
//                        }
//
//                        orderedSequences = new ArrayList<String>();
//                        orderedSequences.add(sequences.get(0));
//                        orderedSequences.add(sequences.get(a));
//                        orderedSequences.add(sequences.get(b));
//                        orderedSequences.add(sequences.get(c));
//                        orderedSequences.add(sequences.get(d));
//
//                        alignment = new MultipleAlignmentApprox(orderedSequences, matrix, 5, new SimpleExtend());
//
//                        scores[count] = Util.computeScoreMulti(alignment.getAlignment(), gap, matrix);
//                        combination[count] = "" + a+"," + b+","+c+","+d;
//                        count++;
//
//
//
//                    }
//                }
//            }
//        }
//        System.out.println("results of 24 orders:");
//        for(int i = 0; i < 24; i++){
//            System.out.println(combination[i] + ": " + scores[i]);
//        }


//        alignment = new MultipleAlignmentApprox(sequences, matrix, 5, new SimpleExtend());
//        alignment.print();
//        System.out.println(Util.computeScoreMulti(alignment.getAlignment(), gap, matrix));

        int[] exactScores = new int[20];
        int[] approxScores = new int[20];


        for(int i = 0; i < 20; i++){
            try {
                sequences = FASTAParser.Parse("input/testseqs_"+(i+1)*10+"_3.fasta");
            } catch (IOException e) {
                e.printStackTrace();
            }

            MultipleAlignmentExact3 myExact = new MultipleAlignmentExact3(sequences.get(0), sequences.get(1), sequences.get(2), matrix, gap);
            myExact.backTrack(false);
            exactScores[i] = Util.computeScoreMulti(myExact.getAlignment(), gap, matrix);

            MultipleAlignmentApprox myApprox = new MultipleAlignmentApprox(sequences, matrix, gap, new SimpleExtend());
            approxScores[i] = Util.computeScoreMulti(myApprox.getAlignment(), gap, matrix);
        }

        System.out.println("scores of 20 fastas:");

        for(int i = 0; i < 20; i++){
            System.out.println(exactScores[i] + ", " + approxScores[i]);
        }


    }
}
