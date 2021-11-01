package Algorithm;

import GameUtils.State;

public class IDS {

    private int expandedNodes = 0;

    private DLS dls;

    public IDS() {
        dls = new DLS();
    }

    public void search(State initialNode, int limit){
        for(int i=0 ; i<=limit ; i++){
            if(dls.search(initialNode, i)){
                expandedNodes += dls.expandedNodes;
                return;
            }
            expandedNodes += dls.expandedNodes;
        }
    }
}
