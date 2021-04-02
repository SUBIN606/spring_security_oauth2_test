# Spring Security OAuth2
## OAuth
인증을 위한 오픈 스탠다드 프로토콜
> OAuth를 사용하면 구글, 페이스북, 트위터 등의 계정을 이용해 다른 웹 사이트에 로그인할 수 있다.

## OAuth2.0
OAuth 프로토콜의 버전 2. 이전의 버전(1.0)보다 인증 절차가 단순화되었다.   
OAuth2.0에서는 보안 강화를 위해 AccessToken의 만료 시점을 설정할 수 있도록 한다.
### OAuth2.0의 네 가지 역할
* **Resource Owner**: 자원의 소유자. 접근 권한을 필요로 함.
* **Resource Server**: 자원을 호스팅하는 서버
* **Client**: 리소스 서버에 접근을 요청하는 애플리케이션
* **Authorization Server**: 클라이언트에게 `access_token`을 발급하는 서버


## 인증(Authentication)과 인가(Authorization)
* 인증은 신원을 확인하는 과정 
* 인가는 승인과 동일. 즉 엑세스 권한 여부를 확인하여 제한된 리소스에 접근할 수 있음

## 블로그 포스트
[OAuth2 포스트 목록](https://gaemi606.tistory.com/search/spring%20security%20oauth2)
