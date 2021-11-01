package Algorithm.UnInformed;

import Algorithm.BasicAlgorithm;
import GameUtils.LimitedSearch;
import GameUtils.SimpleSearch;
import GameUtils.State;

public class IDS extends BasicAlgorithm implements SimpleSearch {

    private LimitedSearch dls;
    private int limit;

    public IDS(int limit) {
        super();
        this.limit = limit;
        dls = new DLS();
    }

    public void search(State initialNode){
        for(int i = 0 ; i <= limit ; i++){
            if(dls.search(initialNode, i)){
                numberOfExpandedNodes += dls.getNumberOfExpandedNodes();
                return;
            }
            numberOfExpandedNodes += dls.getNumberOfExpandedNodes();
        }
    }
}
