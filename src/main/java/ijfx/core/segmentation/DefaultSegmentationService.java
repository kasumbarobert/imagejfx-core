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
package ijfx.core.segmentation;

import ijfx.explorer.ExplorableList;
import java.util.List;
import java.util.stream.Collectors;
import org.scijava.display.DisplayService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.service.AbstractService;
import org.scijava.service.Service;

/**
 *
 * @author cyril
 */
@Plugin(type = Service.class)
public class DefaultSegmentationService extends AbstractService implements SegmentationService {

    @Parameter
    DisplayService displayService;
    
    
    @Override
    public SegmentationTaskBuilder createSegmentation() {
        return new SegmentationTaskBuilder(getContext());
    }

    @Override
    public void show(List<List<? extends SegmentedObject>> objects) {
        
        ExplorableList result = new ExplorableList();
        
        List<SegmentedObjectExplorerWrapper> collect = objects
                .stream()
                .flatMap(list->list.stream())
                .map(SegmentedObjectExplorerWrapper::new)
                .collect(Collectors.toList());

        result.addAll(collect);
        
        
        displayService.createDisplay("Segmentation result", result);
    }
    
    
    

   
    
}
