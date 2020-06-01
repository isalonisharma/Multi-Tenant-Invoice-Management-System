package com.caseStudy.enumeration;

/**
 * Enumeration: PaymentStatus
 * 
 * Use: PaymentStatus(Enumeration) is a data type which contains a fixed set of constants:
 * 
 * 1. PAYMENT_DRAFT
 * 2. PAYMENT_DUE
 * 3. PAYMENT_SENT
 * 4. PAYMENT_PAID
 * 
 * @author saloni.sharma
 */
public enum PaymentStatus {
	PAYMENT_DRAFT("DRAFT"), PAYMENT_DUE("DUE"), PAYMENT_SENT("SENT"), PAYMENT_PAID("PAID");

	PaymentStatus(String name) {
		this.paymentStatus = name;
	}

	private String paymentStatus;

	public String getPaymentStatus() {
		return paymentStatus;
	}
}
