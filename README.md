# Android Assignment
######  We have an interview, yay

### TL;DR

Clone this project, open it in Android Studio and build it.

#### What is this?

This app shows a list of the top three scoring football players in different leagues.

#### What do I do with this?

- Clone it
- Open it in Android Studio
- Let it download its dependencies
- Make the project once (since we have some intermediate classes which need to be generated)
- Chill :)

#### 📝 Task Description
In this task, you are expected to design and implement an Android application that displays football player data for all leagues and players provided.

Core Requirements:
1. Display a list of players
- Player data must be displayed using pagination.
- Sorting should be applied across the entire dataset, not just the current page.
- The method of implementing pagination and sorting — and how they interact — is completely up to you.

2. Follow / Unfollow players
- The user must be able to follow or unfollow any player.
- Followed players should be persisted locally, so the follow status remains after closing and reopening the app.
- How you model and store this state is entirely your decision.

3. Followed players screen
- The user must be able to view their followed players in a dedicated screen.
- How this screen is accessed is also up to you (e.g. a button on the main screen, or a tab in bottom navigation).



#### 🔧 Technical Requirements
The following are mandatory and will be strictly evaluated:

Use Jetpack Compose for UI.

Follow Clean Architecture principles with clear separation of layers (Presentation / Domain / Data).

Implement Unit Tests for key components.

Implement UI Tests for important user flows.

Additional technical decisions — including state management, navigation, persistence, dependency injection, design system, etc. — are entirely up to you.

Final UI/UX design is also open to your judgment and will be evaluated based on your analysis and decisions.



#### 📌 Evaluation Criteria
This task is not just about the final result — we're primarily evaluating your approach and thought process:

- Accurate understanding and coverage of the requirements
- Scalable and flexible architectural design
- Clean and maintainable code structure
- Proper state and data flow management
- Code readability and modularity
- Smooth, intuitive, and consistent user experience
- Effective persistence and handling of follow state
- Meaningful test coverage (both Unit and UI)
- Quality of documentation and clarity of technical decisions

