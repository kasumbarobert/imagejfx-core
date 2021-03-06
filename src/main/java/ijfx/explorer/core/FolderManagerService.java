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
package ijfx.explorer.core;

import ijfx.core.IjfxService;
import ijfx.explorer.ExplorationMode;
import ijfx.explorer.datamodel.Explorable;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;
import net.imagej.display.ImageDisplay;

/**
 *
 * @author Cyril MONGIS, 2016
 */
//TODO: change plane extraction methods to an other service
public interface FolderManagerService extends IjfxService {

    public Folder addFolder(File file);

    public List<Folder> getFolderList();

    public Folder getCurrentFolder();

    public void setCurrentFolder(Folder folder);

    public void setExplorationMode(ExplorationMode mode);

    public ExplorationMode getCurrentExplorationMode();

    public void completeStatistics();

    public void removeFolder(Folder folder);

    public Folder getFolderContainingFile(File f);

    public final static String FOLDER_PREFERENCE_FILE = "folder_db.json";

    /**
     * Opens and explore the folder containing the following image.
     * If no open folder contains the image, it will explorer
     * the image folder
     * @param imagePath 
     */
    public void openImageFolder(String imagePath);
    
    public void openImageFolder(ImageDisplay imageDisplay);
    
    public List<Explorable> extractPlanes(List<? extends Explorable> object);
    
    public Stream<Explorable> extractPlanes(Explorable explorable);
    
}
