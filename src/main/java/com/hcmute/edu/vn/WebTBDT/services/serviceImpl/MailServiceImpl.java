package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.Model.Mail;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillDetailEntity;
import com.hcmute.edu.vn.WebTBDT.entities.OrderBillEntity;
import com.hcmute.edu.vn.WebTBDT.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendMail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom()));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void sendOrderMail(OrderBillEntity orderBill){
        Mail mail = new Mail();
        mail.setMailFrom("nguyentientaigoboi@gmail.com");
        mail.setMailTo(orderBill.getCustomer().getEmail());
        mail.setMailSubject("Web Bán hàng Điện Tử Online - Bạn đặt hàng thành công");
        String Content = "Tổng số tiền của đơn hàng là: " + orderBill.getTotalPrice() +"\n";
        int index = 1;
        for (OrderBillDetailEntity OID : orderBill.getOrderBillDetailEntityList()) {
            Content += index +". Tên sản phẩm: " +  OID.getProduct().getName() +"\n";
            index ++;
        }
        Content += "Sản phẩm được đặt bởi: " + orderBill.getCustomer().getName() + "\n";
        Content += "Sản phẩm được đặt vào ngày: " + orderBill.getOrderDate() + "\n";
        Content += "Sản phẩm được đưa tới địa chỉ: " + orderBill.getCustomer().getAddress()+ "\n";
        Content += "Số điện thoại nhận hàng: " + orderBill.getCustomer().getPhone()+ "\n";
        Content += "Web Bán hàng Điện Tử Online xin chân thành cảm ơn!";
        mail.setMailContent(Content);
        sendMail(mail);
    }
}
