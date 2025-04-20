# GitHub Client App

A modern Android application built using Kotlin and Jetpack Compose that displays GitHub users and their public repositories. Designed with adaptive layouts and clean architecture principles.

---

## ✅ Core Features (as per requirements)

The following features were explicitly mentioned in the provided instructions and have been fully implemented:

- ✅ **GitHub user list** fetched from `https://api.github.com/users`
- ✅ **Search functionality** to filter users by username
- ✅ **Clicking a user** navigates to a detail screen showing their public repositories
- ✅ **Repository list** includes name, description, stars, and language
- ✅ **Responsive layout** that adapts to both portrait and landscape using `ListDetailPaneScaffold`
- ✅ **Jetpack Compose** used for all UI
- ✅ **MVVM Architecture** applied across all modules
- ✅ **At least one Unit Test** and one UI Test included
- ✅ **GitHub Authentication** using Personal Access Token with header injection

---

## ✨ Additional Features Added

Beyond the requirements, the following extra enhancements were implemented:

- ⚡ **Offline support** via Room database for both users and search
- 📱 **Adaptive UI** with `ListDetailPaneScaffold` for landscape and tablet-friendly layout
- 🔁 **Custom shimmer loading effect** without using external libraries
- 🧪 **Multiple test cases** covering ViewModel, UI, DAO with `mockk`, `turbine`, `coroutines-test`
- 🧩 **Clean modular structure** using separate modules for features, data, storage, theme, and common
- 🔐 **Secure token & base URL config** through `local.properties`
- 🌐 **Chrome Custom Tabs** used to open repository links externally
- 🔄 **Live search with debounce** using `StateFlow`
- 🧱 **Version Catalog** powered by `libs.versions.toml` for managing dependencies centrally

---

## 📈 Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material3)
- **Architecture:** MVVM + Clean Modular
- **Navigation:** ListDetailPaneScaffold (adaptive)
- **DI:** Hilt
- **Database:** Room
- **Network:** Ktor + Serialization
- **Image:** Coil

---

## 🔧 Configuration

Create a file named `local.properties` in the project root, and add:

```properties
base_url=https://api.github.com
access_token=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
```

> Replace `access_token` with your GitHub personal access token

---

## 🧪 Tests

### 🧬 Unit Tests
- `UsersViewModelTest` (flow, debounce, state)
- `UserDaoTest` (Room in-memory)
- `FakeMainRepositoryImplTest`

### 🖥️ UI Tests
- `UserListScreenTest` with Compose UI Test

### 🛠 Tools
- `mockk`, `turbine`, `coroutines-test`, `compose-ui-test`, `junit`

---

## 🔮 Possible Future Enhancements

The following features were considered but intentionally skipped to keep the project clean, loosely coupled, and easy to evaluate. They can be added easily due to the modular architecture:

- [ ] **Paging 3 support** for user and repo lists  
      ⤷ Currently limited to 50 users to reduce API usage and simplify implementation. Architecture supports adding Paging via RemoteMediator easily.

- [ ] **Repository details as full screen view**  
      ⤷ Current design uses master-detail layout. Full page navigation can be added for smaller screens.

- [ ] **Improved error handling & retry mechanism**  
      ⤷ Currently shows basic error message. Retry button and custom error UI can improve UX.

- [ ] **CI/CD Integration** using GitHub Actions  
      ⤷ Auto-test run and lint checks can be configured easily.

- [ ] **Dark mode toggle** and theme switcher  
      ⤷ App already uses Material3 theming — toggle can be added easily.

- [ ] **In-app repository search** within user details  
      ⤷ Useful if user has 100+ repos. Not included to stay focused on core functionality.

- [ ] **Support for tablet split-view layout**  
      ⤷ Scaffold already adaptive. Can optimize for larger breakpoints.

---

## 🏃 How to Run

```bash
./gradlew test                  # Run all unit tests
./gradlew connectedAndroidTest # Run UI tests (requires emulator/device)
```

---

## 📁 Project Structure

```
├── app/
│   ├── features/
│   │   ├── main/
│   │   └── auth/
│   ├── common/         # Shared data models
│   ├── storage/        # Room, DataStore
│   ├── theme/          # Compose theming
```

---

## 📸 Screenshots

| User List | Repository View |
|-----------|------------------|
| ![User List](screenshots/user_list.png) | ![Repository](screenshots/repo_screen.png) |

---

## 🎥 Demo Video

[![Watch Demo](https://img.youtube.com/vi/YOUTUBE_VIDEO_ID/0.jpg)](https://www.youtube.com/watch?v=YOUTUBE_VIDEO_ID)

> Replace `YOUTUBE_VIDEO_ID` with your actual video ID

---

## 👤 Developer

**Md Sabbir Ahmed Khan**  
Android Developer | Kotlin & Compose Enthusiast  
🇸🇬 Singapore | 📧 md.sabbir.ahmed.khan@gmail.com  
🔗 [GitHub](https://github.com/androidrey)
