import java.util.ArrayList;

/**
 * Created by Nils Henning on 23-09-2014.
 */
public class naiveExtend implements Extend {
    @Override
    public String[] extend(String basePreAlign, String basePostAlign, String extend, String[] table) {
        ArrayList<Integer> inserts = new ArrayList<Integer>();
        char[] basePost = basePostAlign.toCharArray();
        char[] basePre = basePreAlign.toCharArray();
        int j = 0;
        for(int i = 0; i<basePost.length-1;i++){
            if(basePost[i]=='-' && !(basePost[i]==basePre[j])){
                inserts.add(i+ inserts.size());
            }else {
              j++;
            }
        }
        for(int i=0; i<table.length;i++){
            if(table[i] == null){
                table[i]=extend;
                break;
            }
            //insert gaps
            for(int index : inserts){
                table[i]=table[i].substring(0,index)+"-"+table[i].substring(index);
            }
        }
        return table;
    }
}
