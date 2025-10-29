package tp.jtaSpringBatch.config;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;


@Configuration
public class AtomikosConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager atomikosTransactionManager() {
        UserTransactionManager utm = new UserTransactionManager();
        utm.setForceShutdown(false);
        return utm;
    }

    @Bean
    public UserTransactionImp atomikosUserTransaction() throws Exception {
        UserTransactionImp ut = new UserTransactionImp();
        ut.setTransactionTimeout(300);
        return ut;
    }

    @Bean
    public JtaTransactionManager transactionManager() throws Exception {
        JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(atomikosUserTransaction(), atomikosTransactionManager());
        jtaTransactionManager.setAllowCustomIsolationLevels(true);
        return jtaTransactionManager;
    }
}