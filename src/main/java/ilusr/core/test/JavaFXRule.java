package ilusr.core.test;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * 
 * @author Jeff Riggle
 *
 */
public class JavaFXRule implements TestRule {
	
    private static boolean setup;

    @Override
    public Statement apply(Statement statement, Description description) {
    	FXThread threadAnnotation = description.getAnnotation(FXThread.class);
    	boolean runFX = threadAnnotation == null ? true : threadAnnotation.runOnFXThread();
        
    	return new OnJFXThreadStatement(statement, runFX);
    }

    private static class OnJFXThreadStatement extends Statement {
        
        private final Statement statement;
        private final boolean runFX;

        public OnJFXThreadStatement(Statement statement, boolean runFX) {
            this.statement = statement;
            this.runFX = runFX;
        }

        private Throwable rethrownException = null;
        
        @Override
        public void evaluate() throws Throwable {
            if(!setup) {
                setupJavaFX();
            }
            
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            
            if (!runFX) {
            	statement.evaluate();
            	return;
            }
            
            Platform.runLater(() -> {
                try {
                    statement.evaluate();
                } catch (Throwable e) {
                    rethrownException = e;
                }
                countDownLatch.countDown();
            });
            
            countDownLatch.await();
            
            // if an exception was thrown by the statement during evaluation,
            // then re-throw it to fail the test
            if(rethrownException != null) {
                throw rethrownException;
            }
        }

        private void setupJavaFX() throws InterruptedException {
            final CountDownLatch latch = new CountDownLatch(1);
            
            SwingUtilities.invokeLater(() -> {
            	// initialize the JavaFX environment
                new JFXPanel(); 
                latch.countDown();
            });
            
            latch.await();
            setup = true;
        }
        
    }
}