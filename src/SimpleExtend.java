import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 27-09-2014.
 */
public class SimpleExtend implements ExtendStrategy {

    @Override
    public List<String> extend(List<String> mult, String s1, String si) {
        if(mult.size() == 0){
            mult.add(s1);
            mult.add(si);
            return mult;
        }

        List<Integer> insertsPre = new ArrayList<Integer>();
        List<Integer> insertsNew = new ArrayList<Integer>();


        String pre = mult.get(0);

        for(int i = 0; i < pre.length(); i++){
            if(pre.charAt(i) == '-'){
                if(i > s1.length()-1 || s1.charAt(i) != '-') {
                    insertsPre.add(i);
                }
            }
        }


        for(int i = 0; i < s1.length(); i++){
            if(s1.charAt(i)== '-'){
                if(i > pre.length()-1 || pre.charAt(i) != '-'){
                insertsNew.add(i);
            }}
        }

        int insertCount = 0;
        for(int i : insertsPre){
                si = si.substring(0, i) + "-" + si.substring(i);
                insertCount++;
        }


        for(int k = 0; k < mult.size(); k++){
            String sequence = mult.get(k);
            insertCount = 0;
            for(int i : insertsNew){
                    sequence = sequence.substring(0, i) + "-" + sequence.substring(i);
                    insertCount++;
            }
            mult.remove(k);
            mult.add(k, sequence);
        }




        mult.add(si);
        return mult;
    }
}
