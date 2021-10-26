package Helper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import Algorithm.AlgorithmType;
import GameUtils.Color;
import GameUtils.State;

public class ResultUtil {

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
                FileWriter myWriter = new FileWriter("DLS-result(IDS).txt");
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
}