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
![HIM_ERD](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F226d42d0-9f21-4e62-b1df-3156f13d6c53%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-24_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.15.21.png/size/w=1360?exp=1732696253&sig=sprIylQdX5hB49cjNA0bDYco_EZ5LFvulQrLcT6r1qE)
### 요구사항 정의서
![HIM_요구1](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F5ff0bca9-be6e-4035-a9be-34b699144f3d%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.19.15.png/size/w=1360?exp=1732696342&sig=1063qG4uuVIHpkQbuNPz6urLqc3bHABrTlEm6UH2prs)
![HIM_요구2](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fd0a03841-f1ed-4970-9294-bb8b29070184%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.19.26.png/size/w=1360?exp=1732696384&sig=bzELYiy205i_Uv-8V9mlC8texiWyf6raeBtLvTL3K0k)
### API 명세

![HIM_api1](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F1b3ac1b9-d37a-41d2-aba0-91370b72c76f%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_6.57.08.png/size/w=1360?exp=1732702262&sig=r9AXck7hDPmmV7eZzZtoFf0G9b7NyXuB0PbpnC6SPRc)
![HIM_api2](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fd18b2e25-cfa1-4001-a85a-e8a91f911340%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_6.57.47.png/size/w=1360?exp=1732702270&sig=lFQPb06im4hOOZoN5e2G_d8Ymze-CXaF4MXrfhozCfo)
### Flow Chart
<img src="https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fcb488adf-1114-4023-99d2-3cc8a353834b%2F3.png/size/w=1170?exp=1732698539&sig=eF4ogc5xX1igwxuvJNy7ksyxObpaivtsZumj-eb2v5g" alt="HIM_flow_마이" width="300">
<img src="https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F064c39a2-06ba-49e5-88b0-95c43c24ae2e%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_1.56.20.png/size/w=990?exp=1732698564&sig=qaQrwav-6-o8sGDIPvN0XcLwyjXsF144Pwi9eCW9hNc" alt="HIM_flow_챌린지" width="300">
<img src="https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F614df3db-739a-4285-8fe2-0fad8fd85965%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_1.54.23.png/size/w=1150?exp=1732698562&sig=o1N_bhNWiLWRluFQYJF93CxHh9Z9HY3bf6k9KZ-oC_w" alt="HIM_flow_게임" width="300">

### 시스템 아키텍처
![HIM_sys](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fd40cb0ce-bf27-4b3c-9671-6ce2f2115cbe%2Fimage.png/size/w=1360?exp=1732698514&sig=RB1As35U_X-AwTSuVoi1-sNiI43W7MtVyzTG_c8JY30)

### 유스케이스 다이어그램
![HIM_use](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F4740804f-769c-4c8a-b348-df750a50b09d%2Fhim_usecase.drawio_(1).png/size/w=1280?exp=1732696529&sig=-S9tK_3_DeFP_Hdo49VuI3e53Hcbx_srYZ5LyFRdf8o)

## 서비스 화면
- 시작 화면
![HIM_시작](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F06231a2a-3ed5-4a02-bed1-93155e11a17c%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.40.10.png/size/w=1360?exp=1732697973&sig=ILckHIITABIJN1N1tASMxUMmY6sGWzDuDrVfw8oCPyQ)
- 로그인/회원가입 화면
![HIM_로그인](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F8e25caec-27e6-4af6-8f31-89ca1251b6a4%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.40.28.png/size/w=1360?exp=1732697982&sig=LsLvEFAfnuxK_wTDMcZY_PPQrCz_LxUgRacfVy4ZYIs)
![HIM_회원가입](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Ffebf6eae-fea9-4396-837d-b3f66aa2b6d3%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.40.57.png/size/w=1360?exp=1732697995&sig=yBuStWPqEsBzYCt2JNNCnhTkqY6_lsztbuoCcwyQucg)

