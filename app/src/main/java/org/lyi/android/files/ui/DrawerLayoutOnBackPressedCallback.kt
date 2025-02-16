/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.ui

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener

class DrawerLayoutOnBackPressedCallback(
    private val drawerLayout: DrawerLayout,
    private val gravity: Int = GravityCompat.START
) : OnBackPressedCallback(drawerLayout.isDrawerVisibleAndUnlocked(gravity)) {
    init {
        drawerLayout.addDrawerListener(
            object : SimpleDrawerListener() {
                override fun onDrawerOpened(drawerView: View) {
                    isEnabled = drawerLayout.isDrawerVisibleAndUnlocked(gravity)
                }

                override fun onDrawerClosed(drawerView: View) {
                    isEnabled = drawerLayout.isDrawerVisibleAndUnlocked(gravity)
                }
            }
        )
    }

    override fun handleOnBackPressed() {
        drawerLayout.closeDrawer(gravity)
    }
}

private fun DrawerLayout.isDrawerVisibleAndUnlocked(gravity: Int): Boolean =
    isDrawerVisible(gravity) && getDrawerLockMode(gravity) == DrawerLayout.LOCK_MODE_UNLOCKED
