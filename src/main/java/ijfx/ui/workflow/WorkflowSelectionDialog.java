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
package ijfx.ui.workflow;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import ijfx.core.workflow.Workflow;
import ijfx.ui.main.ImageJFX;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import mongis.utils.FXUtilities;
import org.scijava.Context;

/**
 *
 * @author Cyril MONGIS, 2016
 */
public class WorkflowSelectionDialog extends Dialog<Workflow> {

    WorkflowManagerPanel panel;

    ButtonType EDIT = new ButtonType("Edit");

    public WorkflowSelectionDialog(Context context) {

        super();

        getDialogPane().getStylesheets().add(ImageJFX.STYLESHEET_ADDR);
        panel = new WorkflowManagerPanel(context);
        getDialogPane().setContent(panel);

        getDialogPane().getButtonTypes().add(EDIT);
        getDialogPane().getButtonTypes().add(ButtonType.OK);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(panel.selectedWorkflowProperty().isNull());
        
        // setting the edit button
        Button editButton = (Button) getDialogPane().lookupButton(EDIT);
        editButton.setOnMouseClicked(event -> panel.editSelected());

        // de activate any action event which will prevent the dialog to close on click
        editButton.addEventFilter(ActionEvent.ANY, event->event.consume());
        
        // styling the buttons
        FXUtilities.styleDialogButtons(this);
        FXUtilities.styleDialogButton(this, EDIT, "warning", FontAwesomeIcon.COG);

        setResultConverter(this::convert);

    }

    public Workflow convert(ButtonType t) {

        if (t == ButtonType.OK) {
            return panel.selectedWorkflowProperty().getValue();
        } else {
            return null;
        }

    }

}
