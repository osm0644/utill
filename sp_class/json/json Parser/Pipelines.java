package study;
 
import java.util.List;
 
public class Pipelines {
    private Input INPUT;
    private Replace REPLACE;
    private Split SPLIT;
    private List<Output> OUTPUT;
     
    @Override
    public String toString() {
        return "Pipelines [INPUT=" + INPUT + ", REPLACE=" + REPLACE + ", SPLIT=" + SPLIT + ", OUTPUT=" + OUTPUT + "]";
    }
 
}
