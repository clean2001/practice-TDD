package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
//    @Spy
    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);

        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository); // 2개의 Mock객체를 가지고 있는 메일 서비스 완성

//        doReturn(true)
//                .when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()));

        // Mock 객체에 아무것도 지정하지 않으면 에러가 나는 것이 아니라, 기본값을 리턴해준다.
        Mockito.when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true); // true를 리턴하도록 설정하는거구나.

        BDDMockito.given(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        // 몇번 호출됐는지 횟수를 검증할 수도 있다.
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));

        // when
        // 이 안에서 mailSendClient.sendMail을 호출
        boolean result = mailService.sendMail("",  "", "", "");

        // then
        assertThat(result).isTrue();

    }
}