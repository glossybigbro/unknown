# unknown

---

## 모듈 의존성 그래프

이 프로젝트는 **Graphviz**를 사용해 모듈 간 의존성 관계를 시각화합니다. 이를 통해 프로젝트 구조를 더 쉽게 이해하고 관리할 수 있습니다.

### 사용 방법

#### 1. **Graphviz 설치**
   - macOS: `brew install graphviz`
   - Ubuntu: `sudo apt-get install graphviz`
   - Windows: [Graphviz 다운로드](https://graphviz.org/download/)

#### 2. **그래프 생성**
   ```bash
   ./gradlew projectDependencyGraph
   ```
   실행 후, 프로젝트 루트에 `project.dot.png` 파일이 생성됩니다.

#### 3. **오류 발생 시**: 
   캐시를 정리한 후 아래 명령어로 다시 시도하세요.
   ```bash
   ./gradlew clean projectDependencyGraph --no-configuration-cache
   ```

---
