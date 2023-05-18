package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.Model.Mail;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;

public interface MailService {
    void sendMail(Mail mail);

    void sendOrderMail(OrderBillEntity orderBill);
}
