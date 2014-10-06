import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 27-09-2014.
 */
public class SimpleExtend implements ExtendStrategy {


    @Override
    public List<String> extend(List<String> mult, SinglePairwiseAlignment[] oldAlignments, int center, int current) {


        String s1;
        String si;

        if(current < center) {
            s1 = oldAlignments[current].getSeq2();
            si = oldAlignments[current].getSeq1();
        }else{
            s1 = oldAlignments[current].getSeq1();
            si = oldAlignments[current].getSeq2();
        }
        //simple case of empty m-table
        if (mult.size() == 0) {
            mult.add(s1);
            mult.add(si);
            return mult;
        }


        //gaps in the existing center sequence
        List<Integer> insertsPre = new ArrayList<Integer>();
        //gaps in the new center sequence, fro alignment with si
        List<Integer> insertsNew = new ArrayList<Integer>();


        String pre = mult.get(0);

        System.out.println(s1);
        System.out.println(si);
        System.out.println();

        int a = 0, b = 0;

        int addedToSi = 0;
        try {

            while (a < pre.length() && b < si.length()) {
                if (pre.charAt(a) == '-' && s1.charAt(b - addedToSi) != '-') {
                    si = si.substring(0, a) + "-" + si.substring(a);
                    addedToSi++;
                } else if (pre.charAt(a) != '-' && s1.charAt(b - addedToSi) == '-') {
                    pre = pre.substring(0, a) + "-" + pre.substring(a);
                }
                a++;
                b++;
            }

        }catch(StringIndexOutOfBoundsException e){
            System.out.println(a + ", " + b);
            System.out.println(addedToSi);
            System.out.println(si.length());
            System.out.println(s1.length());
            System.out.println(pre.length());
            throw e;
        }

        if(pre.length() < si.length()){
            for(int i = pre.length(); i <si.length(); i++){
                pre += "-";
            }
        }
        if(si.length() < pre.length()){
            for(int i = si.length(); i <pre.length(); i++){
                si += "-";
            }
        }

        String oldS1 = mult.get(0);
        mult.remove(0);
        mult.add(0, pre);

        for(int i = 1; i < mult.size(); i++){
            String sequence = mult.get(i);


            a = 0;
            int inserted = 0;
            while(a < pre.length()&& a-inserted < oldS1.length()){
                if(pre.charAt(a) == '-'  && oldS1.charAt(a-inserted) != '-' ){
                        sequence = sequence.substring(0, a) + '-' + sequence.substring(a);
                        inserted++;
//                    }
                }
                a++;
            }
            while(sequence.length() < pre.length()) {

                sequence += "-";



            }
            mult.remove(i);
            mult.add(i, sequence);
        }

        mult.add(si);

        return mult;
    }
}
