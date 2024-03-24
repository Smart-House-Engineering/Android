package com.app.eazyliving.model

data class DevicesResponse(
    val message: String,
    val devices: Devices
)

data class Devices(
    val fan: Boolean,
    val yellowLed: Int,
    val RFan: Boolean,
    val motion: Boolean,
    val buzzer:Boolean,
    val relay: Boolean,
    val door: Int,
    val window: Int,
    val gasSensor: Int,
    val photocell: Int,
    val soilSensor: Int,
    val steamSensor: Int,
    val whiteLed: Boolean,
    val button1:Boolean,
    val button2:Boolean
)

data class DeviceList(val sensors: List<SensorData>)



