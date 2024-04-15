package org.example.graphify;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.*;

import static org.example.graphify.CanvasController.*;

public class Algorithm {
    @FXML
    static Group canvasGroup;
    @FXML
    static Label sourceText = new Label("Source");

    static Boolean finished = false;
    static StringBuilder all = new StringBuilder();
    int repeats = 0, unioned = 0;

    public void newBFS(Node source) {
        new BFS(source);
    }

    class BFS {

        BFS(Node source) {
            finished = false;
            repeats = 0;

            for (Node n : circles) {
                distances.add(n.distance);
            }

            source.minDistance = 0;
            source.visited = true;
            LinkedList<Node> q = new LinkedList<Node>();
            q.push(source);
            while (!q.isEmpty()) {
                Node u = q.removeLast();

                for (Edge e : u.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        if (v.visited) {
                            repeats++;
                        } else if (!v.visited) {
                            v.minDistance = u.minDistance + 1;
                            v.visited = true;
                            q.push(v);
                            v.previous = u;
                        }
                    }
                }
            }
        }
    }
        public void newDFS(Node source) {
            new DFS(source);
        }

        class DFS {
            DFS(Node source) {
                all = new StringBuilder();
                finished = false;
                repeats = 0;
                for (Node n : circles) {
                    distances.add(n.distance);
                }

                source.minDistance = 0;
                source.visited = true;
                DFSRecursion(source, 0);
            }

            public void DFSRecursion(Node source, int level) {
                for (Edge e : source.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        if (v.visited) {
                            repeats++;
                        } else if (!v.visited) {
                            v.minDistance = source.minDistance + 1;
                            v.visited = true;
                            v.previous = source;
                            DFSRecursion(v, level + 1);
                        }
                    }
                }
            }
        }

        public void newDijkstra(Node source) {
            new Dijkstra(source);
        }

        class Dijkstra {
            Dijkstra(Node source) {
                finished = false;

                for (Node n : circles) {
                    distances.add(n.distance);
                }
                source.minDistance = 0;
                PriorityQueue<Node> pq = new PriorityQueue<Node>();
                pq.add(source);
                while (!pq.isEmpty()) {
                    Node u = pq.poll();
                    if (u.visited) {
                        continue;
                    }
                    u.visited = true;
                    for (Edge e : u.adjacents) {
                        if (e != null) {
                            Node v = e.target;
                            System.out.println("src " + u.name + "tgt " + v.name);
                            if (u.minDistance + e.weight < v.minDistance) {
                                pq.remove(v);
                                v.minDistance = u.minDistance + e.weight;
                                v.previous = u;
                                pq.add(v);
                            }
                        }
                    }
                }
            }
        }

        public void newFloydWarshall(Node source) {
            new FloydWarshall(source);
        }

        class FloydWarshall {
            FloydWarshall(Node source) {
                finished = false;

                int sz = Integer.MIN_VALUE;
                for (Node n : circles) {
                    if (Integer.parseInt(n.name) > sz) {
                        sz = Integer.parseInt(n.name);
                    }
                }
                double[][] dist = new double[sz][sz];
                Edge[][] floydEdges = new Edge[sz][sz];
                Node[] floydNodes = new Node[sz];
                for (int i = 0; i < sz; i++) {
                    for (int j = 0; j < sz; j++) {
                        if (i == j) {
                            dist[i][j] = 0;
                        } else {
                            dist[i][j] = Double.POSITIVE_INFINITY;
                        }
                    }
                }
                for (Edge e : bellmanEdges) {
                    int i = Integer.valueOf(e.source.name) - 1;
                    int j = Integer.valueOf(e.target.name) - 1;
                    floydNodes[i] = e.source;
                    floydNodes[j] = e.target;
                    System.out.println(i + " " + j);
                    dist[i][j] = e.weight;
                    floydEdges[i][j] = e;
                    if (SelectionController.undirected) {
                        dist[j][i] = e.weight;
                        floydEdges[j][i] = e;
                    }
                }

                for (Node n : circles) {
                    distances.add(n.distance);
                }
                for (int k = 0; k < sz; k++) {
                    for (int i = 0; i < sz; i++) {
                        for (int j = 0; j < sz; j++) {
                            if (dist[i][k] + dist[k][j] < dist[i][j]) {
                                dist[i][j] = dist[i][k] + dist[k][j];
                                if (i == Integer.valueOf(source.name) - 1) {
                                    int finalJ = j;
                                    int finalI = i;
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < circles.size(); i++) {
                    for (int j = 0; j < circles.size(); j++) {
                        System.out.println("dist[" + (i + 1) + "][" + (j + 1) + "]: " + dist[i][j]);
                    }
                }
            }
        }

        public void newBellmanFord(Node source) {
            new BellmanFord(source);
        }

        class BellmanFord {

            BellmanFord(Node source) {
                finished = false;

                for (Node n : circles) {
                    distances.add(n.distance);
                }
                source.minDistance = 0;

                double[][] distancesArray = new double[bellmanEdges.size()][circles.size()];
                boolean negativeCycleDetected = false;
                for (int i = 0; i < circles.size() - 1; i++) {
                    Boolean change = false;

                    for (Edge e : bellmanEdges) {
                        Node u = e.source;
                        Node v = e.target;
                        int idx = i;

                        if (u.minDistance != Integer.MAX_VALUE && u.minDistance + e.weight < v.minDistance) {
                            change = true;
                            v.minDistance = u.minDistance + e.weight;
                            v.previous = u;
                            distancesArray[bellmanEdges.indexOf(e)][i] = v.minDistance;

                        } else if (v.minDistance != Integer.MAX_VALUE && SelectionController.undirected
                                && v.minDistance + e.weight < u.minDistance) {
                            change = true;
                            u.minDistance = v.minDistance + e.weight;
                            u.previous = v;

                            distancesArray[bellmanEdges.indexOf(e)][i] = u.minDistance;
                        }

                    }

                }

                for (Edge e : bellmanEdges) {
                    Node u = e.source;
                    Node v = e.target;
                    if (u.minDistance != Integer.MAX_VALUE && u.minDistance + e.weight < v.minDistance) {
                        negativeCycleDetected = true;
                        break;
                    } else if (v.minDistance != Integer.MAX_VALUE && SelectionController.undirected
                            && v.minDistance + e.weight < u.minDistance) {
                        negativeCycleDetected = true;
                        break;
                    }
                }
            }
        }

        public void newKruskal() {
            new Kruskal();
        }

        class Kruskal {

            int mstValue = 0;

            Node findParent(Node x) {
                if (x != x.previous) {
                    x.previous = findParent(x.previous);
                }
                return x.previous;
            }

            void unionNode(Node x, Node y) {
                Node px = findParent(x);
                Node py = findParent(y);
                if (px == py) {
                    return;
                }
                if (Integer.valueOf(px.name) < Integer.valueOf(py.name)) {
                    px.previous = py;
                    unioned++;
                } else {
                    py.previous = px;
                    unioned++;
                }
            }

            public Kruskal() {
                finished = false;
                unioned = 0;
                for (Node x : circles) {
                    x.previous = x;
                }
                Collections.sort(mstEdges, new Comparator<Edge>() {
                    public int compare(Edge o1, Edge o2) {
                        if (o1.weight == o2.weight) {
                            return 0;
                        }
                        return o1.weight > o2.weight ? 1 : -1;
                    }
                });

                for (Edge e : mstEdges) {
                    if (e.source != null && e.target != null) {
                        if (findParent(e.source.previous) != findParent(e.target.previous)) {
                            unionNode(e.source, e.target);
                            mstValue += e.weight;
                        }
                    }
                }
            }
        }

        public void newPrims() {
            new Prims();
        }

        class Prims {
            List<Edge> primsEdges = new ArrayList<>();
            List<Node> visited = new ArrayList<>();
            int mstValue = 0;

            Prims() {
                Node source = circles.getFirst();
                for (Node node : circles) {
                    node.minDistance = Integer.MAX_VALUE;
                    node.previous = null;
                }
                PriorityQueue<Node> pq = new PriorityQueue<>();
                pq.add(source);
                source.minDistance = 0;
                while (!pq.isEmpty()) {
                    Node u = pq.poll();
                    System.out.println(u.name);
                    if (visited.contains(u)) {
                        continue;
                    }
                    visited.add(u);
                    System.out.println(u.name);
                    for (Edge e : u.adjacents) {
                        if (e != null) {
                            Node v;
                            if (e.target != u) {
                                v = e.target;
                            } else {
                                v = e.source;
                            }
                            if (e.weight < v.minDistance && !visited.contains(v)) {
                                pq.remove(v);
                                v.minDistance = e.weight;
                                v.previous = u;
                                pq.add(v);
                            }
                        }
                    }
                }
                for (Node n : circles) {
                    Node u = n;
                    if (u.previous != null) {
                        System.out.println("Node:" + u.name + "--- parent ---" + u.previous.name);
                        for (Edge mstEdge : mstEdges) {
                            if ((mstEdge.source == u.previous && mstEdge.target == u)
                                    || (mstEdge.target == u.previous && mstEdge.source == u)) {
                                if (!primsEdges.contains(mstEdge)) {
                                    primsEdges.add(mstEdge);
                                    mstValue += mstEdge.weight;
                                    System.out.println(
                                            "Adding For:" + mstEdge.source.name + "--- to ---" + mstEdge.target.name);
                                }
                                break;
                            }
                        }
                    }
                }
                System.out.println(mstValue);
                for (Edge e : mstEdges) {
                    if (!primsEdges.contains(e)) {
                        System.out.println("Not in Prims Edges:");
                        System.out.println(e.source.name + "----" + e.target.name + ", wt: " + e.weight);
                    }
                }
            }
        }

        public List<Node> getShortestPathTo(Node target) {
            List<Node> path = new ArrayList<Node>();
            for (Node i = target; i != null; i = i.previous) {
                path.add(i);
            }
            Collections.reverse(path);
            return path;
        }
    }
