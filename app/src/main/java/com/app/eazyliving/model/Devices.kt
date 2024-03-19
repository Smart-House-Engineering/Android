package com.app.eazyliving.model

data class Devices(
    val fan: Boolean,
    val yellowLed: Int,
    val lights: Boolean,
    val RFan: Boolean,
    val motion: Boolean,
    val buzzer:Boolean,
    val relay: Boolean,
    val servo1: Int,
    val servo2: Int,
    val gasSensor: Int,
    val photocell: Int,
    val soilSensor: Int,
    val steamSensor: Int,
    val whiteLed: Boolean,
    val button1:Boolean,
    val button2:Boolean
)

data class DeviceList(val sensors: List<SensorData>)

fun Devices.toSensorDataList(): List<SensorData> {

    return listOf(
        SensorData("fan", this.fan),
        SensorData("yellow LED", this.yellowLed > 0),
        SensorData("lights", this.lights),
        SensorData("RFan", this.RFan),
        SensorData("motion", this.motion),
        SensorData("buzzer", this.buzzer),
        SensorData("relay", this.relay),
        SensorData("servo1", this.servo1 > 0),
        SensorData("servo2", this.servo2 > 0),
        SensorData("gasSensor", this.gasSensor > 0),
        SensorData("photocell", this.photocell > 0),
        SensorData("soilSensor", this.soilSensor > 0),
        SensorData("steamSensor", this.steamSensor > 0),
        SensorData("white LED", this.whiteLed),
        SensorData("button1", this.button1),
        SensorData("button2", this.button2)
    )
}

