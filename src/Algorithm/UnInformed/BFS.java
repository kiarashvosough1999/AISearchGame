//  Copyright 2020 KiarashVosough and other contributors
//
//  Permission is hereby granted, free of charge, to any person obtaining
//  a copy of this software and associated documentation files (the
//  Software"), to deal in the Software without restriction, including
//  without limitation the rights to use, copy, modify, merge, publish,
//  distribute, sublicense, and/or sell copies of the Software, and to
//  permit persons to whom the Software is furnished to do so, subject to
//  the following conditions:
//
//  The above copyright notice and this permission notice shall be
//  included in all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
//  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
//  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
//  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package Algorithm.UnInformed;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import Algorithm.BasicAlgorithm;
import GameUtils.SimpleSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

 public class BFS extends BasicAlgorithm implements SimpleSearch {

    public BFS() {
        super();
    }

    public void search(State initialState) {
        Queue<State> frontier = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        if(ResultUtil.isGoal(initialState)){
            ResultUtil.result(initialState, AlgorithmType.BFS);
            return;
        }
        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);
        while (!frontier.isEmpty()){
            State tempState = frontier.poll();
            inFrontier.remove(tempState.hash());
            explored.put(tempState.hash(),true);
            numberOfExpandedNodes++;
            ArrayList<State> children = tempState.successor();
            for(int i = 0;i<children.size();i++){
                if(!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    if (ResultUtil.isGoal(children.get(i))) {
                        ResultUtil.result(children.get(i), AlgorithmType.BFS);
                        return;
                    }
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }

    public State searchAndReturnFinalState(State initialState){

        Queue<State> frontier = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(initialState)){
            return initialState;
        }

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);

        while (!frontier.isEmpty()){

            State tempState = frontier.poll();
            inFrontier.remove(tempState.hash());
            explored.put(tempState.hash(),true);

            ArrayList<State> children = tempState.successor();

            for (State state : children) {
                if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    if (ResultUtil.isGoal(state)) {
                        return state;
                    }
                    frontier.add(state);
                    inFrontier.put(state.hash(), true);
                }
            }
        }
        return null;
    }


    public int heuristicSearch(State initialState){

        Queue<State> frontier = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(initialState)){
            return ResultUtil.heuristicResult(initialState);
        }

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);

        while (!frontier.isEmpty()){

            State temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(),true);

            ArrayList<State> children = temp.successor();

            for (State state : children) {
                if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    if (ResultUtil.isGoal(state)) {
                        return ResultUtil.heuristicResult(state);
                    }
                    frontier.add(state);
                    inFrontier.put(state.hash(), true);
                }
            }
        }
        return Integer.MAX_VALUE / 2;
    }
}


