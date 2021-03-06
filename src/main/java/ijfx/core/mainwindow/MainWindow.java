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
package ijfx.core.mainwindow;

import ijfx.core.activity.Activity;
import ijfx.core.hint.Hint;
import ijfx.ui.UiPlugin;
import java.util.List;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javax.management.Notification;
import org.scijava.plugin.SciJavaPlugin;
import ijfx.core.uicontext.ContextualContainer;
import ijfx.core.uiplugin.UiCommand;
import java.util.Collection;
import javafx.scene.Node;

/**
 *
 * @author Cyril MONGIS
 */
public interface MainWindow extends SciJavaPlugin {

    void init();

    void displayHint(Hint hint);
    
    default void displayHint(Collection<? extends Hint> hintList) {
        hintList.forEach(this::displayHint);
    }
    
    void displayActivity(Activity activity);

    void displayDescription(String description);
    
    void displayNotification(Notification notification);

    void displaySideMenuAction(UiCommand<MainWindow> action);

    void registerUiPlugin(UiPlugin uiPlugin);

    void addForegroundTask(Task task);

    void addBackgroundTask(Task task);

    void setReady(boolean ready);

    List<ContextualContainer<Node>> getContextualContainerList();
    
    Parent getUiComponent();

}
