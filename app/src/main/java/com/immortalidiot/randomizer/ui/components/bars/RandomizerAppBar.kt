package com.immortalidiot.randomizer.ui.components.bars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.RandomNavGraph
import com.immortalidiot.randomizer.ui.Routes
import com.immortalidiot.randomizer.ui.appBarMenuItems
import com.immortalidiot.randomizer.ui.components.items.AppBarMenuItem
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RandomizerAppBar(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val menuClosesDelay = 300L

    fun navigateAndClose(route: String) {
        coroutineScope.launch {
            navController.navigateSingleTopRestoreState(route)
            delay(menuClosesDelay)
            expanded = false
        }
    }

    CustomTopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
            )
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = ""
                )

                DropdownMenu(
                    modifier = Modifier
                        .width(160.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    appBarMenuItems.forEach { (name, icon, route) ->
                        DropdownMenuItem(
                            text = {
                                AppBarMenuItem(
                                    icon = icon,
                                    title = stringResource(name)
                                )
                            },
                            onClick = { navigateAndClose(route) }
                        )
                    }
                }
            }
        }
    )
}

private fun NavController.navigateSingleTopRestoreState(route: String) {
    navigate(route) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    RandomizerTheme {
        Scaffold(topBar = { RandomizerAppBar(navController = rememberNavController()) }) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                RandomNavGraph(navController = rememberNavController(), entryPoint = Routes.RANGE_ROUTE)
            }
        }
    }
}
