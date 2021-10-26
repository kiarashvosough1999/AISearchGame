package Algorithm;

public enum AlgorithmType {
    BFS, DFS, DLS;

    public String filePath() {
        switch (this) {
            case BFS: return "BfsResult.txt";
            case DFS: return "DfsResult.txt";
            case DLS: return "DlsResult.txt";
            default: return "";
        }
    }
}
