# TLS
## Reference
> https://b.luavis.kr/server/tls-101
## Difinition
> a protocol that send and recieve to encryption data in internet.
> TLS는 크게 2단계로 구분된다. protocol 버전과 암호화할 키를 주고 받는 *handshake*와 실제 *application이 동작하는 단계*다.
>> handshake 단계에서는 모든 내용이 평문으로 주고 받게 된다.( TLS 1.3 부터는 handshake 중에도 암호화가 된다)
>> application의 내용이 주고 받아지는 부분에서는 대칭키 암호화가 이루어진다.
## HMAC
> 메시지의 손상 여부를 파악하기 위해 사용되는 기술은 MAC(Message Authenrication Code)이다
> 그 중에 TLS는 HMAC(Hash MAC)을 사용한다.
>> 'A'라는 메시지를 보낸다고 하였을 때, 송신 peer는 A를 수신 peer와 함의한 Hash함수를 이용하여 hash값을 만들고 메시지 'A'에 hash를 덧붙힌 A를 보낸다. 이를 수신한 peer는 A와 Hash를 분리하고 A의 hash 값을 구한 뒤 받은 값과 비교하여 검증한다.
## Digital signature
> 신뢰 받는 기관 ( 이 글에서는 Root CA로 호칭하겠다 )에서 내 문서가 사실임을 검증받고 다른 peer들도 이 사실이 사실임을 확인하게 하는 체계다.
> 일반적으로 Digital signature는 RSA 알고리즘을 이용한다. Root CA에서 요청자 문서의 hash값을 구하고 RSA private key로 암호화한다. 그리고 암호화한 값(signature)을 해당 문서 뒤에 첨부한다. 이 문서를 받은 peer는 서명한 Root CA public key로 복호화한 signature와 비교한다. 일치한다면 Root CA에서 signature된 문서라고 판단한다.

## Chain of trust

   ![](../images/ChainOfTrust.png)
 
