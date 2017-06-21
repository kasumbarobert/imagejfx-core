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
package ijfx.ui.widgets;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sapho
 */
public class ValueAnnotationDisplayController implements Initializable {

    @FXML
    private TextField value;
    @FXML
    private TextField newValue;
    
    private final double MAX_HEIGHT = 150.0;
    private final double MIN_HEIGHT = 50.0;
    private final double PADDING = 4.0;
    
    
    private final ObservableValue<Boolean> fillState = Bindings.createObjectBinding(this:: getTextfieldState);
    private final BooleanProperty isValues = new SimpleBooleanProperty();

    
    
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isValues.bind(fillState);
        
    }
    
    //permet la récupération des données
    public String getValue(){
        return value.getText();
    }
    
    public String getNewValue(){
        return newValue.getText();
    }
    
    //défini les conditions de l'observableValue.
    public Boolean getTextfieldState(){
        if (value.getText() != null && newValue.getText() != null){
            return true;
        }
        return false;
    }
    
    //récupération public de la property
    public BooleanProperty getIsValues(){
        return isValues;
    }
    
    
    
}