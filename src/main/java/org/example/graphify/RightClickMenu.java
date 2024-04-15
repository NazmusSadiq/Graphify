package org.example.graphify;

import org.example.graphify.CanvasController.NodeFX;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RightClickMenu {

    ContextMenu menu;
    NodeFX sourceNode;
    Edge sourceEdge;
    MenuItem delete, change;

    public RightClickMenu() {
        menu = new ContextMenu();   //pop-up menu for options
        delete = new MenuItem("Delete");
        change = new MenuItem("Change ID");

        Image openIcon = new Image(getClass().getResourceAsStream("/delete_img.png"));
        ImageView openView = new ImageView(openIcon);
        delete.setGraphic(openView);

        Image textIcon = new Image(getClass().getResourceAsStream("/rename_img.png"));
        ImageView textIconView = new ImageView(textIcon);
        change.setGraphic(textIconView);

        menu.getItems().addAll(delete, change);
        menu.setOpacity(0.9);
    }

    /**
     * Constructor for the context menu on node
     *
     * @param node
     */
    public RightClickMenu(NodeFX node) {
        this();
        sourceNode = node;
        delete.setOnAction(e -> {
            SelectionController.cref.deleteNode(sourceNode);
        });
        change.setOnAction(e -> {
            SelectionController.cref.changeID(node);
        });
    }

    /**
     * Constructor for the context menu on edge
     *
     * @param edge
     */
    public RightClickMenu(Edge edge) {
        this();
        sourceEdge = edge;
        delete.setOnAction(e -> {
            SelectionController.cref.deleteEdge(sourceEdge);
        });
        change.setOnAction(e -> {
            SelectionController.cref.changeWeight(sourceEdge);
        });
    }

    public ContextMenu getMenu() {
        return menu;
    }
}
