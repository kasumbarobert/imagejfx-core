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
package ijfx.core.image;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import net.imagej.Dataset;
import net.imagej.display.DatasetView;
import net.imagej.display.ImageDisplay;
import net.imagej.display.ImageDisplayService;

/**
 *
 * @author Cyril MONGIS
 */
@JsonDeserialize(as = DefaultChannelSettings.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface ChannelSettings {

    public int getChannelCount();

    @JsonGetter("channels")
    public List<Channel> getChannelSettings();
    
    @JsonSetter("channels")
    public void setChannels(List<Channel> settings);

    public default Channel get(int i) {
        return getChannelSettings().get(i);
    }

    public static void applyTo(ChannelSettings set, Dataset dataset) {
        if (set != null) {
            for (int i = 0; i != set.getChannelCount(); i++) {
                // applying the settings to the dataset
                set.get(i).apply(dataset, i);
            }
        }

    }

    public default void apply(ImageDisplay imageDisplay) {
        
        
        ImageDisplayService service = imageDisplay.getContext().getService(ImageDisplayService.class);
        
        apply(service.getActiveDataset(imageDisplay));
        apply(service.getActiveDatasetView(imageDisplay));
        
    }
    
    public default void apply(DatasetView datasetView) {

        for (int i = 0; i != datasetView.getChannelCount(); i++) {

            if (i + 1 > getChannelCount()) {
                return;
            }
            get(i).apply(datasetView, i);

        }

    }

    public default void apply(Dataset dataset) {
        for (int i = 0; i != dataset.getChannels(); i++) {
            if (i + 1 > getChannelCount()) {
                return;
            }
            get(i).apply(dataset, i);

        }
    }

}
