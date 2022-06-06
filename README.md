# DoingDoing
## 프로젝트 설명

TODO 리스트 HTTP API

## 사용 기술

Spring, Mybatis, MySQL

이 기술을 선택한 이유.

전공 프로젝트로 하고 있는데 교수님께서 Spring Boot 와 JPA 를 정말 싫어하셔서 

일단 프로젝트는 Spring Framework 와 Mabatis 로 진행했습니다.

어느 정도 마무리 된 것 같아 현재 김영한님 인프런 강의 들으면서 Spring Boot 와 JPA 학습하고 있습니다.

## 기타

doing.parkh.xyz:8080

etc 폴더에 postman 에서 추출한 json 파일이랑, API 명세서 있습니다.

## 패키지를 나눈 기준

좋은 구조로 짜보고 싶어서 인터넷에서 여러 자료를 찾아보면서 설계 했습니다.

### domain

enity - DB 있는 그대로 가져오는 클래스 모음 

model - request, response 등 필요에 의해 만든 클래스 모음

### controller

사용자의 요청만 담고 있도록 최대한 간단하게 작성 했습니다.

### service

트랜잭션과 비즈니스 로직을 담아 뒀습니다.

### exception

service 에서 예외를 던지고 @RestControllerAdvice 를 이용해 받아서 예외 처리를 했습니다.

### mapper

mybatis 사용을 위한 인터페이스

### filter, interceptor

이 부분은 있는 코드 수정해서 쓴거라 제대로 설명은 못드리겠습니다..!

어떻게 흘러가는지 이해는 했고 필터와 인터셉터를 쓰는 이유, 둘이 차이가 있다는 것 정도까지만 알고 있습니다.

### Test 

테스트 코드를 mapper 까지만 작성하고, service 계층은 따로 controller 만들어서 테스트 해봤습니다. 

테스트 코드도 관련된 책 읽고 있습니다.