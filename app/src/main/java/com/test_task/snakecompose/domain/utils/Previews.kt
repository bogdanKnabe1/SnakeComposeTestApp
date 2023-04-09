package com.test_task.snakecompose.domain.utils

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


@PhoneMainPreview
@TabletMainPreview
annotation class DevicesPreview

@Preview(
    name = "Phone",
    group = "Devices",
    showSystemUi = true,
    device = Devices.PIXEL_3
)
annotation class PhoneMainPreview

@Preview(
    name = "Phone",
    group = "Devices",
    showSystemUi = false,
    device = Devices.PIXEL_3
)
annotation class PhonePreview

@Preview(
    name = "Tablet",
    group = "Devices",
    showSystemUi = true,
    device = Devices.TABLET
)
annotation class TabletMainPreview

@Preview(
    name = "Tablet",
    group = "Devices",
    showSystemUi = false,
    device = Devices.TABLET
)
annotation class TabletPreview