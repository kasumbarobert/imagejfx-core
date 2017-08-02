/*
    This file is part of ImageJ FX.

    ImageJ FX is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ImageJ FX is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with ImageJ FX.  If not, see <http://www.gnu.org/licenses/>. 
    
     Copyright 2015,2016 Cyril MONGIS, Michael Knop
	
 */
package ijfx.explorer.views;

import ijfx.explorer.datamodel.Explorable;
import ijfx.ui.display.annotation.DefaultAnnotationDialog;
import ijfx.ui.main.ImageJFX;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import mongis.utils.panecell.PaneCellController;
import net.imagej.Dataset;

import org.scijava.plugin.Plugin;

/**
 *
 * @author sapho
 */
@Plugin(type = ExplorerView.class, priority = 0.7, label = "Summary", iconPath = "fa:table")
public class DetailsExplorerView extends BorderPane implements ExplorerView {

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox hBox;

    @FXML
    private Button last;

    @FXML
    private Button next;

    @FXML
    private Label label;

    @FXML
    private TableView<Explorable> tableView;

    private List<? extends Explorable> itemsList;

    private List<? extends Explorable> currentItems;

    private Consumer<DataClickEvent<Explorable>> onItemClicked;

    private Explorable currentItemsOne;

    private static String FXMLWAY = "/ijfx/ui/display/image/DetailsDisplay.fxml";

    public DetailsExplorerView() {

        loadFXML();

        last.setOnAction(this::onDisplayLastExplorable);
        next.setOnAction(this::onDisplayNextExplorable);
         
    }

    private void loadFXML() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXMLWAY));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();

        } catch (IOException ex) {
            Logger.getLogger(DefaultAnnotationDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("prout");

    }

    @Override
    public Node getUIComponent() {
        return this;
    }

    @Override
    public void setItems(List<? extends Explorable> items) {
        if (items.size() == 1) {
            this.itemsList = items;
            System.out.println("itemlist "+itemsList);
            currentItems = items;
           
                
           
            
            
        }

    }

    @Override
    public List<? extends Explorable> getSelectedItems() {
        //List<Explorable> selected = tableView.getSelectionModel().getSelectedItems();

        return itemsList;
    }

    @Override
    public void setSelectedItem(List<? extends Explorable> items) {
        ImageJFX.getLogger().info(String.format("MEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERDE"));
        itemsList.stream().forEach(this::labelDisplay);
        System.out.println("Allo ? ici setselectedItem");

    }

    @Override
    public SelectionModel getSelectionModel() {
        return null;

    }

    @Override
    public void refresh() {
        

    }

    @Override
    public void setOnItemClicked(Consumer<DataClickEvent<Explorable>> eventHandler) {
    }

    public void onDisplayLastExplorable(ActionEvent event) {

    }

    public void onDisplayNextExplorable(ActionEvent event) {

    }

    public void labelDisplay(Explorable exp) {
        ImageJFX.getLogger().info(String.format("Label display nous y passons enfin "));
        Platform.runLater(() -> {
            System.out.println("TableDisplay ? ");
            Dataset set = exp.getDataset();
            label.setText(exp.getTitle());
        });

    }

    @Override
    public List<? extends Explorable> getItems() {
        return currentItems;
    }

    public Explorable getDisplayedItem() {
        return currentItemsOne;
    }
    
    private class truc<T extends Object> {
        
        private List<T> currentItems;
        
        private ObservableList<T> selectedItems = FXCollections.observableArrayList();

        public truc() {
        }
        
    }

}