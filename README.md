# HIM (Health Indoor Machine) : HIM 으로 💪 힘 기르세요!
운동 포즈 인식으로 꾸준한 운동 습관을 기르는 헬스 웹 서비스
- 챌린지로 꾸준히 운동 습관을 기를 수 있습니다.
- 다양한 게임 테마로 운동을 재밌게 즐길 수 있습니다.

[⚡ HIM 시작하기](http://him-frontend-build.s3-website.ap-northeast-2.amazonaws.com/)

[🎬  시연 영상 URL](https://1drv.ms/v/s!Aq28gsbN9UsSjpZ-rQTwueVxM0vJaA?e=xHKsDD)
## 📜 프로젝트 개요
HIM은 운동 포즈 인식 기술을 활용하여 집 안에서도 꾸준히 운동을 실천하고, 재미있게 즐길 수 있도록 돕는 헬스 웹 서비스입니다.
- **챌린지** : 꾸준한 운동 습관 형성을 위한 다양한 도전 과제를 제공합니다.
- **게임** : 재미 요소를 결합하여 운동을 즐겁게 만들며, 몰입감을 높입니다.
## 🙋 팀원 소개
| 강보영                                                                                                                            | 윤정석                                            |
|--------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------|
| <img src='https://avatars.githubusercontent.com/u/102157897?s=400&u=46e631a914c83f2907e95766ba8abe3bca72e699&v=4' width='150'> | <img src='https://avatars.githubusercontent.com/u/108466918?v=4' width='150'/>                               |
| [@BOYOUNG-KANG](https://github.com/BOYOUNG-KANG)                                                                                     | [@IcarusToSun](https://github.com/IcarusToSun) |

## 📅 개발 기간
2024.10.19 ~ 2024.11.27
## 🛠️ 개발 환경
#### Back-end
- Java 17
- Spring Framework
- Spring Boot
- MySQL
- MyBatis
#### Front-end
- Vue.js
#### IDE
- IntelliJ
- Visual Studio Code
#### Game graphic
- Pixi.js
#### Pose detection model
- Teachable Machine
#### CI/CD
- AWS EC2
- AWS S3
- AWS Code Deploy
- Github Action
#### Communication
- GitHub

## 설계서
### ERD 설계
![HIM_ERD](https://github.com/user-attachments/assets/0dae70bf-709a-4abd-a357-eab2ecbc1964)

### 요구사항 정의서
![HIM_요구1](https://github.com/user-attachments/assets/a5db294e-9b9c-4600-8b9e-e3f5ad364122)
![HIM_요구2](https://github.com/user-attachments/assets/3b7fbc11-e5b4-4eec-ae9e-c1f651e19754)

### API 명세
![HIM_api1](https://github.com/user-attachments/assets/c82c82bf-b732-4b87-8172-2b5f265b3036)
![HIM_api2](https://github.com/user-attachments/assets/c93964aa-9751-4c67-91bd-8122f745e737)

### Flow Chart
<img src="https://github.com/user-attachments/assets/a1da8db5-f9dc-4b8d-9a7b-0e2d49113b31" alt="HIM_flow_마이" width="300">
<img src="https://github.com/user-attachments/assets/6e004f44-a0f2-4c68-a624-f736856a3431" alt="HIM_flow_챌린지" width="300">
<img src="https://github.com/user-attachments/assets/f3dd2cf6-1210-4e2e-bfc9-378393cbb1f0" alt="HIM_flow_게임" width="300">

### 시스템 아키텍처
![HIM_sys](https://github.com/user-attachments/assets/63a83b1a-d87d-4192-8d6c-59b6fbca5b92)

### 유스케이스 다이어그램
![HIM_use](https://github.com/user-attachments/assets/53567c2a-aa84-4695-b516-25c4a201fb33)

## 서비스 화면
- 시작 화면
  ![HIM_시작](https://github.com/user-attachments/assets/397cdb3b-a259-4ce7-9303-b69e6e252214)
- 로그인 / 회원가입
  ![HIM_로그인](https://github.com/user-attachments/assets/7390366b-19ba-4242-b87e-c57b7cf93781)
  ![HIM_회원가입](https://github.com/user-attachments/assets/d5cf1359-cbe5-4e20-9e56-f5013c1f44e0)
- 홈 화면
  ![HIM_홈페이지](https://github.com/user-attachments/assets/7f5882c1-15bf-4adf-ba01-1175bd31b241)
  ![HIM_홈페이지2](https://github.com/user-attachments/assets/4d23a032-fd67-40d1-9325-f8a988beb96f)
  ![HIM_튜토리얼](https://github.com/user-attachments/assets/62def541-3bca-424a-8cf9-612cc54dac6d)
  ![HIM_티어정보](https://github.com/user-attachments/assets/a389e291-f68b-4b19-802b-158188366a08)
- 자세연습 화면
  ![HIM_push자세](https://github.com/user-attachments/assets/23f182f1-e20c-429b-bcc6-18ba2c4b474d)
  ![HIM_squat자세](https://github.com/user-attachments/assets/e6dd7b31-b691-4bf5-acea-7c5178354523)
- 챌린지 선택 화면
  ![HIM_챌린지선택](https://github.com/user-attachments/assets/d6fe37cb-b4ae-402e-b806-35f37665123a)
  ![HIM_챌린지선택2](https://github.com/user-attachments/assets/a86d64ee-669d-4c80-a7e2-81b6c8e7887e)
- 챌린지 화면
  ![HIM_챌린지](https://github.com/user-attachments/assets/66d37e38-51b9-47a8-a95a-78cb1f82b7dd)
  ![HIM_챌린지종료](https://github.com/user-attachments/assets/c24d83e5-0516-4560-b38c-4659fc5b1f0f)

- 게임 선택 화면
  ![HIM_게임선택](https://github.com/user-attachments/assets/609d763d-ad67-4bd3-b86b-9c4e0be67e81)

- 게임 테마 (오리, 폭탄, fly)
  ![HIM_오리](https://github.com/user-attachments/assets/b8ca77ef-941a-4293-ad70-3f4d35487ad1)
  ![HIM_폭탄](https://github.com/user-attachments/assets/3d879342-35f0-4321-ba72-998bb685aad8)
  ![HIM_fly](https://github.com/user-attachments/assets/1aaa6580-0b0f-484f-96d5-b9a143a03883)

- 로딩중 / 성공 / 실패 화면
  ![HIM_로딩중](https://github.com/user-attachments/assets/ddb05e29-4cf5-45ec-b08c-21d9aadda559)
  ![HIM_성공](https://github.com/user-attachments/assets/133b247c-df59-4c42-bb34-a327a4cc106b)
  ![HIM_실패](https://github.com/user-attachments/assets/4b60c0c7-3ef2-4538-8f94-01eca9a7ce1e)
