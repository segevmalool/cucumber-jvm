package io.cucumber.junit.platform.engine;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import org.apiguardian.api.API;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.ExecutionRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestEngine;
import org.junit.platform.engine.UniqueId;
import reactive.ReactiveTestRunner;

@API(status = API.Status.EXPERIMENTAL)
public class CucumberReactiveTestEngine implements TestEngine {
    Logger log = LoggerFactory.getLogger(CucumberReactiveTestEngine.class);

    @Override
    public String getId() {
        return "cucumber-reactor";
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest engineDiscoveryRequest, UniqueId uniqueId) {
        CucumberEngineDescriptor engineDescriptor = new CucumberEngineDescriptor(uniqueId, this.getId());
        new DiscoverySelectorResolver().resolveSelectors(engineDiscoveryRequest, engineDescriptor);
        return engineDescriptor;
    }

    @Override
    public void execute(ExecutionRequest executionRequest) {
        new ReactiveTestRunner().execute();
    }
}
