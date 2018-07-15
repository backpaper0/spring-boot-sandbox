package study.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class MyTransactionManager extends AbstractPlatformTransactionManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ThreadLocal<MyTransaction> values = new ThreadLocal<MyTransaction>() {
        @Override
        protected MyTransaction initialValue() {
            return MyTransaction.CREATED;
        }
    };

    @Override
    protected Object doGetTransaction() throws TransactionException {
        final Object transaction = values.get();
        logger.info("doGetTransaction: {}", transaction);
        return transaction;
    }

    @Override
    protected void doBegin(final Object transaction, final TransactionDefinition definition)
            throws TransactionException {
        if (transaction == MyTransaction.CREATED) {
            logger.info("doBegin");
            values.set(MyTransaction.BEGAN);
        } else if (transaction == MyTransaction.BEGAN) {
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    protected void doCommit(final DefaultTransactionStatus status) throws TransactionException {
        final Object transaction = status.getTransaction();
        if (transaction == MyTransaction.BEGAN) {
            values.remove();
            logger.info("doCommit");
        }
    }

    @Override
    protected void doRollback(final DefaultTransactionStatus status) throws TransactionException {
        final Object transaction = status.getTransaction();
        if (transaction == MyTransaction.BEGAN) {
            values.remove();
            logger.info("doRollback");
        }
    }

    private enum MyTransaction {
        CREATED,
        BEGAN
    }
}
