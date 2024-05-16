package com.app.eazyliving.model

data class DevicesResponse(
    val message: String,
    val devices: Devices
)

data class Devices(
    val fan: Boolean,   // Fan
    val yellowLed: Int,  // Yellow LED
    val RFan: Boolean,  // RFan (Rotating Fan)?
    val motion: Boolean, // Motion Sensor
    val buzzer:Boolean, // Buzzer, makes your ears bleed
    val relay: Boolean, // Relay, some sort of on/off switch
    val door: Int, // Door motor
    val window: Int, // Window motor
    val gasSensor: Int, // Gas sensor, detects gas leaks, or if someone shits the bed
    val photocell: Int, // Photocell, detects light
    val soilSensor: Int, // Soil sensor, detects moisture in soil
    val steamSensor: Int, // Steam sensor, detects steam/humidity?
    val whiteLed: Boolean, // White LED
    val button1:Boolean, // Button 1 for house alarm probably
    val button2:Boolean  // Button 2 for house alarm probably
)

data class DeviceList(val sensors: List<SensorData>)



