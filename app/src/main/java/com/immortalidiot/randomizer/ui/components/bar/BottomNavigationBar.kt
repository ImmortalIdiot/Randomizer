package com.immortalidiot.randomizer.ui.components.bar

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.immortalidiot.randomizer.ui.navigationItems

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val navbarBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navbarBackStackEntry?.destination?.route

    NavigationBar {
        listOf(
            NavigationBar {
                navigationItems.forEach { item ->
                    val label = stringResource(item.label)
                    val color = if (currentRoute == item.route) LocalContentColor.current
                    else Color.Gray

                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        },
                        label = {
                            Text(
                                text = label,
                                color = color
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "",
                                tint = color
                            )
                        }
                    )
                }
            }
        )
    }
}
