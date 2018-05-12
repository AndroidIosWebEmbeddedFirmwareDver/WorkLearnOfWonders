package com.wondersgroup.healthcloud.utils.security;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p>
 * Created by zhangzhixiu on 02/11/2016.
 */
public class RSAKey {

    public static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoHgm9p0TUE/Ss1QBLUi/agj18CXflkeefZIh6d/ZhHANDgvsvpXehmU3yvvNLD4t3i3UkhBlA7UKINtxJW/CzycoOPRcRa7wh+3rd237I" +
            "+FHtbq48IkJQ5uCqtGB2mt9uRES3VK9bSqQL8qIxhpR2sdFwojBgBQt0L6nskFSPv90ldJXTHlqZDanWyBsdT+CJy8ZBddAN6kNWEFmEclC8Xp+SECV0Cys0m760GVTh9pJ7zxJyVgAMMlhl1wRvP4LnBqAO+xtI1OxaXv/5Xre665u9YmxEd" +
            "+Wbz/MWy8Gzb78CCdq3XB/og9Hhl+oJTjwYwYiCMmYrNHtV6JtdDRqLQIDAQAB";
    public static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgeCb2nRNQT9KzVAEtSL9qCPXwJd+WR559kiHp39mEcA0OC+y+ld6GZTfK" +
            "+80sPi3eLdSSEGUDtQog23Elb8LPJyg49FxFrvCH7et3bfsj4Ue1urjwiQlDm4Kq0YHaa325ERLdUr1tKpAvyojGGlHax0XCiMGAFC3QvqeyQVI" +
            "+/3SV0ldMeWpkNqdbIGx1P4InLxkF10A3qQ1YQWYRyULxen5IQJXQLKzSbvrQZVOH2knvPEnJWAAwyWGXXBG8/gucGoA77G0jU7Fpe//let7rrm71ibER35ZvP8xbLwbNvvwIJ2rdcH" +
            "+iD0eGX6glOPBjBiIIyZis0e1Xom10NGotAgMBAAECggEBAIrhcldd+qk3bJPn0Fzw6TlMKA5/uNLHiVQEdXWEVy1YDPRrPap06vJGB9OVuizgY7I22BLSPZrHxMxLH9HWuDOxIA6q7nEgxuvd7jye4wxanUWbJRSIhYwNuzk7ubbf8R" +
            "+Kt1j1kxw4CjJSh61segtvC+EhmtbOoBhZ6alM/3GlJdRvASZEkNyCEWcCWZiWNhz1vYcTFe4RlQS4qImD/Vdm2kvy5Hn4EInN9tdTanO8vGdGrz+qKQ73l4EVVVZDOMxgKeYJSoRcgVW+LGDNLwcONZcrxd6i5iSNNzWKoweweXBYshS" +
            "+Ohb4nzWR71c5RIlnEyghCK6PqG0f7hnHfcECgYEA2tIJ6xPwIjQXUl+DN5t8onY6R/tR568ePi59HWBJbQDSBlbwSUGlfr9+r86ocKi8r5TKrwbYU0c2zH+kdls65LrNMNzNlLpS1uKFizVWPqXzndlFODo6XLSnlpGr+Gi+KJoui9I5hxg56Lt" +
            "+bp4ANjnY9gBRbmN4GKExT41QHrcCgYEAu7wIvZUpWV7CeUQHNuU+gryse427TbItD59FIHXLMlK0zzy32kFrL4KEQeJHPa9Zd9ET/Dh0XuExWkVe+I7uDSxvzNoozvXvIqjC4ppdBrm38YKpS/NDkfD089fNU35DL5JJTqBog+OC8h41w" +
            "/57HIE5wLnhmDLaxjZLuzaXWjsCgYAWK5G1r59017JFNkglKivGihP9lwkFYEjAJBWpFEhcMukUm1pdEbmizeQYC+glAfOcFocylI72YRC8R3JyS2v8ILEJclWh6/7YprrBD/ZdApNcOtc4iy2+ls8kAj5aPMKHepecswABIAM" +
            "+AUEeFAQRemrxAbc+Z/WTCUHlvXuQoQKBgQCFbOSBaSvUGqb47MJ7334ICPRFc7v1QncILVhB87YQ8/sRdsPsWKZHQ+mfsaSUULWzlGMx1SXP8RjUYY1X9q11q2liHIL4ZI4rAepQhwkqqH" +
            "/1I7oTAre5V5mgXHXPq2MwwjVbrmHZqq78SUBJJep1ndpgATw2oZ4fVaOJUQlM1QKBgFlYnuZc51PbCmn4tFDkVwPkC5Ew/znhSJRQy9B3k+t9I0prCrq2ewwgVSGnp58Z8uNsXfmkpYgWpdDOD/XB2PZMUI5GslMN8uSCUBJKanJjyF" +
            "/vph41z2nFhZyeZYA9NkB9VMN2YtS5k0M2eJvRiiDf9RQ2Z272Kzr9hvXs/ZDZ";


