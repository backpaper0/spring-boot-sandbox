package study.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class MyTransactionManager extends AbstractPlatformTransactionManager {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected Object doGetTransaction() throws TransactionException {
		final Object transaction = TransactionSynchronizationManager.hasResource(this)
				? TransactionSynchronizationManager.getResource(this)
				: MyTransaction.CREATED;
		log.info("doGetTransaction: {}", transaction);
		return transaction;
	}

	@Override
	protected boolean isExistingTransaction(final Object transaction) throws TransactionException {
		return transaction == MyTransaction.BEGAN;
	}

	@Override
	protected void doBegin(final Object transaction, final TransactionDefinition definition)
			throws TransactionException {
		if (transaction == MyTransaction.CREATED) {
			log.info("doBegin");
			TransactionSynchronizationManager.bindResource(this, MyTransaction.BEGAN);
		} else if (transaction == MyTransaction.BEGAN) {
			//nop
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	protected void doCommit(final DefaultTransactionStatus status) throws TransactionException {
		TransactionSynchronizationManager.unbindResource(this);
		log.info("doCommit");
	}

	@Override
	protected void doRollback(final DefaultTransactionStatus status) throws TransactionException {
		TransactionSynchronizationManager.unbindResource(this);
		log.info("doRollback");
	}

	private enum MyTransaction {
		CREATED,
		BEGAN
	}
}
