package functionaltest.utils

//import com.spockframework.report.extension.InfoContainer
//import com.spockframework.report.internal.*
//import com.spockframework.report.util.Utils
//import org.spockframework.runtime.IRunListener
import org.spockframework.runtime.extension.AbstractGlobalExtension
//import org.spockframework.runtime.model.ErrorInfo
//import org.spockframework.runtime.model.FeatureInfo
//import org.spockframework.runtime.model.IterationInfo
import org.spockframework.runtime.model.SpecInfo
import com.saucelabs.common.SauceOnDemandSessionIdProvider
import com.saucelabs.saucerest.SauceREST

class SauceResult extends AbstractGlobalExtension {

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

    @Override
    void visitSpec(SpecInfo spec) {

        //def sauceResult = new SauceSpecResultListener()

    }
}

/*
class SauceSpecResultListener implements IRunListener {
    @Override
    synchronized void beforeSpec( SpecInfo spec ) {
        specData = new SpecData( info: spec )
        log.debug( "Before spec: {}", Utils.getSpecClassName( specData ) )
        startT = System.currentTimeMillis()
    }

    @Override
    void beforeFeature( FeatureInfo feature ) {
        log.debug( "Before feature: {}", feature.name )
        specData.featureRuns << new FeatureRun( feature: feature )
    }

    @Override
    void beforeIteration( IterationInfo iteration ) {
        log.debug( "Before iteration: {}", iteration.name )
        currentRun().with {
            failuresByIteration[ iteration ] = [ ]
            timeByIteration[ iteration ] = System.nanoTime()
        }
        currentIteration = iteration
    }

    @Override
    void afterIteration( IterationInfo iteration ) {
        log.debug( "After iteration: {}", iteration.name )
        currentRun().with {
            def startTime = timeByIteration[ iteration ]
            if ( !startTime ) {
                timeByIteration[ iteration ] = 0L
            } else {
                long totalTime = ( ( System.nanoTime() - startTime ) / 1_000_000L ).toLong()
                timeByIteration[ iteration ] = totalTime
            }
        }
        currentIteration = null
        InfoContainer.addSeparator( Utils.getSpecClassName( specData ) )
    }

    @Override
    void afterFeature( FeatureInfo feature ) {
        log.debug( "After feature: {}", feature.name )
    }

    @Override
    void afterSpec( SpecInfo spec ) {
        assert specData.info == spec
        log.debug( "After spec: {}", Utils.getSpecClassName( specData ) )
        specData.totalTime = System.currentTimeMillis() - startT
        reportCreator.createReportFor specData
        specData = null
    }

    @Override
    void error( ErrorInfo errorInfo ) {
        try {
            def errorInInitialization = ( specData == null )
            log.debug( "Error on spec: {}", errorInInitialization ?
                    "<${EmptyInitializationException.INIT_ERROR}>" :
                    Utils.getSpecClassName( specData ) )

            if ( errorInInitialization ) {
                // call beforeSpec because Spock does not do it in this case
                def specInfo = errorInfo.method.parent
                beforeSpec specInfo

                def currentError = new ErrorInfo( errorInfo.method, new SpecInitializationError( errorInfo.exception ) )
                def features = specInfo.allFeaturesInExecutionOrder

                // simulate all features failing
                if ( features ) for ( featureInfo in features ) {
                    markWithInitializationError featureInfo
                    beforeFeature featureInfo
                    error currentError
                    afterFeature featureInfo

                    // only the first error needs to be complete, use a dummy error for the next features
                    currentError = new ErrorInfo( errorInfo.method, EmptyInitializationException.instance )
                } else {
                    // the error occurred before even the features could be initialized,
                    // make up a fake failed feature to show in the report
                    specData.initializationError = currentError
                    def featureInfo = dummyFeature()
                    beforeFeature featureInfo
                    error currentError
                    afterFeature featureInfo
                }

                // Spock will not call afterSpec in this case as of version 1.0-groovy-2.4
                afterSpec specData.info
            } else {
                def iteration = currentIteration ?: dummySpecIteration()
                currentRun().failuresByIteration[ iteration ] << new SpecProblem( errorInfo )
            }
        } catch ( Throwable e ) {
            // nothing we can do here
            e.printStackTrace()
        }
    }
}
*/