import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Nils Henning on 02-09-2014.
 */
public class Util {

    public static int min3(int v1, int v2, int v3){
        return Math.min(v1,Math.min(v2,v3));
    }
    public static int min7(int v1, int v2, int v3, int v4, int v5, int v6, int v7){
        return Math.min(v1,Math.min(v2,Math.min(v3,Math.min(v4,Math.min(v5,Math.min(v6,v7))))));
    }

    public static int computeScoreMulti(List<String> alignment, int gap, Map<String, Integer> costMatrix){
        //start by making basic check on lengths of the strings
        //should probably throw an exception instead
        int length = alignment.get(0).length();
        for(String s : alignment){
            if(s.length() != length){
                System.out.println("Not all sequences are same length. Invalid Alignment");
                return 0;
            }
        }

        //count is for debugging
        int count = 0;
        int score = 0;
        String pair = "";
        System.out.println("length: " + length);

        //for every index, compute every combination of alignment scores
        for(int i = 0; i < length; i++){

            for(int j = 0; j < alignment.size(); j++){
                char jChar = alignment.get(j).charAt(i);

                for(int k = j+1; k < alignment.size(); k++){
                    count++;
                    char kChar = alignment.get(k).charAt(i);

                    //check if there is a gap, but not "--"
                    if(jChar =='-' || kChar == '-'){
                        if(jChar != kChar) {
                            score += gap;
                        }
                    }else {
                        pair = "" + jChar + kChar;
                        score += costMatrix.get(pair);
                    }
                }
            }
        }
        System.out.println("count: " + count);

        return score;
    }
}
