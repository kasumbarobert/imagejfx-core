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
package ijfx.explorer.datamodel.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import ijfx.commands.io.OpenImageFX;
import ijfx.core.activity.ActivityService;
import ijfx.core.image.DatasetUtilsService;
import ijfx.core.image.ThumbService;
import ijfx.core.imagedb.ImageRecord;
import ijfx.core.metadata.MetaData;
import ijfx.core.metadata.MetaDataSet;
import ijfx.core.metadata.MetaDataSetType;
import ijfx.explorer.datamodel.AbstractExplorable;
import ijfx.explorer.datamodel.Tag;
import static ijfx.explorer.datamodel.Taggable.injectSafe;
import ijfx.ui.activity.DisplayContainer;
import io.scif.services.DatasetIOService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import mongis.utils.FileUtils;
import net.imagej.Dataset;
import org.apache.commons.io.FilenameUtils;
import org.scijava.Context;
import org.scijava.command.CommandModule;
import org.scijava.command.CommandService;
import org.scijava.plugin.Parameter;

/**
 * Wrapper class that transform an ImageRecord to an Explorable
 *
 * @author Cyril MONGIS, 2016
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageRecordIconizer extends AbstractExplorable {

    private ImageRecord imageRecord;

    @Parameter
    ThumbService thumbService;

    @Parameter
    CommandService commandService;

    @Parameter
    ActivityService activityService;

    @Parameter
    DatasetIOService datasetIoService;

    @Parameter
    DatasetUtilsService datasetUtilsService;

    Dataset dataset;
       
    private final BooleanProperty visibleProperty = new SimpleBooleanProperty(false);
    
    private final static String SERIE_NAME_FORMAT = "%s - Serie %d";
    private final static String SERIE_SAVE_FORMAT = "%s_serie_%d";
    
    private BooleanProperty selectedProperty;

    int imageId = 0;

    boolean series = false;

    private MetaDataSet set;

    @JsonCreator
    public ImageRecordIconizer() {
        
    }
    
    
    @JsonSetter(value="imageRecord")
    public void setImageRecord(ImageRecord imageRecord) {
        this.imageRecord = imageRecord;
        set = new MetaDataSet(MetaDataSetType.FILE);
        set.merge(imageRecord.getMetaDataSet());
        set.putGeneric(MetaData.SAVE_NAME, imageRecord.getFile().getName());
    }
    
    @JsonGetter(value ="imageRecord")
    public ImageRecord getImageRecord() {
        return imageRecord;
    }
    
    public ImageRecordIconizer(Context context, ImageRecord imageRecord) {
        context.inject(this);
        setImageRecord(imageRecord);
    }

    public ImageRecordIconizer(Context context, ImageRecord imageRecord, int imageId) {
        
        this(context,imageRecord);
        series = true;
        this.imageId = imageId;
        set.putGeneric(MetaData.SERIE, imageId);
        
        String baseName = FilenameUtils.getBaseName(imageRecord.getFile().getName());
        String saveName = String.format(SERIE_SAVE_FORMAT,baseName,imageId+1);
        set.putGeneric(MetaData.FILE_NAME,
               String.format(SERIE_NAME_FORMAT,imageRecord.getFile().getName(),imageId+1)
        );
        set.putGeneric(MetaData.SAVE_NAME,saveName);
    }

    @Override
    public String getTitle() {
       
        return imageRecord.getFile().getName();
    }

    @Override
    public String getSubtitle() {
        if(series) {
            return String.format("Image %d/%d",imageId+1,set.get(MetaData.SERIE_COUNT).getIntegerValue());
        }
        return FileUtils.readableFileSize(imageRecord.getFile().length());
    }

    @Override
    public String getInformations() {
        return "";
    }

    @Override
    public Image getImage() {
        try {
            return thumbService.getThumb(imageRecord.getFile(),imageId,null,100, 100);
        } catch (Exception ex) {
            Logger.getLogger(ImageRecordIconizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void open() throws Exception {

        HashMap<String, Object> inputs = new HashMap<>();
        inputs.put("file", imageRecord.getFile());
        inputs.put("imageId",imageId);
        Future<CommandModule> run = commandService.run(OpenImageFX.class, true, inputs);
        run.get();
        activityService.open(DisplayContainer.class);
    }

    @Override
    public MetaDataSet getMetaDataSet() {

        return set;
    }
    
    /*
    @Override
    public BooleanProperty selectedProperty() {
        if (selectedProperty == null) {
            selectedProperty = new SimpleBooleanProperty(false);
        }
        return selectedProperty;
    }*/

    public void load() {
        dataset = getDataset();
    }
    

    @Override
    public Dataset getDataset() {
        
        if(dataset != null) return dataset;
        
        try {

            if (series) {
                return datasetUtilsService.open(getImageRecord().getFile(), imageId, false);
            } else {
                return datasetIoService.open(getImageRecord().getFile().getAbsolutePath());
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageRecordIconizer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void dispose() {
        dataset = null;
        super.dispose();
    }
    
    @Override
    public void inject(Context context) {
        if(thumbService == null) {
            injectSafe(this,context);
            injectSafe(imageRecord, context);
        }
    }

    

}
