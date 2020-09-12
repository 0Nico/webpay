package com.paymybuddy.webpay.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.paymybuddy.webpay.WebpayApplication;

@SpringBootTest(classes = WebpayApplication.class)
@Sql({"../scripts-before.sql","../webpay.sql"})
public abstract class AbstractTest {

}
