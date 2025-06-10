package com.app.data.model.response


import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("about")
    val about: Any? = null,
    @SerializedName("affiliate_marketing")
    val affiliateMarketing: String? = null,
    @SerializedName("awin_commissionRange")
    val awinCommissionRange: String? = null,
    @SerializedName("awin_id")
    val awinId: Double? = null,
    @SerializedName("awin_kpi")
    val awinKpi: String? = null,
    @SerializedName("awin_programmeInfo")
    val awinProgrammeInfo: String? = null,
    @SerializedName("cashback_percentage")
    val cashbackPercentage: Double? = null,
    @SerializedName("categories")
    val categories: List<Any>? = null,
    @SerializedName("category_id")
    val categoryId: Any? = null,
    @SerializedName("clickThroughUrl")
    val clickThroughUrl: String? = null,

    @SerializedName("donated")
    val donated: Any? = null,
    @SerializedName("id")
    val id: Double? = null,
    @SerializedName("logo")
    val logo: String? = null,
    @SerializedName("max_cashback_amount")
    val maxCashbackAmount: Double? = null,
    @SerializedName("max_cashback_percentage")
    val maxCashbackPercentage: Double? = null,
    @SerializedName("min_cashback_amount")
    val minCashbackAmount: Double? = null,
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("redirectUrl")
    val redirectUrl: String? = null,

    @SerializedName("status")
    val status: String? = null,
    @SerializedName("store_popularity")
    val storePopularity: String? = null,
    @SerializedName("terms_and_conditions")
    val termsAndConditions: String? = null,
    @SerializedName("website")
    val website: String? = null,
)

