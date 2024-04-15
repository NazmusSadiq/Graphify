package org.example.graphify;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.controlsfx.control.HiddenSidesPane;

import java.net.URL;
import java.time.Instant;
import java.util.*;

public class Algorithm implements Initializable {
    @FXML
    static HiddenSidesPane hiddenPane;
    @FXML
    static AnchorPane anchorRoot;
    @FXML
    static StackPane stackRoot;
    @FXML
    static JFXButton canvasBackButton, clearButton, reloadButton,skipButton, playPauseButton,resetButton,pinUnpin;
    @FXML
    static JFXToggleButton addNodeButton, addEdgeButton, bfsButton, dfsButton, floydButton, dijkstraButton,
            bellmanButton, kruskalButton,primsButton;
    @FXML
    static Pane viewer;
    @FXML
    static Group canvasGroup;
    @FXML
    static Line edgeLine;
    @FXML
    static Label sourceText = new Label("Source"), weight;
    @FXML
    static Pane border;
    @FXML
    static JFXNodesList nodeList;
    @FXML
    static JFXSlider slider = new JFXSlider();
    @FXML
    static ImageView playPauseImage, openHidden,skipImage,reloadImage;

    static Boolean finished=false;
    static StringBuilder all=new StringBuilder();
    static StringBuilder detes=new StringBuilder();
    int repeats=0,unioned=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    // Implementation starts


    //<editor-fold defaultstate="collapsed" desc="BFS">
    public void newBFS(Node source) {
        new BFS(source);
    }

    class BFS {

        BFS(Node source) {
            CanvasController.universalNode=source;
            all=new StringBuilder();
            finished=false;
            repeats=0;
            // Start measuring time
            Instant start = Instant.now();

            // Set labels and distances
            for (CanvasController.NodeFX n : CanvasController.circles) {
                CanvasController.distances.add(n.distance);
                n.distance.setLayoutX(n.point.x + 20);
                n.distance.setLayoutY(n.point.y);
                canvasGroup.getChildren().add(n.distance);
            }
            sourceText.setLayoutX(source.circle.point.x + 20);
            sourceText.setLayoutY(source.circle.point.y + 10);
            canvasGroup.getChildren().add(sourceText);
            CanvasController.st = new SequentialTransition();
            source.circle.distance.setText("Dist. : " + 0);

            source.minDistance = 0;
            source.visited = true;
            LinkedList<Node> q = new LinkedList<Node>();
            q.push(source);
            while (!q.isEmpty()) {
                Node u = q.removeLast();

                // Node Popped Animation
                FillTransition ft = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                if (u.circle.getFill() == Color.BLACK) {
                    ft.setToValue(Color.CHOCOLATE);
                }
                CanvasController.st.getChildren().add(ft);

                String str = "";
                str = str.concat("Popped : Node(" + u.name + ")\n");
                all.append(str);
                final String str2 = str;
                FadeTransition fd = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fd.setOnFinished(e -> {
                    CanvasController.textFlow.appendText(str2);
                });
                fd.onFinishedProperty();
                CanvasController.st.getChildren().add(fd);

                for (Edge e : u.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        if(v.visited){
                            repeats++;
                        }
                        else if (!v.visited) {
                            v.minDistance = u.minDistance + 1;
                            v.visited = true;
                            q.push(v);
                            v.previous = u;

                            // Node visiting animation
                            if (SelectionController.undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                CanvasController.st.getChildren().add(ftEdge);
                            } else if (SelectionController.directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(CanvasController.time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                CanvasController.st.getChildren().add(ftEdge);
                            }

                            FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), v.circle);
                            ft1.setToValue(Color.FORESTGREEN);
                            ft1.setOnFinished(ev -> {
                                v.circle.distance.setText("Dist. : " + v.minDistance);
                            });
                            ft1.onFinishedProperty();
                            CanvasController.st.getChildren().add(ft1);

                            str = "\t";
                            str = str.concat("Pushing : Node(" + v.name + ")\n");
                            all.append(str);
                            final String str1 = str;
                            FadeTransition fd2 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                            fd2.setOnFinished(ev -> {
                                CanvasController.textFlow.appendText(str1);
                            });
                            fd2.onFinishedProperty();
                            CanvasController.st.getChildren().add(fd2);
                        }
                    }
                }
                // Animation Control
                FillTransition ft2 = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                ft2.setToValue(Color.BLUEVIOLET);
                CanvasController.st.getChildren().add(ft2);
            }

            // End measuring time
            Instant end = Instant.now();

            // Calculate duration
            java.time.Duration duration= java.time.Duration.between(start,end);
            System.out.println("Total time for BFS: " + duration.toMillis() + " milliseconds");

