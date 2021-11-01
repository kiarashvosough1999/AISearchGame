package GameUtils;

public interface LimitedSearch extends BasicSearchRequirement {
    public boolean search(State initialState,int limit);
}
