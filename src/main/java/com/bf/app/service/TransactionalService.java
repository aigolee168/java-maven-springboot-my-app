package com.bf.app.service;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

@Service
public class TransactionalService {
	
	private final PlatformTransactionManager txmgr;
	
	public TransactionalService(PlatformTransactionManager txmgr) {
		super();
		this.txmgr = txmgr;
	}

	public void run(Runnable task) {
		TransactionStatus transactionStatus = txmgr.getTransaction(null);
		try {
			task.run();
		} catch (Exception e) {
			txmgr.rollback(transactionStatus);
			throw new RuntimeException(e);
		}
		try {
			// 提交事务出现异常时不能回滚，因此要捕获异常
			txmgr.commit(transactionStatus);
		} catch (RuntimeException e) {
			throw e;
		}
	}
	
	public <T> T call(Callable<T> call) {
		TransactionStatus transactionStatus = txmgr.getTransaction(null);
		T result;
		try {
			result = call.call();
		} catch (Exception e) {
			txmgr.rollback(transactionStatus);
			throw new RuntimeException(e);
		}
		try {
			// 提交事务出现异常时不能回滚，因此要捕获异常
			txmgr.commit(transactionStatus);
			return result;
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
