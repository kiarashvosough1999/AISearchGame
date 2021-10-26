package Algorithm;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import GameUtils.State;
import Helper.ResultUtil;

public class DFS {

    int nodeExpanded = 0;

    public void search(State initialState){
        Stack<State> frontier = new Stack<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if(ResultUtil.isGoal(initialState)){
            ResultUtil.result(initialState, AlgorithmType.DFS);
            return;
        }
        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);
        while (!frontier.isEmpty()){
            State temp = frontier.pop();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(),true);
            nodeExpanded++;
            ArrayList<State> children = temp.successor();
            //System.out.println(temp.hash());
            for(int i = 0;i<children.size();i++){
                if(!(inFrontier.containsKey(children.get(i).hash())) && !(explored.containsKey(children.get(i).hash()))) {
                    if (ResultUtil.isGoal(children.get(i))) {
                        ResultUtil.result(children.get(i), AlgorithmType.DFS);
                        return;
                    }
                    frontier.push(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }
}