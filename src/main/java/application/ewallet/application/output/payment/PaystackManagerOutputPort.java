package application.ewallet.application.output.payment;

import application.ewallet.domain.exceptions.IdentityException;
import application.ewallet.infrastructure.adapters.input.data.request.PaystackRequest;
import application.ewallet.infrastructure.adapters.input.data.responses.PaystackResponse;
import application.ewallet.infrastructure.adapters.input.data.responses.PaystackVerificationResponse;

public interface PaystackManagerOutputPort {

    PaystackResponse initialisePayment(PaystackRequest paystackRequest) throws IdentityException;
    PaystackVerificationResponse verifyPayment(String reference) throws IdentityException;
}
