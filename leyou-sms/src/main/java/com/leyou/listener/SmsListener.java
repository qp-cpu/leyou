package com.leyou.listener;

import com.aliyuncs.exceptions.ClientException;
import com.leyou.config.SmsProperties;
import com.leyou.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.sms.queue",durable = "true"),
            exchange = @Exchange(value = "leyou.sms.exchange",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key ={"verifycode_sms"}
    ))
    public void sendSms(Map<String,String> map) throws ClientException {
        if(CollectionUtils.isEmpty( map ))
        {
            return;
        }
        String phone = map.get( "phone" );
        String code  = map.get( "code" );
         if(StringUtils.isNoneBlank( phone )&&StringUtils.isNoneBlank( code ))
         {
             this.smsUtils.sendSms( phone,code,this.smsProperties.getSignName(),this.smsProperties.getVerifyCodeTemplate() );
         }
    }
}
