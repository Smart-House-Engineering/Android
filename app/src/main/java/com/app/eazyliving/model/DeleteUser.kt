package com.app.eazyliving.model

import com.google.gson.annotations.SerializedName

data class DeleteUser(

    @SerializedName("deleteUserEmail")
    val deleteUserEmail: String
)



