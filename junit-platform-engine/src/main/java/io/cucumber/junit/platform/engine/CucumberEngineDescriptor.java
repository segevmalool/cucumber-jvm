package io.cucumber.junit.platform.engine;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.junit.platform.engine.support.hierarchical.Node;

import java.util.Optional;

class CucumberEngineDescriptor extends EngineDescriptor implements Node<CucumberEngineExecutionContext> {

    private static String ENGINE_ID;

    CucumberEngineDescriptor(UniqueId uniqueId, String engineId) {
        super(uniqueId, "Cucumber");
        this.setEngineId(engineId);
    }

    public static String getEngineId() {
        return ENGINE_ID;
    }

    private static void setEngineId(String engineId) {
        ENGINE_ID = engineId;
    }

    private static void recursivelyMerge(TestDescriptor descriptor, TestDescriptor parent) {
        Optional<? extends TestDescriptor> byUniqueId = parent.findByUniqueId(descriptor.getUniqueId());
        if (!byUniqueId.isPresent()) {
            parent.addChild(descriptor);
        } else {
            byUniqueId.ifPresent(
                existingParent -> descriptor.getChildren()
                        .forEach(child -> recursivelyMerge(child, existingParent)));
        }
    }

    @Override
    public CucumberEngineExecutionContext prepare(CucumberEngineExecutionContext context) {
        if (getChildren().isEmpty()) {
            return context;
        }
        context.startTestRun();
        return context;
    }

    @Override
    public CucumberEngineExecutionContext before(CucumberEngineExecutionContext context) {
        if (getChildren().isEmpty()) {
            return context;
        }
        context.runBeforeAllHooks();
        return context;
    }

    @Override
    public void after(CucumberEngineExecutionContext context) {
        if (getChildren().isEmpty()) {
            return;
        }
        context.runAfterAllHooks();
    }

    @Override
    public void cleanUp(CucumberEngineExecutionContext context) {
        if (getChildren().isEmpty()) {
            return;
        }
        context.finishTestRun();
    }

    void mergeFeature(FeatureDescriptor descriptor) {
        recursivelyMerge(descriptor, this);
    }

}
