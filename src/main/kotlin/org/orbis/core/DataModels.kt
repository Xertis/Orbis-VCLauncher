package org.orbis.core
import kotlinx.serialization.*

@Serializable
data class Instant(
    val name: String,
    val path: String,
    val group: String,
    val version: String,
    val description: String,
    val timeplayed: Int,

    @SerialName("vcim_name")
    val vcimName: String,

    @SerialName("executable_file")
    val executable: String,

    @SerialName("args")
    val startArgs: String,
)

@Serializable
data class InstantShort(
    val name: String,
    val group: String,
    val version: String,

    @SerialName("name_vcim")
    val vcimName: String,
)
