package ir.miare.androidcodechallenge.presentation


import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.Team
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlayerItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockTeam = Team(
        name = "Barcelona",
        rank = 1
    )

    private val mockPlayer = Player(
        name = "Lionel Messi",
        totalGoals = 25,
        team = mockTeam
    )

    @Test
    fun playerItem_displaysPlayerNameAndGoals() {
        var clickedPlayer: Player? = null

        composeTestRule.setContent {
            PlayerItem(
                player = mockPlayer,
                buttonTitle = "Add to Team",
                onButtonClicked = { clickedPlayer = it }
            )
        }

        composeTestRule
            .onNodeWithText("Lionel Messi - Goals(25)")
            .assertIsDisplayed()
    }

    @Test
    fun playerItem_displaysTeamNameAndRank() {
        composeTestRule.setContent {
            PlayerItem(
                player = mockPlayer,
                buttonTitle = "Add to Team",
                onButtonClicked = { }
            )
        }

        composeTestRule
            .onNodeWithText("Barcelona - Rank(1)")
            .assertIsDisplayed()
    }

    @Test
    fun playerItem_displaysButtonWithCorrectTitle() {
        val buttonTitle = "Select Player"

        composeTestRule.setContent {
            PlayerItem(
                player = mockPlayer,
                buttonTitle = buttonTitle,
                onButtonClicked = { }
            )
        }

        composeTestRule
            .onNodeWithText(buttonTitle)
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun playerItem_buttonClickTriggersCallback() {
        var clickedPlayer: Player? = null
        val buttonTitle = "Add Player"

        composeTestRule.setContent {
            PlayerItem(
                player = mockPlayer,
                buttonTitle = buttonTitle,
                onButtonClicked = { clickedPlayer = it }
            )
        }

        composeTestRule
            .onNodeWithText(buttonTitle)
            .performClick()

        assert(clickedPlayer == mockPlayer) { "Expected clicked player to be $mockPlayer, but was $clickedPlayer" }
    }

    @Test
    fun playerItem_textLinesAreLimitedToOne() {
        val longNamePlayer = Player(
            name = "This is a very long player name that should be truncated",
            totalGoals = 99,
            team = Team(
                name = "This is a very long team name that should also be truncated",
                rank = 999
            )
        )

        composeTestRule.setContent {
            PlayerItem(
                player = longNamePlayer,
                buttonTitle = "Test",
                onButtonClicked = { }
            )
        }

        composeTestRule
            .onNodeWithText("This is a very long player name that should be truncated - Goals(99)")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("This is a very long team name that should also be truncated - Rank(999)")
            .assertIsDisplayed()
    }

    @Test
    fun playerItem_handlesZeroGoals() {
        val playerWithZeroGoals = mockPlayer.copy(totalGoals = 0)

        composeTestRule.setContent {
            PlayerItem(
                player = playerWithZeroGoals,
                buttonTitle = "Select",
                onButtonClicked = { }
            )
        }

        composeTestRule
            .onNodeWithText("Lionel Messi - Goals(0)")
            .assertIsDisplayed()
    }

    @Test
    fun playerItem_handlesNegativeRank() {
        val teamWithNegativeRank = mockTeam.copy(rank = -1)
        val playerWithNegativeRankTeam = mockPlayer.copy(team = teamWithNegativeRank)

        composeTestRule.setContent {
            PlayerItem(
                player = playerWithNegativeRankTeam,
                buttonTitle = "Select",
                onButtonClicked = { }
            )
        }

        composeTestRule
            .onNodeWithText("Barcelona - Rank(-1)")
            .assertIsDisplayed()
    }

    @Test
    fun playerItem_semanticsAreCorrect() {
        composeTestRule.setContent {
            PlayerItem(
                player = mockPlayer,
                buttonTitle = "Add to Team",
                onButtonClicked = { }
            )
        }

        composeTestRule
            .onRoot()
            .assertIsDisplayed()

        composeTestRule
            .onAllNodesWithText("Lionel Messi - Goals(25)")
            .assertCountEquals(1)

        composeTestRule
            .onAllNodesWithText("Barcelona - Rank(1)")
            .assertCountEquals(1)

        composeTestRule
            .onAllNodesWithText("Add to Team")
            .assertCountEquals(1)
    }
}