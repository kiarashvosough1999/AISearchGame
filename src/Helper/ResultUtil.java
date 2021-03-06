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

package Helper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import Algorithm.UnInformed.BFS;
import GameUtils.BasicSearchRequirement;
import GameUtils.Color;
import GameUtils.Graph;
import GameUtils.Node;
import GameUtils.State;

public class ResultUtil {

    public static void nodeExpansionResultgenerator(BasicSearchRequirement search) {
        System.out.printf("number of nodes expanded %d", search.getNumberOfExpandedNodes());
    }
    public static int heuristicResult(State state) {
        Stack<State> states = new Stack<State>();
        while (true){
            states.push(state);
            if(state.getParentState() == null){
                break;
            }
            else {
                state = state.getParentState();
            }
        }
        states.pop();
        return states.size();
    }

    public static boolean isGoal(State state){
        for (int i = 0; i < state.getGraph().size(); i++) {
            if(state.getGraph().getNode(i).getColor() == Color.Red
                    || state.getGraph().getNode(i).getColor() == Color.Black){
                return false;
            }
        }
        return true;
    }

    public static void result(State state, AlgorithmType type){
        if(state == null){
            try {
                FileWriter myWriter = new FileWriter(type.filePath());
                myWriter.close();
            }  catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            } finally {
                return;
            }
        }
        Stack<State>  states = new Stack<State>();
        while (true){
            states.push(state);
            if(state.getParentState() == null){
                break;
            }
            else {
                state = state.getParentState();
            }
        }
        try {
            FileWriter myWriter = new FileWriter(type.filePath());
            System.out.println("initial state : ");
            while (!states.empty()){
                State tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId()+" ,");
                myWriter.write(tempState.outputGenerator()+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void reverseResult(State frontNode, State backNode, AlgorithmType type){
        if( frontNode == null && backNode == null ){
            try {
                FileWriter myWriter = new FileWriter(type.filePath());
                myWriter.close();
            }  catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        Stack<State>  states = new Stack<State>();
        while (true){
            states.push(frontNode);
            if(frontNode.getParentState() == null){
                break;
            }
            else {
                frontNode = frontNode.getParentState();
            }
        }

        try {
            FileWriter myWriter = new FileWriter(type.filePath());
            System.out.println("final state : ");
            while (!states.empty()){
                State tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId()+" ,");
                myWriter.write(tempState.outputGenerator()+"\n");
            }
            // in order not to consider intresection node we get its parent
            backNode = backNode.getParentState();

            while (true){
                if(backNode.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + backNode.getSelectedNodeId());
                }
                backNode.getGraph().print();

                myWriter.write(backNode.getSelectedNodeId()+" ,");
                myWriter.write(backNode.outputGenerator()+"\n");
                if(backNode.getParentState() == null){
                    break;
                }
                else {
                    backNode = backNode.getParentState();
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static public Graph relaxGraph(Graph graph){
        Graph newGame = graph.copy();

        for (Node node : newGame.getNodes()) {
            if (node.getColor() == Color.Black) {
                node.setColor(Color.Green);
            }
        }
        return newGame;
    }

    static public int heuristic(State state){
        // first we relax graph
        Graph relaxedGraph = relaxGraph(state.getGraph());

        State temp = new State(relaxedGraph, state.getSelectedNodeId(), null);

        BFS bfs = new BFS();
        // we find path to goal with relaxed map and bfs algorithm
        return bfs.heuristicSearch(temp);
    }

    static public boolean isMinCost(Hashtable<String, Integer> costs,State state){
        Integer cost = Integer.MAX_VALUE;
        try {
            cost = costs.get(state.hash()).intValue();
        }
        catch (Exception e){
            return false;
        }
        Enumeration<String> enumeration = costs.keys();
        while(enumeration.hasMoreElements()){
            if (cost > costs.get(enumeration.nextElement()).intValue()){
                return false;
            }
        }
        return true;
    }
}