package org.example.graphify;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import javafx.util.Duration;
import org.controlsfx.control.HiddenSidesPane;

public class CanvasController implements Initializable, ChangeListener {

    @FXML
    private HiddenSidesPane hiddenPane;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane stackRoot;
    @FXML
    private JFXButton canvasBackButton, clearButton, reloadButton,initButton,ssButton,nextButton, prevButton, skipButton, playPauseButton,resetButton,pinUnpin,detailsButton,graphButton,compareButton;
    @FXML
    private JFXToggleButton addNodeButton, addEdgeButton, bfsButton, dfsButton, floydButton, dijkstraButton,
            bellmanButton, kruskalButton,primsButton;
    @FXML
    private Pane viewer;
    @FXML
    private Tooltip playPause;
    @FXML
    private Group canvasGroup;
    @FXML
    private Line edgeLine;
    @FXML
    private Label sourceText = new Label("Source"), weight;
    @FXML
    private Pane border;
    @FXML
    private Arrow arrow;
    @FXML
    private JFXNodesList nodeList;
    @FXML
    private JFXSlider slider = new JFXSlider();
    @FXML
    private ImageView playPauseImage, openHidden,skipImage,reloadImage;

    boolean menuBool = false;
    ContextMenu globalMenu;    //pop-up menu for rmb options
    static Node universalNode=null;
    static int nNode = 0, time = 500,temp;
    static double nodeRadius=1.5;
    NodeFX selectedNode = null; //Custom class
    static List<NodeFX> circles = new ArrayList<>();
    static List<Edge> mstEdges = new ArrayList<>(), realEdges = new ArrayList<>(),bellmanEdges = new ArrayList<>();
    static List<Shape> edges = new ArrayList<>();
    static boolean addNode = true, addEdge = false, calculate = false,
            calculated = false, playing = false, paused = false, pinned = false,detailed=false,compared=false;
    public static List<Label> distances = new ArrayList<Label>(), visitTime = new ArrayList<>(), lowTime = new ArrayList<Label>();
    static boolean bfs = true, dfs = true, dijkstra = true, kruskal = true,prims=true, floyd = true, bellman = true;
    Algorithm algo = new Algorithm();

    static SequentialTransition st;     // combine multiple transitions and play them sequentially

    public AnchorPane hiddenRoot = new AnchorPane();

    public static TextArea textFlow = new TextArea();
    public ScrollPane textContainer = new ScrollPane();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("In init");
        hiddenPane.setContent(canvasGroup);

        ResetHandle(null);
        viewer.prefHeightProperty().bind(border.heightProperty());
        viewer.prefWidthProperty().bind(border.widthProperty());
//        AddNodeHandle(null);
        addEdgeButton.setDisable(true);
        addNodeButton.setDisable(true);
        clearButton.setDisable(true);
        resetButton.setDisable(true);
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        compareButton.setDisable(true);
        detailsButton.setDisable(true);

        if (SelectionController.weighted) {
            bfsButton.setDisable(true);
            dfsButton.setDisable(true);
        }

        if (SelectionController.unweighted) {
            dijkstraButton.setDisable(true);
            bellmanButton.setDisable(true);
            floydButton.setDisable(true);
        }

