package com.him.fpjt.him_backend.common.constants;

public class EmailTemplates {
    public static final String VERIFICATION_CODE_SUBJECT = "[HIM] 이메일 인증코드 안내";
    public static final String VERIFICATION_CODE_TEXT = """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        color: #333333;
                        line-height: 1.6;
                    }
                    .container {
                        max-width: 600px;
                        margin: 0 auto;
                        padding: 20px;
                        border: 1px solid #dddddd;
                        border-radius: 8px;
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                    }
                    .header {
                        font-size: 24px;
                        font-weight: bold;
                        color: #4CAF50;
                        text-align: center;
                        margin-bottom: 20px;
                    }
                    .code {
                        font-size: 32px;
                        font-weight: bold;
                        color: #333333;
                        text-align: center;
                        padding: 15px;
                        background-color: #f9f9f9;
                        border-radius: 8px;
                        margin: 20px 0;
                    }
                    .footer {
                        font-size: 14px;
                        color: #888888;
                        text-align: center;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        HIM 이메일 인증 코드
                    </div>
                    <p>안녕하세요!</p>
                    <p>아래 인증 코드를 사용하여 이메일 인증을 완료해 주세요:</p>
                    <div class="code">%s</div> <!-- 여기에 인증 코드 삽입 -->
                    <p>이 인증 코드는 15분간 유효합니다.</p>
                    <p>저희 서비스를 이용해 주셔서 감사합니다!</p>
                    <div class="footer">
                        <p>이 메일은 발신 전용 메일입니다. 문의 사항이 있을 경우 고객센터를 이용해 주세요.</p>
                    </div>
                </div>
            </body>
            </html>
            """;
}