            // Animation Control
            CanvasController.st.setOnFinished(ev -> {
                for (CanvasController.NodeFX n : CanvasController.circles) {
                    FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), n);
                    ft1.setToValue(Color.BLACK);
                    ft1.play();
                }
                if (SelectionController.directed) {
                    for (Shape n : CanvasController.edges) {
                        n.setFill(Color.BLACK);
                    }
                } else if (SelectionController.undirected) {
                    for (Shape n : CanvasController.edges) {
                        n.setStroke(Color.BLACK);
                    }
                }
                detes=new StringBuilder();
                detes.append("Total Time Required For BFS: " + duration.toMillis() + " milliseconds"+'\n');
                detes.append("Number Of Nodes Visited: "+CanvasController.circles.size()+'\n');
                detes.append("Number Of Repeated Nodes: "+ repeats+'\n');
                FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), source.circle);
                ft1.setToValue(Color.RED);
                ft1.play();
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                CanvasController.paused = true;
                CanvasController.playing = false;
                CanvasController.textFlow.clear();
                CanvasController.textFlow.appendText(all.toString());
                CanvasController.textFlow.appendText("---Finished--\n");
                if(CanvasController.detailed){
                    CanvasController.textFlow.appendText(detes.toString());
                }
                finished=true;
            });
            CanvasController.st.onFinishedProperty();
            CanvasController.st.play();
            CanvasController.playing = true;
            CanvasController.paused = false;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="DFS">
    public void newDFS(Node source) {
        new DFS(source);
    }

    class DFS {
        DFS(Node source) {
            CanvasController.universalNode=source;
            all=new StringBuilder();
            finished=false;
            repeats=0;
            //<editor-fold defaultstate="collapsed" desc="Animation Setup Distances">

            //Start measuring time
            Instant start = Instant.now();

            for (CanvasController.NodeFX n : CanvasController.circles) {
                CanvasController.distances.add(n.distance);
                n.distance.setLayoutX(n.point.x + 20);
                n.distance.setLayoutY(n.point.y);
                canvasGroup.getChildren().add(n.distance);
            }

            sourceText.setLayoutX(source.circle.point.x + 20);
            sourceText.setLayoutY(source.circle.point.y + 10);
            canvasGroup.getChildren().add(sourceText);
            CanvasController.st = new SequentialTransition();
            source.circle.distance.setText("Dist. : " + 0);
            //</editor-fold>

            source.minDistance = 0;
            source.visited = true;
            DFSRecursion(source, 0);

            // End measuring time
            Instant end = Instant.now();

            // Calculate duration
            java.time.Duration duration = java.time.Duration.between(start, end);
            System.out.println("Total time for DFS: " + duration.toMillis() + " milliseconds");

            //<editor-fold defaultstate="collapsed" desc="Animation after algorithm is finished">
            CanvasController.st.setOnFinished(ev -> {
                for (CanvasController.NodeFX n : CanvasController.circles) {
                    FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), n);
                    ft1.setToValue(Color.BLACK);
                    ft1.play();
                }
                if (SelectionController.directed) {
                    for (Shape n : CanvasController.edges) {
                        n.setFill(Color.BLACK);
                    }
                } else if (SelectionController.undirected) {
                    for (Shape n : CanvasController.edges) {
                        n.setStroke(Color.BLACK);
                    }
                }
                FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), source.circle);
                ft1.setToValue(Color.RED);
                ft1.play();
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                detes=new StringBuilder();
                detes.append("Total Time Required For DFS: " + duration.toMillis() + " milliseconds"+'\n');
                detes.append("Number Of Nodes Visited: "+CanvasController.circles.size()+'\n');
                detes.append("Number Of Repeated Nodes: "+ repeats+'\n');
                CanvasController.paused = true;
                CanvasController.playing = false;
                CanvasController.textFlow.clear();
                CanvasController.textFlow.appendText(all.toString());
                CanvasController.textFlow.appendText("---Finished--\n");
                if(CanvasController.detailed){
                    CanvasController.textFlow.appendText(detes.toString());
                }
                finished=true;
            });
            CanvasController.st.onFinishedProperty();
            CanvasController.st.play();
            CanvasController.playing = true;
            CanvasController.paused = false;
            //</editor-fold>
        }

        public void DFSRecursion(Node source, int level) {
            //<editor-fold defaultstate="collapsed" desc="Animation Control">
            FillTransition ft = new FillTransition(Duration.millis(CanvasController.time), source.circle);
            if (source.circle.getFill() == Color.BLACK) {
                ft.setToValue(Color.FORESTGREEN);
            }
            CanvasController.st.getChildren().add(ft);

            String str = "";
            for (int i = 0; i < level; i++) {
                str = str.concat("\t");
            }
            str = str.concat("DFS(" + source.name + ") Enter\n");
            all.append(str);
            final String str2 = str;
            FadeTransition fd = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
            fd.setOnFinished(e -> {
                CanvasController.textFlow.appendText(str2);
            });
            fd.onFinishedProperty();
            CanvasController.st.getChildren().add(fd);
            //</editor-fold>
            for (Edge e : source.adjacents) {
                if (e != null) {
                    Node v = e.target;
                    if(v.visited){
                        repeats++;
                    }
                    else if (!v.visited) {
                        v.minDistance = source.minDistance + 1;
                        v.visited = true;
                        v.previous = source;
//                        v.circle.distance.setText("Dist. : " + v.minDistance);
                        //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                        if (SelectionController.undirected) {
                            StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.FORESTGREEN);
                            CanvasController.st.getChildren().add(ftEdge);
                        } else if (SelectionController.directed) {
                            FillTransition ftEdge = new FillTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.FORESTGREEN);
                            CanvasController.st.getChildren().add(ftEdge);
                        }
                        //</editor-fold>
                        DFSRecursion(v, level + 1);
                        //<editor-fold defaultstate="collapsed" desc="Animation Control">
                        //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                        if (SelectionController.undirected) {
                            StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.BLUEVIOLET);
                            CanvasController.st.getChildren().add(ftEdge);
                        } else if (SelectionController.directed) {
                            FillTransition ftEdge = new FillTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.BLUEVIOLET);
                            CanvasController.st.getChildren().add(ftEdge);
                        }
                        //</editor-fold>
                        FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), v.circle);
                        ft1.setToValue(Color.BLUEVIOLET);
                        ft1.onFinishedProperty();
                        ft1.setOnFinished(ev -> {
                            v.circle.distance.setText("Dist. : " + v.minDistance);
                        });
                        CanvasController.st.getChildren().add(ft1);
                        //</editor-fold>
                    }
                }
            }
            str = "";
            for (int i = 0; i < level; i++) {
                str = str.concat("\t");
            }
            str = str.concat("DFS(" + source.name + ") Exit\n");
            all.append(str);
            final String str1 = str;
            fd = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
            fd.setOnFinished(e -> {
                CanvasController.textFlow.appendText(str1);
            });
            fd.onFinishedProperty();
            CanvasController.st.getChildren().add(fd);
        }
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Dijkstra">
    public void newDijkstra(Node source) {
        new Dijkstra(source);
    }

    class Dijkstra {
        Dijkstra(Node source) {
            CanvasController.universalNode=source;
            all=new StringBuilder();
            finished=false;
            //<editor-fold defaultstate="collapsed" desc="Animation Control">

            //Start measuring time
            Instant start = Instant.now();
            for (CanvasController.NodeFX n : CanvasController.circles) {
                CanvasController.distances.add(n.distance);
                n.distance.setLayoutX(n.point.x + 20);
                n.distance.setLayoutY(n.point.y);
                canvasGroup.getChildren().add(n.distance);
            }
            sourceText.setLayoutX(source.circle.point.x + 20);
            sourceText.setLayoutY(source.circle.point.y + 10);
            canvasGroup.getChildren().add(sourceText);
            CanvasController.st = new SequentialTransition();
            source.circle.distance.setText("Dist. : " + 0);
            //</editor-fold>

            source.minDistance = 0;
            PriorityQueue<Node> pq = new PriorityQueue<Node>();
            pq.add(source);
            while (!pq.isEmpty()) {
                Node u = pq.poll();
                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                FillTransition ft = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                ft.setToValue(Color.CHOCOLATE);
                CanvasController.st.getChildren().add(ft);
                String str = "";
                str = str.concat("Popped : Node(" + u.name + "), Current Distance: " + u.minDistance + "\n");
                all.append(str);
                final String str2 = str;
                FadeTransition fd = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fd.setOnFinished(e -> {
                    CanvasController.textFlow.appendText(str2);
                });
                fd.onFinishedProperty();
                CanvasController.st.getChildren().add(fd);
                //</editor-fold>
                System.out.println(u.name);
                if(u.visited){
                    continue;
                }
                u.visited=true;
                for (Edge e : u.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        System.out.println("src " + u.name+"tgt "+v.name);
                        if (u.minDistance + e.weight < v.minDistance) {
                            pq.remove(v);
                            v.minDistance = u.minDistance + e.weight;
                            v.previous = u;
                            double ldist=v.minDistance;
                            pq.add(v);
                            //<editor-fold defaultstate="collapsed" desc="Node visiting animation">
                            //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                            if (SelectionController.undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                CanvasController.st.getChildren().add(ftEdge);
                            } else if (SelectionController.directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(CanvasController.time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                CanvasController.st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), v.circle);
                            ft1.setToValue(Color.FORESTGREEN);
                            ft1.setOnFinished(ev -> {
                                System.out.println("Dist. : " + ldist);
                                v.circle.distance.setText("Dist. : " + ldist);
                            });
                            ft1.onFinishedProperty();
                            CanvasController.st.getChildren().add(ft1);

                            str = "\t";
                            str = str.concat("Pushing : Node(" + v.name + "), (" + u.name + "--" + v.name + ") Distance : " + v.minDistance + "\n");
                            all.append(str);
                            final String str1 = str;
                            FadeTransition fd2 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                            fd2.setOnFinished(ev -> {
                                CanvasController.textFlow.appendText(str1);
                            });
                            fd2.onFinishedProperty();
                            CanvasController.st.getChildren().add(fd2);
                            //</editor-fold>
                        }
                    }
                }
                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                FillTransition ft2 = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                ft2.setToValue(Color.BLUEVIOLET);
                CanvasController.st.getChildren().add(ft2);
                //</editor-fold>
            }
            // End measuring time
            Instant end = Instant.now();

            // Calculate duration
            java.time.Duration duration= java.time.Duration.between(start,end);
            System.out.println("Total time for Dijkstra: " + duration.toMillis() + " milliseconds");

            //<editor-fold defaultstate="collapsed" desc="Animation Control">

            CanvasController.st.setOnFinished(ev -> {
                for (CanvasController.NodeFX n : CanvasController.circles) {
                    FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), n);
                    ft1.setToValue(Color.BLACK);
                    ft1.play();
                }
                if (SelectionController.directed) {
                    for (Shape n :CanvasController. edges) {
                        n.setFill(Color.BLACK);
                    }
                } else if (SelectionController.undirected) {
                    for (Shape n :CanvasController. edges) {
                        n.setStroke(Color.BLACK);
                    }
                }
                FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), source.circle);
                ft1.setToValue(Color.RED);
                ft1.play();
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                detes=new StringBuilder();
                detes.append("Total Time Required For Dijkstra: " + duration.toMillis() + " milliseconds"+'\n');
                detes.append("Number Of Nodes Visited: "+CanvasController.circles.size()+'\n');
                CanvasController.paused = true;
                CanvasController.playing = false;
                CanvasController.textFlow.clear();
                CanvasController.textFlow.appendText(all.toString());
                CanvasController.textFlow.appendText("---Finished--\n");
                if(CanvasController.detailed){
                    CanvasController.textFlow.appendText(detes.toString());
                }
                finished=true;
            });
            CanvasController.st.onFinishedProperty();
            CanvasController.st.play();
            CanvasController.playing = true;
            CanvasController.paused = false;
            //</editor-fold>
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Floyd-Warshall">
    public void newFloydWarshall(Node source) {
        new FloydWarshall(source);
    }

    class FloydWarshall {
        FloydWarshall(Node source) {
            CanvasController.universalNode=source;
            all=new StringBuilder();
            finished=false;
            Instant start = Instant.now();
            CanvasController.st = new SequentialTransition();
            int sz = Integer.MIN_VALUE;
            for (CanvasController.NodeFX n : CanvasController.circles) {
                if (Integer.parseInt(n.node.name) > sz) {
                    sz = Integer.parseInt(n.node.name);
                }
            }
            double[][] dist = new double[sz][sz];
            Edge[][] floydEdges=new Edge[sz][sz];
            Node[] floydNodes= new Node[sz];
            for (int i = 0; i < sz; i++) {
                for (int j = 0; j < sz; j++) {
                    if (i == j) {
                        dist[i][j] = 0;
                    } else {
                        dist[i][j] = Double.POSITIVE_INFINITY;
                    }
                }
            }
            for (Edge e : CanvasController.bellmanEdges) {
                int i=Integer.valueOf(e.source.name)-1;
                int j=Integer.valueOf(e.target.name)-1;
                floydNodes[i]=e.source;
                floydNodes[j]=e.target;
                System.out.println(i+" "+j);
                dist[i][j] = e.weight;
                if(e.source==source) {
                    e.target.circle.distance.setText("Dist. : " + e.weight);
                }
                floydEdges[i][j]=e;
                if(SelectionController.undirected) {
                    dist[j][i] = e.weight;
                    floydEdges[j][i]=e;
                }
            }

            //<editor-fold defaultstate="collapsed" desc="Animation Control">
            for (CanvasController.NodeFX n : CanvasController.circles) {
                CanvasController.distances.add(n.distance);
                n.distance.setLayoutX(n.point.x + 20);
                n.distance.setLayoutY(n.point.y);
                canvasGroup.getChildren().add(n.distance);
            }
            sourceText.setLayoutX(source.circle.point.x + 20);
            sourceText.setLayoutY(source.circle.point.y + 10);
            canvasGroup.getChildren().add(sourceText);
            FillTransition fts = new FillTransition(Duration.millis(CanvasController.time), source.circle);
            fts.setToValue(Color.RED);
            CanvasController.st.getChildren().add(fts);
            source.circle.distance.setText("Dist. : " + 0);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Floyd-Warshall Algorithm">
            for (int k = 0; k < sz; k++) {
                for (int i = 0; i < sz; i++) {
                    for (int j = 0; j < sz; j++) {
                        if (dist[i][k] + dist[k][j] < dist[i][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            if (i == Integer.valueOf(source.name) - 1) {
                                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                                FillTransition ft = new FillTransition(Duration.millis(CanvasController.time), floydNodes[j].circle);
                                ft.setToValue(Color.FORESTGREEN);
                                int finalJ = j;
                                int finalI = i;
                                ft.setOnFinished(ev -> {
                                    floydNodes[finalJ].circle.distance.setText("Dist. : " + dist[finalI][finalJ]);
                                });
                                ft.onFinishedProperty();
                                CanvasController.st.getChildren().add(ft);

                                String str = "Updated Distance to Node " + floydNodes[j].name + " to: "+ dist[i][j] + "\n";
                                all.append(str);
                                FadeTransition fd = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                                fd.setOnFinished(e -> {
                                    CanvasController.textFlow.appendText(str);
                                });
                                fd.onFinishedProperty();
                                CanvasController.st.getChildren().add(fd);
                            }
                            FillTransition ft3 = new FillTransition(Duration.millis(CanvasController.time), floydNodes[j].circle);
                            ft3.setToValue(Color.BLACK);
                            CanvasController.st.getChildren().add(ft3);
                            //</editor-fold>
                        }
                    }
                }
            }
            for (int i = 0; i < CanvasController.circles.size(); i++) {
                for (int j = 0; j < CanvasController.circles.size(); j++) {
                    System.out.println("dist["+(i+1)+"]["+(j+1)+"]: "+dist[i][j]);
                }
            }
            //</editor-fold>

            // End measuring time
            Instant end = Instant.now();

            // Calculate duration
            java.time.Duration duration= java.time.Duration.between(start,end);
            System.out.println("Total time for Floyd Warshall: " + duration.toMillis() + " milliseconds");

            //<editor-fold defaultstate="collapsed" desc="Animation Control">
            CanvasController.st.setOnFinished(ev -> {
                for (CanvasController.NodeFX n : CanvasController.circles) {
                    FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), n);
                    ft1.setToValue(Color.BLACK);
                    ft1.play();
                }
                if (SelectionController.directed) {
                    for (Shape n : CanvasController.edges) {
                        n.setFill(Color.BLACK);
                    }
                } else if (SelectionController.undirected) {
                    for (Shape n : CanvasController.edges) {
                        n.setStroke(Color.BLACK);
                    }
                }
                FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), source.circle);
                ft1.setToValue(Color.RED);
                ft1.play();
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                detes=new StringBuilder();
                StringBuilder str=new StringBuilder();

                if(dist[Integer.valueOf(source.name)-1][Integer.valueOf(source.name)-1]<0){
                    str.append("Negative Cycle Present");
                }
                else{
                    str.append("No Negative Cycle Present");
                }
                detes.append("Total Time Required For Floyd Warshall: " + duration.toMillis() + " milliseconds"+'\n');
                detes.append("Number Of Nodes Visited: "+CanvasController.circles.size()+'\n');
                detes.append(str);
                CanvasController.paused = true;
                CanvasController.playing = false;
                CanvasController.textFlow.clear();
                CanvasController.textFlow.appendText(all.toString());
                CanvasController.textFlow.appendText("---Finished--\n");
                if(CanvasController.detailed){
                    CanvasController.textFlow.appendText(detes.toString());
                }
                finished=true;
            });
            CanvasController. st.onFinishedProperty();
            CanvasController.st.play();
            CanvasController.playing = true;
            CanvasController.paused = false;
            //</editor-fold>
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Bellman-Ford">
    public void newBellmanFord(Node source) {
        new BellmanFord(source);
    }

    class BellmanFord {

        BellmanFord(Node source) {
            CanvasController.universalNode=source;
            all=new StringBuilder();
            finished=false;
            Instant start=Instant.now();
            //<editor-fold defaultstate="collapsed" desc="Animation Control">
            for (CanvasController.NodeFX n : CanvasController.circles) {
                CanvasController.distances.add(n.distance);
                n.distance.setLayoutX(n.point.x + 20);
                n.distance.setLayoutY(n.point.y);
                canvasGroup.getChildren().add(n.distance);
            }
            sourceText.setLayoutX(source.circle.point.x + 20);
            sourceText.setLayoutY(source.circle.point.y + 10);
            canvasGroup.getChildren().add(sourceText);
            CanvasController.st = new SequentialTransition();
            source.circle.distance.setText("Dist. : " + 0);
            //</editor-fold>

            source.minDistance = 0;

            double[][] distancesArray = new double[CanvasController.bellmanEdges.size()][CanvasController.circles.size()];
            boolean negativeCycleDetected = false;
            for (int i = 0; i < CanvasController.circles.size() - 1; i++) {
                Boolean change = false;

                //<editor-fold defaultstate="collapsed" desc="Animation Control for Iteration Label">
                String iterationLabel = "For iteration No: "+(i + 1)+"\n";
                all.append(iterationLabel);
                FadeTransition fdIterationLabel = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fdIterationLabel.setOnFinished(d -> {
                    CanvasController.textFlow.appendText(iterationLabel);
                });
                fdIterationLabel.onFinishedProperty();
                CanvasController.st.getChildren().add(fdIterationLabel);
                //</editor-fold>

                for (Edge e : CanvasController.bellmanEdges) {
                    Node u = e.source;
                    Node v = e.target;
                    FillTransition ft2 = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                    int idx=i;
                    if (idx == CanvasController.circles.size() - 2) {
                        ft2.setToValue(Color.BLUEVIOLET);
                    } else {
                        ft2.setToValue(Color.FORESTGREEN);
                    }
                    CanvasController.st.getChildren().add(ft2);
                    if (u.minDistance != Integer.MAX_VALUE && u.minDistance + e.weight < v.minDistance){
                        change = true;
                        v.minDistance = u.minDistance + e.weight;
                        v.previous = u;
                        //<editor-fold defaultstate="collapsed" desc="Node visiting animation">
                        //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                        if (SelectionController.undirected) {
                            StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.FORESTGREEN);
                            CanvasController.st.getChildren().add(ftEdge);
                        } else if (SelectionController.directed) {
                            FillTransition ftEdge = new FillTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.FORESTGREEN);
                            CanvasController.st.getChildren().add(ftEdge);
                        }
                        distancesArray[CanvasController.bellmanEdges.indexOf(e)][i] = v.minDistance;
                        //</editor-fold>
                        FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), v.circle);
                        ft1.setToValue(Color.ORANGE); // Set updated node color
                        ft1.setOnFinished(ev -> {
                            String s=Double.toString(distancesArray[CanvasController.bellmanEdges.indexOf(e)][idx]);
                            v.circle.distance.setText("Dist. : " +s);
                        });
                        ft1.onFinishedProperty();
                        CanvasController.st.getChildren().add(ft1);
                        String updateMessage = "\tUpdating : Node(" + v.name + "), (" + u.name + "--" + v.name + ") Distance : " + v.minDistance + "\n";
                        all.append(updateMessage);
                        FadeTransition fdUpdateMessage = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                        fdUpdateMessage.setOnFinished(ev -> {
                            CanvasController.textFlow.appendText(updateMessage);
                        });
                        fdUpdateMessage.onFinishedProperty();
                        CanvasController.st.getChildren().add(fdUpdateMessage);

                        //</editor-fold>
                    }
                    else if(v.minDistance != Integer.MAX_VALUE && SelectionController.undirected && v.minDistance + e.weight < u.minDistance){
                        change=true;
                        u.minDistance=v.minDistance+e.weight;
                        u.previous=v;
                        //<editor-fold defaultstate="collapsed" desc="Node visiting animation">
                        //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                        if (SelectionController.undirected) {
                            StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.BLACK);
                            CanvasController.st.getChildren().add(ftEdge);
                        } else if (SelectionController.directed) {
                            FillTransition ftEdge = new FillTransition(Duration.millis(CanvasController.time), e.line);
                            ftEdge.setToValue(Color.BLACK);
                            CanvasController.st.getChildren().add(ftEdge);
                        }
                        distancesArray[CanvasController.bellmanEdges.indexOf(e)][i] = u.minDistance;
                        //</editor-fold>
                        FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                        ft1.setToValue(Color.ORANGE); // Set updated node color
                        ft1.setOnFinished(ev -> {
                            String s=Double.toString(distancesArray[CanvasController.bellmanEdges.indexOf(e)][idx]);
                            u.circle.distance.setText("Dist. : " +s);
                        });
                        ft1.onFinishedProperty();
                        CanvasController.st.getChildren().add(ft1);
                        String updateMessage = "\tUpdating : Node(" + v.name + "), (" + u.name + "--" + v.name + ") Distance : " + v.minDistance + "\n";
                        all.append(updateMessage);
                        FadeTransition fdUpdateMessage = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                        fdUpdateMessage.setOnFinished(ev -> {
                            CanvasController.textFlow.appendText(updateMessage);
                        });
                        fdUpdateMessage.onFinishedProperty();
                        CanvasController.st.getChildren().add(fdUpdateMessage);

                        //</editor-fold>
                    }
                    FillTransition ft3 = new FillTransition(Duration.millis(CanvasController.time), v.circle);
                    ft3.setToValue(Color.BLACK);
                    CanvasController.st.getChildren().add(ft3);

                    if (idx != CanvasController.circles.size() - 2) {
                        FillTransition ft4 = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                        ft4.setToValue(Color.BLACK);
                        CanvasController.st.getChildren().add(ft4);
                    }
                    if (SelectionController.undirected) {
                        StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                        ftEdge.setToValue(Color.BLACK);
                        CanvasController.st.getChildren().add(ftEdge);
                    } else if (SelectionController.directed) {
                        FillTransition ftEdge = new FillTransition(Duration.millis(CanvasController.time), e.line);
                        ftEdge.setToValue(Color.BLACK);
                        CanvasController.st.getChildren().add(ftEdge);
                    }
                }
                if (!change) {
                    String noChangeMessage = "\tNo change in this iteration\n";
                    all.append(noChangeMessage);
                    FadeTransition fdNoChangeMessage = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                    fdNoChangeMessage.setOnFinished(ev -> {
                        CanvasController.textFlow.appendText(noChangeMessage);
                    });
                    fdNoChangeMessage.onFinishedProperty();
                    CanvasController.st.getChildren().add(fdNoChangeMessage);
                }

            }

            for (Edge e : CanvasController.bellmanEdges) {
                Node u = e.source;
                Node v = e.target;
                if (u.minDistance != Integer.MAX_VALUE && u.minDistance + e.weight < v.minDistance) {
                    negativeCycleDetected = true;
                    break;
                }
                else if(v.minDistance != Integer.MAX_VALUE && SelectionController.undirected && v.minDistance + e.weight < u.minDistance){
                    negativeCycleDetected = true;
                    break;
                }
            }
            Instant end = Instant.now();
            java.time.Duration duration= java.time.Duration.between(start,end);
            System.out.println("Total time for Bellman Ford: " + duration.toMillis() + " milliseconds");

            //<editor-fold defaultstate="collapsed" desc="Animation Control">
            boolean finalNegativeCycleDetected = negativeCycleDetected;
            CanvasController.st.setOnFinished(ev -> {
                detes=new StringBuilder();
                detes.append("Total Time Required For Bellman Ford: " + duration.toMillis() + " milliseconds\n");
                detes.append("Number of nodes visited: "+CanvasController.circles.size()+'\n');
                if(finalNegativeCycleDetected)
                    detes.append("Negative cycle detected.\n");
                else
                    detes.append("No negative cycle detected.\n");
                //negative cycle, no of iteration
                CanvasController.textFlow.clear();
                CanvasController.textFlow.appendText(all.toString());
                for (CanvasController.NodeFX n : CanvasController.circles) {
                    FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), n);
                    ft1.setToValue(Color.BLACK);
                    ft1.play();
                }
                FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), source.circle);
                ft1.setToValue(Color.RED);
                ft1.play(); // Set source node color to reddish
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                CanvasController.paused = true;
                CanvasController.playing = false;
                CanvasController.textFlow.appendText("\t---Finished--\n");
                if(CanvasController.detailed){
                    CanvasController.textFlow.appendText(detes.toString());
                }
                finished=true;
            });
            CanvasController.st.onFinishedProperty();
            CanvasController.st.play();
            CanvasController.playing = true;
            CanvasController.paused = false;
            //</editor-fold>
        }
    }

