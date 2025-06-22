### 🎯 프로젝트 개요
이 프로젝트는 [팀 프로젝트(씨네픽)](https://github.com/likelion-cinepick/Back#)를 기반으로 하여, 개인적으로 리팩토링하고 외부 API를 연동해 기능을 확장한 버전입니다.

- 팀 프로젝트 당시 제약으로 구현하지 못한 외부 API 연동 기능 추가
- 코드 구조 개선 및 불필요한 로직 정리

# Cinepick 🎬

**MBTI와 개인 취향 기반 맞춤형 영화 + 액티비티 추천 웹 서비스**

---

## 📌 프로젝트 소개

**Cinepick**은 사용자의 MBTI 성격 유형과 개인 취향을 바탕으로,  
여운을 깊이 즐길 수 있는 **커스텀 영화 추천 + 감정 확장 액티비티**까지 제안하는 웹 서비스입니다.

- 감상 전에는 분위기를 돋워줄 음악이나 이미지,
- 감상 중에는 몰입을 돕는 팁,
- 감상 후에는 감정 정리를 위한 글쓰기나 산책 등 행동을 함께 추천합니다.

크리스마스 시즌에 빠듯한 일정 속에서 '산타파이브' 프로젝트에서 영감을 받아 시작했으며,  
이후 코드 리팩터링, 권한 제어 로직 개선 같은 백엔드 기능들을 중심으로 지속적으로 보완했습니다.

---

## ✅ 주요 기능

- **MBTI 기반 영화 추천 알고리즘**
- **감정 순간별 액티비티 추천** (전 · 중 · 후)
- **후기 작성 및 공유 기능**
- **즐겨찾기 및 다시 보기용 '나만의 영화 목록'**

---

## 📌 기술 스택 & 실행 환경

| 구분         | 기술/도구                               |
|--------------|----------------------------------------|
| Backend      | Spring Boot (REST API, 인증/인가, 추천 로직, 이미지 업로드) |
| Database     | MySQL (사용자, 영화, 후기, 추천 결과 등) |
| Infrastructure | AWS EC2 (Ubuntu 기반 배포)             |
| Containerization  | Docker (환경 일관성 확보)             |
| CI/CD        | GitHub Actions (자동 빌드 및 배포)       |

---

## 🗂 폴더 구조
```
📂Cinepick/
│ └─ src/
│  └─ main/
│   ├─ java/
│   │ └─ com.example.cinepick_be
│   │   ├─ controller/        
│   │   ├─ dto/
│   │   ├─ service/           
│   │   ├─ repository/        
│   │   ├─ entity/            
│   │   └─ config/    
│   └─ resources/
│     └─ application.yml
├─ Dockerfile
├─ .github/
│ └─ workflows/
│ └─ deploy.yml
└─ README.md
```

---

## 🧭 로컬 개발 환경 설정

### ✅ 사전 준비

- Java 17 이상
- MySQL 서버
- TMDB API KEY 준비
- JWT secrets 준비

### 백엔드

```bash
cd Cinepick
./gradlew clean build -x test
java -jar build/libs/Cinepick-0.0.1-SNAPSHOT.jar
```

