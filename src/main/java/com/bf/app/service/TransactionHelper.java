package com.bf.app.service;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;

@Service
public class TransactionHelper {
	
	@Autowired
	private PlatformTransactionManager txmgr;
	
	public void run(Runnable task) {
		TransactionStatus transactionStatus = txmgr.getTransaction(null); // 开始事务
		try {
			task.run();
			try {
				// 提交事务出现异常时不能回滚，因此要捕获异常
				txmgr.commit(transactionStatus);
			} catch (RuntimeException e) {
				throw e;
			}
		} catch (Exception e) {
			txmgr.rollback(transactionStatus); // 回滚事务
			throw new RuntimeException(e);
		}
	}
	
	public <T> T call(Callable<T> call) {
		TransactionStatus transactionStatus = txmgr.getTransaction(null); // 开始事务
		try {
			T result = call.call();
			try {
				// 提交事务出现异常时不能回滚，因此要捕获异常
				txmgr.commit(transactionStatus);
				return result;
			} catch (RuntimeException e) {
				throw e;
			}
		} catch (Exception e) {
			txmgr.rollback(transactionStatus); // 回滚事务
			throw new RuntimeException(e);
		}
	}

}
