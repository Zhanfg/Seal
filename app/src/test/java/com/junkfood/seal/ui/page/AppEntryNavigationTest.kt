package com.junkfood.seal.ui.page

import com.junkfood.seal.ui.common.Route
import com.junkfood.seal.ui.page.downloadv2.configure.DownloadDialogViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppEntryNavigationTest {
    private val configure = DownloadDialogViewModel.SheetState.Configure(listOf("https://www.bilibili.com/video/BV1myHQ6SEqR"))

    @Test
    fun hiddenConfigurationDoesNotResetSettingsNavigation() {
        assertFalse(shouldReturnHomeForSheet(
            configure,
            DownloadDialogViewModel.SheetValue.Hidden,
            Route.SETTINGS_PAGE,
        ))
    }

    @Test
    fun expandedConfigurationReturnsToDownloadPage() {
        assertTrue(shouldReturnHomeForSheet(
            configure,
            DownloadDialogViewModel.SheetValue.Expanded,
            Route.SETTINGS_PAGE,
        ))
    }

    @Test
    fun expandedConfigurationOnHomeDoesNotNavigate() {
        assertFalse(shouldReturnHomeForSheet(
            configure,
            DownloadDialogViewModel.SheetValue.Expanded,
            Route.HOME,
        ))
    }

    @Test
    fun expandedUrlInputDoesNotResetSettingsNavigation() {
        assertFalse(shouldReturnHomeForSheet(
            DownloadDialogViewModel.SheetState.InputUrl,
            DownloadDialogViewModel.SheetValue.Expanded,
            Route.SETTINGS_PAGE,
        ))
    }
}
