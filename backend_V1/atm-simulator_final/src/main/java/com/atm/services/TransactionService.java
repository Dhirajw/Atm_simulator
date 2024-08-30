package com.atm.services;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.entities.Check;
import com.atm.entities.Customer;
import com.atm.entities.Transaction;
import com.atm.repositorie.CustomerRepository;
import com.atm.repositorie.TransactionRepository;
import com.atm.serviceinterface.TransactionServiceInterface;

@Service
public class TransactionService implements TransactionServiceInterface{
	
	@Autowired
	private  TransactionRepository transactionRepository;
	@Autowired
	private CustomerRepository custreporef;
	
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

 
    public void saveTransaction(Transaction transaction) {
        try {
            // Ensure all required fields are set
            if (transaction.getInsertedOn() == null) {
                transaction.setInsertedOn(LocalDateTime.now());
            }
            if (transaction.getUpdatedOn() == null) {
                transaction.setUpdatedOn(LocalDateTime.now());
            }

            transactionRepository.save(transaction);
            System.out.println("Transaction saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }
    @Autowired
    public CustomerService custserviceref;
    public void saveCardlessTransaction( String tranId ,double amount,int customerId,int atmId,
            String tranType, String tranStatus, String pspStatus) {
				Transaction transaction = new Transaction();
				transaction.setTranId(tranId);
				transaction.setAtmId(atmId);
				transaction.setCustomerId(customerId);
				transaction.setAmount(amount);
				transaction.setIfscCode("CDAC0001001");
				transaction.setBankName("CDAC Bank");
				transaction.setCustName(custserviceref.findCustomerById(customerId).getCustName());
				transaction.setTranType(tranType);
				transaction.setTranStatus(tranStatus);
				transaction.setPspStatus(pspStatus);
				transaction.setInsertedOn(LocalDateTime.now());
				transaction.setUpdatedOn(LocalDateTime.now());
				
				// Save the transaction to the repository
				transactionRepository.save(transaction);
				}
  
	public List<Transaction> FindAlltranc()
	{
		return transactionRepository.findAll();
	}

	public void saveTransaction(Object upiStatus) {
		// TODO Auto-generated method stub
		
	}
	public String checkStatus(Check transactionId)
	{
		String message = "";
		List<Transaction> allTransaction = transactionRepository.findAll();
		for(Transaction find : allTransaction) {
			if(find.getTranId().equals(transactionId.getCheck())) {
				if(find.getPspStatus().equals("Success"))
					message = "Success";
				break;
			}
			else 
				message =  "Transaction Not Found";
		}
		return message;
	}
	
	
	//to update customer money
//	 public void updatecardlesscustomerbankbalance(String tranId ,Double Amount) {
//		 Transaction obj1 = null;
//		 List<Transaction>listtrans = FindAlltranc();
//		 for(Transaction reqtransc : listtrans) {
//			 if(tranId.equals(reqtransc.getTranId())) {
//				 obj1 = reqtransc;
//				 break;
//			 }
//			 else {
//				 System.out.println("not found");
//				
//			 }
//		 }
//			 List<Customer> custList = custreporef.findAll();
//			
//			 for(Customer singlecust : custList ){
//				 if(obj1.getCustomerId() == singlecust.getCustId()) {
//					 singlecust.setCurrBalance(singlecust.getCurrBalance() - Amount);
//					 break;
//				 }
//			 } 
//	    }
	 
	 

	

	public void updatecardlesscustomerbankbalance(String tranId, Double amount) {
	    Transaction obj1 = null;
	    List<Transaction> listtrans = FindAlltranc(); // This should be optimized to query by ID

	    // Find the transaction
	    for (Transaction reqtransc : listtrans) {
	        if (tranId.equals(reqtransc.getTranId())) {
	            obj1 = reqtransc;
	            break;
	        }
	    }

	    if (obj1 == null) {
	        System.out.println("Transaction not found");
	        return;
	    }

	    List<Customer> custList = custreporef.findAll(); // This should be optimized to query by ID

	    // Find the customer and update balance
	    for (Customer singlecust : custList) {
	    	
	        if (obj1.getCustomerId() == singlecust.getCustId()) {
	            singlecust.setCurrBalance(singlecust.getCurrBalance() - amount);
	            // Save the updated customer
	            custreporef.save(singlecust);
	            return; // Exit after updating the balance
	        }
	    }

	    System.out.println("Customer not found");
	}

	
}