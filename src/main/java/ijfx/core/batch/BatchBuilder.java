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
package ijfx.core.batch;

import ijfx.core.datamodel.DatasetHolder;
import ijfx.core.workflow.DefaultWorkflow;
import ijfx.core.workflow.DefaultWorkflowStep;
import ijfx.core.workflow.Workflow;
import ijfx.ui.main.ImageJFX;
import ijfx.ui.utils.NamingUtils;
import io.scif.services.DatasetIOService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import mongis.utils.task.FluentTask;
import mongis.utils.task.ProgressHandler;
import net.imagej.Dataset;
import org.scijava.Context;
import org.scijava.plugin.Parameter;
import org.scijava.ui.UIService;
import ijfx.explorer.ExplorerViewService;

/**
 *
 * @author Cyril MONGIS
 */
public class BatchBuilder {

    private DefaultWorkflow workflow = new DefaultWorkflow();

    @Parameter
    Context context;

    @Parameter
    ExplorerViewService explorerService;

    @Parameter
    BatchService batchService;

    @Parameter
    DatasetIOService datasetIOService;

    @Parameter
    UIService uiService;
    
    List<DatasetHolder> holders = new ArrayList<>();

    String suffix = null;

    String saveIn;

    Consumer<Dataset> saver = dataset -> {
    };

    private static final Logger logger = ImageJFX.getLogger();

    public BatchBuilder(Context context) {
        context.inject(this);
    }

    public BatchBuilder add(DatasetHolder holder) {
        holders.add(holder);
        return this;
    }
    
    public BatchBuilder add(Collection<? extends DatasetHolder> datasets) {
        holders.addAll(datasets);
        return this;
    }

    public BatchBuilder addFolder(String folder) {

        holders.addAll(explorerService
                .indexDirectory(ProgressHandler.NONE, new File(folder))
                .collect(Collectors.toList()));

        return this;

    }

    public BatchBuilder addStep(Class<?> moduleClass, Object... params) {

        DefaultWorkflowStep step = new DefaultWorkflowStep(context, moduleClass.getName());
        for (int i = 0; i != params.length; i += 2) {
            step.setParameter(params[i].toString(), params[i + 1]);
        }

        workflow.getStepList().add(step);

        return this;

    }

    public BatchBuilder saveIn(String folder) {
        saveIn = folder;
        return then(this::saveDataset);
    }
    
    public BatchBuilder thenShow() {
        return then(this::showDataset);
    }

    public BatchBuilder then(Consumer<Dataset> consumer) {
        this.saver = consumer;
        return this;
    }
    
    public void start(ProgressHandler handler, boolean copy) {

        double total = holders.size() * workflow.getStepList().size();

        handler.setTotal(total);

        for (DatasetHolder holder : holders) {

            holder.load();

            Dataset input = holder.getDataset();

            if (copy) {
                input = input.duplicate();
            }

            Dataset dataset = batchService.applyWorkflow(handler, input, workflow);
            if (dataset == null) {
                logger.severe(input.getName() + " progress not complete. Skipping");
                holder.dispose();
                continue;
            }
            dataset.setName(input.getName());
            saver.accept(dataset);

            holder.dispose();
        }

    }

    public FluentTask<Boolean, Void> startAsync(boolean copy) {

        return new FluentTask<Boolean, Void>()
                .setInput(copy)
                .consume(this::start)
                .start();
    }

    /*
        Handlers
     */
    protected void saveDataset(Dataset dataset) {

        File target = NamingUtils.replaceWithExtension(new File(new File(saveIn), dataset.getName()), "tif");

        if (suffix != null) {
            target = NamingUtils.addSuffix(target, suffix);
        }

        if (target.getParentFile().exists() == false) {
            target.getParentFile().mkdirs();
        }

        logger.info("Saving " + target.getAbsolutePath() + "...");
        try {
            datasetIOService.save(dataset, target.getAbsolutePath());

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error when saving " + dataset.getName(), e);
        }

    }
    protected void showDataset(Dataset dataset) {
        uiService.show(dataset);
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public BatchBuilder setWorkflow(Workflow workflow) {
        this.workflow.setStepList(workflow.getStepList());
        this.workflow.setName(workflow.getName());
        return this;
    }

}
