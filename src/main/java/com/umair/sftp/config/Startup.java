package com.umair.sftp.config;


import com.umair.sftp.constants.AppConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.aopalliance.aop.Advice;
import org.springframework.integration.aop.AbstractMessageSourceAdvice;
import org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.Message;


/**
 *
 * @author m.umair
 */
@Configuration
public class Startup {

    @Bean
    public Advice scanRemoteDirectory(){
        AbstractMessageSourceAdvice advice = new AbstractMessageSourceAdvice() {
            @Override
            public Message<?> afterReceive(Message<?> message, MessageSource<?> messageSource) {
                if (message == null){
                    //
                }
                return message;
            }
        };
        return advice;
    }

    @Bean
    public Advice deleteFileAdvice() {

        ExpressionEvaluatingRequestHandlerAdvice advice = new ExpressionEvaluatingRequestHandlerAdvice();
        advice.setOnSuccessExpressionString(AppConstants.SUCCESS_EXPRESSION);
        advice.setTrapException(true);
        return advice;
    }


    @Bean
    public Advice logAdvice() {
        return new ExpressionEvaluatingRequestHandlerAdvice();
    }



}