    public static String adminPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmtCx3tfNiVd3Hsaxs+ohi/kbFhCotz53Haa" +
            "+tw61U7tjM6vYgBlrgBX3heFYU8yi5u0h09UtsMVjjhEiMNzfRLOYxu9tqg1u4IgqZhijXQHRLckpjYsmAO7SDEhiyXHuP41X8hTU19wPDGgi5L9wS4ByQIiEt151+7u+yKEFMcD9u23RCtbiIej" +
            "+QMlq3mwhGnRVbZoBfGhKQqbzJjL2J5ZNrry4xxJrrTxiLoWSRvuICrthdLQOQ7Mh5Z+b3bYwdBWiJoJ7eta5dTHAQzp4MTM/HXtIDj/C8+UKRB9UjxvnJCFC7i6dIEfKqTfDg1uWKhVX/LeODmpsiLvxnQrBOwIDAQAB";
    public static String adminPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCa0LHe182JV3cexrGz6iGL" +
            "+RsWEKi3Pncdpr63DrVTu2Mzq9iAGWuAFfeF4VhTzKLm7SHT1S2wxWOOESIw3N9Es5jG722qDW7giCpmGKNdAdEtySmNiyYA7tIMSGLJce4" +
            "/jVfyFNTX3A8MaCLkv3BLgHJAiIS3XnX7u77IoQUxwP27bdEK1uIh6P5AyWrebCEadFVtmgF8aEpCpvMmMvYnlk2uvLjHEmutPGIuhZJG+4gKu2F0tA5DsyHln5vdtjB0FaImgnt61rl1McBDOngxMz8de0gOP8Lz5QpEH1SPG" +
            "+ckIULuLp0gR8qpN8ODW5YqFVf8t44OamyIu/GdCsE7AgMBAAECggEAS8sCWdwDJ0sn6WxTUYa9l76EMUBEqTmurViWJB7STUT/YyutmcFYLOQUQ3o4eQZFdpL/raq/bLymacYfnBZ2RDc9A5/ya3" +
            "+aGdjwEP3c9MrP8v96FW7bUzeYpCTIIGFjx79WIBFsxqVYxLUy6BSFUKF4zflIKFHZpz2aQLT8SweynXkOBRsToviSgO/CwzLI9NXDOaWYPUFsMgcdsEeCSTc0vNskP1AamBFyyB6yJ/JMi2hMtLZjFzvXl93q/wn+56WA2xos3p1r2kPcUe1" +
            "/53U/qs6hPmea5F3DPuFU71utpkL9romBG4jkDpecbqOeJru6I/zkOVCqAYp6NfHsaQKBgQDUMAkYeBDHJhfnErScHAbbzRkAYNqetfXI3+gn/ZalsHuUS4+TkPATJrHF6Kly8y5lKXM7SD7jEWuqKctaBJqCRReHiqoxXdAWS/R/S" +
            "/ZTcTfAy9l2O0ggCtgtZDK0tG3EUs/R8V9b+ksMb/ekT+2BiaUxiLgoRiiGdKeBnY2zZwKBgQC6yAbrD9tY18x7pSOQGBl8nUI/EqlNKWtJ+jBDMEro92RF7v93QwqDi8BTMMKUe6bdsK0aot7TXmT/p622c2DfgOA2GVHAR" +
            "/tDiaAjcCFt2iK9fJRkET49C4+zzXsOOj7W7oP0pEBXSAJImqyiKbxAKAHHx2RR35y+LL8XFjgTDQKBgQDJMHKvXMiry5bGZpoRmxF1v9aA4VTpP6a4vHUn1e7NU7UZOzv7s2WvAvV0rdJdR+8pxE" +
            "+aWjommxH22oIR7GOEOXbB8dzFyinPul0thh0dqTAwAhlDXZpZp26qO2NIq/uYNt0ON0svrvcR5+hrR83vEVAs03Gp3jv2oQ1ifL0q6QKBgCoI7C2ogufKoRVOg+Ng2ROHXKpPK9TWtnOOn5/vVVx6mJF4iPuxFcOnBuBx" +
            "/8hkz5fitAJjlxwgiUBT3y9JZgrGuS7ToedRC8YWSfjPumY7ikf0qnyeuNAKMwv2+XOpPg0m3oGHGYfLB+CJFsLj+57Aoi3wvK4EUgUmktbCRFpdAoGBAM2rk/6lGobJGvF3wkmC27ooOeE6CyDQo+CRrK4f" +
            "/pmE2TdI4YPxoqFWCkDEOd5ookAw2J3IRu4hTN9mrRfK9wNKVTwwkBgNOjX5L8IAnMH9CNYnMPqStsYLM57oDLUiI0+jgAJdpLqaql0vKUeZCj14ziUGM1slBnxIoXgvCmur";
}
