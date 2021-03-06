/*
 * /*
 *     This file is part of ImageJ FX.
 *
 *     ImageJ FX is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ImageJ FX is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with ImageJ FX.  If not, see <http://www.gnu.org/licenses/>. 
 *
 * 	Copyright 2015,2016 Cyril MONGIS, Michael Knop
 *
 */
package ijfx.ui.plugin.history;

import ijfx.core.workflow.WorkflowStep;
import mongis.utils.listcell.DraggableListCell;

import org.scijava.Context;
import org.scijava.plugin.Parameter;
import javafx.application.Platform;
import javafx.beans.Observable;

/**
 *
 * @author Cyril MONGIS, 2015
 */
class HistoryStep extends DraggableListCell<WorkflowStep> {

    @Parameter
    Context context;

    HistoryStepCtrl ctrl;

    public void refresh() {
        if (ctrl != null) {
            ctrl.refresh();
        }
    }

    @Override
    protected void onItemChanged(Observable obs, WorkflowStep oldValue, WorkflowStep newValue) {

        if (Platform.isFxApplicationThread() == false) {
            Platform.runLater(() -> onItemChanged(obs, oldValue, newValue));
            return;
        }

        if (ctrl == null) {
            ctrl = new HistoryStepCtrl(context);
        }

        if (newValue == null) {
            setGraphic(null);
        } else {
            setGraphic(ctrl);
            ctrl.setStep(newValue);
        }

    }

}
