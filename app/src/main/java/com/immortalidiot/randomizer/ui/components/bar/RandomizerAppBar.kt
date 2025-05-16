package com.immortalidiot.randomizer.ui.components.bar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.immortalidiot.randomizer.R
import com.immortalidiot.randomizer.ui.RandomNavGraph
import com.immortalidiot.randomizer.ui.Routes
import com.immortalidiot.randomizer.ui.theme.RandomizerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomizerAppBar(
    navController: NavHostController,
    isExpanded: Boolean,
    onMenuClick: () -> Unit = {},
    onDismissMenu: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.SemiBold
            )
        },
        actions = {
            IconButton(
                onClick = onMenuClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = ""
                )

                // TODO: close drop down menu after clicks on item
                DropdownMenu(
                    modifier = Modifier
                        .width(160.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    expanded = isExpanded,
                    onDismissRequest = onDismissMenu
                ) {
                    DropdownMenuItem(
                        text = {
                            AppBarMenuItem(
                                icon = Icons.Rounded.History,
                                title = stringResource(R.string.history)
                            )
                        },
                        onClick = {
                            navController.navigateSingleTopRestoreState(Routes.HISTORY_ROUTE)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            AppBarMenuItem(
                                icon = Icons.Rounded.Settings,
                                title = stringResource(R.string.settings)
                            )
                        },
                        onClick = {
                            navController.navigateSingleTopRestoreState(Routes.SETTINGS_ROUTE)
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            AppBarMenuItem(
                                icon = Icons.Rounded.Info,
                                title = stringResource(R.string.application_info)
                            )
                        },
                        onClick = {
                            navController.navigateSingleTopRestoreState(Routes.APPLICATION_INFO_ROUTE)
                        }
                    )
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

@Composable
fun AppBarMenuItem(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = modifier.weight(1f))
        Icon(
            imageVector = icon,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    RandomizerTheme {
        val expanded = remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                RandomizerAppBar(
                    navController = rememberNavController(),
                    isExpanded = expanded.value,
                    onMenuClick = { expanded.value = true },
                    onDismissMenu = { expanded.value = false }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                RandomNavGraph(navController = rememberNavController())
            }
        }
    }
}