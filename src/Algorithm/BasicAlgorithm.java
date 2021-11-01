package Algorithm;

import GameUtils.BasicSearchRequirement;

public abstract class BasicAlgorithm implements BasicSearchRequirement {

    protected int numberOfExpandedNodes;

    public BasicAlgorithm() {
        this.numberOfExpandedNodes = 0;
    }

    public int getNumberOfExpandedNodes() {
        return numberOfExpandedNodes;
    }

    public void setNumberOfExpandedNodes(int numberOfExpandedNodes) {
        this.numberOfExpandedNodes = numberOfExpandedNodes;
    }

    public void incrementNumberOfExpandedNodes() {
        numberOfExpandedNodes++;
    }
}