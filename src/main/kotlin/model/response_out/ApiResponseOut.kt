package com.milli.model.response_out

data class ApiResponseOut <T>(
    val metadata:MetadataOut,
    val data:T
)
