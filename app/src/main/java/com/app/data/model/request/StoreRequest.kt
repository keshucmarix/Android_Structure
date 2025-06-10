package com.app.data.model.request

import com.google.gson.annotations.SerializedName

data class StoreRequest(
    @SerializedName("page")
    var page: Int? = null,
    /*  @SerializedName("limit")
      var limit: Int? = Common.PAGE_LIMIT,*/
    @SerializedName("categories")
    var categories: String? = null,
    @SerializedName("regions")
    var regions: String? = null,
    @SerializedName("search")
    var search: String? = null,
    @SerializedName("merchantId")
    var merchantId: Int? = null,
    @SerializedName("sortBy")
    var sortBy: String? = null,
    @SerializedName("BussinessId")
    var BussinessId: String? = "123456",
)
