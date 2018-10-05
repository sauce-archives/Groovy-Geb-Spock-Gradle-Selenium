package functionaltest.utils

import com.saucelabs.common.SauceOnDemandSessionIdProvider
import com.saucelabs.saucerest.SauceREST
import org.spockframework.runtime.model.SpecInfo

import org.spockframework.runtime.model.ErrorInfo
//import org.spockframework.runtime.model.FeatureInfo
//import org.spockframework.runtime.model.IterationInfo

class OnFailureListener extends AbstractRunListener {

    /**
     * The underlying {@link com.saucelabs.common.SauceOnDemandSessionIdProvider} instance which contains the Selenium session id.  This is typically
     * the unit test being executed.
     */
    private final SauceOnDemandSessionIdProvider sessionIdProvider

    /**
     * The instance of the Sauce OnDemand Java REST API client.
     */
    private final SauceREST sauceREST

    public OnFailureListener(SauceOnDemandSessionIdProvider sessionIdProvider, String username, String accessKey) {
        this.sessionIdProvider = sessionIdProvider
        this.sauceREST = new SauceREST(username, accessKey)
    }

    def void error(ErrorInfo error) {
        println error;
        this.sauceREST.updateJobInfo(this.sessionIdProvider.getSessionId(), "failed")
    }
}


class SauceResultExtension implements IGlobalExtension {

    protected final String username = System.getenv("SAUCE_USERNAMDE")
    protected final String accesskey = System.getenv("SAUCE_ACCESS_KEY")

    @Override
    void visitSpec(SpecInfo specInfo) {
        specInfo.addListener(new OnFailureListener(username, accesskey))
    }
}