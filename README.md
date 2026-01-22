Program Structure

app/
├── data/					(Data Layer)
│   ├── model/
│   │   ├── User.kt              (Data Models)
│   │   └── Todo.kt
│   ├── remote/
│   │   ├── ApiService.kt        (API Interface)
│   │   └── RetrofitClient.kt    (Network Client)
│   └── repository/
│       └── UserRepository.kt    (Data Repository)
├── domain/                      (Domain Layer)
│   ├── UserState.kt             (UI States)
│   └── TodoState.kt
├── ui/                          (Presentation Layer)
│   ├── screens/
│   │   ├── UserListScreen.kt    (UI Components)
│   │   ├── LoadingScreen.kt
│   │   ├── ErrorScreen.kt
│   │   ├── TodoScreen.kt
│   │   └── MainScreen.kt        (Screen Orchestrator)
│   └── viewmodel/
│       ├── UserViewModel.kt     (Business Logic)
│       └── TodoViewModel.kt
└── MainActivity.kt              (App Entry Point)

Architecture Layer

1. Data Layer (Where data comes from)
  •	Handles data sources (API, database, etc.)
  •	Manages network calls
  •	Provides data to other layers

3. Domain Layer (Business rules)
  •	Defines UI states
  •	Contains business logic
  •	Independent of Android framework

4. Presentation Layer (What user sees)
  •	UI components (Composables)
  •	ViewModels (manages UI state)
  •	User interaction

User.kt
  •	Defines the structure of a User object
  •	Maps JSON data from the API to Kotlin objects
  •	Contains nested data classes (Address, Company, Geo)

ApiService.kt (API Interface)
  •	Defines what API calls the app can make
  •	Specifies the HTTP method (GET, POST, etc.)
  •	Describes the endpoint URLs
  •	Does NOT make the actual network call (Retrofit does that)
  •	@GET = HTTP GET request
  •	suspend = coroutine function (runs on background thread)
  •	@Query = adds query parameter to URL
  •	Returns Response<T> which contains both data and HTTP status

UserRepository.kt (Data Repository)
What it does:
  •	Centralized location for all data operations
  •	Makes the actual API calls using ApiService
  •	Handles errors and wraps results in Result<T>
  •	Switches to IO thread for network operations
  •	Acts as a mediator between ViewModel and data sources

Why it's important:
  •	ViewModel doesn't need to know HOW to get data
  •	Easy to switch data sources (API → Database) without changing ViewModel
  •	Centralized error handling
  •	Can combine multiple data sources (cache + API)

UserState.kt (Sealed Class for UI States)
  •	Defines all possible states the UI can be in
  •	Type-safe way to represent Loading, Success, and Error states
  •	Each state can carry different data

UserViewModel.kt (ViewModel)
  •	Holds and manages UI state (Loading, Success, Error)
  •	Fetches data from Repository
  •	Exposes data to UI via StateFlow
  •	Survives configuration changes (screen rotation)
  •	Handles business logic (when to load, retry, etc.)

Data Flow:

UI → ViewModel. loadUsers()
      ↓
ViewModel → Repository.getUsers()
      ↓
Repository → ApiService → Network
      ↓
Result → ViewModel updates StateFlow
      ↓
UI automatically recomposes with new state

UserListScreen.kt
  •	Pure UI component (Composable function)
  •	Displays list of users using LazyColumn
  •	Handles user clicks
  •	Receives data as parameters (stateless)

LoadingScreen.kt (UI Composable)
  •	Shows a loading indicator when data is being fetched
  •	Provides visual feedback to user
  •	Separate, reusable component

ErrorScreen.kt (UI Composable)
  •	Shows error message when something goes wrong
  •	Provides a Retry button to try again

MainActivity.kt (Entry Point)
  •	Entry point of the Android app
  •	Hosts the Compose UI
  •	Extends ComponentActivity (base class for Compose)
  •	Sets up the main screen

Complete Step-by-step Flow
1. USER LAUNCHES APP
   └── MainActivity.onCreate() is called

2. SETUP COMPOSE UI
   └── setContent { MainScreen() }

3. MAINSCREEN COMPOSES
   └── Creates UserViewModel (or gets existing)
   └── Collects userState StateFlow

4. VIEWMODEL INITIALIZES
   └── init { loadUsers() }

5. LOAD USERS FUNCTION
   └── Set state to Loading
   └── _userState.value = UserState.Loading

6. UI UPDATES (AUTOMATICALLY)
   └── when (state) { is Loading -> LoadingScreen() }
   └── User sees loading spinner

7. FETCH DATA
   └── Repository.getUsers()
   └── withContext(Dispatchers.IO) { apiService.getUsers() }

8. API CALL
   └── Retrofit makes HTTP GET request
   └── GET https://jsonplaceholder.typicode.com/users

9. SERVER RESPONDS
   └── Returns JSON array of users

10. PARSE RESPONSE
    └── Gson converts JSON to List<User>

11. REPOSITORY RETURNS
    └── Result. success(users)

12. VIEWMODEL UPDATES STATE
    └── _userState. value = UserState.Success(users)

13. UI RECOMPOSES (AUTOMATICALLY)
    └── when (state) { is Success -> UserListScreen(users) }

14. USER SEES LIST
    └── LazyColumn displays all users
