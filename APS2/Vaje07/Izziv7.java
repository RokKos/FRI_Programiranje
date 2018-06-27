import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Izziv7 {
  private static class Pair<T, S> {
    private T first;
    private S second;

    Pair(T _first, S _second) {
      first = _first;
      second = _second;
    }

    public T getFirst() {
      return first;
    }

    public S getSecond() {
      return second;
    }

    public void setFirst(T _first) {
      first = _first;
    }

    public void setSecond(S _second) {
      second = _second;
    }
  }

  private static class Node {
    int id;
    // marks for the algorithm
    //------------------------------------
    boolean marked = false;
    boolean pozitive = false;
    Edge augmEdge = null; // the edge over which we brought the flow increase
    int incFlow = -1; //-1 means a potentially infinite flow
    //------------------------------------
    private ArrayList<Edge> inEdges;
    private ArrayList<Edge> outEdges;

    public Node(int i) {
      id = i;
      inEdges = new ArrayList<Edge>();
      outEdges = new ArrayList<Edge>();
    }

    public ArrayList<Edge> GetInEdges() {
      return inEdges;
    }

    public ArrayList<Edge> GetOutEdges() {
      return outEdges;
    }
  }

  private static class Edge {
    int startID;
    int endID;
    int capacity;
    int currFlow;

    public Edge(int fromNode, int toNode, int capacity2) {
      startID = fromNode;
      endID = toNode;
      capacity = capacity2;
      currFlow = 0;
    }
  }

  private static class Network {
    private Node[] nodes;

    /**
     * Create a new network with n nodes (0..n-1).
     * @param n the size of the network.
     */
    public Network(int n) {
      nodes = new Node[n];
      for (int i = 0; i < nodes.length; i++) {
        nodes[i] = new Node(i);
      }
    }

    /**
     * Add a connection to the network, with all the corresponding in and out edges.
     * @param fromNode
     * @param toNode
     * @param capacity
     */
    public void addConnection(int fromNode, int toNode, int capacity) {
      Edge e = new Edge(fromNode, toNode, capacity);
      nodes[fromNode].outEdges.add(e);
      nodes[toNode].inEdges.add(e);
    }

    /**
     * Reset all the marks of the algorithm, before the start of a new iteration.
     */
    public void resetMarks() {
      for (int i = 0; i < nodes.length; i++) {
        nodes[i].marked = false;
        nodes[i].pozitive = false;
        nodes[i].augmEdge = null;
        nodes[i].incFlow = -1;
      }
    }

    public void setMark(int id, boolean _pozitive, Edge _augmEdge, int _incFlow) {
      nodes[id].marked = true;
      nodes[id].pozitive = _pozitive;
      nodes[id].augmEdge = _augmEdge;
      nodes[id].incFlow = _incFlow;
    }

    public Node GetNode(int id) {
      return nodes[id];
    }

    public int GetSizeNetwork() {
      return nodes.length;
    }
  }

  public static boolean FindPath(Network net) {
    net.resetMarks();
    PriorityQueue<Pair<Integer, Boolean>> q =
        new PriorityQueue<Pair<Integer, Boolean>>(new Comparator<Pair<Integer, Boolean>>() {
          @Override
          public int compare(Pair<Integer, Boolean> a, Pair<Integer, Boolean> b) {
            if (a.getFirst() > b.getFirst()) {
              return 1;
            } else if (a.getFirst() < b.getFirst()) {
              return -1;
            }

            return 0;
          }

        });

    boolean[] obs = new boolean[net.GetSizeNetwork()];
    q.add(new Pair<Integer, Boolean>(0, false));
    net.setMark(0, false, null, -1);

    while (!q.isEmpty()) {
      Pair<Integer, Boolean> el = q.poll();
      int id = el.first;
      boolean poz = el.second;
      System.out.println("id: " + id + "poz: " + poz);

      if (obs[id]) {
        System.out.println("obiskan");
        continue;
      }

      boolean ponor_obiskan = net.GetNode(net.GetSizeNetwork() - 1).marked;
      if (ponor_obiskan) {
        System.out.println("ponor");
        break;
      }

      Node node = net.GetNode(id);

      ArrayList<Edge> outPov = node.GetOutEdges();
      for (int i = 0; i < outPov.size(); ++i) {
        int idEnd = outPov.get(i).endID;
        Node endNode = net.GetNode(idEnd);
        int diff = outPov.get(i).capacity - outPov.get(i).currFlow;
        if (!endNode.marked && diff > 0) {
          int flow = node.incFlow == -1 ? diff : Math.min(node.incFlow, diff);
          net.setMark(idEnd, true, outPov.get(i), flow);
          q.add(new Pair<Integer, Boolean>(idEnd, true));
        }
      }

      ArrayList<Edge> inPov = node.GetInEdges();
      for (int i = 0; i < inPov.size(); ++i) {
        int idEnd = inPov.get(i).endID;
        Node endNode = net.GetNode(idEnd);
        if (!endNode.marked && inPov.get(i).currFlow > 0) {
          int flow = node.incFlow == -1 ? inPov.get(i).currFlow
                                        : Math.min(node.incFlow, inPov.get(i).currFlow);
          net.setMark(idEnd, false, inPov.get(i), flow);
          q.add(new Pair<Integer, Boolean>(idEnd, false));
        }
      }
    }

    Node node = net.GetNode(net.GetSizeNetwork() - 1);
    int max_curr_flow = node.incFlow;
    System.out.print(max_curr_flow + ": ");
    boolean flag = false;
    while (node != null) {
      System.out.print(node.id);

      if (node.id == 0) {
        break;
      }
      System.out.print(node.pozitive ? "+ " : "- ");
      node.augmEdge.currFlow += max_curr_flow;

      node = net.GetNode(node.augmEdge.startID);
      flag = true;
    }

    System.out.println();

    return flag;
  }

  // private static void FillPath(Network net, ArrayList<Node> path) {}

  private static void FordFulkersonMaxFlow(Network net) {
    while (FindPath(net)) {
    }
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = Integer.parseInt(args[0]);
    Network network = new Network(n);

    while (in.hasNextInt()) {
      int izvor = in.nextInt();
      int ponor = in.nextInt();
      int cap = in.nextInt();

      network.addConnection(izvor, ponor, cap);
    }

    FordFulkersonMaxFlow(network);
  }
}