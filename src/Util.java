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
}