- 홈 화면
![HIM_홈페이지](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F2fd9da7c-7f77-4f5a-82ae-ccaf8039b9d3%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.41.32.png/size/w=1360?exp=1732698136&sig=v0gWPizcAY6u5GqvVUwdLN0RSNs8Rcrla0YnF0SgiE0)
![HIM_튜토리얼](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F1cd8c409-4f1d-40a2-b3b1-1585e6876279%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.41.51.png/size/w=1360?exp=1732698144&sig=hPHGoxmjAMNCK1VF_4Iumnh2a5lEowDoQD2WpYKQV-w)
![HIM_티어정보](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fb3adf934-b566-4e0e-97e3-2551ae251fbf%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.42.04.png/size/w=1360?exp=1732698153&sig=SaiwPhX_mmeBQLRnpg_7Ev0YtSOK9CX0IbXYYy3LZk4)
- 자세연습 화면
![HIM_push자세](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F6c13bb64-0791-457b-b65f-7c21911f7ad0%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.42.47.png/size/w=1360?exp=1732698227&sig=i7AElSAw9SeqlEKtif3qIeoVi2Cg-MpU-dqsbaz5Nkw)
![HIM_squat자세](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F68ae9573-d052-43ec-9eb8-aed7629733f5%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.43.24.png/size/w=1360?exp=1732698230&sig=5yEW7LQyVnhLopSjt6CScpYqYFdBbqNw89tdlbrE8Ic)
- 챌린지 선택 화면
![HIM_챌린지선택](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fe0d664d9-e28d-4962-a2e7-028b09f1a1d4%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.44.00.png/size/w=1360?exp=1732698248&sig=nQBcsK-SYbmcVgue6O3nB_daSDDQ7lZ1IbU2QwPPsL8)
![HIM_챌린지선택2](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F4c0762d5-41bd-4aec-a5f3-df1924efdb60%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.44.32.png/size/w=1360?exp=1732698250&sig=rc5Fmyb9PXpqRfSWgAopP_6hfnQMorKNolxh1ZkbHu0)
- 챌린지 화면
![HIM_챌린지](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F89438291-0a11-4464-8571-3e8287fd275a%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.45.10.png/size/w=1360?exp=1732698253&sig=rWyKEy5M6KebTkcM7JTG905YJf0cYp6_MyADiwHPvzk)
![HIM_챌린지종료](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fc1618072-dbd4-4188-ab82-1bd610cfec3d%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.45.33.png/size/w=1360?exp=1732698262&sig=JaC_oAQrSGq4k2x2i7sJnih0b8N12uhbe2PxcWw-Z6w)
- 게임 선택 화면
![HIM_게임선택](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F927780e6-a919-4ca9-8f8f-01a98e7531ea%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.47.33.png/size/w=1360?exp=1732698325&sig=6975Jt-p8DVIUIu0s8fRDJWn5As-swRhQEYlFL87JMk)
- 게임 테마 (오리, 폭탄, fly)
![HIM_오리](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F27b26df1-a19c-47be-801e-0d1836d62bf9%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.56.11.png/size/w=1360?exp=1732698405&sig=tqHZulKGJKEAUzNTQLwv26jwBhct5m349iBh5L3WFEc)
![HIM_폭탄](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F6f8ee1ba-2ae2-40bb-bd90-a1cb3acc76ab%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.48.21.png/size/w=1360?exp=1732698411&sig=nV6YcQTFyoWw1R8yqgZLFJMWN8vDjZwbnoDIykf-oPk)
![HIM_fly](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F06b460af-e240-4c83-b90e-ed18e3f4f826%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.49.00.png/size/w=1360?exp=1732698413&sig=-_uRTI9t58o7Kwib7MhmYwgOtvxBbJG8jc5EzYX3ALY)
- 로딩중 / 성공 / 실패 화면 
![HIM_로딩중](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2F44836c98-2a07-4d99-b91e-4c9ce19ff3ad%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.44.53.png/size/w=1360?exp=1732698415&sig=yc_MmbV2fpLcyw6eaYvlSbSKpO8AUcA4KLvTV2u5Ul0)
![HIM_성공](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fd154b352-5d9a-4452-8690-d7e442e6717f%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.50.53.png/size/w=1360?exp=1732698417&sig=9fulVYKSBgfTPjz3TFo2vft9owSHcu3W219f2dooi8k)
![HIM_실패](https://img.notionusercontent.com/s3/prod-files-secure%2F04bdd53f-d388-47e1-a42c-aaa9b65be2b3%2Fa53c9431-a3d9-4340-9466-553b80d383a6%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2024-11-26_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_5.48.40.png/size/w=1360?exp=1732698419&sig=hjj44Vy_Ee8trZYlvWRr3IWHRwz9bGovZQo3KxEXGbQ)
