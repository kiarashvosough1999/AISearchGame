package GameUtils;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {

    public int compare(State state1, State state2) {
        if((state1.getCostUntilNow() + state1.getEstimatedCostToGoal()) > (state2.getCostUntilNow() + state2.getEstimatedCostToGoal()))
            return 1;
        if((state1.getCostUntilNow() + state1.getEstimatedCostToGoal()) < (state2.getCostUntilNow() + state2.getEstimatedCostToGoal()))
            return -1;
        else
            return 0;
    }
}
