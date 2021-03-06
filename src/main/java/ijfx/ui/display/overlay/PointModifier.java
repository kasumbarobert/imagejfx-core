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
package ijfx.ui.display.overlay;

import java.util.List;
import javafx.beans.Observable;
import javafx.geometry.Point2D;
import jdk.nashorn.internal.ir.annotations.Ignore;
import net.imagej.display.ImageDisplay;
import net.imagej.overlay.Overlay;
import net.imagej.overlay.PointOverlay;

/**
 *
 * @author Cyril MONGIS, 2016
 */
@Ignore
public class PointModifier extends AbstractOverlayModifier<PointOverlay>{

    List<MoveablePoint> points;
    

    public PointModifier() {
        super(PointOverlay.class);
    }
    
    @Override
    public List<MoveablePoint> getModifiers(ImageDisplay imageDisplay, PointOverlay overlay) {
       
        init(overlay, imageDisplay);
        /*
        if(points == null) {
            points = new ArrayList<>();
            
            MoveablePoint mp = new MoveablePoint(viewport);
            
            this.overlay = overlay;
            
            mp.positionOnDataProperty().addListener(this::onPositionOnImageChanged);
            
            points.add(mp);
            
            mp.placeOnScreen(viewport.getPositionOnCamera(PointOverlayHelper.getOverlayPosition(overlay)));
            
        }
        */
        return points;
        
    }

    public void onPositionOnImageChanged(Observable obs, Point2D before, Point2D after) {
        
        PointOverlayHelper.setOverlayPosition(getOverlay(), after);
        
    }
    
   
}
