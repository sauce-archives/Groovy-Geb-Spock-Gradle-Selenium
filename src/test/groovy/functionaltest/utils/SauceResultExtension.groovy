package functionaltest.utils

import com.saucelabs.common.SauceOnDemandSessionIdProvider
import com.saucelabs.saucerest.SauceREST
import org.spockframework.runtime.model.SpecInfo
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.extension.AbstractGlobalExtension

import org.spockframework.runtime.model.ErrorInfo


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


class SauceResultExtension extends AbstractGlobalExtension {

    protected final String username = System.getenv("SAUCE_USERNAME")
    protected final String accesskey = System.getenv("SAUCE_ACCESS_KEY")
    protected final SauceOnDemandSessionIdProvider sessionId

    @Override
    void visitSpec(SpecInfo specInfo) {
        specInfo.addListener(new OnFailureListener(sessionId, username, accesskey))
    }
}