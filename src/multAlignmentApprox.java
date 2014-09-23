import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by Nils Henning on 23-09-2014.
 */
public class multAlignmentApprox {

    public static String[] findApprox(String[] sequences, Map<String, Integer> matrix, int gap,Extend extend) {
        int bestValue=Integer.MAX_VALUE;
        int bestSeq=0;
        int value = 0;
        for(int i = 0; i<sequences.length;i++){
            value = 0;
            for(int index=0; index<sequences.length;index++){
                if(index==i)continue;
                value += linearAlignment.fillTableLinear(sequences[i],sequences[index],matrix,gap)[sequences[i].length()][sequences[index].length()];
            }
            if(value > bestValue)bestSeq=i;
        }
        String[] multAlignment = new String[sequences.length];
        multAlignment[0]=sequences[bestSeq];
        linearCostBackTrack backTrack = new linearCostBackTrack();
        int[][] table;
        for(int i = 0; i<sequences.length;i++){
            if(i==bestSeq)continue;
            table = linearAlignment.fillTableLinear(multAlignment[0],sequences[i],matrix,gap);
            backTrack.resetSequenses();
            backTrack.backTrack(multAlignment[0], sequences[i], table, matrix, "", "", false, gap);
            multAlignment = extend.extend(backTrack.getSequenses1().get(0), backTrack.getSequenses2().get(0), multAlignment);
        }
        return multAlignment;
    }
}