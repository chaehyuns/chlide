## 자체 이미지 라이브러리, Childe 🌉

### Childe 라이브러리

- Childe: Glide의 설계에서 영감을 받아 chaehyun + glide
- URL로부터 이미지 로드 및 캐싱을 지원하는 이미지 로딩 라이브러리
- LRU 알고리즘 기반 메모리 및 디스크 캐시 직접 설계 및 구현.
- API 최초 요청 후, 캐시 데이터로 이미지 로드.
- 안드로이드 컴포즈 타겟 → 안드로이드 모듈 생성

### 로직

![로직](https://github.com/user-attachments/assets/4c807e26-4610-43c0-8e27-37ffae5dfb94)

### 주요 기능

**이미지 로딩**

- URL로부터 이미지를 다운로드하여 표시
- 네트워크와 캐시를 효율적으로 활용하여 빠르고 안정적인 이미지 로딩 제공

**캐싱**

- LRU 알고리즘 기반의 메모리와 디스크 캐싱을 직접 설계 및 구현
- 메모리 캐시: LinkedHashMap을 활용한 최근 사용 이미지를 메모리에 저장
- 디스크 캐시: 디스크에 이미지 파일 저장 및 관리

**BitmapPool**

- BitmapPool을 통해 불필요한 비트맵 메모리 할당 감소
- 메모리 효율 최적화와 GC 호출 최소화

**이미지 변환 옵션**

- Grayscale 변환: 이미지를 흑백으로 변환
- 확장 가능성: Blur, 회전 등 추가 변환도 쉽게 구현 가능
- 다양한 ImageContentScale을 제공하여 사용성 증가
``` kotlin
sealed class ImageTransform {
    data object Original : ImageTransform()
    data object Grayscale : ImageTransform()
}
```
``` kotlin
sealed class ImageContentScale {
    data object Crop : ImageContentScale()
    data object Fit : ImageContentScale()
    data object FillBounds : ImageContentScale()
    data object Inside : ImageContentScale()
}
```

**다양한 상태 처리**

- error 와 loading 상태를 쉽게 처리할 수 있는 컴포저블 제공
- 이미지 로딩 중에는 로딩 화면을, 에러 발생 시 에러 화면을 표시

**비동기 네트워크 처리**

- 이미지 로딩과 디코딩은 Coroutine을 활용하여 비동기로 처리
- UI가 멈추지 않도록 설계

**이미지 라이브러리 테스트 코드 구현**

- CacheManagerTest, MemoryCacheTest, DiskCacheTest

### Compose와의 통합

URLImage 컴포저블을 활용하여 이미지를 간단히 로드하고 표시할 수 있습니다.

``` kotlin
@Composable
fun ImageExample() {
    URLImage(
        url = "https://picsum.photos/id/0/5000/3333",
        contentScale = ImageContentScale.Crop,
        transform = ImageTransform.Grayscale // 흑백 변환
    )
}

```

## 📂 프로젝트 구조

### GalleryApp 구조

``` kotlin
├── GalleryApp.kt                // Application 클래스
├── data                         // 데이터 계층
│   ├── dto                      // 네트워크 응답 데이터 모델
│   │   └── ImagesResponse.kt
│   ├── mapper                   // 데이터 변환 (DTO -> Domain)
│   │   └── ImagesMapper.kt
│   ├── network                  // API 서비스 정의
│   │   └── PicsumApiService.kt
│   ├── paging                   // Paging3 소스
│   │   └── GalleryPagingSource.kt
│   └── repository               // Repository 구현체
│       └── DefaultImageRepository.kt
├── di                           // Hilt 모듈
│   └── module
│       ├── NetworkModule.kt     // Retrofit 및 Hilt 네트워크 설정
│       └── RepositoryModule.kt  // Repository Hilt 모듈
├── domain                       // 도메인 계층
│   ├── model                    // 도메인 모델
│   │   ├── GalleryImage.kt
│   │   └── User.kt
│   └── repository               // Repository 인터페이스
│       └── ImageRepository.kt
└── presentation                 // UI 계층
├── MainActivity.kt          // 앱의 진입점
├── gallery                  // 갤러리 리스트 화면
│   ├── GallerySection.kt
│   ├── GalleryViewModel.kt
│   ├── MyGalleryScreen.kt
│   ├── ProfileSection.kt
│   ├── component            // 갤러리 전용 컴포넌트
│   │   └── GalleryItemCard.kt
│   └── preview              // 미리보기 설정
│       └── GalleryPreviewParameterProvider.kt
├── imagedetail              // 이미지 상세 화면
│   ├── GalleryDetailViewModel.kt
│   └── ImageDetailScreen.kt
├── navigation               // Navigation 구성
│   ├── GalleryNavHost.kt
│   └── ImageRoute.kt
├── ui                       // 공통 UI 요소
│   ├── component
│   │   ├── CloseButton.kt
│   │   ├── EmptyScreen.kt
│   │   ├── ErrorScreen.kt
│   │   ├── LoadingScreen.kt
│   │   ├── TemperatureText.kt
│   │   └── TopRoundedBackground.kt
│   └── theme                // 앱 테마 구성
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── utils
└── UIState.kt           // UI 상태 관리 클래스
```

### Chlide 구조

``` kotlin
├── cache
│   ├── Cache.kt              // 캐시 인터페이스 (메모리 및 디스크 캐시 공통 인터페이스)
│   ├── CacheManager.kt       // 메모리와 디스크 캐시를 관리하는 클래스
│   ├── DiskCache.kt          // 디스크에 이미지를 저장하고 불러오는 캐시 구현체
│   └── MemoryCache.kt        // 메모리(RAM)를 사용하는 캐시 구현체
├── compose
│   ├── URLImage.kt           // URL에서 이미지를 로드하고 화면에 표시하는 컴포저블
│   ├── URLImageState.kt      // 이미지 로딩 상태를 나타내는 클래스 (Loading, Success, Error)
│   ├── component
│   │   ├── ErrorScreen.kt    // 이미지 로드 실패 시 표시할 에러 화면 컴포저블
│   │   └── LoadingScreen.kt  // 이미지 로딩 중 표시할 로딩 화면 컴포저블
│   └── option
│       ├── ImageContentScale.kt  // 이미지 크기 조정을 위한 sealed class (Crop, Fit 등)
│       └── ToContentScale.kt     // ImageContentScale을 Compose의 ContentScale로 변환하는 확장 함수
├── dispatcher
│   ├── DefaultDispatcherProvider.kt  // 기본 Coroutine Dispatcher 제공
│   └── DispatcherProvider.kt         // Dispatcher 인터페이스 (테스트 및 커스터마이징 가능)
├── imageload
│   ├── DefaultImageLoader.kt         // 기본 이미지 로더 (캐시 및 네트워크 처리)
│   ├── ImageDownloader.kt            // URL에서 이미지를 다운로드하는 클래스
│   ├── ImageLoader.kt                // 이미지 로더 인터페이스 (커스터마이징 가능)
│   └── transform
│       └── Grayscale.kt              // 이미지를 흑백으로 변환하는 트랜스포머 클래스
├── pool
│   └── BitmapPool.kt                 // 비트맵 재활용 풀 구현
└── utils
    ├── HttpConnection.kt             // HTTP 연결 생성 유틸리티 클래스
    └── Ratio.kt                      // 비율 계산을 돕는 확장 함수
```


### state를 활용하여 loading, error 등의 상태를 관리

- 공통 컴포넌트(ui/component): ErrorScreen, LoadingScreen으로 다양한 상태 대응.
  에러 발생 시, 재시도 버튼으로 사용자 편의성 향상.
- 네트워크 장애 발생 시 재시도 버튼을 통해 이미지를 다시 불러올 수 있도록 구현
- 네트워크 에러나 구성 변경 시에도 ViewModel에 저장된 imageId를 통해 복구 가능.


### 가로모드 및 다크모드 지원

### 주요 라이브러리

- Hilt: 의존성 관리를 자동화 및 간단한 어노테이션(@Inject, @HiltViewModel)으로 DI 구성
- Retrofit: API 요청 및 응답 처리를 간단하고 안정적으로 구현
- Kotlin Serialization: Kotlin 특화 빠르고 간편한 JSON 직렬화/역직렬화 지원
- Paging3: 대규모 데이터셋(이미지 리스트)을 효율적으로 로드 및 표시
- Navigation: Compose 환경에서 화면 전환을 선언적으로 구성

### 참고: 프로파일러

<img width="600" alt="스크린샷 2025-01-26 오후 9 38 37" src="https://github.com/user-attachments/assets/cb8a845a-cf49-491f-b1fd-a260827ea350" />
<img width="600" alt="스크린샷 2025-01-26 오후 9 39 57" src="https://github.com/user-attachments/assets/ee3bcb9a-4704-4364-9f2d-cef3734ac058" />
<img width="600" alt="스크린샷 2025-01-26 오후 9 39 23" src="https://github.com/user-attachments/assets/a7c31c86-6208-4403-b1f5-68d19b3b2b2c" />
