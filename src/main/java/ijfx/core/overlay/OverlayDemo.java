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
package ijfx.core.overlay;

import net.imagej.display.ImageDisplay;
import net.imagej.display.ImageDisplayService;
import net.imagej.overlay.RectangleOverlay;
import org.scijava.Context;
import org.scijava.ItemIO;
import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 *
 * @author Cyril MONGIS
 */
@Plugin(type = Command.class, menuPath = "Plugins > Test > Overlay Demo")
public class OverlayDemo extends ContextCommand{

    @Parameter(type = ItemIO.BOTH)
    ImageDisplay imageDisplay;
    
    @Parameter
    ImageDisplayService imageDisplayService;
    
    @Parameter
    OverlayUtilsService overlayUtilsService;
    
    @Parameter
    Context context;
    
    @Override
    public void run() {
        
        overlayUtilsService.removeAllOverlay(imageDisplay);
        
        
        RectangleOverlay overlay = new RectangleOverlay(context);
        
        overlay.setOrigin(10, 0);
        overlay.setOrigin(10,1);
        overlay.setExtent(50, 0);
        overlay.setExtent(50,1);
        
        
        overlayUtilsService.addOverlay(imageDisplay, overlay);
        

    }
    
    
    
    
}
