package com.wondersgroup.healthcloud.api.http.dto.login;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class LoginConfigure {

    @Value("${login.textmessage.verification}")
    private boolean isSMSVerification;

    @Value("${super.administrator.loginname}")
    private String superLoginName;

    @Value("${super.administrator.phone}")
    private String superPhone;

    @Value("${nonsuper.internal.access.time.from}")
    private String timeFrom;

    @Value("${nonsuper.internal.access.time.to}")
    private String timeTo;
}
