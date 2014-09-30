import java.util.List;

/**
 * Created by Simon on 27-09-2014.
 */
public interface ExtendStrategy {

    public List<String> extend(List<String> mult, SinglePairwiseAlignment[] oldAlignments, int center, int current);
}
