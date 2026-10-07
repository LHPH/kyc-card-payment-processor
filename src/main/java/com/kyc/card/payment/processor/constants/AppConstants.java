package com.kyc.card.payment.processor.constants;

public final class AppConstants {

    public static final String ERROR_MESSAGE_001 = "001";
    public static final String ERROR_MESSAGE_002 = "002";
    public static final String ERROR_MESSAGE_003 = "003";
    public static final String ERROR_MESSAGE_004 = "004";
    public static final String ERROR_MESSAGE_005 = "005";

    public static final int CARD_PAYMENT_LAYOUT_SIZE = 8;
    public static final String CARD_PAYMENT_LAYOUT_REGEX = "\\|";
    public static final String CARD_PAYMENT_LAYOUT_DATE_FORMAT = "yyyyMMddHHmmss";

    public static final int CARD_PAYMENT_LAYOUT_FOLIO = 0;
    public static final int CARD_PAYMENT_LAYOUT_ACCOUNT = 1;
    public static final int CARD_PAYMENT_LAYOUT_METHOD = 2;
    public static final int CARD_PAYMENT_LAYOUT_AMOUNT = 3;
    public static final int CARD_PAYMENT_LAYOUT_MOTIVE = 4;
    public static final int CARD_PAYMENT_LAYOUT_OFFICE= 5;
    public static final int CARD_PAYMENT_LAYOUT_CUSTOMER = 6;
    public static final int CARD_PAYMENT_LAYOUT_DATE = 7;

    public static final String CREDIT_CARD_PAYMENT_OPERATION_INQUIRY = "1C";
    public static final String CREDIT_CARD_PAYMENT_OPERATION_PAYMENT = "3C";
    public static final String DEBIT_CARD_PAYMENT_OPERATION_INQUIRY = "1D";
    public static final String DEBIT_CARD_PAYMENT_OPERATION_PAYMENT = "3D";


    private AppConstants() {}
}