        //Set back button action
        canvasBackButton.setOnAction(e -> {
            try {
                ResetHandle(null);
                Parent root = FXMLLoader.load(getClass().getResource("Selection.fxml"));

                Scene scene = new Scene(root);
                GraphifyMain.primaryStage.setScene(scene);
            } catch (IOException ex) {
                System.out.println("Back e jay na");
                Logger.getLogger(CanvasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Setup Slider
        slider = new JFXSlider(10, 1000, 500);// min, max, initial
        slider.setPrefWidth(120);
        slider.setPrefHeight(40);
        slider.setSnapToTicks(true);
        slider.setMinorTickCount(100);
        slider.setIndicatorPosition(JFXSlider.IndicatorPosition.RIGHT);
        slider.setBlendMode(BlendMode.SRC_OVER);
        slider.setCursor(Cursor.CLOSED_HAND);
        nodeList.addAnimatedNode(slider);
        nodeList.setSpacing(-20D);
        slider.toFront();
        nodeList.toFront();
        playPauseButton.setDisable(true);
        slider.valueProperty().addListener(this);

        hiddenRoot.setPrefWidth(220);
        hiddenRoot.setPrefHeight(581);

        hiddenRoot.setCursor(Cursor.DEFAULT);

        //Set Label "Detail"
        Label detailLabel = new Label("Steps");
        detailLabel.setAlignment(Pos.CENTER);
        detailLabel.setFont(new Font("Roboto", 20));
        detailLabel.setPadding(new Insets(7, 40, 3, -10));
        detailLabel.setStyle("-fx-background-color: red;");
        detailLabel.prefWidthProperty().bind(hiddenRoot.widthProperty());

        //Set TextFlow pane properties
        textFlow.prefHeightProperty().bind(hiddenRoot.heightProperty());
        textFlow.prefWidthProperty().bind(hiddenRoot.widthProperty());
        textContainer.prefHeightProperty().bind(hiddenRoot.heightProperty());
        textContainer.prefWidthProperty().bind(hiddenRoot.widthProperty());
        textFlow.setLayoutY(36);
        textContainer.setLayoutY(textFlow.getLayoutY());
        textFlow.setStyle("-fx-background-color: #dfe6e9;");
        textFlow.setPadding(new Insets(5, 0, 0, 5));    // 5 top,0 down,0 right,5 left
        textFlow.setEditable(false);
        textContainer.setContent(textFlow);

        //Set Pin/Unpin Button
        pinUnpin = new JFXButton();
        pinUnpin.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        ImageView imgPin = new ImageView(new Image(getClass().getResourceAsStream("/pinned.png")));
        imgPin.setFitHeight(20);
        imgPin.setFitWidth(20);
        ImageView imgUnpin = new ImageView(new Image(getClass().getResourceAsStream("/unpinned.png")));
        imgUnpin.setFitHeight(20);
        imgUnpin.setFitWidth(20);
        pinUnpin.setGraphic(imgPin);

        pinUnpin.setPrefSize(20, 39);
        pinUnpin.setButtonType(JFXButton.ButtonType.FLAT);

        pinUnpin.setOnMouseClicked(e -> {
            if (pinned) {
                pinUnpin.setGraphic(imgPin);
                hiddenPane.setPinnedSide(null);
                pinned = false;
            } else {
                pinUnpin.setGraphic(imgUnpin);
                hiddenPane.setPinnedSide(Side.RIGHT);
                pinned = true;
            }
        });

        //Add Label and TextFlow to hiddenPane
        hiddenRoot.getChildren().addAll(detailLabel,pinUnpin, textContainer);
        hiddenPane.setRight(hiddenRoot);    //node to be displayed in right region
        hiddenRoot.setOnMouseEntered(e -> {
            hiddenPane.setPinnedSide(Side.RIGHT);   //pane to be pinned right when hidden.
            openHidden.setVisible(false);
            e.consume();    //calls quit
        });
        graphButton.setOnMouseClicked(e ->{
            if (detailed || compared) {
                detailed = false;
                compared = false;
                hiddenPane.setPinnedSide(null);
                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(event -> {
                    hiddenRoot.prefWidthProperty().unbind(); // Unbind prefWidth
                    hiddenRoot.prefHeightProperty().unbind(); // Unbind prefHeight
                    hiddenRoot.setPrefWidth(220);
                    hiddenRoot.setPrefHeight(581);
                    detailLabel.setText("Steps");
                    textFlow.clear();
                    if (bfs || dfs || dijkstra || floyd || bellman || kruskal || prims) {
                        textFlow.appendText(Algorithm.all.toString());
                    }
                    //textFlow.setPrefSize(hiddenRoot.getPrefWidth(), hiddenRoot.getPrefHeight() - 2);
                    pinUnpin.setVisible(true);
                    openHidden.setVisible(true);
                });
                pause.play();
            }
        });

        detailsButton.setOnMouseClicked(e -> {
            if (!detailed) {
                detailed = true;
                if(compared){
                    compared = false;
                    hiddenPane.setPinnedSide(null);
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(event -> {
                        hiddenRoot.prefWidthProperty().unbind(); // Unbind prefWidth
                        hiddenRoot.prefHeightProperty().unbind(); // Unbind prefHeight
                        hiddenRoot.setPrefWidth(220);
                        hiddenRoot.setPrefHeight(581);
                        detailLabel.setText("Steps");
                        textFlow.clear();
                        if (bfs || dfs || dijkstra || floyd || bellman || kruskal || prims) {
                            textFlow.appendText(Algorithm.all.toString());
                        }
                        pinUnpin.setVisible(true);
                        openHidden.setVisible(true);
                    });
                    pause.play();
                }
                if(pinned) {
                    hiddenPane.setPinnedSide(null);
                }
                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(event -> {
                    detailLabel.setText("Details");
                    pinUnpin.setGraphic(imgPin);
                    textFlow.clear();
                    if (bfs || dfs || dijkstra || floyd || bellman || kruskal || prims) {
                        textFlow.appendText(Algorithm.all.toString());
                        if (Algorithm.finished) {
                            if(bfs) {
                                textFlow.appendText(Algorithm.bfsDetes.toString());
                            }
                            else if(dfs) {
                                textFlow.appendText(Algorithm.dfsDetes.toString());
                            }
                            else if(dijkstra) {
                                textFlow.appendText(Algorithm.dijkDetes.toString());
                            }
                            else if(floyd) {
                                textFlow.appendText(Algorithm.fwDetes.toString());
                            }
                            else if(bellman) {
                                textFlow.appendText(Algorithm.bfDetes.toString());
                            }
                            else if(kruskal) {
                                textFlow.appendText(Algorithm.krusDetes.toString());
                            }
                            else if(prims) {
                                textFlow.appendText(Algorithm.primsDetes.toString());
                            }
                            textFlow.appendText(Algorithm.altDetes.toString());
                        }
                    }
                    //textFlow.setPrefSize(border.getPrefWidth(), border.getPrefHeight() - 2);
                    hiddenRoot.prefHeightProperty().bind(border.heightProperty());
                    hiddenRoot.prefWidthProperty().bind(border.widthProperty());
                    hiddenPane.setPinnedSide(Side.RIGHT);
                    hiddenRoot.setTopAnchor(border, 73.0);
                    hiddenRoot.setRightAnchor(border, 0.0);
                    hiddenRoot.setBottomAnchor(border, 0.0);
                    hiddenRoot.setLeftAnchor(border, 200.0);
                    pinUnpin.setVisible(false);
                    openHidden.setVisible(false);
                });
                pause.play();
                hiddenPane.setPinnedSide(null);
                pinned = false;
            }
        });

        compareButton.setOnMouseClicked(e -> {
            if (!compared) {
                compared = true;
                if(detailed){
                    detailed = false;
                    hiddenPane.setPinnedSide(null);
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(event -> {
                        hiddenRoot.prefWidthProperty().unbind(); // Unbind prefWidth
                        hiddenRoot.prefHeightProperty().unbind(); // Unbind prefHeight
                        hiddenRoot.setPrefWidth(220);
                        hiddenRoot.setPrefHeight(581);
                        detailLabel.setText("Steps");
                        textFlow.clear();
                        if (bfs || dfs || dijkstra || floyd || bellman || kruskal || prims) {
                            textFlow.appendText(Algorithm.all.toString());
                        }
                        pinUnpin.setVisible(true);
                        openHidden.setVisible(true);
                    });
                    pause.play();
                }
                if(pinned) {
                    hiddenPane.setPinnedSide(null);
                }
                //hiddenPane.setTriggerDistance(0);
                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(event -> {
                    detailLabel.setText("Comparison");
                    pinUnpin.setGraphic(imgPin);
                    textFlow.clear();
                    PauseTransition pause2 = new PauseTransition(Duration.seconds(0.5));
                    pause2.setOnFinished(event2 -> {
                                compare();
                    });
                    pause2.play();
                    //textFlow.setPrefSize(border.getPrefWidth(), border.getPrefHeight() - 2);
                    hiddenRoot.prefHeightProperty().bind(border.heightProperty());
                    hiddenRoot.prefWidthProperty().bind(border.widthProperty());
                    hiddenPane.setPinnedSide(Side.RIGHT);
                    hiddenRoot.setTopAnchor(border, 73.0);
                    hiddenRoot.setRightAnchor(border, 0.0);
                    hiddenRoot.setBottomAnchor(border, 0.0);
                    hiddenRoot.setLeftAnchor(border, 200.0);
                    pinUnpin.setVisible(false);
                    openHidden.setVisible(false);
                });
                pause.play();
                hiddenPane.setPinnedSide(null);
                pinned = false;
            }
        });

        hiddenRoot.setOnMouseExited(e -> {
            if (!pinned && !detailed && !compared) {
                hiddenPane.setPinnedSide(null);
                openHidden.setVisible(true);
            }
            e.consume();
        });
        hiddenPane.setTriggerDistance(60);      // automatically expand/collapse in this range
        algo_Setup();
    }


    /**
     * Change Listener for change in speed slider values.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        temp = (int) slider.getValue();

        if (temp > 500) {
            int diff = temp - 500;
            temp = 500;
            temp -= diff;
            temp += 10;
        } else if (temp < 500) {
            int diff = 500 - temp;
            temp = 500;
            temp += diff;
            temp -= 10;
        }
        time = temp;
        System.out.println(time);
    }

    /**
     * Handles events for mouse clicks on the canvas. Adds a new node on the
     * drawing canvas where mouse is clicked.
     *
     * @param ev
     */
    @FXML
    public void handle(MouseEvent ev) {
        if (ev.getButton() == MouseButton.PRIMARY) {
            resetButton.setDisable(false);
        }
        if (addNode) {
            if (nNode == 1) {
                addNodeButton.setDisable(false);
            }
            if (nNode == 2) {
                addEdgeButton.setDisable(false);
                AddNodeHandle(null);
            }

            if (!ev.getSource().equals(canvasGroup)) {
                if (ev.getEventType() == MouseEvent.MOUSE_RELEASED && ev.getButton() == MouseButton.PRIMARY) {
                    if (menuBool == true) {
                        System.out.println("here" + ev.getEventType());
                        menuBool = false;
                        return;
                    }
                    nNode++;
                    NodeFX circle = new NodeFX(ev.getX(), ev.getY(), nodeRadius, String.valueOf(nNode));
                    canvasGroup.getChildren().add(circle);

                    circle.setOnMousePressed(mouseHandler);
                    circle.setOnMouseReleased(mouseHandler);
                    circle.setOnMouseDragged(mouseHandler);
                    circle.setOnMouseExited(mouseHandler);
                    circle.setOnMouseEntered(mouseHandler);

                    ScaleTransition tr = new ScaleTransition(Duration.millis(100), circle);
                    tr.setByX(10f);
                    tr.setByY(10f);
                    tr.setInterpolator(Interpolator.EASE_OUT);
                    tr.play();

                }
            }
        }
    }

    /**
     * Checks if an edge already exists between two nodes before adding a new
     * edge.
     *
     * @param u = selected node
     * @param v = second selected node
     * @return True if edge already exists. Else false.
     */
    boolean edgeExists(NodeFX u, NodeFX v) {
        for (Edge e : realEdges) {
            //if(e.source.equals(u.node))
            if (e.source == u.node && e.target == v.node) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an edge between two selected nodes. Handles events for mouse clicks
     * on a node.
     */
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            NodeFX circle = (NodeFX) mouseEvent.getSource();
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {

                if (!circle.isSelected) {
                    if (selectedNode != null) {
                        if (addEdge && !edgeExists(selectedNode, circle)) {
                            weight = new Label();
                            //System.out.println("Adding Edge");
                            //Adds the edge between two selected nodes
                            double angle = Math.atan2((circle.point.y - selectedNode.point.y), (circle.point.x - selectedNode.point.x));
                            double startX = selectedNode.point.x + nodeRadius * 12* Math.cos(angle);
                            double startY = selectedNode.point.y + nodeRadius * 12* Math.sin(angle);
                            double endX = circle.point.x + nodeRadius *12 * Math.cos(angle + Math.PI);
                            double endY = circle.point.y + nodeRadius *12* Math.sin(angle + Math.PI);
                            if (SelectionController.undirected) {
                                edgeLine = new Line(startX, startY,endX, endY);
                                edgeLine.setStroke(Color.BLACK);
                                canvasGroup.getChildren().add(edgeLine);
                                edgeLine.setId("line");
                            } else if (SelectionController.directed) {
                                arrow = new Arrow(startX, startY,endX, endY);
                                canvasGroup.getChildren().add(arrow);
                                arrow.setId("arrow");
                            }
                            //Adds weight between two selected nodes
                            if (SelectionController.weighted) {
                                weight.setLayoutX(((selectedNode.point.x) + (circle.point.x)) / 2);
                                weight.setLayoutY(((selectedNode.point.y) + (circle.point.y)) / 2);

                                TextInputDialog dialog = new TextInputDialog("0");
                                dialog.setTitle(null);
                                dialog.setHeaderText("Enter Weight of the Edge :");
                                dialog.setContentText(null);

                                Optional<String> result = dialog.showAndWait();
                                if (result.isPresent()) {
                                    String value = result.get();
                                    value = value.replaceAll("^\\+", "");
                                    if (value.matches("-?\\d+(\\.\\d+)?")) {
                                        weight.setText(value);
                                    } else {
                                        weight.setText("0");
                                    }
                                } else {
                                    weight.setText("0");
                                }
                                canvasGroup.getChildren().add(weight);
                            } else if (SelectionController.unweighted) {
                                weight.setText("1");
                            }
                            Shape line_arrow = null;
                            Edge temp = null;
                            if (SelectionController.undirected) {
                                if(Integer.valueOf(selectedNode.node.name)<Integer.valueOf(circle.node.name)) {
                                    temp = new Edge(selectedNode.node, circle.node, Integer.valueOf(weight.getText()), edgeLine, weight);
                                }
                                else {
                                    temp = new Edge(circle.node,selectedNode.node, Integer.valueOf(weight.getText()), edgeLine, weight);
                                }
                                if (SelectionController.weighted) {
                                    mstEdges.add(temp);
                                    bellmanEdges.add(temp);
                                }
                                if(Integer.valueOf(selectedNode.node.name)<Integer.valueOf(circle.node.name)) {
                                    selectedNode.node.adjacents.add(new Edge(selectedNode.node, circle.node, Double.valueOf(weight.getText()), edgeLine, weight));
                                }
                                else{
                                    selectedNode.node.adjacents.add(new Edge(circle.node,selectedNode.node, Double.valueOf(weight.getText()), edgeLine, weight));
                                }
                                circle.node.adjacents.add(new Edge(circle.node, selectedNode.node, Double.valueOf(weight.getText()), edgeLine, weight));
                                edges.add(edgeLine);
                                realEdges.add(selectedNode.node.adjacents.get(selectedNode.node.adjacents.size() - 1));
                                realEdges.add(circle.node.adjacents.get(circle.node.adjacents.size() - 1));
                                line_arrow = edgeLine;

                            } else if (SelectionController.directed) {
                                temp = new Edge(selectedNode.node, circle.node, Double.valueOf(weight.getText()), arrow, weight);
                                selectedNode.node.adjacents.add(temp);
//                                circle.node.revAdjacents.add(new Edge(circle.node, selectedNode.node, Integer.valueOf(weight.getText()), arrow));
                                edges.add(arrow);
                                line_arrow = arrow;
                                realEdges.add(temp);
                                if(SelectionController.weighted) {
                                    bellmanEdges.add(temp);
                                }
                            }

                            RightClickMenu rt = new RightClickMenu(temp);
                            ContextMenu menu = rt.getMenu();
                            if (SelectionController.weighted) {
                                rt.change.setText("Change Weight");
                            } else if (SelectionController.unweighted) {
                                rt.change.setDisable(true);
                            }
                            final Shape la = line_arrow;
                            line_arrow.setOnContextMenuRequested(e -> {
                                System.out.println("In Edge Menu :" + menuBool);

                                if (menuBool == true) {
                                    globalMenu.hide();
                                    menuBool = false;
                                }
                                if (addEdge || addNode) {
                                    globalMenu = menu;
                                    menu.show(la, e.getScreenX(), e.getScreenY());
                                    menuBool = true;
                                }
                            });
                            menu.setOnAction(e -> {
                                menuBool = false;
                            });
                        }
                        if (addNode || (calculate && !calculated) || addEdge) {
                            selectedNode.isSelected = false;
                            FillTransition ft1 = new FillTransition(Duration.millis(300), selectedNode, Color.RED, Color.BLACK);
                            ft1.play();
                        }
                        selectedNode = null;
                        return;
                    }

                    FillTransition ft = new FillTransition(Duration.millis(300), circle, Color.BLACK, Color.RED);
                    ft.play();
                    circle.isSelected = true;
                    selectedNode = circle;

                    // WHAT TO DO WHEN SELECTED ON ACTIVE ALGORITHM
                    if (calculate && !calculated) {
                        if (bfs) {
                            algo.newBFS(circle.node);
                        } else if (dfs) {
                            algo.newDFS(circle.node);
                        } else if (dijkstra) {
                            algo.newDijkstra(circle.node);
                        }  else if (floyd) {
                            algo.newFloydWarshall(circle.node);
                        }
                        else if (bellman) {
                            algo.newBellmanFord(circle.node);
                        }
                        nodeList.setDisable(true);
                        playPauseButton.setDisable(false);
                        calculated = true;
                    } else if (calculate && calculated && !kruskal &&!prims && !floyd) {

                        for (NodeFX n : circles) {
                            n.isSelected = false;
                            FillTransition ft1 = new FillTransition(Duration.millis(300), n);
                            ft1.setToValue(Color.BLACK);
                            ft1.play();
                        }
                        List<Node> path = algo.getShortestPathTo(circle.node);
                        for (Node n : path) {
                            FillTransition ft1 = new FillTransition(Duration.millis(300), n.circle);
                            ft1.setToValue(Color.BLUE);
                            ft1.play();
                        }
                    }
                } else {
                    circle.isSelected = false;
                    FillTransition ft1 = new FillTransition(Duration.millis(300), circle, Color.RED, Color.BLACK);
                    ft1.play();
                    selectedNode = null;
                }

            }
        }

    };

    /**
     * Event handler for the Play/Pause button.
     *
     * @param event
     */
    @FXML
    public void PlayPauseHandle(ActionEvent event) {
        //System.out.println("IN PLAYPAUSE");
        System.out.println(playing + " " + paused);

        try {
            if (playing && st != null && st.getStatus() == Animation.Status.RUNNING) {
                Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                playPauseImage.setImage(image);
                playPause.setText("Play");
                st.pause();
                paused = true;
                playing = false;
                prevButton.setDisable(false);
                nextButton.setDisable(false);
            } else if (paused && st != null) {
                Image image = new Image(getClass().getResourceAsStream("/pause_black_48x48.png"));
                playPauseImage.setImage(image);
                playPause.setText("Pause");
                if (st.getStatus() == Animation.Status.PAUSED)
                    st.play();
                else if (st.getStatus() == Animation.Status.STOPPED)
                    st.playFromStart();
                playing = true;
                paused = false;
                prevButton.setDisable(true);
                nextButton.setDisable(true);
            }
        } catch (Exception e) {
            System.out.println("Error while play/pause: " + e);
            ClearHandle(null);
        }
    }

    @FXML
    public void NextStepHandle(ActionEvent event) {
        try {
            if (st != null && !st.getChildren().isEmpty()) {
                Duration currentTime = st.getCurrentTime();
                Duration totalDuration = st.getTotalDuration();
                if (currentTime.greaterThanOrEqualTo(totalDuration)) {
                    System.out.println("Animation has already reached the end.");
                    nextButton.setDisable(true);
                    return;
                }
                playPauseButton.setDisable(false);
                prevButton.setDisable(false);
                Duration stepForwardTime = currentTime.add(Duration.millis(time));
                if (stepForwardTime.greaterThan(totalDuration)) {
                    stepForwardTime = totalDuration;
                }
                List<Transition> transitionsInRange = getTransitionsInRange(currentTime, stepForwardTime);
                st.jumpTo(stepForwardTime);
                executeOnFinishedForTransitions(transitionsInRange);
            }
        } catch (Exception e) {
            System.out.println("Error while next step: " + e);
            ClearHandle(null);
        }
    }

    @FXML
    public void PrevStepHandle(ActionEvent event) {
        try {
            if (st != null && !st.getChildren().isEmpty()) {
                Duration currentTime = st.getCurrentTime();
                Duration stepBackTime = currentTime.subtract(Duration.millis(time));
                if (stepBackTime.lessThanOrEqualTo(Duration.ZERO)) {
                    stepBackTime = Duration.ZERO;
                    prevButton.setDisable(true);
                }
                List<Transition> transitionsInRange = getTransitionsInRange(Duration.ZERO, stepBackTime);
                playPauseButton.setDisable(false);
                detailsButton.setDisable(true);
                compareButton.setDisable(true);
                nextButton.setDisable(false);
                if(dijkstra || bellman || floyd) {
                    universalNode.circle.distance.setText("Dist. : " + 0);
                    for (Label x : distances) {
                        x.setText("Distance : INFINITY");
                    }
                }
                st.jumpTo(stepBackTime);
                textFlow.clear();
                executeOnFinishedForTransitions(transitionsInRange);
            }
        } catch (Exception e) {
            System.out.println("Error while prev step: " + e);
            ClearHandle(null);
        }
    }

    private List<Transition> getTransitionsInRange(Duration startTime, Duration endTime) {
        List<Transition> transitionsInRange = new ArrayList<>();
        Duration cumulativeTime = Duration.ZERO;

        for (Animation animation : st.getChildren()) {
            if (animation instanceof Transition) {
                Transition transition = (Transition) animation;
                Duration transitionDuration = transition.getCycleDuration();
                cumulativeTime = cumulativeTime.add(transitionDuration);

                if (cumulativeTime.greaterThan(startTime) && cumulativeTime.lessThanOrEqualTo(endTime)) {
                    transitionsInRange.add(transition);
                }
            }
        }
        return transitionsInRange;
    }
    private void executeOnFinishedForTransitions(List<Transition> transitions) {
        for (Transition transition : transitions) {
            if (transition.getOnFinished() != null) {
                transition.getOnFinished().handle(new ActionEvent());
            }
        }
    }

    /**
     * Event handler for the Screenshot button.
     *
     * @param event
     */

    @FXML
    public void ScrrenshotHandle(ActionEvent event) {
        System.out.println("taking ss");

        try {
            WritableImage writableImage = new WritableImage((int) GraphifyMain.primaryStage.getWidth(), (int) GraphifyMain.primaryStage.getHeight());
            GraphifyMain.primaryStage.getScene().snapshot(writableImage);
            BufferedImage screenShot = convertToBufferedImage(writableImage);
            String baseFileName = System.getProperty("user.home") + "/Pictures/Screenshots/GraphifyScreenshot.png";
            File file = getUniqueFile(baseFileName);
            ImageIO.write(screenShot, "png", file);

            //System.out.println("Screenshot saved as: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static File getUniqueFile(String baseFileName) {
        File file = new File(baseFileName);
        int counter = 1;
        while (file.exists()) {
            file = new File(baseFileName + "_" + counter + ".png");
            counter++;
        }
        return file;
    }
    private static BufferedImage convertToBufferedImage(WritableImage writableImage) {
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        PixelReader pixelReader = writableImage.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color fxColor = pixelReader.getColor(x, y);
                int argb = (int) (fxColor.getOpacity() * 255) << 24 |
                        (int) (fxColor.getRed() * 255) << 16 |
                        (int) (fxColor.getGreen() * 255) << 8 |
                        (int) (fxColor.getBlue() * 255);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        return bufferedImage;
    }
    /**
     * Event handler for the Skip button.
     *
     * @param event
     */
    @FXML
    public void SkipHandle(ActionEvent event) {
        if (st != null && st.getCurrentTime().greaterThanOrEqualTo(st.getTotalDuration())){
            return;
        }
        try {
            while (st.getCurrentTime().lessThan(st.getTotalDuration())){
                NextStepHandle(null);
            }
            playPauseButton.setDisable(true);
            //st.pause();
            nextButton.setDisable(true);
            detailsButton.setDisable(false);
            compareButton.setDisable(false);
        } catch (Exception e) {
            System.out.println("Error while skipping: " + e);
            ClearHandle(null);
        }
    }
    /**
     * Event handler for the Skip button.
     *
     * @param event
     */
    @FXML
    public void InitHandle(ActionEvent event) {
        if (st != null && st.getCurrentTime().lessThanOrEqualTo(Duration.ZERO)){
            return;
        }
        try {
            while (st.getCurrentTime().greaterThan(Duration.ZERO)){
                PrevStepHandle(null);
            }
            playPauseButton.setDisable(false);
            Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
            playPauseImage.setImage(image);
            playPause.setText("Play");
            st.pause();
            paused = true;
            playing = false;
            nextButton.setDisable(false);

        } catch (Exception e) {
            System.out.println("Error while skipping: " + e);
            ClearHandle(null);
        }
    }

    /**
     * Event handler for the Reload button.
     *
     * @param event
     */
    @FXML
    public void ReloadHandle(ActionEvent event) {
        if (universalNode != null && !detailed) {
            if(dfs){
                ClearHandle(null);
                dfsButton.setSelected(true);
                dfs=true;
                algo.newDFS(universalNode);
                nodeList.setDisable(true);
                playPauseButton.setDisable(false);
            }
            else if (bfs) {
                ClearHandle(null);
                bfsButton.setSelected(true);
                bfs=true;
                algo.newBFS(universalNode);
                nodeList.setDisable(true);
                playPauseButton.setDisable(false);
            }
            else if(dijkstra) {
                ClearHandle(null);
                dijkstraButton.setSelected(true);
                dijkstra=true;
                algo.newDijkstra(universalNode);
                nodeList.setDisable(true);
                playPauseButton.setDisable(false);
            }
            else if(bellman) {
                ClearHandle(null);
                bellmanButton.setSelected(true);
                bellman=true;
                algo.newBellmanFord(universalNode);
                nodeList.setDisable(true);
                playPauseButton.setDisable(false);
            }
            else if(floyd) {
                ClearHandle(null);
                floydButton.setSelected(true);
                floyd=true;
                algo.newFloydWarshall(universalNode);
                nodeList.setDisable(true);
                playPauseButton.setDisable(false);
            }
            addNodeButton.setDisable(true);
            addEdgeButton.setDisable(true);
        } else if(!detailed){
            if(kruskal){
                ClearHandle(null);
                kruskalButton.setSelected(true);
                kruskal=true;
                algo.newKruskal();
                addNodeButton.setDisable(true);
                addEdgeButton.setDisable(true);
                nodeList.setDisable(true);
                playPauseButton.setDisable(false);
            } else if (prims) {
                ClearHandle(null);
                kruskalButton.setSelected(true);
                prims=true;
                addNodeButton.setDisable(true);
                addEdgeButton.setDisable(true);
                algo.newPrims();
                nodeList.setDisable(true);
                playPauseButton.setDisable(false);
            }
            else {
                System.out.println("Nothing played yet. Unable to replay.");
            }
        }
    }

    /**
     * Event handler for the Reset button. Clears all the lists and empties the
     * canvas.
     *
     * @param event
     */
    @FXML
    public void ResetHandle(ActionEvent event) {
        ClearHandle(null);
        nNode = 0;
        canvasGroup.getChildren().clear();
        canvasGroup.getChildren().addAll(viewer);
        selectedNode = null;
        circles = new ArrayList<NodeFX>();
        distances = new ArrayList<Label>();
        visitTime = new ArrayList<Label>();
        lowTime = new ArrayList<Label>();
        realEdges.clear();
        mstEdges.clear();
        bellmanEdges.clear();
        addNode = true;
        addEdge = false;
        calculate = false;
        calculated = false;
        pinned=false;
        addNodeButton.setSelected(true);
        addEdgeButton.setSelected(false);
        addEdgeButton.setDisable(true);
        addNodeButton.setDisable(false);
        clearButton.setDisable(true);
        algo = new Algorithm();
        Image image = new Image(getClass().getResourceAsStream("/pause_black_48x48.png"));
        ImageView imgPin = new ImageView(new Image(getClass().getResourceAsStream("/pinned.png")));
        playPauseImage.setImage(image);
        if(pinUnpin!=null)
            pinUnpin.setGraphic(imgPin);
        hiddenPane.setPinnedSide(null);

        bfsButton.setDisable(true);
        floydButton.setDisable(true);
        dfsButton.setDisable(true);
        dijkstraButton.setDisable(true);
        bellmanButton.setDisable(true);
        kruskalButton.setDisable(true);
        primsButton.setDisable(true);
        playing = false;
        paused = false;
    }

    /**
     * Event handler for the Clear button. Re-initiates the distance and node
     * values and labels.
     *
     * @param event
     */
    @FXML
    public void ClearHandle(ActionEvent event) {
        if (st != null && st.getStatus() != Animation.Status.STOPPED)
            st.stop();
        if (st != null)
            st.getChildren().clear();
        menuBool = false;
        selectedNode = null;
        calculated = false;
        nodeList.setDisable(false);
        playPauseButton.setDisable(true);
        compareButton.setDisable(true);
        detailsButton.setDisable(true);
        prevButton.setDisable(true);
        nextButton.setDisable(true);
        for (NodeFX n : circles) {
            n.isSelected = false;
            n.node.visited = false;
            n.node.previous = null;
            n.node.minDistance = Double.POSITIVE_INFINITY;
            n.node.DAGColor = 0;

            FillTransition ft1 = new FillTransition(Duration.millis(300), n);
            ft1.setToValue(Color.BLACK);
            ft1.play();
        }
        for(Edge e:mstEdges){
            e.weightLabel.setStyle("-fx-text-fill: red;");
        }
        for (Shape x : edges) {
            if (SelectionController.undirected) {
                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), x);
                ftEdge.setToValue(Color.BLACK);
                ftEdge.play();
            } else if (SelectionController.directed) {
                FillTransition ftEdge = new FillTransition(Duration.millis(time), x);
                ftEdge.setToValue(Color.BLACK);
                ftEdge.play();
            }
        }
        canvasGroup.getChildren().remove(sourceText);
        for (Label x : distances) {
            x.setText("Distance : INFINITY");
            canvasGroup.getChildren().remove(x);
        }
        for (Label x : visitTime) {
            x.setText("Visit : 0");
            canvasGroup.getChildren().remove(x);
        }
        for (Label x : lowTime) {
            x.setText("Low Value : NULL");
            canvasGroup.getChildren().remove(x);
        }
        if(!compared) {
            textFlow.clear();
        }

        Image image = new Image(getClass().getResourceAsStream("/pause_black_48x48.png"));
        playPauseImage.setImage(image);

        distances = new ArrayList<>();
        visitTime = new ArrayList<>();
        lowTime = new ArrayList<>();
        addNodeButton.setDisable(false);
        addEdgeButton.setDisable(false);
        AddNodeHandle(null);
        bfs = false;
        dfs = false;
        bellman=false;
        dijkstra = false;
        kruskal = false;
        prims=false;
        floyd = false;
        playing = false;
        paused = false;
    }

    /**
     * Event handler for the Add Edge button.
     *
     * @param event
     */
    @FXML
    public void AddEdgeHandle(ActionEvent event) {
        ClearHandle(null);
        addNode = false;
        addEdge = true;
        //selectedNode = null;
        calculate = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(true);

        if (SelectionController.unweighted) {
            bfsButton.setDisable(false);
            bfsButton.setSelected(false);
            dfsButton.setDisable(false);
            dfsButton.setSelected(false);
        }
        if (SelectionController.weighted) {
            dijkstraButton.setDisable(false);
            dijkstraButton.setSelected(false);
            bellmanButton.setDisable(false);
            bellmanButton.setSelected(false);
            floydButton.setDisable(false);
            floydButton.setSelected(false);
            if (SelectionController.undirected) {
                kruskalButton.setDisable(false);
                kruskalButton.setSelected(false);
                primsButton.setDisable(false);
                primsButton.setSelected(false);
            }
        }
    }

    @FXML
    public void AddNodeHandle(ActionEvent event) {
        addNode = true;
        addEdge = false;
        calculate = false;
        addNodeButton.setSelected(true);
        addEdgeButton.setSelected(false);
        selectedNode = null;

        if (SelectionController.unweighted) {
            bfsButton.setDisable(false);
            bfsButton.setSelected(false);
            dfsButton.setDisable(false);
            dfsButton.setSelected(false);
        }
        if (SelectionController.weighted) {
            dijkstraButton.setDisable(false);
            dijkstraButton.setSelected(false);
            bellmanButton.setDisable(false);
            bellmanButton.setSelected(false);
            floydButton.setDisable(false);
            floydButton.setSelected(false);
            if (SelectionController.undirected) {
                kruskalButton.setDisable(false);
                kruskalButton.setSelected(false);
                primsButton.setDisable(false);
                primsButton.setSelected(false);
            }
        }
    }

    @FXML
    public void BFSHandle(ActionEvent event) {
        ClearHandle(null);
        bfsButton.setSelected(true);
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        bfs = true;
    }

    @FXML
    public void DFSHandle(ActionEvent event) {
        ClearHandle(null);
        dfsButton.setSelected(true);
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        dfs = true;
    }

    @FXML
    public void FloydWarshallHandle(ActionEvent event) {
        ClearHandle(null);
        floydButton.setSelected(true);
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        floyd=true;
    }

    @FXML
    public void DijkstraHandle(ActionEvent event) {
        ClearHandle(null);
        dijkstraButton.setSelected(true);
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        dijkstra = true;
    }

    @FXML
    public void BellmanHandle(ActionEvent event) {
        ClearHandle(null);
        bellmanButton.setSelected(true);
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        bellman = true;
    }

    @FXML
    public void KruskalHandle(ActionEvent event) {
        ClearHandle(null);
        kruskalButton.setSelected(true);
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        kruskal = true;
        algo.newKruskal();
        nodeList.setDisable(true);
        playPauseButton.setDisable(false);
    }

    @FXML
    public void PrimsHandle(ActionEvent event) {
        ClearHandle(null);
        primsButton.setSelected(true);
        addNode = false;
        addEdge = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(false);
        addNodeButton.setDisable(true);
        addEdgeButton.setDisable(true);
        calculate = true;
        clearButton.setDisable(false);
        prims=true;
        algo.newPrims();
        nodeList.setDisable(true);
        playPauseButton.setDisable(false);
    }

    /**
     * Changes the current Name/ID of a node.
     *
     * @param source Node reference of selected node
     */
    public void changeID(NodeFX source) {
        selectedNode = null;

        TextInputDialog dialog = new TextInputDialog(Integer.toString(nNode));
        dialog.setTitle(null);
        dialog.setHeaderText("Enter Node ID :");
        dialog.setContentText(null);

        String res = null;
        String cur=source.id.getText();
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String value = result.get();
            value = value.replaceAll("^\\+", "");
            if (value.matches("-?\\d+") && Integer.parseInt(value)>0) {
                res=value;
                for(NodeFX n:circles){
                    if(Integer.parseInt(n.id.getText())==Integer.parseInt(value)){
                        res=cur;
                        break;
                    }
                }
            } else {
                res=cur;
                System.out.println("Invalid ID");
            }
        }

        circles.get(circles.indexOf(source)).id.setText(res);
        circles.get(circles.indexOf(source)).node.name = res;

    }

    /**
     * Deletes the currently selected node.
     *
     * @param sourceFX
     */
    public void deleteNode(NodeFX sourceFX) {
        selectedNode = null;

        Node source = sourceFX.node;
        circles.remove(sourceFX);

        List<Edge> tempEdges = new ArrayList<>();
        List<Node> tempNodes = new ArrayList<>();
        for (Edge e : source.adjacents) {
            Node u = e.target;
            for (Edge x : u.adjacents) {
                if (x.target == source) {
                    x.target = null;
                    tempNodes.add(u);
                    tempEdges.add(x);
                }
            }
            edges.remove(e.getLine());
            canvasGroup.getChildren().remove(e.getLine());
            mstEdges.remove(e);
            bellmanEdges.remove(e);
            // Remove associated weight label
            canvasGroup.getChildren().remove(e.weightLabel);
        }
        for (Node q : tempNodes) {
            q.adjacents.removeAll(tempEdges);
        }
        List<Edge> tempEdges2 = new ArrayList<>();
        List<Shape> tempArrows = new ArrayList<>();
        List<Node> tempNodes2 = new ArrayList<>();
        for (NodeFX z : circles) {
            for (Edge s : z.node.adjacents) {
                if (s.target == source) {
                    tempEdges2.add(s);
                    tempArrows.add(s.line);
                    tempNodes2.add(z.node);
                    canvasGroup.getChildren().remove(s.line);
                    canvasGroup.getChildren().remove(s.weightLabel);
                }
            }
        }
        for (Node z : tempNodes2) {
            z.adjacents.removeAll(tempEdges2);
        }
        realEdges.removeAll(tempEdges);
        realEdges.removeAll(tempEdges2);
        if(SelectionController.weighted){
            bellmanEdges.removeAll(tempEdges);
            bellmanEdges.removeAll(tempEdges2);
        }
        canvasGroup.getChildren().remove(sourceFX.id);
        canvasGroup.getChildren().remove(sourceFX);

    }

    /**
     * Deletes the currently selected Edge.
     *
     * @param sourceEdge
     */
    public void deleteEdge(Edge sourceEdge) {

        System.out.println(sourceEdge.source.name + " -- " + sourceEdge.target.name);
        List<Edge> ls1 = new ArrayList<>();
        List<Shape> lshape2 = new ArrayList<>();
        for (Edge e : sourceEdge.source.adjacents) {
            if (e.target == sourceEdge.target) {
                ls1.add(e);
                lshape2.add(e.line);
                // Remove associated weight label
                canvasGroup.getChildren().remove(e.weightLabel);
            }
        }
        for (Edge e : sourceEdge.target.adjacents) {
            if (e.target == sourceEdge.source) {
                ls1.add(e);
                lshape2.add(e.line);
                // Remove associated weight label
                canvasGroup.getChildren().remove(e.weightLabel);
            }
        }
        sourceEdge.source.adjacents.removeAll(ls1);
        sourceEdge.target.adjacents.removeAll(ls1);
        realEdges.removeAll(ls1);
        if(SelectionController.weighted) {
            bellmanEdges.removeAll(ls1);
        }

        edges.removeAll(lshape2);
        canvasGroup.getChildren().removeAll(lshape2);

    }


    /**
     * Change weight of the currently selected edge. Disabled for unweighted
     * graphs.
     *
     * @param sourceEdge
     */
    public void changeWeight(Edge sourceEdge) {

        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle(null);
        dialog.setHeaderText("Enter Weight of the Edge :");
        dialog.setContentText(null);

        String res = null;
        Optional<String> result = dialog.showAndWait();
        String cur=sourceEdge.weightLabel.getText();
        if (result.isPresent()) {
            String value = result.get();
            value = value.replaceAll("^\\+", "");
            if (value.matches("-?\\d+(\\.\\d+)?")) {
                res=value;
            } else {
                res=cur;
            }
        }
        for (Edge e : sourceEdge.source.adjacents) {
            if (e.target == sourceEdge.target) {
                e.weight = Double.valueOf(res);
                e.weightLabel.setText(res);
            }
        }
        for (Edge e : sourceEdge.target.adjacents) {
            if (e.target == sourceEdge.source) {
                e.weight = Double.valueOf(res);
            }
        }
        for (Edge e : mstEdges) {
            if (e.source == sourceEdge.source && e.target == sourceEdge.target) {
                e.weight = Double.valueOf(res);
            }
        }

    }

    /**
     * Shape class for the nodes.
     */
    public class NodeFX extends Circle {

        Node node;
        Point point;
        Label distance = new Label("Dist. : INFINITY");
        Label id;
        boolean isSelected = false;

        public NodeFX(double x, double y, double rad, String name) {
            super(x, y, rad);
            point = new Point((int) x, (int) y);
            node = new Node(name, this);
            id = new Label(name);
            canvasGroup.getChildren().add(id);
            id.setLayoutX(x-5);
            id.setLayoutY(y-12);
            id.setStyle("-fx-font-size: 18px;");
            this.setOpacity(0.45);
            this.setBlendMode(BlendMode.MULTIPLY);
            this.setId("node");

            RightClickMenu rt = new RightClickMenu(this);
            ContextMenu menu = rt.getMenu();
            globalMenu = menu;
            this.setOnContextMenuRequested(e -> {
                if (addEdge || addNode) {
                    menu.show(this, e.getScreenX(), e.getScreenY());
                    menuBool = true;
                }
            });
            menu.setOnAction(e -> {
                menuBool = false;
            });

            circles.add(this);
            //System.out.println("ADDing: " + circles.size());
        }
    }

    public void compare()
    {
        if(bfs)
        {
            ClearHandle(null);
            StringBuilder bfsstr= new StringBuilder("\n\t\tFor BFS:\n\n");
            bfsstr.append(Algorithm.bfsDetes);
            dfs=true;
            algo.newDFS(universalNode);
            SkipHandle(null);
            textFlow.appendText(bfsstr.toString());
            StringBuilder dfsstr= new StringBuilder("\n\t\tFor DFS:\n\n");
            dfsstr.append(Algorithm.dfsDetes);
            ClearHandle(null);
            algo.newBFS(universalNode);
            SkipHandle(null);
            textFlow.appendText(dfsstr.toString());
            textFlow.appendText("\n\t\t---Comparison Finished---");
            bfs=true;
            bfsButton.setSelected(true);
        }
        else if(dfs)
        {
            ClearHandle(null);
            StringBuilder dfsstr= new StringBuilder("\n\t\tFor DFS:\n\n");
            dfsstr.append(Algorithm.dfsDetes);
            bfs=true;
            algo.newBFS(universalNode);
            SkipHandle(null);
            textFlow.appendText(dfsstr.toString());
            StringBuilder bfsstr= new StringBuilder("\n\t\tFor BFS:\n\n");
            bfsstr.append(Algorithm.bfsDetes);
            ClearHandle(null);
            algo.newDFS(universalNode);
            SkipHandle(null);
            textFlow.appendText(bfsstr.toString());
            textFlow.appendText("\n\t\t---Comparison Finished---");
            dfs=true;
            dfsButton.setSelected(true);
        }
        else if(dijkstra)
        {
            ClearHandle(null);
            StringBuilder dijkstr= new StringBuilder("\n\t\tFor Dijkstra:\n\n");
            dijkstr.append(Algorithm.dijkDetes);
            floyd=true;
            algo.newFloydWarshall(universalNode);
            SkipHandle(null);
            textFlow.appendText(dijkstr.toString());
            StringBuilder fwstr= new StringBuilder("\n\t\tFor Floyd Warshall :\n\n");
            fwstr.append(Algorithm.fwDetes);
            ClearHandle(null);
            bellman=true;
            algo.newBellmanFord(universalNode);
            SkipHandle(null);
            StringBuilder bfstr= new StringBuilder("\n\t\tFor Bellman Ford :\n\n");
            bfstr.append(Algorithm.bfDetes);
            ClearHandle(null);
            algo.newDijkstra(universalNode);
            SkipHandle(null);
            textFlow.appendText(fwstr.toString());
            textFlow.appendText(bfstr.toString());
            textFlow.appendText("\n\t\t---Comparison Finished---");
            dijkstra=true;
            dijkstraButton.setSelected(true);
        }
        else if(bellman)
        {
            ClearHandle(null);
            StringBuilder bfstr= new StringBuilder("\n\t\tFor Bellman Ford:\n\n");
            bfstr.append(Algorithm.bfDetes);
            floyd=true;
            algo.newFloydWarshall(universalNode);
            SkipHandle(null);
            textFlow.appendText(bfstr.toString());
            StringBuilder fwstr= new StringBuilder("\n\t\tFor Floyd Warshall :\n\n");
            fwstr.append(Algorithm.fwDetes);
            ClearHandle(null);
            dijkstra=true;
            algo.newDijkstra(universalNode);
            SkipHandle(null);
            StringBuilder dijkstr= new StringBuilder("\n\t\tFor Dijkstra :\n\n");
            dijkstr.append(Algorithm.dijkDetes);
            ClearHandle(null);
            algo.newBellmanFord(universalNode);
            SkipHandle(null);
            textFlow.appendText(fwstr.toString());
            textFlow.appendText(dijkstr.toString());
            textFlow.appendText("\n\t\t---Comparison Finished---");
            bellman=true;
            bellmanButton.setSelected(true);
        }
        else if(floyd)
        {
            ClearHandle(null);
            StringBuilder fwstr= new StringBuilder("\n\t\tFor Floyd Warshall:\n\n");
            fwstr.append(Algorithm.fwDetes);
            dijkstra=true;
            algo.newDijkstra(universalNode);
            SkipHandle(null);
            textFlow.appendText(fwstr.toString());
            StringBuilder dijkstr= new StringBuilder("\n\t\tFor Dijkstra :\n\n");
            dijkstr.append(Algorithm.dijkDetes);
            ClearHandle(null);
            bellman=true;
            algo.newBellmanFord(universalNode);
            SkipHandle(null);
            StringBuilder bfstr= new StringBuilder("\n\t\tFor Bellman Ford :\n\n");
            bfstr.append(Algorithm.bfDetes);
            ClearHandle(null);
            algo.newFloydWarshall(universalNode);
            SkipHandle(null);
            textFlow.appendText(dijkstr.toString());
            textFlow.appendText(bfstr.toString());
            textFlow.appendText("\n\t\t---Comparison Finished---");
            floyd=true;
            floydButton.setSelected(true);
        }
        else if(kruskal)
        {
            ClearHandle(null);
            StringBuilder krusstr= new StringBuilder("\n\t\tFor Kruskal:\n\n");
            krusstr.append(Algorithm.krusDetes);
            prims=true;
            algo.newPrims();
            SkipHandle(null);
            textFlow.appendText(krusstr.toString());
            StringBuilder primsstr= new StringBuilder("\n\t\tFor Prims:\n\n");
            primsstr.append(Algorithm.primsDetes);
            ClearHandle(null);
            algo.newKruskal();
            SkipHandle(null);
            textFlow.appendText(primsstr.toString());
            textFlow.appendText("\n\t\t---Comparison Finished---");
            kruskal=true;
            kruskalButton.setSelected(true);
        }
        else if(prims)
        {
            ClearHandle(null);
            StringBuilder primsstr= new StringBuilder("\n\t\tFor Prims:\n\n");
            primsstr.append(Algorithm.primsDetes);
            kruskal=true;
            algo.newKruskal();
            SkipHandle(null);
            textFlow.appendText(primsstr.toString());
            StringBuilder krusstr= new StringBuilder("\n\t\tFor Kruskal:\n\n");
            krusstr.append(Algorithm.krusDetes);
            ClearHandle(null);
            algo.newPrims();
            SkipHandle(null);
            textFlow.appendText(krusstr.toString());
            textFlow.appendText("\n\t\t---Comparison Finished---");
            prims=true;
            primsButton.setSelected(true);
        }
    }
    public void algo_Setup() {
        Algorithm.canvasGroup = canvasGroup;
        Algorithm.sourceText = sourceText;
        Algorithm.playPauseImage = playPauseImage;
        Algorithm.nodeList=nodeList;
        Algorithm.playPauseButton=playPauseButton;
        Algorithm.compareButton=compareButton;
        Algorithm.detailsButton=detailsButton;
    }

}

