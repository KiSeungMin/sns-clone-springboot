# Docker

docker 설명 잘해둔 사이트
- https://subicura.com/2017/01/19/docker-guide-for-beginners-1.html

설치는 윈도우에 직접 해서 연동했기 때문에 wsl에 따로 설치 안해줘도 된다. (develop_settings.md 참고)



***

## 1. 이미지 pull

docker hub에서 공식 이미지들을 다운받아 base로 사용할 것이다.

- https://hub.docker.com/

받고 싶은 이미지를 찾아 복사하기 버튼을 눌러 바로 사용하면 이미지를 다운받을 수 있다.
또는 

- docker pull [이미지 이름]:[태그 이름]

docker images 로 다운받은 이미지를 확인 할 수 있다.,

***

## 2. 컨테이너 생성

> docker run [OPTIONS] IMAGE[:TAG|@DIGEST] [COMMAND] [ARG...]

에제 

> docker run -d -it --name java -v /home/ckiruser:/ckiruser ubuntu:20.04

|옵션|설명|
|-|-|
|-d|백그라운드 모드|
|-p|포트포워딩|
|-v|디렉토리 연결(마운트)|
|-name|컨테이너 이름 설정, default는 랜덤|
|-rm|컨테이너 종료시 자동 삭제|
|-it|그냥 하자, 입력 하려면 필수|

그 외에도 수많은 옵션들이 있지만 그나마 많이 쓰는 것들은 위의 것들이다. 그 중에서도

**-d -v -name -it** 

를 기본적으로 쓰게 될 것이다.

## 3. 컨테이너 접속

-d 옵션을 사용하지 않았다면 바로 접속이 되었을테지만 우리는 백그라운드에서 돌리고 필요할때마다 컨테이너에 접속하여 포그라운드로 나타내고 싶다. 접속하는 방식은 다음과 같다.

> docker exec -it [컨테이너 이름] bash

컨테이너에 bash를 실행하라는 의미이다. 물론 실제로는 bash를 안쓰는 컨테이너도 있을수 있기에 다 다르겠지만 대부분의 상황에서는 적용 될 것이다.
그 컨테이너에서는 무엇을 하든 자유이다!! 물론 세팅을 해줘야 된다.

## TODO
- [x] 도커 이미지 및 docker hub
- [x] 컨테이너 생성 및 실행
- [ ] 이미지 빌드, Dockerfile
- [ ] docker-compose
- [ ] docker로 배포
