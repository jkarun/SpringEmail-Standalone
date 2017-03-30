package com.arun.service;

import java.util.HashMap;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.arun.model.ProductOrder;

import freemarker.template.Configuration;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	Configuration freemarkerConfiguration;

	public void sendEmail(Object object) {
		ProductOrder order = (ProductOrder) object;
		try {
			MimeMessagePreparator mime = getMessagePreparator(order);
			mailSender.send(mime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MimeMessagePreparator getMessagePreparator(final ProductOrder order) {
		MimeMessagePreparator prep = new MimeMessagePreparator() {
			public void prepare(MimeMessage mail) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(mail, true);
				helper.setFrom("jkarun63@gmail.com");
				helper.setTo(order.getCustomerInfo().getEmail());

				// we are using freemarker template.
				/*
				 * helper.setText("Dear " + order.getCustomerInfo().getName() +
				 * ", thank you for placing order. Your order id is " +
				 * order.getOrderId() + ". Item name is " +
				 * order.getProductName() + ". status : " + order.getStatus());
				 * helper.setSubject("Your order on Demoapp");
				 */

				HashMap<String, Object> model = new HashMap<String, Object>();
				model.put("order", order);

				String text = getFreemakerTemplate(model);// Use Freemarker
				System.out.println("Template content : " + text);

				// use the true flag to indicate you need a multipart message
				helper.setText(text, true);
				// Add a resource as an attachment
				helper.addAttachment("Attachment", new ClassPathResource(
						"/files/Config_EHCache.pdf"));
			}
		};

		return prep;

	}

	public String getFreemakerTemplate(HashMap<String, Object> model) {
		StringBuffer buf = new StringBuffer();
		try {
			buf.append(FreeMarkerTemplateUtils.processTemplateIntoString(
					freemarkerConfiguration.getTemplate("fm_template.txt"), model));
			return buf.toString();
		} catch (Exception e) {
			System.err
					.println("Exception occured while processing fmtemplate: "
							+ e.getMessage());
		}
		return "";
	}
}
