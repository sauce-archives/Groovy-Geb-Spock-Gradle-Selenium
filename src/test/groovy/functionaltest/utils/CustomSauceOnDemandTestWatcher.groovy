package functionaltest.utils

import com.saucelabs.common.SauceOnDemandSessionIdProvider
import com.saucelabs.common.Utils
import com.saucelabs.saucerest.SauceREST
import org.junit.rules.TestWatcher
import org.junit.runner.Description


class CustomSauceOnDemandTestWatcher extends TestWatcher {
    private String watchedLog = ""
    
     /**
     * The underlying {@link com.saucelabs.common.SauceOnDemandSessionIdProvider} instance which contains the Selenium session id.  This is typically
     * the unit test being executed.
     */
    private final SauceOnDemandSessionIdProvider sessionIdProvider

    /**
     * The instance of the Sauce OnDemand Java REST API client.
     */
    private final SauceREST sauceREST

    /**
     * Boolean indicating whether to print the log messages to the stdout.
     */
    private boolean verboseMode = true


    public CustomSauceOnDemandTestWatcher(SauceOnDemandSessionIdProvider sessionIdProvider, String username, String accessKey, boolean verboseMode) {
        this.sessionIdProvider = sessionIdProvider
        this.sauceREST = new SauceREST(username, accessKey)
        this.verboseMode = verboseMode
    }

    private void printSessionId(Description description) {
        if (verboseMode) {
            String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s.%3$s", sessionIdProvider.getSessionId(), description.getClassName(), description.getMethodName())
            System.out.println(message)
        }
    }

    /*
     * This doesn't fully work due to https://github.com/spockframework/spock/issues/118
     * The new version of spock v.1.1 is supposed to fix this soon
     */
    protected void failed(Throwable e, Description description) {
        this.watchedLog+= description.getMethodName()
        if (sessionIdProvider!=null && sessionIdProvider.getSessionId()!=null) {
            printSessionId(description)
            updateTestStatus()
            if (verboseMode) {
                // get, and print to StdOut, the link to the job
                String authLink = sauceREST.getPublicJobLink(sessionIdProvider.getSessionId())
                System.out.println("Job link: " + authLink)
            }
        } else {
            if (verboseMode) {
                System.out.println("Test Failed")
            }
        }
    }

    protected void succeeded(Description description) {
        if (sessionIdProvider.getSessionId()!=null) {
            System.out.println("Test succeeded: " + description.getMethodName())
            updateTestStatus()
        } else {
            if (verboseMode) {
                System.out.println("Test Succeeded")
            }
        }
    }

    public boolean areTestsSuccessful() {
        boolean pass = true;
        if(this.watchedLog!=null && !this.watchedLog.equals("")) {
            pass = false
        }
        return pass
    }

    public void updateTestStatus() {
        Map<String, Object> updates = new HashMap<>()
        updates.put("passed", areTestsSuccessful())
        Utils.addBuildNumberToUpdate(updates)
        this.sauceREST.updateJobInfo(this.sessionIdProvider.getSessionId(), updates)
    }

}