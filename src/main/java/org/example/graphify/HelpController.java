package org.example.graphify;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.application.HostServices;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelpController implements Initializable {
    @FXML
    private JFXToggleButton bfsButton, dfsButton, floydButton, dijkstraButton,
            bellmanButton, kruskalButton,primsButton;
    @FXML
    private JFXButton canvasBackButton;

    @FXML
    private Label title;

    @FXML
    private TextFlow textArea;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    Text textNode1,textNode2,textNode3,textNode4,textNode5;
    private HostServices hostServices;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textArea.prefWidthProperty().bind(scrollPane.widthProperty());
        textArea.prefHeightProperty().bind(scrollPane.heightProperty());
        BFSHandle(null);
        canvasBackButton.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("Selection.fxml"));
                Scene scene = new Scene(root);
                GraphifyMain.primaryStage.setScene(scene);
            } catch (IOException ex) {
                System.out.println("Back e jay na");
                Logger.getLogger(CanvasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    public void BFSHandle(ActionEvent event){
        System.out.println("BFS explanation");
        title.setText("BFS");
        textArea.getChildren().clear();

        //<editor-fold defaultstate="collapsed" desc="Setting Up Text">
        Label descriptionLabel = new Label("\nIntroduction:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introTitle = new Text(descriptionLabel.getText());
        introTitle.setFill(Paint.valueOf("red"));
        introTitle.setWrappingWidth(scrollPane.getPrefWidth());
        introTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("We utilize the breadth-first search or BFS algorithm to search a tree or graph data structure for a node that meets a set of criteria. We begin at the root of the tree or graph and investigate all nodes at the current depth level before moving on to nodes at the next depth level. BFS is a graph traversal approach in which we start at a source node and layer by layer through the graph, analyzing the nodes directly related to the source node. Then, in BFS traversal, we must move on to the next-level neighbor nodes.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introDescription = new Text(descriptionLabel.getText());
        introDescription.setFill(Paint.valueOf("black"));
        introDescription.setWrappingWidth(scrollPane.getPrefWidth());
        introDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("How Does the BFS Algorithm Work?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksTitle = new Text(descriptionLabel.getText());
        howItWorksTitle.setFill(Paint.valueOf("red"));
        howItWorksTitle.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("We use a queue data structure technique to store the vertices in Breadth-First Search. And the queue follows the First In First Out (FIFO) principle, which means that the neighbors of the node will be displayed, beginning with the node that was put first.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksDescription = new Text(descriptionLabel.getText());
        howItWorksDescription.setFill(Paint.valueOf("black"));
        howItWorksDescription.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Steps of BFS:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text visitNodeTitle = new Text(descriptionLabel.getText());
        visitNodeTitle.setFill(Paint.valueOf("red"));
        visitNodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        visitNodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• We start with the source node.\n"
                + "• We add that node at the front of the queue to the visited list.\n"
                + "• We make a list of the nodes as visited that are close to that vertex.\n"
                + "• And dequeue the nodes once they are visited.\n"
                + "• We repeat the actions until the queue is empty.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text visitNodeDescription = new Text(descriptionLabel.getText());
        visitNodeDescription.setFill(Paint.valueOf("black"));
        visitNodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        visitNodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Why Do We Need Breadth-First Search Algorithm?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForBFS = new Text(descriptionLabel.getText());
        needForBFS.setFill(Paint.valueOf("red"));
        needForBFS.setWrappingWidth(scrollPane.getPrefWidth());
        needForBFS.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("There are several reasons why we should use the BFS Algorithm to traverse graph data structure. The following are some of the essential features that make the BFS algorithm necessary:\n"
                + "• The BFS algorithm has a simple and reliable architecture.\n"
                + "• The BFS algorithm helps us evaluate nodes in a graph and determine the shortest path to traverse nodes.\n"
                + "• The BFS algorithm can traverse a graph in the fewest number of iterations possible.\n"
                + "• The iterations in the BFS algorithm are smooth, and there is no way for this method to get stuck in an infinite loop.\n"
                + "• In comparison to other algorithms, the BFS algorithm's result has a high level of accuracy.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForBFSDescription = new Text(descriptionLabel.getText());
        needForBFSDescription.setFill(Paint.valueOf("black"));
        needForBFSDescription.setWrappingWidth(scrollPane.getPrefWidth());
        needForBFSDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Pseudocode Of Breadth-First Search Algorithm\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeTitle = new Text(descriptionLabel.getText());
        pseudocodeTitle.setFill(Paint.valueOf("red"));
        pseudocodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Bredth_First_Serach( G, A ) // G ie the graph and A is the source node\n"
                + "          Let q be the queue \n"
                + "          q.enqueue( A ) // Inserting source node A to the queue\n"
                + "          Mark A node as visited.\n"
                + "          While ( q is not empty )\n"
                + "          B = q.dequeue( ) // Removing that vertex from the queue, which will be visited by its neighbour\n"
                + "Processing all the neighbors of B\n"
                + "For all neighbors of C of B\n"
                + "             If C is not visited, q. enqueue( C ) //Stores C in q to visit its neighbour\n"
                + "Mark C a visited\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeDescription = new Text(descriptionLabel.getText());
        pseudocodeDescription.setFill(Paint.valueOf("black"));
        pseudocodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Application Of Breadth-First Search Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationTitle = new Text(descriptionLabel.getText());
        applicationTitle.setFill(Paint.valueOf("red"));
        applicationTitle.setWrappingWidth(scrollPane.getPrefWidth());
        applicationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• Shortest path and minimum spanning tree identification can be done using BFS.\n"
                + "• To find all nearby locations using GPS, we utilize the BFS.\n"
                + "• A broadcast packet in a network uses BFS to reach all nodes.\n"
                + "• To see if there is a path between two vertices, we can BFS.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationDescription = new Text(descriptionLabel.getText());
        applicationDescription.setFill(Paint.valueOf("black"));
        applicationDescription.setWrappingWidth(scrollPane.getPrefWidth());
        applicationDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Complexity Of Breadth-First Search Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityTitle = new Text(descriptionLabel.getText());
        complexityTitle.setFill(Paint.valueOf("red"));
        complexityTitle.setWrappingWidth(scrollPane.getPrefWidth());
        complexityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The time complexity of the DFS algorithm is represented in the form of O(V+E), where V is the number of nodes and E is the number of edges.\n" +
                "The space complexity of the algorithm is O(V).\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityDescription = new Text(descriptionLabel.getText());
        complexityDescription.setFill(Paint.valueOf("black"));
        complexityDescription.setWrappingWidth(scrollPane.getPrefWidth());
        complexityDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Useful Links:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text usefulLinks = new Text(descriptionLabel.getText());
        usefulLinks.setFill(Paint.valueOf("red"));
        usefulLinks.setWrappingWidth(scrollPane.getPrefWidth());
        usefulLinks.setFont(Font.font("Arial", FontWeight.BOLD, 20));


        Hyperlink wikipediaLink = new Hyperlink("Wikipedia - Breadth-First Search\n");
        wikipediaLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Breadth-first_search"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink geeksforGeeksLink = new Hyperlink("GeeksforGeeks - Breadth-First Search or BFS for a Graph\n");
        geeksforGeeksLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink tutorialspointLink = new Hyperlink("Tutorialspoint - Breadth-First Search (BFS)\n");
        tutorialspointLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tutorialspoint.com/data_structures_algorithms/breadth_first_traversal.htm"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        textArea.getChildren().addAll(introTitle,introDescription,howItWorksTitle,howItWorksDescription,visitNodeTitle,visitNodeDescription,
                needForBFS,needForBFSDescription,pseudocodeTitle,pseudocodeDescription,applicationTitle,applicationDescription,complexityTitle,
                complexityDescription, usefulLinks,wikipediaLink, geeksforGeeksLink, tutorialspointLink);

        //</editor-fold>

        updateTextFlowSize();

    }

    @FXML
    public void DFSHandle(ActionEvent event){
        System.out.println("DFS explanation");
        title.setText("DFS");
        textArea.getChildren().clear();

        //<editor-fold defaultstate="collapsed" desc="Setting Up Text">
        Label introTitle = new Label("\nIntroduction:\n\n");
        introTitle.setWrapText(true);
        introTitle.setMaxHeight(Double.MAX_VALUE);
        introTitle.setTextAlignment(TextAlignment.JUSTIFY);
        Text introTitleText = new Text(introTitle.getText());
        introTitleText.setFill(Paint.valueOf("red"));
        introTitleText.setWrappingWidth(scrollPane.getPrefWidth());
        introTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label introDescription = new Label("Depth first Search or Depth first traversal is a recursive algorithm for searching all the vertices of a graph or tree data structure. Traversal means visiting all the nodes of a graph. According to the DFS, we can traverse the graph in a recursive method:\n\n");
        introDescription.setWrapText(true);
        introDescription.setMaxHeight(Double.MAX_VALUE);
        introDescription.setTextAlignment(TextAlignment.JUSTIFY);
        Text introDescriptionText = new Text(introDescription.getText());
        introDescriptionText.setFill(Paint.valueOf("black"));
        introDescriptionText.setWrappingWidth(scrollPane.getPrefWidth());
        introDescriptionText.setFont(Font.font("Arial", 16));

        Label howItWorksTitle = new Label("How Does the DFS Algorithm Work?\n\n");
        howItWorksTitle.setWrapText(true);
        howItWorksTitle.setMaxHeight(Double.MAX_VALUE);
        howItWorksTitle.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksTitleText = new Text(howItWorksTitle.getText());
        howItWorksTitleText.setFill(Paint.valueOf("red"));
        howItWorksTitleText.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label howItWorksDescription = new Label("Depth First Traversal (or DFS) for a graph is similar to Depth First Traversal of a tree. The only catch here is, that, unlike trees, graphs may contain cycles (a node may be visited twice). To avoid processing a node more than once, we use a boolean visited array. A graph can have more than one DFS traversal.\n"
                + "Depth-first search is an algorithm for traversing or searching tree or graph data structures. The algorithm starts at the root node (selecting some arbitrary node as the root node in the case of a graph) and explores as far as possible along each branch before backtracking.\n\n");
        howItWorksDescription.setWrapText(true);
        howItWorksDescription.setMaxHeight(Double.MAX_VALUE);
        howItWorksDescription.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksDescriptionText = new Text(howItWorksDescription.getText());
        howItWorksDescriptionText.setFill(Paint.valueOf("black"));
        howItWorksDescriptionText.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksDescriptionText.setFont(Font.font("Arial", 16));

        Label stepsTitle = new Label("Steps of DFS:\n\n");
        stepsTitle.setWrapText(true);
        stepsTitle.setMaxHeight(Double.MAX_VALUE);
        stepsTitle.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsTitleText = new Text(stepsTitle.getText());
        stepsTitleText.setFill(Paint.valueOf("red"));
        stepsTitleText.setWrappingWidth(scrollPane.getPrefWidth());
        stepsTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label stepsDescription = new Label("• Initially stack and visited arrays are empty.\n"
                + "• We start with the source node and put that node at the front of the queue to the visited list.\n"
                + "• We pop it from the stack and put its adjacent nodes which are not visited in stack.\n"
                + "• We mark these newly inserted nodes as visited.\n"
                + "• Stack becomes empty, which means we have visited all the nodes and our DFS traversal ends.\n\n");
        stepsDescription.setWrapText(true);
        stepsDescription.setMaxHeight(Double.MAX_VALUE);
        stepsDescription.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsDescriptionText = new Text(stepsDescription.getText());
        stepsDescriptionText.setFill(Paint.valueOf("black"));
        stepsDescriptionText.setWrappingWidth(scrollPane.getPrefWidth());
        stepsDescriptionText.setFont(Font.font("Arial", 16));

        Label needForBFSTitle = new Label("Why Do We Need Breadth-First Search Algorithm?\n\n");
        needForBFSTitle.setWrapText(true);
        needForBFSTitle.setMaxHeight(Double.MAX_VALUE);
        needForBFSTitle.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForBFSTitleText = new Text(needForBFSTitle.getText());
        needForBFSTitleText.setFill(Paint.valueOf("red"));
        needForBFSTitleText.setWrappingWidth(scrollPane.getPrefWidth());
        needForBFSTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label needForBFSDescription = new Label("There are several reasons why we should use the DFS Algorithm to traverse graph data structure. The following are some of the essential features that make the BFS algorithm necessary:\n"
                + "• The DFS algorithm has a simple and reliable architecture.\n"
                + "• The DFS algorithm helps evaluate nodes in a graph and determines the path to traverse nodes.\n"
                + "• The DFS algorithm can detect if there is any cycle in a graph.\n"
                + "• The iterations in the DFS algorithm are smooth, and there is no way for this method to get stuck in an infinite loop.\n"
                + "• In comparison to other algorithms, the DFS algorithm's result has a high level of accuracy.\n\n");
        needForBFSDescription.setWrapText(true);
        needForBFSDescription.setMaxHeight(Double.MAX_VALUE);
        needForBFSDescription.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForBFSDescriptionText = new Text(needForBFSDescription.getText());
        needForBFSDescriptionText.setFill(Paint.valueOf("black"));
        needForBFSDescriptionText.setWrappingWidth(scrollPane.getPrefWidth());
        needForBFSDescriptionText.setFont(Font.font("Arial", 16));

        Label pseudocodeTitle = new Label("Pseudocode Of Breadth-First Search Algorithm\n\n");
        pseudocodeTitle.setWrapText(true);
        pseudocodeTitle.setMaxHeight(Double.MAX_VALUE);
        pseudocodeTitle.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeTitleText = new Text(pseudocodeTitle.getText());
        pseudocodeTitleText.setFill(Paint.valueOf("red"));
        pseudocodeTitleText.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label pseudocodeDescription = new Label("Depth_First_Search(matrix[ ][ ] ,source_node, visited, value)\n"
                + "{\n"
                + " If ( sourcce_node == value)\n"
                + " return true // we found the value\n"
                + " visited[source_node] = True\n"
                + " for node in matrix[source_node]:\n"
                + " If visited [ node ] == false\n"
                + " Depth_first_search ( matrix, node, visited)\n"
                + " end if\n"
                + " end for\n"
                + " return false //If it gets to this point, it means that all nodes have been explored.\n"
                + "                    //And we haven't located the value yet.\n"
                + "}\n\n");
        pseudocodeDescription.setWrapText(true);
        pseudocodeDescription.setMaxHeight(Double.MAX_VALUE);
        pseudocodeDescription.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeDescriptionText = new Text(pseudocodeDescription.getText());
        pseudocodeDescriptionText.setFill(Paint.valueOf("black"));
        pseudocodeDescriptionText.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeDescriptionText.setFont(Font.font("Arial", 16));

        Label applicationTitle = new Label("Application Of Breadth-First Search Algorithm:\n\n");
        applicationTitle.setWrapText(true);
        applicationTitle.setMaxHeight(Double.MAX_VALUE);
        applicationTitle.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationTitleText = new Text(applicationTitle.getText());
        applicationTitleText.setFill(Paint.valueOf("red"));
        applicationTitleText.setWrappingWidth(scrollPane.getPrefWidth());
        applicationTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label applicationDescription = new Label("• Detecting a graph's cycle.\n"
                + "• Topological Sorting.\n"
                + "• To determine if a graph is bipartite\n"
                + "• The DFS algorithm can be customized to discover a path between two specified vertices, a and b..\n\n");
        applicationDescription.setWrapText(true);
        applicationDescription.setMaxHeight(Double.MAX_VALUE);
        applicationDescription.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationDescriptionText = new Text(applicationDescription.getText());
        applicationDescriptionText.setFill(Paint.valueOf("black"));
        applicationDescriptionText.setWrappingWidth(scrollPane.getPrefWidth());
        applicationDescriptionText.setFont(Font.font("Arial", 16));

        Label complexityTitle = new Label("Complexity of Depth-First Search Algorithm:\n\n");
        complexityTitle.setWrapText(true);
        complexityTitle.setMaxHeight(Double.MAX_VALUE);
        complexityTitle.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityTitleText = new Text(complexityTitle.getText());
        complexityTitleText.setFill(Paint.valueOf("red"));
        complexityTitleText.setWrappingWidth(scrollPane.getPrefWidth());
        complexityTitleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Label complexityDescription = new Label("The time complexity of the DFS algorithm is represented in the form of O(V+E), where V is the number of nodes and E is the number of edges.\n"
                + "The space complexity of the algorithm is O(V)\n\n");
        complexityDescription.setWrapText(true);
        complexityDescription.setMaxHeight(Double.MAX_VALUE);
        complexityDescription.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityDescriptionText = new Text(complexityDescription.getText());
        complexityDescriptionText.setFill(Paint.valueOf("black"));
        complexityDescriptionText.setWrappingWidth(scrollPane.getPrefWidth());
        complexityDescriptionText.setFont(Font.font("Arial", 16));

        Label usefulLinks = new Label("Useful Links:\n\n");
        usefulLinks.setWrapText(true);
        usefulLinks.setMaxHeight(Double.MAX_VALUE);
        usefulLinks.setTextAlignment(TextAlignment.JUSTIFY);
        Text usefulLinksText = new Text(usefulLinks.getText());
        usefulLinksText.setFill(Paint.valueOf("red"));
        usefulLinksText.setWrappingWidth(scrollPane.getPrefWidth());
        usefulLinksText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Hyperlink wikipediaLink = new Hyperlink("Wikipedia - Depth-First Search\n");
        wikipediaLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Depth-first_search"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink geeksforGeeksLink = new Hyperlink("GeeksforGeeks - Depth-First Search or DFS in a Graph\n");
        geeksforGeeksLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink tutorialspointLink = new Hyperlink("Tutorialspoint - Depth-First Search (DFS)\n");
        tutorialspointLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tutorialspoint.com/data_structures_algorithms/depth_first_traversal.htm"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        textArea.getChildren().addAll(introTitleText, introDescriptionText, howItWorksTitleText, howItWorksDescriptionText,
                stepsTitleText, stepsDescriptionText, needForBFSTitleText, needForBFSDescriptionText, pseudocodeTitleText,
                pseudocodeDescriptionText, applicationTitleText, applicationDescriptionText, complexityTitleText,
                complexityDescriptionText, usefulLinksText, wikipediaLink, geeksforGeeksLink, tutorialspointLink);

    //</editor-fold>

        updateTextFlowSize();

    }
    @FXML
    public void DijkstraHandle(ActionEvent event){
        System.out.println("Dijkstra explanation");
        title.setText("Dijkstra");
        textArea.getChildren().clear();

        //<editor-fold defaultstate="collapsed" desc="Setting Up Text">

        Label descriptionLabel = new Label("\nIntroduction:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introTitle = new Text(descriptionLabel.getText());
        introTitle.setFill(Paint.valueOf("red"));
        introTitle.setWrappingWidth(scrollPane.getPrefWidth());
        introTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Dijkstra's Algorithm is a Graph algorithm that finds the shortest path from a source vertex to all other vertices in the Graph (single source shortest path)." +
                " It is a type of Greedy Algorithm that only works on Weighted Graphs having positive weights. \n\n" +
                "Dijkstra’s algorithm is used to find the shortest path between the two mentioned vertices of a graph and " +
                "It differs from the minimum spanning tree because the shortest distance between two vertices might not include all the vertices of the graph. " +
                "It has many applications.For Example: It is used to find the shortest between the destination to visit from our current location on a Google map.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introDescription = new Text(descriptionLabel.getText());
        introDescription.setFill(Paint.valueOf("black"));
        introDescription.setWrappingWidth(scrollPane.getPrefWidth());
        introDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("How Does the Dijkstra Algorithm Work?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksTitle = new Text(descriptionLabel.getText());
        howItWorksTitle.setFill(Paint.valueOf("red"));
        howItWorksTitle.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Dijkstra's Algorithm works on the basis that any subpath B->D of the shortest path A->D between vertices A and D is also the shortest path between vertices B and D. Djikstra used this property in the opposite direction i.e we overestimate the distance of each vertex from the starting vertex. Then we visit each node and its neighbors to find the shortest subpath to those neighbors.\n\nThe algorithm uses a greedy approach in the sense that we find the next best solution hoping that the end result is the best solution for the whole problem.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksDescription = new Text(descriptionLabel.getText());
        howItWorksDescription.setFill(Paint.valueOf("black"));
        howItWorksDescription.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Steps of Dijkstra:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text visitNodeTitle = new Text(descriptionLabel.getText());
        visitNodeTitle.setFill(Paint.valueOf("red"));
        visitNodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        visitNodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• Dijkstra's Algorithm begins at the node we select (the source node), and it examines the graph to find the shortest path between that node and all the other nodes in the graph.\n\n• The Algorithm keeps records of the presently acknowledged shortest distance from each node to the source node, and it updates these values if it finds any shorter path.\n\n• Once the Algorithm has retrieved the shortest path between the source and another node, that node is marked as 'visited' and included in the path.\n\n• The procedure continues until all the nodes in the graph have been included in the path. In this manner, we have a path connecting the source node to all other nodes, following the shortest possible path to reach each node.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text visitNodeDescription = new Text(descriptionLabel.getText());
        visitNodeDescription.setFill(Paint.valueOf("black"));
        visitNodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        visitNodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Why Do We Need Dijkstra Algorithm?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForDijkstra = new Text(descriptionLabel.getText());
        needForDijkstra.setFill(Paint.valueOf("red"));
        needForDijkstra.setWrappingWidth(scrollPane.getPrefWidth());
        needForDijkstra.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("There are several reasons why you should use the DFS Algorithm to traverse graph data structure. The following are some of the essential features that make the BFS algorithm necessary:\n"
                + "• The DFS algorithm has a simple and reliable architecture.\n"
                + "• The DFS algorithm helps evaluate nodes in a graph and determines the path to traverse nodes.\n"
                + "• The DFS algorithm can detect if there is any cycle in a graph.\n"
                + "• The iterations in the DFS algorithm are smooth, and there is no way for this method to get stuck in an infinite loop.\n"
                + "• In comparison to other algorithms, the DFS algorithm's result has a high level of accuracy.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForDijkstraDescription = new Text(descriptionLabel.getText());
        needForDijkstraDescription.setFill(Paint.valueOf("black"));
        needForDijkstraDescription.setWrappingWidth(scrollPane.getPrefWidth());
        needForDijkstraDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Pseudocode Of Dijkstra Algorithm\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeTitle = new Text(descriptionLabel.getText());
        pseudocodeTitle.setFill(Paint.valueOf("red"));
        pseudocodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("function dijkstra(G, S)\n"
                + "    for each vertex V in G\n"
                + "        distance[V] <- infinite\n"
                + "        previous[V] <- NULL\n"
                + "        If V != S, add V to Priority Queue Q\n"
                + "    distance[S] <- 0\n\n"
                + "    while Q IS NOT EMPTY\n"
                + "        U <- Extract MIN from Q\n"
                + "        for each unvisited neighbour V of U\n"
                + "            tempDistance <- distance[U] + edge_weight(U, V)\n"
                + "            if tempDistance < distance[V]\n"
                + "                distance[V] <- tempDistance\n"
                + "                previous[V] <- U\n"
                + "    return distance[], previous[]\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeDescription = new Text(descriptionLabel.getText());
        pseudocodeDescription.setFill(Paint.valueOf("black"));
        pseudocodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Application Of Dijkstra Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationTitle = new Text(descriptionLabel.getText());
        applicationTitle.setFill(Paint.valueOf("red"));
        applicationTitle.setWrappingWidth(scrollPane.getPrefWidth());
        applicationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• To find the shortest path\n"
                + "• In social networking applications\n"
                + "• In a telephone network\n"
                + "• To find the locations on the map\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationDescription = new Text(descriptionLabel.getText());
        applicationDescription.setFill(Paint.valueOf("black"));
        applicationDescription.setWrappingWidth(scrollPane.getPrefWidth());
        applicationDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Complexity of Dijkstra Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityTitle = new Text(descriptionLabel.getText());
        complexityTitle.setFill(Paint.valueOf("red"));
        complexityTitle.setWrappingWidth(scrollPane.getPrefWidth());
        complexityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The time complexity of the DFS algorithm is represented in the form of O(E*logV), where V is the number of nodes and E is the number of edges.\n"
                + "The space complexity of the algorithm is O(V)\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityDescription = new Text(descriptionLabel.getText());
        complexityDescription.setFill(Paint.valueOf("black"));
        complexityDescription.setWrappingWidth(scrollPane.getPrefWidth());
        complexityDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Useful Links:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text usefulLinks = new Text(descriptionLabel.getText());
        usefulLinks.setFill(Paint.valueOf("red"));
        usefulLinks.setWrappingWidth(scrollPane.getPrefWidth());
        usefulLinks.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Hyperlink wikipediaLink = new Hyperlink("Wikipedia - Dijkstra's Algorithm\n");
        wikipediaLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink geeksforGeeksLink = new Hyperlink("GeeksforGeeks - Dijkstra's Algorithm\n");
        geeksforGeeksLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink tutorialspointLink = new Hyperlink("Tutorialspoint - Dijkstra's Algorithm\n");
        tutorialspointLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tutorialspoint.com/data_structures_algorithms/dijkstras_shortest_path_algorithm.htm"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        textArea.getChildren().addAll(introTitle, introDescription, howItWorksTitle, howItWorksDescription, visitNodeTitle, visitNodeDescription,
                needForDijkstra, needForDijkstraDescription, pseudocodeTitle, pseudocodeDescription, applicationTitle, applicationDescription, complexityTitle,
                complexityDescription, usefulLinks,wikipediaLink, geeksforGeeksLink, tutorialspointLink);

        //</editor-fold>

        updateTextFlowSize();

    }
    @FXML
    public void FloydWarshallHandle(ActionEvent event){
        System.out.println("Floyd-Warshall explanation");
        title.setText("Floyd-Warshall");
        textArea.getChildren().clear();

        //<editor-fold defaultstate="collapsed" desc="Setting Up Text">
        Label descriptionLabel = new Label("\nIntroduction:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introTitle = new Text(descriptionLabel.getText());
        introTitle.setFill(Paint.valueOf("red"));
        introTitle.setWrappingWidth(scrollPane.getPrefWidth());
        introTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The Floyd-Warshall algorithm is a dynamic programming algorithm that finds the shortest paths between all pairs of vertices in a weighted graph. It works for both directed and undirected graphs and also for graphs with negative edge weights (as long as there are no negative cycles).\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introDescription = new Text(descriptionLabel.getText());
        introDescription.setFill(Paint.valueOf("black"));
        introDescription.setWrappingWidth(scrollPane.getPrefWidth());
        introDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("How Does the Floyd-Warshall Algorithm Work?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksTitle = new Text(descriptionLabel.getText());
        howItWorksTitle.setFill(Paint.valueOf("red"));
        howItWorksTitle.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The Floyd-Warshall algorithm uses a dynamic programming approach to find the shortest paths between all pairs of vertices in a graph. It maintains a matrix of distances between all pairs of vertices, gradually updating it until it contains the shortest distances. The algorithm iterates through all vertices and considers each vertex as an intermediate vertex in the shortest path between every pair of vertices. It compares the distances through the intermediate vertex with the current distances and updates them if a shorter path is found.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksDescription = new Text(descriptionLabel.getText());
        howItWorksDescription.setFill(Paint.valueOf("black"));
        howItWorksDescription.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Steps of Floyd-Warshall:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsTitle = new Text(descriptionLabel.getText());
        stepsTitle.setFill(Paint.valueOf("red"));
        stepsTitle.setWrappingWidth(scrollPane.getPrefWidth());
        stepsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• We initialize the distance matrix with the weights of the edges in the graph.\n"
                + "• We iterate through all vertices and consider each vertex as an intermediate vertex.\n"
                + "• For each pair of vertices (u, v), we check if the path through the intermediate vertex k is shorter than the current path. If it is, update the distance matrix.\n"
                + "• We repeat step 2 until all vertices have been considered as intermediate vertices.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsDescription = new Text(descriptionLabel.getText());
        stepsDescription.setFill(Paint.valueOf("black"));
        stepsDescription.setWrappingWidth(scrollPane.getPrefWidth());
        stepsDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Why Do We Need Floyd-Warshall Algorithm?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForFloydWarshall = new Text(descriptionLabel.getText());
        needForFloydWarshall.setFill(Paint.valueOf("red"));
        needForFloydWarshall.setWrappingWidth(scrollPane.getPrefWidth());
        needForFloydWarshall.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The Floyd-Warshall algorithm is useful in scenarios where we need to find the shortest paths between all pairs of vertices in a graph. Some reasons why we might need the Floyd-Warshall algorithm include:\n"
                + "• We need to find the shortest paths between all pairs of vertices in a graph.\n"
                + "• We have a graph with negative edge weights, and we want to find the shortest paths despite the negative weights.\n"
                + "• We want a simple and efficient algorithm for finding all pairs shortest paths.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForFloydWarshallDescription = new Text(descriptionLabel.getText());
        needForFloydWarshallDescription.setFill(Paint.valueOf("black"));
        needForFloydWarshallDescription.setWrappingWidth(scrollPane.getPrefWidth());
        needForFloydWarshallDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Pseudocode Of Floyd-Warshall Algorithm\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeTitle = new Text(descriptionLabel.getText());
        pseudocodeTitle.setFill(Paint.valueOf("red"));
        pseudocodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("function FloydWarshall (graph[][], int V)\n"
                + "    // Initialize the distance matrix with the weights of the edges\n"
                + "    let dist[][] be a matrix of size VxV\n"
                + "    for each i, j where i, j < V\n"
                + "        dist[i][j] = graph[i][j]\n"
                + "    // Add vertices individually and check if the path is shorter\n"
                + "    for each k where k < V\n"
                + "        for each i where i < V\n"
                + "            for each j where j < V\n"
                + "                if (dist[i][k] + dist[k][j] < dist[i][j])\n"
                + "                    dist[i][j] = dist[i][k] + dist[k][j]\n"
                + "    return dist[][]\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeDescription = new Text(descriptionLabel.getText());
        pseudocodeDescription.setFill(Paint.valueOf("black"));
        pseudocodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Application Of Floyd-Warshall Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationTitle = new Text(descriptionLabel.getText());
        applicationTitle.setFill(Paint.valueOf("red"));
        applicationTitle.setWrappingWidth(scrollPane.getPrefWidth());
        applicationTitle.setFont(Font.font("Arial",FontWeight.BOLD, 20));

        descriptionLabel = new Label("• The Floyd-Warshall algorithm is commonly used in network routing protocols to find the shortest paths between routers.\n"
                + "• It is used in geographical information systems (GIS) for finding the shortest paths between locations on a map.\n"
                + "• In traffic planning systems, the Floyd-Warshall algorithm helps to optimize traffic flow by finding the shortest routes between locations.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationDescription = new Text(descriptionLabel.getText());
        applicationDescription.setFill(Paint.valueOf("black"));
        applicationDescription.setWrappingWidth(scrollPane.getPrefWidth());
        applicationDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Complexity Of Floyd-Warshall Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityTitle = new Text(descriptionLabel.getText());
        complexityTitle.setFill(Paint.valueOf("red"));
        complexityTitle.setWrappingWidth(scrollPane.getPrefWidth());
        complexityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The time complexity of the Floyd-Warshall algorithm is O(V^3), where V is the number of vertices in the graph.\n"
                + "The space complexity of the algorithm is O(V^2), as it requires storing the distances between all pairs of vertices in a matrix.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityDescription = new Text(descriptionLabel.getText());
        complexityDescription.setFill(Paint.valueOf("black"));
        complexityDescription.setWrappingWidth(scrollPane.getPrefWidth());
        complexityDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Useful Links:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text usefulLinks = new Text(descriptionLabel.getText());
        usefulLinks.setFill(Paint.valueOf("red"));
        usefulLinks.setWrappingWidth(scrollPane.getPrefWidth());
        usefulLinks.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Hyperlink wikipediaLink = new Hyperlink("Wikipedia - Floyd-Warshall Algorithm");
        wikipediaLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink geeksforGeeksLink = new Hyperlink("GeeksforGeeks - Floyd-Warshall Algorithm");
        geeksforGeeksLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink tutorialspointLink = new Hyperlink("Tutorialspoint - Floyd-Warshall Algorithm");
        tutorialspointLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tutorialspoint.com/data_structures_algorithms/floyd_warshall_algorithm.htm"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        textArea.getChildren().addAll(introTitle, introDescription, howItWorksTitle, howItWorksDescription, stepsTitle, stepsDescription,
                needForFloydWarshall, needForFloydWarshallDescription, pseudocodeTitle, pseudocodeDescription, applicationTitle,
                applicationDescription, complexityTitle, complexityDescription, usefulLinks, wikipediaLink,geeksforGeeksLink, tutorialspointLink);

        //</editor-fold>

        updateTextFlowSize();

    }
    @FXML
    public void BellmanHandle(ActionEvent event){
        System.out.println("Bellman-Ford explanation");
        title.setText("Bellman-Ford");
        textArea.getChildren().clear();

    //<editor-fold defaultstate="collapsed" desc="Setting Up Text">
        Label descriptionLabel = new Label("\nIntroduction:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introTitle = new Text(descriptionLabel.getText());
        introTitle.setFill(Paint.valueOf("red"));
        introTitle.setWrappingWidth(scrollPane.getPrefWidth());
        introTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The Bellman-Ford algorithm is a single-source shortest path algorithm that works on graphs with negative edge weights, unlike Dijkstra's algorithm, which requires all edge weights to be non-negative. It is used to find the shortest paths from a single source vertex to all other vertices in the graph.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introDescription = new Text(descriptionLabel.getText());
        introDescription.setFill(Paint.valueOf("black"));
        introDescription.setWrappingWidth(scrollPane.getPrefWidth());
        introDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("How Does the Bellman-Ford Algorithm Work?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksTitle = new Text(descriptionLabel.getText());
        howItWorksTitle.setFill(Paint.valueOf("red"));
        howItWorksTitle.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The Bellman-Ford algorithm iterates over all edges multiple times, relaxing them each time to find the shortest path. It repeats this process until no further improvements can be made. This algorithm can handle graphs with negative edge weights but detects negative cycles, preventing infinite loops.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksDescription = new Text(descriptionLabel.getText());
        howItWorksDescription.setFill(Paint.valueOf("black"));
        howItWorksDescription.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Steps of Bellman-Ford:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsTitle = new Text(descriptionLabel.getText());
        stepsTitle.setFill(Paint.valueOf("red"));
        stepsTitle.setWrappingWidth(scrollPane.getPrefWidth());
        stepsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• We initialize distances from source to all vertices as INFINITE and distance to the source itself as 0.\n"
                + "• We iterate over all edges V-1 times and relax each edge.\n"
                + "• After V-1 iterations, we iterate over all edges one more time.\n"
                + "• If there is any relaxation in this iteration, there is presence of negative cycles.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsDescription = new Text(descriptionLabel.getText());
        stepsDescription.setFill(Paint.valueOf("black"));
        stepsDescription.setWrappingWidth(scrollPane.getPrefWidth());
        stepsDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Why Do We Need Bellman-Ford Algorithm?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForBellmanFord = new Text(descriptionLabel.getText());
        needForBellmanFord.setFill(Paint.valueOf("red"));
        needForBellmanFord.setWrappingWidth(scrollPane.getPrefWidth());
        needForBellmanFord.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• The Bellman-Ford algorithm can handle graphs with negative edge weights and detect negative cycles.\n"
                + "• It is useful in network routing protocols where negative edge weights may occur.\n"
                + "• This algorithm is also employed in detecting negative cycles, which can lead to financial losses or incorrect computations.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForBellmanFordDescription = new Text(descriptionLabel.getText());
        needForBellmanFordDescription.setFill(Paint.valueOf("black"));
        needForBellmanFordDescription.setWrappingWidth(scrollPane.getPrefWidth());
        needForBellmanFordDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Pseudocode Of Bellman-Ford Algorithm\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeTitle = new Text(descriptionLabel.getText());
        pseudocodeTitle.setFill(Paint.valueOf("red"));
        pseudocodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("BellmanFord(graph, source)\n"
                + "    Initialize distances from source to all vertices as INFINITE and distance to the source itself as 0.\n"
                + "    Iterate over all edges |V| - 1 times and relax each edge.\n"
                + "    After |V| - 1 iterations, iterate over all edges one more time to detect negative cycles.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeDescription = new Text(descriptionLabel.getText());
        pseudocodeDescription.setFill(Paint.valueOf("black"));
        pseudocodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Application Of Bellman-Ford Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationTitle = new Text(descriptionLabel.getText());
        applicationTitle.setFill(Paint.valueOf("red"));
        applicationTitle.setWrappingWidth(scrollPane.getPrefWidth());
        applicationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• Network Routing Protocols\n"
                + "• Financial Trading Systems\n"
                + "• Pathfinding in Video Games\n"
                + "• Detecting Negative Cycles\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationDescription = new Text(descriptionLabel.getText());
        applicationDescription.setFill(Paint.valueOf("black"));
        applicationDescription.setWrappingWidth(scrollPane.getPrefWidth());
        applicationDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Complexity Of Bellman-Ford Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityTitle = new Text(descriptionLabel.getText());
        complexityTitle.setFill(Paint.valueOf("red"));
        complexityTitle.setWrappingWidth(scrollPane.getPrefWidth());
        complexityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel =new Label("The time complexity of the Bellman-Ford algorithm is O(V * E), where V is the number of vertices and E is the number of edges in the graph. The space complexity is O(V).\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityDescription = new Text(descriptionLabel.getText());
        complexityDescription.setFill(Paint.valueOf("black"));
        complexityDescription.setWrappingWidth(scrollPane.getPrefWidth());
        complexityDescription.setFont(Font.font("Arial", 16));

        Label usefulLinks = new Label("Useful Links:\n\n");
        usefulLinks.setWrapText(true);
        usefulLinks.setMaxHeight(Double.MAX_VALUE);
        usefulLinks.setTextAlignment(TextAlignment.JUSTIFY);
        Text usefulLinksTitle = new Text(usefulLinks.getText());
        usefulLinksTitle.setFill(Paint.valueOf("red"));
        usefulLinksTitle.setWrappingWidth(scrollPane.getPrefWidth());
        usefulLinksTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Hyperlink wikipediaLink = new Hyperlink("Wikipedia - Bellman-Ford Algorithm");
        wikipediaLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink geeksforGeeksLink = new Hyperlink("GeeksforGeeks - Bellman-Ford Algorithm");
        geeksforGeeksLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink tutorialspointLink = new Hyperlink("Tutorialspoint - Bellman-Ford Algorithm");
        tutorialspointLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tutorialspoint.com/Bellman-Ford-Algorithm-for-Shortest-Paths#:~:text=Bellman%2DFord%20algorithm%20is%20used,we%20can%20handle%20it%20easily."));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        textArea.getChildren().addAll(introTitle, introDescription, howItWorksTitle, howItWorksDescription, stepsTitle, stepsDescription,
                needForBellmanFord, needForBellmanFordDescription, pseudocodeTitle, pseudocodeDescription, applicationTitle, applicationDescription,
                complexityTitle, complexityDescription, usefulLinksTitle, wikipediaLink, geeksforGeeksLink, tutorialspointLink);

        //</editor-fold>

        updateTextFlowSize();

    }
    @FXML
    public void KruskalHandle(ActionEvent event){
        System.out.println("Kruskal explanation");
        title.setText("Kruskal");
        textArea.getChildren().clear();
        //<editor-fold defaultstate="collapsed" desc="Setting Up Text">

        Label descriptionLabel = new Label("\nIntroduction:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introTitle = new Text(descriptionLabel.getText());
        introTitle.setFill(Paint.valueOf("red"));
        introTitle.setWrappingWidth(scrollPane.getPrefWidth());
        introTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Kruskal's Algorithm is a greedy algorithm used to find the minimum spanning tree for a connected, weighted graph. "
                + "It begins with a forest of singleton trees and repeatedly merges the two smallest trees until a single tree containing all vertices is obtained. "
                + "The algorithm sorts the edges of the graph in non-decreasing order of their weights and selects edges that do not form a cycle until all vertices are connected. "
                + "This algorithm is used in various applications such as network design, circuit design, and clustering.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introDescription = new Text(descriptionLabel.getText());
        introDescription.setFill(Paint.valueOf("black"));
        introDescription.setWrappingWidth(scrollPane.getPrefWidth());
        introDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("How Does Kruskal's Algorithm Work?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksTitle = new Text(descriptionLabel.getText());
        howItWorksTitle.setFill(Paint.valueOf("red"));
        howItWorksTitle.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Kruskal's Algorithm works by considering the edges of the graph in non-decreasing order of their weights. "
                + "It selects the smallest edge that does not form a cycle in the current forest of trees and adds it to the minimum spanning tree. "
                + "The algorithm continues this process until all vertices are connected or all edges have been considered.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksDescription = new Text(descriptionLabel.getText());
        howItWorksDescription.setFill(Paint.valueOf("black"));
        howItWorksDescription.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Steps of Kruskal's Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsTitle = new Text(descriptionLabel.getText());
        stepsTitle.setFill(Paint.valueOf("red"));
        stepsTitle.setWrappingWidth(scrollPane.getPrefWidth());
        stepsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• Sort all the edges in non-decreasing order of their weight.\n"
                + "• Initialize an empty minimum spanning tree.\n"
                + "• Iterate through all the edges and select the smallest edge that does not form a cycle with the edges already in the minimum spanning tree. "
                + "Add this edge to the minimum spanning tree.\n"
                + "• Repeat step 3 until all vertices are connected.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsDescription = new Text(descriptionLabel.getText());
        stepsDescription.setFill(Paint.valueOf("black"));
        stepsDescription.setWrappingWidth(scrollPane.getPrefWidth());
        stepsDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Why Do We Need Kruskal's Algorithm?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForKruskal = new Text(descriptionLabel.getText());
        needForKruskal.setFill(Paint.valueOf("red"));
        needForKruskal.setWrappingWidth(scrollPane.getPrefWidth());
        needForKruskal.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Kruskal's Algorithm is necessary for finding the minimum spanning tree of a connected, weighted graph. "
                + "It ensures that the resulting spanning tree has the lowest possible total weight, making it ideal for various applications such as network design, circuit design, and clustering.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForKruskalDescription = new Text(descriptionLabel.getText());
        needForKruskalDescription.setFill(Paint.valueOf("black"));
        needForKruskalDescription.setWrappingWidth(scrollPane.getPrefWidth());
        needForKruskalDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Pseudocode Of Kruskal's Algorithm\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeTitle = new Text(descriptionLabel.getText());
        pseudocodeTitle.setFill(Paint.valueOf("red"));
        pseudocodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("function Kruskal(Graph) is\n"
                + "    edges := sort(Graph.edges) // Sort edges by weight\n"
                + "    forest := MakeSet(Graph.vertices)\n"
                + "    MST := empty set\n"
                + "    for each edge in edges do\n"
                + "        if Find(edge.source) ≠ Find(edge.destination) then\n"
                + "            Add edge to MST\n"
                + "            Union(edge.source, edge.destination)\n"
                + "    return MST\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeDescription = new Text(descriptionLabel.getText());
        pseudocodeDescription.setFill(Paint.valueOf("black"));
        pseudocodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Application Of Kruskal's Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationTitle = new Text(descriptionLabel.getText());
        applicationTitle.setFill(Paint.valueOf("red"));
        applicationTitle.setWrappingWidth(scrollPane.getPrefWidth());
        applicationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• Solving circuits design and optimization problems\n"
                + "• In image segmentation to partition an image into regions with similar characteristics\n"
                + "• In bioinformatics, Kruskal's algorithm is used to construct phylogenetic trees\n"
                + "• In computer network (LAN connection)\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationDescription = new Text(descriptionLabel.getText());
        applicationDescription.setFill(Paint.valueOf("black"));
        applicationDescription.setWrappingWidth(scrollPane.getPrefWidth());
        applicationDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Complexity Of Kruskal's Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityTitle = new Text(descriptionLabel.getText());
        complexityTitle.setFill(Paint.valueOf("red"));
        complexityTitle.setWrappingWidth(scrollPane.getPrefWidth());
        complexityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("The time complexity of Kruskal's algorithm is O(E log V), where E is the number of edges and V is the number of vertices in the graph.\n"
                + "The space complexity of the algorithm is O(V + E), as it requires storing the edges and vertices of the graph.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityDescription = new Text(descriptionLabel.getText());
        complexityDescription.setFill(Paint.valueOf("black"));
        complexityDescription.setWrappingWidth(scrollPane.getPrefWidth());
        complexityDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Useful Links:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text usefulLinks = new Text(descriptionLabel.getText());
        usefulLinks.setFill(Paint.valueOf("red"));
        usefulLinks.setWrappingWidth(scrollPane.getPrefWidth());
        usefulLinks.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Hyperlink wikipediaLink = new Hyperlink("Wikipedia - Kruskal's Algorithm");
        wikipediaLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Kruskal%27s_algorithm"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink geeksforGeeksLink = new Hyperlink("GeeksforGeeks - Kruskal's Algorithm");
        geeksforGeeksLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink tutorialspointLink = new Hyperlink("Tutorialspoint - Kruskal's Algorithm");
        tutorialspointLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tutorialspoint.com/data_structures_algorithms/kruskals_spanning_tree_algorithm.htm"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        textArea.getChildren().addAll(introTitle, introDescription, howItWorksTitle, howItWorksDescription, stepsTitle, stepsDescription,
                needForKruskal, needForKruskalDescription, pseudocodeTitle, pseudocodeDescription, applicationTitle,
                applicationDescription, complexityTitle, complexityDescription, usefulLinks, wikipediaLink, geeksforGeeksLink, tutorialspointLink);

        //</editor-fold>

        updateTextFlowSize();

    }
    @FXML
    public void PrimsHandle(ActionEvent event){

        System.out.println("Prim's Algorithm explanation");
        title.setText("Prim's Algorithm");
        textArea.getChildren().clear();

        //<editor-fold defaultstate="collapsed" desc="Setting Up Text">

        Label descriptionLabel = new Label("\nIntroduction:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introTitle = new Text(descriptionLabel.getText());
        introTitle.setFill(Paint.valueOf("red"));
        introTitle.setWrappingWidth(scrollPane.getPrefWidth());
        introTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Prim's Algorithm is a greedy algorithm used to find the minimum spanning tree from a graph. " +
                "It starts with an arbitrary node and grows the spanning tree one vertex at a time, selecting the " +
                "cheapest edge that connects the spanning tree to a vertex not yet in the tree. The algorithm continues " +
                "until all vertices are included in the minimum spanning tree.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text introDescription = new Text(descriptionLabel.getText());
        introDescription.setFill(Paint.valueOf("black"));
        introDescription.setWrappingWidth(scrollPane.getPrefWidth());
        introDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("How Does Prim's Algorithm Work?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksTitle = new Text(descriptionLabel.getText());
        howItWorksTitle.setFill(Paint.valueOf("red"));
        howItWorksTitle.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Prim's Algorithm maintains a set of vertices that are part of the minimum spanning tree " +
                "and a set of vertices that are not yet included. It repeatedly selects the cheapest edge that connects " +
                "a vertex from the set of included vertices to a vertex not yet included. The process continues until all " +
                "vertices are included in the minimum spanning tree.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text howItWorksDescription = new Text(descriptionLabel.getText());
        howItWorksDescription.setFill(Paint.valueOf("black"));
        howItWorksDescription.setWrappingWidth(scrollPane.getPrefWidth());
        howItWorksDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Steps of Prim's Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsTitle = new Text(descriptionLabel.getText());
        stepsTitle.setFill(Paint.valueOf("red"));
        stepsTitle.setWrappingWidth(scrollPane.getPrefWidth());
        stepsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• Start with an arbitrary node and mark it as visited.\n" +
                "• While there are still vertices not included in the minimum spanning tree:\n" +
                "• Find the cheapest edge that connects a visited vertex to an unvisited vertex.\n" +
                "• Mark the unvisited vertex and the edge as part of the minimum spanning tree.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text stepsDescription = new Text(descriptionLabel.getText());
        stepsDescription.setFill(Paint.valueOf("black"));
        stepsDescription.setWrappingWidth(scrollPane.getPrefWidth());
        stepsDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Why Do We Need Prim's Algorithm?\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForPrim = new Text(descriptionLabel.getText());
        needForPrim.setFill(Paint.valueOf("red"));
        needForPrim.setWrappingWidth(scrollPane.getPrefWidth());
        needForPrim.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Prim's Algorithm is essential for finding the minimum spanning tree in a graph, " +
                "which has various applications in network design, circuit layout, and transportation planning. " +
                "It ensures that all vertices are connected with the minimum total edge weight, optimizing resource usage.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text needForPrimDescription = new Text(descriptionLabel.getText());
        needForPrimDescription.setFill(Paint.valueOf("black"));
        needForPrimDescription.setWrappingWidth(scrollPane.getPrefWidth());
        needForPrimDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Pseudocode Of Prim's Algorithm\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeTitle = new Text(descriptionLabel.getText());
        pseudocodeTitle.setFill(Paint.valueOf("red"));
        pseudocodeTitle.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("Prim( Graph graph )\n" +
                "   Initialize:\n" +
                "       - Set of vertices included in minimum spanning tree: includedVertices = {} \n" +
                "       - Minimum spanning tree: MST = {}\n" +
                "   Choose an arbitrary starting vertex s\n" +
                "   while (includedVertices.size() < graph.V())\n" +
                "       Find the cheapest edge (u, v) where u is in includedVertices and v is not\n" +
                "       Add v to includedVertices\n" +
                "       Add edge (u, v) to MST\n" +
                "   return MST\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text pseudocodeDescription = new Text(descriptionLabel.getText());
        pseudocodeDescription.setFill(Paint.valueOf("black"));
        pseudocodeDescription.setWrappingWidth(scrollPane.getPrefWidth());
        pseudocodeDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Application Of Prim's Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationTitle = new Text(descriptionLabel.getText());
        applicationTitle.setFill(Paint.valueOf("red"));
        applicationTitle.setWrappingWidth(scrollPane.getPrefWidth());
        applicationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("• Network design\n" +
                "• Circuit layout\n" +
                "• To make protocols in network cycles\n" +
                "• Transportation planning\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text applicationDescription = new Text(descriptionLabel.getText());
        applicationDescription.setFill(Paint.valueOf("black"));
        applicationDescription.setWrappingWidth(scrollPane.getPrefWidth());
        applicationDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Complexity Of Prim's Algorithm:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityTitle = new Text(descriptionLabel.getText());
        complexityTitle.setFill(Paint.valueOf("red"));
        complexityTitle.setWrappingWidth(scrollPane.getPrefWidth());
        complexityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        descriptionLabel = new Label("With an adjacency list implementation, the time complexity is O(E*logV), where " +
                "E is the number of edges, V is the number of vertices.\nThe space complexity is O(V+E) for the adjacency list representation.\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text complexityDescription = new Text(descriptionLabel.getText());
        complexityDescription.setFill(Paint.valueOf("black"));
        complexityDescription.setWrappingWidth(scrollPane.getPrefWidth());
        complexityDescription.setFont(Font.font("Arial", 16));

        descriptionLabel = new Label("Useful Links:\n\n");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setTextAlignment(TextAlignment.JUSTIFY);
        Text usefulLinks = new Text(descriptionLabel.getText());
        usefulLinks.setFill(Paint.valueOf("red"));
        usefulLinks.setWrappingWidth(scrollPane.getPrefWidth());
        usefulLinks.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Hyperlink wikipediaLink = new Hyperlink("Wikipedia - Prim's Algorithm");
        wikipediaLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Prim%27s_algorithm"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink geeksforGeeksLink = new Hyperlink("GeeksforGeeks - Prim's Algorithm");
        geeksforGeeksLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Hyperlink tutorialspointLink = new Hyperlink("Tutorialspoint - Prim's Algorithm");
        tutorialspointLink.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://www.tutorialspoint.com/data_structures_algorithms/prims_spanning_tree_algorithm.htm"));
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        textArea.getChildren().addAll(introTitle, introDescription, howItWorksTitle, howItWorksDescription, stepsTitle,
                stepsDescription, needForPrim, needForPrimDescription, pseudocodeTitle, pseudocodeDescription,
                applicationTitle, applicationDescription, complexityTitle, complexityDescription, usefulLinks,
                wikipediaLink, geeksforGeeksLink, tutorialspointLink);

        //</editor-fold>

        updateTextFlowSize();

    }

    private void updateTextFlowSize() {
        textArea.requestLayout();
        scrollPane.requestLayout();
    }
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }
}
