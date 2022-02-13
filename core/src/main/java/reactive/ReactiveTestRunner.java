package reactive;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;

public class ReactiveTestRunner {
    Logger log = LoggerFactory.getLogger(ReactiveTestRunner.class);

    public void execute() {
        log.info(() -> "Executing the tests yo");
    }
}
