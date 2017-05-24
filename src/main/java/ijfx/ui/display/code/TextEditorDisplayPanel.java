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
package ijfx.ui.display.code;

import ijfx.ui.display.image.AbstractFXDisplayPanel;
import ijfx.ui.display.image.FXDisplayPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.fxmisc.richtext.CodeArea;
import org.scijava.display.Display;
import org.scijava.display.TextDisplay;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * TODO : Change to ScriptDisplayPanelFX
 * @author florian
 */
@Plugin(type = FXDisplayPanel.class)
public class TextEditorDisplayPanel extends AbstractFXDisplayPanel<ScriptDisplay> {
    @Parameter
    Scene scene;
    AnchorPane root;
    BorderPane borderPane;

    public TextEditorDisplayPanel() {
        super(ScriptDisplay.class);
    }

    @Override
    public void pack() {
        borderPane = new BorderPane();
        root = new AnchorPane();
        root.getChildren().add(borderPane);
        root.getStylesheets().add(getClass().getResource("/ijfx/ui/display/code/JavaRichtext.css").toExternalForm());
        
        AnchorPane.setBottomAnchor(borderPane, 15d);
        AnchorPane.setTopAnchor(borderPane, 0d);
        AnchorPane.setLeftAnchor(borderPane, 0d);
        AnchorPane.setRightAnchor(borderPane, 0d);

        TextArea textAreaCreator = new TextArea();
        CodeArea codeArea = textAreaCreator.getCodeArea();
        borderPane.setCenter(codeArea);
        textAreaCreator.nanorcParser(getClass().getResource("/ijfx/ui/display/code/javascript.nanorc").getFile());

    }

    @Override
    public Pane getUIComponent() {
        return this.root;
    }

    @Override
    public void redoLayout() {
    }

    @Override
    public void setLabel(String string) {
    }

    @Override
    public void redraw() {
    }

}
