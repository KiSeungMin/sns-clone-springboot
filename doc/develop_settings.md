# Develop Settings

windows에서 지원하는 wsl을 사용하여 리눅스 환경에서 진행하며, 리눅스 내에서 docker를 이용한 개발환경에서 진행할 것이다.

***

## 0. 시작하기전에

구글링은 만능이지만 설치 관련해서는 가능하면 공식 documentation에 해당하는 문서를 찾자. 옛날 자료들은 그 당시에만 의미가 있는 것이 많다.

***

## 1. WSL 설치

microsoft 공식 사이트

- https://docs.microsoft.com/ko-kr/windows/wsl/install

따라서 하면 된다. 정리하면 다음과 같다. (Powershell)
```powershell
wsl --set-version 2 # wsl 버전을 wsl2로 한다.  
wsl --list --online # 다운 받을 수 있는 wsl 리스트 확인  
wsl --install -d Ubuntu-20.04 # 실제 설치 (Ubuntu 20.04로 진행)
```

설치 후 계정 이름 및 암호까지 설정

***

## 2. Docker 설정

docker 공식 사이트

- https://www.docker.com/get-started

Docker Desktop 설치

설치 후 다음 microsoft 공식 문서 참고하여 설정 세팅

- https://docs.microsoft.com/ko-kr/windows/wsl/tutorials/wsl-containers

**WSL INTERGRATION** 설정을 해두자

***

## 3. VS Code로 실행

powershell에서
```powershell
wsl # wsl 시작 (default가 시작, 여러개 있다면 default 옵션 바꿔주자(구글링))
cd ~ # 홈 디렉토리로 이동
#...
# git 설정 하고 프로젝트 clone 하자
#...
cd SNS_Project
code . # vs code 시작
```

코드 위치를 어디 두든 상관은 없지만.. 좀 더 편하게 하기 위해서 위처럼 했다. (경로가 짧아서) 어디서 시작하든 본인 마음

***

## TODO
- [ ] 