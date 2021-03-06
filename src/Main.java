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

import Algorithm.Informed.AStar;
import Algorithm.Informed.GBFS;
import Algorithm.Informed.IDAStar;
import Algorithm.Informed.RBFS;
import Algorithm.UnInformed.BDS;
import Algorithm.UnInformed.BFS;
import Algorithm.UnInformed.DFS;
import Algorithm.UnInformed.IDS;
import Algorithm.UnInformed.UCS;
import GameUtils.BidirectionalSearch;
import GameUtils.Color;
import GameUtils.Graph;
import GameUtils.Node;
import GameUtils.SimpleSearch;
import GameUtils.State;
import Helper.ResultUtil;

public class Main {

    public static void main(String[] args) {

        //-----------------------------------------------> test1 :
//        Graph initialGraph= new Graph(5);
//        initialGraph.addNode(new Node(0, Color.Red));
//        initialGraph.addNode(new Node(1, Color.Red));
//        initialGraph.addNode(new Node(2, Color.Red));
//        initialGraph.addNode(new Node(3, Color.Green));
//        initialGraph.addNode(new Node(4, Color.Red));
//        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(1));
//        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(2));
//        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(3));
//        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
//        initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(4));

        //-----------------------------------------------> test2 :
        Graph initialGraph= new Graph(7);
        initialGraph.addNode(new Node(0, Color.Red));
        initialGraph.addNode(new Node(1, Color.Black));
        initialGraph.addNode(new Node(2, Color.Green));
        initialGraph.addNode(new Node(3, Color.Red));
        initialGraph.addNode(new Node(4, Color.Red));
        initialGraph.addNode(new Node(5, Color.Green));
        initialGraph.addNode(new Node(6, Color.Red));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(4));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(3));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(4));
        initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(5));
        initialGraph.addLinkBetween(initialGraph.getNode(4), initialGraph.getNode(5));
        initialGraph.addLinkBetween(initialGraph.getNode(5), initialGraph.getNode(6));

        //-----------------------------------------------> test3 :
    //    Graph initialGraph= new Graph(15);
    //    initialGraph.addNode(new Node(0, Color.Red));
    //    initialGraph.addNode(new Node(1, Color.Black));
    //    initialGraph.addNode(new Node(2, Color.Black));
    //    initialGraph.addNode(new Node(3, Color.Black));
    //    initialGraph.addNode(new Node(4, Color.Red));
    //    initialGraph.addNode(new Node(5, Color.Green));
    //    initialGraph.addNode(new Node(6, Color.Green));
    //    initialGraph.addNode(new Node(7, Color.Red));
    //    initialGraph.addNode(new Node(8, Color.Red));
    //    initialGraph.addNode(new Node(9, Color.Green));
    //    initialGraph.addNode(new Node(10, Color.Red));
    //    initialGraph.addNode(new Node(11, Color.Red));
    //    initialGraph.addNode(new Node(12, Color.Red));
    //    initialGraph.addNode(new Node(13, Color.Green));
    //    initialGraph.addNode(new Node(14, Color.Red));

    //    initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(1));
    //    initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(2));
    //    initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(14));
    //    initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
    //    initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(3));
    //    initialGraph.addLinkBetween(initialGraph.getNode(2), initialGraph.getNode(5));
    //    initialGraph.addLinkBetween(initialGraph.getNode(2), initialGraph.getNode(6));
    //    initialGraph.addLinkBetween(initialGraph.getNode(2), initialGraph.getNode(7));
    //    initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(13));
    //    initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(14));
    //    initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(7));
    //    initialGraph.addLinkBetween(initialGraph.getNode(4), initialGraph.getNode(6));
    //    initialGraph.addLinkBetween(initialGraph.getNode(4), initialGraph.getNode(11));
    //    initialGraph.addLinkBetween(initialGraph.getNode(5), initialGraph.getNode(10));
    //    initialGraph.addLinkBetween(initialGraph.getNode(5), initialGraph.getNode(12));
    //    initialGraph.addLinkBetween(initialGraph.getNode(6), initialGraph.getNode(11));
    //    initialGraph.addLinkBetween(initialGraph.getNode(7), initialGraph.getNode(8));
    //    initialGraph.addLinkBetween(initialGraph.getNode(7), initialGraph.getNode(9));
    //    initialGraph.addLinkBetween(initialGraph.getNode(8), initialGraph.getNode(14));


        State initialState= new State(initialGraph, -1, null);
        

        BidirectionalSearch bds = null;
        SimpleSearch search = null;

        
        // uninformed

        // Test DFS
        // tested
        // search = new DFS();
        // search.search(initialState);


        // Test BFS
        // testes
        // search = new BFS();
        // search.search(initialState);

        // Test IDS
        // testet
        // search = new IDS(5);
        // search.search(initialState);

        // Test UCS
        // tested
        // search = new UCS();
        // search.search(initialState);

        // Test BDS
        // tested
        // bds = new BDS();
        // bds.search(initialState,  ((BFS) search).searchAndReturnFinalState(initialState));

        // informed
       
        // AStar
        // tested
        // search = new AStar();
        // search.search(initialState);

        // IDAStar
        // tested
        // search = new IDAStar();
        // search.search(initialState);

        // RBFS
        // tested
        // search = new RBFS();
        // search.search(initialState);

        // GBFS
        // tested
        // search = new GBFS();
        // search.search(initialState);

        ResultUtil.nodeExpansionResultgenerator(search != null ? search : bds);
    }
}
