# SNS_Project

## Member
- 멤버(유저) 가입 및 로그인 기능
- 멤버끼리 팔로우(Follow) 기능

## Board
- 보드 업로드, 수정삭제 등 기능
- 보드 좋아요(Like) 기능

## Data
- 프로필 이미지 또는 보드 안에 들어갈 텍스트와 이미지 실제 저장 기능
- 간단한 파일 시스템 이용
- (미구현) AWS Storage 시스템 이용

## etc
- Home Controller로 entrypoint 생성
- Spring Security를 이용하여 로그인 기능 구현
- (미구현) OAuth2 지원
- (미구현) Docker-compose와 Nginx를 이용하여 Linux 서버로 배포
- (미구현) Elastic Search 이용하여 보드 찾기
- (미구현) 보드 추천 시스템 이용

## TODO  
- [x] Hashmap memory db & Simple login/signup page (baseline)   
- [x] Member Service/Controller/Repository Baseline   
- [x] Board Service/Controller/Repository Baseline
- [x] JPA
- [x] Spring Security
- [x] Like, Follow Implement
- [ ] Front, Thymeleaf, Bootstrap
- [ ] File Upload
- [ ] OAuth2