//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Kruskal">
    public void newKruskal() {
        new Kruskal();
    }

    class Kruskal {

        Instant start=Instant.now();
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
            CanvasController.universalNode=null;
            all=new StringBuilder();
            finished=false;
            unioned=0;
            CanvasController.st = new SequentialTransition();
            for (CanvasController.NodeFX x : CanvasController.circles) {
                x.node.previous = x.node;
            }

            //<editor-fold defaultstate="collapsed" desc="Detail Information">
            String init = "Intially : \n";
            for (CanvasController.NodeFX x : CanvasController.circles) {
                final String s = "Node : " + x.node.name + " , Parent: " + x.node.previous.name + "\n";
                all.append(s);
                FadeTransition fd = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fd.setOnFinished(e -> {
                    CanvasController.textFlow.appendText(s);
                });
                fd.onFinishedProperty();
                CanvasController.st.getChildren().add(fd);
            }
            final String s = "Start Algorithm :---\n";
            all.append(s);
            FadeTransition fdss = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
            fdss.setOnFinished(ev -> {
                CanvasController.textFlow.appendText(s);
            });
            fdss.onFinishedProperty();
            CanvasController.st.getChildren().add(fdss);
            //</editor-fold>
            Collections.sort(CanvasController.mstEdges, new Comparator<Edge>() {
                public int compare(Edge o1, Edge o2) {
                    if (o1.weight == o2.weight) {
                        return 0;
                    }
                    return o1.weight > o2.weight ? 1 : -1;
                }
            });

            for (Edge e : CanvasController.mstEdges) {

                StrokeTransition ft1 = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                ft1.setToValue(Color.DARKORANGE);
                CanvasController.st.getChildren().add(ft1);

                //<editor-fold defaultstate="collapsed" desc="Detail Information">
                final String se = "Selected Edge:- (" + e.source.name.trim() + "--" + e.target.name.trim() + ") Weight: " + String.valueOf(e.weight) + " \n";
                all.append(se);
                FadeTransition fdx = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fdx.setOnFinished(evx -> {
                    CanvasController.textFlow.appendText(se);
                });
                fdx.onFinishedProperty();
                CanvasController.st.getChildren().add(fdx);

                final String s1 = "\t-> Node :" + e.source.name.trim() + "  Parent: " + findParent(e.source.previous).name.trim() + "\n";
                all.append(s1);
                FadeTransition fdx2 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fdx2.setOnFinished(evx -> {
                    CanvasController.textFlow.appendText(s1);
                });
                fdx2.onFinishedProperty();
                CanvasController.st.getChildren().add(fdx2);

                final String s2 = "\t-> Node :" + e.target.name.trim() + "  Parent: " + findParent(e.target.previous).name.trim() + "\n";
                all.append(s2);
                FadeTransition fdx3 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fdx3.setOnFinished(evx -> {
                    CanvasController.textFlow.appendText(s2);
                });
                fdx3.onFinishedProperty();
                CanvasController.st.getChildren().add(fdx3);
                //</editor-fold>
                if (e.source != null && e.target != null) {
                    if (findParent(e.source.previous) != findParent(e.target.previous)) {
                        unionNode(e.source, e.target);
                        mstValue += e.weight;

                        //<editor-fold defaultstate="collapsed" desc="Detail Information">
                        final String sa = "\t---->Unioned\n";
                        final String sa1 = "\t\t->Node :" + e.source.name.trim() + "  Parent: " + findParent(e.source.previous).name.trim() + "\n";
                        final String sa2 = "\t\t->Node :" + e.target.name.trim() + "  Parent: " + findParent(e.target.previous).name.trim() + "\n";
                        all.append(sa);
                        all.append(sa1);
                        all.append(sa2);
                        FadeTransition fdx4 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                        fdx4.setOnFinished(evx -> {
                            CanvasController.textFlow.appendText(sa);
                        });
                        fdx4.onFinishedProperty();
                        CanvasController.st.getChildren().add(fdx4);
                        FadeTransition fdx5 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                        fdx5.setOnFinished(evx -> {
                            CanvasController.textFlow.appendText(sa1);
                        });
                        fdx5.onFinishedProperty();
                        CanvasController.st.getChildren().add(fdx5);
                        FadeTransition fdx6 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                        fdx6.setOnFinished(evx -> {
                            CanvasController.textFlow.appendText(sa2);
                        });
                        fdx6.onFinishedProperty();
                        CanvasController.st.getChildren().add(fdx6);

                        StrokeTransition ft2 = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                        ft2.setToValue(Color.AQUA);
                        CanvasController.st.getChildren().add(ft2);

                        FillTransition ft3 = new FillTransition(Duration.millis(CanvasController.time), e.source.circle);
                        ft3.setToValue(Color.FORESTGREEN);
                        CanvasController.st.getChildren().add(ft3);

                        ft3 = new FillTransition(Duration.millis(CanvasController.time), e.target.circle);
                        ft3.setToValue(Color.FORESTGREEN);
                        CanvasController.st.getChildren().add(ft3);
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="Detail Info">
                        final String sa = "\t---->Cycle Detected\n";
                        all.append(sa);
                        FadeTransition fdx7 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                        fdx7.setOnFinished(evx -> {
                            CanvasController.textFlow.appendText(sa);
                        });
                        fdx7.onFinishedProperty();
                        CanvasController.st.getChildren().add(fdx7);
                        //</editor-fold>
                        StrokeTransition ft2 = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                        ft2.setToValue(Color.TRANSPARENT);
                        ft2.setOnFinished(ev -> {
                            e.weightLabel.setStyle("-fx-text-fill: transparent;");
                        });
                        CanvasController.st.getChildren().add(ft2);

                    }
                }
            }

            Instant end = Instant.now();
            java.time.Duration duration= java.time.Duration.between(start,end);
            System.out.println("Total time for Kruskal: " + duration.toMillis() + " milliseconds");

            //<editor-fold defaultstate="collapsed" desc="Animation after algorithm is finished">
            CanvasController.st.setOnFinished(ev -> {
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                detes=new StringBuilder();
                detes.append("Total Time Required For Kruskal: " + duration.toMillis() + " milliseconds\n");
                detes.append("Minimum Cost Of The Graph: " + mstValue+'\n');
                detes.append("Number Of Nodes Unioned: "+unioned+'\n');
                // mst value,no of nodes unioned, cycles detected
                CanvasController.paused = true;
                CanvasController.playing = false;
                CanvasController.textFlow.clear();
                CanvasController.textFlow.appendText(all.toString());
                CanvasController.textFlow.appendText("\t---Finished--\n");
                if(CanvasController.detailed){
                    CanvasController.textFlow.appendText(detes.toString());
                }
                finished=true;
            });
            CanvasController.st.onFinishedProperty();
            CanvasController.st.play();
            CanvasController.playing = true;
            //</editor-fold>
            System.out.println("" + mstValue);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Prims">
    public void newPrims() {
        new Prims();
    }

    class Prims {
        List<Edge> primsEdges = new ArrayList<>();
        List<Node> visited= new ArrayList<>();
        int mstValue = 0;
        Prims() {
            Instant start = Instant.now();
            Node source=CanvasController.circles.getFirst().node;
            all=new StringBuilder();
            for (CanvasController.NodeFX node : CanvasController.circles) {
                node.node.minDistance = Integer.MAX_VALUE;
                node.node.previous = null;
            }
            CanvasController.universalNode=null;
            CanvasController.st = new SequentialTransition();
            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(source);
            source.minDistance=0;
            while (!pq.isEmpty()) {
                Node u = pq.poll();
                System.out.println(u.name);
                if(visited.contains(u)){
                    continue;
                }
                visited.add(u);
                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                FillTransition ft = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                ft.setToValue(Color.CHOCOLATE);
                CanvasController.st.getChildren().add(ft);
                String str = "";
                str = str.concat("Popped : Node(" + u.name + "), Current Distance: " + u.minDistance + "\n");
                all.append(str);
                final String str2 = str;
                FadeTransition fd = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                fd.setOnFinished(e -> {
                    CanvasController.textFlow.appendText(str2);
                });
                fd.onFinishedProperty();
                CanvasController.st.getChildren().add(fd);
                //</editor-fold>
                System.out.println(u.name);
                for (Edge e : u.adjacents) {
                    if (e != null) {
                        Node v;
                        if(e.target!=u){
                            v= e.target;
                        }
                        else {
                            v=e.source;
                        }
                        if (e.weight < v.minDistance && !visited.contains(v)) {
                            pq.remove(v);
                            v.minDistance = e.weight;
                            v.previous = u;
                            pq.add(v);
                            //<editor-fold defaultstate="collapsed" desc="Edge visiting animation">
                            if (!CanvasController.mstEdges.contains(e)) { // If edge is not part of MST
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                                ftEdge.setToValue(Color.RED);
                                CanvasController.st.getChildren().add(ftEdge);
                            } else {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                CanvasController.st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), v.circle);
                            ft1.setToValue(Color.AQUAMARINE);
                            ft1.onFinishedProperty();
                            CanvasController.st.getChildren().add(ft1);

                            str = "\t";
                            str = str.concat("Pushing : Node(" + v.name + "), (" + u.name + "--" + v.name + ") Distance : " + v.minDistance + "\n");
                            all.append(str);
                            final String str1 = str;
                            FadeTransition fd2 = new FadeTransition(Duration.millis(10), CanvasController.textFlow);
                            fd2.setOnFinished(ev -> {
                                CanvasController.textFlow.appendText(str1);
                            });
                            fd2.onFinishedProperty();
                            CanvasController.st.getChildren().add(fd2);
                        }
                        StrokeTransition ftEdge = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                        ftEdge.setToValue(Color.AQUA);
                        CanvasController.st.getChildren().add(ftEdge);
                    }
                }
                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                FillTransition ft2 = new FillTransition(Duration.millis(CanvasController.time), u.circle);
                ft2.setToValue(Color.BLUEVIOLET);
                CanvasController.st.getChildren().add(ft2);
                //</editor-fold>
            }
            for (CanvasController.NodeFX n : CanvasController.circles) {
                Node u = n.node;
                if (u.previous != null) {
                    System.out.println("Node:" + u.name + "--- parent ---" + u.previous.name);
                    for (Edge mstEdge : CanvasController.mstEdges) {
                        if ((mstEdge.source == u.previous && mstEdge.target == u) || (mstEdge.target == u.previous && mstEdge.source == u)) {
                            if (!primsEdges.contains(mstEdge)) {
                                primsEdges.add(mstEdge);
                                mstValue += mstEdge.weight;
                                System.out.println("Adding For:" + mstEdge.source.name + "--- to ---" + mstEdge.target.name);
                            }
                            break;
                        }
                    }
                }
            }
            System.out.println(mstValue);
            //<editor-fold defaultstate="collapsed" desc="Animation Control">
            for(Edge e: CanvasController.mstEdges){
                if(!primsEdges.contains(e)){
                    System.out.println("Not in Prims Edges:");
                    System.out.println(e.source.name+"----"+e.target.name+", wt: "+e.weight);
                    StrokeTransition ft2 = new StrokeTransition(Duration.millis(CanvasController.time), e.line);
                    ft2.setToValue(Color.TRANSPARENT);
                    ft2.setOnFinished(ev2 -> {
                        e.weightLabel.setStyle("-fx-text-fill: transparent;");
                    });
                    CanvasController.st.getChildren().add(ft2);
                }
            }
            Instant end = Instant.now();
            java.time.Duration duration= java.time.Duration.between(start,end);
            System.out.println("Total time for Prim's: " + duration.toMillis() + " milliseconds");
            CanvasController.st.setOnFinished(ev -> {
                for (CanvasController.NodeFX n :CanvasController. circles) {
                    FillTransition ft1 = new FillTransition(Duration.millis(CanvasController.time), n);
                    ft1.setToValue(Color.FORESTGREEN);
                    ft1.play();
                }
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                detes=new StringBuilder();
                detes.append("Total Time Required For Prim's: " + duration.toMillis() + " milliseconds\n");
                detes.append("Minimum Cost Of The Graph: " + mstValue+'\n');
                detes.append("Number Of Nodes Visited: "+CanvasController.circles.size()+'\n');
                //mst value,nodes visited, repeated nodes
                CanvasController.paused = true;
                CanvasController.playing = false;
                CanvasController.textFlow.clear();
                CanvasController.textFlow.appendText(all.toString());
                CanvasController.textFlow.appendText("Minimum Cost of the Graph " + mstValue+"\n");
                CanvasController.textFlow.appendText("\t---Finished--\n");
                if(CanvasController.detailed){
                    CanvasController.textFlow.appendText(detes.toString());
                }
                finished=true;
            });
            CanvasController.st.onFinishedProperty();
            CanvasController.st.play();
            CanvasController.playing = true;
            CanvasController.paused = false;
            //</editor-fold>
        }
    }
    //</editor-fold>

    public List<Node> getShortestPathTo(Node target) {
            List<Node> path = new ArrayList<Node>();
            for (Node i = target; i != null; i = i.previous) {
                path.add(i);
            }
            Collections.reverse(path);
            return path;
        }
}
